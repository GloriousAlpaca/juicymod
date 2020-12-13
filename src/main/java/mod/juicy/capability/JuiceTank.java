package mod.juicy.capability;

import javax.annotation.Nonnull;

import mod.juicy.fluid.FluidHolder;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class JuiceTank extends FluidTank{

	public JuiceTank(int capacity) {
		super(capacity, (fstack)-> fstack.getFluid().isEquivalentTo(FluidHolder.MOBJUICE_STILL));
	}

	@Nonnull
    @Override
    public FluidStack drain(FluidStack resource, FluidAction action)
    {
        return FluidStack.EMPTY;
    }

    @Nonnull
    @Override
    public FluidStack drain(int maxDrain, FluidAction action)
    {
        return FluidStack.EMPTY;
    }
    
}
