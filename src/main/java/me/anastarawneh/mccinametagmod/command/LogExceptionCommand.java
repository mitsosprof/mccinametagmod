package me.anastarawneh.mccinametagmod.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import me.anastarawneh.mccinametagmod.MCCINametagMod;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class LogExceptionCommand {
    public static void register() {
        // Plain Brigadier is used instead of Fabric's literal() helper, whose class was renamed in 26.x.
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(
                LiteralArgumentBuilder.<FabricClientCommandSource>literal("logexception")
                        .executes(LogExceptionCommand::execute)));
    }

    static int execute(CommandContext<FabricClientCommandSource> context) {
        if (MCCINametagMod.LATEST_EXCEPTION == null) {
            context.getSource().sendFeedback(MCCINametagMod.MESSAGE_PREFIX.copy().append("Nothing to debug."));
            return 1;
        }
        MCCINametagMod.LOGGER.warn("Throwing latest exception:", MCCINametagMod.LATEST_EXCEPTION);
        context.getSource().sendFeedback(MCCINametagMod.MESSAGE_PREFIX.copy().append("Logged the latest exception."));
        MCCINametagMod.LATEST_EXCEPTION = null;
        return 1;
    }
}
