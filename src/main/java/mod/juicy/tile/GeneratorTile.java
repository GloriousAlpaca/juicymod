package mod.juicy.tile;

import mod.juicy.Config;
import mod.juicy.fluid.FluidHolder;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class GeneratorTile extends TileEntity implements ITickableTileEntity{
	FluidTank tank;
	EnergyStorage energy;
	int gasPerTick;
	int rfLastTick;
	
	public GeneratorTile() {
		super(TileHolder.TILE_GENERATOR_TYPE);
		tank = new FluidTank(Config.GENERATOR_GASCAP.get(), (fstack) -> fstack.getFluid().isEquivalentTo(FluidHolder.MOBGAS_STILL));
		energy = new EnergyStorage(Config.GENERATOR_ENERGYCAP.get());
		gasPerTick = 1;
		rfLastTick = 0;
	}
	

	
	@SuppressWarnings("unchecked")
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return (LazyOptional<T>) LazyOptional.of(() -> tank);
		}
		else if(cap == CapabilityEnergy.ENERGY) {
			return (LazyOptional<T>) LazyOptional.of(() -> energy);
		}
		return super.getCapability(cap, side);
	}


	@Override
	public void tick() {
		if(!world.isRemote)
		{
			if(!tank.isEmpty()) {
				int gasPerTick = (int) Math.round(Config.GENERATOR_BURNMULT.get()*(float)tank.getFluidAmount()/(float)tank.getCapacity());
				int drained = tank.drain(gasPerTick, FluidAction.SIMULATE).getAmount();
				int generated = energy.receiveEnergy(Config.GENERATOR_RFPERGAS.get()*drained, true);
				if(generated>0) {
					tank.drain(generated/Config.GENERATOR_RFPERGAS.get(), FluidAction.EXECUTE);
					energy.receiveEnergy(generated, false);
				}
				rfLastTick = generated;
			}
		}
	}
	
	public int getLastGenerated() {
		return rfLastTick;
	}
	
}
