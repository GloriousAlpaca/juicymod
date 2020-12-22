package mod.juicy.tile;

import mod.juicy.capability.BacteriaCapability;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class AlertTile extends TankSlaveTile{
	private int operator=1;
	private boolean mode=true;
	private int amount=0;
	
	public AlertTile() {
		super(TileHolder.TILE_ALERT_TYPE);
	}

	public boolean getPower() {
		if(controller != null)
		switch (operator) {
		case 1:
			return getValue() < amount;
		case 2:
			return getValue() > amount;
		case 3:
			return getValue() <= amount;
		case 4:
			return getValue() >= amount;
		default:
			return false;
		}
		return false;
	}

	/**
	 * Sets the Operator with which to check
	 * 1: < 2: > 3: <= 4: >=
	 * @param pOperator
	 */
	public void setOperator(int pOperator) {
		this.operator = pOperator;
	}
	
	/**
	 * Sets if the Alertblock checks for Juice or Bacteria
	 * @param pMode True for Juice; False for Bacteria
	 */
	public void setMode(boolean pMode) {
		this.mode = pMode;
	}
	
	/**
	 * Sets the Amount of Juice/Bacteria to check for
	 * @param pAmount Amount to check for
	 */
	public void setAmount(int pAmount) {
		this.amount = pAmount;
	}

	public int getOperator() {
		return operator;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public boolean getMode() {
		return mode;
	}
	
	/**
	 * Gets the current Value of the Tank depending on the mode
	 * @return Current Amount of Juice/Bacteria
	 */
	private int getValue() {
		if(controller != null) {
		TileEntity tile = world.getTileEntity(controller);
		if(tile != null)
			if(tile instanceof TankControllerTile) {
				if(mode) {
					return tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).map(tank->tank.getFluidInTank(1).getAmount()).orElse(0);
				}
				else {
					return tile.getCapability(BacteriaCapability.BACT_CAPABILITY).map(bact->(int)Math.round(bact.getBact())).orElse(0);
				}
			}
		return 0;
		}
		return 0;
	}
	
	@Override
	public void read(BlockState state, CompoundNBT nbt) {
		super.read(state, nbt);
		this.operator = nbt.getInt("operator");
		this.amount = nbt.getInt("amount");
		this.mode = nbt.getBoolean("mode");	
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound) {
		CompoundNBT nbt = super.write(compound);
		nbt.putInt("operator", this.operator);
		nbt.putInt("amount", this.amount);
		nbt.putBoolean("moder", this.mode);
		return nbt;
	}
	
	@Override
	public void handleUpdateTag(BlockState state, CompoundNBT tag) {
		super.read(state, tag);
	}
}
