package thepurplepoe.gravitech;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thepurplepoe.gravitech.items.GravitechItemsOLD;
import thepurplepoe.gravitech.renders.PrettyUtilOLD;

@SideOnly(value=Side.CLIENT)
class GraviChestplateColourHandlerOLD
implements IItemColor {
    GraviChestplateColourHandlerOLD() {
    }

    static void register() {
        PrettyUtilOLD.mc.getItemColors().registerItemColorHandler((IItemColor)new GraviChestplateColourHandlerOLD(), new Item[]{GravitechItemsOLD.graviChestplate});
    }

    public int getColorFromItemstack(ItemStack stack, int tintIndex) {
        return tintIndex > 0 ? -1 : ((ItemArmor)stack.getItem()).getColor(stack);
    }

	@Override
	public int colorMultiplier(ItemStack stack, int tintIndex) {
		return 0;
	}
}

