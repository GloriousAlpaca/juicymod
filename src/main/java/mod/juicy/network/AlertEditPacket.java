package mod.juicy.network;

import java.util.function.Supplier;

import mod.juicy.Juicy;
import mod.juicy.tile.AlertTile;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

public class AlertEditPacket {
	private BlockPos pos;
	private int operator;
	private int amount;
	private boolean mode;
	
	public AlertEditPacket(PacketBuffer buf) {
		this.pos = buf.readBlockPos();
		this.operator = buf.readInt();
		this.amount = buf.readInt();
		this.mode = buf.readBoolean();
	}

	public AlertEditPacket(BlockPos tilePos, int pOperator, int pAmount, boolean pMode) {
		this.pos = tilePos;
		this.operator = pOperator;
		this.amount = pAmount;
		this.mode = pMode;
	}

	public void toBytes(PacketBuffer buf) {
		if (pos != null)
			buf.writeBlockPos(pos);
		buf.writeInt(this.operator);
		buf.writeInt(this.amount);
		buf.writeBoolean(this.mode);
	}

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(()-> {
        	World serverWorld = ctx.get().getSender().getServerWorld();
        	TileEntity tile = serverWorld.getTileEntity(pos);
        	if(tile != null)
        		if(tile instanceof AlertTile) {
        			((AlertTile) tile).setOperator(this.operator);
        			((AlertTile) tile).setAmount(this.amount);
        			((AlertTile) tile).setMode(this.mode);
        	}
        		else
        			Juicy.LOGGER.info("THIS SHOULD NOT HAPPEN!");
        	else
        		Juicy.LOGGER.info("THIS SHOULD NOT HAPPEN!");
        });
        return true;
    }
}
