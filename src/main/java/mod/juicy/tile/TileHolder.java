package mod.juicy.tile;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder("juicy")
public class TileHolder {

	

	@ObjectHolder ("tank_controller")
    public static TileEntityType<TankControllerTile> TILE_TANK_CONTROLLER_TYPE = null;
	
	@ObjectHolder ("tank")
    public static TileEntityType<TankTile> TILE_TANK_TYPE = null;
	
	@ObjectHolder ("juicegutter")
    public static TileEntityType<GutterTile> TILE_GUTTER_TYPE = null; 
	
	@ObjectHolder ("thermostat")
    public static TileEntityType<ThermTile> TILE_THERM_TYPE =null; 
	
	@ObjectHolder ("valve")
	public static TileEntityType<ValveTile> TILE_VALVE_TYPE = null;
	
	@ObjectHolder ("generator")
	public static TileEntityType<GeneratorTile> TILE_GENERATOR_TYPE = null;
	
}
