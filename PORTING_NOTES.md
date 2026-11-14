# 26.2 port notes (not compiled - read this first)

This port was written without a compiler or the 26.2 game jar, so names were taken from
Mojang's mappings plus the Fabric/NeoForge 26.x porting guides. Expect a few compile errors.

Build: `./gradlew build` (JDK 25). Jar ends up in `build/libs/`.
Set `modmenu_version` in gradle.properties; check the other versions with fabricmc.net/develop/template.

## Changes vs 1.21.6
- Stonecutter and old version targets removed (single 26.2 target, Mojang names, no mappings).
- Formatting/ChatFormatting colours -> `util/Colors` RGB constants.
- Nametag drawing: the hand-written Font.draw code is replaced by `SubmitNodeCollector#submitNameTag`.
  Label heights are re-created by translating the PoseStack (+0.5/+1.0/+1.5 on top of vanilla's +0.5).
  The rank label now also gets the normal name tag background (it had none before).
- Resource pack reload hook (ServerResourcePackLoaderMixin) replaced by a 10 s timer after joining.
- NoxesiumListener: **not wired up** (Noxesium 3.x API differs). Handlers are kept as plain static
  methods; per-game nametags stay off until `init()` registers them.

## Most likely to need fixing (guessed names)
1. `LivingEntityRendererMixin` / `EntityRendererMixin`: `submit`, `extractRenderState`, `submitNameTag(...)` signature,
   `state.nameTagAttachment`, `state.isDiscrete`, `state.lightCoords`.
2. `GenericContainerScreenMixin`: `extractRenderState` + `GuiGraphicsExtractor` on `ContainerScreen`.
3. `FontManagerMixin` (`safeLoad`, `BuilderId`) and `BitmapFontLoaderMixin` (`BitmapProvider.Definition#load`).
4. `HudAccessor` (`Hud.overlayMessageString`) and `gui.hud.isHidden()` / `gui.screen()`.
5. `ClientPacketListenerMixin` (`handlePlayerInfoUpdate`).
6. Access widener header namespace (`official`).
