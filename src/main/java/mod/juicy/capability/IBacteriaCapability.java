package mod.juicy.capability;

import net.minecraft.nbt.CompoundNBT;

public interface IBacteriaCapability {

		/**
		 * Get the Amount of bacteria.
		 * @return Current Amount of bacteria
		 */
		public double getBact();
	  
		/**
		 * Get the Limit at which bacteria start to die.
		 * @return Current bacteria limit
		 */
		public int getLimit();
		
		/**
		 * Set the Amount of Bacteria
		 * @param value Amount of Bacteria
		 * @return The actual Amount of Bacteria
		 */
		public double setBact(double value);
	  
		/**
		 * Set the limit at which bacteria start to die.
		 * @param value Amount of Bacteria
		 */
		public void setLimit(int value);
		
		/**
		 * Add Bacteria to the Object
		 * @param maxReceive The maximal Amount added
		 * @param simulate Only simulate adding the bacteria?
		 * @return The actual Amount added
		 */
		public double receiveBact(double maxReceive, boolean simulate);
	  
		/**
		 * Extract Bacteria from the Object
		 * @param maxExtract How much should be extracted
		 * @param simulate Is the extraction only simulated
		 * @return The actual amount extracted
		 */
		public double extractBact(double maxExtract, boolean simulate);
	  
		public IBacteriaCapability readFromNBT(CompoundNBT nbt);
		
		public CompoundNBT writeToNBT(CompoundNBT nbt);
		
		/**
		 * Grow Bacteria in the Tank
		 * @param temp The Temperature in the Tank
		 * @param juice How much Juice is in the Tank
		 * @return
		 */
		public double growBact(double temp, int juice);
		
}
