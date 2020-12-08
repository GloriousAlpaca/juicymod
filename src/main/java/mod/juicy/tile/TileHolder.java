package mod.juicy.tile;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder("juicy")
public class TileHolder {

	@ObjectHolder ("fermentation_tank")
    public static TileEntityType<TankTile> TILE_TANK_TYPE = null;
	
	@ObjectHolder ("juice_source")
    public static TileEntityType<TankTile> TILE_SOURCE_TYPE = null;
	
}
