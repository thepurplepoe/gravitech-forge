/*
 * Decompiled with CFR 0_124.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.common.config.Configuration
 *  net.minecraftforge.common.config.Property
 *  org.apache.logging.log4j.Logger
 */
package thepurplepoe.gravitech;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import thepurplepoe.gravitech.items.ItemVajra;
import thepurplepoe.gravitech.renders.GravitechOverlay;

final class Config {
    private static final String HUD = "HUD settings";
    private static final String CRAFTING = "Recipe settings";
    static boolean canCraftAdvJetpack;
    static boolean canCraftAdvNano;
    static boolean canCraftGravi;
    static boolean canCraftAdvLappack;
    static boolean canCraftUltiLappack;
    static boolean canCraftAdvDrill;
    static boolean canCraftAdvChainsaw;
    static boolean canCraftGraviTool;
    static boolean canCraftVajra;
    static boolean shouldReplaceQuantum;

    Config() {
    }

    static void loadConfig(File configFile, boolean client) {
        Gravitech.log.info("Loading GS Config from " + configFile.getAbsolutePath());
        Configuration config = new Configuration(configFile);
        try {
            config.load();
            if (client) {
                GravitechOverlay.hudEnabled = config.get("HUD settings", "enableHud", true).getBoolean(true);
                GravitechOverlay.hudPos = Config.getHudPosition(config);
            }
            canCraftAdvJetpack = !config.get("Recipe settings", "Disable Advanced Jetpack recipe", false).getBoolean(false);
            canCraftAdvNano = !config.get("Recipe settings", "Disable Advanced NanoChestPlate recipe", false).getBoolean(false);
            shouldReplaceQuantum = config.get("Recipe settings", "Change the Quantumsuit BodyArmour recipe", true).getBoolean(true);
            canCraftGravi = !config.get("Recipe settings", "Disable GraviChestPlate recipe", false).getBoolean(false);
            canCraftAdvLappack = !config.get("Recipe settings", "Disable AdvancedLappack recipe", false).getBoolean(false);
            canCraftUltiLappack = !config.get("Recipe settings", "Disable UltimateLappack recipe", false).getBoolean(false);
            canCraftAdvDrill = !config.get("Recipe settings", "Disable Advanced Dimond Drill recipe", false).getBoolean(false);
            canCraftAdvChainsaw = !config.get("Recipe settings", "Disable Advanced Chainsaw recipe", false).getBoolean(false);
            canCraftGraviTool = !config.get("Recipe settings", "Disable GraviTool recipe", false).getBoolean(false);
            canCraftVajra = !config.get("Recipe settings", "Disable Vajra recipe", false).getBoolean(false);
            ItemVajra.accurateEnabled = !config.get("Vajra settings", "Disable Vajra accurate mode", false).getBoolean(false);
        }
        catch (Exception e) {
            Gravitech.log.fatal("Fatal error reading config file.", (Throwable)e);
            throw new RuntimeException(e);
        }
        finally {
            if (config.hasChanged()) {
                config.save();
            }
        }
    }

    private static byte getHudPosition(Configuration config) {
        return (byte)((config.get("HUD settings", "hudPosition", 1).getInt(1) - 1) % 4 + 1);
    }
}

