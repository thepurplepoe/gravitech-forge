package thepurplepoe.gravitech.renders;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thepurplepoe.gravitech.entity.EntityAdvancedMiningLaserOLD;

@SideOnly(value=Side.CLIENT)
public class RenderCrossedOLD
extends Render<EntityAdvancedMiningLaserOLD> {
    private final ResourceLocation texture;

    public RenderCrossedOLD(RenderManager manager, ResourceLocation texture) {
        super(manager);
        this.texture = texture;
    }

    public void doRender(EntityAdvancedMiningLaserOLD entity, double x, double y, double z, float entityYaw, float partialTicks) {
        if (entity.prevRotationYaw == 0.0f && entity.prevRotationPitch == 0.0f) {
            return;
        }
        this.bindTexture(this.getEntityTexture(entity));
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)((float)x), (float)((float)y), (float)((float)z));
        GlStateManager.rotate((float)(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0f), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks), (float)0.0f, (float)0.0f, (float)1.0f);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldrenderer = tessellator.getBuffer();
        float uSideS = 0.0f;
        float uSideE = 0.5f;
        float vSideS = 0.0f;
        float vSideE = 0.15625f;
        float uBackS = 0.0f;
        float uBackE = 0.15625f;
        float vBackS = 0.15625f;
        float vBackE = 0.3125f;
        float scale = 0.05625f;
        GlStateManager.enableRescaleNormal();
        GlStateManager.rotate((float)45.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        GlStateManager.scale((float)scale, (float)scale, (float)scale);
        GlStateManager.translate((float)-4.0f, (float)0.0f, (float)0.0f);
        GL11.glNormal3f((float)scale, (float)0.0f, (float)0.0f);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(-7.0, -2.0, -2.0).tex((double)uBackS, (double)vBackS).endVertex();
        worldrenderer.pos(-7.0, -2.0, 2.0).tex((double)uBackE, (double)vBackS).endVertex();
        worldrenderer.pos(-7.0, 2.0, 2.0).tex((double)uBackE, (double)vBackE).endVertex();
        worldrenderer.pos(-7.0, 2.0, -2.0).tex((double)uBackS, (double)vBackE).endVertex();
        tessellator.draw();
        GL11.glNormal3f((float)(- scale), (float)0.0f, (float)0.0f);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(-7.0, 2.0, -2.0).tex((double)uBackS, (double)vBackS).endVertex();
        worldrenderer.pos(-7.0, 2.0, 2.0).tex((double)uBackE, (double)vBackS).endVertex();
        worldrenderer.pos(-7.0, -2.0, 2.0).tex((double)uBackE, (double)vBackE).endVertex();
        worldrenderer.pos(-7.0, -2.0, -2.0).tex((double)uBackS, (double)vBackE).endVertex();
        tessellator.draw();
        for (int j = 0; j < 4; ++j) {
            GlStateManager.rotate((float)90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glNormal3f((float)0.0f, (float)0.0f, (float)scale);
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
            worldrenderer.pos(-8.0, -2.0, 0.0).tex((double)uSideS, (double)vSideS).endVertex();
            worldrenderer.pos(8.0, -2.0, 0.0).tex((double)uSideE, (double)vSideS).endVertex();
            worldrenderer.pos(8.0, 2.0, 0.0).tex((double)uSideE, (double)vSideE).endVertex();
            worldrenderer.pos(-8.0, 2.0, 0.0).tex((double)uSideS, (double)vSideE).endVertex();
            tessellator.draw();
        }
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
    }

    protected ResourceLocation getEntityTexture(EntityAdvancedMiningLaserOLD entity) {
        return this.texture;
    }
}

