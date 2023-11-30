package xyz.funky493.little_big_hordes_of_mine.datapack;

public abstract class Condition {
    public abstract ConditionType getConditionType();
    public abstract String getConditionId();
    public enum ConditionType {
        WORLD,
        FABRIC
    }
}
