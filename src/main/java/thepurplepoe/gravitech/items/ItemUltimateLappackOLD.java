package thepurplepoe.gravitech.items;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemUltimateLappackOLD
extends ItemGeneralLappackOLD {
    public ItemUltimateLappackOLD(String name) {
        super(name, 60000000, 100000.0, 4);
    }

    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }
}

