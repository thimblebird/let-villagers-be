# ![mod icon representing a pixelated 8x8 villager head](src/main/resources/assets/letvillagersbe/icon_32x32.png) Let Villagers Be [Fabric]

[![mod loader: fabric](https://img.shields.io/badge/loader-fabric-lightyellow?style=flat-square)](https://fabricmc.net)
![version 1.0.1](https://img.shields.io/badge/version-1.0.1-lightgreen?style=flat-square)
![minecraft 1.19.3](https://img.shields.io/badge/minecraft-1.19.3-yellowgreen?style=flat-square)
[![show on curseforge](https://img.shields.io/badge/curseforge-mod-red?style=flat-square&logo=curseforge)](https://www.curseforge.com/minecraft/mc-mods/let-villagers-be)
[![license: mit](https://img.shields.io/badge/license-mit-lightblue?style=flat-square)](LICENSE)

## Features
- supported environments: **[ *client+server* || *server-only* ]**
- **all features are configurable** (path: *`/config/letvillagersbe-config.json5`*)
  - with [Mod Menu](https://www.curseforge.com/minecraft/mc-mods/modmenu) support!
- players can't right-click villagers out of their beds
- players can't left-click damage sleeping villagers
- players can't break the bed a villager is sleeping in
- villagers can't be moved/pushed while sleeping
- **... and more!** *(like one or two, tops)*

## Known Issues
- translations only work out-of-the-box when installing the mod both on the client and the server
  - if installed server-side only, translations won't work *(yet)* but vanilla clients can still connect and use the mod!
    - you can disable all messages in the `showMessageWhen` section of the server config, see [#3 (comment)](https://github.com/thimblebird/let-villagers-be/issues/3#issuecomment-1289626596)
  - *NOTE: maybe look into [Server Translations API](https://github.com/NucleoidMC/Server-Translations) to make translations for server-side only installations work*
