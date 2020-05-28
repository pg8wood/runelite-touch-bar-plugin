package com.patrickgatewood.runelitetouchbar;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;

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
	private ClientToolbar clientToolbar;
	@Inject
	private TouchBarConfig config;

	@Override
	protected void startUp() throws Exception
	{
		// TODO: - Get the user's F-key settings from the client (F1 attack styles, etc)
	}

	@Override
	protected void shutDown() throws Exception
	{
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
//			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Touch Bar says " + config.greeting(), null);
		}
	}

	@Provides
	TouchBarConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(TouchBarConfig.class);
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event) {
		if (!event.getGroup().equals(TouchBarConfig.groupName)) {
			return;
		}

		switch (event.getKey()) {
			case "touchBarType":
				log.info("new config value: " + event.getNewValue());
				break;
			default:
				break;
		}
	}
}
