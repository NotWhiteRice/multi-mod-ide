# Multi-mod IDE

## Included mods
- Endless Skies v0.1.0-alpha
- Circuits v0.2.0-alpha

## Versioning
### Alpha/Beta versions
- vMAJOR.MINOR.PATCH-BUILD
  - MAJOR:
    - Increments each public release
  - MINOR:
    - Increments when significant changes happen
  - PATCH:
    - Increments for minor changes and bug fixes
    - Changes to Endless Skies will increment PATCH should they only affect the low-level workings of the mod and not the higher level interactions
### Public versions
- vMCVERSION-MAJORMOD.MAJORAPI.MINOR.PATCH
  - https://docs.minecraftforge.net/en/1.20.x/gettingstarted/versioning/
  - Changes to items/blocks in Endless Skies will increment PATCH should they only serve as examples before and after

## Mod Changelog 
- Endless Skies v0.1.0-alpha
  - Rebranded NWR Core Library v0.2.1-alpha as Endless Skies v0.1.0-alpha
  - Implemented a mod logger class
  - Moved RegisterBundle to registry.assets as a subclass of ModAssets
  - Renamed modRegisters in ModRegistry as mAssetsTable
  - Removed parameters from registerEventBus in ModRegistry
  - Renamed classes to mimic vanilla classes
  - Implemented FaceBlock and DirectionalFaceBlock from Circuits as PanelBlock and AimedPanelBlock
  - Added example blocks for FaceBlock and DirectionalFaceBlock
- Circuits v0.2.1-alpha
  - Renamed classes to mimic vanilla classes
  - Moved FaceBlock and DirectionalFaceBlock to NWR Core Library
  - Moved DirectionalPanelBlock to NWR Core Library