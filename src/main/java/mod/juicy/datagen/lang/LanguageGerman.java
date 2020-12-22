package mod.juicy.datagen.lang;

import mod.juicy.Juicy;
import mod.juicy.block.BlockHolder;
import mod.juicy.item.ItemHolder;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class LanguageGerman extends LanguageProvider{

	public LanguageGerman(DataGenerator gen) {
		super(gen, Juicy.MODID, "de_de");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void addTranslations() {
		addBlock(()->BlockHolder.ALERT_BLOCK, "Meldungs Block");
		addBlock(()->BlockHolder.GENERATOR_BLOCK, "Gas Generator");
		addBlock(()->BlockHolder.GUTTER_BLOCK, "Saft Rinne");
		addBlock(()->BlockHolder.TANK_BLOCK, "Fermentations Tank");
		addBlock(()->BlockHolder.TANK_CONTROLLER_BLOCK, "Tank-Steuerungs Block");
		addBlock(()->BlockHolder.THERM_BLOCK, "Thermostat Block");
		addBlock(()->BlockHolder.VALVE_BLOCK, "Ventil Block");
		addFluid("mobjuice", "Mob Saft");
		addFluid("mobgas", "Mob Gas");
		addItem(()->ItemHolder.JUICE_BUCKET, "Saft Eimer");
		addItem(()->ItemHolder.PROBE, "Bakterien-Pipette");
		addItem(()->ItemHolder.PLATE, "Rostfeste Platte");
		add("button.set", "Setzen");
		add("button.juice", "Saft");
		add("button.bacteria", "Bakterien");
		add("itemGroup.juicy", "Juicy");
		add("information.probeitem", "Bakterien: ");
		add("title.intake", "Zufuhr");
		add("title.temp", "Temperatur");
		addScreen("alert", "Meldungs Menü");
		addScreen("generator", "Gas Generator");
		addScreen("tank", "Tank");
		addScreen("thermostat", "Temperatur Steuerung");
		addScreen("valve", "Fluss Steuerung");
	}

	public void addScreen(String identifier, String name){
		add("screen.juicy."+identifier, name);
	}
	
	public void addFluid(String identifier, String name) {
		add("fluid.juicy."+identifier+"_still", name);
	}
}
