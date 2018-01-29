package thepurplepoe.gravitech.util;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import thepurplepoe.gravitech.Gravitech;

public class ConfigUtil {
	public static Configuration config;
	
	public static void loadConfig(File file) {
		config = new Configuration(file);
		
		try {
			config.load();
		} catch (Exception e) {
			Gravitech.logs.error("Gravitech config could not load");
		} finally {
			Gravitech.logs.info("Gravitech config loaded");
		}
	}
}
