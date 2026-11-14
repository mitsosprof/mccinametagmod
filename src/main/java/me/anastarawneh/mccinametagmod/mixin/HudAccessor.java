package me.anastarawneh.mccinametagmod.mixin;

import net.minecraft.client.gui.Hud;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

// 26.2 split the heads-up display out of Gui into Hud (Minecraft.getInstance().gui.hud).
@Mixin(Hud.class)
public interface HudAccessor {
    @Accessor("overlayMessageString")
    Component getOverlayMessage();
}
