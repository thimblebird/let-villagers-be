package io.thimblebird.letvillagersbe.event;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import static io.thimblebird.letvillagersbe.LetVillagersBe.*;

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
    private void randomSendStatus(VillagerEntity villager, byte status) {
        if (status != STATUS_NONE) {
            if (this.random.nextDouble() <= CONFIG.villagerResponseParticlesChance()) {
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

        if (!villager.world.isClient() && villager.isAlive()) {
            // try say no
            if ((boolean) this.wakeReaction[1]) {
                this.randomShakeHead(villager, (double) this.wakeReaction[2]);
            }

            if (CONFIG.villagerResponseParticlesChance() > 0.0d) {
                this.randomSendStatus(villager, (byte) this.wakeReaction[3]);
            }

            if (CONFIG.showMessageWhen.wakeUpVillager()) {
                player.sendMessage(this.getOccupiedMessage(villager), true);
            }
        }
    }

    private void respond(VillagerEntity villager, PlayerEntity player) {
        this.random = Random.create();

        // @TODO: doing this until i figure out how to add a proper cooldown
        // random chance to respond
        if (this.random.nextDouble() <= CONFIG.villagerResponseChance()) {
            this.sendResponse(villager, player);
        }
    }

    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        if (CONFIG.villagerWakeUpAllowed()) return ActionResult.PASS;

        if (isSurvivalPlayer(player)) {
            BlockPos hitBlockPos = hitResult.getBlockPos();

            if (isBedBlock(world, hitBlockPos)) {
                hitBlockPos = getBedBlockPos(world, hitBlockPos);

                if (isBedAndOccupiedByVillager(world, hitBlockPos)) {
                    VillagerEntity villager = getVillagerOccupyingBed(world, hitBlockPos);

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
