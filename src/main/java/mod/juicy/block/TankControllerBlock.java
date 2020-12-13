package mod.juicy.block;

import java.time.LocalTime;
import java.util.Vector;

import javax.annotation.Nullable;

import mod.juicy.Juicy;
import mod.juicy.tile.TankControllerTile;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class TankControllerBlock extends TankBlock{
			
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return new TankControllerTile();
	}
	
	/**
	    * Called by ItemBlocks after a block is set in the world, to allow post-place logic
	    */
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer,ItemStack stack) 
	{
		if(!worldIn.isRemote) {
			int startTime = java.time.LocalTime.now().getNano();
			Juicy.LOGGER.info("MultiBlock start: "+startTime);
			Vector<BlockPos> marked = ((TankControllerTile) worldIn.getTileEntity(pos)).searchMultiBlock();
			Juicy.LOGGER.info("MultiBlock took : "+(LocalTime.now().getNano()-startTime));
			TankControllerTile tile = ((TankControllerTile) worldIn.getTileEntity(pos));
			tile.setMultiBlock(marked);
			tile.announceController(marked);
			tile.markDirty();
		}
	}
	
}
