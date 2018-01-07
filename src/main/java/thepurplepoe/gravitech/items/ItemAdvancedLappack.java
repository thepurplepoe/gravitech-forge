/*
 * Decompiled with CFR 0_124.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.EnumRarity
 *  net.minecraft.item.ItemStack
 */
package thepurplepoe.gravitech.items;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemAdvancedLappack
extends ItemGeneralLappack {
    public ItemAdvancedLappack() {
        super("advancedLappack", 3000000.0, 30000.0, 3);
    }

    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.UNCOMMON;
    }
}

