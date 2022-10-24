package io.thimblebird.letvillagersbe.mixin.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.PistonBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static io.thimblebird.letvillagersbe.LetVillagersBe.CONFIG;
import static net.minecraft.block.BedBlock.OCCUPIED;

@Mixin(PistonBlock.class)
public class PistonBlockMixin extends FacingBlock {
    protected PistonBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;getPistonBehavior()Lnet/minecraft/block/piston/PistonBehavior;"
            ),
            method = "isMovable(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;ZLnet/minecraft/util/math/Direction;)Z",
            cancellable = true
    )
    private static void lvb$injectedIsMovable(
            BlockState state,
            World world,
            BlockPos pos,
            Direction direction,
            boolean canBreak,
            Direction pistonDir,
            CallbackInfoReturnable<Boolean> cir
    ) {
        System.out.println(">>> START");

        if (!CONFIG.bedBreakingUsingPistonsAllowed()) {
            System.out.println("CONFIG.bedBreakingUsingPistonsAllowed() === false");

            // isBedBlock(world, pos)
            if (state.get(OCCUPIED)) {
                System.out.println("state.get(OCCUPIED) === true");
                cir.setReturnValue(false);
            } else {
                System.out.println("state.get(OCCUPIED) === false");
            }
        }

        System.out.println(">>> END");
    }
}
