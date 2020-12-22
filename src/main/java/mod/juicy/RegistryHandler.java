package mod.juicy;

import static mod.juicy.Juicy.MODID;

import mod.juicy.block.AlertBlock;
import mod.juicy.block.BlockHolder;
import mod.juicy.block.GeneratorBlock;
import mod.juicy.block.GutterBlock;
import mod.juicy.block.TankBlock;
import mod.juicy.block.TankControllerBlock;
import mod.juicy.block.ThermBlock;
import mod.juicy.block.ValveBlock;
import mod.juicy.container.AlertContainer;
import mod.juicy.container.GeneratorContainer;
import mod.juicy.container.TankContainer;
import mod.juicy.container.ThermContainer;
import mod.juicy.container.ValveContainer;
import mod.juicy.fluid.FluidHolder;
import mod.juicy.item.ItemHolder;
import mod.juicy.item.ProbeItem;
import mod.juicy.tile.AlertTile;
import mod.juicy.tile.GeneratorTile;
import mod.juicy.tile.GutterTile;
import mod.juicy.tile.TankControllerTile;
import mod.juicy.tile.TankTile;
import mod.juicy.tile.ThermTile;
import mod.juicy.tile.ValveTile;
import mod.juicy.util.ItemHelper;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.common.extensions.IForgeContainerType;

