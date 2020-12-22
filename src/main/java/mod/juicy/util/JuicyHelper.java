package mod.juicy.util;

import java.util.Vector;

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
			if(((TankSlaveTile) tile).getController() != null) {
			TankControllerTile controllerTile = (TankControllerTile) worldIn.getTileEntity(((TankSlaveTile) tile).getController());
			if(controllerTile != null)
				return LazyOptional.of(()->controllerTile);
			}
		}
		return LazyOptional.empty();
	}
	
	public static int[][] posVectorToArray(Vector<BlockPos> pVector) {
		int[] vectX = new int[pVector.size()];
		int[] vectY = new int[pVector.size()];
		int[] vectZ = new int[pVector.size()];
		for(int i=0;i<pVector.size();i++) {
			vectX[i] = pVector.get(i).getX();
			vectY[i] = pVector.get(i).getY();
			vectZ[i] = pVector.get(i).getZ();
		}
		int[][] outarray = {vectX,vectY,vectZ};
		return outarray;
	}
	
	public static Vector<BlockPos> posArraytoVector(int[][] pArray){
		Vector<BlockPos> vect = new Vector<BlockPos>();
		for(int i=0;i<pArray[0].length;i++) {
			vect.add(new BlockPos(pArray[0][i], pArray[1][i], pArray[2][i]));
		}
		return vect;
	}
}
