package mod.juicy;

import mod.juicy.block.GutterBlock;
import mod.juicy.capability.ProbeProvider;
import mod.juicy.item.ProbeItem;
import mod.juicy.tile.GutterTile;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
		LivingEntity entity = event.getEntityLiving();
		World worldIn = entity.getEntityWorld();
		if(!worldIn.isRemote)
		for(int i=0;i<2;i++) {
			BlockPos cPos = entity.getPosition().down(i);
			Juicy.LOGGER.info("Current Position: "+cPos);
			if(worldIn.getBlockState(cPos).getBlock() instanceof GutterBlock) {
				if(worldIn.getTileEntity(cPos) != null)
					//TODO Add Config
					((GutterTile) worldIn.getTileEntity(cPos)).addJuice(100);
			}
		}
		
	}
}
