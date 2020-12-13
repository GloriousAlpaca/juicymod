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
	
	@ObjectHolder("gas_output")
	public static final Block GAS_OUTPUT_BLOCK = null;
	
}
