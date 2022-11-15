package com.stand.utility;

import de.leonhard.storage.Yaml;

public class Language {

	// This class used to load Language.yml if it doesn't exist.

	public static void init() {

		final Yaml yaml = new Yaml("Language", "plugins/PlayerModification");
		yaml.setDefault("Anti-build-message" , "&cYou cannot build anything in this world!");
		yaml.setDefault("Pvp-disable-message" , "&cThis world isn't allowed to pvp!");
		yaml.setDefault("Do-not-have-permission" , "&cYou don't have permission to access PlayerModification plugin!");

	}
}
