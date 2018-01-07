/*
 * Decompiled with CFR 0_124.
 * 
 * Could not load the following classes:
 *  ic2.core.block.state.IIdProvider
 *  ic2.core.ref.IItemModelProvider
 *  ic2.core.ref.IMultiItem
 *  ic2.core.ref.ItemName
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package thepurplepoe.gravitech;

import ic2.core.block.state.IIdProvider;
import ic2.core.ref.IItemModelProvider;
import ic2.core.ref.IMultiItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thepurplepoe.gravitech.items.ItemAdvancedChainsaw;
import thepurplepoe.gravitech.items.ItemAdvancedDrill;
import thepurplepoe.gravitech.items.ItemAdvancedElectricJetpack;
import thepurplepoe.gravitech.items.ItemAdvancedLappack;
import thepurplepoe.gravitech.items.ItemAdvancedNanoChestplate;
import thepurplepoe.gravitech.items.ItemCraftingThings;
import thepurplepoe.gravitech.items.ItemGraviChestplate;
import thepurplepoe.gravitech.items.ItemGraviTool;
import thepurplepoe.gravitech.items.ItemUltimateLappack;
import thepurplepoe.gravitech.items.ItemVajra;

public enum GS_Items {
    ADVANCED_LAPPACK,
    ADVANCED_JETPACK,
    ADVANCED_NANO_CHESTPLATE,
    ULTIMATE_LAPPACK,
    GRAVI_CHESTPLATE,
    ADVANCED_DRILL,
    ADVANCED_CHAINSAW,
    GRAVITOOL,
    VAJRA,
    CRAFTING;
    
    private Item instance;

    private GS_Items() {
    }

    public <T extends Item> T getInstance() {
        return (T)this.instance;
    }

    public <T extends Enum<T>> ItemStack getItemStack(T variant) {
        if (this.instance == null) {
            return null;
        }
        if (this.instance instanceof IMultiItem) {
            IMultiItem multiItem = (IMultiItem)this.instance;
            return multiItem.getItemStack((IIdProvider)variant);
        }
        if (variant == null) {
            return new ItemStack(this.instance);
        }
        throw new IllegalArgumentException("Not applicable");
    }

    public <T extends Item> void setInstance(T instance) {
        if (this.instance != null) {
            throw new IllegalStateException("Duplicate instances!");
        }
        this.instance = instance;
    }

    static void buildItems(Side side) {
        ADVANCED_LAPPACK.setInstance((T)((Object)new ItemAdvancedLappack()));
        ADVANCED_JETPACK.setInstance((T)((Object)new ItemAdvancedElectricJetpack()));
        ADVANCED_NANO_CHESTPLATE.setInstance((T)((Object)new ItemAdvancedNanoChestplate()));
        ULTIMATE_LAPPACK.setInstance((T)((Object)new ItemUltimateLappack()));
        GRAVI_CHESTPLATE.setInstance((T)((Object)new ItemGraviChestplate()));
        ADVANCED_DRILL.setInstance((T)((Object)new ItemAdvancedDrill()));
        ADVANCED_CHAINSAW.setInstance((T)((Object)new ItemAdvancedChainsaw()));
        GRAVITOOL.setInstance((T)((Object)new ItemGraviTool()));
        VAJRA.setInstance((T)((Object)new ItemVajra()));
        CRAFTING.setInstance((T)((Object)new ItemCraftingThings()));
        if (side == Side.CLIENT) {
            GS_Items.doModelGuf();
        }
    }

    @SideOnly(value=Side.CLIENT)
    private static void doModelGuf() {
        for (GS_Items item : GS_Items.values()) {
            ((IItemModelProvider)item.getInstance()).registerModels(null);
        }
    }
}

