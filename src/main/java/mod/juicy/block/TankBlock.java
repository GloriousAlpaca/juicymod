package mod.juicy.block;

import java.util.Vector;

import javax.annotation.Nullable;

import mod.juicy.capability.BacteriaCapability;
import mod.juicy.capability.IBacteriaCapability;
import mod.juicy.container.TankContainer;
import mod.juicy.tile.TankControllerTile;
import mod.juicy.tile.TankSlaveTile;
import mod.juicy.tile.TankTile;
import mod.juicy.util.JuicyHelper;
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
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.network.NetworkHooks;

public class TankBlock extends Block {
    
	public TankBlock() {
		super(AbstractBlock.Properties.create(Material.IRON).hardnessAndResistance(1.5f, 1000f).harvestLevel(1).harvestTool(ToolType.PICKAXE).setRequiresTool()
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
				Vector<BlockPos> multiBlock = controller.searchMultiBlock();
				controller.setMultiBlock(multiBlock);
				controller.updateCapacity();
				controller.markDirty();
			}
		}
	}


	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		super.onBlockHarvested(worldIn, pos, state, player);
		if (!worldIn.isRemote) {
			TileEntity tile = worldIn.getTileEntity(pos);
			if (tile != null) {
				if (tile instanceof TankControllerTile) {
					((TankControllerTile) tile).renounceController();
				} else {
					BlockPos controllerPos = ((TankSlaveTile) tile).getController();
					if (controllerPos != null) {
						TankControllerTile controller = (TankControllerTile) worldIn.getTileEntity(controllerPos);
						if (controller != null) {
							Vector<BlockPos> oldMulti = controller.getMultiBlock();
							Vector<BlockPos> marked = new Vector<BlockPos>();
							marked.add(pos);
							controller.searchMultiBlock(marked);
							marked.remove(pos);
							controller.setMultiBlock(marked);
							controller.updateCapacity();
							controller.markDirty();
							oldMulti.removeAll(marked);
							oldMulti.remove(controllerPos);
							oldMulti.forEach(slavepos -> {
								TileEntity slave = worldIn.getTileEntity(slavepos);
								if (slave != null)
									if (slave instanceof TankSlaveTile)
										((TankSlaveTile) slave).setController(null);
							});
						}
					}
				}
			}
		}
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {
		if(worldIn.getTileEntity(pos) == null) {
			return ActionResultType.PASS;
		}
		else if(worldIn.isRemote()) {
	        if(player.isCrouching())
	        	return ActionResultType.PASS;
	        else
	        	return ActionResultType.SUCCESS;
		}
		else if (FluidUtil.interactWithFluidHandler(player, handIn, worldIn, hit.getPos(), hit.getFace())) {
			JuicyHelper.getController(pos, worldIn).ifPresent(controller -> controller.markDirty());
			return ActionResultType.CONSUME;
			
		} else if (player.getHeldItem(handIn).getCapability(BacteriaCapability.BACT_CAPABILITY).isPresent()) {
			
			LazyOptional<IBacteriaCapability> bacttile = worldIn.getTileEntity(pos).getCapability(BacteriaCapability.BACT_CAPABILITY);
			bacttile.ifPresent(cap -> {
				LazyOptional<IBacteriaCapability> playercap = player.getHeldItemMainhand()
						.getCapability(BacteriaCapability.BACT_CAPABILITY);
				double drained = playercap.map(bact -> bact.extractBact(bact.getBact(), false)).orElse(0.);
				cap.setBact(drained+cap.getBact());
			});
			JuicyHelper.getController(pos, worldIn).ifPresent(controller -> controller.markDirty());
			return ActionResultType.CONSUME;
			
		} else if (!player.isCrouching()) {
				TileEntity tile = worldIn.getTileEntity(pos);
				if (tile != null)
					if(JuicyHelper.getController(pos, worldIn).isPresent()) {
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
				}
			return ActionResultType.SUCCESS;
	}
		return ActionResultType.FAIL;
	}
	
}
