package com.stand;


import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class PluginCollection {

	private static ArrayList<UUID> pvpEnabledList = new ArrayList<>();

	public static void addPvpPlayer(final Player player) {
		pvpEnabledList.add(player.getUniqueId());
	}

	public static boolean getPvpPlayer(final Player player) {
		return pvpEnabledList.contains(player.getUniqueId());
	}

	public static void removePvpPlayer(final Player player) {
		pvpEnabledList.remove(player.getUniqueId());
	}

	public static void clear() {
		pvpEnabledList.clear();
	}
}
