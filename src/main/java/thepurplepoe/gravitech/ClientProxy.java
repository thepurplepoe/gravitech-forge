package thepurplepoe.gravitech;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import thepurplepoe.gravitech.items.GravitechItems;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerItemRenderer(Item item, int meta, String name, String variant) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation("gravitech" + ":" + name, variant));
	}
	
	@Override
	public void registerModels() {	
		GravitechItems.advancedChainsaw.registerModels();
		GravitechItems.advancedDrill.registerModels();
		GravitechItems.advancedElectricJetpack.registerModels();
		GravitechItems.advancedNanoChestplate.registerModels();
		GravitechItems.advancedLappack.registerModels();
		GravitechItems.ultimateLappack.registerModels();
		GravitechItems.graviChestplate.registerModels();
		GravitechItems.graviTool.registerModels();
		GravitechItems.vajra.registerModels();
		GravitechItems.tacticalLaser.registerModels();
		for (int i = 0; i < 7; i++) {
			GravitechItems.crafting.registerModels(i);
		}
	}
}
