package mod.juicy.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class ProbeProvider implements ICapabilitySerializable<CompoundNBT>{
	
	@CapabilityInject(IBacteriaCapability.class)
    public static final Capability<IBacteriaCapability> BACT_CAPABILITY = null;
	
	private IBacteriaCapability bacteria;
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if(cap == BACT_CAPABILITY){
			return (LazyOptional<T>) bacteria;
		}
		return null;
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT nbt = new CompoundNBT();
		nbt.put("Bacteria",BACT_CAPABILITY.writeNBT(bacteria, null));
		return null;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		BACT_CAPABILITY.readNBT(bacteria, null, ((CompoundNBT) nbt).get("Bacteria"));
	}


}
