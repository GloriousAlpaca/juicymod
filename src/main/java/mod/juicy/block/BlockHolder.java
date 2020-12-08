package mod.juicy.block;

import mod.juicy.Juicy;
import net.minecraft.block.Block;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Juicy.MODID)
public class BlockHolder {

	@ObjectHolder("juicegutter")
	public static final Block GUTTER_BLOCK = null;
	
	@ObjectHolder("fermentation_tank")
	public static final Block TANK_BLOCK = null;
}
