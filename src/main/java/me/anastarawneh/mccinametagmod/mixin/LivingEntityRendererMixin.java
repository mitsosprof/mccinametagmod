package me.anastarawneh.mccinametagmod.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import me.anastarawneh.mccinametagmod.MCCINametagMod;
import me.anastarawneh.mccinametagmod.nametags.*;
import me.anastarawneh.mccinametagmod.util.Colors;
import me.anastarawneh.mccinametagmod.util.Game;
import me.anastarawneh.mccinametagmod.util.LivingEntityRenderStateExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityAttachment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin extends EntityRendererMixin {
    @Unique
    private boolean applyLabel(LivingEntity livingEntity) {
        if (!MCCINametagMod.modEnabled()) return false;
        if (!(livingEntity instanceof Player player) || !player.isLocalPlayer()) return false;

        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer localPlayer = minecraft.player;
        boolean visible = !livingEntity.isInvisibleTo(localPlayer);

        // Minecraft#renderNames() is gone in 26.2; the HUD now reports whether it is hidden.
        boolean modifiedCheck = !minecraft.gui.hud.isHidden() && visible && !livingEntity.isVehicle();
        var screen = minecraft.gui.screen();
        boolean currentScreen = screen == null || screen instanceof ChatScreen || (screen instanceof InventoryScreen && MCCINametagMod.getConfig().showInInventory);
        return modifiedCheck && currentScreen;
    }

    public void setLabels(Player player) {
        String playerName = player.getName().getString();
        Game game = MCCINametagMod.GAME;
        try {
            if (game == Game.HOLE_IN_THE_WALL) {
                HoleInTheWall.setNametag();
            }
            else if (game == Game.TGTTOS) {
                TGTTOS.setNametag();
            }
            else if (game == Game.BATTLE_BOX) {
                float health = player.getHealth();
                BattleBox.setNametag(health);
            }
            else if (game == Game.SKY_BATTLE) {
                float health = player.getHealth();
                SkyBattle.setNametag(health);
            }
            else if (game == Game.PARKOUR_WARRIOR_DOJO) {
                ParkourWarriorDojo.setNametag();
            }
            else if (game == Game.PARKOUR_WARRIOR_SURVIVOR) {
                float health = player.getHealth();
                float maxHealth = player.getMaxHealth();
                ParkourWarriorSurvivor.setNametag(health, maxHealth);
            }
            else if (game == Game.DYNABALL) {
                Dynaball.setNametag();
            }
            else if (game == Game.ROCKET_SPLEEF_RUSH) {
                int maxDamage = player.getItemBySlot(EquipmentSlot.BODY).getMaxDamage();
                int currentDamage = maxDamage - player.getItemBySlot(EquipmentSlot.BODY).getDamageValue();
                int health = (int) Math.floor(((float) currentDamage) / maxDamage * 20);
                if (Objects.equals(player.getItemBySlot(EquipmentSlot.BODY).getHoverName().getString(), "Air")) health = 20;
                RocketSpleefRush.setNametag(health);
            }
            else if (game == Game.FISHING) {
                Fishing.setNametag();
            }
            else {
                Lobby.setNametag();
            }
        } catch (Exception ex) {
            Nametags.TOP_LABEL = Component.literal(playerName).setStyle(Style.EMPTY.withColor(Colors.GRAY));
            Nametags.BOTTOM_LABEL = Component.literal("Error.").setStyle(Style.EMPTY.withColor(Colors.DARK_RED));
            Nametags.RANK_LABEL = Component.empty();
            MCCINametagMod.LATEST_EXCEPTION = ex;
        }
    }

    @Override
    public void extractRenderState(Entity entity, EntityRenderState state, float partialTick, CallbackInfo ci) {
        if (entity instanceof Player player && player.isLocalPlayer()) {
            ((LivingEntityRenderStateExtension) state).setApplyLabel(applyLabel(player));
            setLabels(player);
            state.nameTagAttachment = entity.getAttachments().getNullable(EntityAttachment.NAME_TAG, 0, entity.getYRot(partialTick));
        } else {
            ((LivingEntityRenderStateExtension) state).setApplyLabel(false);
        }
    }

    @Override
    public void submit(EntityRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera, CallbackInfo ci) {
        if (((LivingEntityRenderStateExtension) state).getApplyLabel()) submitLabels(state, poseStack, collector, camera);
    }

    /**
     * The 1.21.x builds drew the three labels by hand with Font#draw. Font drawing is gone in 26.2, so the
     * labels are handed to the vanilla name tag submission instead (same see-through/background handling as
     * a normal name tag). Vanilla lifts a name tag 0.5 above the attachment point; the labels used to sit at
     * +1.0 / +1.5 / +2.0, hence the extra 0.5 / 1.0 / 1.5 translation.
     */
    @Unique
    private void submitLabels(EntityRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        Vec3 attachment = state.nameTagAttachment;
        if (attachment == null) return;
        submitLabel(Nametags.BOTTOM_LABEL, 0.5, state, poseStack, collector, camera);
        if (!Nametags.TOP_LABEL.getString().isEmpty()) submitLabel(Nametags.TOP_LABEL, 1.0, state, poseStack, collector, camera);
        if (!Nametags.RANK_LABEL.getString().isEmpty()) submitLabel(Nametags.RANK_LABEL, 1.5, state, poseStack, collector, camera);
    }

    @Unique
    private void submitLabel(Component label, double extraHeight, EntityRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        int offset = "deadmau5".equals(label.getString()) ? -10 : 0;
        poseStack.pushPose();
        poseStack.translate(0.0, extraHeight, 0.0);
        collector.submitNameTag(poseStack, state.nameTagAttachment, offset, label, !state.isDiscrete, state.lightCoords, camera);
        poseStack.popPose();
    }
}
