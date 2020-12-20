package mod.juicy.item;

import java.util.List;

import javax.annotation.Nullable;

import mod.juicy.Juicy;
import mod.juicy.capability.BacteriaCapability;
import mod.juicy.capability.IBacteriaCapability;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

public class ProbeItem extends Item{
	
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
				LazyOptional<IBacteriaCapability> capability = context.getItem().getCapability(BacteriaCapability.BACT_CAPABILITY, null);
				capability.ifPresent(cap->{
					cap.receiveBact(Math.round(Math.random()*5), false);
					Juicy.LOGGER.info("Bacteria: " + cap.getBact());
				});
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
		LazyOptional<IBacteriaCapability> cap = stack.getCapability(BacteriaCapability.BACT_CAPABILITY, null);
		cap.ifPresent(cap1 -> tooltip.add(new TranslationTextComponent("information.probeitem").appendString(Long.toString(Math.round(cap1.getBact())))));
	}
	
	
	@Override
	public boolean shouldSyncTag() {
		return true;
	}
	
	@Override
	public CompoundNBT getShareTag(ItemStack stack) {
		CompoundNBT nbt = super.getShareTag(stack);
		if(nbt != null)
		stack.getCapability(BacteriaCapability.BACT_CAPABILITY, null).ifPresent(cap->nbt.put("bacteria", BacteriaCapability.BACT_CAPABILITY.writeNBT(cap, null)));
		return nbt;
	}
	
	@Override
	public void readShareTag(ItemStack stack, CompoundNBT nbt) {
		super.readShareTag(stack, nbt);
		stack.getCapability(BacteriaCapability.BACT_CAPABILITY, null).ifPresent(cap->BacteriaCapability.BACT_CAPABILITY.readNBT(cap, null, nbt.get("bacteria")));
	}
	
}
