package mod.juicy.block;

import mod.juicy.container.AlertContainer;
import mod.juicy.tile.AlertTile;
import mod.juicy.tile.TankControllerTile;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class AlertBlock extends TankBlock{

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return new AlertTile();
	}
	
	@Override
	public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
		boolean active = ((AlertTile) blockAccess.getTileEntity(pos)).getPower();
		return active ? 15 : 0;
	}
	
	@Override
	public int getStrongPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
		boolean active = ((AlertTile) blockAccess.getTileEntity(pos)).getPower();
		return active ? 15 : 0;
	}
	
	@Override
	public boolean canProvidePower(BlockState state) {
		return true;
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		if(!worldIn.isRemote) {
			TileEntity tile = worldIn.getTileEntity(pos);
			if(tile != null)
				if(tile instanceof AlertTile) {
					if(((AlertTile) tile).getController() != null) {
					TankControllerTile controller = (TankControllerTile) worldIn.getTileEntity(((AlertTile) tile).getController());
					if(controller != null) {
						controller.addAlert(pos);
					}
					}
				}		
		}
	}
	
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		super.onBlockHarvested(worldIn, pos, state, player);
		if (!worldIn.isRemote) {
			TileEntity tile = worldIn.getTileEntity(pos);
			if (tile != null)
				if (tile instanceof AlertTile) {
					if (((AlertTile) tile).getController() != null) {
						TankControllerTile controller = (TankControllerTile) worldIn.getTileEntity(((AlertTile) tile).getController());
						if (controller != null) {
							controller.removeAlert(pos);
						}
					}
				}
		}
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
							return new TranslationTextComponent("screen.juicy.alert");
						}

						@Override
						public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
							return new AlertContainer(i, worldIn, pos, playerInventory, playerEntity);
						}
					};
					NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, tile.getPos());
				}
			}
			return ActionResultType.func_233537_a_(worldIn.isRemote);
		}
		return ActionResultType.PASS;
	}
}
