package me.anastarawneh.mccinametagmod.nametags;

import me.anastarawneh.mccinametagmod.util.Colors;
import me.anastarawneh.mccinametagmod.util.Util;
import me.anastarawneh.mccinametagmod.MCCINametagMod;
import me.anastarawneh.mccinametagmod.util.UnicodeChars;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class Nametags {
    public static MutableComponent TOP_LABEL = Component.empty();
    public static MutableComponent BOTTOM_LABEL = Component.empty();
    public static MutableComponent RANK_LABEL = Component.empty();

    public static MutableComponent getStandardBottomText(boolean faction) {
        String playerName = Minecraft.getInstance().player.getName().getString();

        if (faction) return Component.literal(MCCINametagMod.RANK + MCCINametagMod.FACTION + MCCINametagMod.CROWN).setStyle(Style.EMPTY.withColor(MCCINametagMod.COLOR).withFont(Util.font("mcc", "icon")))
                .append(Component.literal("\uE002").setStyle(Style.EMPTY.withFont(Util.font("mcc", "padding_nosplit"))))
                .append(Component.literal(playerName).setStyle(Style.EMPTY.withFont(Util.font("minecraft", "default"))));
        else return Component.literal(MCCINametagMod.RANK + MCCINametagMod.CROWN).setStyle(Style.EMPTY.withColor(MCCINametagMod.COLOR).withFont(Util.font("mcc", "icon")))
                .append(Component.literal("\uE002").setStyle(Style.EMPTY.withFont(Util.font("mcc", "padding_nosplit"))))
                .append(Component.literal(playerName).setStyle(Style.EMPTY.withFont(Util.font("minecraft", "default"))));
    }

    public static MutableComponent getHealthBar(int health) {
        return getHealthBar(health, false);
    }

    public static MutableComponent getHealthBar(int health, boolean hideNumber) {
        MutableComponent label = Component.empty();
        for (int segment = 0; segment < 5; segment++) {
            if (health >= 4 + (4 * segment)) label = label.append(Component.literal(UnicodeChars.HealthBar2_0).setStyle(Style.EMPTY.withColor(Colors.WHITE).withFont(Util.font("mcc", "icon"))));
            else if (health == 3 + (4 * segment)) label = label.append(Component.literal(UnicodeChars.HealthBar1_5).setStyle(Style.EMPTY.withColor(Colors.WHITE).withFont(Util.font("mcc", "icon"))));
            else if (health == 2 + (4 * segment)) label = label.append(Component.literal(UnicodeChars.HealthBar1_0).setStyle(Style.EMPTY.withColor(Colors.WHITE).withFont(Util.font("mcc", "icon"))));
            else if (health == 1 + (4 * segment)) label = label.append(Component.literal(UnicodeChars.HealthBar0_5).setStyle(Style.EMPTY.withColor(Colors.WHITE).withFont(Util.font("mcc", "icon"))));
            else if (segment == 4 && !hideNumber) {
                label = label.append(Component.literal(switch (health) {
                    case 16 -> UnicodeChars.HealthNum8_0;
                    case 15 -> UnicodeChars.HealthNum7_5;
                    case 14 -> UnicodeChars.HealthNum7_0;
                    case 13 -> UnicodeChars.HealthNum6_5;
                    case 12 -> UnicodeChars.HealthNum6_0;
                    case 11 -> UnicodeChars.HealthNum5_5;
                    case 10 -> UnicodeChars.HealthNum5_0;
                    case  9 -> UnicodeChars.HealthNum4_5;
                    case  8 -> UnicodeChars.HealthNum4_0;
                    case  7 -> UnicodeChars.HealthNum3_5;
                    case  6 -> UnicodeChars.HealthNum3_0;
                    case  5 -> UnicodeChars.HealthNum2_5;
                    case  4 -> UnicodeChars.HealthNum2_0;
                    case  3 -> UnicodeChars.HealthNum1_5;
                    case  2 -> UnicodeChars.HealthNum1_0;
                    case  1 -> UnicodeChars.HealthNum0_5;
                    default -> UnicodeChars.HealthBar0_0;
                }).setStyle(Style.EMPTY.withColor(Colors.WHITE).withFont(Util.font("mcc", "icon"))));
            }
            else label = label.append(Component.literal(UnicodeChars.HealthBar0_0).setStyle(Style.EMPTY.withColor(Colors.WHITE).withFont(Util.font("mcc", "icon"))));

            if (segment < 4) label = label.append(Component.literal("\uE002").setStyle(Style.EMPTY.withFont(Util.font("mcc", "negative_padding_nosplit"))));
        }
        return label;
    }

    public static MutableComponent getPartialHealthBar(int health, int maxHealth) {
        MutableComponent label = Component.empty();
        health /= 2;
        maxHealth /= 2;
        for (int segment = 0; segment < 5; segment++) {
            if (health >= 2 + (2 * segment)) label = label.append(Component.literal(UnicodeChars.HealthBar2_0).setStyle(Style.EMPTY.withColor(Colors.WHITE).withFont(Util.font("mcc", "icon"))));
            else if (maxHealth == 1 + (2 * segment) && health == 1 + (2 * segment)) label = label.append(Component.literal(UnicodeChars.DisabledHealthBar1_0).setStyle(Style.EMPTY.withColor(Colors.WHITE).withFont(Util.font("mcc", "icon"))));
            else if (maxHealth == 1 + (2 * segment)) label = label.append(Component.literal(UnicodeChars.DisabledHealthBar1_0Hit).setStyle(Style.EMPTY.withColor(Colors.WHITE).withFont(Util.font("mcc", "icon"))));
            else if (health == 1 + (2 * segment)) label = label.append(Component.literal(UnicodeChars.HealthBar1_0).setStyle(Style.EMPTY.withColor(Colors.WHITE).withFont(Util.font("mcc", "icon"))));
            else if (maxHealth < (2 * segment)) label = label.append(Component.literal(UnicodeChars.DisabledHealthBar2_0).setStyle(Style.EMPTY.withColor(Colors.WHITE).withFont(Util.font("mcc", "icon"))));
            else label = label.append(Component.literal(UnicodeChars.HealthBar0_0).setStyle(Style.EMPTY.withColor(Colors.WHITE).withFont(Util.font("mcc", "icon"))));

            if (segment < 4) label = label.append(Component.literal("\uE002").setStyle(Style.EMPTY.withFont(Util.font("mcc", "negative_padding_nosplit"))));
        }
        return label;
    }
}
