package mod.juicy.container;

import mod.juicy.block.BlockHolder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.InvWrapper;

public class GeneratorContainer extends Container {
	private BlockPos tilePos;
	private World world;

	public GeneratorContainer(int windowId, World worldIn, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
		super(ContainerHolder.GENERATOR_CONTAINER, windowId);
		tilePos = pos;
		this.world = worldIn;
		new InvWrapper(playerInventory);
		// Setup Player Inventory Slots
		for (int l = 0; l < 3; ++l) {
			for (int j1 = 0; j1 < 9; ++j1) {
				this.addSlot(new Slot(playerInventory, j1 + l * 9 + 9, 8 + j1 * 18, 84 + l * 18));
			}
		}

		for (int i1 = 0; i1 < 9; ++i1) {
			this.addSlot(new Slot(playerInventory, i1, 8 + i1 * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return isWithinUsableDistance(IWorldPosCallable.of(world, tilePos), playerIn, BlockHolder.GENERATOR_BLOCK);
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		//TODO FIX TRANSFERSTACKINSLOT GENERATOR
		return ItemStack.EMPTY;
	}

	public BlockPos getPos() {
		return this.tilePos;
	}

}
