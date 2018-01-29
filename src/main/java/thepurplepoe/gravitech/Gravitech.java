package thepurplepoe.gravitech;

import org.apache.logging.log4j.Logger;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thepurplepoe.gravitech.items.GravitechItems;
import thepurplepoe.gravitech.util.ConfigUtil;
import thepurplepoe.gravitech.util.GravitechNames;

@Mod(modid=GravitechNames.modID, name=GravitechNames.modName, dependencies="required-after:ic2	;", version=GravitechNames.modVersion)
public class Gravitech {
	
	@Instance
	public static Gravitech instance;
	
	@SidedProxy(clientSide = "thepurplepoe.gravitech.ClientProxy", serverSide = "thepurplepoe.gravitech.CommonProxy")
	public static CommonProxy proxy;
	
	public static CreativeTabGravitech TabGravitech = new CreativeTabGravitech("Gravitech");
	
	public static Logger logs;
	
	// Registration
	@EventHandler
    public void preInit(FMLPreInitializationEvent event) {
		logs = event.getModLog();	
		
		GravitechItems.setupItems();
		
		GraviKeysOLD.addFlyKey();
		
		ConfigUtil.loadConfig(event.getSuggestedConfigurationFile());
		
		proxy.preInit();
	}
	
	// Client init stuff
	@EventHandler
	public void init(FMLInitializationEvent event) {
		
		proxy.init();
	}
	
	// Mod compat stuff
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
		proxy.postInit();
	}
}
