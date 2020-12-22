package mod.juicy.datagen;

import mod.juicy.block.BlockHolder;
import net.minecraft.data.DataGenerator;

public class BlockLoot extends BaseLootTableProvider{

	public BlockLoot(DataGenerator dataGeneratorIn) {
		super(dataGeneratorIn);
	}

	@Override
	public void registerTables() {
		lootTables.put(BlockHolder.ALERT_BLOCK, simpleBlockTable("alert", BlockHolder.ALERT_BLOCK));
		lootTables.put(BlockHolder.GENERATOR_BLOCK, simpleBlockTable("generator", BlockHolder.GENERATOR_BLOCK));
		lootTables.put(BlockHolder.GUTTER_BLOCK, simpleBlockTable("gutter", BlockHolder.GUTTER_BLOCK));
		lootTables.put(BlockHolder.TANK_BLOCK, simpleBlockTable("tank", BlockHolder.TANK_BLOCK));
		lootTables.put(BlockHolder.TANK_CONTROLLER_BLOCK, simpleBlockTable("tank_controller", BlockHolder.TANK_CONTROLLER_BLOCK));
		lootTables.put(BlockHolder.THERM_BLOCK, simpleBlockTable("thermostat", BlockHolder.THERM_BLOCK));
		lootTables.put(BlockHolder.VALVE_BLOCK, simpleBlockTable("valve", BlockHolder.VALVE_BLOCK));
	}
}
