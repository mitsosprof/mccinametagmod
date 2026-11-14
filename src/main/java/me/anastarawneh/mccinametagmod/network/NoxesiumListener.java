package me.anastarawneh.mccinametagmod.network;

import me.anastarawneh.mccinametagmod.MCCINametagMod;
import me.anastarawneh.mccinametagmod.util.Game;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.util.HashMap;

public class NoxesiumListener {
    public void init() {
        // TODO(26.2): Noxesium 3.x replaced the 2.x packet API this used to call:
        //   NoxesiumFabricMod.initialize();
        //   NoxesiumPackets.CLIENT_MCC_SERVER.addListener(this, (k, packet, context) -> handleMccServerPacket(...));
        //   NoxesiumPackets.CLIENT_MCC_GAME_STATE.addListener(this, (k, packet, context) -> handleMccGameStatePacket(...));
        // The handlers below are unchanged apart from taking plain values instead of packet objects, so wiring
        // this up only needs the 3.x listener registration. Until then GAME stays NONE (lobby nametag only).
        MCCINametagMod.LOGGER.warn("Noxesium 3.x listener is not wired up yet; per-game nametags are disabled.");
    }

    public static void handleMccServerPacket(String serverType, String subType, String associatedGame) {
        if (MCCINametagMod.getConfig().debug) {
            HashMap<String, String> map = new HashMap<>();
            map.put("serverType", serverType);
            map.put("subType", subType);
            map.put("associatedGame", associatedGame);
            Minecraft.getInstance().player.displayClientMessage(Component.literal("Received packet -> " + map), false);
        }

        switch (serverType) {
            case "hole-in-the-wall":
                MCCINametagMod.GAME = Game.HOLE_IN_THE_WALL;
                break;
            case "tgttos":
                MCCINametagMod.GAME = Game.TGTTOS;
                break;
            case "battle-box":
                MCCINametagMod.GAME = Game.BATTLE_BOX;
                MCCINametagMod.STAGE = "";
                break;
            case "sky-battle":
                MCCINametagMod.GAME = Game.SKY_BATTLE;
                MCCINametagMod.STAGE = "";
                break;
            case "parkour-warrior":
                if (subType.equals("main")) MCCINametagMod.GAME = Game.PARKOUR_WARRIOR_DOJO;
                else {
                    MCCINametagMod.GAME = Game.PARKOUR_WARRIOR_SURVIVOR;
                    MCCINametagMod.STAGE = "";
                    MCCINametagMod.PHASE_TYPE = "";
                }
                break;
            case "dynaball":
                MCCINametagMod.GAME = Game.DYNABALL;
                MCCINametagMod.STAGE = "";
                MCCINametagMod.PHASE_TYPE = "";
                break;
            case "rocket-spleef":
                MCCINametagMod.GAME = Game.ROCKET_SPLEEF_RUSH;
                MCCINametagMod.STAGE = "";
                MCCINametagMod.PHASE_TYPE = "";
                break;
            default:
                if (subType.equals("parkour-warrior")) MCCINametagMod.GAME = Game.PARKOUR_WARRIOR_LOBBY;
                else if (subType.contains("temperate_") ||
                         subType.contains("tropical_") ||
                         subType.contains("barren_")) MCCINametagMod.GAME = Game.FISHING;
                else MCCINametagMod.GAME = Game.NONE;
                break;
        }
    }

    public static void handleMccGameStatePacket(String phaseType, String stage, int round, int totalRounds, String mapId, String mapName) {
        if (MCCINametagMod.getConfig().debug) {
            HashMap<String, String> map = new HashMap<>();
            map.put("phaseType", phaseType);
            map.put("stage", stage);
            map.put("round", String.valueOf(round));
            map.put("totalRounds", String.valueOf(totalRounds));
            map.put("mapId", mapId);
            map.put("mapName", mapName);
            Minecraft.getInstance().player.displayClientMessage(Component.literal("Received packet -> " + map), false);
        }

        MCCINametagMod.STAGE = stage;
        MCCINametagMod.PHASE_TYPE = phaseType;
    }
}
