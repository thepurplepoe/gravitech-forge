package thepurplepoe.gravitech.items;

import java.util.EnumSet;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.CaseFormat;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.init.Localization;
import ic2.core.item.tool.ItemElectricTool;
import ic2.core.ref.ItemName;
import ic2.core.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistryEntry;
import thepurplepoe.gravitech.Gravitech;

public class ItemAdvancedChainsaw
extends ItemElectricTool {
    protected static final String NAME = "advancedChainsaw";

    public ItemAdvancedChainsaw() {
        super(null, 100, ItemElectricTool.HarvestLevel.Iron, EnumSet.of(ItemElectricTool.ToolClass.Axe, ItemElectricTool.ToolClass.Sword, ItemElectricTool.ToolClass.Shears));
        ((ItemAdvancedChainsaw)GameRegistry.register((IForgeRegistryEntry)this, (ResourceLocation)new ResourceLocation("Gravitech", "advancedChainsaw"))).setUnlocalizedName("advancedChainsaw");
        this.maxCharge = 45000;
        this.transferLimit = 500;
        this.tier = 2;
        this.efficiency = 30.0f;
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    @SideOnly(value=Side.CLIENT)
    public void registerModels(ItemName name) {
        ModelLoader.setCustomModelResourceLocation((Item)this, (int)0, (ModelResourceLocation)new ModelResourceLocation("Gravitech:" + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "advancedChainsaw"), null));
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (!worldIn.isRemote && IC2.keyboard.isModeSwitchKeyDown(playerIn)) {
        	ItemStack stack = playerIn.getHeldItem(handIn);
            NBTTagCompound nbt = StackUtil.getOrCreateNbtData((ItemStack)stack);
            if (nbt.getBoolean("disableShear")) {
                nbt.setBoolean("disableShear", false);
                Gravitech.messagePlayer(playerIn, "Gravitech.advancedChainsaw.shear", TextFormatting.DARK_GREEN, Localization.translate((String)"Gravitech.message.on"));
            } else {
                nbt.setBoolean("disableShear", true);
                Gravitech.messagePlayer(playerIn, "Gravitech.advancedChainsaw.shear", TextFormatting.DARK_RED, Localization.translate((String)"Gravitech.message.off"));
            }
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        ElectricItem.manager.use(stack, this.operationEnergyCost, attacker);
        if (attacker instanceof EntityPlayer && target instanceof EntityCreeper && target.getHealth() <= 0.0f) {
            IC2.achievements.issueAchievement((EntityPlayer)attacker, "killCreeperChainsaw");
        }
        return true;
    }

    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        BlockPos pos;
        IShearable target;
        EntityPlayer player = event.getEntityPlayer();
        if (player.world.isRemote) {
            return;
        }
        Entity entity = event.getTarget();
        ItemStack stack = player.inventory.getStackInSlot(player.inventory.currentItem);
        if (stack != null && stack.getItem() == this && entity instanceof IShearable && !StackUtil.getOrCreateNbtData((ItemStack)stack).getBoolean("disableShear") && ElectricItem.manager.use(stack, this.operationEnergyCost, (EntityLivingBase)player) && (target = (IShearable)entity).isShearable(stack, (IBlockAccess)entity.world, pos = new BlockPos(entity))) {
            List<ItemStack> drops = target.onSheared(stack, (IBlockAccess)entity.world, pos, EnchantmentHelper.getEnchantmentLevel((Enchantment)Enchantments.FORTUNE, (ItemStack)stack));
            for (ItemStack drop : drops) {
                EntityItem item = entity.entityDropItem(drop, 1.0f);
                item.motionY += (double)(itemRand.nextFloat() * 0.05f);
                item.motionX += (double)((itemRand.nextFloat() - itemRand.nextFloat()) * 0.1f);
                item.motionZ += (double)((itemRand.nextFloat() - itemRand.nextFloat()) * 0.1f);
            }
        }
    }

    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player) {
        IShearable target;
        World world = player.world;
        if (world.isRemote) {
            return false;
        }
        if (StackUtil.getOrCreateNbtData((ItemStack)stack).getBoolean("disableShear")) {
            return false;
        }
        Block block = world.getBlockState(pos).getBlock();
        if (block instanceof IShearable && (target = (IShearable)block).isShearable(stack, (IBlockAccess)world, pos) && ElectricItem.manager.use(stack, this.operationEnergyCost, (EntityLivingBase)player)) {
            List<ItemStack> drops = target.onSheared(stack, (IBlockAccess)world, pos, EnchantmentHelper.getEnchantmentLevel((Enchantment)Enchantments.FORTUNE, (ItemStack)stack));
            for (ItemStack drop : drops) {
                StackUtil.dropAsEntity((World)world, (BlockPos)pos, (ItemStack)drop);
            }
            player.addStat(StatList.getBlockStats((Block)block), 1);
        }
        return false;
    }

    public String getUnlocalizedName() {
        return "Gravitech." + super.getUnlocalizedName().substring(4);
    }

    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.UNCOMMON;
    }

    @SideOnly(value=Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (StackUtil.getOrCreateNbtData((ItemStack)stack).getBoolean("disableShear")) {
            tooltip.add((Object)TextFormatting.DARK_RED + Localization.translate((String)"Gravitech.advancedChainsaw.shear", (Object[])new Object[]{Localization.translate((String)"Gravitech.message.off")}));
        } else {
            tooltip.add((Object)TextFormatting.DARK_GREEN + Localization.translate((String)"Gravitech.advancedChainsaw.shear", (Object[])new Object[]{Localization.translate((String)"Gravitech.message.on")}));
        }
    }

    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        if (slot != EntityEquipmentSlot.MAINHAND) {
            return super.getAttributeModifiers(slot, stack);
        }
        HashMultimap<String, AttributeModifier> ret = HashMultimap.create();
        if (ElectricItem.manager.canUse(stack, this.operationEnergyCost)) {
            ret.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", (double)this.attackSpeed, 0));
            ret.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(Item.ATTACK_DAMAGE_MODIFIER, "Tool modifier", 13.0, 0));
        }
        return ret;
    }

    protected String getIdleSound(EntityLivingBase player, ItemStack stack) {
        return "Tools/Chainsaw/ChainsawIdle.ogg";
    }

    protected String getStopSound(EntityLivingBase player, ItemStack stack) {
        return "Tools/Chainsaw/ChainsawStop.ogg";
    }
}

