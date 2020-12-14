package mod.juicy;

import mod.juicy.capability.ProbeProvider;
import mod.juicy.item.ProbeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EventHandler {

	@SubscribeEvent
	public static void attachCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
        if(event.getObject().getItem() instanceof ProbeItem)
        	event.addCapability(new ResourceLocation(Juicy.MODID, "bacteria"), new ProbeProvider());
    }
	
	@SubscribeEvent
	public static void gutterCheck(LivingDeathEvent event) {
	}
}
