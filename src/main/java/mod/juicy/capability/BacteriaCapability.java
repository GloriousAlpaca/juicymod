package mod.juicy.capability;

public class BacteriaCapability implements IBacteriaCapability {
	int bacteria;
	int maxBacteria;

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
