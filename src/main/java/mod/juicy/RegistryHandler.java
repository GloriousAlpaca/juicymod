package mod.juicy;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static mod.juicy.Juicymod.MODID;

import java.awt.Color;
import java.util.function.Supplier;

import mod.juicy.fluid.FluidHolder;

public class RegistryHandler {
	
	
	//Register
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	private static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, MODID);
	
	
	//Mobjuice Data
	public static final ResourceLocation MOBJUICE_STILL_RL = new ResourceLocation(Juicymod.MODID,"blocks/mobjuice_still");
	public static final ResourceLocation MOBJUICE_FLOWING_RL = new ResourceLocation(Juicymod.MODID,"blocks/mobjuice_flowing");
	public static final ResourceLocation MOBJUICE_OVERLAY_RL = new ResourceLocation(Juicymod.MODID,"blocks/mobjuice_overlay");
		
	public static final ForgeFlowingFluid.Properties MOBJUICE_PROPERTIES = new ForgeFlowingFluid.Properties(
			()-> FluidHolder.MOBJUICE_STILL, 
			()-> FluidHolder.MOBJUICE_FLOWING, 
			FluidAttributes.builder(MOBJUICE_STILL_RL, MOBJUICE_FLOWING_RL).color(Color.GREEN.getRGB()).overlay(MOBJUICE_OVERLAY_RL).sound(SoundEvents.ENTITY_COD_FLOP));
	
	//Register Fluids
	public static final RegistryObject<Fluid> MOBJUICE_STILL = FLUIDS.register("mobjuice_still",() -> new ForgeFlowingFluid.Source(MOBJUICE_PROPERTIES));
	public static final RegistryObject<Fluid> MOBJUICE_FLOWING = FLUIDS.register("mobjuice_flowing",() -> new ForgeFlowingFluid.Flowing(MOBJUICE_PROPERTIES));
	
	//Register Blocks
	public static final RegistryObject<Block> MOBJUICE_BLOCK = BLOCKS.register("mobjuice", () -> new FlowingFluidBlock(
			//Temporary Change to RegistryObject
			()-> FluidHolder.MOBJUICE_STILL,
			AbstractBlock.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));
	
	public static void registerall(){
		FLUIDS.register(FMLJavaModLoadingContext.get().getModEventBus());
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
	
}
