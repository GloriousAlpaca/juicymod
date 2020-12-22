package mod.juicy.datagen;

import java.util.function.Function;

import mod.juicy.Juicy;
import mod.juicy.block.BlockHolder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStates extends BlockStateProvider{

    public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Juicy.MODID, exFileHelper);
    }

	@Override
	protected void registerStatesAndModels() {
		orientedBlock(BlockHolder.GENERATOR_BLOCK, (state)->new UncheckedModelFile(new ResourceLocation(Juicy.MODID, "block/generator")));
		simpleBlock(BlockHolder.ALERT_BLOCK, new UncheckedModelFile(new ResourceLocation(Juicy.MODID, "block/alert")));
		simpleBlock(BlockHolder.GUTTER_BLOCK, new UncheckedModelFile(new ResourceLocation(Juicy.MODID, "block/juicegutter")));
		simpleBlock(BlockHolder.TANK_BLOCK, new UncheckedModelFile(new ResourceLocation(Juicy.MODID, "block/tank")));
		simpleBlock(BlockHolder.TANK_CONTROLLER_BLOCK, new UncheckedModelFile(new ResourceLocation(Juicy.MODID, "block/tank_controller")));
		simpleBlock(BlockHolder.THERM_BLOCK, new UncheckedModelFile(new ResourceLocation(Juicy.MODID, "block/thermostat")));
		simpleBlock(BlockHolder.VALVE_BLOCK, new UncheckedModelFile(new ResourceLocation(Juicy.MODID, "block/valve")));
	}

  
    private void orientedBlock(Block block, Function<BlockState, ModelFile> modelFunc) {
        getVariantBuilder(block)
                .forAllStates(state -> {
                    Direction dir = state.get(BlockStateProperties.FACING);
                    return ConfiguredModel.builder()
                            .modelFile(modelFunc.apply(state))
                            .rotationX(dir.getAxis() == Direction.Axis.Y ?  dir.getAxisDirection().getOffset() * -90 : 0)
                            .rotationY(dir.getAxis() != Direction.Axis.Y ? ((dir.getHorizontalIndex() + 2) % 4) * 90 : 0)
                            .build();
                });
    }
    
}
