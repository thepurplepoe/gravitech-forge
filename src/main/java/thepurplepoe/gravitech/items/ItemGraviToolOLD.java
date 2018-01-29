package thepurplepoe.gravitech.items;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.tile.IWrenchable;
import ic2.core.IC2;
import ic2.core.audio.PositionSpec;
import ic2.core.block.TileEntityBarrel;
import ic2.core.init.Localization;
import ic2.core.init.MainConfig;
import ic2.core.item.ElectricItemManager;
import ic2.core.item.tool.ItemTreetap;
import ic2.core.ref.BlockName;
import ic2.core.util.Config;
import ic2.core.util.ConfigUtil;
import ic2.core.util.LogCategory;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameType;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thepurplepoe.gravitech.GravitechOLD;

public class ItemGraviToolOLD
extends ItemTool
implements IElectricItem {
    protected static final String NAME = "graviTool";
    public String itemName;
    protected static final double ROTATE = 50.0;
    protected static final double HOE = 50.0;
    protected static final double TAP = 50.0;
    protected static final double SCREW = 500.0;

    public ItemGraviToolOLD(String name) {
        super(Item.ToolMaterial.IRON, Collections.emptySet());
    	itemName = name;
        this.setMaxDamage(27);
        this.setCreativeTab((CreativeTabs)IC2.tabIC2);
        this.efficiency = 16.0f;
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
    }

    @SideOnly(value=Side.CLIENT)
    public void registerModels() {
        ModelLoader.setCustomMeshDefinition((Item)this, (ItemMeshDefinition)new ItemMeshDefinition(){

            public ModelResourceLocation getModelLocation(ItemStack stack) {
                GraviToolMode mode = ItemGraviToolOLD.hasToolMode(stack) ? ItemGraviToolOLD.readToolMode(stack) : GraviToolMode.HOE;
                return mode.model;
            }
        });
        for (GraviToolMode mode : GraviToolMode.VALUES) {
            ModelBakery.registerItemVariants((Item)this, (ResourceLocation[])new ResourceLocation[]{mode.model});
        }
    }

    @SideOnly(value=Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }

    public static boolean hasToolMode(ItemStack stack) {
        return stack.hasTagCompound() && stack.getTagCompound().hasKey("toolMode", 3);
    }

    public static GraviToolMode readToolMode(ItemStack stack) {
        return GraviToolMode.getFromID(StackUtil.getOrCreateNbtData((ItemStack)stack).getInteger("toolMode"));
    }

    public static GraviToolMode readNextToolMode(ItemStack stack) {
        return GraviToolMode.getFromID(StackUtil.getOrCreateNbtData((ItemStack)stack).getInteger("toolMode") + 1);
    }

    public static void saveToolMode(ItemStack stack, GraviToolMode mode) {
        StackUtil.getOrCreateNbtData((ItemStack)stack).setInteger("toolMode", mode.ordinal());
    }

    public String getUnlocalizedName() {
        return "gravitech." + super.getUnlocalizedName().substring(5);
    }

    public String getUnlocalizedName(ItemStack stack) {
        return this.getUnlocalizedName();
    }

    public String getItemStackDisplayName(ItemStack stack) {
        if (ItemGraviToolOLD.hasToolMode(stack)) {
            return Localization.translate((String)"gravitech.graviTool.set", (Object[])new Object[]{Localization.translate((String)this.getUnlocalizedName(stack)), Localization.translate((String)ItemGraviToolOLD.readToolMode((ItemStack)stack).translationName)});
        }
        return Localization.translate((String)this.getUnlocalizedName(stack));
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (IC2.keyboard.isModeSwitchKeyDown(playerIn)) {
            if (worldIn.isRemote) {
                IC2.audioManager.playOnce((Object)playerIn, PositionSpec.Hand, "gravitech:toolChange.ogg", true, IC2.audioManager.getDefaultVolume());
            } else {
            	ItemStack stack = playerIn.getHeldItem(handIn);
                GraviToolMode mode = ItemGraviToolOLD.readNextToolMode(stack);
                ItemGraviToolOLD.saveToolMode(stack, mode);
                GravitechOLD.messagePlayer(playerIn, "gravitech.graviTool.changeTool", mode.colour, mode.translationName);
            }
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
    	ItemStack stack = player.getHeldItem(hand);
        switch (ItemGraviToolOLD.readToolMode(stack)) {
            case WRENCH: {
                return this.onWrenchUse(stack, player, world, pos, side) ? (world.isRemote ? EnumActionResult.PASS : EnumActionResult.SUCCESS) : EnumActionResult.FAIL;
            }
            case SCREWDRIVER: {
                return this.onScrewdriverUse(stack, player, world, pos) ? (world.isRemote ? EnumActionResult.PASS : EnumActionResult.SUCCESS) : EnumActionResult.FAIL;
            }
		default:
			break;
        }
        return super.onItemUse(player, world, pos, hand, side, hitX, hitY, hitZ);
    }

    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    	ItemStack stack = player.getHeldItem(hand);
        switch (ItemGraviToolOLD.readToolMode(stack)) {
            case HOE: {
                return this.onHoeUse(stack, player, worldIn, pos, facing) ? (worldIn.isRemote ? EnumActionResult.PASS : EnumActionResult.SUCCESS) : EnumActionResult.FAIL;
            }
            case TREETAP: {
                return this.onTreeTapUse(stack, player, worldIn, pos, facing) ? (worldIn.isRemote ? EnumActionResult.PASS : EnumActionResult.SUCCESS) : EnumActionResult.FAIL;
            }
		default:
			break;
        }
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    
    protected boolean onHoeUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side) {
        if (!player.canPlayerEdit(pos.offset(side), side, stack) || !ItemGraviToolOLD.hasNecessaryPower(stack, HOE, player)) {
            return false;
        }
        UseHoeEvent event = new UseHoeEvent(player, stack, world, pos);
        if (MinecraftForge.EVENT_BUS.post((Event)event)) {
            return false;
        }
        if (event.getResult() == Event.Result.ALLOW) {
            return ItemGraviToolOLD.checkNecessaryPower(stack, HOE, player, true);
        }
        IBlockState state = Util.getBlockState((IBlockAccess)world, (BlockPos)pos);
        Block block = state.getBlock();
        if (side != EnumFacing.DOWN && world.isAirBlock(pos.up())) {
            if (block == Blocks.GRASS || block == Blocks.GRASS_PATH) {
                return this.setHoedBlock(stack, player, world, pos, Blocks.FARMLAND.getDefaultState());
            }
            if (block == Blocks.DIRT) {
                switch ((BlockDirt.DirtType)state.getValue((IProperty)BlockDirt.VARIANT)) {
                    case DIRT: {
                        return this.setHoedBlock(stack, player, world, pos, Blocks.FARMLAND.getDefaultState());
                    }
                    case COARSE_DIRT: {
                        return this.setHoedBlock(stack, player, world, pos, Blocks.DIRT.getDefaultState().withProperty((IProperty)BlockDirt.VARIANT, (Comparable)BlockDirt.DirtType.DIRT));
                    }
                }
            }
        }
        return false;
    }
    

    protected boolean setHoedBlock(ItemStack stack, EntityPlayer player, World world, BlockPos pos, IBlockState state) {
        if (ItemGraviToolOLD.checkNecessaryPower(stack, HOE, player, true)) {
            world.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
            if (!world.isRemote) {
                world.setBlockState(pos, state, 11);
            }
            return true;
        }
        return false;
    }

    protected boolean onTreeTapUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side) {
        TileEntity te;
        IBlockState state = Util.getBlockState((IBlockAccess)world, (BlockPos)pos);
        if (side.getAxis() != EnumFacing.Axis.Y && (te = world.getTileEntity(pos)) instanceof TileEntityBarrel) {
            TileEntityBarrel barrel = (TileEntityBarrel)te;
            if (!barrel.getActive()) {
                if (ItemGraviToolOLD.checkNecessaryPower(stack, TAP, player, true)) {
                    if (!world.isRemote) {
                        barrel.setActive(true);
                        barrel.onPlaced(stack, null, side.getOpposite());
                    }
                    return true;
                }
                return false;
            }
            return false;
        }
        if (state.getBlock() == BlockName.rubber_wood.getInstance() && ItemGraviToolOLD.hasNecessaryPower(stack, TAP, player)) {
            return ItemTreetap.attemptExtract((EntityPlayer)player, (World)world, (BlockPos)pos, (EnumFacing)side, (IBlockState)state, (List)null) && ItemGraviToolOLD.checkNecessaryPower(stack, TAP, player);
        }
        return false;
    }

    protected boolean onWrenchUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side) {
        IBlockState state = Util.getBlockState((IBlockAccess)world, (BlockPos)pos);
        Block block = state.getBlock();
        if (block.isAir(state, (IBlockAccess)world, pos)) {
            return false;
        }
        if (block instanceof IWrenchable) {
            EnumFacing current;
            IWrenchable wrenchable = (IWrenchable)block;
            EnumFacing newFacing = current = wrenchable.getFacing(world, pos);
            if (IC2.keyboard.isAltKeyDown(player)) {
                EnumFacing.Axis axis = side.getAxis();
                newFacing = !player.isSneaking() && side.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE || player.isSneaking() && side.getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE ? newFacing.rotateAround(axis) : newFacing.rotateAround(axis).rotateAround(axis).rotateAround(axis);
            } else {
                newFacing = player.isSneaking() ? side.getOpposite() : side;
            }
            if (current != newFacing) {
                if (ItemGraviToolOLD.hasNecessaryPower(stack, ROTATE, player)) {
                    if (wrenchable.setFacing(world, pos, newFacing, player)) {
                        return ItemGraviToolOLD.checkNecessaryPower(stack, ROTATE, player);
                    }
                } else {
                    return false;
                }
            }
            if (wrenchable.wrenchCanRemove(world, pos, player)) {
                if (!ItemGraviToolOLD.hasNecessaryPower(stack, ROTATE, player)) {
                    return false;
                }
                if (!world.isRemote) {
                    int experience;
                    TileEntity te = world.getTileEntity(pos);
                    if (ConfigUtil.getBool((Config)MainConfig.get(), (String)"protection/wrenchLogging")) {
                        Object[] arrobject = new Object[4];
                        arrobject[0] = player.getGameProfile().getName() + "/" + player.getGameProfile().getId();
                        arrobject[1] = state;
                        arrobject[2] = te != null ? te.getClass().getSimpleName().replace("TileEntity", "") : "no te";
                        arrobject[3] = Util.formatPosition((IBlockAccess)world, (BlockPos)pos);
                        IC2.log.info(LogCategory.PlayerActivity, "Player %s used a wrench to remove the %s (%s) at %s.", arrobject);
                    }
                    if (player instanceof EntityPlayerMP) {
                        experience = ForgeHooks.onBlockBreakEvent((World)world, (GameType)((EntityPlayerMP)player).interactionManager.getGameType(), (EntityPlayerMP)((EntityPlayerMP)player), (BlockPos)pos);
                        if (experience < 0) {
                            return false;
                        }
                    } else {
                        experience = 0;
                    }
                    block.onBlockHarvested(world, pos, state, player);
                    if (!block.removedByPlayer(state, world, pos, player, true)) {
                        return false;
                    }
                    block.onBlockDestroyedByPlayer(world, pos, state);
                    List<ItemStack> drops = wrenchable.getWrenchDrops(world, pos, state, te, player, 0);
                    for (ItemStack drop : drops) {
                        StackUtil.dropAsEntity((World)world, (BlockPos)pos, (ItemStack)drop);
                    }
                    if (!player.capabilities.isCreativeMode && experience > 0) {
                        block.dropXpOnBlockBreak(world, pos, experience);
                    }
                }
                return ItemGraviToolOLD.checkNecessaryPower(stack, ROTATE, player);
            }
        }
        return false;
    }

    protected boolean onScrewdriverUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos) {
        IBlockState state = Util.getBlockState((IBlockAccess)world, (BlockPos)pos);
        Block block = state.getBlock();
        if (!block.isAir(state, (IBlockAccess)world, pos) && block instanceof BlockHorizontal && ItemGraviToolOLD.checkNecessaryPower(stack, 500.0, player)) {
            EnumFacing facing = (EnumFacing)state.getValue((IProperty)BlockHorizontal.FACING);
            facing = player.isSneaking() ? facing.rotateYCCW() : facing.rotateY();
            world.setBlockState(pos, state.withProperty((IProperty)BlockHorizontal.FACING, (Comparable)facing));
            return true;
        }
        return false;
    }

    public static boolean hasNecessaryPower(ItemStack stack, double usage, EntityPlayer player) {
        ElectricItem.manager.chargeFromArmor(stack, (EntityLivingBase)player);
        return Util.isSimilar((double)ElectricItem.manager.discharge(stack, usage, Integer.MAX_VALUE, true, false, true), (double)usage);
    }

    protected static boolean checkNecessaryPower(ItemStack stack, double usage, EntityPlayer player) {
        return ItemGraviToolOLD.checkNecessaryPower(stack, usage, player, false);
    }

    protected static boolean checkNecessaryPower(ItemStack stack, double usage, EntityPlayer player, boolean supressSound) {
        if (ElectricItem.manager.use(stack, usage, (EntityLivingBase)player)) {
            if (!supressSound && player.world.isRemote) {
                IC2.audioManager.playOnce((Object)player, PositionSpec.Hand, "gravitech:wrench.ogg", true, IC2.audioManager.getDefaultVolume());
            }
            return true;
        }
        IC2.platform.messagePlayer(player, Localization.translate((String)"gravitech.graviTool.noEnergy"), new Object[0]);
        return false;
    }

    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        return false;
    }

    public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        return true;
    }

    public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player) {
        return true;
    }

    public boolean isRepairable() {
        return false;
    }

    public int getItemEnchantability() {
        return 0;
    }

    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return false;
    }

    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot slot) {
        return HashMultimap.create();
    }

    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.UNCOMMON;
    }

    public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> subItems) {
        ElectricItemManager.addChargeVariants((Item)item, subItems);
    }

    public boolean canProvideEnergy(ItemStack stack) {
        return false;
    }

    public double getMaxCharge(ItemStack stack) {
        return 300000.0;
    }

    public int getTier(ItemStack stack) {
        return 2;
    }

    public double getTransferLimit(ItemStack stack) {
        return 10000.0;
    }

    public static enum GraviToolMode {
        HOE(TextFormatting.DARK_GREEN),
        TREETAP(TextFormatting.GOLD),
        WRENCH(TextFormatting.AQUA),
        SCREWDRIVER(TextFormatting.LIGHT_PURPLE);
        
        private final ModelResourceLocation model;
        public final String translationName;
        public final TextFormatting colour;
        private static final GraviToolMode[] VALUES;

        private GraviToolMode(TextFormatting colour) {
            this.model = new ModelResourceLocation("gravitech:" + "graviTool".toLowerCase(Locale.ENGLISH) + '/' + this.name().toLowerCase(Locale.ENGLISH), null);
            this.translationName = "gravitech.graviTool." + this.name().toLowerCase(Locale.ENGLISH);
            this.colour = colour;
        }

        public static GraviToolMode getFromID(int ID) {
            return VALUES[ID % VALUES.length];
        }

        static {
            VALUES = GraviToolMode.values();
        }
    }

}

