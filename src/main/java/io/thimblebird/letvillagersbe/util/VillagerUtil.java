package io.thimblebird.letvillagersbe.util;

import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.minecraft.util.math.random.Random;

import static io.thimblebird.letvillagersbe.LetVillagersBe.*;

public class VillagerUtil {
    private static Random random;
    private static Object[] wakeReaction;

    // randomly make villager shake head and make noise
    public static void randomShakeHead(VillagerEntity villager, double sayNoChance) {
        if (random.nextDouble() <= sayNoChance) {
            villager.sayNo();
        }
    }

    // spawns a random sprite above the villager's head
    public static void randomSendStatus(VillagerEntity villager, byte status) {
        if (status != STATUS_NONE && random.nextDouble() <= CONFIG.villagerResponseParticlesChance()) {
            villager.getEntityWorld().sendEntityStatus(villager, status);
        }
    }

    public static MutableText getOccupiedMessage(VillagerEntity villager) {
        return Text.translatable((String) wakeReaction[0], villager.getName());
    }

    public static void sendResponse(VillagerEntity villager, PlayerEntity player) {
        // get a random response
        wakeReaction = Util.getRandom(WAKE_REACTIONS, random);

        if (!villager.world.isClient() && villager.isAlive()) {
            // try say no
            if ((boolean) wakeReaction[1]) {
                randomShakeHead(villager, (double) wakeReaction[2]);
            }

            if (CONFIG.villagerResponseParticlesChance() > 0.0d) {
                randomSendStatus(villager, (byte) wakeReaction[3]);
            }

            if (CONFIG.showMessageWhen.wakeUpVillager()) {
                player.sendMessage(getOccupiedMessage(villager), true);
            }
        }
    }

    public static void respond(VillagerEntity villager, PlayerEntity player) {
        random = Random.create();

        // @TODO: doing this until i figure out how to add a proper cooldown
        // random chance to respond
        if (random.nextDouble() <= CONFIG.villagerResponseChance()) {
            sendResponse(villager, player);
        }
    }
}
