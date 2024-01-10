package dev.reyaan.suppliers;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.PokemonStats;

public interface StatSetterFunctionSupplier<T> {
    void set(PokemonStats pokemon, Integer t);
}
