package com.andrei1058.bedwars.cmds;

import club.mher.privategames.api.PrivateGames;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.configuration.ConfigManager;
import com.andrei1058.bedwars.cmds.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;

public class Main extends JavaPlugin implements Listener {

    private static Main plugin;
    private static ConfigManager cfg;
    //public static BedWars api;

    private static PrivateGames privateGamesAPI = null;

    private final ArrayList<IArena> privategames = new ArrayList<>();

    public void onEnable() {
        plugin = this;

        if (Bukkit.getPluginManager().getPlugin("BedWars1058") == null) {
            this.getLogger().severe("I can't run without BedWars1058 Plugin!");
            this.setEnabled(false);
            return;
        }

        try {
            Class.forName("com.andrei1058.bedwars.api.BedWars");
        } catch (Exception ex){
            getLogger().severe("Your BedWars1058 version is outdated. Please download the latest version!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        //api = Bukkit.getServicesManager().getRegistration(BedWars.class).getProvider();

        /* Setup configuration */
        cfg = new ConfigManager(this, "config", "plugins/BedWars1058/Addons/Cmds");
        setupConfiguration();

        if (Bukkit.getPluginManager().isPluginEnabled("BedWars1058-PrivateGames")){
            Bukkit.getPluginManager().registerEvents(new PrivateGameListener(), getPlugin());
            privateGamesAPI = Bukkit.getServicesManager().getRegistration(PrivateGames.class).getProvider();
        }

        /* Register listeners */
        if (getCfg().getBoolean(ConfigPath.GAME_WIN_ENABLE)) {
            Bukkit.getPluginManager().registerEvents(new WinListener(), getPlugin());
        }
        if (getCfg().getBoolean(ConfigPath.REGULAR_KILL_FOR_KILLER_ENABLE)
                || getCfg().getBoolean(ConfigPath.REGULAR_KILL_FOR_VICTIM_NO_KILLER_ENABLE)
                || getCfg().getBoolean(ConfigPath.REGULAR_KILL_FOR_VICTIM_WITH_KILLER_ENABLE)) {
            Bukkit.getPluginManager().registerEvents(new RegularKillsListener(), getPlugin());
        }
        if (getCfg().getBoolean(ConfigPath.FINAL_KILL_FOR_KILLER_ENABLE)
                || getCfg().getBoolean(ConfigPath.FINAL_KILL_FOR_VICTIM_NO_KILLER_ENABLE)
                || getCfg().getBoolean(ConfigPath.FINAL_KILL_FOR_VICTIM_WITH_KILLER_ENABLE)) {
            Bukkit.getPluginManager().registerEvents(new FinalKillsListener(), getPlugin());
        }
        if (getCfg().getBoolean(ConfigPath.BED_DESTROY_ENABLE)) {
            Bukkit.getPluginManager().registerEvents(new BedDestroyListener(), getPlugin());
        }
    }

    private static void setupConfiguration() {
        YamlConfiguration yml = getCfg().getYml();
        yml.options().header("This is a BedWars1058 mini-game add-on by andrei1058." +
                "\nDocumentation here: https://gitlab.com/bedwars-addons/bedwars1058-rewardcmds/wikis/home");

        yml.addDefault(ConfigPath.GAME_WIN_ENABLE, true);
        yml.addDefault(ConfigPath.GAME_WIN_WINNER_CMDS_AS_PLAYER, Collections.singletonList("me I won on arena {arenaDisplay}!"));
        yml.addDefault(ConfigPath.GAME_WIN_WINNER_CMDS_AS_CONSOLE, Collections.singletonList("eco give {player} 100"));

        yml.addDefault(ConfigPath.REGULAR_KILL_FOR_KILLER_ENABLE, true);
        yml.addDefault(ConfigPath.REGULAR_KILL_KILLER_AS_PLAYER, Collections.singletonList("me {victim}aws killed by me on arena {displayArena}!"));
        yml.addDefault(ConfigPath.REGULAR_KILL_KILLER_AS_CONSOLE, Collections.singletonList("eco give {killer} 30"));
        yml.addDefault(ConfigPath.REGULAR_KILL_FOR_VICTIM_WITH_KILLER_ENABLE, true);
        yml.addDefault(ConfigPath.REGULAR_KILL_VICTIM_WITH_KILLER_AS_PLAYER, Collections.singletonList("me I was killed by {killer} on arena {arenaDisplay}"));
        yml.addDefault(ConfigPath.REGULAR_KILL_VICTIM_WITH_KILLER_AS_CONSOLE, Collections.singletonList("eco give {victim} 5"));
        yml.addDefault(ConfigPath.REGULAR_KILL_FOR_VICTIM_NO_KILLER_ENABLE, true);
        yml.addDefault(ConfigPath.REGULAR_KILL_VICTIM_NO_KILLER_AS_PLAYER, Collections.singletonList("me I died like a noob!"));
        yml.addDefault(ConfigPath.REGULAR_KILL_VICTIM_NO_KILLER_AS_CONSOLE, Collections.singletonList("eco give {victim} 1"));

        yml.addDefault(ConfigPath.FINAL_KILL_FOR_KILLER_ENABLE, true);
        yml.addDefault(ConfigPath.FINAL_KILL_KILLER_AS_PLAYER, Collections.singletonList("me {victim}aws killed by me on arena {displayArena}!"));
        yml.addDefault(ConfigPath.FINAL_KILL_KILLER_AS_CONSOLE, Collections.singletonList("eco give {killer} 30"));
        yml.addDefault(ConfigPath.FINAL_KILL_FOR_VICTIM_WITH_KILLER_ENABLE, true);
        yml.addDefault(ConfigPath.FINAL_KILL_VICTIM_WITH_KILLER_AS_PLAYER, Collections.singletonList("me I was killed by {killer} on arena {arenaDisplay}"));
        yml.addDefault(ConfigPath.FINAL_KILL_VICTIM_WITH_KILLER_AS_CONSOLE, Collections.singletonList("eco give {victim} 5"));
        yml.addDefault(ConfigPath.FINAL_KILL_FOR_VICTIM_NO_KILLER_ENABLE, true);
        yml.addDefault(ConfigPath.FINAL_KILL_VICTIM_NO_KILLER_AS_PLAYER, Collections.singletonList("me I died like a noob!"));
        yml.addDefault(ConfigPath.FINAL_KILL_VICTIM_NO_KILLER_AS_CONSOLE, Collections.singletonList("eco give {victim} 1"));

        yml.addDefault(ConfigPath.BED_DESTROY_ENABLE, true);
        yml.addDefault(ConfigPath.BED_DESTROY_FOR_DESTROYER_AS_PLAYER, Collections.singletonList("me Bed destroyed! Hahah"));
        yml.addDefault(ConfigPath.BED_DESTROY_FOR_DESTROYER_CONSOLE, Collections.singletonList("eco give {victim} 5"));
        yml.addDefault(ConfigPath.BED_DESTROY_FOR_DESTROYERS_AS_PLAYER, Collections.singletonList("me GG {victimTeam}"));
        yml.addDefault(ConfigPath.BED_DESTROY_FOR_DESTROYERS_CONSOLE, Collections.singletonList("eco give {victim} 2"));

        yml.options().copyDefaults(true);
        getCfg().save();
    }

    public static Main getPlugin() {
        return plugin;
    }

    public static ConfigManager getCfg() {
        return cfg;
    }

    public void addPrivateGame(IArena arena){this.privategames.add(arena);}

    public ArrayList<IArena> getPrivateGames(){return this.privategames;}

    public void removePrivateGame(IArena hey){this.privategames.remove(hey);}

    public PrivateGames getPrivateGamesAPI() {return privateGamesAPI;}
}
