package dev.reyaan;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.abilities.Abilities;
import com.cobblemon.mod.common.api.abilities.Ability;
import com.cobblemon.mod.common.api.moves.Moves;
import com.cobblemon.mod.common.api.pokemon.Natures;
import com.cobblemon.mod.common.api.pokemon.stats.Stats;
import com.cobblemon.mod.common.api.types.ElementalTypes;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.PokemonStats;
import dev.reyaan.suppliers.PokemonStatSetterFunctionSupplier;
import dev.reyaan.suppliers.SetterFunctionSupplier;
import dev.reyaan.suppliers.StatSetterFunctionSupplier;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class PokemonFormat {
    public static final Map<Pattern, SetterFunctionSupplier> fields = new HashMap<>();

    static void init() {
        basicSupplier("Ability", "\\w+(?: \\w+)*", (Pokemon p, String s) -> {
            var a = Abilities.INSTANCE.get(s);
            p.setAbility(new Ability(a != null ? a : Abilities.INSTANCE.getDUMMY(), false));
        });
        basicSupplier("Level", "\\d", (Pokemon p, String f) -> p.setLevel(Integer.parseInt(f)));
        basicSupplier("Dynamax Level", "\\d", (Pokemon p, String f) -> p.setDmaxLevel(Integer.parseInt(f)));
        basicSupplier("Happiness", "\\d", (Pokemon p, String f) -> p.setFriendship(Integer.parseInt(f), false));
        basicSupplier("Tera Type", "\\w", (Pokemon p, String s) -> {
            var t = ElementalTypes.INSTANCE.get(s);
            p.setTeraType(t != null ? t : ElementalTypes.INSTANCE.getNORMAL());
        });
        basicSupplier(Pattern.compile("(\\w) Nature"), (Pokemon p, String s) -> {
            var n = Natures.INSTANCE.getNature(new Identifier(Cobblemon.MODID, s));
            p.setNature(n != null ? n : Natures.INSTANCE.getRandomNature());
        });
        basicSupplier("Shiny", "Yes", (Pokemon p, String s) -> {
            var t = ElementalTypes.INSTANCE.get(s);
            p.setTeraType(t != null ? t : ElementalTypes.INSTANCE.getNORMAL());
        });

        statSupplier("EVs", Pokemon::getEvs);
        statSupplier("IVs", Pokemon::getIvs);

        basicSupplier(Pattern.compile("- \\w+(?: \\w+)*"), (Pokemon p, String s) -> p.getMoveSet().add(Moves.INSTANCE.getByNameOrDummy(s).create()));

    }

    static void basicSupplier(String key, String regex, SetterFunctionSupplier supplier) {
        basicSupplier(Pattern.compile("^%s: (%s)".formatted(key, regex)), supplier);
    }

    static void basicSupplier(Pattern pattern, SetterFunctionSupplier supplier) {
        fields.put(pattern, supplier);
    }

    static void statSupplier(String key, PokemonStatSetterFunctionSupplier supplier) {
        var stat_pattern = Pattern.compile("(\\d+) (\\w+)");
        basicSupplier(key, ".*", (Pokemon p, String s) -> {
            var unformattedStats = s.split("/");

            for (String stat : unformattedStats) {
                var m = stat_pattern.matcher(stat.toLowerCase());
                var amount = m.group(0);
                var type = m.group(1);

                Map<String, StatSetterFunctionSupplier<?>> statMap = Map.of(
                        "hp", (PokemonStats stats, Integer n) -> stats.set(Stats.HP, n),
                        "atk", (PokemonStats stats, Integer n) -> stats.set(Stats.ATTACK, n),
                        "def", (PokemonStats stats, Integer n) -> stats.set(Stats.DEFENCE, n),
                        "spdef", (PokemonStats stats, Integer n) -> stats.set(Stats.SPECIAL_DEFENCE, n),
                        "spa", (PokemonStats stats, Integer n) -> stats.set(Stats.SPECIAL_ATTACK, n),
                        "spe", (PokemonStats stats, Integer n) -> stats.set(Stats.SPEED, n)
                );
                if (statMap.containsKey(type)) {
                    statMap.get(type).set(supplier.get(p), Integer.parseInt(amount));
                }
            }
        });
    }
}
