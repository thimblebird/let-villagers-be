package io.thimblebird.letvillagersbe.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends LivingEntityMixin {
    public VillagerEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    protected void lvb$isPushable(CallbackInfoReturnable<Boolean> cir) {
        if (this.isSleepingInBed()) {
            cir.setReturnValue(false);
        }
    }
}
