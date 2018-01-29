package thepurplepoe.gravitech.items;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.init.Localization;
import ic2.core.item.tool.ItemElectricTool;
import ic2.core.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameType;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thepurplepoe.gravitech.GravitechOLD;

public class ItemVajraOLD
extends ItemElectricTool {
    protected static final String NAME = "vajra";
    public String itemName;
    public static boolean accurateEnabled = true;

    public ItemVajraOLD(String name) {
        super(null, 3333, ItemElectricTool.HarvestLevel.Iridium, EnumSet.of(ItemElectricTool.ToolClass.Pickaxe, ItemElectricTool.ToolClass.Shovel, ItemElectricTool.ToolClass.Axe));
        itemName = name;
        this.maxCharge = 10000000;
        this.transferLimit = 60000;
        this.tier = 3;
        this.efficiency = 20000.0f;
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
    }

    @SideOnly(value=Side.CLIENT)
    public void registerModels() {
        ModelLoader.setCustomModelResourceLocation((Item)this, (int)0, (ModelResourceLocation)new ModelResourceLocation("gravitech:vajra", null));
    }

    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    	super.addInformation(stack, worldIn, tooltip, flagIn);
        if (StackUtil.getOrCreateNbtData((ItemStack)stack).getBoolean("accurate")) {
            tooltip.add((Object)TextFormatting.GOLD + Localization.translate((String)"gravitech.vajra.silkTouch", (Object[])new Object[]{new StringBuilder().append((Object)TextFormatting.DARK_GREEN).append(Localization.translate((String)"gravitech.message.on")).toString()}));
        } else {
            tooltip.add((Object)TextFormatting.GOLD + Localization.translate((String)"gravitech.vajra.silkTouch", (Object[])new Object[]{new StringBuilder().append((Object)TextFormatting.DARK_RED).append(Localization.translate((String)"gravitech.message.off")).toString()}));
        }
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (!worldIn.isRemote && IC2.keyboard.isModeSwitchKeyDown(playerIn)) {
        	ItemStack stack = playerIn.getHeldItem(handIn);
            NBTTagCompound nbt = StackUtil.getOrCreateNbtData((ItemStack)stack);
            if (nbt.getBoolean("accurate")) {
                nbt.setBoolean("accurate", false);
                GravitechOLD.messagePlayer(playerIn, "gravitech.vajra.silkTouch", TextFormatting.DARK_RED, Localization.translate((String)"gravitech.message.off"));
            } else if (accurateEnabled) {
                nbt.setBoolean("accurate", true);
                GravitechOLD.messagePlayer(playerIn, "gravitech.vajra.silkTouch", TextFormatting.DARK_GREEN, Localization.translate((String)"gravitech.message.on"));
            } else {
                GravitechOLD.messagePlayer(playerIn, "gravitech.vajra.silkTouchDisabled", TextFormatting.DARK_RED, new Object[0]);
            }
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player) {
        if (accurateEnabled && StackUtil.getOrCreateNbtData((ItemStack)stack).getBoolean("accurate")) {
            World world = player.world;
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
        return "gravitech." + super.getUnlocalizedName().substring(4);
    }

    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }
}

