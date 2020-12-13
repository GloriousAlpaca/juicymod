package mod.juicy.item;

import java.util.List;

import javax.annotation.Nullable;

import mod.juicy.Juicy;
import mod.juicy.capability.IBacteriaCapability;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.LazyOptional;

public class ProbeItem extends Item{

	@CapabilityInject(IBacteriaCapability.class)
    public static final Capability<IBacteriaCapability> BACT_CAPABILITY = null;
	
	public ProbeItem() {
		super(new Properties().maxStackSize(1).group(Juicy.itemGroup));
	}

	/**
	* Called when this item is used when targetting a Block
	*/
	public ActionResultType onItemUse(ItemUseContext context) {
		if(!context.getWorld().isRemote())
		{
			if(context.getWorld().getBlockState(context.getPos()).getBlock().getRegistryName().toString().contains("kelp")) {
				IBacteriaCapability capability = context.getItem().getCapability(BACT_CAPABILITY, null).orElse(null);
				capability.receiveBact(5, false);
				Juicy.LOGGER.info("Bacteria: " + capability.getBact());
				return ActionResultType.SUCCESS;
			}

		}
		return ActionResultType.PASS;
	}
	
	/**
	 * Cannot break Blocks with the Probe
	 */
	public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
	      return false;
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		//TODO Fix Client Sync
		LazyOptional<IBacteriaCapability> cap = stack.getCapability(BACT_CAPABILITY);
		cap.ifPresent(cap1 -> tooltip.add(new StringTextComponent("Bacteria: "+cap1.getBact())));
	}
	
}
