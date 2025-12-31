package fun.bm.killmertm;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class KillmeRTM implements ModInitializer {

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("killme")
                    .executes(this::executeKill)
            );
        });
    }

    private int executeKill(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();

        if (source.getPlayer() == null) {
            source.sendFeedback(() -> Text.literal("该命令只能由玩家执行"), false);
            return 0;
        }

        ServerPlayerEntity player = source.getPlayer();
        player.kill(player.getEntityWorld());
        source.sendFeedback(() -> Text.literal("已自杀"), true);
        return 1;
    }
}
