package com.patrickgatewood.runelitetouchbar;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

// TODO: - Add macOS minimum version specifier here and in the plugin properties.
//  Also see if we can hide the plugin on unsupported systems.
@Slf4j
@PluginDescriptor(
	name = "Touch Bar",
	description = "Touch Bar settings for macOS",
	tags = { "mac", "macOS", "touch bar", "f keys", "f-keys" },
	loadWhenOutdated = true
)
public class TouchBarPlugin extends Plugin
{
	@Inject
	private Client client;
	@Inject
	ConfigManager configManager;
	@Inject
	private TouchBarConfig config;

	@Override
	protected void startUp() throws Exception
	{
		// TODO: - Launch the OSRS Touch Bar process.
		// TODO: - Get the user's F-key settings from the client (F1 attack styles, etc)

		loadConfigAndPresentTouchBar();
	}

	private void loadConfigAndPresentTouchBar() {
		// TODO: - load all stored config options and style the touch bar appropriately!

		// Control Strip
		String showControlStripString = configManager.getConfiguration(TouchBarConfig.configGroup, "toggleControlStrip");
		boolean showControlStrip = Boolean.parseBoolean(showControlStripString);
		HTTPClient.toggleControlStrip(showControlStrip);

		// Touch Bar Presentation
		String touchBarTypeString = configManager.getConfiguration(TouchBarConfig.configGroup, "touchBarType");
		TouchBarType touchBarType = TouchBarType.valueOf(touchBarTypeString);
		presentTouchBar(touchBarType);
	}

	@Override
	protected void shutDown() throws Exception
	{
		// TODO: restore control strip prefs and whatever else
	}

	@Provides
	TouchBarConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(TouchBarConfig.class);
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event) {
		if (!event.getGroup().equals(TouchBarConfig.configGroup)) {
			return;
		}

		switch (event.getKey()) {
			case "toggleControlStrip":
				boolean showControlStrip = Boolean.parseBoolean(event.getNewValue());
				HTTPClient.toggleControlStrip(showControlStrip);
			case "touchBarType":
				TouchBarType touchBarType = TouchBarType.valueOf(event.getNewValue());
				presentTouchBar(touchBarType);
				break;
			case "customizeFKeyTouchBar":
				boolean isCustomizationEnabled = Boolean.parseBoolean(event.getNewValue());

				if (isCustomizationEnabled) {
					HTTPClient.presentTouchBarCustomizationWindow();
				} else {
					// TODO: - Load standard F Key touch bar
				}
				break;
				// TODO: - implement fit f keys to touch bar
			default:
				break;
		}
	}

	private void presentTouchBar(TouchBarType touchBarType) {
		switch (touchBarType) {
			case F_KEYS:
				HTTPClient.presentFKeyTouchBar();
				break;
			case NONE:
				HTTPClient.killTouchBar();
				break;
		}
	}
}
