package mod.juicy.tile;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;

public class ValveTile extends TankSlaveTile{
	int flowLow;
	int flowHigh;
	
	public ValveTile() {
		super(TileHolder.TILE_VALVE_TYPE);
		flowHigh = 10000;
		flowLow = 1000;
	}

	public void setFlow(int pFlow) {
		if (this.hasController()) {
			TankControllerTile tile = (TankControllerTile) this.getWorld().getTileEntity(controller);
			tile.setFlow(pFlow);
		}
	}
	
	public void setHigh(int pflowHigh) {
		this.flowHigh = pflowHigh;
	}
	
	public void setLow(int pflowLow) {
		this.flowLow = pflowLow;
	}
	
	public int getHigh() {
		return this.flowHigh;
	}
	
	public int getLow() {
		return this.flowLow;
	}
	
	@Override
	public void read(BlockState state, CompoundNBT nbt) {
		super.read(state, nbt);
		this.flowHigh = nbt.getInt("high");
		this.flowLow = nbt.getInt("low");
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound) {
		CompoundNBT nbt = super.write(compound);
		nbt.putInt("high", this.flowHigh);
		nbt.putInt("low", flowLow);
		return nbt;
	}
	
	@Override
	public void handleUpdateTag(BlockState state, CompoundNBT tag) {
		super.read(state, tag);
	}
	
}
