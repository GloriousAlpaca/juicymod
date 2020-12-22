package mod.juicy.network;

import java.util.function.Supplier;

import mod.juicy.capability.BacteriaCapability;
import mod.juicy.gui.TankScreen;
import mod.juicy.tile.TankControllerTile;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.network.NetworkEvent;

public class TankReturnPacket {
	int gas;
	int gasCap;
	int juice;
	int juiceCap;
	int bacteria;
	int bacteriaCap;
	int intake;
	double temp;
	
	public TankReturnPacket(PacketBuffer buf) {
		this.gas = buf.readInt();
		this.gasCap = buf.readInt();
		this.juice = buf.readInt();
		this.juiceCap = buf.readInt();
		this.bacteria = buf.readInt();
		this.bacteriaCap = buf.readInt();
		this.intake = buf.readInt();
		this.temp = buf.readDouble();
	}

	public TankReturnPacket(TankControllerTile controller) {
		this.gas = controller.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).map(tank->tank.getFluidInTank(2).getAmount()).orElse(0);
		this.gasCap = controller.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).map(tank->tank.getTankCapacity(2)).orElse(0);
		this.juice = controller.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).map(tank->tank.getFluidInTank(1).getAmount()).orElse(0);
		this.juiceCap = controller.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).map(tank->tank.getTankCapacity(1)).orElse(0);
		this.bacteria = (int) Math.round(controller.getCapability(BacteriaCapability.BACT_CAPABILITY).map(bact->bact.getBact()).orElse(0.));
		this.bacteriaCap = controller.getCapability(BacteriaCapability.BACT_CAPABILITY).map(bact->bact.getLimit()).orElse(0);
		this.intake = controller.getFlow();
		this.temp = controller.getTemp();
	}
	
	public void toBytes(PacketBuffer buf) {
		buf.writeInt(this.gas);
		buf.writeInt(this.gasCap);
		buf.writeInt(this.juice);
		buf.writeInt(this.juiceCap);
		buf.writeInt(this.bacteria);
		buf.writeInt(this.bacteriaCap);
		buf.writeInt(this.intake);
		buf.writeDouble(this.temp);
	}
	
	public boolean handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(()->{
			TankScreen.gas = this.gas;
			TankScreen.gasCap = this.gasCap;
			TankScreen.juice = this.juice;
			TankScreen.juiceCap = this.juiceCap;
			TankScreen.bacteria = this.bacteria;
			TankScreen.bacteriaCap = this.bacteriaCap;
			TankScreen.intake = this.intake;
			TankScreen.temp = this.temp;
		});
		return true;
	}
	
}
