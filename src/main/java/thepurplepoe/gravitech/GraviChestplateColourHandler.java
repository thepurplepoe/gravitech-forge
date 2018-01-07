/*
 * Decompiled with CFR 0_124.
 * 
 * Could not load the following classes:
 *  ic2.core.ref.IItemModelProvider
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.color.IItemColor
 *  net.minecraft.client.renderer.color.ItemColors
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package com.chocohead.gravisuite;

import com.chocohead.gravisuite.GS_Items;
import com.chocohead.gravisuite.renders.PrettyUtil;
import ic2.core.ref.IItemModelProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
class GraviChestplateColourHandler
implements IItemColor {
    GraviChestplateColourHandler() {
    }

    static void register() {
        PrettyUtil.mc.getItemColors().registerItemColorHandler((IItemColor)new GraviChestplateColourHandler(), new Item[]{GS_Items.GRAVI_CHESTPLATE.getInstance()});
    }

    public int getColorFromItemstack(ItemStack stack, int tintIndex) {
        return tintIndex > 0 ? -1 : ((ItemArmor)stack.getItem()).getColor(stack);
    }
}

