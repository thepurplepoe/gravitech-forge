/*
 * Decompiled with CFR 0_124.
 * 
 * Could not load the following classes:
 *  ic2.api.item.ElectricItem
 *  ic2.api.item.IElectricItemManager
 *  ic2.core.IC2
 *  ic2.core.init.Localization
 *  ic2.core.item.tool.ItemElectricTool
 *  ic2.core.item.tool.ItemElectricTool$HarvestLevel
 *  ic2.core.item.tool.ItemElectricTool$ToolClass
 *  ic2.core.ref.ItemName
 *  ic2.core.util.Keyboard
 *  ic2.core.util.StackUtil
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.renderer.block.model.ModelResourceLocation
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Enchantments
 *  net.minecraft.item.EnumRarity
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.network.NetHandlerPlayServer
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.server.SPacketBlockChange
 *  net.minecraft.server.management.PlayerInteractionManager
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.ActionResult
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.EnumActionResult
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.text.TextFormatting
 *  net.minecraft.world.GameType
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraftforge.client.model.ModelLoader
 *  net.minecraftforge.common.ForgeHooks
 *  net.minecraftforge.fml.common.registry.GameRegistry
 *  net.minecraftforge.fml.common.registry.IForgeRegistryEntry
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package thepurplepoe.gravitech.items;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.init.Localization;
import ic2.core.item.tool.ItemElectricTool;
import ic2.core.ref.ItemName;
import ic2.core.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameType;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistryEntry;
import thepurplepoe.gravitech.Gravitech;

public class ItemVajra
extends ItemElectricTool {
    protected static final String NAME = "vajra";
    public static boolean accurateEnabled = true;

    public ItemVajra() {
        super(null, 3333, ItemElectricTool.HarvestLevel.Iridium, EnumSet.of(ItemElectricTool.ToolClass.Pickaxe, ItemElectricTool.ToolClass.Shovel, ItemElectricTool.ToolClass.Axe));
        ((ItemVajra)GameRegistry.register((IForgeRegistryEntry)this, (ResourceLocation)new ResourceLocation("Gravitech", "vajra"))).setUnlocalizedName("vajra");
        this.maxCharge = 10000000;
        this.transferLimit = 60000;
        this.tier = 3;
        this.efficiencyOnProperMaterial = 20000.0f;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerModels(ItemName name) {
        ModelLoader.setCustomModelResourceLocation((Item)this, (int)0, (ModelResourceLocation)new ModelResourceLocation("Gravitech:vajra", null));
    }

    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        if (StackUtil.getOrCreateNbtData((ItemStack)stack).getBoolean("accurate")) {
            tooltip.add((Object)TextFormatting.GOLD + Localization.translate((String)"Gravitech.vajra.silkTouch", (Object[])new Object[]{new StringBuilder().append((Object)TextFormatting.DARK_GREEN).append(Localization.translate((String)"Gravitech.message.on")).toString()}));
        } else {
            tooltip.add((Object)TextFormatting.GOLD + Localization.translate((String)"Gravitech.vajra.silkTouch", (Object[])new Object[]{new StringBuilder().append((Object)TextFormatting.DARK_RED).append(Localization.translate((String)"Gravitech.message.off")).toString()}));
        }
    }

    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
        if (!world.isRemote && IC2.keyboard.isModeSwitchKeyDown(player)) {
            NBTTagCompound nbt = StackUtil.getOrCreateNbtData((ItemStack)stack);
            if (nbt.getBoolean("accurate")) {
                nbt.setBoolean("accurate", false);
                Gravitech.messagePlayer(player, "Gravitech.vajra.silkTouch", TextFormatting.DARK_RED, Localization.translate((String)"Gravitech.message.off"));
            } else if (accurateEnabled) {
                nbt.setBoolean("accurate", true);
                Gravitech.messagePlayer(player, "Gravitech.vajra.silkTouch", TextFormatting.DARK_GREEN, Localization.translate((String)"Gravitech.message.on"));
            } else {
                Gravitech.messagePlayer(player, "Gravitech.vajra.silkTouchDisabled", TextFormatting.DARK_RED, new Object[0]);
            }
            return new ActionResult(EnumActionResult.SUCCESS, (Object)stack);
        }
        return super.onItemRightClick(stack, world, player, hand);
    }

    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player) {
        if (accurateEnabled && StackUtil.getOrCreateNbtData((ItemStack)stack).getBoolean("accurate")) {
            World world = player.worldObj;
            if (!world.isRemote && ElectricItem.manager.canUse(stack, this.operationEnergyCost)) {
                stack.addEnchantment(Enchantments.SILK_TOUCH, 10);
                IBlockState state = world.getBlockState(pos);
                Block block = state.getBlock();
                boolean didHarvest = false;
                if (!block.isAir(state, (IBlockAccess)world, pos) && block.canHarvestBlock((IBlockAccess)world, pos, player)) {
                    int experience;
                    didHarvest = true;
                    if (player instanceof EntityPlayerMP) {
                        experience = ForgeHooks.onBlockBreakEvent((World)world, (GameType)((EntityPlayerMP)player).interactionManager.getGameType(), (EntityPlayerMP)((EntityPlayerMP)player), (BlockPos)pos);
                        if (experience < 0) {
                            didHarvest = false;
                        }
                    } else {
                        experience = 0;
                    }
                    if (didHarvest) {
                        block.onBlockHarvested(world, pos, state, player);
                        if (player.isCreative()) {
                            if (block.removedByPlayer(state, world, pos, player, false)) {
                                block.onBlockDestroyedByPlayer(world, pos, state);
                            }
                        } else {
                            if (block.removedByPlayer(state, world, pos, player, true)) {
                                block.onBlockDestroyedByPlayer(world, pos, state);
                                block.harvestBlock(world, player, pos, state, world.getTileEntity(pos), stack);
                                if (experience > 0) {
                                    block.dropXpOnBlockBreak(world, pos, experience);
                                }
                            }
                            stack.onBlockDestroyed(world, state, pos, player);
                        }
                    }
                    if (didHarvest) {
                        ElectricItem.manager.use(stack, this.operationEnergyCost, (EntityLivingBase)player);
                        world.playEvent(2001, pos, Block.getStateId((IBlockState)state));
                        ((EntityPlayerMP)player).connection.sendPacket((Packet)new SPacketBlockChange(world, pos));
                    }
                }
                Map enchants = EnchantmentHelper.getEnchantments((ItemStack)stack);
                enchants.remove((Object)Enchantments.SILK_TOUCH);
                EnchantmentHelper.setEnchantments((Map)enchants, (ItemStack)stack);
                return didHarvest;
            }
        }
        return super.onBlockStartBreak(stack, pos, player);
    }

    protected ItemStack getStack(IBlockState state) {
        Item item = Item.getItemFromBlock((Block)state.getBlock());
        if (item == null) {
            return null;
        }
        return new ItemStack(item, 1, item.getHasSubtypes() ? state.getBlock().getMetaFromState(state) : 0);
    }

    public boolean canHarvestBlock(IBlockState state, ItemStack stack) {
        return state.getBlock() != Blocks.BEDROCK;
    }

    public boolean hitEntity(ItemStack itemstack, EntityLivingBase target, EntityLivingBase attacker) {
        if (attacker instanceof EntityPlayer) {
            if (ElectricItem.manager.use(itemstack, this.operationEnergyCost * 2.0, attacker)) {
                target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)((EntityPlayer)attacker)), 25.0f);
            } else {
                target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)((EntityPlayer)attacker)), 1.0f);
            }
        }
        return true;
    }

    public String getUnlocalizedName() {
        return "Gravitech." + super.getUnlocalizedName().substring(4);
    }

    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }
}

