package org.shawty.Utilities;

import org.shawty.Core;

public enum Messages {
    INVENTORY_FULL_ERROR("Uh oh! Your inventory is full!"),
    LEVEL_ALREADY_REACHED("This minion is already past this upgrade!"),
    PREVIOUS_LEVEL_REQUIRED("You must unlock the previous level first!"),
    NOT_ENOUGH_XP("You do not have the required amount of XP!"),
    CHEST_LINKED("The chest has been linked!"),
    CHEST_LINK("Click on a chest to link!"),
    INVALID_PLAYER("This player does not exist!"),
    INVALID_COMMAND("This is a invalid command!"),
    INVALID_CHEST("This is not a valid chest!"),
    INVALID_MINION("We cannot find this type of minion!");
    private final String message;

    Messages(String message) {
        this.message = Core.getConfigClass().getPrefix() + " " + message;
    }

    public String getMessage() {
        return message;
    }

}
