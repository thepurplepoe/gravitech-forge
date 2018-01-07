/*
 * Decompiled with CFR 0_124.
 * 
 * Could not load the following classes:
 *  ic2.api.item.IC2Items
 *  ic2.api.recipe.ICraftingRecipeManager
 *  ic2.api.recipe.Recipes
 *  ic2.core.block.state.IIdProvider
 *  ic2.core.recipe.AdvRecipe
 *  ic2.core.ref.IItemModelProvider
 *  ic2.core.util.StackUtil
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.crafting.CraftingManager
 *  net.minecraft.item.crafting.IRecipe
 */
package thepurplepoe.gravitech;

import java.util.Iterator;

import ic2.api.item.IC2Items;
import ic2.core.recipe.AdvRecipe;
import ic2.core.util.StackUtil;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import thepurplepoe.gravitech.items.ItemCraftingThings;

final class Recipes {
    Recipes() {
    }

    static void addCraftingRecipes() {
        Recipes.addShapedRecipe(Recipes.expandStack(GS_Items.CRAFTING.getItemStack(ItemCraftingThings.CraftingTypes.SUPERCONDUCTOR_COVER), 3), new Object[]{"APA", "CCC", "APA", Character.valueOf('A'), IC2Items.getItem((String)"crafting", (String)"alloy"), Character.valueOf('P'), IC2Items.getItem((String)"crafting", (String)"iridium"), Character.valueOf('C'), IC2Items.getItem((String)"crafting", (String)"carbon_plate")});
        Recipes.addShapedRecipe(Recipes.expandStack(GS_Items.CRAFTING.getItemStack(ItemCraftingThings.CraftingTypes.SUPERCONDUCTOR), 3), new Object[]{"SSS", "CGC", "SSS", Character.valueOf('S'), GS_Items.CRAFTING.getItemStack(ItemCraftingThings.CraftingTypes.SUPERCONDUCTOR_COVER), Character.valueOf('G'), "ingotGold", Character.valueOf('C'), IC2Items.getItem((String)"cable", (String)"type:glass,insulation:0")});
        Recipes.addShapedRecipe(GS_Items.CRAFTING.getItemStack(ItemCraftingThings.CraftingTypes.COOLING_CORE), new Object[]{"CSC", "HPH", "CSC", Character.valueOf('C'), IC2Items.getItem((String)"hex_heat_storage"), Character.valueOf('S'), IC2Items.getItem((String)"advanced_heat_exchanger"), Character.valueOf('H'), IC2Items.getItem((String)"heat_plating"), Character.valueOf('P'), IC2Items.getItem((String)"crafting", (String)"iridium")});
        Recipes.addShapedRecipe(GS_Items.CRAFTING.getItemStack(ItemCraftingThings.CraftingTypes.GRAVITATION_ENGINE), new Object[]{"TST", "CHC", "TST", Character.valueOf('T'), IC2Items.getItem((String)"te", (String)"tesla_coil"), Character.valueOf('S'), GS_Items.CRAFTING.getItemStack(ItemCraftingThings.CraftingTypes.SUPERCONDUCTOR), Character.valueOf('C'), GS_Items.CRAFTING.getItemStack(ItemCraftingThings.CraftingTypes.COOLING_CORE), Character.valueOf('H'), IC2Items.getItem((String)"te", (String)"hv_transformer")});
        if (Config.canCraftAdvLappack) {
            Recipes.addShapedRecipe(new ItemStack(GS_Items.ADVANCED_LAPPACK.getInstance()), new Object[]{"P", "C", "E", Character.valueOf('P'), StackUtil.copyWithWildCard((ItemStack)IC2Items.getItem((String)"energy_pack")), Character.valueOf('C'), IC2Items.getItem((String)"crafting", (String)"advanced_circuit"), Character.valueOf('E'), StackUtil.copyWithWildCard((ItemStack)IC2Items.getItem((String)"energy_crystal"))});
        }
        if (Config.canCraftAdvJetpack) {
            Recipes.addShapedRecipe(GS_Items.CRAFTING.getItemStack(ItemCraftingThings.CraftingTypes.ENGINE_BOOSTER), new Object[]{"GAG", "COC", "AVA", Character.valueOf('G'), Items.GLOWSTONE_DUST, Character.valueOf('A'), IC2Items.getItem((String)"crafting", (String)"alloy"), Character.valueOf('C'), IC2Items.getItem((String)"crafting", (String)"advanced_circuit"), Character.valueOf('O'), IC2Items.getItem((String)"upgrade", (String)"overclocker"), Character.valueOf('V'), IC2Items.getItem((String)"advanced_heat_vent")});
            Recipes.addShapedRecipe(new ItemStack(GS_Items.ADVANCED_JETPACK.getInstance()), new Object[]{"CJC", "BLB", "GAG", Character.valueOf('C'), IC2Items.getItem((String)"crafting", (String)"carbon_plate"), Character.valueOf('J'), StackUtil.copyWithWildCard((ItemStack)IC2Items.getItem((String)"jetpack_electric")), Character.valueOf('B'), GS_Items.CRAFTING.getItemStack(ItemCraftingThings.CraftingTypes.ENGINE_BOOSTER), Character.valueOf('L'), StackUtil.copyWithWildCard((ItemStack)new ItemStack(GS_Items.ADVANCED_LAPPACK.getInstance())), Character.valueOf('G'), IC2Items.getItem((String)"cable", (String)"type:glass,insulation:0"), Character.valueOf('A'), IC2Items.getItem((String)"crafting", (String)"advanced_circuit")});
        }
        if (Config.canCraftAdvNano) {
            Recipes.addShapedRecipe(new ItemStack(GS_Items.ADVANCED_NANO_CHESTPLATE.getInstance()), new Object[]{"CJC", "CNC", "GAG", Character.valueOf('C'), IC2Items.getItem((String)"crafting", (String)"carbon_plate"), Character.valueOf('J'), StackUtil.copyWithWildCard((ItemStack)new ItemStack(GS_Items.ADVANCED_JETPACK.getInstance())), Character.valueOf('N'), StackUtil.copyWithWildCard((ItemStack)IC2Items.getItem((String)"nano_chestplate")), Character.valueOf('G'), IC2Items.getItem((String)"cable", (String)"type:glass,insulation:0"), Character.valueOf('A'), IC2Items.getItem((String)"crafting", (String)"advanced_circuit")});
        }
        if (Config.canCraftUltiLappack) {
            Recipes.addShapedRecipe(new ItemStack(GS_Items.ULTIMATE_LAPPACK.getInstance()), new Object[]{"CPC", "CLC", "CSC", Character.valueOf('C'), StackUtil.copyWithWildCard((ItemStack)IC2Items.getItem((String)"lapotron_crystal")), Character.valueOf('P'), IC2Items.getItem((String)"crafting", (String)"iridium"), Character.valueOf('L'), StackUtil.copyWithWildCard((ItemStack)IC2Items.getItem((String)"lappack")), Character.valueOf('S'), GS_Items.CRAFTING.getItemStack(ItemCraftingThings.CraftingTypes.SUPERCONDUCTOR)});
            Recipes.addShapedRecipe(new ItemStack(GS_Items.ULTIMATE_LAPPACK.getInstance()), new Object[]{"CPC", "CLC", "CSC", Character.valueOf('C'), StackUtil.copyWithWildCard((ItemStack)IC2Items.getItem((String)"lapotron_crystal")), Character.valueOf('P'), IC2Items.getItem((String)"crafting", (String)"iridium"), Character.valueOf('L'), StackUtil.copyWithWildCard((ItemStack)new ItemStack(GS_Items.ADVANCED_LAPPACK.getInstance())), Character.valueOf('S'), GS_Items.CRAFTING.getItemStack(ItemCraftingThings.CraftingTypes.SUPERCONDUCTOR)});
        }
        if (Config.canCraftGravi) {
            Recipes.addShapedColourRecipe(new ItemStack(GS_Items.GRAVI_CHESTPLATE.getInstance()), new Object[]{"SAS", "DBD", "SCS", Character.valueOf('S'), GS_Items.CRAFTING.getItemStack(ItemCraftingThings.CraftingTypes.SUPERCONDUCTOR), Character.valueOf('A'), StackUtil.copyWithWildCard((ItemStack)IC2Items.getItem((String)"quantum_chestplate")), Character.valueOf('D'), GS_Items.CRAFTING.getItemStack(ItemCraftingThings.CraftingTypes.GRAVITATION_ENGINE), Character.valueOf('B'), IC2Items.getItem((String)"te", (String)"hv_transformer"), Character.valueOf('C'), StackUtil.copyWithWildCard((ItemStack)new ItemStack(GS_Items.ULTIMATE_LAPPACK.getInstance()))});
        }
        if (Config.canCraftAdvDrill) {
            Recipes.addShapedRecipe(new ItemStack(GS_Items.ADVANCED_DRILL.getInstance()), new Object[]{"ODO", "COC", Character.valueOf('O'), IC2Items.getItem((String)"upgrade", (String)"overclocker"), Character.valueOf('D'), StackUtil.copyWithWildCard((ItemStack)IC2Items.getItem((String)"diamond_drill")), Character.valueOf('C'), IC2Items.getItem((String)"crafting", (String)"advanced_circuit")});
        }
        if (Config.canCraftAdvChainsaw) {
            Recipes.addShapedRecipe(new ItemStack(GS_Items.ADVANCED_CHAINSAW.getInstance()), new Object[]{" D ", "OCO", "AOA", Character.valueOf('D'), "gemDiamond", Character.valueOf('O'), IC2Items.getItem((String)"upgrade", (String)"overclocker"), Character.valueOf('C'), StackUtil.copyWithWildCard((ItemStack)IC2Items.getItem((String)"chainsaw")), Character.valueOf('A'), IC2Items.getItem((String)"crafting", (String)"advanced_circuit")});
        }
        if (Config.canCraftGraviTool) {
            Recipes.addShapedRecipe(new ItemStack(GS_Items.GRAVITOOL.getInstance()), new Object[]{"PHP", "AEA", "WCT", Character.valueOf('P'), IC2Items.getItem((String)"crafting", (String)"carbon_plate"), Character.valueOf('H'), StackUtil.copyWithWildCard((ItemStack)IC2Items.getItem((String)"electric_hoe")), Character.valueOf('A'), IC2Items.getItem((String)"crafting", (String)"alloy"), Character.valueOf('E'), StackUtil.copyWithWildCard((ItemStack)IC2Items.getItem((String)"energy_crystal")), Character.valueOf('W'), StackUtil.copyWithWildCard((ItemStack)IC2Items.getItem((String)"electric_wrench")), Character.valueOf('C'), IC2Items.getItem((String)"crafting", (String)"advanced_circuit"), Character.valueOf('T'), StackUtil.copyWithWildCard((ItemStack)IC2Items.getItem((String)"electric_treetap"))});
        }
        if (Config.canCraftVajra) {
            Recipes.addShapedRecipe(GS_Items.CRAFTING.getItemStack(ItemCraftingThings.CraftingTypes.MAGNETRON), new Object[]{"ICI", "CSC", "ICI", Character.valueOf('I'), "plateIron", Character.valueOf('C'), "plateCopper", Character.valueOf('S'), GS_Items.CRAFTING.getItemStack(ItemCraftingThings.CraftingTypes.SUPERCONDUCTOR)});
            Recipes.addShapedRecipe(GS_Items.CRAFTING.getItemStack(ItemCraftingThings.CraftingTypes.VAJRA_CORE), new Object[]{" M ", "PTP", "SHS", Character.valueOf('M'), GS_Items.CRAFTING.getItemStack(ItemCraftingThings.CraftingTypes.MAGNETRON), Character.valueOf('P'), IC2Items.getItem((String)"crafting", (String)"iridium"), Character.valueOf('T'), IC2Items.getItem((String)"te", (String)"tesla_coil"), Character.valueOf('S'), GS_Items.CRAFTING.getItemStack(ItemCraftingThings.CraftingTypes.SUPERCONDUCTOR), Character.valueOf('H'), IC2Items.getItem((String)"te", (String)"hv_transformer")});
            Recipes.addShapedRecipe(new ItemStack(GS_Items.VAJRA.getInstance()), new Object[]{"PEP", "CVC", "ALA", Character.valueOf('P'), "plateIron", Character.valueOf('E'), StackUtil.copyWithWildCard((ItemStack)IC2Items.getItem((String)"energy_crystal")), Character.valueOf('C'), IC2Items.getItem((String)"crafting", (String)"carbon_plate"), Character.valueOf('V'), GS_Items.CRAFTING.getItemStack(ItemCraftingThings.CraftingTypes.VAJRA_CORE), Character.valueOf('A'), IC2Items.getItem((String)"crafting", (String)"alloy"), Character.valueOf('L'), StackUtil.copyWithWildCard((ItemStack)IC2Items.getItem((String)"lapotron_crystal"))});
        }
    }

