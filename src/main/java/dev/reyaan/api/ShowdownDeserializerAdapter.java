package dev.reyaan.api;

import com.google.gson.*;
import com.cobblemon.mod.common.pokemon.Pokemon;

import java.lang.reflect.Type;
import java.util.Arrays;

public class ShowdownDeserializerAdapter implements JsonDeserializer<Pokemon> {

    @Override
    public Pokemon deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        return ShowdownParserAPI.fromFormat(Arrays.stream(json.getAsString().split("\n")).toList());
    }
}
