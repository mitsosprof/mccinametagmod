package me.anastarawneh.mccinametagmod.mixin;

import com.mojang.blaze3d.font.GlyphProvider;
import net.minecraft.client.gui.font.FontManager;
import net.minecraft.client.gui.font.providers.GlyphProviderDefinition;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(FontManager.class)
public class FontManagerMixin {
    // Yarn FontManager#load / FontKey == Mojang FontManager#safeLoad / BuilderId
    @Inject(method = "safeLoad", at = @At("HEAD"), cancellable = true)
    private static void load(FontManager.BuilderId key, GlyphProviderDefinition.Loader loadable, ResourceManager resourceManager, Executor executor, CallbackInfoReturnable<CompletableFuture<Optional<GlyphProvider>>> cir) {
        if (key.fontId().getNamespace().equals("mccinametagmod")) {
            cir.setReturnValue(CompletableFuture.supplyAsync(() -> {
                try {
                    return Optional.of(loadable.load(resourceManager));
                } catch (Exception exception) {
                    return Optional.empty();
                }
            }, executor));
        }
    }
}
