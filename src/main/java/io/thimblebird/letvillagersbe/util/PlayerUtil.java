package io.thimblebird.letvillagersbe.util;

public class PlayerUtil {
    public static boolean isSurvival(net.minecraft.entity.player.PlayerEntity player) {
        return player.isAlive() && !player.isCreative() && !player.isSpectator() && player.isPlayer();
    }
}
