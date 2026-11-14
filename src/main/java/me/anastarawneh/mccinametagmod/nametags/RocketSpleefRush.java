package me.anastarawneh.mccinametagmod.nametags;

import me.anastarawneh.mccinametagmod.util.Colors;
import me.anastarawneh.mccinametagmod.util.Util;
import me.anastarawneh.mccinametagmod.MCCINametagMod;
import me.anastarawneh.mccinametagmod.util.UnicodeChars;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class RocketSpleefRush {
    private static int HEALTH = 20;

    public static void setNametag(float health) {
        HEALTH = (int) health;
        setNametag();
    }
    
    public static void setNametag() {
        if (MCCINametagMod.STAGE.isEmpty() || MCCINametagMod.STAGE.equals("preRound")) {
            Nametags.TOP_LABEL = Component.empty();
            Nametags.BOTTOM_LABEL = Nametags.getStandardBottomText(false);
            Nametags.RANK_LABEL = Component.empty();
        }
        else if (MCCINametagMod.STAGE.equals("inRound")) {
            Nametags.TOP_LABEL = Component.literal(UnicodeChars.TeamFlagBig).setStyle(Style.EMPTY.withColor(MCCINametagMod.FACTION_COLOR).withFont(Util.font("mcc", "icon")))
                    .append(Component.literal(" ").setStyle(Style.EMPTY.withColor(Colors.WHITE)))
                    .append(Nametags.getHealthBar(HEALTH, true));
            Nametags.BOTTOM_LABEL = Nametags.getStandardBottomText(false);
            Nametags.RANK_LABEL = Component.empty();
        }
        else if (MCCINametagMod.STAGE.equals("postGame")) {
            Nametags.TOP_LABEL = Component.empty();
            Nametags.BOTTOM_LABEL = Nametags.getStandardBottomText(false);
            Nametags.RANK_LABEL = Component.empty();
        }
        else {
            Nametags.TOP_LABEL = Component.empty();
            Nametags.BOTTOM_LABEL = Nametags.getStandardBottomText(true);
            Nametags.RANK_LABEL = Component.empty();
        }
    }
}
