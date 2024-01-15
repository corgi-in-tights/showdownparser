# showdownparser
fabric 1.20.1 library

converts the pokemon showdown format to a cobblemon `Pokemon` using the power of REGEX
seriously there is a lot of regex.. only the title uses lookaheads and stuff though, rest is pretty basic


__this thing vv__
```
beat em up (Lucario) @ Choice Scarf  
Ability: Justified  
Tera Type: Fighting  
EVs: 4 HP / 252 Atk / 252 Spe  
Jolly Nature  
- Earthquake  
- Brick Break  
- Crunch  
- Ice Punch
```

becomes __this thing vv__

how am i supposed to represent this


ill probably get to adding it to jitpack one of these days.. just yoink the files till then or download the build in releases and include that, a credit or two would be appreciated so people know about this

files:
- 1 accessor mixin (because `CobblemonHeldItemManager#remaps` is private)
- 3 interfaces (for no good reason)
- 1 method (`ShowdownParserAPI#fromFormat`)

NOTES:
- the game will crash if you pass an invalid title or line or itll return a random pokemon - maybe, you can surround it in a try catch so it does not
- **please only run `fromFormat` AFTER species and berries have been initialized
- idk if berries work as held items im pretty sure not lol, ill get to fixing that some day

