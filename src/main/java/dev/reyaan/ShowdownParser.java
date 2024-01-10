package dev.reyaan;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.pokemon.Pokemon;
import dev.reyaan.api.ShowdownParserAPI;
import kotlin.Unit;
import net.fabricmc.api.DedicatedServerModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static dev.reyaan.PokemonFormat.fields;

public class ShowdownParser implements DedicatedServerModInitializer {
	public static String MOD_ID = "showdownparser";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	
	@Override
	public void onInitializeServer() {
		PokemonFormat.init();
	}
}