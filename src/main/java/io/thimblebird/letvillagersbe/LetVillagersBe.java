package io.thimblebird.letvillagersbe;

import io.thimblebird.letvillagersbe.config.LetVillagersBeConfig;
import io.thimblebird.letvillagersbe.event.AttackBlockHandler;
import io.thimblebird.letvillagersbe.event.AttackEntityHandler;
import io.thimblebird.letvillagersbe.event.UseBlockHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.BedPart;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class LetVillagersBe implements ModInitializer {
	public static String MOD_ID = "letvillagersbe";
	public static final LetVillagersBeConfig CONFIG = LetVillagersBeConfig.createAndLoad();

	@Override
	public void onInitialize() {
		// register event handlers
		AttackBlockCallback.EVENT.register(new AttackBlockHandler());
		UseBlockCallback.EVENT.register(new UseBlockHandler());
		AttackEntityCallback.EVENT.register(new AttackEntityHandler());
	}

	public static final byte STATUS_NONE = 0;
	public static final byte STATUS_HEART = 12;
	public static final byte STATUS_ANGRY = 13;
	public static final byte STATUS_HAPPY = 14;
	public static final byte STATUS_SPLASH = 42;

	public static final String LANG_KEY = "text." + MOD_ID;
	public static final String MESSAGE_BREAK_BED = LANG_KEY + ".break_bed";
	public static final Object[][] WAKE_REACTIONS = {
		// [translation key], [try "say no"], ["say no" chance], [entity status]
		{ LANG_KEY + ".wake_reaction.wants_to_sleep", true, 1.0D, STATUS_ANGRY },
		{ LANG_KEY + ".wake_reaction.needs_their_sleep", true, 0.5D, STATUS_SPLASH },
		{ LANG_KEY + ".wake_reaction.let_sleep", false, 0.0D, STATUS_NONE },
		{ LANG_KEY + ".wake_reaction.wont_get_up", true, 0.5D, STATUS_ANGRY },
		{ LANG_KEY + ".wake_reaction.ignores_you", false, 0.0D, STATUS_NONE },
		{ LANG_KEY + ".wake_reaction.had_a_long_day", true, 0.5D, STATUS_SPLASH },
		{ LANG_KEY + ".wake_reaction.is_dreaming_about_sleeping", false, 0.0D, STATUS_HAPPY },
		{ LANG_KEY + ".wake_reaction.is_dreaming_about_you_letting_them_sleep", false, 0.0D, STATUS_HEART },
		{ LANG_KEY + ".wake_reaction.is_having_a_nap", false, 0.0D, STATUS_NONE },
	};

	public static boolean isBedBlock(World world, BlockPos pos) {
		return world.getBlockState(pos).getBlock() instanceof BedBlock;
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

	public static boolean isBedAndOccupiedByVillager(World world, BlockPos pos) {
		if (isBedBlock(world, pos)) {
			return isBedOccupiedByVillager(world, pos);
		}

		return false;
	}

	public static BlockPos getBedBlockPos(World world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);

		if (world.getBlockState(pos).get(BedBlock.PART) != BedPart.HEAD) {
			return pos.offset(state.get(BedBlock.FACING));
		}

		return pos;
	}

	public static boolean isSurvivalPlayer(PlayerEntity player) {
		return player.isAlive() && !player.isCreative() && !player.isSpectator();
	}
}
