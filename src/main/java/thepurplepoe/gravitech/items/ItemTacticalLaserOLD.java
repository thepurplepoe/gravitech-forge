package thepurplepoe.gravitech.items;

import java.util.LinkedList;
import java.util.List;

import com.google.common.base.CaseFormat;

import ic2.api.event.LaserEvent;
import ic2.api.item.ElectricItem;
import ic2.api.network.INetworkItemEventListener;
import ic2.core.IC2;
import ic2.core.audio.PositionSpec;
import ic2.core.init.Localization;
import ic2.core.item.tool.ItemElectricTool;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import ic2.core.util.Vector3;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thepurplepoe.gravitech.entity.EntityAdvancedMiningLaserOLD;

public class ItemTacticalLaserOLD
extends ItemElectricTool
implements INetworkItemEventListener {
    private static final int EventShotTactical = 0;
    private static final int EventShotSniper = 1;
    private static final int EventShotExplosive = 2;
    private static final int EventShotShotgun = 3;
    private static final int EventShotPenetrator = 4;
    private static final int EventShotIncendiary = 5;
    
    public String name;

    public ItemTacticalLaserOLD(String itemName) {
        super(null, 100);
        this.maxCharge = 600000;
        this.transferLimit = 512;
        this.tier = 3;
        name = itemName;
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
    }
    
    @SideOnly(value=Side.CLIENT)
    public void registerModels() {
        ModelLoader.setCustomModelResourceLocation((Item)this, (int)0, (ModelResourceLocation)new ModelResourceLocation("gravitech:" + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "tacticalLaser"), null));
    	}

    @SideOnly(value=Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag par4) {
        String mode;
        super.addInformation(stack, world, list, par4);
        NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
        switch (nbtData.getInteger("laserSetting")) {
            case 0: {
                mode = Localization.translate("ic2.tooltip.mode.mining");
                break;
            }
            case 1: {
                mode = Localization.translate("ic2.tooltip.mode.lowFocus");
                break;
            }
            case 2: {
                mode = Localization.translate("ic2.tooltip.mode.longRange");
                break;
            }
            case 3: {
                mode = Localization.translate("ic2.tooltip.mode.horizontal");
                break;
            }
            case 4: {
                mode = Localization.translate("ic2.tooltip.mode.superHeat");
                break;
            }
            case 5: {
                mode = Localization.translate("ic2.tooltip.mode.scatter");
                break;
            }
            default: {
                return;
            }
        }
        list.add(Localization.translate("ic2.tooltip.mode", mode));
    }

    @Override
    public List<String> getHudInfo(ItemStack stack, boolean advanced) {
        LinkedList<String> info = new LinkedList<String>();
        NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
        String mode = Localization.translate(ItemTacticalLaserOLD.getModeString(nbtData.getInteger("laserSetting")));
        info.addAll(super.getHudInfo(stack, advanced));
        info.add(Localization.translate("ic2.tooltip.mode", mode));
        return info;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = StackUtil.get(player, hand);
        if (!IC2.platform.isSimulating()) {
            return new ActionResult(EnumActionResult.PASS, (Object)stack);
        }
        NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
        int laserSetting = nbtData.getInteger("laserSetting");
        if (IC2.keyboard.isModeSwitchKeyDown(player)) {
            laserSetting = (laserSetting + 1) % 5;
            nbtData.setInteger("laserSetting", laserSetting);
            IC2.platform.messagePlayer(player, "ic2.tooltip.mode", ItemTacticalLaserOLD.getModeString(laserSetting));
        } else {
            int consume = new int[]{1250, 100, 5000, 0, 2500, 10000, 5000, 7500}[laserSetting];
            if (!ElectricItem.manager.use(stack, consume, (EntityLivingBase)player)) {
                return new ActionResult(EnumActionResult.FAIL, (Object)stack);
            }
            //tactical,sniper,explosive,shotgun,penetrator,incendiary
            switch (laserSetting) {
                case 0: {
                    if (!this.shootLaser(stack, world, (EntityLivingBase)player, Float.POSITIVE_INFINITY, 5.0f, Integer.MAX_VALUE, false, false, 1.5f, 2.0f, 8)) break;
                    IC2.network.get(true).initiateItemEvent(player, stack, 0, true);
                    break;
                }
                case 1: {
                    if (!this.shootLaser(stack, world, (EntityLivingBase)player, Float.POSITIVE_INFINITY, 20.0f, Integer.MAX_VALUE, false, false, 1.8f, 2.0f, 15)) break;
                    IC2.network.get(true).initiateItemEvent(player, stack, 1, true);
                    break;
                }
                case 2: {
                    if (!this.shootLaser(stack, world, (EntityLivingBase)player, Float.POSITIVE_INFINITY, 12.0f, Integer.MAX_VALUE, true, false, 1.5f, 2.5f, 5)) break;
                    IC2.network.get(true).initiateItemEvent(player, stack, 2, true);
                    break;
                }
                case 3: {
                    Vector3 look = Util.getLook((Entity)player);
                    Vector3 right = look.copy().cross(Vector3.UP).normalize();
                    Vector3 up = right.copy().cross(look);
                    int sideShots = 2;
                    double unitDistance = 8.0;
                    look.scale(unitDistance);
                    for (int r = -sideShots; r <= sideShots; ++r) {
                        for (int u = -sideShots; u <= sideShots; ++u) {
                            Vector3 dir = look.copy().addScaled(right, r).addScaled(up, u).normalize();
                            this.shootLaser(stack, world, dir, (EntityLivingBase)player, Float.POSITIVE_INFINITY, 12.0f, Integer.MAX_VALUE, false, false, 1.5f, 2.5f, 5);
                        }
                    }
                    IC2.network.get(true).initiateItemEvent(player, stack, 3, true);
                    break;
                }
                case 4: {
                    if (!this.shootLaser(stack, world, (EntityLivingBase)player, 10, 20.0f, Integer.MAX_VALUE, false, false, 1.5f, 0.1f, 20)) break;
                    IC2.network.get(true).initiateItemEvent(player, stack, 4, true);
                    break;
                }
                case 5: {
                    if (!this.shootLaser(stack, world, (EntityLivingBase)player, Float.POSITIVE_INFINITY, 12.0f, Integer.MAX_VALUE, false, true, 1.5f, 2.5f, 5)) break;
                    IC2.network.get(true).initiateItemEvent(player, stack, 5, true);
                    break;
                }
            }
        }
        return super.onItemRightClick(world, player, hand);
    }

    private static Vector3 adjustStartPos(Vector3 pos, Vector3 dir) {
        return pos.addScaled(dir, 0.2);
    }

    public boolean shootLaser(ItemStack stack, World world, EntityLivingBase owner, float range, float power, int blockBreaks, boolean explosive, boolean smelt, double speed, float blockpower, float laserdamage) {
        Vector3 dir = Util.getLook((Entity)owner);
        return this.shootLaser(stack, world, dir, owner, range, power, blockBreaks, explosive, smelt, speed, blockpower, laserdamage);
    }

    public boolean shootLaser(ItemStack stack, World world, Vector3 dir, EntityLivingBase owner, float range, float power, int blockBreaks, boolean explosive, boolean smelt, double speed, float blockpower, float laserdamage) {
        Vector3 start = ItemTacticalLaserOLD.adjustStartPos(Util.getEyePosition((Entity)owner), dir);
        return this.shootLaser(stack, world, start, dir, owner, range, power, blockBreaks, explosive, smelt, speed, blockpower, laserdamage);
    }

    public boolean shootLaser(ItemStack stack, World world, Vector3 start, Vector3 dir, EntityLivingBase owner, float range, float power, int blockBreaks, boolean explosive, boolean smelt, double speed, float blockpower, float laserdamage) {
        EntityAdvancedMiningLaserOLD entity = new EntityAdvancedMiningLaserOLD(world, start, dir, owner, range, power, blockBreaks, explosive, speed, blockpower, laserdamage);
        LaserEvent.LaserShootEvent event = new LaserEvent.LaserShootEvent(world, entity, owner, range, power, blockBreaks, explosive, smelt, stack);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            return false;
        }
        entity.copyDataFromEvent(event);
        world.spawnEntity((Entity)entity);
        return true;
    }

    @SideOnly(value=Side.CLIENT)
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.UNCOMMON;
    }

    @Override
    public void onNetworkEvent(ItemStack stack, EntityPlayer player, int event) {
        switch (event) {
            case 0: {
                IC2.audioManager.playOnce((Object)player, PositionSpec.Hand, "Tools/MiningLaser/MiningLaser.ogg", true, IC2.audioManager.getDefaultVolume());
                break;
            }
            case 1: {
                IC2.audioManager.playOnce((Object)player, PositionSpec.Hand, "Tools/MiningLaser/MiningLaserLowFocus.ogg", true, IC2.audioManager.getDefaultVolume());
                break;
            }
            case 2: {
                IC2.audioManager.playOnce((Object)player, PositionSpec.Hand, "Tools/MiningLaser/MiningLaserLongRange.ogg", true, IC2.audioManager.getDefaultVolume());
                break;
            }
            case 3: {
                IC2.audioManager.playOnce((Object)player, PositionSpec.Hand, "Tools/MiningLaser/MiningLaser.ogg", true, IC2.audioManager.getDefaultVolume());
                break;
            }
            case 4: {
                IC2.audioManager.playOnce((Object)player, PositionSpec.Hand, "Tools/MiningLaser/MiningLaser.ogg", true, IC2.audioManager.getDefaultVolume());
                break;
            }
            case 5: {
                IC2.audioManager.playOnce((Object)player, PositionSpec.Hand, "Tools/MiningLaser/MiningLaserScatter.ogg", true, IC2.audioManager.getDefaultVolume());
                break;
            }
            case 6: {
                IC2.audioManager.playOnce((Object)player, PositionSpec.Hand, "Tools/MiningLaser/MiningLaserExplosive.ogg", true, IC2.audioManager.getDefaultVolume());
                break;
            }
            case 7: {
                IC2.audioManager.playOnce((Object)player, PositionSpec.Hand, "Tools/MiningLaser/MiningLaserScatter.ogg", true, IC2.audioManager.getDefaultVolume());
            }
        }
    }

    private static String getModeString(int mode) {
        switch (mode) {
            case 0: {
                return "Mode: Tactical";
            }
            case 1: {
                return "Mode: Sniper";
            }
            case 2: {
                return "Mode: Explosive";
            }
            case 3: {
                return "Mode: Shotgun";
            }
            case 4: {
                return "Mode: Penetrator";
            }
            case 5: {
                return "Mode: Incendiary";
            }
        }
        assert (false);
        return "";
    }
}

