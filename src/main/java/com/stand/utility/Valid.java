package com.stand.utility;

import lombok.SneakyThrows;

public class Valid {

	public void checkNotNull(final Object toCheck) {
		if (toCheck == null)
			throw new NullPointerException();
	}

	public static void checkNotNull(final Object toCheck, final String falseMessage) {
		if (toCheck == null)
			throw new NullPointerException(falseMessage);
	}

	@SneakyThrows
	public static void checkBoolean(final boolean expression) {
		if (!expression)
			throw new NullPointerException();
	}

	@SneakyThrows
	public static void checkBoolean(final boolean expression, final String falseMessage, final Object... replacements) {
		if (!expression)
			throw new NullPointerException(String.format(falseMessage, replacements));
	}
}
