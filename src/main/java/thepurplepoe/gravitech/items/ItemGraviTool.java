/*
 * Decompiled with CFR 0_124.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.HashMultimap
 *  com.google.common.collect.Multimap
 *  com.mojang.authlib.GameProfile
 *  ic2.api.item.ElectricItem
 *  ic2.api.item.IElectricItem
 *  ic2.api.item.IElectricItemManager
 *  ic2.api.tile.IWrenchable
 *  ic2.core.CreativeTabIC2
 *  ic2.core.IC2
 *  ic2.core.Platform
 *  ic2.core.audio.AudioManager
 *  ic2.core.audio.PositionSpec
 *  ic2.core.block.TileEntityBarrel
 *  ic2.core.init.Localization
 *  ic2.core.init.MainConfig
 *  ic2.core.item.ElectricItemManager
 *  ic2.core.item.tool.ItemTreetap
 *  ic2.core.ref.BlockName
 *  ic2.core.ref.IItemModelProvider
 *  ic2.core.ref.ItemName
 *  ic2.core.util.Config
 *  ic2.core.util.ConfigUtil
 *  ic2.core.util.Keyboard
 *  ic2.core.util.Log
 *  ic2.core.util.LogCategory
 *  ic2.core.util.StackUtil
 *  ic2.core.util.Util
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockDirt
 *  net.minecraft.block.BlockDirt$DirtType
 *  net.minecraft.block.BlockGrass
 *  net.minecraft.block.BlockHorizontal
 *  net.minecraft.block.properties.IProperty
 *  net.minecraft.block.properties.PropertyDirection
 *  net.minecraft.block.properties.PropertyEnum
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.renderer.ItemMeshDefinition
 *  net.minecraft.client.renderer.block.model.ModelBakery
 *  net.minecraft.client.renderer.block.model.ModelResourceLocation
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.ai.attributes.AttributeModifier
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.entity.player.PlayerCapabilities
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.SoundEvents
 *  net.minecraft.inventory.EntityEquipmentSlot
 *  net.minecraft.item.EnumRarity
 *  net.minecraft.item.Item
 *  net.minecraft.item.Item$ToolMaterial
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemTool
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.server.management.PlayerInteractionManager
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.ActionResult
 *  net.minecraft.util.EnumActionResult
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumFacing$Axis
 *  net.minecraft.util.EnumFacing$AxisDirection
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.SoundCategory
 *  net.minecraft.util.SoundEvent
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.text.TextFormatting
 *  net.minecraft.world.GameType
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraftforge.client.model.ModelLoader
 *  net.minecraftforge.common.ForgeHooks
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.entity.player.UseHoeEvent
 *  net.minecraftforge.fml.common.eventhandler.Event
 *  net.minecraftforge.fml.common.eventhandler.Event$Result
 *  net.minecraftforge.fml.common.eventhandler.EventBus
 *  net.minecraftforge.fml.common.registry.GameRegistry
 *  net.minecraftforge.fml.common.registry.IForgeRegistryEntry
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
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
import ic2.core.ref.IItemModelProvider;
import ic2.core.ref.ItemName;
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
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistryEntry;
import thepurplepoe.gravitech.Gravitech;

public class ItemGraviTool
extends ItemTool
implements IElectricItem,
IItemModelProvider {
    protected static final String NAME = "graviTool";
    protected static final double ROTATE = 50.0;
    protected static final double HOE = 50.0;
    protected static final double TAP = 50.0;
    protected static final double SCREW = 500.0;

    public ItemGraviTool() {
        super(Item.ToolMaterial.IRON, Collections.emptySet());
        ((ItemGraviTool)GameRegistry.register((IForgeRegistryEntry)this, (ResourceLocation)new ResourceLocation("Gravitech", "graviTool"))).setUnlocalizedName("graviTool");
        this.setMaxDamage(27);
        this.setCreativeTab((CreativeTabs)IC2.tabIC2);
        this.efficiencyOnProperMaterial = 16.0f;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerModels(ItemName name) {
        ModelLoader.setCustomMeshDefinition((Item)this, (ItemMeshDefinition)new ItemMeshDefinition(){

            public ModelResourceLocation getModelLocation(ItemStack stack) {
                GraviToolMode mode = ItemGraviTool.hasToolMode(stack) ? ItemGraviTool.readToolMode(stack) : GraviToolMode.HOE;
                return mode.model;
            }
        });
        for (GraviToolMode mode : VALUES) {
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
        return "Gravitech." + super.getUnlocalizedName().substring(5);
    }

    public String getUnlocalizedName(ItemStack stack) {
        return this.getUnlocalizedName();
    }

    public String getItemStackDisplayName(ItemStack stack) {
        if (ItemGraviTool.hasToolMode(stack)) {
            return Localization.translate((String)"Gravitech.graviTool.set", (Object[])new Object[]{Localization.translate((String)this.getUnlocalizedName(stack)), Localization.translate((String)ItemGraviTool.readToolMode((ItemStack)stack).translationName)});
        }
        return Localization.translate((String)this.getUnlocalizedName(stack));
    }

    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
        if (IC2.keyboard.isModeSwitchKeyDown(player)) {
            if (world.isRemote) {
                IC2.audioManager.playOnce((Object)player, PositionSpec.Hand, "Gravitech:toolChange.ogg", true, IC2.audioManager.getDefaultVolume());
            } else {
                GraviToolMode mode = ItemGraviTool.readNextToolMode(stack);
                ItemGraviTool.saveToolMode(stack, mode);
                Gravitech.messagePlayer(player, "Gravitech.graviTool.changeTool", mode.colour, mode.translationName);
            }
            return new ActionResult(EnumActionResult.SUCCESS, (Object)stack);
        }
        return super.onItemRightClick(stack, world, player, hand);
    }

    public EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        switch (ItemGraviTool.readToolMode(stack)) {
            case WRENCH: {
                return this.onWrenchUse(stack, player, world, pos, side) ? (world.isRemote ? EnumActionResult.PASS : EnumActionResult.SUCCESS) : EnumActionResult.FAIL;
            }
            case SCREWDRIVER: {
                return this.onScrewdriverUse(stack, player, world, pos) ? (world.isRemote ? EnumActionResult.PASS : EnumActionResult.SUCCESS) : EnumActionResult.FAIL;
            }
        }
        return super.onItemUseFirst(stack, player, world, pos, side, hitX, hitY, hitZ, hand);
    }

    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        switch (ItemGraviTool.readToolMode(stack)) {
            case HOE: {
                return this.onHoeUse(stack, player, world, pos, facing) ? (world.isRemote ? EnumActionResult.PASS : EnumActionResult.SUCCESS) : EnumActionResult.FAIL;
            }
            case TREETAP: {
                return this.onTreeTapUse(stack, player, world, pos, facing) ? (world.isRemote ? EnumActionResult.PASS : EnumActionResult.SUCCESS) : EnumActionResult.FAIL;
            }
        }
        return super.onItemUse(stack, player, world, pos, hand, facing, hitX, hitY, hitZ);
    }

    protected boolean onHoeUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side) {
        if (!player.canPlayerEdit(pos.offset(side), side, stack) || !ItemGraviTool.hasNecessaryPower(stack, HOE, player)) {
            return false;
        }
        UseHoeEvent event = new UseHoeEvent(player, stack, world, pos);
        if (MinecraftForge.EVENT_BUS.post((Event)event)) {
            return false;
        }
        if (event.getResult() == Event.Result.ALLOW) {
            return ItemGraviTool.checkNecessaryPower(stack, HOE, player, true);
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
        if (ItemGraviTool.checkNecessaryPower(stack, HOE, player, true)) {
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
                if (ItemGraviTool.checkNecessaryPower(stack, TAP, player, true)) {
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
        if (state.getBlock() == BlockName.rubber_wood.getInstance() && ItemGraviTool.hasNecessaryPower(stack, TAP, player)) {
            return ItemTreetap.attemptExtract((EntityPlayer)player, (World)world, (BlockPos)pos, (EnumFacing)side, (IBlockState)state, (List)null) && ItemGraviTool.checkNecessaryPower(stack, TAP, player);
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
                if (ItemGraviTool.hasNecessaryPower(stack, ROTATE, player)) {
                    if (wrenchable.setFacing(world, pos, newFacing, player)) {
                        return ItemGraviTool.checkNecessaryPower(stack, ROTATE, player);
                    }
                } else {
                    return false;
                }
            }
            if (wrenchable.wrenchCanRemove(world, pos, player)) {
                if (!ItemGraviTool.hasNecessaryPower(stack, ROTATE, player)) {
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
                    List drops = wrenchable.getWrenchDrops(world, pos, state, te, player, 0);
                    for (ItemStack drop : drops) {
                        StackUtil.dropAsEntity((World)world, (BlockPos)pos, (ItemStack)drop);
                    }
                    if (!player.capabilities.isCreativeMode && experience > 0) {
                        block.dropXpOnBlockBreak(world, pos, experience);
                    }
                }
                return ItemGraviTool.checkNecessaryPower(stack, ROTATE, player);
            }
        }
        return false;
    }

    protected boolean onScrewdriverUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos) {
        IBlockState state = Util.getBlockState((IBlockAccess)world, (BlockPos)pos);
        Block block = state.getBlock();
        if (!block.isAir(state, (IBlockAccess)world, pos) && block instanceof BlockHorizontal && ItemGraviTool.checkNecessaryPower(stack, 500.0, player)) {
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
        return ItemGraviTool.checkNecessaryPower(stack, usage, player, false);
    }

    protected static boolean checkNecessaryPower(ItemStack stack, double usage, EntityPlayer player, boolean supressSound) {
        if (ElectricItem.manager.use(stack, usage, (EntityLivingBase)player)) {
            if (!supressSound && player.worldObj.isRemote) {
                IC2.audioManager.playOnce((Object)player, PositionSpec.Hand, "Gravitech:wrench.ogg", true, IC2.audioManager.getDefaultVolume());
            }
            return true;
        }
        IC2.platform.messagePlayer(player, Localization.translate((String)"Gravitech.graviTool.noEnergy"), new Object[0]);
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
            this.model = new ModelResourceLocation("Gravitech:" + "graviTool".toLowerCase(Locale.ENGLISH) + '/' + this.name().toLowerCase(Locale.ENGLISH), null);
            this.translationName = "Gravitech.graviTool." + this.name().toLowerCase(Locale.ENGLISH);
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

