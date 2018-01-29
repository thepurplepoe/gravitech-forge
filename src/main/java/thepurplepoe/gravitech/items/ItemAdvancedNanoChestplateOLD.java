package thepurplepoe.gravitech.items;

import ic2.api.item.ElectricItem;
import ic2.api.item.IC2Items;
import ic2.core.util.StackUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemAdvancedNanoChestplateOLD
extends ItemAdvancedElectricJetpack {
    protected static final ItemStack WATER_CELL = IC2Items.getItem((String)"fluid_cell", (String)"water");
    protected static final ItemStack EMPTY_CELL = IC2Items.getItem((String)"fluid_cell");
    protected static final byte TICK_RATE = 20;
    protected byte ticker;

    public ItemAdvancedNanoChestplateOLD(String name) {
        super(name);
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
        super.onArmorTick(world, player, stack);
        byte by = this.ticker;
        this.ticker = (byte)(by + 1);
        if (by % 20 == 0 && player.isBurning() && ElectricItem.manager.canUse(stack, 50000.0)) {
            for (int slot = 0; slot < player.inventory.mainInventory.size(); ++slot) {
                ItemStack slotStack = player.inventory.mainInventory.get(slot);
                if (slotStack == null || !StackUtil.checkItemEquality((ItemStack)WATER_CELL, (ItemStack)slotStack.copy()) || !StackUtil.storeInventoryItem((ItemStack)EMPTY_CELL, (EntityPlayer)player, (boolean)false)) continue;
                if (slotStack.getCount() > 1) {
                	slotStack.setCount(slotStack.getCount() - 1);
                } else {
                    player.inventory.mainInventory.set(slot, ItemStack.EMPTY);
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

