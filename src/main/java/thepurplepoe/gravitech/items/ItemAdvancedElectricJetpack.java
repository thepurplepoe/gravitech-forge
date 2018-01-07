/*
 * Decompiled with CFR 0_124.
 * 
 * Could not load the following classes:
 *  com.google.common.base.CaseFormat
 *  ic2.api.item.ElectricItem
 *  ic2.api.item.IElectricItemManager
 *  ic2.core.IC2
 *  ic2.core.init.Localization
 *  ic2.core.item.armor.ItemArmorElectric
 *  ic2.core.item.armor.jetpack.IBoostingJetpack
 *  ic2.core.ref.ItemName
 *  ic2.core.util.Keyboard
 *  ic2.core.util.StackUtil
 *  net.minecraft.client.renderer.block.model.ModelResourceLocation
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.EntityEquipmentSlot
 *  net.minecraft.item.EnumRarity
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.text.TextFormatting
 *  net.minecraft.world.World
 *  net.minecraftforge.client.model.ModelLoader
 *  net.minecraftforge.fml.common.registry.GameRegistry
 *  net.minecraftforge.fml.common.registry.IForgeRegistryEntry
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package thepurplepoe.gravitech.items;

import com.google.common.base.CaseFormat;

import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.init.Localization;
import ic2.core.item.armor.ItemArmorElectric;
import ic2.core.item.armor.jetpack.IBoostingJetpack;
import ic2.core.ref.ItemName;
import ic2.core.util.StackUtil;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistryEntry;
import thepurplepoe.gravitech.GraviKeys;
import thepurplepoe.gravitech.Gravitech;

public class ItemAdvancedElectricJetpack
extends ItemArmorElectric
implements IBoostingJetpack {
    protected final String name;

    public ItemAdvancedElectricJetpack() {
        this("advancedJetpack");
    }

    protected ItemAdvancedElectricJetpack(String name) {
        this(name, 3000000.0, 30000.0, 3);
    }

    protected ItemAdvancedElectricJetpack(String name, double maxCharge, double transferLimit, int tier) {
        super(null, null, EntityEquipmentSlot.CHEST, maxCharge, transferLimit, tier);
        this.name = name;
        ((ItemAdvancedElectricJetpack)GameRegistry.register((IForgeRegistryEntry)this, (ResourceLocation)new ResourceLocation("Gravitech", this.name))).setUnlocalizedName(name);
        this.setMaxDamage(27);
        this.setMaxStackSize(1);
        this.setNoRepair();
    }

    @SideOnly(value=Side.CLIENT)
    public void registerModels(ItemName name) {
        ModelLoader.setCustomModelResourceLocation((Item)this, (int)0, (ModelResourceLocation)new ModelResourceLocation("Gravitech:" + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, this.name), null));
    }

    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        return "Gravitech:textures/armour/" + this.name + ".png";
    }

    public String getUnlocalizedName() {
        return "Gravitech." + super.getUnlocalizedName().substring(4);
    }

    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.UNCOMMON;
    }

    public static boolean isJetpackOn(ItemStack stack) {
        return StackUtil.getOrCreateNbtData((ItemStack)stack).getBoolean("isFlyActive");
    }

    public static boolean isHovering(ItemStack stack) {
        return StackUtil.getOrCreateNbtData((ItemStack)stack).getBoolean("hoverMode");
    }

    public static boolean switchJetpack(ItemStack stack) {
        NBTTagCompound nbt = StackUtil.getOrCreateNbtData((ItemStack)stack);
        boolean newMode = !nbt.getBoolean("isFlyActive");
        nbt.setBoolean("isFlyActive", newMode);
        return newMode;
    }

    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
        NBTTagCompound nbt = StackUtil.getOrCreateNbtData((ItemStack)stack);
        byte toggleTimer = nbt.getByte("toggleTimer");
        if (GraviKeys.isFlyKeyDown(player) && toggleTimer == 0) {
            toggleTimer = 10;
            nbt.setByte("toggleTimer", (byte)10);
            if (!world.isRemote) {
                String mode = ItemAdvancedElectricJetpack.switchJetpack(stack) ? (Object)TextFormatting.DARK_GREEN + Localization.translate((String)"Gravitech.message.on") : (Object)TextFormatting.DARK_RED + Localization.translate((String)"Gravitech.message.off");
                Gravitech.messagePlayer(player, "Gravitech.message.jetpackSwitch", TextFormatting.YELLOW, mode);
            }
        }
        if (toggleTimer > 0 && !ItemAdvancedElectricJetpack.isJetpackOn(stack)) {
            toggleTimer = (byte)(toggleTimer - 1);
            nbt.setByte("toggleTimer", toggleTimer);
        }
    }

    public boolean isJetpackActive(ItemStack stack) {
        return ItemAdvancedElectricJetpack.isJetpackOn(stack);
    }

    public double getChargeLevel(ItemStack stack) {
        return ElectricItem.manager.getCharge(stack) / this.getMaxCharge(stack);
    }

    public float getPower(ItemStack stack) {
        return 1.0f;
    }

    public float getDropPercentage(ItemStack stack) {
        return 0.05f;
    }

    public float getBaseThrust(ItemStack stack, boolean hover) {
        return hover ? 0.65f : 0.3f;
    }

    public float getBoostThrust(EntityPlayer player, ItemStack stack, boolean hover) {
        return IC2.keyboard.isBoostKeyDown(player) && ElectricItem.manager.getCharge(stack) >= 60.0 ? (hover ? 0.07f : 0.09f) : 0.0f;
    }

    public boolean useBoostPower(ItemStack stack, float boostAmount) {
        return ElectricItem.manager.discharge(stack, 60.0, Integer.MAX_VALUE, true, false, false) > 0.0;
    }

    public float getWorldHeightDivisor(ItemStack stack) {
        return 1.0f;
    }

    public float getHoverMultiplier(ItemStack stack, boolean upwards) {
        return 0.2f;
    }

    public float getHoverBoost(EntityPlayer player, ItemStack stack, boolean up) {
        if (IC2.keyboard.isBoostKeyDown(player) && ElectricItem.manager.getCharge(stack) >= 60.0) {
            if (!player.onGround) {
                ElectricItem.manager.discharge(stack, 60.0, Integer.MAX_VALUE, true, false, false);
            }
            return 2.0f;
        }
        return 1.0f;
    }

    public boolean drainEnergy(ItemStack pack, int amount) {
        return ElectricItem.manager.discharge(pack, (double)(amount * 6), Integer.MAX_VALUE, true, false, false) > 0.0;
    }

    public boolean canProvideEnergy(ItemStack stack) {
        return true;
    }

    public int getEnergyPerDamage() {
        return 0;
    }

    public double getDamageAbsorptionRatio() {
        return 0.0;
    }
}

