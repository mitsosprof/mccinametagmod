package me.anastarawneh.mccinametagmod.nametags;

import me.anastarawneh.mccinametagmod.MCCINametagMod;
import net.minecraft.network.chat.Component;

public class HoleInTheWall {
    public static void setNametag() {
        if (!MCCINametagMod.STAGE.equals("podiumPhase")) {
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
