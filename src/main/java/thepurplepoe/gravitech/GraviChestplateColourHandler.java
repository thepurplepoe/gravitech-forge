package thepurplepoe.gravitech;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thepurplepoe.gravitech.items.GravitechItems;
import thepurplepoe.gravitech.renders.PrettyUtil;

@SideOnly(value=Side.CLIENT)
class GraviChestplateColourHandler
implements IItemColor {
    GraviChestplateColourHandler() {
    }

    static void register() {
        PrettyUtil.mc.getItemColors().registerItemColorHandler((IItemColor)new GraviChestplateColourHandler(), new Item[]{GravitechItems.graviChestplate});
    }

    public int getColorFromItemstack(ItemStack stack, int tintIndex) {
        return tintIndex > 0 ? -1 : ((ItemArmor)stack.getItem()).getColor(stack);
    }

	@Override
	public int colorMultiplier(ItemStack stack, int tintIndex) {
		return 0;
	}
}

