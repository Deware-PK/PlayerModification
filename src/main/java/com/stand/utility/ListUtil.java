package com.stand.utility;


import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ListUtil {

	private static final ArrayList<UUID> pvpEnabledList = new ArrayList<>();
	private static final ArrayList<UUID> antiBuildWorldList = new ArrayList<>();
	public static final Set<World> blackListedWorlds = new HashSet<>();
	private static final ArrayList<UUID> gravityEnabledList = new ArrayList<>();

	public static void addPvpPlayer(final Player player) {
		pvpEnabledList.add(player.getUniqueId());
	}

	public static boolean getPvpPlayer(final Player player) {
		return pvpEnabledList.contains(player.getUniqueId());
	}

	public static void removePvpPlayer(final Player player) {
		pvpEnabledList.remove(player.getUniqueId());
	}

	// -------------------------------------------------------------

	public static void addAntiBuildingList(final Player player) {
		antiBuildWorldList.add(player.getUniqueId());
	}

	public static boolean getAntiBuildPlayer(final Player player) {
		return antiBuildWorldList.contains(player.getUniqueId());
	}

	public static void removeAntiBuildPlayer(final Player player) {
		antiBuildWorldList.remove(player.getUniqueId());
	}

	// -------------------------------------------------------------
	// Future idea

	public static void addGravityList(final Player player) {
		gravityEnabledList.add(player.getUniqueId());
	}

	public static boolean getGravityPlayer(final Player player) {
		return gravityEnabledList.contains(player.getUniqueId());
	}

	public static void removeGravityPlayer(final Player player) {
		gravityEnabledList.remove(player.getUniqueId());
	}
}