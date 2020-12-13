package mod.juicy.block;

import mod.juicy.tile.TankTile;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class GasOutputBlock extends TankBlock{

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return new TankTile();
	}
	
}