public class RegistryHandler {
	
	
	//Register
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	private static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, MODID);
	private static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MODID);
	private static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MODID);
	
	//Mobjuice Data
	public static final ResourceLocation MOBJUICE_STILL_RL = new ResourceLocation(Juicy.MODID,"block/mobjuice_still");
	public static final ResourceLocation MOBJUICE_FLOWING_RL = new ResourceLocation(Juicy.MODID,"block/mobjuice_flowing");
	public static final ResourceLocation MOBJUICE_OVERLAY_RL = new ResourceLocation(Juicy.MODID,"block/mobjuice_overlay");
	public static final ResourceLocation MOBGAS_STILL_RL = new ResourceLocation(Juicy.MODID,"block/mobgas_still");
	public static final ResourceLocation MOBGAS_FLOWING_RL = new ResourceLocation(Juicy.MODID,"block/mobgas_flowing");
	public static final ResourceLocation MOBGAS_OVERLAY_RL = new ResourceLocation(Juicy.MODID,"block/mobgas_overlay");
	public static final ForgeFlowingFluid.Properties MOBJUICE_PROPERTIES = new ForgeFlowingFluid.Properties(()-> FluidHolder.MOBJUICE_STILL, ()-> FluidHolder.MOBJUICE_FLOWING, FluidAttributes.builder(MOBJUICE_STILL_RL, MOBJUICE_FLOWING_RL).density(1000).luminosity(10).overlay(MOBJUICE_OVERLAY_RL)).block(()->FluidHolder.MOBJUICE_BLOCK).bucket(()->ItemHolder.JUICE_BUCKET);
	public static final ForgeFlowingFluid.Properties MOBGAS_PROPERTIES = new ForgeFlowingFluid.Properties(()-> FluidHolder.MOBGAS_STILL, ()-> FluidHolder.MOBGAS_FLOWING, FluidAttributes.builder(MOBGAS_STILL_RL, MOBGAS_FLOWING_RL).density(5).viscosity(1).luminosity(10).overlay(MOBGAS_OVERLAY_RL).gaseous()).block(()->FluidHolder.MOBGAS_BLOCK);
	
	//Register Fluids
	public static final RegistryObject<ForgeFlowingFluid> MOBJUICE_STILL = FLUIDS.register("mobjuice_still",() -> new ForgeFlowingFluid.Source(MOBJUICE_PROPERTIES));
	public static final RegistryObject<ForgeFlowingFluid> MOBJUICE_FLOWING = FLUIDS.register("mobjuice_flowing",() -> new ForgeFlowingFluid.Flowing(MOBJUICE_PROPERTIES));
	public static final RegistryObject<ForgeFlowingFluid> MOBGAS_STILL = FLUIDS.register("mobgas_still",() -> new ForgeFlowingFluid.Source(MOBGAS_PROPERTIES));
	public static final RegistryObject<ForgeFlowingFluid> MOBGAS_FLOWING = FLUIDS.register("mobgas_flowing",() -> new ForgeFlowingFluid.Flowing(MOBGAS_PROPERTIES));
	
	//Register Blocks
	public static final RegistryObject<Block> MOBJUICE_BLOCK = BLOCKS.register("mobjuice", () -> new FlowingFluidBlock(()-> FluidHolder.MOBJUICE_STILL, AbstractBlock.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));
	public static final RegistryObject<Block> MOBGAS_BLOCK = BLOCKS.register("mobgas", () -> new FlowingFluidBlock(()-> FluidHolder.MOBGAS_STILL, AbstractBlock.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));
	public static final RegistryObject<Block> GUTTER_BLOCK = BLOCKS.register("juicegutter", ()-> new GutterBlock());
	public static final RegistryObject<Block> TANK_CONTROLLER_BLOCK = BLOCKS.register("tank_controller", ()-> new TankControllerBlock());
	public static final RegistryObject<Block> TANK_BLOCK = BLOCKS.register("tank", ()-> new TankBlock());
	public static final RegistryObject<Block> THERM_BLOCK = BLOCKS.register("thermostat", ()-> new ThermBlock());
	public static final RegistryObject<Block> VALVE_BLOCK = BLOCKS.register("valve", ()-> new ValveBlock());
	public static final RegistryObject<Block> GENERATOR_BLOCK = BLOCKS.register("generator", ()-> new GeneratorBlock());
	public static final RegistryObject<Block> ALERT_BLOCK = BLOCKS.register("alert", ()-> new AlertBlock());
	
	//Register Tile Entity Types
	public static final RegistryObject<TileEntityType<?>> TANK_CONTROLLER_TILE = TILES.register("tank_controller", () -> TileEntityType.Builder.create(() -> new TankControllerTile(), BlockHolder.TANK_CONTROLLER_BLOCK).build(null));
	public static final RegistryObject<TileEntityType<?>> TANK_TILE = TILES.register("tank", () -> TileEntityType.Builder.create(() -> new TankTile(), BlockHolder.TANK_BLOCK).build(null));
	public static final RegistryObject<TileEntityType<?>> GUTTER_TILE = TILES.register("juicegutter", () -> TileEntityType.Builder.create(() -> new GutterTile(), BlockHolder.GUTTER_BLOCK).build(null));
	public static final RegistryObject<TileEntityType<?>> THERM_TILE = TILES.register("thermostat", () -> TileEntityType.Builder.create(() -> new ThermTile(), BlockHolder.THERM_BLOCK).build(null));
	public static final RegistryObject<TileEntityType<?>> VALVE_TILE = TILES.register("valve", () -> TileEntityType.Builder.create(() -> new ValveTile(), BlockHolder.VALVE_BLOCK).build(null));
	public static final RegistryObject<TileEntityType<?>> GENERATOR_TILE = TILES.register("generator", () -> TileEntityType.Builder.create(() -> new GeneratorTile(), BlockHolder.GENERATOR_BLOCK).build(null));
	public static final RegistryObject<TileEntityType<?>> ALERT_TILE = TILES.register("alert", () -> TileEntityType.Builder.create(() -> new AlertTile(), BlockHolder.ALERT_BLOCK).build(null));
	
	//Register ContainerTypes
	//TODO Reduce ContainerTypes
    public static final RegistryObject<ContainerType<TankContainer>> TANK_CONTAINER = CONTAINERS.register("tank", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getEntityWorld();
        return new TankContainer(windowId, world, pos, inv, inv.player);
    }));
    public static final RegistryObject<ContainerType<GeneratorContainer>> GENERATOR_CONTAINER = CONTAINERS.register("generator", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getEntityWorld();
        return new GeneratorContainer(windowId, world, pos, inv, inv.player);
    }));
    public static final RegistryObject<ContainerType<ThermContainer>> THERM_CONTAINER = CONTAINERS.register("thermostat", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getEntityWorld();
        return new ThermContainer(windowId, world, pos, inv, inv.player);
    }));
    public static final RegistryObject<ContainerType<ValveContainer>> VALVE_CONTAINER = CONTAINERS.register("valve", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getEntityWorld();
        return new ValveContainer(windowId, world, pos, inv, inv.player);
    }));
    public static final RegistryObject<ContainerType<AlertContainer>> ALERT_CONTAINER = CONTAINERS.register("alert", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getEntityWorld();
        return new AlertContainer(windowId, world, pos, inv, inv.player);
    }));
    
	//Register Items
	public static final RegistryObject<Item> PROBE_ITEM = ITEMS.register("probe",() -> new ProbeItem());
	public static final RegistryObject<Item> PLATE_ITEM = ITEMS.register("plate",() -> new Item(new Item.Properties().group(Juicy.itemGroup)));
	public static final RegistryObject<Item> JUICE_BUCKET_ITEM = ITEMS.register("juice_bucket",() -> new BucketItem(()-> FluidHolder.MOBJUICE_STILL, new Item.Properties().group(ItemGroup.MISC)));
	
	//Register Blockitems
	public static final RegistryObject<Item> GUTTER_BLOCK_ITEM = ITEMS.register("juicegutter",() -> ItemHelper.ItemfromBlock(BlockHolder.GUTTER_BLOCK));
	public static final RegistryObject<Item> TANK_CONTROLLER_BLOCK_ITEM = ITEMS.register("tank_controller",() -> ItemHelper.ItemfromBlock(BlockHolder.TANK_CONTROLLER_BLOCK));
	public static final RegistryObject<Item> TANK_BLOCK_ITEM = ITEMS.register("tank",() -> ItemHelper.ItemfromBlock(BlockHolder.TANK_BLOCK));
	public static final RegistryObject<Item> THERM_BLOCK_ITEM = ITEMS.register("thermostat",() -> ItemHelper.ItemfromBlock(BlockHolder.THERM_BLOCK));
	public static final RegistryObject<Item> VALVE_BLOCK_ITEM = ITEMS.register("valve",() -> ItemHelper.ItemfromBlock(BlockHolder.VALVE_BLOCK));
	public static final RegistryObject<Item> GENERATOR_BLOCK_ITEM = ITEMS.register("generator",() -> ItemHelper.ItemfromBlock(BlockHolder.GENERATOR_BLOCK));
	public static final RegistryObject<Item> ALERT_BLOCK_ITEM = ITEMS.register("alert",() -> ItemHelper.ItemfromBlock(BlockHolder.ALERT_BLOCK));
	
	public static void registerall(){
		FLUIDS.register(FMLJavaModLoadingContext.get().getModEventBus());
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
		CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
		
	}
	
	
}
