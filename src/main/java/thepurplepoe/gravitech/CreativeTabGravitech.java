package thepurplepoe.gravitech;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import thepurplepoe.gravitech.items.GravitechItemsOLD;

public class CreativeTabGravitech extends CreativeTabs {

	public CreativeTabGravitech(String label) {
		super(label);
	}

	@Override
	public ItemStack getTabIconItem() {
		return GravitechItemsOLD.graviChestplate.getDefaultInstance();
	}

}
