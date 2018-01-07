/*
 * Decompiled with CFR 0_124.
 * 
 * Could not load the following classes:
 *  ic2.core.block.state.IIdProvider
 *  ic2.core.item.ItemMulti
 *  ic2.core.ref.ItemName
 *  net.minecraft.client.renderer.block.model.ModelResourceLocation
 *  net.minecraft.item.Item
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.client.model.ModelLoader
 *  net.minecraftforge.fml.common.registry.GameRegistry
 *  net.minecraftforge.fml.common.registry.IForgeRegistryEntry
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package thepurplepoe.gravitech.items;

import java.util.Locale;

import ic2.core.block.state.IIdProvider;
import ic2.core.item.ItemMulti;
import ic2.core.ref.ItemName;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistryEntry;
import thepurplepoe.gravitech.items.ItemCraftingThings.CraftingTypes;

public class ItemCraftingThings
extends ItemMulti<CraftingTypes> {
    protected static final String NAME = "crafting";

    public ItemCraftingThings() {
        super(null, CraftingTypes.class);
        ((ItemCraftingThings)GameRegistry.register((IForgeRegistryEntry)this, (ResourceLocation)new ResourceLocation("gravisuite", "crafting"))).setUnlocalizedName("crafting");
    }

    @SideOnly(value=Side.CLIENT)
    protected void registerModel(int meta, ItemName name, String extraName) {
        ModelLoader.setCustomModelResourceLocation((Item)this, (int)meta, (ModelResourceLocation)new ModelResourceLocation("gravisuite:crafting/" + CraftingTypes.getFromID(meta).getName(), null));
    }

    public String getUnlocalizedName() {
        return "gravisuite." + super.getUnlocalizedName().substring(4);
    }

    public static enum CraftingTypes implements IIdProvider
    {
        SUPERCONDUCTOR_COVER(0),
        SUPERCONDUCTOR(1),
        COOLING_CORE(2),
        GRAVITATION_ENGINE(3),
        MAGNETRON(4),
        VAJRA_CORE(5),
        ENGINE_BOOSTER(6);
        
        private final String name;
        private final int ID;
        private static final CraftingTypes[] VALUES;

        private CraftingTypes(int ID) {
            this.name = this.name().toLowerCase(Locale.ENGLISH);
            this.ID = ID;
        }

        public String getName() {
            return this.name;
        }

        public int getId() {
            return this.ID;
        }

        public static CraftingTypes getFromID(int ID) {
            return VALUES[ID % VALUES.length];
        }

        static {
            VALUES = CraftingTypes.values();
        }
    }

}

