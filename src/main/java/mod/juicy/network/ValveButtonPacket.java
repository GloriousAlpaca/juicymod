package mod.juicy.network;

import java.util.function.Supplier;

import mod.juicy.tile.ValveTile;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

public class ValveButtonPacket {
	private BlockPos pos;
	private int high;
	private int low;

	public ValveButtonPacket(PacketBuffer buf) {
		this.pos = buf.readBlockPos();
		this.high = buf.readInt();
		this.low = buf.readInt();
	}

	public ValveButtonPacket(BlockPos tilePos, int pHigh, int pLow) {
		this.pos = tilePos;
		this.high = pHigh;
		this.low = pLow;
	}

	public void toBytes(PacketBuffer buf) {
		if (pos != null)
			buf.writeBlockPos(pos);
			buf.writeInt(this.high);
			buf.writeInt(this.low);
	}

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(()-> {
        	World serverWorld = ctx.get().getSender().getServerWorld();
        	TileEntity tile = serverWorld.getTileEntity(pos);
        	if(tile != null) {
        		if(tile instanceof ValveTile)
        		((ValveTile) tile).setHigh(high);
        		((ValveTile) tile).setLow(low);
        	}
        });
        return true;
    }
}
