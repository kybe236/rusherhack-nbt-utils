package org.nbt;

import org.nbt.command.NbtCommand;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.plugin.Plugin;

/**
 * NbtEditor
 *
 * @author kybe236
 */
public class NbtMain extends Plugin {
	
	@Override
	public void onLoad() {
		
		//logger
		this.getLogger().info("[NBTVIEWER] Started");

		final NbtCommand nbtCommand = new NbtCommand();
		RusherHackAPI.getCommandManager().registerFeature(nbtCommand);
	}
	
	@Override
	public void onUnload() {
		this.getLogger().info("[NBTVIEWER] unloaded!");
	}
	
}