package mod.juicy.network;

import java.util.function.Supplier;

import mod.juicy.gui.ThermScreen;
import mod.juicy.tile.ThermTile;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class ThermReturnPacket {
	private double high;
	private double low;
	
	public ThermReturnPacket(PacketBuffer buf) {
		this.high = buf.readDouble();
		this.low = buf.readDouble();
	}

	public ThermReturnPacket(ThermTile tile) {
		this.high = tile.getHigh();
		this.low = tile.getLow();
	}
	
	public void toBytes(PacketBuffer buf) {
		buf.writeDouble(this.high);
		buf.writeDouble(this.low);
	}
	
	public boolean handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(()->{
			ThermScreen.high = this.high;
			ThermScreen.low = this.low;
			if(ThermScreen.textFieldHigh != null)
			ThermScreen.textFieldHigh.setText(Double.toString(this.high));
			if(ThermScreen.textFieldLow != null)
			ThermScreen.textFieldLow.setText(Double.toString(this.low));
		});
		return true;
	}
}
