package mod.juicy.block;

import mod.juicy.tile.GutterTile;
import mod.juicy.tile.TankSlaveTile;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class GutterBlock extends Block{

	public GutterBlock() {
		super(AbstractBlock.Properties.create(Material.IRON).hardnessAndResistance(1.5f, 7f).harvestLevel(2));
	}

	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return new GutterTile();
	}
	
}
