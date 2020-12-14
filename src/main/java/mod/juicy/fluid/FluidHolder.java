package mod.juicy.fluid;

import mod.juicy.Juicy;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Juicy.MODID)
public class FluidHolder {

	
	@ObjectHolder("mobjuice_still")
	public static final ForgeFlowingFluid MOBJUICE_STILL = null;
	
	@ObjectHolder("mobjuice_flowing")
	public static final ForgeFlowingFluid MOBJUICE_FLOWING = null;
	
	@ObjectHolder("mobjuice")
	public static final FlowingFluidBlock MOBJUICE_BLOCK = null;
	
	@ObjectHolder("mobgas_still")
	public static final ForgeFlowingFluid MOBGAS_STILL = null;
	
	@ObjectHolder("mobgas_flowing")
	public static final ForgeFlowingFluid MOBGAS_FLOWING = null;
	
	@ObjectHolder("mobgas")
	public static final FlowingFluidBlock MOBGAS_BLOCK = null;
	
}
