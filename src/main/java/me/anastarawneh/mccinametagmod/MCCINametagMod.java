package me.anastarawneh.mccinametagmod;

import me.anastarawneh.mccinametagmod.command.LogExceptionCommand;
import me.anastarawneh.mccinametagmod.config.Config;
import me.anastarawneh.mccinametagmod.network.NoxesiumListener;
import me.anastarawneh.mccinametagmod.util.Colors;
import me.anastarawneh.mccinametagmod.util.Game;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MCCINametagMod implements ModInitializer {
    public static final String MODID = "mccinametagmod";
    public static final Logger LOGGER = LogManager.getLogger("MCCINametagMod");
    public static final MutableComponent MESSAGE_PREFIX = Component.literal("[").setStyle(Style.EMPTY.withColor(Colors.GRAY))
            .append(Component.literal("MCCINametagMod").setStyle(Style.EMPTY.withColor(Colors.GREEN)))
            .append(Component.literal("] ").setStyle(Style.EMPTY.withColor(Colors.GRAY)));
    public static String RANK = "";
    public static String FACTION = "";
    public static String CROWN = "";
    public static TextColor COLOR = TextColor.fromRgb(Colors.DARK_GRAY);
    public static String RANK_BADGE = "";
    public static TextColor FACTION_COLOR = TextColor.fromRgb(Colors.GRAY);
    public static int FACTION_LEVEL = -1;
    public static Game GAME = Game.NONE;
    public static String STAGE = "";
    public static String PHASE_TYPE = "";

    public static Exception LATEST_EXCEPTION = null;

    // The 1.21.x builds hooked the server resource pack reload to warn about a missing faction level.
    // That hook (a mixin into the download pack source) is replaced by a short timer after joining.
    private static int factionWarningCountdown = -1;
    private static boolean sentFactionLevelWarning = false;

    @Override
    public void onInitialize() {
        AutoConfig.register(Config.class, Toml4jConfigSerializer::new);
        FACTION_LEVEL = getConfig().factionLevel;
        LOGGER.info("Hello there");

        LogExceptionCommand.register();

        new NoxesiumListener().init();

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> factionWarningCountdown = 200);
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (factionWarningCountdown < 0) return;
            if (--factionWarningCountdown > 0) return;
            factionWarningCountdown = -1;
            if (client.player != null && modEnabled() && FACTION_LEVEL == -1 && !sentFactionLevelWarning) {
                sendChatMessage(Component.literal("Due to an MCC Island update, your faction level could not be obtained. Click here to fix.")
                        .setStyle(Style.EMPTY.withColor(Colors.RED).withClickEvent(new ClickEvent.RunCommand("/profile"))));
                sentFactionLevelWarning = true;
            }
        });
    }

    public static Config getConfig() {
        return AutoConfig.getConfigHolder(Config.class).getConfig();
    }
    public static void saveConfig() {
        AutoConfig.getConfigHolder(Config.class).save();
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean modEnabled() {
        Config config = getConfig();
        ServerData server = Minecraft.getInstance().getCurrentServer();
        return config.enabled && server != null && server.ip.endsWith("mccisland.net");
    }

    public static void sendChatMessage(Component message) {
        Minecraft.getInstance().player.sendSystemMessage(MCCINametagMod.MESSAGE_PREFIX.copy().append(message));
    }
}
