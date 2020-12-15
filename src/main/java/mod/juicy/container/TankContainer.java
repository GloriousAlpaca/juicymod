package mod.juicy.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;

public class TankContainer extends Container{

	protected TankContainer(ContainerType<?> type, int id) {
		super(type, id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		// TODO Auto-generated method stub
		return false;
	}

}
