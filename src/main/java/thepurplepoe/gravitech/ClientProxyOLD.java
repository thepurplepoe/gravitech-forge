package thepurplepoe.gravitech;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import thepurplepoe.gravitech.entity.EntityAdvancedMiningLaserOLD;
import thepurplepoe.gravitech.items.GravitechItemsOLD;
import thepurplepoe.gravitech.renders.RenderCrossedOLD;

public class ClientProxyOLD extends CommonProxyOLD {

	@Override
	public void registerItemRenderer(Item item, int meta, String name, String variant) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation("gravitech" + ":" + name, variant));
	}
	
	@Override
	public void preInit() {
		 RenderingRegistry.registerEntityRenderingHandler(EntityAdvancedMiningLaserOLD.class, (IRenderFactory<EntityAdvancedMiningLaserOLD>)new IRenderFactory<EntityAdvancedMiningLaserOLD>(){

	            public Render<EntityAdvancedMiningLaserOLD> createRenderFor(RenderManager manager) {
	                return new RenderCrossedOLD(manager, new ResourceLocation("ic2", "textures/models/laser.png"));
	            }
	        });
	}
	
	@Override
	public void registerModels() {	
		GravitechItemsOLD.advancedChainsaw.registerModels();
		GravitechItemsOLD.advancedDrill.registerModels();
		GravitechItemsOLD.advancedElectricJetpack.registerModels();
		GravitechItemsOLD.advancedNanoChestplate.registerModels();
		GravitechItemsOLD.advancedLappack.registerModels();
		GravitechItemsOLD.ultimateLappack.registerModels();
		GravitechItemsOLD.graviChestplate.registerModels();
		GravitechItemsOLD.graviTool.registerModels();
		GravitechItemsOLD.vajra.registerModels();
		GravitechItemsOLD.tacticalLaser.registerModels();
		for (int i = 0; i < 7; i++) {
			GravitechItemsOLD.crafting.registerModels(i);
		}
	}
}
