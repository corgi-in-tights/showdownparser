package dev.reyaan.mixin;


import com.cobblemon.mod.common.api.pokemon.helditem.HeldItemManager;
import com.cobblemon.mod.common.pokemon.helditem.BaseCobblemonHeldItemManager;
import com.cobblemon.mod.common.pokemon.helditem.CobblemonHeldItemManager;
import com.google.common.collect.HashBiMap;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(CobblemonHeldItemManager.class)
public interface RemapAccessor {
    @Accessor(value = "remaps", remap = false)
    Map<Item, String>  getRemaps();
}
