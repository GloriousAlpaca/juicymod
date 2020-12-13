package mod.juicy.block;

import javax.annotation.Nullable;

import mod.juicy.capability.BacteriaCapability;
import mod.juicy.tile.TankControllerTile;
import mod.juicy.tile.TankTile;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;

public class TankBlock extends Block{

	public TankBlock() {
		super(AbstractBlock.Properties.create(Material.IRON).hardnessAndResistance(1.5f, 7f).harvestLevel(1));
	}

	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return new TankTile();
	}
	
	/**
	* Called by ItemBlocks after a block is set in the world, to allow post-place logic
	*/
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer,ItemStack stack) 
	{
		if(!worldIn.isRemote())
		{
			TankTile tile = (TankTile) worldIn.getTileEntity(pos);
			BlockPos controllerTile = tile.searchController();
			if(controllerTile != null)
			{
			tile.setController(controllerTile);
			tile.markDirty();
			((TankControllerTile) worldIn.getTileEntity(tile.getController())).addtoMultiBlock(pos);
			}
		}
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if(!worldIn.isRemote)
		if(FluidUtil.interactWithFluidHandler(player, handIn, worldIn, hit.getPos(), hit.getFace()))
			return ActionResultType.SUCCESS;
		else if(player.getActiveItemStack().getCapability(BacteriaCapability.BACT_CAPABILITY).isPresent()){
			return ActionResultType.SUCCESS;
		}
		else {
			return ActionResultType.FAIL;
		}
			return ActionResultType.PASS;
    }
}
