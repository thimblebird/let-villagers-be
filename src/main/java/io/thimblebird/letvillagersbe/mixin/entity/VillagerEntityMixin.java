package io.thimblebird.letvillagersbe.mixin.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static io.thimblebird.letvillagersbe.LetVillagersBe.CONFIG;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends LivingEntityMixin {
    public VillagerEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    protected void lvb$isPushable(CallbackInfoReturnable<Boolean> cir) {
        if (!CONFIG.villagerPushingAllowed() && this.isSleepingInBed()) {
            cir.setReturnValue(false);
        }
    }
}
