package com.patrickgatewood.runelitetouchbar;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup(TouchBarConfig.groupName)
public interface TouchBarConfig extends Config
{
	String groupName = "touchBar";

	enum TouchBarType
	{
		DEFAULT_F_KEYS,
		CUSTOM_F_KEYS,
		DYNAMIC,
		NONE
	}
	@ConfigItem(
			keyName = "touchBarType",
			name = "Touch Bar Type",
			description = "Which controls and information to show in the Touch Bar"
	)
	default TouchBarType enumConfig() { return TouchBarType.DEFAULT_F_KEYS; }
}
