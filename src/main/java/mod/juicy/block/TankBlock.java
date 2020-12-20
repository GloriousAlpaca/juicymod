package mod.juicy.block;

import javax.annotation.Nullable;

import mod.juicy.capability.BacteriaCapability;
import mod.juicy.capability.IBacteriaCapability;
import mod.juicy.container.TankContainer;
import mod.juicy.tile.TankControllerTile;
import mod.juicy.tile.TankSlaveTile;
import mod.juicy.tile.TankTile;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.network.NetworkHooks;

public class TankBlock extends Block {
    
	public TankBlock() {
		super(AbstractBlock.Properties.create(Material.IRON).hardnessAndResistance(1.5f, 7f).harvestLevel(1)
				.sound(SoundType.METAL));
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new TankTile();
	}

	/**
	 * Called by ItemBlocks after a block is set in the world, to allow post-place
	 * logic
	 */
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer,
			ItemStack stack) {
		if (!worldIn.isRemote()) {
			TankSlaveTile tile = (TankSlaveTile) worldIn.getTileEntity(pos);
			BlockPos controllerTile = tile.searchController();
			if (controllerTile != null) {
				tile.setController(controllerTile);
				tile.markDirty();
				TankControllerTile controller = ((TankControllerTile) worldIn.getTileEntity(tile.getController()));
				controller.addtoMultiBlock(pos);
				controller.updateCapacity();
			}
		}
	}

	/*
	 * @Override public void onBlockHarvested(World worldIn, BlockPos pos,
	 * BlockState state, PlayerEntity player) { super.onBlockHarvested(worldIn, pos,
	 * state, player); TileEntity tile = worldIn.getTileEntity(pos); if(tile !=
	 * null) { if(tile instanceof TankControllerTile) { ((TankControllerTile)
	 * tile).renounceController(); } else { ((TankControllerTile)
	 * worldIn.getTileEntity(((TankSlaveTile)
	 * tile).getController())).removeFromMultiBlock(pos); } } }
	 */

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {
		// TODO Fix Bucket Interaction
		if (!worldIn.isRemote) {
			if (FluidUtil.interactWithFluidHandler(player, handIn, worldIn, hit.getPos(), hit.getFace())) {
				return ActionResultType.SUCCESS;
			} else if (player.getActiveItemStack().getCapability(BacteriaCapability.BACT_CAPABILITY).isPresent()) {
				LazyOptional<IBacteriaCapability> bacttile = worldIn.getTileEntity(pos)
						.getCapability(BacteriaCapability.BACT_CAPABILITY);
				bacttile.ifPresent(cap -> cap
						.receiveBact(player.getActiveItemStack().getCapability(BacteriaCapability.BACT_CAPABILITY)
								.orElseThrow(() -> new NullPointerException()).getBact(), true));
				return ActionResultType.SUCCESS;
			} else if(player.getActiveItemStack().equals(ItemStack.EMPTY)){
				TileEntity tile = worldIn.getTileEntity(pos);
				if (tile != null) {
					INamedContainerProvider containerProvider = new INamedContainerProvider() {
	                    @Override
	                    public ITextComponent getDisplayName() {
	                        return new TranslationTextComponent("screen.juicy.tank");
	                    }

	                    @Override
	                    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
	                        return new TankContainer(i, worldIn, pos, playerInventory, playerEntity);
	                    }
	                };
						NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, tile.getPos());
						return ActionResultType.SUCCESS;

				} else {
					return ActionResultType.FAIL;
				}
			}
		}
		return ActionResultType.PASS;
	}

}
