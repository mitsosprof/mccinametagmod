package me.anastarawneh.mccinametagmod.nametags;

import me.anastarawneh.mccinametagmod.util.Colors;
import me.anastarawneh.mccinametagmod.util.Util;
import me.anastarawneh.mccinametagmod.MCCINametagMod;
import me.anastarawneh.mccinametagmod.util.Game;
import me.anastarawneh.mccinametagmod.util.UnicodeChars;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class Lobby {
    public static void setNametag() {
        LocalPlayer player = Minecraft.getInstance().player;
        String playerName = player.getName().getString();

        Nametags.TOP_LABEL = Component.literal(playerName).setStyle(Style.EMPTY.withColor(MCCINametagMod.COLOR));
        int crownLevel = player.experienceLevel;
        Nametags.BOTTOM_LABEL = Component.literal(MCCINametagMod.FACTION).setStyle(Style.EMPTY.withFont(Util.font("mcc", "icon")))
                .append(Component.literal(MCCINametagMod.FACTION_LEVEL + " ").setStyle(Style.EMPTY.withFont(Util.font("minecraft", "default"))))
                .append(Component.literal(MCCINametagMod.CROWN).setStyle(Style.EMPTY.withFont(Util.font("mcc", "icon"))))
                .append(Component.literal(String.valueOf(crownLevel)).setStyle(Style.EMPTY.withColor(Colors.YELLOW).withFont(Util.font("minecraft", "default"))));
        Nametags.RANK_LABEL = Component.literal(MCCINametagMod.RANK_BADGE).setStyle(Style.EMPTY.withFont(Util.font("mcc", "icon")));

        /*if (MCCINametagMod.GAME == Game.PARKOUR_WARRIOR_LOBBY)
        {
            Scoreboard scoreboard = Minecraft.getInstance().player.getScoreboard();
            ScoreboardObjective obj = scoreboard.getObjectiveForSlot(ScoreboardDisplaySlot.SIDEBAR);
            int medals = -1;
            for (ScoreboardEntry entry : scoreboard.getScoreboardEntries(obj).stream().toList()) {
                String line = entry.name().getString();
                if (!line.contains("MEDALS")) continue;
                line = line.replace(",", "").replace("MEDALS", "").replace(".", "");
                int len = line.length();
                medals = Integer.parseInt(line.substring(1, len - 1));
            }
            Nametags.BOTTOM_LABEL = Nametags.BOTTOM_LABEL
                    .append(Component.literal(" " + UnicodeChars.MedalUnicode).setStyle(Style.EMPTY.withFont(Util.font("mcc", "icon"))))
                    .append(Component.literal(String.valueOf(medals)).setStyle(Style.EMPTY.withColor(Colors.YELLOW).withFont(Util.font("minecraft", "default"))));
        }*/
    }
}
