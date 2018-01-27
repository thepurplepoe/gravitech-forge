package thepurplepoe.gravitech;

import org.apache.logging.log4j.Logger;

import ic2.core.IC2;
import ic2.core.init.Localization;
import ic2.core.item.armor.jetpack.JetpackAttachmentRecipe;
import ic2.core.item.tool.EntityMiningLaser;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentBase;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
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
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import thepurplepoe.gravitech.entity.EntityAdvancedMiningLaser;
import thepurplepoe.gravitech.items.GravitechItems;
import thepurplepoe.gravitech.renders.GravitechOverlay;
import thepurplepoe.gravitech.renders.PrettyUtil;

@Mod(modid="gravitech", name="Gravitech", /*dependencies="required-after:IC2;",*/ version="3.0.1")
public final class Gravitech {
    public static final String MODID = "gravitech";
    @Instance
    public static Gravitech instance;
    
	@SidedProxy(clientSide = "thepurplepoe.gravitech.ClientProxy", serverSide = "thepurplepoe.gravitech.CommonProxy")
	public static CommonProxy proxy;
	
    public static Logger log;

    @EventHandler
    public void load(FMLPreInitializationEvent event) {
        log = event.getModLog();
        Config.loadConfig(event.getSuggestedConfigurationFile(), event.getSide().isClient());
        GraviKeys.addFlyKey();

        
        GravitechItems.setup();
        this.registerJetpackBlacklist();
        
        EntityRegistry.registerModEntity(new ResourceLocation("gravitech", "advanced_mining_laser"), EntityAdvancedMiningLaser.class, (String)"advancedMiningLaser", (int)0, (Object)this, (int)160, (int)5, (boolean)true);
        
        //RecipeSorter.register((String)"gs:colourCarrying", ColourCarryingRecipe.class, (RecipeSorter.Category)RecipeSorter.Category.SHAPED, (String)"after:ic2:shaped");
        //RecipeSorter.register((String)"gs:graviDying", ColourDyingRecipe.class, (RecipeSorter.Category)RecipeSorter.Category.SHAPELESS, (String)"after:ic2:shapeless");
        //GameRegistry.addRecipe((IRecipe)new ColourDyingRecipe());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        if (event.getSide().isClient()) {
            new PrettyUtil();
            new GravitechOverlay();
        }
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        if (event.getSide().isClient()) {
            //GraviChestplateColourHandler.register();
        }
    }

    private void registerJetpackBlacklist() {
    	/*
    	JetpackAttachmentRecipe.blacklistedItems.add(GravitechItems.advancedElectricJetpack);
    	JetpackAttachmentRecipe.blacklistedItems.add(GravitechItems.advancedNanoChestplate);
    	JetpackAttachmentRecipe.blacklistedItems.add(GravitechItems.graviChestplate);
    	*/
    }

    public static /* varargs */ void messagePlayer(EntityPlayer player, String message, TextFormatting colour, Object ... args) {
        if (player.world.isRemote) {
            TextComponentBase msg = args.length > 0 ? new TextComponentTranslation(message, (Object[])Gravitech.getMessageComponents(args)) : new TextComponentString(Localization.translate((String)message));
            PrettyUtil.mc.ingameGUI.getChatGUI().printChatMessage(msg.setStyle(new Style().setColor(colour)));
        } else if (player instanceof EntityPlayerMP) {
            TextComponentBase msg = args.length > 0 ? new TextComponentTranslation(message, (Object[])Gravitech.getMessageComponents(args)) : new TextComponentString(Localization.translate((String)message));
            ((EntityPlayerMP)player).sendMessage(msg.setStyle(new Style().setColor(colour)));
        }
    }

    private static /* varargs */ ITextComponent[] getMessageComponents(Object ... args) {
        ITextComponent[] encodedArgs = new ITextComponent[args.length];
        for (int i = 0; i < args.length; ++i) {
            encodedArgs[i] = args[i] instanceof String && ((String)args[i]).startsWith("gravitech.") ? new TextComponentTranslation((String)args[i], new Object[0]) : new TextComponentString(args[i].toString());
        }
        return encodedArgs;
    }
    
	@EventBusSubscriber
	public static class Registration {
		@SubscribeEvent
		public static void registerItems(RegistryEvent.Register<Item> event) {
			GravitechItems.register(event.getRegistry());
		}
		
		@SubscribeEvent
		public static void registerBlocks(RegistryEvent.Register<Block> event) {
			//Blocks.register(event.getRegistry());
		}
		
		@SubscribeEvent
		public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
			//EntityEntry e = new EntityEntry(EntityProjectileBase.class, "ProjectileBase3");
			//e.setRegistryName("ProjectileBase2");
			//EntityRegistry.registerModEntity(e.getRegistryName(), EntityProjectileBase.class, "ProjectileBase", 5, CreeperTech.instance, 64, 1, true);
			//event.getRegistry().registerAll(e);
		}
		
	}
}


