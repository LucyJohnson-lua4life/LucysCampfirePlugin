package com.lcf.utils;

import com.hypixel.hytale.server.core.inventory.Inventory;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.inventory.container.EmptyItemContainer;
import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
import com.hypixel.hytale.server.core.inventory.container.SimpleItemContainer;

import java.util.Map;

public class ClassSelectionUtils {


    public static final String CLASS_WARRIOR = "Warrior";
    public static final String CLASS_MAGE = "Mage";

    public static Map<String, Integer> classIndex = Map.of(CLASS_WARRIOR, 0, CLASS_MAGE, 1);

    public static Map<String, Inventory> classInventoryMapping = Map.of(CLASS_WARRIOR, createRoyalGuardInventory(),
    CLASS_MAGE, createArcaneMageInventory());

    public static String getNextClass(String currentClass) {
        int size = classIndex.size();

        Integer currentIndex = classIndex.get(currentClass);
        if (currentIndex == null) {
            currentIndex = 0;
        }

        int nextIndex = (currentIndex + 1) % size;

        return classIndex.entrySet()
                .stream()
                .filter(e -> e.getValue() == nextIndex)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow();
    }

    public static String getPreviousClass(String currentClass) {
        int size = classIndex.size();

        Integer currentIndex = classIndex.get(currentClass);
        if (currentIndex == null) {
            currentIndex = 0;
        }

        // Decrement with wrap-around
        int prevIndex = (currentIndex - 1 + size) % size;

        return classIndex.entrySet()
                .stream()
                .filter(e -> e.getValue() == prevIndex)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow();
    }
    private static Inventory createRoyalGuardInventory(){
        ItemContainer armor = new SimpleItemContainer((short) 4);

        armor.setItemStackForSlot((short) 0, new ItemStack("Armor_Mithril_Head", 1));
        armor.setItemStackForSlot((short) 1, new ItemStack("Armor_Mithril_Chest", 1));
        armor.setItemStackForSlot((short) 2, new ItemStack("Armor_Mithril_Hands", 1));
        armor.setItemStackForSlot((short) 3, new ItemStack("Armor_Mithril_Legs", 1));


        ItemContainer hotbar = new SimpleItemContainer((short) 9);
        hotbar.setItemStackForSlot((short) 0, new ItemStack("Weapon_Sword_Mithril", 1));
        hotbar.setItemStackForSlot((short) 1, new ItemStack("Tool_Pickaxe_Mithril", 1));
        hotbar.setItemStackForSlot((short) 2, new ItemStack("Food_Bread", 25));
        hotbar.setItemStackForSlot((short) 3, new ItemStack("Potion_Health_Large", 10));

        return new Inventory(EmptyItemContainer.INSTANCE, armor, hotbar,EmptyItemContainer.INSTANCE,EmptyItemContainer.INSTANCE,EmptyItemContainer.INSTANCE);
    }

    private static Inventory createArcaneMageInventory(){
        ItemContainer armor = new SimpleItemContainer((short) 4);

        armor.setItemStackForSlot((short) 0, new ItemStack("Armor_Prisma_Head", 1));
        armor.setItemStackForSlot((short) 1, new ItemStack("Armor_Prisma_Chest", 1));
        armor.setItemStackForSlot((short) 2, new ItemStack("Armor_Prisma_Hands", 1));
        armor.setItemStackForSlot((short) 3, new ItemStack("Armor_Prisma_Legs", 1));


        ItemContainer hotbar = new SimpleItemContainer((short) 9);
        hotbar.setItemStackForSlot((short) 0, new ItemStack("Weapon_Staff_Wood", 1));
        hotbar.setItemStackForSlot((short) 1, new ItemStack("Tool_Pickaxe_Mithril", 1));
        hotbar.setItemStackForSlot((short) 2, new ItemStack("Food_Bread", 25));
        hotbar.setItemStackForSlot((short) 3, new ItemStack("Potion_Health_Large", 10));

        return new Inventory(EmptyItemContainer.INSTANCE, armor, hotbar,EmptyItemContainer.INSTANCE,EmptyItemContainer.INSTANCE,EmptyItemContainer.INSTANCE);
    }

}
