package mod.juicy.datagen;

import mod.juicy.Juicy;
import mod.juicy.block.BlockHolder;
import mod.juicy.item.ItemHolder;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class Items extends ItemModelProvider {

    public Items(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Juicy.MODID, existingFileHelper);
    }

	@Override
	protected void registerModels() {
		withExistingParent(BlockHolder.ALERT_BLOCK.getRegistryName().getPath(), new ResourceLocation(Juicy.MODID, "block/alert"));
		withExistingParent(BlockHolder.GENERATOR_BLOCK.getRegistryName().getPath(), new ResourceLocation(Juicy.MODID, "block/generator"));
		withExistingParent(BlockHolder.GUTTER_BLOCK.getRegistryName().getPath(), new ResourceLocation(Juicy.MODID, "block/juicegutter"));
		withExistingParent(BlockHolder.TANK_BLOCK.getRegistryName().getPath(), new ResourceLocation(Juicy.MODID, "block/tank"));
		withExistingParent(BlockHolder.TANK_CONTROLLER_BLOCK.getRegistryName().getPath(), new ResourceLocation(Juicy.MODID, "block/tank_controller"));
		withExistingParent(BlockHolder.THERM_BLOCK.getRegistryName().getPath(), new ResourceLocation(Juicy.MODID, "block/thermostat"));
		withExistingParent(BlockHolder.VALVE_BLOCK.getRegistryName().getPath(), new ResourceLocation(Juicy.MODID, "block/valve"));
        singleTexture(ItemHolder.JUICE_BUCKET.getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                "layer0", new ResourceLocation(Juicy.MODID, "item/juice_bucket"));
        singleTexture(ItemHolder.PLATE.getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                "layer0", new ResourceLocation(Juicy.MODID, "item/plate"));
	}

}
