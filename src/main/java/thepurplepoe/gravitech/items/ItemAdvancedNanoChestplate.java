/*
 * Decompiled with CFR 0_124.
 * 
 * Could not load the following classes:
 *  ic2.api.item.ElectricItem
 *  ic2.api.item.IC2Items
 *  ic2.api.item.IElectricItemManager
 *  ic2.core.util.StackUtil
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.World
 */
package thepurplepoe.gravitech.items;

import ic2.api.item.ElectricItem;
import ic2.api.item.IC2Items;
import ic2.core.util.StackUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemAdvancedNanoChestplate
extends ItemAdvancedElectricJetpack {
    protected static final ItemStack WATER_CELL = IC2Items.getItem((String)"fluid_cell", (String)"water");
    protected static final ItemStack EMPTY_CELL = IC2Items.getItem((String)"fluid_cell");
    protected static final byte TICK_RATE = 20;
    protected byte ticker;

    public ItemAdvancedNanoChestplate() {
        super("advancedNanoChestplate");
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
        super.onArmorTick(world, player, stack);
        byte by = this.ticker;
        this.ticker = (byte)(by + 1);
        if (by % 20 == 0 && player.isBurning() && ElectricItem.manager.canUse(stack, 50000.0)) {
            for (int slot = 0; slot < player.inventory.mainInventory.length; ++slot) {
                ItemStack slotStack = player.inventory.mainInventory[slot];
                if (slotStack == null || !StackUtil.checkItemEquality((ItemStack)WATER_CELL, (ItemStack)slotStack.copy()) || !StackUtil.storeInventoryItem((ItemStack)EMPTY_CELL, (EntityPlayer)player, (boolean)false)) continue;
                if (slotStack.stackSize > 1) {
                    --slotStack.stackSize;
                } else {
                    player.inventory.mainInventory[slot] = null;
                }
                ElectricItem.manager.discharge(stack, 50000.0, Integer.MAX_VALUE, true, false, false);
                player.extinguish();
                break;
            }
        }
    }

    @Override
    public int getEnergyPerDamage() {
        return 800;
    }

    @Override
    public double getDamageAbsorptionRatio() {
        return 0.9;
    }
}

