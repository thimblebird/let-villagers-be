package io.thimblebird.letvillagersbe.config;

import io.wispforest.owo.config.annotation.*;

@SuppressWarnings("unused")
@Modmenu(modId = "letvillagersbe")
@Config(name = "letvillagersbe-config", wrapperName = "LetVillagersBeConfig")
public class LetVillagersBeConfigModel {
    @SectionHeader("sectionPlayersConfiguration")
    public boolean playerDamageVillagerAllowed = false;

    @SectionHeader("sectionVillagersConfiguration")
    public boolean villagerWakeUpAllowed = false;
    public boolean villagerPushingAllowed = false;
    @RangeConstraint(min = 0.0d, max = 1.0d)
    public double villagerResponseChance = 0.33d;
    @RangeConstraint(min = 0.0d, max = 1.0d)
    public double villagerResponseParticlesChance = 0.66d;

    @SectionHeader("sectionBedsConfiguration")
    public boolean bedBreakingAllowed = false;

    @SectionHeader("sectionMessagesConfiguration")
    @Nest
    @Expanded
    public ShowMessageWhenTryingTo showMessageWhen = new ShowMessageWhenTryingTo();
    public static class ShowMessageWhenTryingTo {
        public boolean wakeUpVillager = true;
        public boolean breakingBad = true;
    }

    @SectionHeader("sectionExperimentalConfiguration")
    public boolean bedBreakingUsingPistonsAllowed = false;

    @SectionHeader("sectionNotImplementedConfiguration")
    public boolean playerProjectileDamageAllowed = false;
    public boolean highlightVillagerName = true;
}
