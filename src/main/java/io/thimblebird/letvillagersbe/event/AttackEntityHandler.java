package io.thimblebird.letvillagersbe.event;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static io.thimblebird.letvillagersbe.LetVillagersBe.isSurvivalPlayer;

public class AttackEntityHandler implements AttackEntityCallback {
    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, Entity entity, @Nullable EntityHitResult hitResult) {
        if (!isSurvivalPlayer(player)) return ActionResult.PASS;

        if (!world.isClient()) {
            if (entity instanceof VillagerEntity && ((VillagerEntity) entity).isSleeping()) {
                return ActionResult.FAIL;
            }
        }

        return ActionResult.PASS;
    }
}
