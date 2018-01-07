/*
 * Decompiled with CFR 0_124.
 * 
 * Could not load the following classes:
 *  com.google.common.base.CaseFormat
 *  ic2.core.item.armor.ItemArmorElectric
 *  ic2.core.ref.ItemName
 *  net.minecraft.client.renderer.block.model.ModelResourceLocation
 *  net.minecraft.entity.Entity
 *  net.minecraft.inventory.EntityEquipmentSlot
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.client.model.ModelLoader
 *  net.minecraftforge.fml.common.registry.GameRegistry
 *  net.minecraftforge.fml.common.registry.IForgeRegistryEntry
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package thepurplepoe.gravitech.items;

import com.google.common.base.CaseFormat;

import ic2.core.item.armor.ItemArmorElectric;
import ic2.core.ref.ItemName;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class ItemGeneralLappack
extends ItemArmorElectric {
    protected final String name;

    protected ItemGeneralLappack(String name, double maxCharge, double transferLimit, int tier) {
        super(null, null, EntityEquipmentSlot.CHEST, maxCharge, transferLimit, tier);
        this.name = name;
        ((ItemGeneralLappack)GameRegistry.register((IForgeRegistryEntry)this, (ResourceLocation)new ResourceLocation("gravisuite", this.name))).setUnlocalizedName(name);
    }

    @SideOnly(value=Side.CLIENT)
    public void registerModels(ItemName name) {
        ModelLoader.setCustomModelResourceLocation((Item)this, (int)0, (ModelResourceLocation)new ModelResourceLocation("gravisuite:" + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, this.name), null));
    }

    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        return "gravisuite:textures/armour/" + this.name + ".png";
    }

    public String getUnlocalizedName() {
        return "gravisuite." + super.getUnlocalizedName().substring(4);
    }

    public boolean canProvideEnergy(ItemStack stack) {
        return true;
    }

    public double getDamageAbsorptionRatio() {
        return 0.0;
    }

    public int getEnergyPerDamage() {
        return 0;
    }
}

