package mod.juicy.datagen.lang;

import mod.juicy.Juicy;
import mod.juicy.block.BlockHolder;
import mod.juicy.item.ItemHolder;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class LanguageEnglish extends LanguageProvider{

	public LanguageEnglish(DataGenerator gen) {
		super(gen, Juicy.MODID, "en_us");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void addTranslations() {
		addBlock(()->BlockHolder.ALERT_BLOCK, "Alert Block");
		addBlock(()->BlockHolder.GENERATOR_BLOCK, "Gas Generator");
		addBlock(()->BlockHolder.GUTTER_BLOCK, "Juice Gutter");
		addBlock(()->BlockHolder.TANK_BLOCK, "Fermentation Tank Block");
		addBlock(()->BlockHolder.TANK_CONTROLLER_BLOCK, "Fermentation Tank Controller");
		addBlock(()->BlockHolder.THERM_BLOCK, "Thermostat Block");
		addBlock(()->BlockHolder.VALVE_BLOCK, "Valve Block");
		addFluid("mobjuice", "Mob Juice");
		addFluid("mobgas", "Mob Gas");
		addItem(()->ItemHolder.JUICE_BUCKET, "Juice Bucket");
		addItem(()->ItemHolder.PROBE, "Bacteria Probe");
		addItem(()->ItemHolder.PLATE, "Corrosion-Proof Plate");
		add("button.set", "Set");
		add("button.juice", "Juice");
		add("button.bacteria", "Bacteria");
		add("itemGroup.juicy", "Juicy");
		add("information.probeitem", "Bacteria: ");
		add("title.intake", "Intake");
		add("title.temp", "Temperature");
		addScreen("alert", "Alert Menu");
		addScreen("generator", "Gas Generator");
		addScreen("tank", "Tank");
		addScreen("thermostat", "Temperature Control");
		addScreen("valve", "Flow Control");
	}

	public void addScreen(String identifier, String name){
		add("screen.juicy."+identifier, name);
	}
	
	public void addFluid(String identifier, String name) {
		add("fluid.juicy."+identifier+"_still", name);
	}
}
