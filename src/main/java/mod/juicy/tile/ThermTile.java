package mod.juicy.tile;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ThermTile extends TankSlaveTile {
	double temperaturelow;
	double temperaturehigh;

	public ThermTile() {
		super(TileHolder.TILE_THERM_TYPE);
	}

	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos,
			boolean isMoving) {
		if (!worldIn.isRemote) {
			if (worldIn.isBlockPowered(pos)) {
				setTemp(temperaturehigh);
			} else {
				setTemp(temperaturelow);
			}
		}
	}

	public void setTemp(double pTemp) {
		if (this.hasController()) {
			TankControllerTile tile = (TankControllerTile) this.getWorld().getTileEntity(controller);
			tile.setTemperature(pTemp);
		}
	}

}
