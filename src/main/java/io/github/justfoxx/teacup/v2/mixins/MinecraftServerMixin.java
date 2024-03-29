package io.github.justfoxx.teacup.v2.mixins;

import io.github.justfoxx.teacup.v2.event.data.SingletonData;
import io.github.justfoxx.teacup.v2.event.events.MinecraftServerEvents;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(method = "runServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;setupServer()Z"))
    private void beforeServerStarting(CallbackInfo ci) {
        MinecraftServerEvents.getON_SERVER_STARTING().invoker().invoke(new SingletonData<>((MinecraftServer) (Object) this, ci));
    }

    @Inject(method = "runServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;createMetadata()Lnet/minecraft/server/ServerMetadata;", ordinal = 0))
    private void afterServerStarted(CallbackInfo ci) {
        MinecraftServerEvents.getON_SERVER_STARTED().invoker().invoke(new SingletonData<>((MinecraftServer) (Object) this, ci));
    }

    @Inject(method = "shutdown", at = @At("HEAD"))
    private void beforeServerStopping(CallbackInfo ci) {
        MinecraftServerEvents.getON_SERVER_STOPPING().invoker().invoke(new SingletonData<>((MinecraftServer) (Object) this, ci));
    }

    @Inject(method = "shutdown", at = @At("RETURN"))
    private void afterServerStopped(CallbackInfo ci) {
        MinecraftServerEvents.getON_SERVER_STOPPED().invoker().invoke(new SingletonData<>((MinecraftServer) (Object) this, ci));
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTicking(CallbackInfo ci) {
        MinecraftServerEvents.getON_SERVER_TICKING().invoker().invoke(new SingletonData<>((MinecraftServer) (Object) this, ci));
    }

    @Inject(method = "tick", at = @At("RETURN"))
    private void onTicked(CallbackInfo ci) {
        MinecraftServerEvents.getON_SERVER_TICKED().invoker().invoke(new SingletonData<>((MinecraftServer) (Object) this, ci));
    }
}
