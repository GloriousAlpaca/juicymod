package mod.juicy.tile;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;

public class ThermTile extends TankSlaveTile {
	double temperatureLow=10.;
	double temperatureHigh=20.;

	public ThermTile() {
		super(TileHolder.TILE_THERM_TYPE);
	}

	public void setTemp(double pTemp) {
		if (this.hasController()) {
			TankControllerTile tile = (TankControllerTile) this.getWorld().getTileEntity(controller);
			tile.setTemperature(pTemp);
		}
	}
	
	public void setHigh(double ptemperatureHigh) {
		this.temperatureHigh = ptemperatureHigh;
	}
	
	public void setLow(double ptemperatureLow) {
		this.temperatureLow = ptemperatureLow;
	}
	
	public double getHigh() {
		return this.temperatureHigh;
	}
	
	public double getLow() {
		return this.temperatureLow;
	}
	
	@Override
	public void read(BlockState state, CompoundNBT nbt) {
		super.read(state, nbt);
		this.temperatureHigh = nbt.getDouble("high");
		this.temperatureLow = nbt.getDouble("low");
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound) {
		CompoundNBT nbt = super.write(compound);
		nbt.putDouble("high", this.temperatureHigh);
		nbt.putDouble("low", temperatureLow);
		return nbt;
	}
	
	@Override
	public void handleUpdateTag(BlockState state, CompoundNBT tag) {
		super.read(state, tag);
	}
}
