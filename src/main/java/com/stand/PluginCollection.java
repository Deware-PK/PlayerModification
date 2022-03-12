package com.stand;


import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PluginCollection {

	private static final ArrayList<UUID> pvpEnabledList = new ArrayList<>();
	private static final ArrayList<String> blackListWorlds = new ArrayList<>();

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

	public static void addBlackListWorld(final String name) {
		blackListWorlds.add(name);
	}

	public static boolean containsBlackListWorld(final String name) {
		return blackListWorlds.contains(name);
	}

	public static List<String> getBlackListWorldList() {
		return blackListWorlds;
	}

	public static void clearBlackListWorlds() {
		blackListWorlds.clear();
	}

	public static void addAllBlackListWorlds(final List<String> list) {
		blackListWorlds.addAll(list);
	}
}
