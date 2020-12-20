package mod.juicy.network;

import java.util.function.Supplier;

import mod.juicy.gui.ValveScreen;
import mod.juicy.tile.ValveTile;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class ValveReturnPacket {
	private int high;
	private int low;
	
	public ValveReturnPacket(PacketBuffer buf) {
		this.high = buf.readInt();
		this.low = buf.readInt();
	}

	public ValveReturnPacket(ValveTile tile) {
		this.high = tile.getHigh();
		this.low = tile.getLow();
	}
	
	public void toBytes(PacketBuffer buf) {
		buf.writeInt(this.high);
		buf.writeInt(this.low);
	}
	
	public boolean handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(()->{
			ValveScreen.high = this.high;
			ValveScreen.low = this.low;
			if(ValveScreen.textFieldHigh != null)
			ValveScreen.textFieldHigh.setText(Integer.toString(this.high));
			if(ValveScreen.textFieldLow != null)
			ValveScreen.textFieldLow.setText(Integer.toString(this.low));
		});
		return true;
	}
}
