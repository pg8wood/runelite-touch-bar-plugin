package com.patrickgatewood.runelitetouchbar;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

enum TouchBarType
{
	F_KEYS,
	NONE
}

@ConfigGroup(TouchBarConfig.configGroup)
public interface TouchBarConfig extends Config
{
	String configGroup = "touchBar";

	@ConfigItem(
			keyName = "toggleControlStrip",
			name = "Show Control Strip",
			description = "Show or hide the Control Strip while running RuneLite. This may or may not apply to you depending on your Touch Bar settings in System Preferences -> Keyboard",
			position = 2
	)
	default boolean showControlStrip() { return false; }

	@ConfigItem(
			keyName = "touchBarType",
			name = "Touch Bar Type",
			description = "Which controls and information to show in the Touch Bar",
			position = 1
	)
	default TouchBarType touchBarType() { return TouchBarType.F_KEYS; }

	@ConfigItem(
			keyName = "customizeFKeyTouchBar",
			name = "Customize F-Key Touch Bar",
			description = "If checked will allow you to add/remove buttons to the Touch Bar",
			position = 3
	)
	default boolean customizeFKeyTouchBar() { return false; }

	@ConfigItem(
			keyName = "fitFKeyButtonsToTouchBar",
			name = "Fit F-Key Buttons to Touch Bar",
			description = "If checked will attempt to size the buttons to fit the Touch Bar's available space",
			position = 4
	)
	// TODO: - implement this in the plugin and HTTP Server
	default boolean fitFKeyButtonsToTouchBar() { return false; }
}
