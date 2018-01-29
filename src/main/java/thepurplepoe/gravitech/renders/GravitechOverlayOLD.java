package thepurplepoe.gravitech.renders;

import ic2.core.init.Localization;
import ic2.core.item.armor.jetpack.IJetpack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thepurplepoe.gravitech.items.ItemAdvancedElectricJetpack;

@SideOnly(value=Side.CLIENT)
public class GravitechOverlayOLD {
    public static boolean hudEnabled = true;
    public static byte hudPos = 1;

    public GravitechOverlayOLD() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    @SubscribeEvent
    public void onRender(TickEvent.RenderTickEvent event) {
        Minecraft mc = PrettyUtilOLD.mc;
        if (hudEnabled && mc.world != null && mc.inGameHasFocus && !mc.gameSettings.showDebugInfo) {
            Item item;
            ItemStack stack = mc.player.inventory.armorItemInSlot(EntityEquipmentSlot.CHEST.getIndex());
            if (stack == null || (item = stack.getItem()) == null) {
                return;
            }
            FontRenderer fontRenderer = mc.fontRenderer;
            String energyLevel = "";
            int energyLevelWidth = 0;
            String status = "";
            int statusWidth = 0;
            if (item instanceof ItemAdvancedElectricJetpack) {
                float chargeLevel = (float)((IJetpack)item).getChargeLevel(stack) * 100.0f;
                energyLevel = Localization.translate((String)"gravitech.message.energy", (Object[])new Object[]{GravitechOverlayOLD.getEnergyStatus(chargeLevel)});
                energyLevelWidth = fontRenderer.getStringWidth(Localization.translate((String)"gravitech.message.energy", (Object[])new Object[]{Integer.toString(Math.round(chargeLevel))}));
                if (ItemAdvancedElectricJetpack.isJetpackOn(stack)) {
                    String hoverModeStatus = ItemAdvancedElectricJetpack.isHovering(stack) ? Localization.translate((String)"gravitech.message.hover") : "";
                    status = (Object)TextFormatting.GREEN + Localization.translate((String)"gravitech.message.jetpackEngine", (Object[])new Object[]{new StringBuilder().append((Object)TextFormatting.YELLOW).append(hoverModeStatus).toString()});
                    statusWidth = fontRenderer.getStringWidth(Localization.translate((String)"gravitech.message.jetpackEngine", (Object[])new Object[]{hoverModeStatus}));
                }
            }
            if (!energyLevel.isEmpty()) {
                int fontHeight = fontRenderer.FONT_HEIGHT;
                int yOffset = 3;
                int xPos = 0;
                int yPos = 0;
                int xPos2 = 0;
                int yPos2 = 0;
                switch (hudPos) {
                    case 1: {
                        xPos = 2;
                        xPos2 = 2;
                        yPos = 2;
                        yPos2 = 5 + fontHeight;
                        break;
                    }
                    case 2: {
                        int width = new ScaledResolution(mc).getScaledWidth();
                        if (!status.isEmpty()) {
                            xPos = width - statusWidth - 2;
                        }
                        xPos2 = width - energyLevelWidth - 2;
                        yPos = 2;
                        yPos2 = 5 + fontHeight;
                        break;
                    }
                    case 3: {
                        xPos = 2;
                        xPos2 = 2;
                        yPos = new ScaledResolution(mc).getScaledWidth() - 2 - fontHeight;
                        yPos2 = yPos - 3 - fontHeight;
                        break;
                    }
                    case 4: {
                        ScaledResolution size = new ScaledResolution(mc);
                        int width = size.getScaledWidth();
                        if (!status.isEmpty()) {
                            xPos = width - statusWidth - 2;
                        }
                        xPos2 = width - energyLevelWidth - 2;
                        yPos = size.getScaledHeight() - 2 - fontHeight;
                        yPos2 = yPos - 3 - fontHeight;
                        break;
                    }
                    default: {
                        throw new IllegalStateException("Invalid value of HUD pos: expected 1-4, got " + hudPos + '!');
                    }
                }
                if (!status.isEmpty()) {
                    mc.ingameGUI.drawString(fontRenderer, status, xPos, yPos, 16777215);
                    mc.ingameGUI.drawString(fontRenderer, energyLevel, xPos2, yPos2, 16777215);
                } else {
                    mc.ingameGUI.drawString(fontRenderer, energyLevel, xPos2, yPos, 16777215);
                }
            }
        }
    }

    public static String getEnergyStatus(float energyStatus) {
        if (energyStatus <= 10.0f) {
            if (energyStatus <= 5.0f) {
                return (Object)TextFormatting.RED + Integer.toString(Math.round(energyStatus)) + '%';
            }
            return (Object)TextFormatting.GOLD + Integer.toString(Math.round(energyStatus)) + '%';
        }
        return Integer.toString(Math.round(energyStatus)) + '%';
    }
}

