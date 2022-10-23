package io.thimblebird.letvillagersbe.event;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import static io.thimblebird.letvillagersbe.LetVillagersBe.*;
import static io.thimblebird.letvillagersbe.config.ModConfigs.BED_BREAKING_ALLOW_BREAKING;

public class AttackBlockHandler implements AttackBlockCallback {
    @SuppressWarnings("DanglingJavadoc")
    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockPos pos, Direction direction) {
        /**
         * config check
         * @see io.thimblebird.letvillagersbe.config.ModConfigs
         */
        if (BED_BREAKING_ALLOW_BREAKING) return ActionResult.PASS;

        if (isSurvivalPlayer(player)) {
            if (isBedBlock(world, pos)) {
                pos = getBedBlockPos(world, pos);

                if (isBedAndOccupiedByVillager(world, pos)) {
                    VillagerEntity villager = getVillagerOccupyingBed(world, pos);

                    if (villager != null) {
                        player.sendMessage(Text.translatable(MESSAGE_BREAK_BED, villager.getName()), true);
                    }

                    return ActionResult.CONSUME;
                }
            }
        }

        return ActionResult.PASS;
    }
}
