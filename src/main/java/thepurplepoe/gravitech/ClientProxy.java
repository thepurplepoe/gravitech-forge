package thepurplepoe.gravitech;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ClientProxy extends CommonProxy {
	@Override
	public void preInit() {
		
	}
	
	@Override
	public void init() {
		
	}
	
	@Override
	public void postInit() {
		
	}
	
	@Override
	public void registerItemModel(Item item, int i, ModelResourceLocation modelResourceLocation) {
		ModelLoader.setCustomModelResourceLocation(item, i, modelResourceLocation);
	}
}
