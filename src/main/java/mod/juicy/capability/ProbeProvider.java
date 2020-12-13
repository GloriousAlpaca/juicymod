package mod.juicy.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class ProbeProvider implements ICapabilitySerializable<CompoundNBT>{
	private IBacteriaCapability bacteria;
	
	public ProbeProvider() {
		bacteria = new BacteriaCapability(30);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if(cap == BacteriaCapability.BACT_CAPABILITY){
			return (LazyOptional<T>) LazyOptional.of(()->bacteria);
		}
		return LazyOptional.empty();
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT nbt = new CompoundNBT();
		nbt.put("amount",BacteriaCapability.BACT_CAPABILITY.writeNBT(bacteria, null));
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		BacteriaCapability.BACT_CAPABILITY.readNBT(bacteria, null, ((CompoundNBT) nbt).get("amount"));
	}


}
