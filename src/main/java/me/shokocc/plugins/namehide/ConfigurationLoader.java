package me.shokocc.plugins.namehide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;

class ConfigurationLoader {

    public static Map<String, Object> ConfigValues = new HashMap<String, Object>();
    static File configFile;

    public static void CreateFile(boolean override) {
        configFile = new File(ShokoNameHide.getPlugin(ShokoNameHide.class).getDataFolder(), "config.yml");
        if (override || !configFile.exists()) {
            try {
                ShokoNameHide.getPlugin(ShokoNameHide.class).getDataFolder().mkdirs();
                configFile.createNewFile();
                InputStream def = ShokoNameHide.getPlugin(ShokoNameHide.class).getResource("config.yml");
                byte[] buffer = new byte[def.available()];
                def.read(buffer);
                FileOutputStream stream = new FileOutputStream(configFile);
                stream.write(buffer);
                stream.close();
            } catch (IOException e) { }
        }
    }

    public static void LoadConfigurationFile() {
        if (configFile == null) {
            configFile = new File(ShokoNameHide.getPlugin(ShokoNameHide.class).getDataFolder() + "/config.yml");
            if (!configFile.exists())
                CreateFile(false);

        }

        FileConfiguration config = ShokoNameHide.getPlugin(ShokoNameHide.class).getConfig();
        for(String item : config.getKeys(false)) {
            if(config.getString(item) != null)
                ConfigValues.put(item, config.get(item));
        }
    }

    public static Object GetValue(String key) {
        Object value = ConfigValues.get(key);
        if (value == null) {
            CreateFile(true);
            LoadConfigurationFile();
        }
        value = ConfigValues.get(key);
        return value;
    }
}