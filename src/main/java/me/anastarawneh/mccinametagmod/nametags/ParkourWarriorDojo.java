package me.anastarawneh.mccinametagmod.nametags;

import me.anastarawneh.mccinametagmod.util.Colors;
import me.anastarawneh.mccinametagmod.util.Util;
import me.anastarawneh.mccinametagmod.MCCINametagMod;
import me.anastarawneh.mccinametagmod.util.UnicodeChars;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class ParkourWarriorDojo {
    private static int MEDALS = -1;

    public static void setNametag() {
        String playerName = Minecraft.getInstance().player.getName().getString();
        
        Nametags.TOP_LABEL = Component.literal(playerName).setStyle(Style.EMPTY.withColor(MCCINametagMod.COLOR));
        int crownLevel = Minecraft.getInstance().player.experienceLevel;
        for (PlayerInfo entry : Minecraft.getInstance().player.connection.getListedOnlinePlayers())
            if (entry.getTabListDisplayName() != null && entry.getTabListDisplayName().toString().contains("color=yellow"))
                MEDALS = Integer.parseInt(entry.getTabListDisplayName().getSiblings().get(1).getString());
        Nametags.BOTTOM_LABEL = Component.literal(MCCINametagMod.FACTION).setStyle(Style.EMPTY.withFont(Util.font("mcc", "icon")))
                .append(Component.literal(MCCINametagMod.FACTION_LEVEL + " ").setStyle(Style.EMPTY.withFont(Util.font("minecraft", "default"))))
                .append(Component.literal(MCCINametagMod.CROWN).setStyle(Style.EMPTY.withFont(Util.font("mcc", "icon"))))
                .append(Component.literal(crownLevel + " ").setStyle(Style.EMPTY.withColor(Colors.YELLOW).withFont(Util.font("minecraft", "default"))))
                .append(Component.literal(UnicodeChars.MedalUnicode).setStyle(Style.EMPTY.withFont(Util.font("mcc", "icon"))))
                .append(Component.literal(String.valueOf(MEDALS)).setStyle(Style.EMPTY.withColor(Colors.YELLOW).withFont(Util.font("minecraft", "default"))));
        Nametags.RANK_LABEL = Component.literal(MCCINametagMod.RANK_BADGE).setStyle(Style.EMPTY.withFont(Util.font("mcc", "icon")));
    }
}
