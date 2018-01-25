package thepurplepoe.gravitech.renders;

import java.lang.reflect.Field;
import java.util.Collection;

import ic2.core.util.ReflectionUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockSign;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thepurplepoe.gravitech.items.GravitechItems;
import thepurplepoe.gravitech.items.ItemAdvancedDrill;

@SideOnly(value=Side.CLIENT)
public final class PrettyUtil
implements IResourceManagerReloadListener {
    public static final TextureAtlasSprite[] DESTROY_BLOCK_ICONS = new TextureAtlasSprite[10];
    public static final Minecraft mc = Minecraft.getMinecraft();
    private static final Field CUR_BLOCK_DAMAGE_MP = PrettyUtil.getCBDMP();

    private static Field getCBDMP() {
        Field field = ReflectionUtil.getField(PlayerControllerMP.class, (String[])new String[]{"e", "field_78770_f", "curBlockDamageMP"});
        if (field == null) {
            throw new RuntimeException("Cannot find curBlockDamageMP!");
        }
        return field;
    }

    public PrettyUtil() {
        MinecraftForge.EVENT_BUS.register((Object)this);
        IResourceManager resourceManager = mc.getResourceManager();
        if (!(resourceManager instanceof IReloadableResourceManager)) {
            throw new IllegalStateException("ResourceManager is not reloadable?!");
        }
        ((IReloadableResourceManager)resourceManager).registerReloadListener((IResourceManagerReloadListener)this);
    }

    public void onResourceManagerReload(IResourceManager resourceManager) {
        TextureMap texturemap = mc.getTextureMapBlocks();
        for (int icon = 0; icon < DESTROY_BLOCK_ICONS.length; icon = (int)((byte)(icon + 1))) {
            PrettyUtil.DESTROY_BLOCK_ICONS[icon] = texturemap.getAtlasSprite("minecraft:blocks/destroy_stage_" + icon);
        }
    }

    @SubscribeEvent
    public void renderAdditionalBlockBounds(DrawBlockHighlightEvent event) {
        EntityPlayer player;
        ItemStack stack;
        if (event.getSubID() == 0 && event.getTarget().typeOfHit == RayTraceResult.Type.BLOCK && (stack = (player = event.getPlayer()).getHeldItem(EnumHand.MAIN_HAND)) != null && stack.getItem() == GravitechItems.advancedDrill && ItemAdvancedDrill.readDrillMode(stack) == ItemAdvancedDrill.DrillMode.BIG_HOLES) {
            PrettyUtil.drawAdditionalBlockbreak(event.getContext(), player, event.getPartialTicks(), ItemAdvancedDrill.getBrokenBlocks(player, event.getTarget()));
        }
    }

    public static void drawAdditionalBlockbreak(RenderGlobal context, EntityPlayer player, float partialTicks, Collection<BlockPos> blocks) {
        for (BlockPos pos : blocks) {
            context.drawSelectionBox(player, new RayTraceResult(new Vec3d(0.0, 0.0, 0.0), null, pos), 0, partialTicks);
        }
        if (PrettyUtil.mc.playerController.getIsHittingBlock()) {
            PrettyUtil.drawBlockDamageTexture((Entity)player, blocks, partialTicks);
        }
    }

    private static float get_curBlockDamageMP(PlayerControllerMP controller) {
        try {
            return CUR_BLOCK_DAMAGE_MP.getFloat((Object)controller);
        }
        catch (IllegalArgumentException e) {
            throw new RuntimeException("curBlockDamageMP is not a float?! Turns out it was a " + CUR_BLOCK_DAMAGE_MP.getType(), e);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException("One job...", e);
        }
    }

    public static void drawBlockDamageTexture(Entity entity, Collection<BlockPos> blocks, float partialTicks) {
        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partialTicks;
        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks;
        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partialTicks;
        int progress = (int)(PrettyUtil.get_curBlockDamageMP(PrettyUtil.mc.playerController) * 10.0f) - 1;
        if (progress < 0) {
            return;
        }
        TextureAtlasSprite sprite = DESTROY_BLOCK_ICONS[progress];
        PrettyUtil.mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        GlStateManager.tryBlendFuncSeparate((int)774, (int)768, (int)1, (int)0);
        GlStateManager.enableBlend();
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)0.5f);
        GlStateManager.doPolygonOffset((float)-3.0f, (float)-3.0f);
        GlStateManager.enablePolygonOffset();
        GlStateManager.alphaFunc((int)516, (float)0.1f);
        GlStateManager.enableAlpha();
        GlStateManager.pushMatrix();
        BufferBuilder worldRenderer = Tessellator.getInstance().getBuffer();
        worldRenderer.begin(7, DefaultVertexFormats.BLOCK);
        worldRenderer.setTranslation(- x, - y, - z);
        World world = entity.world;
        for (BlockPos pos : blocks) {
            boolean hasBreak;
            IBlockState state = world.getBlockState(pos);
            if (state.getMaterial() == Material.AIR) continue;
            Block block = state.getBlock();
            boolean bl = hasBreak = block instanceof BlockChest || block instanceof BlockEnderChest || block instanceof BlockSign || block instanceof BlockSkull;
            if (!hasBreak) {
                TileEntity te = world.getTileEntity(pos);
                boolean bl2 = hasBreak = te != null && te.canRenderBreaking();
            }
            if (hasBreak) continue;
            mc.getBlockRendererDispatcher().renderBlockDamage(state, pos, sprite, (IBlockAccess)world);
        }
        Tessellator.getInstance().draw();
        worldRenderer.setTranslation(0.0, 0.0, 0.0);
        GlStateManager.disableAlpha();
        GlStateManager.doPolygonOffset((float)0.0f, (float)0.0f);
        GlStateManager.disablePolygonOffset();
        GlStateManager.enableAlpha();
        GlStateManager.depthMask((boolean)true);
        GlStateManager.popMatrix();
    }
}

