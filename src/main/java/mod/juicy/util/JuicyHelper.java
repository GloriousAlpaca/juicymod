package mod.juicy.util;

import mod.juicy.tile.TankControllerTile;
import mod.juicy.tile.TankSlaveTile;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

public class JuicyHelper {
	public static Vector3i[] neighbours = { new Vector3i(1, 0, 0), new Vector3i(-1, 0, 0), new Vector3i(0, 1, 0),
			new Vector3i(0, -1, 0), new Vector3i(0, 0, 1), new Vector3i(0, 0, -1) };
	
	public static LazyOptional<TankControllerTile> getController(BlockPos pos, World worldIn) {
		TileEntity tile = worldIn.getTileEntity(pos);
		if(tile != null)
		if(tile instanceof TankControllerTile) {
			return LazyOptional.of(()->(TankControllerTile) tile);
		}
		else if(tile instanceof TankSlaveTile) {
			TankControllerTile controllerTile = (TankControllerTile) worldIn.getTileEntity(((TankSlaveTile) tile).getController());
			if(controllerTile != null)
				return LazyOptional.of(()->controllerTile);
		}
		return LazyOptional.empty();
	}
	
}
