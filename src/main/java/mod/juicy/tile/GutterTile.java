package mod.juicy.tile;

import mod.juicy.Juicy;
import mod.juicy.fluid.FluidHolder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class GutterTile extends TileEntity{
	FluidTank juice;
	
	public GutterTile() {
		super(TileHolder.TILE_GUTTER_TYPE);
		juice = new FluidTank(FluidAttributes.BUCKET_VOLUME, (fstack) -> fstack.getFluid().isEquivalentTo(FluidHolder.MOBJUICE_STILL));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return (LazyOptional<T>) LazyOptional.of(() -> juice);
		}
		return super.getCapability(cap, side);
	}
	
	public void addJuice(int max) {
		juice.fill(new FluidStack(FluidHolder.MOBJUICE_STILL, (int) Math.round(Math.random()*max)), FluidAction.EXECUTE);
		Juicy.LOGGER.info("FILLED GUTTERBLOCK: "+juice.getFluidAmount());
	}
	
}
