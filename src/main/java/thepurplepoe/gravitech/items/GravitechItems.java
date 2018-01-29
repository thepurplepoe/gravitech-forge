package thepurplepoe.gravitech.items;

import net.minecraft.item.Item;
import net.minecraft.util.registry.RegistrySimple;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class GravitechItems {
	private static RegistrySimple<String, IRegistrableItem> itemRegister = new RegistrySimple<String, IRegistrableItem>();
	
	public static IRegistrableItem advancedChainsaw;
	public static IRegistrableItem advancedDrill;
	public static IRegistrableItem advancedElectricJetpack;
	public static IRegistrableItem advancedLappack;
	public static IRegistrableItem advancedNanoChestplate;
	public static IRegistrableItem crafting;
	public static IRegistrableItem graviChestplate;
	public static IRegistrableItem graviTool;
	public static IRegistrableItem tacticalLaser;
	public static IRegistrableItem ultimateLappack;
	public static IRegistrableItem vajra;
	
	public static void setupItems() {
		
	}
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		for (IRegistrableItem i : itemRegister) {
			i.registerModel();
			event.getRegistry().register((Item)advancedChainsaw);
		}
	}
}
