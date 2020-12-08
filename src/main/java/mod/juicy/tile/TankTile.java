package mod.juicy.tile;

import mod.juicy.Juicy;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TankTile extends TileEntity implements ITickableTileEntity{

	public TankTile() {
		super(TileHolder.TILE_TANK_TYPE);
		Juicy.LOGGER.info("NEW TANK!");
	}

	@Override
	public void tick() {
		
	}


}
