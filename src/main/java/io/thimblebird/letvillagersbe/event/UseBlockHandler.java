package io.thimblebird.letvillagersbe.event;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static io.thimblebird.letvillagersbe.LetVillagersBe.CONFIG;
import static io.thimblebird.letvillagersbe.util.BedUtil.*;
import static io.thimblebird.letvillagersbe.util.PlayerUtil.isSurvival;
import static io.thimblebird.letvillagersbe.util.VillagerUtil.respond;

public class UseBlockHandler implements UseBlockCallback {
    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        if (!CONFIG.villagerWakeUpAllowed() && isSurvival(player)) {
            BlockPos hitBlockPos = hitResult.getBlockPos();

            if (isBedBlock(world, hitBlockPos)) {
                hitBlockPos = getBedBlockPos(world, hitBlockPos);

                if (isBedAndOccupied(world, hitBlockPos)) {
                    VillagerEntity villager = (VillagerEntity) getEntityOccupyingBed(world, hitBlockPos);

                    if (CONFIG.villagerResponseChance() > 0) {
                        respond(villager, player);
                    }

                    return ActionResult.SUCCESS;
                }
            }
        }

        return ActionResult.PASS;
    }
}
