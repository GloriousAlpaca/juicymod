package mod.juicy.tile;

import java.util.ArrayDeque;
import java.util.Vector;

import mod.juicy.Juicy;
import mod.juicy.block.TankBlock;
import mod.juicy.capability.BacteriaCapability;
import mod.juicy.capability.IBacteriaCapability;
import mod.juicy.capability.JuiceTank;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class TankControllerTile extends TileEntity implements ITickableTileEntity{
	static Vector3i[] neighbours = {new Vector3i(1, 0, 0), new Vector3i(-1, 0, 0), new Vector3i(0, 1, 0), new Vector3i(0, -1, 0), new Vector3i(0, 0, 1), new Vector3i(0, 0, -1)};
	Vector<BlockPos> multiBlock;
	IBacteriaCapability bacteria;
	JuiceTank juice;
	
	public TankControllerTile() {
		super(TileHolder.TILE_TANK_CONTROLLER_TYPE);
		bacteria = new BacteriaCapability(Integer.MAX_VALUE);
		juice = new JuiceTank(FluidAttributes.BUCKET_VOLUME*2, FluidAttributes.BUCKET_VOLUME);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		Juicy.LOGGER.info("CONTROLLER CAPABILITY");
		if(cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
			return (LazyOptional<T>) LazyOptional.of(()->juice);
		}
		else if(cap == BacteriaCapability.BACT_CAPABILITY) {
			return (LazyOptional<T>) LazyOptional.of(()->bacteria);
		}
		return LazyOptional.empty();
	}
	
	@Override
	public void tick() {
		if(juice.getFluidAmount()>0) {
		juice.removeFluid(1);
		juice.addGas(1);
		}
		Juicy.LOGGER.info("Gas Amount: "+juice.getFluidInTank(2));
	}
	
	public Vector<BlockPos> setMultiBlock(Vector<BlockPos> tankPos){
		multiBlock = tankPos;
		return multiBlock;
	}
	
	public Vector<BlockPos> addtoMultiBlock(BlockPos tankPos){
		multiBlock.add(tankPos);
		return multiBlock;
	}
	
	/**
	 * Checks the multiBlock Vector for tank occurrences
	 * Call this Method sparingly!
	 * @return How many Tanks are in the multiBlock vector
	 */
	public int getCapacity() {
		int capacity=0;
		for(int i=0;i<multiBlock.size();i++) {
			capacity += this.getWorld().getTileEntity(multiBlock.get(i)) instanceof TankTile ? 1 : 0;
		}
		return capacity;
	}
	
	public void updateCapacity() {
		juice.setCapacity(this.getCapacity()*FluidAttributes.BUCKET_VOLUME+FluidAttributes.BUCKET_VOLUME);
	}
	
	public Vector<BlockPos> searchMultiBlock(){
		Vector<BlockPos> marked = new Vector<BlockPos>();
		ArrayDeque<BlockPos> queue = new ArrayDeque<BlockPos>();
		queue.add(this.pos);
		while(!queue.isEmpty()) {
			BlockPos cPos = queue.getFirst();
			for (int i = 0; i < 6; i++) {
				BlockPos n = cPos.add(neighbours[i]);
				if(!marked.contains(n))
				if(this.getWorld().getBlockState(n).getBlock() instanceof TankBlock) {
				marked.add(n);
				queue.add(n);
				}
			}
			queue.pop();
		}
		return marked;
	}
	
	/**
	 * Sets this as the controller for the TankTiles at the BlockPos of the Vector.
	 * @param tanks Blockpos of the tankblocks
	 */
	public void announceController(Vector<BlockPos> tanks) {
		//TODO Remove Crash: NullPointerException
		tanks.forEach(cpos->{
			if(this.getWorld().getTileEntity(cpos) instanceof TankControllerTile)
				tanks.remove(cpos);
		});
		tanks.forEach(tank -> ((TankTile) world.getTileEntity(tank)).setController(this.getPos()));
	}
	
	@Override
	public void read(BlockState state, CompoundNBT nbt)
	{
		super.read(state, nbt);
		nbt.getInt("juice");
	}
		
	@Override
	public CompoundNBT write(CompoundNBT compound) {
		CompoundNBT nbt = super.write(compound);
		nbt.putInt("juice", this.juice.getFluidInTank(1).getAmount());
		nbt.putInt("gas", this.juice.getFluidInTank(2).getAmount());
		return nbt;
	}
	
}
