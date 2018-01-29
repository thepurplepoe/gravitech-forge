package thepurplepoe.gravitech.items;

import ic2.core.IC2;
import ic2.core.item.armor.jetpack.JetpackAttachmentRecipe;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;
import thepurplepoe.gravitech.GravitechOLD;

public class GravitechItemsOLD {
	
	public static ItemAdvancedChainsawOLD advancedChainsaw;
	public static ItemAdvancedDrill advancedDrill;
	public static ItemAdvancedElectricJetpack advancedElectricJetpack;
	public static ItemAdvancedNanoChestplateOLD advancedNanoChestplate;
	public static ItemAdvancedLappackOLD advancedLappack;
	public static ItemUltimateLappackOLD ultimateLappack;
	public static ItemGraviChestplateOLD graviChestplate;
	public static ItemGraviToolOLD graviTool;
	public static ItemVajraOLD vajra;
	public static ItemTacticalLaserOLD tacticalLaser;
	public static ItemCraftingThingsOLD crafting;
	
	public static void setup() {
		advancedChainsaw = new ItemAdvancedChainsawOLD("advancedChainsaw");
		advancedDrill = new ItemAdvancedDrill();
		advancedElectricJetpack = new ItemAdvancedElectricJetpack("advancedJetpack");
		advancedNanoChestplate	 = new ItemAdvancedNanoChestplateOLD("advancedNanoChestplate");
		advancedLappack = new ItemAdvancedLappackOLD("advancedLappack");
		ultimateLappack = new ItemUltimateLappackOLD("ultimateLappack");
		graviChestplate = new ItemGraviChestplateOLD("graviChestplate");
		graviTool = new ItemGraviToolOLD("graviTool");
		vajra = new ItemVajraOLD("vajra");
		tacticalLaser = new ItemTacticalLaserOLD("tacticalLaser");
		crafting = new ItemCraftingThingsOLD();
		

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
			
			tacticalLaser.setCreativeTab(IC2.tabIC2);
			registry.register(tacticalLaser);
			
			crafting.setCreativeTab(IC2.tabIC2);
			registry.register(crafting);
			
	    	JetpackAttachmentRecipe.blacklistedItems.add(advancedElectricJetpack);
	    	JetpackAttachmentRecipe.blacklistedItems.add(advancedNanoChestplate);
	    	JetpackAttachmentRecipe.blacklistedItems.add(graviChestplate);
			
			GravitechOLD.proxy.registerModels();
	}
}
