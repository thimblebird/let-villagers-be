package io.thimblebird.letvillagersbe.config;

import com.mojang.datafixers.util.Pair;
import io.thimblebird.letvillagersbe.LetVillagersBe;

public class ModConfigs {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider configs;

    public static boolean BED_VILLAGER_ALLOW_WAKE_UP;
    public static boolean BED_VILLAGER_ALLOW_PLAYER_DAMAGE;
    public static boolean BED_VILLAGER_ALLOW_PLAYER_PROJECTILE_DAMAGE;
    public static boolean BED_VILLAGER_IS_PUSHABLE;
    public static boolean BED_BREAKING_ALLOW_BREAKING;
    public static boolean BED_BREAKING_ALLOW_PISTON_BREAKING;

    public static void registerConfigs() {
        configs = new ModConfigProvider();

        createConfigs();
        CONFIG = SimpleConfig.of(LetVillagersBe.MOD_ID + "-config").provider(configs).request();
        assignConfigs();
    }

    private static void createConfigs() {
        configs.addKeyValuePair(new Pair<>("bed.villager.allow_wake_up", false), "boolean");
        configs.addKeyValuePair(new Pair<>("bed.villager.allow_player_damage", false), "boolean");
        configs.addKeyValuePair(new Pair<>("bed.villager.allow_player_projectile_damage", true), "boolean (not implemented yet)");
        configs.addKeyValuePair(new Pair<>("bed.villager.is_pushable", false), "boolean");
        configs.addKeyValuePair(new Pair<>("bed.breaking.allow_breaking", false), "boolean");
        configs.addKeyValuePair(new Pair<>("bed.breaking.allow_piston_breaking", false), "boolean (experimental)");
    }

    private static void assignConfigs() {
        BED_VILLAGER_ALLOW_WAKE_UP = CONFIG.getOrDefault("bed.villager.allow_wake_up", false);
        BED_VILLAGER_ALLOW_PLAYER_DAMAGE = CONFIG.getOrDefault("bed.villager.allow_player_damage", false);
        BED_VILLAGER_ALLOW_PLAYER_PROJECTILE_DAMAGE = CONFIG.getOrDefault("bed.villager.allow_player_projectile_damage", true);
        BED_VILLAGER_IS_PUSHABLE = CONFIG.getOrDefault("bed.villager.is_pushable", false);
        BED_BREAKING_ALLOW_BREAKING = CONFIG.getOrDefault("bed.breaking.allow_breaking", false);
        BED_BREAKING_ALLOW_PISTON_BREAKING = CONFIG.getOrDefault("bed.breaking.allow_piston_breaking", false);

        //System.out.println("All " + configs.getConfigsList().size() + " have been set properly");
    }
}
