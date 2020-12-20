package mod.juicy.container;

import mod.juicy.Juicy;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Juicy.MODID)
public class ContainerHolder {

	@ObjectHolder ("tank")
    public static ContainerType<TankContainer> TANK_CONTAINER = null;
	
	@ObjectHolder ("generator")
    public static ContainerType<GeneratorContainer> GENERATOR_CONTAINER = null;
	
	
}
