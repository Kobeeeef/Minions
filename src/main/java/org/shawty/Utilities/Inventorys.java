package org.shawty.Utilities;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Inventorys {
    Inventory inventory;

    public Inventorys(Inventory inventory) {
        this.inventory = inventory;
    }

    public boolean isFull() {
        return inventory.firstEmpty() == -1;
    }
    public void removeItem(ItemStack itemToRemove, int amount) {
        for (ItemStack itemStack : inventory) {
            // Check that the ItemStack isn't null (empty/air slots) or if it's not a slot containing diamonds
            if(itemStack == null || !itemStack.equals(itemToRemove)) {
                // if so, skip running further logic on this stack
                continue;
            }
            // Remove one from the amount, the ItemStack will be removed if it has 0 amount
            itemStack.setAmount(itemStack.getAmount() - amount);
            // Break out of the parent for loop to avoid removing more than we want
            break;
        }
    }

}
