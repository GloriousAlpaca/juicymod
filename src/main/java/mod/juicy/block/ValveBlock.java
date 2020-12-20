package mod.juicy.block;

import mod.juicy.container.ValveContainer;
import mod.juicy.tile.ValveTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ValveBlock extends TankBlock{

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return new ValveTile();
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {
		if (!player.isCrouching()) {
			if (!worldIn.isRemote) {
				TileEntity tile = worldIn.getTileEntity(pos);
				if (tile != null) {
					INamedContainerProvider containerProvider = new INamedContainerProvider() {
						@Override
						public ITextComponent getDisplayName() {
							return new TranslationTextComponent("screen.juicy.valve");
						}

						@Override
						public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
							return new ValveContainer(i, worldIn, pos, playerInventory, playerEntity);
						}
					};
					NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, tile.getPos());
				}
			}
			return ActionResultType.func_233537_a_(worldIn.isRemote);
		}
		return ActionResultType.PASS;
	}
	
	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos,
			boolean isMoving) {
		if(!worldIn.isRemote) {
			TileEntity tile = worldIn.getTileEntity(pos);
			if(tile != null)
				if(tile instanceof ValveTile) {
					if (worldIn.isBlockPowered(pos)) {
						((ValveTile) tile).setFlow(((ValveTile) tile).getHigh());
					} else {
						((ValveTile) tile).setFlow(((ValveTile) tile).getLow());
					}
				}
		}
	}
	
}
