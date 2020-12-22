package mod.juicy.network;

import java.util.function.Supplier;

import mod.juicy.gui.AlertScreen;
import mod.juicy.tile.AlertTile;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

public class AlertReturnPacket {
	private int operator;
	private int amount;
	private boolean mode;

	
	public AlertReturnPacket(PacketBuffer buf) {
		this.operator = buf.readInt();
		this.amount = buf.readInt();
		this.mode = buf.readBoolean();
	}

	public AlertReturnPacket(AlertTile tile) {
		this.operator = tile.getOperator();
		this.amount = tile.getAmount();
		this.mode = tile.getMode();
	}
	
	public void toBytes(PacketBuffer buf) {
		buf.writeInt(this.operator);
		buf.writeInt(this.amount);
		buf.writeBoolean(this.mode);
	}
	
	public boolean handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(()->{
			AlertScreen.operator = this.operator;
			AlertScreen.amount = this.amount;
			AlertScreen.mode = this.mode;
			AlertScreen.textField.setText(Integer.toString(this.amount));
			AlertScreen.modeButton.setMessage(this.mode ? new TranslationTextComponent("button.juice") : new TranslationTextComponent("button.bacteria"));
		});
		return true;
	}
}
