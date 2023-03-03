package io.github.justfoxx.teacup;

import io.github.justfoxx.teacup.v1.event.Events;
import io.github.justfoxx.teacup.v1.utils.Mod;

import io.github.justfoxx.teacup.v1.utils.tuples.Pair;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.minecraft.util.ActionResult;

import java.util.Optional;

public class PreMain implements PreLaunchEntrypoint {
    public static final Mod MOD = new Mod("teacup");
    @Override
    public void onPreLaunch() {
        Events.CONFIG.onEvent(
                config -> MOD.getLogger().info("Config: " + config.toString()),
                new Pair<>(MOD, Optional.empty())
        );
        Events.ON_ITEM_USE.onEvent(
                data -> {
                    MOD.getLogger().info("Player: " + data.player().getName().getString());
                    MOD.getLogger().info("Stack: " + data.itemStack().toString());
                    MOD.getLogger().info("Hand: " + data.hand().toString());
                    return ActionResult.PASS;
                },
                $ -> true
        );
    }
}