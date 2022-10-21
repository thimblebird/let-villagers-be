package io.thimblebird.letvillagersbe;

import io.thimblebird.letvillagersbe.event.UseBlockHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;

public class LetVillagersBe implements ModInitializer {
	public static final String MOD_ID = "letvillagersbe";

	public static final byte STATUS_NONE = 0;
	public static final byte STATUS_HEART = 12;
	public static final byte STATUS_ANGRY = 13;
	public static final byte STATUS_HAPPY = 14;
	public static final byte STATUS_SPLASH = 42;

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

	@Override
	public void onInitialize() {
		UseBlockCallback.EVENT.register(new UseBlockHandler());
	}
}
