package io.thimblebird.letvillagersbe.mixin.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    // modified to `public` by access widener for usage in `VillagerEntityMixin`
    @Shadow public abstract boolean isSleepingInBed();

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    /**
     * workaround for using `isSleepingInBed()` and overriding `isPushable()` in `VillagerEntityMixin`.
     * `cancellable` needs to be set to `true` here; ignore the warning.
     * @see <a href="https://fabricmc.net/wiki/tutorial:mixinheritance?s[]=mixin&s[]=inheritance#a_technique_for_improved_compatibility">[Fabric Wiki] Mixin Inheritance</a>
     * @see VillagerEntityMixin
     */
    @SuppressWarnings("all")
    @Inject(
            at = @At("RETURN"),
            method = "isPushable",
            cancellable = true
    )
    protected void lvb$isPushable(CallbackInfoReturnable<Boolean> cir) {}
}
