
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

        // 1. Check OS environment variables (GitHub Actions / CI)
        String value = System.getenv(key.toUpperCase());

        // 2. Fall back to the local .env file
        if (value == null || value.isBlank()) {
            value = DOTENV.get(key);
        }

        // 3. Fail clearly if neither source provides a value
        if (value == null || value.isBlank()) {
            throw new RuntimeException(
                    "Environment variable is missing: " + key
            );
        }

        return value.trim();
    }
}
