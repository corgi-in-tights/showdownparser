package dev.reyaan.api;

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies;
import com.cobblemon.mod.common.pokemon.Gender;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import com.cobblemon.mod.common.pokemon.helditem.CobblemonHeldItemManager;
import dev.reyaan.PokemonFormat;
import dev.reyaan.mixin.RemapAccessor;
import dev.reyaan.suppliers.SetterFunctionSupplier;
import net.minecraft.item.Item;
import net.minecraft.text.Text;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import static dev.reyaan.PokemonFormat.fields;

public class ShowdownParserAPI {
    public static Pokemon fromFormat(List<String> lines) {
        Pokemon pokemon = new Pokemon();
        if (lines.isEmpty()) return pokemon;


        var title = lines.get(0);
        // ensure 2 spaces at the end for the regex
        if (!title.endsWith("  ")) title += "  ";

        var titlePattern = Pattern.compile("^(.*?) ?[@(]?(?:\\(((?!M|!F)\\w+)\\))? ?(?:\\(([M,F])\\))?(?: @ (\\w+(?: \\w+)*))?  ");
        var titleMatcher = titlePattern.matcher(title);

        if (!titleMatcher.matches() || titleMatcher.groupCount() == 0) return pokemon;

        var displayNameOrSpecies = titleMatcher.group(1);
        var nullableSpecies = titleMatcher.group(2);
        var nullableGender = titleMatcher.group(3);
        var nullableHeldItem = titleMatcher.group(4);


        Species species;
        if (nullableSpecies == null) { // 1st group is species, no display name
            species = PokemonSpecies.INSTANCE.getByName(displayNameOrSpecies.toLowerCase());
        } else { // 1st group is display name, 2nd is species
            pokemon.setNickname(Text.literal(displayNameOrSpecies));
            species = PokemonSpecies.INSTANCE.getByName(nullableSpecies.toLowerCase());
        }
        pokemon.setSpecies(species != null ? species : PokemonSpecies.INSTANCE.random());

        if (nullableGender != null) {
            if (nullableGender.equalsIgnoreCase("m")) pokemon.setGender(Gender.MALE);
            else if (nullableGender.equalsIgnoreCase("f")) pokemon.setGender(Gender.FEMALE);
            else pokemon.setGender(List.of(Gender.FEMALE, Gender.MALE).get(new Random().nextInt(2)));
        }

        if (nullableHeldItem != null) {
            // the ultimate unchecked cast
            Map<Item, String> remaps = ((RemapAccessor) (Object) CobblemonHeldItemManager.INSTANCE).getRemaps();
            var itemId = nullableHeldItem.toLowerCase().strip().replace(" ", "_");

            remaps.forEach((k, v) -> {
                if (itemId.equals(v)) {
                    pokemon.swapHeldItem(k.getDefaultStack(), false);
                }
            });
        }



        var data = lines.subList(1, lines.size());

        for (int i = 0; i < data.size(); i++) {
            String l = data.get(i);

             for (var entry : fields.entrySet()) {
                 Pattern pattern = entry.getKey();
                 var matcher = pattern.matcher(l);
                 if (matcher.matches()) {
                     entry.getValue().set(pokemon, matcher.group(1));
                     data.remove(i);
                 }
             }
        }

        return pokemon;
    }
}
