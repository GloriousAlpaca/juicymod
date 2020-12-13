package mod.juicy.tile;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder("juicy")
public class TileHolder {

	@ObjectHolder ("tank_controller")
    public static TileEntityType<TankControllerTile> TILE_TANK_CONTROLLER_TYPE = null;
	
	@ObjectHolder ("tank")
    public static TileEntityType<TankControllerTile> TILE_TANK_TYPE = null;
	
	@ObjectHolder ("gas_output")
    public static TileEntityType<TankControllerTile> TILE_GAS_TYPE = null;
	
}
