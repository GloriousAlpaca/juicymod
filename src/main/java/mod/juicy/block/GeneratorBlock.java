package mod.juicy.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GeneratorBlock extends Block{

	public GeneratorBlock() {
		super(AbstractBlock.Properties.create(Material.IRON).hardnessAndResistance(1.5f, 7f).harvestLevel(1));
	}

}
