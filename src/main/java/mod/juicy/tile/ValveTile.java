package mod.juicy.tile;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ValveTile extends TankSlaveTile{
	int flowlow;
	int flowhigh;
	
	public ValveTile() {
		super(TileHolder.TILE_VALVE_TYPE);
	}

	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos,
			boolean isMoving) {
		if (!worldIn.isRemote) {
			if (worldIn.isBlockPowered(pos)) {
				setFlow(flowhigh);
			} else {
				setFlow(flowlow);
			}
		}
	}

	public void setFlow(int pFlow) {
		if (this.hasController()) {
			TankControllerTile tile = (TankControllerTile) this.getWorld().getTileEntity(controller);
			tile.setFlow(pFlow);
		}
	}
	
}
