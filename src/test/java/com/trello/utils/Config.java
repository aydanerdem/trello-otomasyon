package com.trello.utils;

import java.io.*;
import java.util.Properties;

public class Config {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateProperty(String key, String newValue) {
        Properties properties = new Properties();
        File file = new File("src/main/resources/config.properties");

        try (FileInputStream fis = new FileInputStream(file)) {
            properties.load(fis);
        } catch (IOException e) {
            System.err.println("Dosya okunamadı: " + e.getMessage());
        }

        properties.setProperty(key, newValue);

        try (FileOutputStream fos = new FileOutputStream(file)) {
            properties.store(fos, "Updated " + key);
        } catch (IOException e) {
            System.err.println("Dosya yazılamadı: " + e.getMessage());
        }
    }

    public static String getApiKey() {
        return properties.getProperty("api.key");
    }

    public static String getBoardId() {
        return properties.getProperty("boardIdKey");
    }

    public static String getListId() {
        return properties.getProperty("listIdKey");
    }

    public static String getCardId1() {
        return properties.getProperty("cardId1Key");
    }

    public static String getCardId2() {
        return properties.getProperty("cardId2Key");
    }

    public static String getToken() {
        return properties.getProperty("token");
    }
}