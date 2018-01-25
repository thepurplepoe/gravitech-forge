package thepurplepoe.gravitech.items;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemAdvancedLappack
extends ItemGeneralLappack {

	
    public ItemAdvancedLappack(String name) {
        super(name, 3000000.0, 30000.0, 3);
    }

    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.UNCOMMON;
    }
}

