package mod.juicy.capability;

import mod.juicy.Juicy;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class BacteriaCapability implements IBacteriaCapability {
	@CapabilityInject(IBacteriaCapability.class)
    public static final Capability<IBacteriaCapability> BACT_CAPABILITY = null;
	
	double bacteria=0.;
	double death=0;
	int limitBacteria;

	public BacteriaCapability(int pmaxBacteria) {
		limitBacteria = pmaxBacteria;
	}
		
	public int growBact(double temp, int juice) {
		double dbacteria = this.bacteria;
		if (dbacteria > limitBacteria) {
			death = death + 0.01 * Math.abs(dbacteria - juice);
			dbacteria -= 0.01 * death;
		} else if (bacteria > 0) {
			// Find the next x-Value on the sigmoid function
			double x = (-1. * Math.log(limitBacteria / dbacteria - 1.) + 6.) * temp / 12. + 1 / 20.;
			dbacteria = limitBacteria / (1 + Math.exp((-x + 1. / (1. / (temp / 2))) * 6 * (1 / (temp / 2))))- 0.001 * death;
			death -= 0.001 * death;
		}
		// There can't be a negative amount of Bacteria
		if (dbacteria < 1) {
			dbacteria = 0;
			death = 0;
		}

		return (int) Math.round(dbacteria);
	}
	
	@Override
	public double getBact() {
		return bacteria;
	}

	@Override
	public int getLimit() {
		// TODO Auto-generated method stub
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
		Juicy.LOGGER.info("WRITE NBT: "+ bacteria);
		nbt.putInt("limit", limitBacteria);
		return nbt;
	}
}
