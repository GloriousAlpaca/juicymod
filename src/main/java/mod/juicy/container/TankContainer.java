package mod.juicy.container;

import mod.juicy.block.BlockHolder;
import mod.juicy.tile.TankControllerTile;
import mod.juicy.tile.TankSlaveTile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class TankContainer extends Container{
	private TankControllerTile tile;
	private PlayerEntity playerEntity;
    private IItemHandler playerInventory;
    
	public TankContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
		super(ContainerHolder.TANK_CONTAINER, windowId);
		TileEntity tileEntity = world.getTileEntity(pos);
		if(tileEntity != null)
		if(tileEntity instanceof TankSlaveTile) {
			tile = (TankControllerTile) world.getTileEntity(((TankSlaveTile) tileEntity).getController());
		}
		else if(tileEntity instanceof TankControllerTile) {
			tile = (TankControllerTile) tileEntity;
		}
		this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
        layoutPlayerInventorySlots(10, 70);
	}

	@Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tile.getWorld(), tile.getPos()), playerEntity, BlockHolder.TANK_BLOCK);
    }

	/*
	 * Player Inventory Code
	 * ---------------------------------------------------------------------------------------------------------------------
	 */
    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }
    
}
