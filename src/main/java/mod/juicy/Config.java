package mod.juicy;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
	public static final String CATEGORY_GENERAL = "block";
	public static final String SUBCATEGORY_TANK = "tank";
	public static final String SUBCATEGORY_GENERATOR = "generator";
	public static final String SUBCATEGORY_GUTTER = "gutter";
	
	//Tank Fields:
	public static ForgeConfigSpec.DoubleValue TANK_BPERJ;
	public static ForgeConfigSpec.DoubleValue TANK_GPERB;
	public static ForgeConfigSpec.DoubleValue TANK_GBONUS;
	public static ForgeConfigSpec.DoubleValue TANK_DEATHPERTICK;
	public static ForgeConfigSpec.DoubleValue TANK_DEATHMOD;
	public static ForgeConfigSpec.DoubleValue TANK_RECOVERMOD;
	public static ForgeConfigSpec.IntValue TANK_JCAPPERBLOCK;
	
	//Generator Fields:
	public static ForgeConfigSpec.IntValue GENERATOR_RFPERGAS;
	public static ForgeConfigSpec.DoubleValue GENERATOR_BURNMULT;
	public static ForgeConfigSpec.IntValue GENERATOR_ENERGYCAP;
	public static ForgeConfigSpec.IntValue GENERATOR_GASCAP;
	
	//Gutter Fields:
	public static ForgeConfigSpec.IntValue GUTTER_MAXJPERMOB;
	public static ForgeConfigSpec.IntValue GUTTER_JUICECAP;
	public static ForgeConfigSpec.IntValue GUTTER_HEIGHT;
	
	public static ForgeConfigSpec SERVER_CONFIG;
	  
	static {

        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();

        SERVER_BUILDER.comment("Block Settings").push(CATEGORY_GENERAL);
        setupGutterConfig(SERVER_BUILDER);
        setupTankConfig(SERVER_BUILDER);
        setupGeneratorConfig(SERVER_BUILDER);
        SERVER_BUILDER.pop();


        SERVER_CONFIG = SERVER_BUILDER.build();
    }
	
	private static void setupTankConfig(ForgeConfigSpec.Builder SERVER_BUILDER) {
		SERVER_BUILDER.comment("Fermentation Tank Settings").push(SUBCATEGORY_TANK);
		TANK_JCAPPERBLOCK = SERVER_BUILDER.comment("Capacity per Tank Block")
                .defineInRange("capPerTank", 1000, 1, Integer.MAX_VALUE);
		TANK_BPERJ = SERVER_BUILDER.comment("Amount of Bacteria per mb of Juice")
                .defineInRange("bacteriaPerJuice", 10., 0., Double.MAX_VALUE);
		TANK_GPERB = SERVER_BUILDER.comment("Amount of Gas per Bacteria per Tick")
                .defineInRange("gasPerBacteria", 0.001, 0., Double.MAX_VALUE);
		TANK_GBONUS = SERVER_BUILDER.comment("Amount of Bonus-Gas per difference between Bacteria and Juice")
                .defineInRange("gasBonus", 0.001, 0., Double.MAX_VALUE);
		TANK_DEATHPERTICK = SERVER_BUILDER.comment("Amount of Death added per Tick")
                .defineInRange("deathPerTick", 0.1, 0., Double.MAX_VALUE);
		TANK_DEATHMOD = SERVER_BUILDER.comment("Amount of Bacteria dying per Deathpoint")
                .defineInRange("deathMod", 0.01, 0., Double.MAX_VALUE);
		TANK_RECOVERMOD = SERVER_BUILDER.comment("Recovery-Multiplier")
                .defineInRange("recoverMod", 0.001, 0., Double.MAX_VALUE);
		SERVER_BUILDER.pop();
	}
	
	private static void setupGeneratorConfig(ForgeConfigSpec.Builder SERVER_BUILDER) {
		SERVER_BUILDER.comment("Generator Settings").push(SUBCATEGORY_GENERATOR);
		GENERATOR_RFPERGAS = SERVER_BUILDER.comment("RF per mb of Gas")
                .defineInRange("rfPerGas", 10, 1, Integer.MAX_VALUE);
		GENERATOR_BURNMULT = SERVER_BUILDER.comment("Gas Burn Multiplier")
                .defineInRange("burnMult", 10., 1., Double.MAX_VALUE);
		GENERATOR_ENERGYCAP = SERVER_BUILDER.comment("Energy Capacity of the Generator")
                .defineInRange("energyCap", 20000, 1, Integer.MAX_VALUE);
		GENERATOR_GASCAP = SERVER_BUILDER.comment("Gas Capacity of the Generator")
                .defineInRange("gasCap", 4000, 1, Integer.MAX_VALUE);
		SERVER_BUILDER.pop();
	}
	
	private static void setupGutterConfig(ForgeConfigSpec.Builder SERVER_BUILDER) {
		SERVER_BUILDER.comment("Juicegutter Settings").push(SUBCATEGORY_GUTTER);
		GUTTER_MAXJPERMOB = SERVER_BUILDER.comment("Max Amount of Juice generated per Mob")
                .defineInRange("juicePerMob", 10, 1, Integer.MAX_VALUE);
		GUTTER_JUICECAP = SERVER_BUILDER.comment("Juice Capacity of the Juicegutter")
                .defineInRange("juiceCap", 1000, 1, Integer.MAX_VALUE);
		GUTTER_HEIGHT = SERVER_BUILDER.comment("Maximal Height of Death to collect Juice")
                .defineInRange("collectHeight", 1000, 1, Integer.MAX_VALUE);
		SERVER_BUILDER.pop();
		
	}
	
}
