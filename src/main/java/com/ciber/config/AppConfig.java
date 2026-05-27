package com.ciber.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Properties;

public class AppConfig {

    private static final Properties PROPERTIES = new Properties();
    private static final Properties ENV_FILE = new Properties();

    static {
        try (InputStream input = AppConfig.class.getResourceAsStream("/application.properties")) {
            if (input != null) {
                PROPERTIES.load(input);
            }
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }

        loadEnvFile();
    }

    private AppConfig() {
    }

    public static String get(String key, String defaultValue) {
        String systemValue = System.getProperty(key);
        if (hasText(systemValue)) {
            return systemValue;
        }

        String envKey = toEnvKey(key);
        String envValue = System.getenv(envKey);
        if (hasText(envValue)) {
            return envValue;
        }

        String envFileValue = ENV_FILE.getProperty(envKey);
        if (hasText(envFileValue)) {
            return envFileValue;
        }

        return PROPERTIES.getProperty(key, defaultValue);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = get(key, String.valueOf(defaultValue));
        return Boolean.parseBoolean(value);
    }

    private static void loadEnvFile() {
        Path envPath = Path.of(".env");
        if (!Files.exists(envPath)) {
            return;
        }

        try {
            for (String rawLine : Files.readAllLines(envPath)) {
                String line = rawLine.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                int separator = line.indexOf('=');
                if (separator <= 0) {
                    continue;
                }

                String key = line.substring(0, separator).trim();
                String value = line.substring(separator + 1).trim();
                ENV_FILE.setProperty(key, stripQuotes(value));
            }
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private static String stripQuotes(String value) {
        if (value.length() >= 2) {
            char first = value.charAt(0);
            char last = value.charAt(value.length() - 1);
            if ((first == '"' && last == '"') || (first == '\'' && last == '\'')) {
                return value.substring(1, value.length() - 1);
            }
        }

        return value;
    }

    private static String toEnvKey(String key) {
        return key.toUpperCase(Locale.ROOT).replace('.', '_');
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}
