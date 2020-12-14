package mod.juicy.capability;

import javax.annotation.Nonnull;

import mod.juicy.fluid.FluidHolder;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class JuiceTank extends FluidTank {
	int gascapacity;
	FluidStack gas = FluidStack.EMPTY;

	public JuiceTank(int capacity, int pgascapacity) {
		super(capacity, (fstack) -> fstack.getFluid().isEquivalentTo(FluidHolder.MOBJUICE_STILL));
		this.gascapacity = pgascapacity;
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
		if (gas.getAmount() + amount < gascapacity) {
			FluidStack next = new FluidStack(gas.getFluid(), gas.getAmount() + amount);
			gas = next;
		} else
			gas = new FluidStack(gas.getFluid(), gascapacity);
	}

	public int getGasAmount() {
		return gas.getAmount();
	}

	public void removeFluid(int amount) {
		if (fluid.getAmount() > amount) {
			FluidStack next = new FluidStack(fluid.getFluid(), fluid.getAmount() - amount);
			next.shrink(amount);
			fluid = next;
		} else
			fluid = FluidStack.EMPTY;
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

	@Override
	public FluidTank readFromNBT(CompoundNBT nbt) {

		FluidStack fluid = FluidStack.loadFluidStackFromNBT(nbt);
		setFluid(fluid);
		return this;
	}

	@Override
	public CompoundNBT writeToNBT(CompoundNBT nbt) {

		fluid.writeToNBT(nbt);

		return nbt;
	}
}
