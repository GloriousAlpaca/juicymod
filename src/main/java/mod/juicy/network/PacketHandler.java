package mod.juicy.network;

import mod.juicy.Juicy;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandler {
	private static final String PROTOCOL_VERSION = "1";
	private static int ID = 0;
	public static SimpleChannel INSTANCE;
	
	private static int nextID() {
        return ID++;
    }
	
	public static void registerMessages() {
		INSTANCE = NetworkRegistry.newSimpleChannel(
			    new ResourceLocation(Juicy.MODID, "main"),
			    () -> PROTOCOL_VERSION,
			    PROTOCOL_VERSION::equals,
			    PROTOCOL_VERSION::equals
			);
		
        INSTANCE.messageBuilder(TankPacket.class, nextID())
        .encoder(TankPacket::toBytes)
        .decoder(TankPacket::new)
        .consumer(TankPacket::handle)
        .add();
        
        INSTANCE.messageBuilder(TankReturnPacket.class, nextID())
        .encoder(TankReturnPacket::toBytes)
        .decoder(TankReturnPacket::new)
        .consumer(TankReturnPacket::handle)
        .add();
        
        INSTANCE.messageBuilder(GeneratorPacket.class, nextID())
        .encoder(GeneratorPacket::toBytes)
        .decoder(GeneratorPacket::new)
        .consumer(GeneratorPacket::handle)
        .add();
        
        INSTANCE.messageBuilder(GeneratorReturnPacket.class, nextID())
        .encoder(GeneratorReturnPacket::toBytes)
        .decoder(GeneratorReturnPacket::new)
        .consumer(GeneratorReturnPacket::handle)
        .add();
        
        INSTANCE.messageBuilder(ThermPacket.class, nextID())
        .encoder(ThermPacket::toBytes)
        .decoder(ThermPacket::new)
        .consumer(ThermPacket::handle)
        .add();
        
        INSTANCE.messageBuilder(ThermReturnPacket.class, nextID())
        .encoder(ThermReturnPacket::toBytes)
        .decoder(ThermReturnPacket::new)
        .consumer(ThermReturnPacket::handle)
        .add();
        
        INSTANCE.messageBuilder(ThermButtonPacket.class, nextID())
        .encoder(ThermButtonPacket::toBytes)
        .decoder(ThermButtonPacket::new)
        .consumer(ThermButtonPacket::handle)
        .add();
        
        INSTANCE.messageBuilder(ValvePacket.class, nextID())
        .encoder(ValvePacket::toBytes)
        .decoder(ValvePacket::new)
        .consumer(ValvePacket::handle)
        .add();
        
        INSTANCE.messageBuilder(ValveReturnPacket.class, nextID())
        .encoder(ValveReturnPacket::toBytes)
        .decoder(ValveReturnPacket::new)
        .consumer(ValveReturnPacket::handle)
        .add();
        
        INSTANCE.messageBuilder(ValveButtonPacket.class, nextID())
        .encoder(ValveButtonPacket::toBytes)
        .decoder(ValveButtonPacket::new)
        .consumer(ValveButtonPacket::handle)
        .add();
	}
	
	public static void sendToClient(Object packet, ServerPlayerEntity player) {
        INSTANCE.sendTo(packet, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendToServer(Object packet) {
        INSTANCE.sendToServer(packet);
    }
    
}