    static void changeQuantumRecipe() {
        assert (Config.shouldReplaceQuantum);
        Item quantumSuit = IC2Items.getItem((String)"quantum_chestplate").getItem();
        Iterator it = CraftingManager.getInstance().getRecipeList().iterator();
        while (it.hasNext()) {
            IRecipe recipe = (IRecipe)it.next();
            if (!StackUtil.checkItemEquality((ItemStack)recipe.getRecipeOutput(), (Item)quantumSuit) || !(recipe instanceof AdvRecipe)) continue;
            it.remove();
            break;
        }
        Recipes.addShapedRecipe(IC2Items.getItem((String)"quantum_chestplate"), new Object[]{"ANA", "ILI", "IAI", Character.valueOf('N'), StackUtil.copyWithWildCard((ItemStack)new ItemStack(GS_Items.ADVANCED_NANO_CHESTPLATE.getInstance())), Character.valueOf('A'), IC2Items.getItem((String)"crafting", (String)"alloy"), Character.valueOf('I'), IC2Items.getItem((String)"crafting", (String)"iridium"), Character.valueOf('L'), StackUtil.copyWithWildCard((ItemStack)IC2Items.getItem((String)"lapotron_crystal"))});
    }

    private static ItemStack expandStack(ItemStack stack, int newSize) {
        return new ItemStack(stack.getItem(), newSize, stack.getItemDamage());
    }

    private static /* varargs */ void addShapedRecipe(ItemStack output, Object ... inputs) {
        ic2.api.recipe.Recipes.advRecipes.addRecipe(output, inputs);
    }

    private static /* varargs */ void addShapedColourRecipe(ItemStack output, Object ... inputs) {
        new ColourCarryingRecipe(output, inputs).add();
    }
}

