package xyz.funky493.little_big_hordes_of_mine.util;

import org.jetbrains.annotations.NotNull;

public class InputUtil {
    public static boolean checkComparatorString(String comparatorString, Float value) {
        // Check if the comparator string is == or !=
        if(comparatorString.matches("==")) {
            return value == Float.parseFloat(comparatorString);
        } else if(comparatorString.matches("!=")) {
            return value != Float.parseFloat(comparatorString);
        }

        // Check if its a single comparator and not a range
        if(!comparatorString.matches("[<>]+.*[<>]+")) {
            return simpleComparison(comparatorString, value);
        }

        // We know it is a range
        // Split by x
        String[] rangeSplit = comparatorString.split("x");
        return simpleComparison(rangeSplit[0] + "x", value) && simpleComparison("x" + rangeSplit[1], value);
    }

    @NotNull
    private static String getOperator(String comparatorString) {
        int operatorIndex;
        if(comparatorString.contains("<")) {
            operatorIndex = comparatorString.indexOf("<");
        } else {
            operatorIndex = comparatorString.indexOf(">");
        }
        // Get the operator as a string
        String operator = comparatorString.charAt(operatorIndex) + "";
        // If theres an equal sign *somewhere* then we probably have >= or <=
        if(comparatorString.contains("=")) {
            operator += "=";
        }
        return operator;
    }

    private static boolean simpleComparison(String comparatorString, float value) {
        // Where is our operator
        String operator = getOperator(comparatorString);
        String[] values = comparatorString.split(operator);
        float firstValue;
        float secondValue;
        if(values[0].equals("x")) {
            firstValue = value;
            secondValue = Float.parseFloat(values[1]);
        } else {
            firstValue = Float.parseFloat(values[0]);
            secondValue = value;
        }
        return switch(operator) {
            case ">" -> firstValue > secondValue;
            case ">=" -> firstValue >= secondValue;
            case "<" -> firstValue < secondValue;
            case "<=" -> firstValue <= secondValue;
            default -> false;
        };
    }

}
