package mod.juicy.capability;

import mod.juicy.Config;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class BacteriaCapability implements IBacteriaCapability {
	
    @CapabilityInject(IBacteriaCapability.class)
    public static Capability<IBacteriaCapability> BACT_CAPABILITY = null;
	
	double bacteria=0.;
	double death=0;
	int limitBacteria;

	public BacteriaCapability(int pmaxBacteria) {
		limitBacteria = pmaxBacteria;
	}

	/**
	 * Grow Bacteria in the Tank
	 * @param temp A Temperature between 0C and 40C
	 * @param juice How much Juice is in the Tank
	 * @return
	 */
	public double growBact(double temp, int juice) {
		double dbacteria = this.bacteria;
		double a = Math.exp(-0.13246*(temp-40));
		if (dbacteria > limitBacteria) {
			death = death + Config.TANK_DEATHPERTICK.get() * Math.abs(dbacteria - juice);
			dbacteria -= Config.TANK_DEATHMOD.get() * death;
		} else if (bacteria > 0) {
			// Find the next x-Value on the sigmoid function
			double x = (-1. * Math.log(limitBacteria / dbacteria - 1.) + 6.) * a / 12. + 1 / 20.;
			dbacteria = limitBacteria / (1 + Math.exp((-x + 1. / (1. / (a / 2))) * 6 * (1 / (a / 2))))- Config.TANK_RECOVERMOD.get() * death;
			death -= Config.TANK_RECOVERMOD.get() * death;
		}
		// There can't be a negative amount of Bacteria
		if (dbacteria < 1) {
			dbacteria = 0;
			death = 0;
		}
		return dbacteria;
	}
	
	@Override
	public double getBact() {
		return bacteria;
	}

	@Override
	public int getLimit() {
		return limitBacteria;
	}
	
	@Override
	public double setBact(double value) {
		bacteria = value;
		return bacteria;
	}
	
	public void setLimit(int value) {
		limitBacteria = value;
	}
	
	public double setDeath(double value) {
		death = value;
		return death;
	}
	
	public double addDeath(double value) {
		death += value;
		return death;
	}
	
	@Override
	public double receiveBact(double maxReceive, boolean simulate) {
		double added = Math.min(limitBacteria - bacteria, maxReceive);
		if (!simulate)
			bacteria += added;
		return added;
	}

	@Override
	public double extractBact(double maxExtract, boolean simulate) {
		double removed = Math.min(bacteria, maxExtract);
		if (!simulate)
			bacteria -= removed;
		return removed;
	}

	@Override
	public BacteriaCapability readFromNBT(CompoundNBT nbt) {
		bacteria = nbt.getDouble("amount");
		limitBacteria = nbt.getInt("limit");
		return this;
	}

	@Override
	public CompoundNBT writeToNBT(CompoundNBT nbt) {
		nbt.putDouble("amount", bacteria);
		nbt.putInt("limit", limitBacteria);
		return nbt;
	}
}
