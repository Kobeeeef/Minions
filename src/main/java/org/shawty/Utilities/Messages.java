package org.shawty.Utilities;

import org.shawty.Core;

public enum Messages {
    INVENTORY_FULL_ERROR("Uh oh! Your inventory is full!"),
    LEVEL_ALREADY_REACHED("This minion is already past this upgrade!"),
    PREVIOUS_LEVEL_REQUIRED("You must unlock the previous level first!"),
    NOT_ENOUGH_XP("You do not have the required amount of XP!"),
    INVALID_PLAYER("This player does not exist!"),
    INVALID_MINION("We cannot find this type of minion!");
    private final String message;

    Messages(String message) {
        this.message = Core.getConfigClass().getPrefix() + " " + message;
    }

    public String getMessage() {
        return message;
    }

}
