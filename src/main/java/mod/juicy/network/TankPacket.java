package mod.juicy.network;

import java.util.function.Supplier;

import mod.juicy.tile.TankControllerTile;
import mod.juicy.tile.TankSlaveTile;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

public class TankPacket{
	private BlockPos pos;
	
	public TankPacket(PacketBuffer buf) {
		this.pos = buf.readBlockPos();
	}
	
	public TankPacket(BlockPos controller) {
		this.pos = controller;
	}
	
    public void toBytes(PacketBuffer buf) {
    	if(pos != null)
        buf.writeBlockPos(pos);
    }
    
    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(()-> {
        	World serverWorld = ctx.get().getSender().getServerWorld();
        	TileEntity tile = serverWorld.getTileEntity(pos);
        	if(tile != null) {
        		TankControllerTile controller;
        		if(tile instanceof TankControllerTile) {
        			controller = (TankControllerTile) tile;
        		}
        		else{
        			controller = (TankControllerTile) serverWorld.getTileEntity(((TankSlaveTile) tile).getController());
        		}
        		PacketHandler.sendToClient(new TankReturnPacket(controller), ctx.get().getSender());	
        	}
        });
        return true;
    }
    
}
