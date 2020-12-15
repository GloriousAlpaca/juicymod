package mod.juicy.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

public class BacteriaStorage implements Capability.IStorage<IBacteriaCapability>{

	@Override
	public INBT writeNBT(Capability<IBacteriaCapability> capability, IBacteriaCapability instance, Direction side) {
		CompoundNBT nbt = new CompoundNBT();
		instance.writeToNBT(nbt);
		return nbt;
	}

	@Override
	public void readNBT(Capability<IBacteriaCapability> capability, IBacteriaCapability instance, Direction side, INBT nbt) {
		instance.readFromNBT((CompoundNBT) nbt);
	}

}
