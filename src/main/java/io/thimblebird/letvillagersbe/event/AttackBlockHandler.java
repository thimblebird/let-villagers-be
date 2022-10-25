package io.thimblebird.letvillagersbe.event;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import static io.thimblebird.letvillagersbe.LetVillagersBe.*;
import static io.thimblebird.letvillagersbe.util.BedUtil.*;
import static io.thimblebird.letvillagersbe.util.PlayerUtil.isSurvival;
import static io.thimblebird.letvillagersbe.util.WakeUpEntityUtil.shouldWakeUp;

public class AttackBlockHandler implements AttackBlockCallback {
    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockPos pos, Direction direction) {
        if (!CONFIG.bedBreakingAllowed() && isSurvival(player) && isBedBlock(world, pos)) {
            pos = getBedBlockPos(world, pos);

            if (isBedAndOccupied(world, pos)) {
                LivingEntity entity = getEntityOccupyingBed(world, pos);
                assert entity != null;

                if (shouldWakeUp(entity)) {
                    if (CONFIG.showMessageWhen.breakingBad()) {
                        player.sendMessage(Text.translatable(MESSAGE_BREAK_BED, entity.getName()), true);
                    }

                    return ActionResult.CONSUME;
                }
            }
        }

        return ActionResult.PASS;
    }
}
