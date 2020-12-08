package mod.juicy.capability;

public interface IBacteriaCapability {

		/**
		 * Get the Amount of Bacteria.
		 * @return Current Amount of Bacteria
		 */
		public int getBact();
	  
		/**
		 * Set the Amount of Bacteria
		 * @param value Amount of Bacteria
		 * @return The actual Amount of Bacteria
		 */
		public int setBact(int value);
	  
		/**
		 * Add Bacteria to the Object
		 * @param maxReceive The maximal Amount added
		 * @param simulate Only simulate adding the bacteria?
		 * @return The actual Amount added
		 */
		public int receiveBact(int maxReceive, boolean simulate);
	  
		/**
		 * Extract Bacteria from the Object
		 * @param maxExtract How much should be extracted
		 * @param simulate Is the extraction only simulated
		 * @return The actual amount extracted
		 */
		public int extractBact(int maxExtract, boolean simulate);
	  
}
