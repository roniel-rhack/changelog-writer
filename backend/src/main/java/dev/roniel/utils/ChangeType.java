package dev.roniel.utils;

public enum ChangeType {
    ADDED("added"),
    FIXED("fixed"),
    CHANGED("changed"),
    REMOVED("removed");

    private final String value;

    ChangeType(String value) {
        this.value = value;
    }

    public static ChangeType fromString(String value) {
        for (ChangeType type : ChangeType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }
}
