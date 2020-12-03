package mod.juicy.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GutterBlock extends Block{

	public GutterBlock() {
		super(AbstractBlock.Properties.create(Material.IRON).hardnessAndResistance(1.5f, 7f).harvestLevel(2));
	}

}
