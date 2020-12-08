package mod.juicy.capability;

import java.util.concurrent.Callable;

public class BacteriaFactory implements Callable<IBacteriaCapability> {

	@Override
	public IBacteriaCapability call() throws Exception {
		return new BacteriaCapability();
	}

}
