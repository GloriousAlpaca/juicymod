package mod.juicy.capability;

import javax.annotation.Nonnull;

import mod.juicy.Juicy;
import mod.juicy.fluid.FluidHolder;
import net.minecraft.client.Minecraft;
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

	public int addGas(int amount, boolean simulate) {
			int added = Math.min(amount, gascapacity-this.gas.getAmount());
			if(!simulate)
			this.gas = new FluidStack(FluidHolder.MOBGAS_STILL, gas.getAmount()+added);
			return added;
	}

	public int getGasAmount() {
		return gas.getAmount();
	}

	public int removeFluid(int amount, boolean simulate) {
		int removed = Math.min(fluid.getAmount(), amount);
		if(!simulate)
			this.fluid = new FluidStack(FluidHolder.MOBJUICE_STILL, fluid.getAmount()-removed);
		return removed;
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

	public void setTankCapacity(int tank, int pcapacity) {
		if (tank == 1)
			capacity = pcapacity;
		else if (tank == 2)
			gascapacity = pcapacity;
	}
	
	public void setIntake(int pIntake) {
		this.intake = pIntake;
	}
	
	public int getIntake() {
		return intake;
	}
	
	@Override
	public JuiceTank readFromNBT(CompoundNBT nbt) {
		FluidStack fluid = FluidStack.loadFluidStackFromNBT((CompoundNBT) nbt.get("fluid"));
		FluidStack gas = FluidStack.loadFluidStackFromNBT((CompoundNBT) nbt.get("gas"));
		setFluid(fluid);
		setGas(gas);
		setTankCapacity(1, nbt.getInt("fluidcap"));
		setTankCapacity(2, nbt.getInt("gascap"));
		this.intake = nbt.getInt("intake");
		return this;
	}

	@Override
	public CompoundNBT writeToNBT(CompoundNBT nbt) {
		Juicy.LOGGER.info("WRITE NBT JUICETANK!-----------------------------------------------------------------------------------------------------------");
		nbt.put("fluid", fluid.writeToNBT(new CompoundNBT()));
		nbt.put("gas", gas.writeToNBT(new CompoundNBT()));
		nbt.putInt("fluidcap", capacity);
		nbt.putInt("gascap", gascapacity);
		Juicy.LOGGER.info("WRITE NBT FLUID: "+fluid.getDisplayName()+fluid.getAmount());
		Juicy.LOGGER.info("WRITE NBT GAS: "+gas.getDisplayName()+gas.getAmount());
		nbt.putInt("intake", intake);
		return nbt;
	}
}
