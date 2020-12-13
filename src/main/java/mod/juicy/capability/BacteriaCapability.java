package mod.juicy.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class BacteriaCapability implements IBacteriaCapability {
	@CapabilityInject(IBacteriaCapability.class)
    public static final Capability<IBacteriaCapability> BACT_CAPABILITY = null;
	
	int bacteria=0;
	int maxBacteria;

	public BacteriaCapability(int pmaxBacteria) {
		maxBacteria = pmaxBacteria;
	}
		
	@Override
	public int getBact() {
		return bacteria;
	}

	@Override
	public int setBact(int value) {
		bacteria = Math.min(maxBacteria, value);
		return bacteria;
	}

	@Override
	public int receiveBact(int maxReceive, boolean simulate) {
		int added = Math.min(maxBacteria - bacteria, maxReceive);
		if (!simulate)
			bacteria += added;
		return added;
	}

	@Override
	public int extractBact(int maxExtract, boolean simulate) {
		int removed = Math.min(bacteria, maxExtract);
		if (!simulate)
			bacteria -= removed;
		return removed;
	}

}
