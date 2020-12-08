package mod.juicy.item;

import mod.juicy.Juicy;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class ProbeItem extends Item{

	public ProbeItem() {
		super(new Properties().maxStackSize(1).group(Juicy.itemGroup));
	}

	/**
	* Called when this item is used when targetting a Block
	*/
	public ActionResultType onItemUse(ItemUseContext context) {
		
		return ActionResultType.PASS;
	}
	
	/**
	 * Cannot break Blocks with the Probe
	 */
	public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
	      return false;
	}
	
}
