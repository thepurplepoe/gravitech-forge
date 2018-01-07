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

public class ItemUltimateLappack
extends ItemGeneralLappack {
    public ItemUltimateLappack() {
        super("ultimateLappack", 6.0E7, 100000.0, 4);
    }

    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }
}

