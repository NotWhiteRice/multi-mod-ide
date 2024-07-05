# Multi-mod IDE

## Included mods
- Endless Skies v0.1.1-alpha
- Circuits v0.2.2-alpha

## Versioning
- <MAJOR>.<major>.<minor>.<patch>-<build>
  - MAJOR: Increments when updating to a new version of Minecraft
  - major: Increments with api-breaking changes
  - minor: Increments with minor changes
  - patch: Bug fixes
  - build: For alpha and beta versions of the mod

## TODO
- [ ] Reimplement things from v3
  - [ ] ContextBase
  - [ ] InnerContextBase
  - [ ] ModContext
    - [ ] ModRegistry (incorporated to other classes and removed)
  - [ ] BlockContext
    - [ ] ItemLikeContext
    - [ ] BlockStateProviderTag
  - [ ] ItemContext
    - [ ] ItemLikeContext
    - [ ] ItemStateProviderTag
  - [ ] CreativeModeTabContext
    - [ ] CreativeTabEntryFactory (renamed CreativeModeTabContext.TabEntry in its own file)
    - [ ] ModEvents (stays in its own class)
  - [ ] DataGenEvents
    - [ ] ModBlockStateProvider
    - [ ] ModItemModelProvider
    - [ ] ModLootTableProvider (renamed)
    - [ ] ModBlockLootSubProvider
- [ ] Implement some new classes to v4
  - [X] BlockFactory
  - [X] ItemFactory
  - [X] StringHelper
    - [X] constructUniqueID(original, keySet)
- [ ] Remove DualRegistryException and instead construct a unique id for duplicates 
## Mod Changelog 
- Endless Skies v1.2.1.0-alpha (in development)
- Endless Skies v1.2.0.0-alpha
  - Moved everything to src/test, including the old mod folder and an attempt at remaking the api, so the framework can be more secure
  - Removed Circuits from the mods.toml
  - Changed the gradle.properties, build.gradle, and mods.toml to use mod___ instead of lib___ when referring to Endless Skies
- Endless Skies v1.1.0.0-alpha
  - Implemented ModContext, BlockContext, ItemContext, and CreativeModeTabContext
  - Implemented ModRegistry and functions to create blocks, items, and creative mode tabs from the ModContext returned by ModRegistry.registerMod(modID)
  - Implemented functions to customize custom creative mode tabs along with adding custom items to vanilla creative mode tabs
  - Implemented functions to create a mod-specific tab, which automatically inserts all blocks and items registered by the mod prior to that point
  - Implemented capacity to expedite block state and item model generation
    - Allows for blocks without items
    - Allows for blocks and items with a pre-existing custom model
    - Allows for handheld items
    - Allows for blocks and items to have a default, provided model and texture, used if datagen is not specified
    - Allows for skipping data generation entirely
  - Transitioned the init classes to using contexts instead of registry objects
  - Added a test block and item with a custom texture
  - Added a new creative mode tab for all "dev" items in the Endless SKies mod
  - Added the Rock Crusher with a menu with an animated progress bar and hardcoded gravel recipe
- Endless Skies v1.0.1.0-alpha
  - The progress arrow in the Mineral Infuser menu now has the proper animation
  - Fixed the display name for the Mineral Infuser using the wrong modID
  - Added unimplemented ModRegistry and ModContext classes
  - Added en_us.json
  - Added a default model to the Mineral Infuser
- Endless Skies v1.0.0.0-alpha
  - Did a history reset and reverted the version number to v1.0.0.0-alpha
  - Added crude datagen
  - Added capabilities for planned future updates far in advance
  - Added a unimplemented hand wrench item
  - Added a Mineral Enricher with a menu and hardcoded gilded blackstone recipe
  - Added preparations for JEI compatibility
  - Implemented unused base classes for tile entities w/ and w/o menus
- Circuits v1.0.0.0-alpha (in hiatus)
  - Did a history reset and reverted the version number to v1.0.0.0-alpha
  - Added crude datagen