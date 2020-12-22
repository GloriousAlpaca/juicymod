package mod.juicy.network;

import java.util.function.Supplier;

import mod.juicy.tile.AlertTile;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

public class AlertPacket {
	private BlockPos pos;

	public AlertPacket(PacketBuffer buf) {
		this.pos = buf.readBlockPos();
	}

	public AlertPacket(BlockPos tilePos) {
		this.pos = tilePos;
	}

	public void toBytes(PacketBuffer buf) {
		if (pos != null)
			buf.writeBlockPos(pos);
	}

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(()-> {
        	World serverWorld = ctx.get().getSender().getServerWorld();
        	TileEntity tile = serverWorld.getTileEntity(pos);
        	if(tile != null) {
        		if(tile instanceof AlertTile)
        		PacketHandler.sendToClient(new AlertReturnPacket((AlertTile)tile), ctx.get().getSender());
        	}
        });
        return true;
    }
    
}
