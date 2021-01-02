package mod.juicy.datagen;

import java.util.function.Consumer;

import mod.juicy.block.BlockHolder;
import mod.juicy.item.ItemHolder;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;

public class Recipes extends RecipeProvider {

	public Recipes(DataGenerator generatorIn) {
		super(generatorIn);
	}

	@Override
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
		ShapedRecipeBuilder.shapedRecipe(ItemHolder.PLATE, 16)
		.key('#', Items.DRIED_KELP).key('I', Items.IRON_INGOT).key('P', Items.PRISMARINE_SHARD)
		.patternLine("IPI")
		.patternLine("P#P")
		.patternLine("IPI")
		.addCriterion("has_prismarine", hasItem(Items.PRISMARINE_SHARD))
		.build(consumer);
		ShapedRecipeBuilder.shapedRecipe(ItemHolder.PROBE, 1)
		.key('#', ItemHolder.PLATE).key('I', Items.IRON_INGOT).key('K', Items.STONE_BUTTON).key('B', Items.GLASS_BOTTLE).key('N', Items.IRON_BARS)
		.patternLine(" IK")
		.patternLine("IB#")
		.patternLine("N# ")
		.addCriterion("has_plate", hasItem(ItemHolder.PLATE))
		.build(consumer);
		ShapedRecipeBuilder.shapedRecipe(BlockHolder.ALERT_BLOCK, 1)
		.key('#', ItemHolder.PLATE).key('L', Items.LEVER).key('O', Items.OBSIDIAN).key('D', Items.DISPENSER).key('R', Items.REDSTONE_LAMP)
		.patternLine("LRL")
		.patternLine("#D#")
		.patternLine("O#O")
		.addCriterion("has_plate", hasItem(ItemHolder.PLATE))
		.build(consumer);
		ShapedRecipeBuilder.shapedRecipe(BlockHolder.GENERATOR_BLOCK, 1)
		.key('#', ItemHolder.PLATE).key('C', Items.PRISMARINE_CRYSTALS).key('G', Items.GLASS).key('F', Items.BLAST_FURNACE).key('*', Items.FIREWORK_STAR)
		.patternLine("CGC")
		.patternLine("#F#")
		.patternLine("#*#")
		.addCriterion("has_plate", hasItem(ItemHolder.PLATE))
		.build(consumer);
		ShapedRecipeBuilder.shapedRecipe(BlockHolder.GUTTER_BLOCK, 1)
		.key('#', ItemHolder.PLATE).key('B', Items.BUCKET).key('I', Items.IRON_BARS).key('O', Items.OBSIDIAN)
		.patternLine("#I#")
		.patternLine("#B#")
		.patternLine("#O#")
		.addCriterion("has_plate", hasItem(ItemHolder.PLATE))
		.build(consumer);
		ShapedRecipeBuilder.shapedRecipe(BlockHolder.TANK_BLOCK, 1)
		.key('#', ItemHolder.PLATE).key('B', Items.BUCKET).key('D', Items.DIAMOND).key('O', Items.OBSIDIAN)
		.patternLine("O#D")
		.patternLine("#B#")
		.patternLine("D#O")
		.addCriterion("has_plate", hasItem(ItemHolder.PLATE))
		.build(consumer);
		ShapedRecipeBuilder.shapedRecipe(BlockHolder.TANK_CONTROLLER_BLOCK, 1)
		.key('#', ItemHolder.PLATE).key('H', Items.HEART_OF_THE_SEA).key('O', Items.OBSIDIAN)
		.key('G', Items.GOLD_INGOT).key('E', Items.EMERALD).key('D', Items.DIAMOND).key('L', Items.SEA_LANTERN)
		.patternLine("GED")
		.patternLine("#H#")
		.patternLine("OLO")
		.addCriterion("has_plate", hasItem(ItemHolder.PLATE))
		.build(consumer);
		ShapedRecipeBuilder.shapedRecipe(BlockHolder.THERM_BLOCK, 1)
		.key('#', ItemHolder.PLATE).key('B', Items.IRON_BLOCK).key('O', Items.OBSIDIAN).key('L', Items.SEA_LANTERN).key('I', Items.IRON_BARS)
		.patternLine("I#I")
		.patternLine("#B#")
		.patternLine("OLO")
		.addCriterion("has_plate", hasItem(ItemHolder.PLATE))
		.build(consumer);
		ShapedRecipeBuilder.shapedRecipe(BlockHolder.VALVE_BLOCK, 1)
		.key('#', ItemHolder.PLATE).key('B', Items.IRON_BLOCK).key('O', Items.OBSIDIAN).key('L', Items.SEA_LANTERN).key('F', Items.GLASS_BOTTLE)
		.patternLine("###")
		.patternLine("FBF")
		.patternLine("OLO")
		.addCriterion("has_plate", hasItem(ItemHolder.PLATE))
		.build(consumer);
	}
}
