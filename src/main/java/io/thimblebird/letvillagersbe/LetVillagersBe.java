package io.thimblebird.letvillagersbe;

import io.thimblebird.letvillagersbe.event.AttackBlockHandler;
import io.thimblebird.letvillagersbe.event.UseBlockHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.BedPart;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class LetVillagersBe implements ModInitializer {
	public static final String MOD_ID = "letvillagersbe";

	public static final byte STATUS_NONE = 0;
	public static final byte STATUS_HEART = 12;
	public static final byte STATUS_ANGRY = 13;
	public static final byte STATUS_HAPPY = 14;
	public static final byte STATUS_SPLASH = 42;

	public static final String MESSAGE_BREAK_BED = MOD_ID + ".break_bed";
	public static final Object[][] WAKE_REACTIONS = {
			// translation key, try "say no", "say no" chance, entity status, status chance
			{ MOD_ID + ".entity.wants_to_sleep", true, 1.0D, STATUS_ANGRY, 0.7D },
			{ MOD_ID + ".entity.needs_their_sleep", true, 0.5D, STATUS_SPLASH, 0.85D },
			{ MOD_ID + ".entity.let_sleep", false, 0.0D, STATUS_NONE, 0.0D },
			{ MOD_ID + ".entity.wont_get_up", true, 0.5D, STATUS_ANGRY, 0.5D },
			{ MOD_ID + ".entity.ignores_you", false, 0.0D, STATUS_NONE, 0.0D },
			{ MOD_ID + ".entity.had_a_long_day", true, 0.5D, STATUS_SPLASH, 1.0D },
			{ MOD_ID + ".entity.is_dreaming_about_sleeping", false, 0.0D, STATUS_HAPPY, 0.5D },
			{ MOD_ID + ".entity.is_dreaming_about_you_letting_them_sleep", false, 0.0D, STATUS_HEART, 0.5D },
			{ MOD_ID + ".entity.is_having_a_nap", false, 0.0D, STATUS_NONE, 0.0D },
	};

	public static boolean isBedBlock(World world, BlockPos pos) {
		return world.getBlockState(pos).getBlock() instanceof BedBlock;
	}

	public static BlockPos getBedBlockPos(World world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);

		if (world.getBlockState(pos).get(BedBlock.PART) != BedPart.HEAD) {
			return pos.offset(state.get(BedBlock.FACING));
		}

		return pos;
	}

	public static VillagerEntity getVillagerOccupyingBed(World world, BlockPos pos) {
		// borrowed from VillagerEntity.class
		List<VillagerEntity> villagerEntityList = world.getEntitiesByClass(
				net.minecraft.entity.passive.VillagerEntity.class,
				new Box(pos),
				LivingEntity::isSleeping
		);

		if (!villagerEntityList.isEmpty()) {
			return villagerEntityList.get(0);
		}

		return null;
	}

	public static boolean isBedOccupiedByVillager(World world, BlockPos pos) {
		VillagerEntity villager = getVillagerOccupyingBed(world, pos);

		return villager != null;
	}

	@Override
	public void onInitialize() {
		UseBlockCallback.EVENT.register(new UseBlockHandler());
	}
}
