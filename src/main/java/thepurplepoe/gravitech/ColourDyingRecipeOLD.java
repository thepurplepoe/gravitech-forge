package thepurplepoe.gravitech;

import java.util.ArrayList;

import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipesArmorDyes;
import net.minecraft.world.World;
import thepurplepoe.gravitech.items.ItemGraviChestplateOLD;

class ColourDyingRecipeOLD
extends RecipesArmorDyes {
    ColourDyingRecipeOLD() {
    }

    public boolean matches(InventoryCrafting craftingInv, World world) {
        ItemStack helmet = null;
        ArrayList<ItemStack> dyes = new ArrayList<ItemStack>(8);
        for (int slot = 0; slot < craftingInv.getSizeInventory(); ++slot) {
            ItemStack stack = craftingInv.getStackInSlot(slot);
            if (stack == null) continue;
            if (stack.getItem() instanceof ItemGraviChestplateOLD) {
                if (helmet != null) {
                    return false;
                }
                helmet = stack;
                continue;
            }
            if (stack.getItem() != Items.DYE) {
                return false;
            }
            dyes.add(stack);
        }
        return helmet != null;
    }

    public ItemStack getCraftingResult(InventoryCrafting craftingInv) {
        ItemStack armourStack = null;
        ItemGraviChestplateOLD chestplate = null;
        int[] newRBG = new int[3];
        int totalColour = 0;
        int numberOfDyes = 0;
        for (int slot = 0; slot < craftingInv.getSizeInventory(); ++slot) {
            ItemStack stack = craftingInv.getStackInSlot(slot);
            if (stack == null) continue;
            if (stack.getItem() instanceof ItemGraviChestplateOLD) {
                chestplate = (ItemGraviChestplateOLD)stack.getItem();
                if (armourStack != null) {
                    return null;
                }
                armourStack = stack.copy();
                armourStack.setCount(1);
                if (!chestplate.hasColor(stack)) continue;
                int oldColour = chestplate.getColor(armourStack);
                float r = (float)(oldColour >> 16 & 255) / 255.0f;
                float g = (float)(oldColour >> 8 & 255) / 255.0f;
                float b = (float)(oldColour & 255) / 255.0f;
                totalColour = (int)((float)totalColour + Math.max(r, Math.max(g, b)) * 255.0f);
                newRBG[0] = (int)((float)newRBG[0] + r * 255.0f);
                newRBG[1] = (int)((float)newRBG[1] + g * 255.0f);
                newRBG[2] = (int)((float)newRBG[2] + b * 255.0f);
                ++numberOfDyes;
                continue;
            }
            if (stack.getItem() != Items.DYE) {
                return null;
            }
            float[] dyeRGB = EntitySheep.getDyeRgb((EnumDyeColor)EnumDyeColor.byDyeDamage((int)stack.getItemDamage()));
            int r = (int)(dyeRGB[0] * 255.0f);
            int g = (int)(dyeRGB[1] * 255.0f);
            int b = (int)(dyeRGB[2] * 255.0f);
            totalColour += Math.max(r, Math.max(g, b));
            int[] arrn = newRBG;
            arrn[0] = arrn[0] + r;
            int[] arrn2 = newRBG;
            arrn2[1] = arrn2[1] + g;
            int[] arrn3 = newRBG;
            arrn3[2] = arrn3[2] + b;
            ++numberOfDyes;
        }
        if (chestplate == null || numberOfDyes == 0) {
            return null;
        }
        if (chestplate.hasColor(armourStack) && numberOfDyes == 1) {
            chestplate.removeColor(armourStack);
        } else {
            int averageRed = newRBG[0] / numberOfDyes;
            int averageGreen = newRBG[1] / numberOfDyes;
            int averageBlue = newRBG[2] / numberOfDyes;
            float gain = (float)totalColour / (float)numberOfDyes;
            float averageMax = Math.max(averageRed, Math.max(averageGreen, averageBlue));
            averageRed = (int)((float)averageRed * gain / averageMax);
            averageGreen = (int)((float)averageGreen * gain / averageMax);
            averageBlue = (int)((float)averageBlue * gain / averageMax);
            int finalColour = (averageRed << 8) + averageGreen;
            finalColour = (finalColour << 8) + averageBlue;
            chestplate.setColor(armourStack, finalColour);
        }
        return armourStack;
    }
}

