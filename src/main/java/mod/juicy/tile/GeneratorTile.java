package mod.juicy.tile;

import mod.juicy.fluid.FluidHolder;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class GeneratorTile extends TileEntity implements ITickableTileEntity{
	FluidTank tank;
	EnergyStorage energy;
	int RFperMb;
	
	public GeneratorTile() {
		super(TileHolder.TILE_GENERATOR_TYPE);
		tank = new FluidTank(5*FluidAttributes.BUCKET_VOLUME, (fstack) -> fstack.getFluid().isEquivalentTo(FluidHolder.MOBGAS_STILL));
		energy = new EnergyStorage(20000);
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
		return LazyOptional.empty();
	}


	@Override
	public void tick() {
		if(!world.isRemote)
		{
			if(!tank.isEmpty()) {
				int drained = tank.drain(1, FluidAction.SIMULATE).getAmount();
				if(energy.receiveEnergy(RFperMb*drained, true)>0) {
					tank.drain(drained, FluidAction.EXECUTE);
					energy.receiveEnergy(RFperMb*drained, false);
				}
			}
		}
		
	}
	
}
