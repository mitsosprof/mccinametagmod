package me.anastarawneh.mccinametagmod.mixin;

import me.anastarawneh.mccinametagmod.MCCINametagMod;
import me.anastarawneh.mccinametagmod.util.Colors;
import me.anastarawneh.mccinametagmod.util.UnicodeChars;
import me.anastarawneh.mccinametagmod.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(ClientPacketListener.class)
public abstract class ClientPacketListenerMixin {
    @Shadow public abstract Collection<PlayerInfo> getOnlinePlayers();

    @Inject(method = "handlePlayerInfoUpdate", at = @At("TAIL"))
    public void onPlayerList(ClientboundPlayerInfoUpdatePacket packet, CallbackInfo ci) {
        try {
            if (MCCINametagMod.FACTION.equals("")) {
                Collection<PlayerInfo> tabList = getOnlinePlayers();
                for (PlayerInfo tabEntry : tabList) {
                    Component display = tabEntry.getTabListDisplayName();
                    if (display != null && display.getSiblings().get(4).getString().equals(Minecraft.getInstance().getUser().getName())) {
                        MCCINametagMod.RANK = display.getSiblings().get(0).getString();
                        MCCINametagMod.FACTION = display.getSiblings().get(1).getString();
                        MCCINametagMod.CROWN = display.getSiblings().get(2).getString();
                        MCCINametagMod.COLOR = display.getSiblings().get(4).getStyle().getColor();
                        if (MCCINametagMod.COLOR != null) {
                            String rank = MCCINametagMod.RANK;
                            if (rank.equals(UnicodeChars.NoxcrewIcon)) MCCINametagMod.RANK_BADGE = UnicodeChars.NoxcrewRank;
                            else if (rank.equals(UnicodeChars.ModIcon)) MCCINametagMod.RANK_BADGE = UnicodeChars.ModRank;
                            else if (rank.equals(UnicodeChars.ContestantIcon)) MCCINametagMod.RANK_BADGE = UnicodeChars.ContestantRank;
                            else if (rank.equals(UnicodeChars.CreatorIcon)) MCCINametagMod.RANK_BADGE = UnicodeChars.CreatorRank;
                            else if (rank.equals(UnicodeChars.GrandChampSupremeIcon)) MCCINametagMod.RANK_BADGE = UnicodeChars.GrandChampSupremeRank;
                            else if (rank.equals(UnicodeChars.GrandChampRoyaleIcon)) MCCINametagMod.RANK_BADGE = UnicodeChars.GrandChampRoyaleRank;
                            else if (rank.equals(UnicodeChars.GrandChampIcon)) MCCINametagMod.RANK_BADGE = UnicodeChars.GrandChampRank;
                            else if (rank.equals(UnicodeChars.ChampIcon)) MCCINametagMod.RANK_BADGE = UnicodeChars.ChampRank;
                            else if (rank.equals(UnicodeChars.BasicIcon)) MCCINametagMod.RANK_BADGE = "";
                            else MCCINametagMod.RANK_BADGE = "!";
                        }
                        break;
                    }
                }
            }

            if (MCCINametagMod.FACTION_COLOR.getValue() == Colors.GRAY) {
                MCCINametagMod.FACTION_COLOR = Util.GetFactionColor();
            }
        } catch (IndexOutOfBoundsException ignored) {

        }
    }
}
