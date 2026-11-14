package me.anastarawneh.mccinametagmod.mixin;

import me.anastarawneh.mccinametagmod.MCCINametagMod;
import me.anastarawneh.mccinametagmod.util.Colors;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ChestMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ContainerScreen.class)
public class GenericContainerScreenMixin {
    @Unique private Container container;
    @Unique private Component title;
    @Unique boolean processed = false;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void constructor(ChestMenu menu, Inventory inventory, Component title, CallbackInfo ci) {
        this.container = menu.getContainer();
        this.title = title;
        this.processed = false;
    }

    // 26.x renamed Screen#render to extractRenderState (GuiGraphicsExtractor replaces GuiGraphics).
    @Inject(method = "extractRenderState", at = @At("TAIL"))
    public void render(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (!processed && MCCINametagMod.modEnabled()) {
            if (title.getString().contains("MY PROFILE")) {
                if (container.isEmpty()) return; // do this THEN try looping, it eventually calls render again after the items arrive
                try {
                    if (!container.getItem(24).getHoverName().getString().contains("Faction Loyalty")) return;
                    String levelLine = container.getItem(24).get(DataComponents.LORE).lines().get(2).getString();
                    MCCINametagMod.LOGGER.info(levelLine);
                    String level = levelLine.split(" ")[1];
                    if (MCCINametagMod.FACTION_LEVEL != Integer.parseInt(level)) {
                        MCCINametagMod.FACTION_LEVEL = Integer.parseInt(level);
                        MCCINametagMod.getConfig().factionLevel = Integer.parseInt(level);
                        MCCINametagMod.saveConfig();
                        MCCINametagMod.sendChatMessage(Component.literal("Saved faction level (" + level + ").").setStyle(Style.EMPTY.withColor(Colors.GREEN)));
                    }
                } catch (Exception ex) {
                    MCCINametagMod.sendChatMessage(Component.literal("Could not retrieve current faction level. Check the log for more details.").setStyle(Style.EMPTY.withColor(Colors.RED)));
                    MCCINametagMod.LOGGER.throwing(ex);
                }
            }
            processed = true;
        }
    }
}
