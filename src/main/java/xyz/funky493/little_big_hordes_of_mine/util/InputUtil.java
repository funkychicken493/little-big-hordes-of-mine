package xyz.funky493.little_big_hordes_of_mine.util;

import org.jetbrains.annotations.NotNull;
import xyz.funky493.little_big_hordes_of_mine.LittleBigHordesOfMine;

public class InputUtil {
    public static boolean checkComparatorString(String comparatorString, Float value) {
        // Check if the comparator string is == or !=
        if(comparatorString.contains("==")) {
            String[] split = comparatorString.split("==");
            if(split[0].equals("x")) {
                return value == Float.parseFloat(split[1]);
            } else {
                return Float.parseFloat(split[0]) == value;
            }
        } else if(comparatorString.contains("!=")) {
            String[] split = comparatorString.split("!=");
            if(split[0].equals("x")) {
                return value != Float.parseFloat(split[1]);
            } else {
                return Float.parseFloat(split[0]) != value;
            }
        }

        // Check if its a single comparator and not a range
        if(!comparatorString.matches("[<>]+.*[<>]+")) {
            return simpleComparison(comparatorString, value);
        }

        //TODO: fix ranges

        // We know it is a range
        // Split by x
        String[] rangeSplit = comparatorString.split("x");
        LittleBigHordesOfMine.LOGGER.info(rangeSplit[0] + "\n" + rangeSplit[1]);
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
        if(values[0].contains("x")) {
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
            default -> throw new IllegalStateException("Unexpected value: " + operator);
        };
    }

}
