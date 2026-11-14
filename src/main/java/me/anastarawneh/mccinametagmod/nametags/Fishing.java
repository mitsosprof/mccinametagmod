package me.anastarawneh.mccinametagmod.nametags;

import me.anastarawneh.mccinametagmod.MCCINametagMod;
import me.anastarawneh.mccinametagmod.mixin.HudAccessor;
import me.anastarawneh.mccinametagmod.util.UnicodeChars;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.Component;
import me.anastarawneh.mccinametagmod.util.Colors;
import me.anastarawneh.mccinametagmod.util.Util;

import java.util.Objects;

public class Fishing {
    public static void setNametag() {
        LocalPlayer player = Minecraft.getInstance().player;
        String playerName = player.getName().getString();

        Component actionBar = ((HudAccessor) Minecraft.getInstance().gui.hud).getOverlayMessage();
        String fishingGlyphBig = actionBar.getSiblings().get(0).getSiblings().get(13).getString();
        String fishingGlyph = "?";
        if (fishingGlyphBig.equals(UnicodeChars.FishingLevel0Big)) fishingGlyph = UnicodeChars.FishingLevel0;
        else if (fishingGlyphBig.equals(UnicodeChars.FishingLevel1Big)) fishingGlyph = UnicodeChars.FishingLevel1;
        else if (fishingGlyphBig.equals(UnicodeChars.FishingLevel2Big)) fishingGlyph = UnicodeChars.FishingLevel2;
        else if (fishingGlyphBig.equals(UnicodeChars.FishingLevel3Big)) fishingGlyph = UnicodeChars.FishingLevel3;
        else if (fishingGlyphBig.equals(UnicodeChars.FishingLevel4Big)) fishingGlyph = UnicodeChars.FishingLevel4;
        else if (fishingGlyphBig.equals(UnicodeChars.FishingLevel5Big)) fishingGlyph = UnicodeChars.FishingLevel5;
        else if (fishingGlyphBig.equals(UnicodeChars.FishingLevel6Big)) fishingGlyph = UnicodeChars.FishingLevel6;
        else if (fishingGlyphBig.equals(UnicodeChars.FishingLevel7Big)) fishingGlyph = UnicodeChars.FishingLevel7;
        else if (fishingGlyphBig.equals(UnicodeChars.FishingLevel8Big)) fishingGlyph = UnicodeChars.FishingLevel8;
        else if (fishingGlyphBig.equals(UnicodeChars.FishingLevel9Big)) fishingGlyph = UnicodeChars.FishingLevel9;
        else if (fishingGlyphBig.equals(UnicodeChars.FishingLevel10Big)) fishingGlyph = UnicodeChars.FishingLevel10;

        Nametags.TOP_LABEL = Component.literal(playerName).setStyle(Style.EMPTY.withColor(MCCINametagMod.COLOR));
        int crownLevel = player.experienceLevel;
        int fishingLevel = Integer.parseInt(actionBar.getSiblings().get(0).getSiblings().get(15).getString());
        Nametags.BOTTOM_LABEL = Component.literal(MCCINametagMod.FACTION).setStyle(Style.EMPTY.withFont(Util.font("mcc", "icon")))
                .append(Component.literal(MCCINametagMod.FACTION_LEVEL + " ").setStyle(Style.EMPTY.withFont(Util.font("minecraft", "default"))))
                .append(Component.literal(MCCINametagMod.CROWN).setStyle(Style.EMPTY.withFont(Util.font("mcc", "icon"))))
                .append(Component.literal(crownLevel + " ").setStyle(Style.EMPTY.withColor(Colors.YELLOW).withFont(Util.font("minecraft", "default"))))
                .append(Component.literal(fishingGlyph).setStyle(Style.EMPTY.withFont(Util.font("mcc", "icon"))))
                .append(Component.literal(String.valueOf(fishingLevel)).setStyle(Style.EMPTY.withColor(Colors.AQUA).withFont(Util.font("minecraft", "default"))));
        Nametags.RANK_LABEL = Component.literal(MCCINametagMod.RANK_BADGE).setStyle(Style.EMPTY.withFont(Util.font("mcc", "icon")));
    }
}
