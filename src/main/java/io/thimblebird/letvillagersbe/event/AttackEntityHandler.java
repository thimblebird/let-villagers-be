package io.thimblebird.letvillagersbe.event;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static io.thimblebird.letvillagersbe.LetVillagersBe.CONFIG;
import static io.thimblebird.letvillagersbe.LetVillagersBe.LANG_KEY;
import static io.thimblebird.letvillagersbe.util.PlayerUtil.isSurvival;
import static io.thimblebird.letvillagersbe.util.WakeUpEntityUtil.shouldWakeUp;

public class AttackEntityHandler implements AttackEntityCallback {
    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, Entity entity, @Nullable EntityHitResult hitResult) {
        if (!CONFIG.playerDamageVillagerAllowed()) {
            if (isSurvival(player) && shouldWakeUp(entity) && ((LivingEntity) entity).isSleeping()) {
                if (!((LivingEntity) entity).isSleepingInBed()) {
                    player.sendMessage(Text.translatable(LANG_KEY + ".wake_reaction.let_sleep", entity.getName()), true);
                }

                return ActionResult.FAIL;
            }
        }

        return ActionResult.PASS;
    }
}
