package it.omnisys.plugin;

import it.omnisys.plugin.Commands.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static it.omnisys.plugin.Utils.ColorUtils.color;

public final class SocialX extends JavaPlugin {

    public static SocialX plugin;

    private File messageConfigFile;
    private FileConfiguration messageConfig;

    public static HashMap<String, String> commandlist = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        createMessageConfig();

        String license = getConfig().getString("License");

        if(!new LicenseSystem(license, "https://socialx-licenses.000webhostapp.com/verify.php", this).register()) {
            return;
        };

            plugin = this;

            String ver = getDescription().getVersion();
            String authors = String.valueOf(getDescription().getAuthors());

            getServer().getConsoleSender().sendMessage(color("\n" + "&b" +
                            "   _____ ____  _____________    __       _  __\n" +
                            "  / ___// __ \\/ ____/  _/   |  / /      | |/ /\n" +
                            "  \\__ \\/ / / / /    / // /| | / /      |   /     &aRunning Version &8v" + ver + "\n" +
                            " ___/ / /_/ / /____/ // ___ |/ /___      /   |     &aPlugin By" + authors.replace("[GX_Regent, Sgattix]", "GX_Regent & Sgattix") + "\n" +
                            "/____/\\____/\\____/___/_/  |_/_____/   /_/|_|  \n"));


            getCommand("youtube").setExecutor(new YouTubeCMD());
            commandlist.put("youtube", "Sends the youtube channel to the sender player");

            getCommand("twitch").setExecutor(new TwitchCMD());
            commandlist.put("twitch", "Sends the twitch channel to the sender player");

            getCommand("website").setExecutor(new WebsiteCMD());
            commandlist.put("website", "Sends the website link to the sender player");

            getCommand("tiktok").setExecutor(new TiktokCMD());
            commandlist.put("tiktok", "Sends the tiktok channel to the sender player");

            getCommand("store").setExecutor(new StoreCMD());
            commandlist.put("store", "Sends the store link to the sender player");

            getCommand("socialx").setExecutor(new SocialXCMD());
            commandlist.put("socialx", "Main Command");

            getCommand("discord").setExecutor(new DiscordCMD());
            commandlist.put("discord", "Sends the discord link to the sender player");
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic

        String ver = getDescription().getVersion();
        String authors = String.valueOf(getDescription().getAuthors());
        getServer().getConsoleSender().sendMessage(color(
                "   _____ ____  _____________    __       _  __\n" +
                "  / ___// __ \\/ ____/  _/   |  / /      | |/ /\n" +
                "  \\__ \\/ / / / /    / // /| | / /      |   /     &cDisabled &8v" + ver + "\n" +
                " ___/ / /_/ / /____/ // ___ |/ /___      /   |     &aPlugin By" + authors.replace("[GX_Regent, Sgattix]", "GX_Regent & Sgattix") + "\n" +
                "/____/\\____/\\____/___/_/  |_/_____/   /_/|_|  \n"));
    }


    public FileConfiguration getMessageConfig() {
        return this.messageConfig;
    }

    private void createMessageConfig() {
        messageConfigFile = new File(getDataFolder(), "messages.yml");
        if (!messageConfigFile.exists()) {
            messageConfigFile.getParentFile().mkdirs();
            saveResource("messages.yml", false);
        }

        messageConfig = new YamlConfiguration();
        try {
            messageConfig.load(messageConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
