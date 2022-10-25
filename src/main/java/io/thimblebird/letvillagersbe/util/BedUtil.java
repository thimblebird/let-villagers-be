package io.thimblebird.letvillagersbe.util;

import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.BedPart;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class BedUtil {
    public static boolean isBedBlock(World world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() instanceof BedBlock;
    }

    public static BlockPos getBedBlockPos(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);

        if (state.get(BedBlock.PART) != BedPart.HEAD) {
            return pos.offset(state.get(BedBlock.FACING));
        }

        return pos;
    }

    public static LivingEntity getEntityOccupyingBed(World world, BlockPos pos) {
        List<LivingEntity> entityList = world.getEntitiesByClass(LivingEntity.class, new Box(pos), LivingEntity::isSleeping);

        if (!entityList.isEmpty()) {
            return entityList.get(0);
        }

        return null;
    }

    /*public static Class<?> getEntityClassOccupyingBed(World world, BlockPos pos) {
        return Objects.requireNonNull(getEntityOccupyingBed(world, pos)).getClass();
    }*/

    public static boolean isOccupied(World world, BlockPos pos) {
        return getEntityOccupyingBed(world, pos) != null;
    }

    public static boolean isBedAndOccupied(World world, BlockPos pos) {
        return isBedBlock(world, pos) && isOccupied(world, pos);
    }
}
