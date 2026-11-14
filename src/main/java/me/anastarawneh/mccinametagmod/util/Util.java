package me.anastarawneh.mccinametagmod.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.anastarawneh.mccinametagmod.MCCINametagMod;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.util.GsonHelper;

import java.util.Objects;

public class Util {
    /** Font reference for Style#withFont (takes a FontDescription since 1.21.9). */
    public static FontDescription font(String namespace, String path) {
        return new FontDescription.Resource(Identifier.fromNamespaceAndPath(namespace, path));
    }

    public static TextColor GetFactionColor() {
        try {
            Resource resource = Minecraft.getInstance().getResourceManager().getResourceStack(Identifier.fromNamespaceAndPath("mcc", "font/icon.json")).get(0);
            String json = resource.openAsReader().readLine();
            String iconPath = "";

            JsonObject icons = GsonHelper.parse(json);
            JsonArray providers = icons.get("providers").getAsJsonArray();
            for (JsonElement icon : providers) {
                if (!icon.getAsJsonObject().get("type").getAsString().equals("bitmap")) continue;
                if (Objects.equals(icon.getAsJsonObject().get("chars").getAsJsonArray().get(0).getAsString(), MCCINametagMod.FACTION)) {
                    iconPath = icon.getAsJsonObject().get("file").getAsString();
                    break;
                }
            }

            String teamColor = iconPath.split("/")[3];
            return switch (teamColor) {
                case "red" -> TextColor.fromRgb(16733524);
                case "orange" -> TextColor.fromRgb(Colors.GOLD);
                case "yellow" -> TextColor.fromRgb(Colors.YELLOW);
                case "lime" -> TextColor.fromRgb(Colors.GREEN);
                case "green" -> TextColor.fromRgb(Colors.DARK_GREEN);
                case "cyan" -> TextColor.fromRgb(48025);
                case "aqua" -> TextColor.fromRgb(5627391);
                case "blue" -> TextColor.fromRgb(5605631);
                case "purple" -> TextColor.fromRgb(8926207);
                case "pink" -> TextColor.fromRgb(Colors.LIGHT_PURPLE);
                default -> TextColor.fromRgb(Colors.GRAY);
            };
        } catch (Exception ex) {
            return TextColor.fromRgb(Colors.GRAY);
        }
    }
}
