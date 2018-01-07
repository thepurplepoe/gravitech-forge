package thepurplepoe.gravitech;

import org.apache.logging.log4j.Logger;

import ic2.core.init.Localization;
import ic2.core.item.armor.jetpack.JetpackAttachmentRecipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter;
import thepurplepoe.gravitech.renders.GravisuiteOverlay;
import thepurplepoe.gravitech.renders.PrettyUtil;

@Mod(modid="gravisuite", name="Gravitation Suite", dependencies="required-after:IC2@[2.6.99-ex10,);", version="3.0.1")
public final class Gravitech {
    public static final String MODID = "gravisuite";
    @Mod.Instance
    public static Gravitech instance;
    public static Logger log;

    @Mod.EventHandler
    public void load(FMLPreInitializationEvent event) {
        log = event.getModLog();
        Config.loadConfig(event.getSuggestedConfigurationFile(), event.getSide().isClient());
        GraviKeys.addFlyKey();
        GS_Items.buildItems(event.getSide());
        this.registerJetpackBlacklist();
        RecipeSorter.register((String)"gs:colourCarrying", ColourCarryingRecipe.class, (RecipeSorter.Category)RecipeSorter.Category.SHAPED, (String)"after:ic2:shaped");
        RecipeSorter.register((String)"gs:graviDying", ColourDyingRecipe.class, (RecipeSorter.Category)RecipeSorter.Category.SHAPELESS, (String)"after:ic2:shapeless");
        GameRegistry.addRecipe((IRecipe)new ColourDyingRecipe());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        Recipes.addCraftingRecipes();
        if (event.getSide().isClient()) {
            new PrettyUtil();
            new GravisuiteOverlay();
        }
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        if (Config.shouldReplaceQuantum) {
            Recipes.changeQuantumRecipe();
        }
        if (event.getSide().isClient()) {
            GraviChestplateColourHandler.register();
        }
    }

    private void registerJetpackBlacklist() {
        JetpackAttachmentRecipe.blacklistedItems.add(GS_Items.ADVANCED_JETPACK.getInstance());
        JetpackAttachmentRecipe.blacklistedItems.add(GS_Items.ADVANCED_NANO_CHESTPLATE.getInstance());
        JetpackAttachmentRecipe.blacklistedItems.add(GS_Items.GRAVI_CHESTPLATE.getInstance());
    }

    public static /* varargs */ void messagePlayer(EntityPlayer player, String message, TextFormatting colour, Object ... args) {
        if (player.worldObj.isRemote) {
            TextComponentString msg = args.length > 0 ? new TextComponentTranslation(message, (Object[])Gravitech.getMessageComponents(args)) : new TextComponentString(Localization.translate((String)message));
            PrettyUtil.mc.ingameGUI.getChatGUI().printChatMessage(msg.setStyle(new Style().setColor(colour)));
        } else if (player instanceof EntityPlayerMP) {
            TextComponentString msg = args.length > 0 ? new TextComponentTranslation(message, (Object[])Gravitech.getMessageComponents(args)) : new TextComponentString(Localization.translate((String)message));
            ((EntityPlayerMP)player).addChatMessage(msg.setStyle(new Style().setColor(colour)));
        }
    }

    private static /* varargs */ ITextComponent[] getMessageComponents(Object ... args) {
        ITextComponent[] encodedArgs = new ITextComponent[args.length];
        for (int i = 0; i < args.length; ++i) {
            encodedArgs[i] = args[i] instanceof String && ((String)args[i]).startsWith("gravisuite.") ? new TextComponentTranslation((String)args[i], new Object[0]) : new TextComponentString(args[i].toString());
        }
        return encodedArgs;
    }
}


