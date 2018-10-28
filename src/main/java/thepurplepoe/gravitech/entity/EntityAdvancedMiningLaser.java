package thepurplepoe.gravitech.entity;

import ic2.api.event.LaserEvent;
import ic2.core.ExplosionIC2;
import ic2.core.IC2;
import ic2.core.IC2Achievements;
import ic2.core.Platform;
import ic2.core.block.MaterialIC2TNT;
import ic2.core.ref.BlockName;
import ic2.core.ref.IBlockModelProvider;
import ic2.core.util.StackUtil;
import ic2.core.util.Vector3;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.registry.IThrowableEntity;

public class EntityAdvancedMiningLaser
extends Entity
implements IThrowableEntity {
    public float range = 0.0f;
    public float blockPower = 0.0f;
    public float damagePower = 0.0f;
    public int blockBreaks = 0;
    public boolean explosive = false;
    public static double laserSpeed = 1.0;
    public EntityLivingBase owner;
    public boolean headingSet = false;
    public boolean smelt = false;
    private int ticksInAir = 0;

    public EntityAdvancedMiningLaser(World world) {
        super(world);
        this.setSize(0.8f, 0.8f);
    }

    public EntityAdvancedMiningLaser(World world, Vector3 start, Vector3 dir, EntityLivingBase owner, float range, float power, int blockBreaks, boolean explosive) {
        super(world);
        this.owner = owner;
        this.setSize(0.8f, 0.8f);
        this.setPosition(start.x, start.y, start.z);
        this.setLaserHeading(dir.x, dir.y, dir.z, 1.0);
        this.range = range;
        this.power = power;
        this.blockBreaks = blockBreaks;
        this.explosive = explosive;
    }

    protected void entityInit() {
    }

    public void setLaserHeading(double motionX, double motionY, double motionZ, double speed) {
        double currentSpeed = Math.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
        this.motionX = motionX / currentSpeed * speed;
        this.motionY = motionY / currentSpeed * speed;
        this.motionZ = motionZ / currentSpeed * speed;
        this.prevRotationYaw = this.rotationYaw = (float)Math.toDegrees(Math.atan2(motionX, motionZ));
        this.prevRotationPitch = this.rotationPitch = (float)Math.toDegrees(Math.atan2(motionY, Math.sqrt(motionX * motionX + motionZ * motionZ)));
        this.headingSet = true;
    }

    public void setVelocity(double motionX, double motionY, double motionZ) {
        this.setLaserHeading(motionX, motionY, motionZ, laserSpeed);
    }

    public void onUpdate() {
        block12 : {
            block11 : {
                super.onUpdate();
                if (IC2.platform.isSimulating() && (this.range < 1.0f || this.power <= 0.0f || this.blockBreaks <= 0)) {
                    if (this.explosive) {
                        this.explode();
                    }
                    this.setDead();
                    return;
                }
                ++this.ticksInAir;
                Vec3d oldPosition = new Vec3d(this.posX, this.posY, this.posZ);
                Vec3d newPosition = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
                World world = this.getEntityWorld();
                RayTraceResult result = world.rayTraceBlocks(oldPosition, newPosition, false, true, false);
                oldPosition = new Vec3d(this.posX, this.posY, this.posZ);
                newPosition = result != null ? new Vec3d(result.hitVec.x, result.hitVec.y, result.hitVec.z) : new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
                Entity hitEntity = null;
                List<Entity> list = world.getEntitiesWithinAABBExcludingEntity((Entity)this, this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).expand(1.0, 1.0, 1.0));
                double distance = 0.0;
                for (Entity entity : list) {
                    double newDistance;
                    if (!entity.canBeCollidedWith() || entity == this.owner && this.ticksInAir < 5) continue;
                    float f4 = 0.3f;
                    AxisAlignedBB axisalignedbb1 = entity.getEntityBoundingBox().expand((double)f4, (double)f4, (double)f4);
                    RayTraceResult movingobjectposition1 = axisalignedbb1.calculateIntercept(oldPosition, newPosition);
                    if (movingobjectposition1 == null || (newDistance = oldPosition.distanceTo(movingobjectposition1.hitVec)) >= distance && distance != 0.0) continue;
                    hitEntity = entity;
                    distance = newDistance;
                }
                if (hitEntity != null) {
                    result = new RayTraceResult(hitEntity);
                }
                if (result == null || result.typeOfHit == RayTraceResult.Type.MISS || world.isRemote) break block11;
                if (this.explosive) {
                    this.explode();
                    this.setDead();
                    return;
                }
                switch (result.typeOfHit) {
                    case BLOCK: {
                        if (!this.hitBlock(result.getBlockPos(), result.sideHit)) {
                            this.power -= 0.5f;
                        }
                        break block12;
                    }
                    case ENTITY: {
                        this.hitEntity(result.entityHit);
                        break block12;
                    }
                }
                throw new RuntimeException("invalid hit type: " + (Object)result.typeOfHit);
            }
            this.power -= 0.5f;
        }
        this.setPosition(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        this.range = (float)((double)this.range - Math.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ));
        if (this.isInWater()) {
            this.setDead();
        }
    }

    private void explode() {
        World world = this.getEntityWorld();
        LaserEvent.LaserExplodesEvent event = new LaserEvent.LaserExplodesEvent(world, this, this.owner, this.range, this.power, this.blockBreaks, this.explosive, this.smelt, 5.0f, 0.85f, 0.55f);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            this.setDead();
            return;
        }
        this.copyDataFromEvent(event);
        ExplosionIC2 explosion = new ExplosionIC2(world, null, this.posX, this.posY, this.posZ, event.explosionPower, event.explosionDropRate);
        explosion.doExplosion();
    }

    private void hitEntity(Entity entity) {
        LaserEvent.LaserHitsEntityEvent event = new LaserEvent.LaserHitsEntityEvent(this.getEntityWorld(), this, this.owner, this.range, this.power, this.blockBreaks, this.explosive, this.smelt, entity);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            this.setDead();
            return;
        }
        this.copyDataFromEvent(event);
        entity = event.hitEntity;
        int damage = (int)this.power;
        if (damage > 0) {
            entity.setFire(damage * (this.smelt ? 2 : 1));
            if (entity.attackEntityFrom(new EntityDamageSourceIndirect("arrow", (Entity)this, (Entity)this.owner).setProjectile(), (float)damage) && (this.owner instanceof EntityPlayer && entity instanceof EntityDragon && ((EntityDragon)entity).getHealth() <= 0.0f || entity instanceof MultiPartEntityPart && ((MultiPartEntityPart)entity).parent instanceof EntityDragon && ((EntityLivingBase)((MultiPartEntityPart)entity).parent).getHealth() <= 0.0f)) {
                IC2.achievements.issueAchievement((EntityPlayer)this.owner, "killDragonMiningLaser");
            }
        }
        this.setDead();
    }

    private boolean hitBlock(BlockPos pos, EnumFacing side) {
        World world = this.getEntityWorld();
        LaserEvent.LaserHitsBlockEvent event = new LaserEvent.LaserHitsBlockEvent(world, this, this.owner, this.range, this.power, this.blockBreaks, this.explosive, this.smelt, pos, side, 0.9f, true, true);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            this.setDead();
            return true;
        }
        this.copyDataFromEvent(event);
        IBlockState state = world.getBlockState(event.pos);
        Block block = state.getBlock();
        if (block.isAir(state, (IBlockAccess)world, event.pos) || block == Blocks.GLASS || block == Blocks.GLASS_PANE || block == BlockName.glass.getInstance()) {
            return false;
        }
        if (world.isRemote) {
            return true;
        }
        float hardness = state.getBlockHardness(world, event.pos);
        if (hardness < 0.0f) {
            this.setDead();
            return true;
        }
        this.power -= hardness / 1.5f;
        if (this.power < 0.0f) {
            return true;
        }
        ArrayList<ItemStack> replacements = new ArrayList<ItemStack>();
        if (state.getMaterial() == Material.TNT || state.getMaterial() == MaterialIC2TNT.instance) {
            block.onBlockDestroyedByExplosion(world, event.pos, new Explosion(world, (Entity)this, (double)event.pos.getX() + 0.5, (double)event.pos.getY() + 0.5, (double)event.pos.getZ() + 0.5, 1.0f, false, true));
        } else if (this.smelt) {
            if (state.getMaterial() == Material.WOOD) {
                event.dropBlock = false;
            } else {
                for (ItemStack isa : StackUtil.getDrops((IBlockAccess)world, event.pos, state, block, 0)) {
                    ItemStack is = FurnaceRecipes.instance().getSmeltingResult(isa);
                    if (StackUtil.isEmpty(is)) continue;
                    replacements.add(is);
                }
                event.dropBlock = replacements.isEmpty();
            }
        }
        if (event.removeBlock) {
            if (event.dropBlock) {
                block.dropBlockAsItemWithChance(world, event.pos, state, event.dropChance, 0);
            }
            world.setBlockToAir(event.pos);
            for (ItemStack replacement : replacements) {
                if (!StackUtil.placeBlock(replacement, world, event.pos)) {
                    StackUtil.dropAsEntity(world, event.pos, replacement);
                }
                this.power = 0.0f;
            }
            if (world.rand.nextInt(10) == 0 && state.getMaterial().getCanBurn()) {
                world.setBlockState(event.pos, Blocks.FIRE.getDefaultState());
            }
        }
        --this.blockBreaks;
        return true;
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
    }

    public void copyDataFromEvent(LaserEvent event) {
        this.owner = event.owner;
        this.range = event.range;
        this.power = event.power;
        this.blockBreaks = event.blockBreaks;
        this.explosive = event.explosive;
        this.smelt = event.smelt;
    }

    public Entity getThrower() {
        return this.owner;
    }

    public void setThrower(Entity entity) {
        if (entity instanceof EntityLivingBase) {
            this.owner = (EntityLivingBase)entity;
        }
    }

}

