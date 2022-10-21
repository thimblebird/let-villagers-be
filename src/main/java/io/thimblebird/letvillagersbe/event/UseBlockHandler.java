package io.thimblebird.letvillagersbe.event;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.BedPart;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.List;

import static io.thimblebird.letvillagersbe.LetVillagersBe.STATUS_NONE;
import static io.thimblebird.letvillagersbe.LetVillagersBe.WAKE_REACTIONS;

public class UseBlockHandler implements UseBlockCallback {
    protected Random random;
    protected Object[] wakeReaction;

    // randomly make villager shake head and make noise
    private void randomShakeHead(VillagerEntity villager, double sayNoChance) {
        if (this.random.nextDouble() <= sayNoChance) {
            villager.sayNo();
        }
    }

    // spawns a random sprite above the villager's head
    private void randomSendStatus(VillagerEntity villager, byte status, double statusChance) {
        if (status != STATUS_NONE) {
            if (this.random.nextDouble() <= statusChance) {
                villager.getEntityWorld().sendEntityStatus(villager, status);
            }
        }
    }

    private MutableText getOccupiedMessage(VillagerEntity villager) {
        return Text.translatable((String) this.wakeReaction[0], villager.getName());
    }

    private void sendResponse(VillagerEntity villager, PlayerEntity player) {
        // get a random response
        this.wakeReaction = Util.getRandom(WAKE_REACTIONS, this.random);
        boolean trySayNo = (boolean) this.wakeReaction[1];

        if (!villager.world.isClient() && villager.isAlive()) {
            if (trySayNo) {
                this.randomShakeHead(villager, (double) this.wakeReaction[2]);
            }

            this.randomSendStatus(villager, (byte) this.wakeReaction[3], (double) this.wakeReaction[4]);
            player.sendMessage(this.getOccupiedMessage(villager), true);
        }
    }

    private void respond(VillagerEntity villager, PlayerEntity player) {
        this.random = Random.create();

        // @TODO: doing this until i figure out how to add a proper cooldown
        // random chance to respond
        if (this.random.nextDouble() <= 0.33D) {
            this.sendResponse(villager, player);
        }
    }

    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        // spectators can't get no sleep
        if (player.isSpectator()) return ActionResult.PASS;

        // only check when alive and in survival mode
        boolean isSurvival = !player.isCreative() && player.isAlive();

        if (isSurvival && !world.isClient()) {
            BlockPos hitBlockPos = hitResult.getBlockPos();
            BlockState hitBlockState = world.getBlockState(hitBlockPos);

            if (hitBlockState.getBlock() instanceof BedBlock) {
                if (hitBlockState.get(BedBlock.PART) != BedPart.HEAD) {
                    hitBlockPos = hitBlockPos.offset(hitBlockState.get(BedBlock.FACING));
                }

                // borrowed from VillagerEntity.class
                List<net.minecraft.entity.passive.VillagerEntity> villagerEntityList = world.getEntitiesByClass(net.minecraft.entity.passive.VillagerEntity.class, new Box(hitBlockPos), LivingEntity::isSleeping);

                if (!villagerEntityList.isEmpty()) {
                    VillagerEntity villager = villagerEntityList.get(0);

                    respond(villager, player);

                    return ActionResult.SUCCESS;
                }
            }
        }

        return ActionResult.PASS;
    }
}
