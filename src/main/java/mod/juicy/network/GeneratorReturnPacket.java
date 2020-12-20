package mod.juicy.network;

import java.util.function.Supplier;

import mod.juicy.gui.GeneratorScreen;
import mod.juicy.tile.GeneratorTile;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.network.NetworkEvent;

public class GeneratorReturnPacket {
	private int energy;
	private int gas;
	private int lastGenerated;
	
	public GeneratorReturnPacket(PacketBuffer buf) {
		this.gas = buf.readInt();
		this.energy = buf.readInt();
		this.lastGenerated = buf.readInt();
	}

	public GeneratorReturnPacket(GeneratorTile tile) {
		this.gas = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).map(tank->tank.getFluidInTank(1).getAmount()).orElse(0);
		this.energy = tile.getCapability(CapabilityEnergy.ENERGY).map(energy->energy.getEnergyStored()).orElse(0);
		this.lastGenerated = tile.getLastGenerated();
	}
	
	public void toBytes(PacketBuffer buf) {
		buf.writeInt(this.gas);
		buf.writeInt(this.energy);
		buf.writeInt(this.lastGenerated);
	}
	
	public boolean handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(()->{
			GeneratorScreen.gas = this.gas;
			GeneratorScreen.energy = this.energy;
			GeneratorScreen.lastGenerated = this.lastGenerated;
		});
		return true;
	}
	
}
