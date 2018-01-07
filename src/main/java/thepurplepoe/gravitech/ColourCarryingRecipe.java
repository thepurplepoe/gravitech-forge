/*
 * Decompiled with CFR 0_124.
 * 
 * Could not load the following classes:
 *  ic2.core.item.armor.ItemArmorQuantumSuit
 *  ic2.core.recipe.AdvRecipe
 *  net.minecraft.inventory.InventoryCrafting
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.crafting.IRecipe
 *  net.minecraftforge.fml.common.registry.GameRegistry
 */
package thepurplepoe.gravitech;

import ic2.core.item.armor.ItemArmorQuantumSuit;
import ic2.core.recipe.AdvRecipe;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.GameRegistry;

class ColourCarryingRecipe
extends AdvRecipe {
    public ColourCarryingRecipe(ItemStack result, Object[] args) {
        super(result, args);
    }

    public void add() {
        GameRegistry.addRecipe((IRecipe)this);
    }

    public ItemStack getCraftingResult(InventoryCrafting craftingInv) {
        ItemStack initialResult = super.getCraftingResult(craftingInv);
        if (initialResult != null && initialResult.getItem() instanceof ItemArmor) {
            int colour = -1;
            for (int slot = 0; slot < craftingInv.getSizeInventory(); ++slot) {
                ItemStack offer = craftingInv.getStackInSlot(slot);
                if (offer == null || !(offer.getItem() instanceof ItemArmorQuantumSuit)) continue;
                colour = ((ItemArmorQuantumSuit)offer.getItem()).getColor(offer);
                break;
            }
            if (colour != -1) {
                ((ItemArmor)initialResult.getItem()).setColor(initialResult, colour);
            }
        }
        return initialResult;
    }
}

