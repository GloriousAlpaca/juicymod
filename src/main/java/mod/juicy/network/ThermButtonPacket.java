package mod.juicy.network;

import java.util.function.Supplier;
import mod.juicy.tile.ThermTile;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

public class ThermButtonPacket {
	private BlockPos pos;
	private double high;
	private double low;

	public ThermButtonPacket(PacketBuffer buf) {
		this.pos = buf.readBlockPos();
		this.high = buf.readDouble();
		this.low = buf.readDouble();
	}

	public ThermButtonPacket(BlockPos tilePos, double pHigh, double pLow) {
		this.pos = tilePos;
		this.high = pHigh;
		this.low = pLow;
	}

	public void toBytes(PacketBuffer buf) {
		if (pos != null)
			buf.writeBlockPos(pos);
			buf.writeDouble(this.high);
			buf.writeDouble(this.low);
	}

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(()-> {
        	World serverWorld = ctx.get().getSender().getServerWorld();
        	TileEntity tile = serverWorld.getTileEntity(pos);
        	if(tile != null)
        		if(tile instanceof ThermTile) {
        		((ThermTile) tile).setHigh(high);
        		((ThermTile) tile).setLow(low);
        		((ThermTile) tile).setTemp(serverWorld.isBlockPowered(pos) ? high : low);
        		((ThermTile) tile).markDirty();
        	}
        });
        return true;
    }
}
