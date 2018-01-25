package thepurplepoe.gravitech.items;

import ic2.core.IC2;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;
import thepurplepoe.gravitech.Gravitech;

public class GravitechItems {
	
	public static ItemAdvancedChainsaw advancedChainsaw;
	public static ItemAdvancedDrill advancedDrill;
	public static ItemAdvancedElectricJetpack advancedElectricJetpack;
	public static ItemAdvancedNanoChestplate advancedNanoChestplate;
	public static ItemAdvancedLappack advancedLappack;
	public static ItemUltimateLappack ultimateLappack;
	public static ItemGraviChestplate graviChestplate;
	public static ItemGraviTool graviTool;
	public static ItemVajra vajra;
	public static ItemCraftingThings crafting;
	
	public static void setup() {
		advancedChainsaw = new ItemAdvancedChainsaw("advancedChainsaw");
		advancedDrill = new ItemAdvancedDrill("advancedDrill");
		advancedElectricJetpack = new ItemAdvancedElectricJetpack("advancedJetpack");
		advancedNanoChestplate	 = new ItemAdvancedNanoChestplate("advancedNanoChestplate");
		advancedLappack = new ItemAdvancedLappack("advancedLappack");
		ultimateLappack = new ItemUltimateLappack("ultimateLappack");
		graviChestplate = new ItemGraviChestplate("graviChestplate");
		graviTool = new ItemGraviTool("graviTool");
		vajra = new ItemVajra("vajra");
		crafting = new ItemCraftingThings();
	}
	
	public static void register(IForgeRegistry<Item> registry) {
			advancedChainsaw.setCreativeTab(IC2.tabIC2);
			registry.register(advancedChainsaw);
			
			advancedDrill.setCreativeTab(IC2.tabIC2);
			registry.register(advancedDrill);
			
			advancedElectricJetpack.setCreativeTab(IC2.tabIC2);
			registry.register(advancedElectricJetpack);
			
			advancedNanoChestplate.setCreativeTab(IC2.tabIC2);
			registry.register(advancedNanoChestplate);
			
			advancedLappack.setCreativeTab(IC2.tabIC2);
			registry.register(advancedLappack);
			
			ultimateLappack.setCreativeTab(IC2.tabIC2);
			registry.register(ultimateLappack);
			
			graviChestplate.setCreativeTab(IC2.tabIC2);
			registry.register(graviChestplate);
			
			graviTool.setCreativeTab(IC2.tabIC2);
			registry.register(graviTool);
			
			vajra.setCreativeTab(IC2.tabIC2);
			registry.register(vajra);
			
			crafting.setCreativeTab(IC2.tabIC2);
			registry.register(crafting);
			
			Gravitech.proxy.registerModels();
	}
}
