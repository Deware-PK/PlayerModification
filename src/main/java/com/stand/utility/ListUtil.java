package com.stand.utility;


import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;

public class ListUtil {

	private static final ArrayList<UUID> pvpEnabledList = new ArrayList<>();
	private static final ArrayList<UUID> antiBuildWorldList = new ArrayList<>();
	public static final Set<World> blackListedWorlds = new HashSet<>();
	private static final List<UUID> unableDropExp = new ArrayList<>();

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

	public static void addDisableDropEntity(final Entity entity) {
		unableDropExp.add(entity.getUniqueId());
	}

	public static boolean getDisableDropEntity(final Entity entity) {
		return unableDropExp.contains(entity.getUniqueId());
	}

	public static void removeDisableDropEntity(final Entity entity) {
		unableDropExp.remove(entity.getUniqueId());
	}

}