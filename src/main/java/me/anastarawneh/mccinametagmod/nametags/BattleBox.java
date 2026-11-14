package me.anastarawneh.mccinametagmod.nametags;

import me.anastarawneh.mccinametagmod.util.Colors;
import me.anastarawneh.mccinametagmod.util.Util;
import me.anastarawneh.mccinametagmod.MCCINametagMod;
import me.anastarawneh.mccinametagmod.util.UnicodeChars;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;

public class BattleBox {
    private static TextColor TEAM_COLOR = TextColor.fromRgb(Colors.GRAY);
    private static int HEALTH = 20;
    private static MutableComponent DECORATED_TEXT = Component.empty();

    public static void setNametag(float health) {
        HEALTH = (int) health;
        setNametag();
    }
    
    public static void setNametag() {
        String playerName = Minecraft.getInstance().player.getName().getString();

        if (MCCINametagMod.STAGE.isEmpty()) {
            Component text = Minecraft.getInstance().getConnection().getOnlinePlayers().stream().map(PlayerInfo::getTabListDisplayName).filter(t -> t != null && t.getString().contains(playerName)).findFirst().get();
            Style style = text.getSiblings().getFirst().getSiblings().getFirst().getStyle();
            TEAM_COLOR = style.getColor();
            DECORATED_TEXT = (MutableComponent) text.getSiblings().getFirst();

            Nametags.TOP_LABEL = Component.empty();
            Nametags.BOTTOM_LABEL = DECORATED_TEXT;
            Nametags.RANK_LABEL = Component.empty();
        }
        else if (!MCCINametagMod.STAGE.equals("podiumPhase") && !MCCINametagMod.STAGE.equals("postGame")) {
            Nametags.TOP_LABEL = Component.literal(UnicodeChars.TeamFlagBig).setStyle(Style.EMPTY.withColor(TEAM_COLOR).withFont(Util.font("mcc", "icon")))
                    .append(Component.literal(" ").setStyle(Style.EMPTY.withColor(Colors.WHITE)))
                    .append(Nametags.getHealthBar(HEALTH));
            Nametags.BOTTOM_LABEL = Component.literal(playerName).setStyle(Style.EMPTY.withColor(TEAM_COLOR).withFont(Util.font("minecraft", "default")));
            Nametags.RANK_LABEL = Component.empty();
        }
        else if (MCCINametagMod.STAGE.equals("postGame")) {
            Nametags.TOP_LABEL = Component.empty();
            Nametags.BOTTOM_LABEL = DECORATED_TEXT;
            Nametags.RANK_LABEL = Component.empty();
        }
        else {
            Nametags.TOP_LABEL = Component.empty();
            Nametags.BOTTOM_LABEL = Nametags.getStandardBottomText(true);
            Nametags.RANK_LABEL = Component.empty();
        }
    }
}
