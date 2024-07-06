package org.nbt;

import org.nbt.command.NbtCommand;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.plugin.Plugin;

/**
 * Example rusherhack plugin
 *
 * @author John200410
 */
public class NbtMain extends Plugin {
	
	@Override
	public void onLoad() {
		
		//logger
		this.getLogger().info("Kybes World!");

		/*
		//creating and registering a new module
		final ExampleModule exampleModule = new ExampleModule();
		RusherHackAPI.getModuleManager().registerFeature(exampleModule);
		
		//creating and registering a new hud element
		final ExampleHudElement exampleHudElement = new ExampleHudElement();
		RusherHackAPI.getHudManager().registerFeature(exampleHudElement);
		
		//creating and registering a new command
		final ExampleCommand exampleCommand = new ExampleCommand();
		RusherHackAPI.getCommandManager().registerFeature(exampleCommand);
		 */

		final NbtCommand nbtCommand = new NbtCommand();
		RusherHackAPI.getCommandManager().registerFeature(nbtCommand);
	}
	
	@Override
	public void onUnload() {
		this.getLogger().info("Example plugin unloaded!");
	}
	
}