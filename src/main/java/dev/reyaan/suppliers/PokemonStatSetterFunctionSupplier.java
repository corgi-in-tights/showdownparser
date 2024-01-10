package dev.reyaan.suppliers;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.PokemonStats;

public interface PokemonStatSetterFunctionSupplier {
    PokemonStats get(Pokemon pokemon);
}
