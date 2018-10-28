package thepurplepoe.gravitech.items;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.CaseFormat;

import ic2.core.IC2;
import ic2.core.init.Localization;
import ic2.core.item.tool.ItemDrill;
import ic2.core.item.tool.ItemElectricTool;
import ic2.core.ref.ItemName;
import ic2.core.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameType;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thepurplepoe.gravitech.Gravitech;

public class ItemAdvancedDrill
extends ItemDrill {
	public String itemName;
    protected static final Material[] MATERIALS = new Material[]{Material.ROCK, Material.GRASS, Material.GROUND, Material.SAND, Material.CLAY};

    public ItemAdvancedDrill(String name) {
        super(null, 160, ItemElectricTool.HarvestLevel.Iridium, 45000, 500, 2, DrillMode.NORMAL.drillSpeed);
        itemName = name;
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
    }

    @SideOnly(value=Side.CLIENT)
    public void registerModels() {
        ModelLoader.setCustomModelResourceLocation((Item)this, (int)0, (ModelResourceLocation)new ModelResourceLocation("gravitech:" + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "advancedDrill"), null));
    }

    public static DrillMode readDrillMode(ItemStack stack) {
        return DrillMode.getFromID(StackUtil.getOrCreateNbtData((ItemStack)stack).getInteger("toolMode"));
    }

    public static DrillMode readNextDrillMode(ItemStack stack) {
        return DrillMode.getFromID(StackUtil.getOrCreateNbtData((ItemStack)stack).getInteger("toolMode") + 1);
    }

    public static void saveDrillMode(ItemStack stack, DrillMode mode) {
        StackUtil.getOrCreateNbtData((ItemStack)stack).setInteger("toolMode", mode.ordinal());
    }

    public String getUnlocalizedName() {
        return "gravitech." + super.getUnlocalizedName().substring(4);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (IC2.keyboard.isModeSwitchKeyDown(playerIn)) {
            if (!worldIn.isRemote) {
            	ItemStack stack = playerIn.getHeldItem(handIn);
                DrillMode mode = ItemAdvancedDrill.readNextDrillMode(stack);
                ItemAdvancedDrill.saveDrillMode(stack, mode);
                Gravitech.messagePlayer(playerIn, "gravitech.advancedDrill.mode", mode.colour, mode.translationName);
                this.efficiency = mode.drillSpeed;
                this.operationEnergyCost = mode.energyCost;
            }
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public static Collection<BlockPos> getBrokenBlocks(EntityPlayer player, RayTraceResult ray) {
        return ItemAdvancedDrill.getBrokenBlocks(player, ray.getBlockPos(), ray.sideHit);
    }

    protected static Collection<BlockPos> getBrokenBlocks(EntityPlayer player, BlockPos pos, EnumFacing side) {
        assert (side != null);
        int xMove = 1;
        int yMove = 1;
        int zMove = 1;
        switch (side.getAxis()) {
            case X: {
                xMove = 0;
                break;
            }
            case Y: {
                yMove = 0;
                break;
            }
            case Z: {
                zMove = 0;
            }
        }
        World world = player.world;
        ArrayList<BlockPos> list = new ArrayList<BlockPos>(9);
        for (int x = pos.getX() - xMove; x <= pos.getX() + xMove; ++x) {
            for (int y = pos.getY() - yMove; y <= pos.getY() + yMove; ++y) {
                for (int z = pos.getZ() - zMove; z <= pos.getZ() + zMove; ++z) {
                    BlockPos potential = new BlockPos(x, y, z);
                    if (!ItemAdvancedDrill.canBlockBeMined(world, potential, player, false)) continue;
                    list.add(potential);
                }
            }
        }
        return list;
    }

    protected static boolean canBlockBeMined(World world, BlockPos pos, EntityPlayer player, boolean skipEffectivity) {
        IBlockState state = world.getBlockState(pos);
        return state.getBlock().canHarvestBlock((IBlockAccess)world, pos, player) && (skipEffectivity || ItemAdvancedDrill.isEffective(state.getMaterial())) && state.getPlayerRelativeBlockHardness(player, world, pos) != 0.0f;
    }

    protected static boolean isEffective(Material material) {
        for (Material option : MATERIALS) {
            if (material != option) continue;
            return true;
        }
        return false;
    }

    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player) {
        if (ItemAdvancedDrill.readDrillMode(stack) == DrillMode.BIG_HOLES) {
            World world = player.world;
            if (!world.isRemote) {
                Collection<BlockPos> blocks = ItemAdvancedDrill.getBrokenBlocks(player, this.rayTrace(world, player, true));
                if (!blocks.contains((Object)pos) && ItemAdvancedDrill.canBlockBeMined(world, pos, player, true)) {
                    blocks.add(pos);
                }
                boolean powerRanOut = false;
                for (BlockPos blockPos : blocks) {
                    Block block;
                    IBlockState state;
                    int experience;
                    if (!ItemGraviTool.hasNecessaryPower(stack, this.operationEnergyCost, player)) {
                        powerRanOut = true;
                        break;
                    }
                    if (!world.isBlockLoaded(blockPos) || (block = (state = world.getBlockState(blockPos)).getBlock()).isAir(state, (IBlockAccess)world, blockPos)) continue;
                    if (player instanceof EntityPlayerMP) {
                        experience = ForgeHooks.onBlockBreakEvent((World)world, (GameType)((EntityPlayerMP)player).interactionManager.getGameType(), (EntityPlayerMP)((EntityPlayerMP)player), (BlockPos)blockPos);
                        if (experience < 0) {
                            return false;
                        }
                    } else {
                        experience = 0;
                    }
                    block.onBlockHarvested(world, blockPos, state, player);
                    if (player.isCreative()) {
                        if (block.removedByPlayer(state, world, blockPos, player, false)) {
                            block.onBlockDestroyedByPlayer(world, blockPos, state);
                        }
                    } else {
                        if (block.removedByPlayer(state, world, blockPos, player, true)) {
                            block.onBlockDestroyedByPlayer(world, blockPos, state);
                            block.harvestBlock(world, player, blockPos, state, world.getTileEntity(blockPos), stack);
                            if (experience > 0) {
                                block.dropXpOnBlockBreak(world, blockPos, experience);
                            }
                        }
                        stack.onBlockDestroyed(world, state, blockPos, player);
                    }
                    world.playEvent(2001, blockPos, Block.getStateId((IBlockState)state));
                    ((EntityPlayerMP)player).connection.sendPacket((Packet)new SPacketBlockChange(world, blockPos));
                }
                if (powerRanOut) {
                    IC2.platform.messagePlayer(player, "gravitech.advancedDrill.ranOut", new Object[0]);
                }
                return true;
            }
        }
        return super.onBlockStartBreak(stack, pos, player);
    }

    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.UNCOMMON;
    }

    @SideOnly(value=Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    	super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add((Object)TextFormatting.GOLD + Localization.translate((String)"gravitech.advancedDrill.mode", (Object[])new Object[]{new StringBuilder().append((Object)TextFormatting.WHITE).append(Localization.translate((String)ItemAdvancedDrill.readDrillMode((ItemStack)stack).translationName)).toString()}));
    }

    public static enum DrillMode {
        NORMAL(TextFormatting.DARK_GREEN, 35.0f, 160.0),
        LOW_POWER(TextFormatting.GOLD, 16.0f, 80.0),
        FINE(TextFormatting.AQUA, 10.0f, 50.0),
        BIG_HOLES(TextFormatting.LIGHT_PURPLE, 16.0f, 160.0);
        
        public final String translationName;
        public final TextFormatting colour;
        public final double energyCost;
        public final float drillSpeed;
        private static final DrillMode[] VALUES;

        private DrillMode(TextFormatting colour, float speed, double energyCost) {
            this.translationName = "gravitech.advancedDrill." + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, this.name());
            this.energyCost = energyCost;
            this.drillSpeed = speed;
            this.colour = colour;
        }

        public static DrillMode getFromID(int ID) {
            return VALUES[ID % VALUES.length];
        }

        static {
            VALUES = DrillMode.values();
        }
    }

}

