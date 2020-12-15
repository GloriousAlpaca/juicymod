package mod.juicy.capability;

import javax.annotation.Nonnull;

import mod.juicy.Juicy;
import mod.juicy.fluid.FluidHolder;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class JuiceTank extends FluidTank {
	protected int gascapacity;
	protected FluidStack gas = FluidStack.EMPTY;
	protected int intake;
	
	public JuiceTank(int capacity, int pgascapacity) {
		super(capacity, (fstack) -> fstack.getFluid().isEquivalentTo(FluidHolder.MOBJUICE_STILL));
		this.gascapacity = pgascapacity;
		this.intake = capacity;
	}

	@Override
    public int fill(FluidStack resource, FluidAction action)
    {
        if (resource.isEmpty() || !isFluidValid(resource))
        {
            return 0;
        }
        if (action.simulate())
        {
            if (fluid.isEmpty())
            {
                return Math.min(Math.min(capacity, intake), resource.getAmount());
            }
            if (!fluid.isFluidEqual(resource))
            {
                return 0;
            }
            return Math.min(Math.min(capacity - fluid.getAmount(), intake), resource.getAmount());
        }
        if (fluid.isEmpty())
        {
            fluid = new FluidStack(resource, Math.min(Math.min(capacity, intake), resource.getAmount()));
            onContentsChanged();
            return fluid.getAmount();
        }
        if (!fluid.isFluidEqual(resource))
        {
            return 0;
        }
        int filled = Math.min(capacity - fluid.getAmount(), intake);

        if (resource.getAmount() < filled)
        {
            fluid.grow(resource.getAmount());
            filled = resource.getAmount();
        }
        else
        {
            fluid.grow(filled);
        }
        if (filled > 0)
            onContentsChanged();
        return filled;
    }
	
	@Nonnull
	@Override
	public FluidStack drain(FluidStack resource, FluidAction action) {
		if (resource.isEmpty() || !resource.isFluidEqual(gas)) {
			return FluidStack.EMPTY;
		}
		return drain(resource.getAmount(), action);
	}

	@Nonnull
	@Override
	public FluidStack drain(int maxDrain, FluidAction action) {
		int drained = maxDrain;
		if (gas.getAmount() < drained) {
			drained = gas.getAmount();
		}
		FluidStack stack = new FluidStack(gas, drained);
		if (action.execute() && drained > 0) {
			gas.shrink(drained);
			onContentsChanged();
		}
		return stack;
	}

	@Override
	public int getTanks() {

		return 2;
	}

	public void setGas(FluidStack stack) {
		this.gas = stack;
	}

	public void addGas(int amount) {
			Juicy.LOGGER.info("ADDING: "+amount+" GAS");
			this.gas = new FluidStack(FluidHolder.MOBGAS_STILL, Math.min(this.gas.getAmount()+amount, gascapacity));
	}

	public int getGasAmount() {
		return gas.getAmount();
	}

	public void removeFluid(int amount) {
		if(fluid.getAmount()>amount)
			this.fluid = new FluidStack(this.fluid, this.fluid.getAmount()-amount);
		else
			this.fluid = FluidStack.EMPTY;
	}

	@Nonnull
	@Override
	public FluidStack getFluidInTank(int tank) {

		if (tank == 1)
			return fluid;
		else
			return gas;
	}

	@Override
	public int getTankCapacity(int tank) {
		if (tank == 1)
			return capacity;
		else if (tank == 2)
			return gascapacity;
		else
			return 0;
	}

	public void setIntake(int pIntake) {
		this.intake = pIntake;
	}
	
	@Override
	public JuiceTank readFromNBT(CompoundNBT nbt) {
		FluidStack fluid = FluidStack.loadFluidStackFromNBT((CompoundNBT) nbt.get("fluid"));
		FluidStack gas = FluidStack.loadFluidStackFromNBT((CompoundNBT) nbt.get("gas"));
		setFluid(fluid);
		setGas(gas);
		this.intake = nbt.getInt("intake");
		Juicy.LOGGER.info("READ NBT FLUID: "+fluid.getDisplayName()+fluid.getAmount());
		Juicy.LOGGER.info("READ NBT GAS: "+gas.getDisplayName()+gas.getAmount());
		return this;
	}

	@Override
	public CompoundNBT writeToNBT(CompoundNBT nbt) {
		Juicy.LOGGER.info("WRITE NBT JUICETANK!-----------------------------------------------------------------------------------------------------------");
		nbt.put("fluid", fluid.writeToNBT(new CompoundNBT()));
		nbt.put("gas", gas.writeToNBT(new CompoundNBT()));
		Juicy.LOGGER.info("WRITE NBT FLUID: "+fluid.getDisplayName()+fluid.getAmount());
		Juicy.LOGGER.info("WRITE NBT GAS: "+gas.getDisplayName()+gas.getAmount());
		nbt.putInt("intake", intake);
		return nbt;
	}
}
