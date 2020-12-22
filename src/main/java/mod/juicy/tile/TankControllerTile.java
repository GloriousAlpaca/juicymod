package mod.juicy.tile;

import java.util.ArrayDeque;
import java.util.Vector;

import javax.annotation.Nullable;

import mod.juicy.Config;
import mod.juicy.Juicy;
import mod.juicy.block.BlockHolder;
import mod.juicy.block.TankBlock;
import mod.juicy.capability.BacteriaCapability;
import mod.juicy.capability.IBacteriaCapability;
import mod.juicy.capability.JuiceTank;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class TankControllerTile extends TileEntity implements ITickableTileEntity {
	static Vector3i[] neighbours = { new Vector3i(1, 0, 0), new Vector3i(-1, 0, 0), new Vector3i(0, 1, 0),
			new Vector3i(0, -1, 0), new Vector3i(0, 0, 1), new Vector3i(0, 0, -1) };
	Vector<BlockPos> multiBlock;
	Vector<AlertTile> alerts;
	
	IBacteriaCapability bacteria;
	JuiceTank juice;
	double temperature;

	public TankControllerTile() {
		super(TileHolder.TILE_TANK_CONTROLLER_TYPE);
		bacteria = new BacteriaCapability(Integer.MAX_VALUE);
		juice = new JuiceTank(FluidAttributes.BUCKET_VOLUME, 500);
		multiBlock = new Vector<BlockPos>();
		alerts = new Vector<AlertTile>();
		temperature = 20;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return (LazyOptional<T>) LazyOptional.of(() -> juice);
		} else if (cap == BacteriaCapability.BACT_CAPABILITY) {
			return (LazyOptional<T>) LazyOptional.of(() -> bacteria);
		}
		return super.getCapability(cap, side);
	}

	@Override
	public void tick() {
		if (!this.getWorld().isRemote) {
			if(bacteria.getBact()>0) {
			bacteria.setLimit((int) Math.round(juice.getFluidAmount()*Config.TANK_BPERJ.get()));
			double newBact = bacteria.growBact(temperature, juice.getFluidAmount());
			Juicy.LOGGER.info("Bacteria Amount: "+newBact);
			bacteria.setBact(newBact);
			int drained = juice.removeFluid(Math.max((int) Math.round(newBact*Config.TANK_GPERB.get()), 1), false);
			Juicy.LOGGER.info("Juice Drained: "+drained);
			int bonus = (int) Math.round(Config.TANK_GBONUS.get()*Math.abs(newBact-juice.getFluidAmount()));
			int added = juice.addGas(drained+bonus, false);
			Juicy.LOGGER.info("Gas Added: "+added);
			notifyAlertBlocks();
			this.markDirty();
			}
		}
	}

	public void addAlert(AlertTile pAlert) {
		this.alerts.add(pAlert);
	}
	
	public void removeAlert(AlertTile pAlert) {
		this.alerts.remove(pAlert);
	}
	
	public void notifyAlertBlocks() {
		if (!alerts.isEmpty()) {
			alerts.forEach(alert->world.notifyNeighborsOfStateChange(alert.getPos(), BlockHolder.ALERT_BLOCK));
		}
	}
	
	public void setMultiBlock(Vector<BlockPos> tankPos) {
		multiBlock = tankPos;
	}

	public Vector<BlockPos> getMultiBlock(){
		return multiBlock;
	}
	
	public Vector<BlockPos> addtoMultiBlock(BlockPos tankPos) {
		multiBlock.add(tankPos);
		return multiBlock;
	}

	public Vector<BlockPos> removeFromMultiBlock(BlockPos tankPos) {
		multiBlock.remove(tankPos);
		return multiBlock;
	}
	
	public Vector<BlockPos> searchMultiBlock() {
		return searchMultiBlock(new Vector<BlockPos>());
	}
	
	public Vector<BlockPos> searchMultiBlock(Vector<BlockPos> marked) {
		ArrayDeque<BlockPos> queue = new ArrayDeque<BlockPos>();
		queue.add(this.pos);
		while (!queue.isEmpty()) {
			BlockPos cPos = queue.getFirst();
			for (int i = 0; i < 6; i++) {
				BlockPos n = cPos.add(neighbours[i]);
				if (!marked.contains(n))
					if (this.getWorld().getBlockState(n).getBlock() instanceof TankBlock) {
						marked.add(n);
						queue.add(n);
					}
			}
			queue.pop();
		}
		return marked;
	}
	
	/**
	 * Checks the multiBlock Vector for tank occurrences Call this Method sparingly!
	 * 
	 * @return How many Tanks are in the multiBlock vector
	 */
	public int getCapacity() {
		int capacity = 0;
		for (int i = 0; i < multiBlock.size(); i++) {
			capacity += this.getWorld().getTileEntity(multiBlock.get(i)) instanceof TankTile ? 1 : 0;
		}
		Juicy.LOGGER.info("CAPACITY: "+capacity);
		return capacity;
	}

	public void updateCapacity() {
		int cap = this.getCapacity();
		juice.setTankCapacity(1,  cap * Config.TANK_JCAPPERBLOCK.get() + FluidAttributes.BUCKET_VOLUME);
		juice.setTankCapacity(2,  Math.round((cap * Config.TANK_JCAPPERBLOCK.get() + FluidAttributes.BUCKET_VOLUME)/2f));
	}

	public void setTemperature(double pTemp) {
		this.temperature = pTemp;
	}

	public void setFlow(int pFlow) {
		juice.setIntake(pFlow);
	}
	
	/**
	 * Sets this as the controller for the TankTiles at the BlockPos of the Vector.
	 * 
	 * @param tanks Blockpos of the tankblocks
	 */
	public void announceController(Vector<BlockPos> tanks) {
		tanks.forEach(tankpos ->{
			TileEntity tile = this.getWorld().getTileEntity(tankpos);
			if(tile != null)
			if(tile instanceof TankSlaveTile)
				((TankSlaveTile) tile).setController(this.getPos());
		});
	}

	/**
	 * Sets null as the controller for the TankTiles at the BlockPos of the Vector.
	 * 
	 * @param tanks Blockpos of the tankblocks
	 */
	public void renounceController() {
		if(!multiBlock.isEmpty())
		multiBlock.forEach(tankpos ->{
			TileEntity tile = this.getWorld().getTileEntity(tankpos);
			if(tile != null)
			if(tile instanceof TankSlaveTile)
				((TankSlaveTile) tile).setController(null);
		});
	}
	
	@Override
	public void read(BlockState state, CompoundNBT nbt) {
		super.read(state, nbt);
		BacteriaCapability.BACT_CAPABILITY.readNBT(bacteria, null, nbt.get("bacteria"));
		juice.readFromNBT((CompoundNBT) nbt.get("juice"));
		int[] multiX = nbt.getIntArray("multiblockx");
		int[] multiY = nbt.getIntArray("multiblocky");
		int[] multiZ = nbt.getIntArray("multiblockz");
		multiBlock = new Vector<BlockPos>();
		for(int i=0;i<multiX.length;i++) {
			multiBlock.add(new BlockPos(multiX[i], multiY[i], multiZ[i]));
		}
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		CompoundNBT nbt = super.write(compound);
		nbt.put("bacteria",BacteriaCapability.BACT_CAPABILITY.writeNBT(bacteria, null));
		nbt.put("juice",juice.writeToNBT(new CompoundNBT()));
		int[] multiX = new int[multiBlock.size()];
		int[] multiY = new int[multiBlock.size()];
		int[] multiZ = new int[multiBlock.size()];
		for(int i=0;i<multiBlock.size();i++) {
			multiX[i] = multiBlock.get(i).getX();
			multiY[i] = multiBlock.get(i).getY();
			multiZ[i] = multiBlock.get(i).getZ();
		}
		nbt.putIntArray("multiblockx", multiX);
		nbt.putIntArray("multiblocky", multiY);
		nbt.putIntArray("multiblockz", multiZ);
		return nbt;
	}

	@Override
	public void handleUpdateTag(BlockState state, CompoundNBT tag) {
		super.read(state, tag);
	}

	public double getTemp() {
		return temperature;
	}
	
	public int getFlow() {
		return juice.getIntake();
	}
}
