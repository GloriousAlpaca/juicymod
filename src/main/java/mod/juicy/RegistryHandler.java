package mod.juicy;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static mod.juicy.Juicy.MODID;

import java.awt.Color;
import java.util.function.Supplier;

import mod.juicy.block.BlockHolder;
import mod.juicy.block.GutterBlock;
import mod.juicy.block.TankBlock;
import mod.juicy.capability.BacteriaFactory;
import mod.juicy.capability.BacteriaStorage;
import mod.juicy.capability.IBacteriaCapability;
import mod.juicy.tile.TankTile;
import mod.juicy.fluid.FluidHolder;
import mod.juicy.item.ItemHolder;
import mod.juicy.item.ProbeItem;
import mod.juicy.util.ItemHelper;

public class RegistryHandler {
	
	
	//Register
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	private static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, MODID);
	private static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MODID);
	
	//Mobjuice Data
	public static final ResourceLocation MOBJUICE_STILL_RL = new ResourceLocation(Juicy.MODID,"block/mobjuice_still");
	public static final ResourceLocation MOBJUICE_FLOWING_RL = new ResourceLocation(Juicy.MODID,"block/mobjuice_flowing");
	public static final ResourceLocation MOBJUICE_OVERLAY_RL = new ResourceLocation(Juicy.MODID,"block/mobjuice_overlay");
	public static final ForgeFlowingFluid.Properties MOBJUICE_PROPERTIES = new ForgeFlowingFluid.Properties(()-> FluidHolder.MOBJUICE_STILL, ()-> FluidHolder.MOBJUICE_FLOWING, FluidAttributes.builder(MOBJUICE_STILL_RL, MOBJUICE_FLOWING_RL).density(5).luminosity(10).overlay(MOBJUICE_OVERLAY_RL)).block(()->FluidHolder.MOBJUICE_BLOCK).bucket(()->ItemHolder.JUICE_BUCKET);
	
	//Register Fluids
	public static final RegistryObject<ForgeFlowingFluid> MOBJUICE_STILL = FLUIDS.register("mobjuice_still",() -> new ForgeFlowingFluid.Source(MOBJUICE_PROPERTIES));
	public static final RegistryObject<ForgeFlowingFluid> MOBJUICE_FLOWING = FLUIDS.register("mobjuice_flowing",() -> new ForgeFlowingFluid.Flowing(MOBJUICE_PROPERTIES));
	
	//Register Blocks
	public static final RegistryObject<Block> MOBJUICE_BLOCK = BLOCKS.register("mobjuice", () -> new FlowingFluidBlock(()-> FluidHolder.MOBJUICE_STILL, AbstractBlock.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));
	public static final RegistryObject<Block> GUTTER_BLOCK = BLOCKS.register("juicegutter", ()-> new GutterBlock());
	public static final RegistryObject<Block> TANK_BLOCK = BLOCKS.register("fermentation_tank", ()-> new TankBlock());

	//Register Tile Entity Types
	public static final RegistryObject<TileEntityType<?>> TANK_TILE = TILES.register("fermentation_tank", () -> TileEntityType.Builder.create(() -> new TankTile(), BlockHolder.TANK_BLOCK).build(null).setRegistryName("fermentation_tank"));
	
	//Register Items
	public static final RegistryObject<Item> PROBE_ITEM = ITEMS.register("probe",() -> new ProbeItem());
	public static final RegistryObject<Item> JUICE_BUCKET_ITEM = ITEMS.register("juice_bucket",() -> new BucketItem(()-> FluidHolder.MOBJUICE_STILL, new Item.Properties().group(ItemGroup.MISC)));
	
	//Register Blockitems
	public static final RegistryObject<Item> GUTTER_BLOCK_ITEM = ITEMS.register("juicegutter",() -> ItemHelper.ItemfromBlock(BlockHolder.GUTTER_BLOCK));
	public static final RegistryObject<Item> TANK_BLOCK_ITEM = ITEMS.register("fermentation_tank",() -> ItemHelper.ItemfromBlock(BlockHolder.TANK_BLOCK));
	
	public static void registerall(){
		FLUIDS.register(FMLJavaModLoadingContext.get().getModEventBus());
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
	
	
}
