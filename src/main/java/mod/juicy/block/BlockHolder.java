package mod.juicy.block;

import mod.juicy.Juicy;
import net.minecraft.block.Block;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Juicy.MODID)
public class BlockHolder {

	@ObjectHolder("juicegutter")
	public static final Block GUTTER_BLOCK = null;
	
	@ObjectHolder("tank_controller")
	public static final Block TANK_CONTROLLER_BLOCK = null;
	
	@ObjectHolder("tank")
	public static final Block TANK_BLOCK = null;
	
	@ObjectHolder("thermostat")
	public static final Block THERM_BLOCK = null;
	
	@ObjectHolder("valve")
	public static final Block VALVE_BLOCK = null;
	
	@ObjectHolder("generator")
	public static final Block GENERATOR_BLOCK = null;
	
	@ObjectHolder("alert")
	public static final Block ALERT_BLOCK = null;
}
