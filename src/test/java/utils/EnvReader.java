package utils;

import io.github.cdimascio.dotenv.Dotenv;

public final class EnvReader {

    private static final Dotenv DOTENV =
            Dotenv.configure()
                    .ignoreIfMissing()
                    .load();

    private EnvReader() {
    }

    public static String get(String key) {

        String value = DOTENV.get(key);

        if (value == null || value.isBlank()) {
            throw new RuntimeException(
                    "Environment variable is missing: " + key
            );
        }

        return value.trim();
    }
}
