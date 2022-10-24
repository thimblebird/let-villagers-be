package io.thimblebird.letvillagersbe.mixin.block;

import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.piston.PistonBehavior;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static io.thimblebird.letvillagersbe.LetVillagersBe.CONFIG;
import static net.minecraft.block.BedBlock.OCCUPIED;

@Mixin(BedBlock.class)
public abstract class BedBlockMixin extends HorizontalFacingBlock {
    protected BedBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(
            at = @At("TAIL"),
            method = "getPistonBehavior(Lnet/minecraft/block/BlockState;)Lnet/minecraft/block/piston/PistonBehavior;",
            cancellable = true
    )
    public void lvb$getPistonBehavior(BlockState state, CallbackInfoReturnable<PistonBehavior> cir) {
        if (!CONFIG.bedBreakingUsingPistonsAllowed()) { // experimental
            // TODO: figure out how to only check for sleeping villagers and not general `OCCUPIED` state somehow :(
            if (state.get(OCCUPIED)) {
                cir.setReturnValue(PistonBehavior.IGNORE);
            }
        }
    }
}
