package com.patrickgatewood.runelitetouchbar;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.inject.Provides;
import javax.inject.Inject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Skill;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.http.api.RuneLiteAPI;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

@Slf4j
@PluginDescriptor(
	name = "Touch Bar"
)
public class TouchBarPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private TouchBarConfig config;

	private HttpServer server;

	@Override
	protected void startUp() throws Exception
	{
		log.info("Touch Bar started!");
		server = HttpServer.create(new InetSocketAddress(8080), 0);
		server.createContext("/stats", new StatsHandler());
		server.setExecutor(Executors.newSingleThreadExecutor());
		server.start();
	}

	@Override
	protected void shutDown() throws Exception
	{
		server.stop(1);
	}

	class StatsHandler implements HttpHandler
	{
		@Override
		public void handle(HttpExchange exchange) throws IOException
		{
			JsonArray skills = new JsonArray();
			for (Skill skill : Skill.values())
			{
				if (skill == Skill.OVERALL)
				{
					continue;
				}

				JsonObject object = new JsonObject();
				object.addProperty("stat", skill.getName());
				object.addProperty("level", client.getRealSkillLevel(skill));
				object.addProperty("boostedLevel", client.getBoostedSkillLevel(skill));
				object.addProperty("xp", client.getSkillExperience(skill));
				skills.add(object);
			}

			exchange.sendResponseHeaders(200, 0);
			try (OutputStreamWriter out = new OutputStreamWriter(exchange.getResponseBody()))
			{
				RuneLiteAPI.GSON.toJson(skills, out);
			}
		}
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Touch Bar says " + config.greeting(), null);
		}
	}

	@Provides
	TouchBarConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(TouchBarConfig.class);
	}
}
