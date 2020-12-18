package mod.juicy;

import javax.annotation.Nonnull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mod.juicy.capability.BacteriaFactory;
import mod.juicy.capability.BacteriaStorage;
import mod.juicy.capability.IBacteriaCapability;
import mod.juicy.container.ContainerHolder;
import mod.juicy.gui.TankScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Juicy.MODID)
public class Juicy
{
	/*Mod Identifiers*/
	public static final String MODID = "juicy";
	
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public static ItemGroup itemGroup = new ItemGroup(MODID)
	{
		@Override
		@Nonnull
		public ItemStack createIcon()
		{
			return new ItemStack(Items.GRAVEL);
		}
	};
	
    public Juicy() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        RegistryHandler.registerall();
        //Register the Event Handler
        MinecraftForge.EVENT_BUS.register(this);
    }

	// Pre Init
	private void setup(final FMLCommonSetupEvent event) {
		LOGGER.info("Register Bacteria Capability");
		CapabilityManager.INSTANCE.register(IBacteriaCapability.class, new BacteriaStorage(), new BacteriaFactory());
	}

	private void clientSetup(final FMLClientSetupEvent event) {
		ScreenManager.registerFactory(ContainerHolder.TANK_CONTAINER, TankScreen::new);
		Juicy.LOGGER.info("Screens Registered");
	}

}
