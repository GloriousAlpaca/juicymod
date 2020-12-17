package mod.juicy.tile;

import java.util.ArrayDeque;
import java.util.Vector;

import javax.annotation.Nullable;

import mod.juicy.Juicy;
import mod.juicy.block.TankBlock;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public class TankSlaveTile extends TileEntity {
	protected BlockPos controller;
	static Vector3i[] neighbours = { new Vector3i(1, 0, 0), new Vector3i(-1, 0, 0), new Vector3i(0, 1, 0),
			new Vector3i(0, -1, 0), new Vector3i(0, 0, 1), new Vector3i(0, 0, -1) };

	public TankSlaveTile(TileEntityType<? extends TankSlaveTile> type) {
		super(type);
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (controller != null) {
			Juicy.LOGGER.info("CONTROLLER NON NULL");
			return this.getWorld().getTileEntity(controller).getCapability(cap, side);
		} else
			return LazyOptional.empty();
	}

	public void setController(BlockPos pPos) {
		controller = pPos;
		Juicy.LOGGER.info("SET CONTROLLER FOR BLOCK AT: " + pos);
		Juicy.LOGGER.info("CONTROLLER IS AT: " + pPos);
	}

	public BlockPos getController() {
		return controller;
	}

	public boolean hasController() {
		return controller != null;
	}

	public BlockPos searchController() {
		Vector<BlockPos> marked = new Vector<BlockPos>();
		ArrayDeque<BlockPos> queue = new ArrayDeque<BlockPos>();
		queue.add(this.pos);
		while (!queue.isEmpty()) {
			BlockPos cPos = queue.getFirst();
			for (int i = 0; i < 6; i++) {
				BlockPos n = cPos.add(neighbours[i]);
				if (!marked.contains(n))
					if (this.getWorld().getBlockState(n).getBlock() instanceof TankBlock) {
						TileEntity tile = this.getWorld().getTileEntity(n);
						if (tile instanceof TankControllerTile) {
							return tile.getPos();
						} else if (tile instanceof TankSlaveTile && (((TankSlaveTile) tile).getController() != null)) {
							return ((TankSlaveTile) tile).getController();
						}
						marked.add(n);
						queue.add(n);
					}
			}
			queue.pop();
		}
		return null;
	}
	


	@Override
	public void read(BlockState state, CompoundNBT nbt) {
		super.read(state, nbt);
		int[] controllerCoords = nbt.getIntArray("controller");
		Juicy.LOGGER.info("ARRAY LENGTH: " + controllerCoords.length);
		if (controllerCoords.length == 3) {
			BlockPos cpos = new BlockPos(controllerCoords[0], controllerCoords[1], controllerCoords[2]);
			setController(cpos);
		}
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		CompoundNBT nbt = super.write(compound);
		if (controller != null) {
			int[] controllerCoords = { controller.getX(), controller.getY(), controller.getZ() };
			nbt.putIntArray("controller", controllerCoords);
		}
		return nbt;
	}

	//UPDATE PACKET--------------------------------------------------------------------
	/**
	 * Retrieves packet to send to the client whenever this Tile Entity is resynced
	 * via World.notifyBlockUpdate. For modded TE's, this packet comes back to you
	 * clientside in {@link #onDataPacket}
	 */
	@Override
	@Nullable
	public SUpdateTileEntityPacket getUpdatePacket() {
		return new SUpdateTileEntityPacket(this.getPos(), 1, this.getUpdateTag());
	}
	
	@Override
	public void onDataPacket(net.minecraft.network.NetworkManager net, net.minecraft.network.play.server.SUpdateTileEntityPacket pkt){
		super.onDataPacket(net, pkt);
		this.handleUpdateTag(this.getWorld().getBlockState(pkt.getPos()),pkt.getNbtCompound());
	}
	
	
	//UPDATE TAG--------------------------------------------------------------------
	/**
	 * Get an NBT compound to sync to the client with SPacketChunkData, used for
	 * initial loading of the chunk or when many blocks change at once. This
	 * compound comes back to you clientside in {@link handleUpdateTag}
	 */
	@Override
	public CompoundNBT getUpdateTag() {
		CompoundNBT nbt = super.getUpdateTag();
		if (controller != null) {
			int[] controllerCoords = { controller.getX(), controller.getY(), controller.getZ() };
			nbt.putIntArray("controller", controllerCoords);
		}
		return nbt;
	}
	
}
