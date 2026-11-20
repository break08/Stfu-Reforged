package stfu.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.clothconfig2.gui.entries.SelectionListEntry;
import org.jetbrains.annotations.NotNull;

@Config(name = "stfu")
public class StfuConfig implements ConfigData {
    public static StfuConfig get(){
        return AutoConfig.getConfigHolder(StfuConfig.class).getConfig();
    }
    // Basic
    @ConfigEntry.Category("basic")
    @ConfigEntry.Gui.Tooltip
    public int maxChatHistory = 100;

    @ConfigEntry.Category("basic")
    @ConfigEntry.Gui.Tooltip
    public boolean announceAdvancements = true;

    @ConfigEntry.Category("basic")
    @ConfigEntry.Gui.Tooltip
    public boolean advancementToasts = true;

    @ConfigEntry.Category("basic")
    @ConfigEntry.Gui.Tooltip
    public boolean recipeToasts = false;

    @ConfigEntry.Category("basic")
    @ConfigEntry.Gui.Tooltip
    public AdminChat adminChat = AdminChat.ENABLED;

    @ConfigEntry.Category("basic")
    @ConfigEntry.Gui.Tooltip
    public CompactChat compactChat = CompactChat.ONLY_CONSECUTIVE;

    @ConfigEntry.Category("basic")
    @ConfigEntry.Gui.Tooltip
    public boolean disableWidgetFade = true;

    @ConfigEntry.Category("basic")
    @ConfigEntry.Gui.Tooltip
    public boolean disableFade = false;

    @ConfigEntry.Category("basic")
    @ConfigEntry.Gui.Tooltip
    public boolean disableSplash = true;

    @ConfigEntry.Category("basic")
    @ConfigEntry.Gui.Tooltip
    public boolean disableLoadingTerrain = true;

    @ConfigEntry.Category("basic")
    @ConfigEntry.Gui.Tooltip
    public boolean disableWorldAdvice = true;

    @ConfigEntry.Category("basic")
    @ConfigEntry.Gui.Tooltip
    public boolean nightVisionFlicker = false;

    @ConfigEntry.Category("basic")
    @ConfigEntry.Gui.Tooltip
    public boolean disableParticles = false;

    @ConfigEntry.Category("basic")
    @ConfigEntry.Gui.Tooltip
    public boolean animateTextures = true;

    @ConfigEntry.Category("basic")
    @ConfigEntry.Gui.Tooltip
    public boolean renderWeather = true;

    @ConfigEntry.Category("basic")
    @ConfigEntry.Gui.Tooltip
    public boolean deleteToTrash = true;

    @ConfigEntry.Category("basic")
    @ConfigEntry.BoundedDiscrete(min = 0, max = 1)
    @ConfigEntry.Gui.Tooltip
    public float unfocusedVolume = 1F;

    // Experimental
    @ConfigEntry.Category("experimental")
    @ConfigEntry.Gui.Tooltip(count = 5)
    public boolean fixModelGaps = true;

    @ConfigEntry.Category("experimental")
    @ConfigEntry.Gui.Tooltip
    public boolean tooltip_fix = false;

    // Advanced

    /*
    @ConfigEntry.Category("advanced")
    @ConfigEntry.BoundedDiscrete(min = 1, max = 10)
    @ConfigEntry.Gui.Tooltip
    public int renderThreadPriority = (Runtime.getRuntime().availableProcessors() > 4) ? 8 : 5;

    @ConfigEntry.Category("advanced")
    @ConfigEntry.BoundedDiscrete(min = 1, max = 10)
    @ConfigEntry.Gui.Tooltip
    public int serverThreadPriority = (Runtime.getRuntime().availableProcessors() > 4) ? 8 : 5;

    @ConfigEntry.Category("advanced")
    @ConfigEntry.BoundedDiscrete(min = 1, max = 10)
    @ConfigEntry.Gui.Tooltip
    public int ioThreadPriority = 1;

     */

    public enum AdminChat implements SelectionListEntry.Translatable {
        ENABLED,
        ONLY_PLAYERS,
        DISABLED;

        @Override
        public @NotNull String getKey() {
            return "text.autoconfig.stfu.option.adminChat." + name().toLowerCase();
        }
    }

    public enum CompactChat implements SelectionListEntry.Translatable {
        ALL,
        ONLY_CONSECUTIVE,
        NEVER;

        @Override
        public @NotNull String getKey() {
            return "text.autoconfig.stfu.option.compactChat." + name().toLowerCase();
        }
    }
}

