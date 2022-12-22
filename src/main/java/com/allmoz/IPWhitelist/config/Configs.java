package com.allmoz.IPWhitelist.config;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.allmoz.IPWhitelist.IPWhitelist;
import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import lombok.Getter;
import lombok.Setter;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class Configs {

    @Getter private static Config config;
    @Getter private static Set<String> whitelist = new HashSet<>();
    private static Path configFile;
    private static Path whitelistFile;

    /**
     * Loads the config files.
     * @param ipWhitelist
     */
    public static void loadConfigs(IPWhitelist ipWhitelist) {
        configFile = Path.of(ipWhitelist.getDataDirectory() + "/config.toml");
        whitelistFile = Path.of(ipWhitelist.getDataDirectory() + "/whitelist.json");

        //Create data directory
        if(!ipWhitelist.getDataDirectory().toFile().exists()) {
            ipWhitelist.getDataDirectory().toFile().mkdir();
        }

        //Load the config.toml to memory
        if(!configFile.toFile().exists()) {
            try (InputStream in = IPWhitelist.class.getResourceAsStream("/config.toml")) {
                Files.copy(in, configFile);
            } catch (Exception e) {
                ipWhitelist.getLogger().error("Error loading config.toml");
                e.printStackTrace();
            }
        }
        config = new Toml().read(configFile.toFile()).to(Config.class);

        //Load whitelist players to memory (if any)
        if(whitelistFile.toFile().exists()) {
            try (InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(whitelistFile.toFile()), "UTF8")) {
                Type whitelistSetType = new TypeToken<HashSet<String>>(){}.getType();
                whitelist = new Gson().fromJson(inputStreamReader, whitelistSetType);
            } catch (Exception e) {
                ipWhitelist.getLogger().error("Error loading whitelist.json");
                e.printStackTrace();
            }
        }
    }

    /**
     * Save the config
     * @param ipWhitelist
     */
    public static void saveConfig(IPWhitelist ipWhitelist) {
        try {
            new TomlWriter().write(config, configFile.toFile());
        } catch (Exception e) {
            ipWhitelist.getLogger().error("Error writing config.toml");
            e.printStackTrace();
        }
    }

    /**
     * Save the whitelist file
     * @param ipWhitelist
     */
    public static void saveWhitelist(IPWhitelist ipWhitelist) {
        try {
            FileWriter fileWriter = new FileWriter(whitelistFile.toFile());
            new Gson().toJson(whitelist, fileWriter);
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            ipWhitelist.getLogger().error("Error writing whitelist.json");
            e.printStackTrace();
        }
    }

    /**
     * The main config
     */
    public class Config {

        @Getter @Setter
        private boolean enabled;
        @Getter
        private String message;

        @Override
        public String toString() {
            return "Panel{" +
                "enabled='" + enabled + '\'' +
                ", message='" + message + '\'' +
            '}';
        }
    }
}