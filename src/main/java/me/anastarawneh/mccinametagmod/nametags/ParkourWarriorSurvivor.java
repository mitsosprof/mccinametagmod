package me.anastarawneh.mccinametagmod.nametags;

import me.anastarawneh.mccinametagmod.util.Colors;
import me.anastarawneh.mccinametagmod.util.Util;
import me.anastarawneh.mccinametagmod.MCCINametagMod;
import me.anastarawneh.mccinametagmod.util.UnicodeChars;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class ParkourWarriorSurvivor {
    private static int HEALTH = 10;
    private static int MAX_HEALTH = 10;

    public static void setNametag(float health, float maxHealth) {
        HEALTH = (int) health;
        MAX_HEALTH = (int) maxHealth;
        setNametag();
    }
    
    public static void setNametag() {
        String playerName = Minecraft.getInstance().player.getName().getString();

        if (!MCCINametagMod.PHASE_TYPE.equals("POST_GAME")) {
            Nametags.TOP_LABEL = Component.literal(UnicodeChars.TeamFlagBig).setStyle(Style.EMPTY.withColor(MCCINametagMod.FACTION_COLOR).withFont(Util.font("mcc", "icon")))
                    .append(Component.literal(" ").setStyle(Style.EMPTY.withColor(Colors.WHITE)))
                    .append(Nametags.getPartialHealthBar(HEALTH, MAX_HEALTH));Nametags.BOTTOM_LABEL = Component.literal(playerName).setStyle(Style.EMPTY.withColor(MCCINametagMod.FACTION_COLOR).withFont(Util.font("minecraft", "default")));
            Nametags.RANK_LABEL = Component.empty();
        }
        else if (MCCINametagMod.STAGE.equals("postGame")) {
            Nametags.TOP_LABEL = Component.empty();
            Nametags.BOTTOM_LABEL = Component.literal(playerName).setStyle(Style.EMPTY.withColor(MCCINametagMod.FACTION_COLOR));
            Nametags.RANK_LABEL = Component.empty();
        }
        else {
            Nametags.TOP_LABEL = Component.empty();
            Nametags.BOTTOM_LABEL = Nametags.getStandardBottomText(true);
            Nametags.RANK_LABEL = Component.empty();
        }
    }
}
