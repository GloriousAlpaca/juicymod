package mod.juicy.container;

import mod.juicy.block.BlockHolder;
import mod.juicy.tile.TankControllerTile;
import mod.juicy.tile.TankSlaveTile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class TankContainer extends Container{
	private TileEntity tile;
	private BlockPos pos = null;
	private PlayerEntity playerEntity;
    private IItemHandler playerInventory;
    
	public TankContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
		super(ContainerHolder.TANK_CONTAINER, windowId);
		tile = world.getTileEntity(pos);
		this.pos = pos;
		this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
        //Setup Player Inventory Slots
        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
               this.addSlot(new Slot(playerInventory, j1 + l * 9 + 9, 8 + j1 * 18, 84 + l * 18));
            }
         }

         for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(playerInventory, i1, 8 + i1 * 18, 142));
         }
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		if (tile != null)
			if (tile instanceof TankSlaveTile) {
				if (((TankSlaveTile) tile).getController() != null)
					return isWithinUsableDistance(IWorldPosCallable.of(tile.getWorld(), tile.getPos()), playerIn,
							BlockHolder.TANK_BLOCK);
				else
					return false;
			} else if (tile instanceof TankControllerTile) {
				return isWithinUsableDistance(IWorldPosCallable.of(tile.getWorld(), tile.getPos()), playerIn,
						BlockHolder.TANK_CONTROLLER_BLOCK);
			}
		return false;
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		return ItemStack.EMPTY;
	}

	public BlockPos getPos() {
		return this.pos;
	}
	
}
