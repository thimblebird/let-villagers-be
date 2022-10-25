package io.thimblebird.letvillagersbe.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

import java.util.List;

public class WakeUpEntityUtil {
    public static final List<?> WakeUpEntityTypes = List.of(
            EntityType.VILLAGER,
            EntityType.FOX,
            EntityType.CAT
    );

    public static boolean shouldWakeUp(Entity entity) {
        return WakeUpEntityTypes.contains(entity.getType());
    }
}
