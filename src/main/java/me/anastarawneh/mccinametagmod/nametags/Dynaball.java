package me.anastarawneh.mccinametagmod.nametags;

import me.anastarawneh.mccinametagmod.util.Colors;
import me.anastarawneh.mccinametagmod.util.Util;
import me.anastarawneh.mccinametagmod.MCCINametagMod;
import me.anastarawneh.mccinametagmod.util.UnicodeChars;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;

public class Dynaball {
    private static TextColor TEAM_COLOR = TextColor.fromRgb(Colors.GRAY);

    public static void setNametag() {
        String playerName = Minecraft.getInstance().player.getName().getString();

        if (!MCCINametagMod.STAGE.equals("podiumPhase")) {
            if (MCCINametagMod.STAGE.isEmpty()) {
                Component text = Minecraft.getInstance().getConnection().getOnlinePlayers().stream().map(PlayerInfo::getTabListDisplayName).filter(t -> t != null && t.getString().contains(Minecraft.getInstance().player.getName().getString())).findFirst().get();
                Style style = text.getSiblings().getFirst().getSiblings().getFirst().getStyle();
                TEAM_COLOR = style.getColor();
            }

            Nametags.TOP_LABEL = Component.empty();
            Nametags.BOTTOM_LABEL = Component.literal(UnicodeChars.TeamFlag).setStyle(Style.EMPTY.withColor(TEAM_COLOR).withFont(Util.font("mcc", "icon")))
                    .append(Component.literal(MCCINametagMod.RANK + MCCINametagMod.CROWN).setStyle(Style.EMPTY.withColor(MCCINametagMod.COLOR).withFont(Util.font("mcc", "icon"))))
                    .append(Component.literal("\uE002").setStyle(Style.EMPTY.withFont(Util.font("mcc", "padding_nosplit"))))
                    .append(Component.literal(playerName).setStyle(Style.EMPTY.withFont(Util.font("minecraft", "default")).withColor(TEAM_COLOR)));
            Nametags.RANK_LABEL = Component.empty();
        }
        else {
            Nametags.TOP_LABEL = Component.empty();
            Nametags.BOTTOM_LABEL = Nametags.getStandardBottomText(true);
            Nametags.RANK_LABEL = Component.empty();
        }
    }
}
