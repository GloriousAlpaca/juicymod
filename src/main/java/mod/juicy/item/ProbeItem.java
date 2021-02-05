package mod.juicy.item;

import java.util.List;

import javax.annotation.Nullable;

import mod.juicy.Juicy;
import mod.juicy.capability.BacteriaCapability;
import mod.juicy.capability.IBacteriaCapability;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
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
			Block lookingAt = context.getWorld().getBlockState(context.getPos()).getBlock();
			if(lookingAt.equals(Blocks.KELP_PLANT) || lookingAt.equals(Blocks.KELP)) {
				LazyOptional<IBacteriaCapability> capability = context.getItem().getCapability(BacteriaCapability.BACT_CAPABILITY, null);
				capability.ifPresent(cap->{
					if(cap.getBact()<1.)
					cap.receiveBact(Math.round(Math.random()*30), false);
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
		cap.ifPresent(cap1 ->{ 
		tooltip.add(new TranslationTextComponent("information.probeitem"));
		tooltip.add(new StringTextComponent(Long.toString(Math.round(cap1.getBact()))));
			});
	}
	
	
	@Override
	public boolean shouldSyncTag() {
		return true;
	}
	
	@Override
	public CompoundNBT getShareTag(ItemStack stack) {
		CompoundNBT nbt1 = super.getShareTag(stack);
		CompoundNBT nbt2;
		if(nbt1 != null)
			nbt2 = nbt1;
		else
			nbt2 = new CompoundNBT();
		stack.getCapability(BacteriaCapability.BACT_CAPABILITY, null).ifPresent(cap -> nbt2.put("bacteria", BacteriaCapability.BACT_CAPABILITY.writeNBT(cap, null)));
		return nbt2;
	}
	
	@Override
	public void readShareTag(ItemStack stack, CompoundNBT nbt) {
		if(nbt != null) {
		super.readShareTag(stack, nbt);
		stack.getCapability(BacteriaCapability.BACT_CAPABILITY, null).ifPresent(cap->BacteriaCapability.BACT_CAPABILITY.readNBT(cap, null, nbt.get("bacteria")));
		}
	}
	
}
