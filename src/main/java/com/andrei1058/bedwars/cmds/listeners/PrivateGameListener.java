package com.andrei1058.bedwars.cmds.listeners;

import club.mher.privategames.api.PrivateGames;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.andrei1058.bedwars.cmds.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PrivateGameListener implements Listener {

    @EventHandler
    public void onCreate(GameStateChangeEvent event){
        if (event.getNewState()  == GameState.playing){  //game is playing
            if (Bukkit.getServicesManager().getRegistration(PrivateGames.class).getProvider().getPrivateGameUtil().isPrivateGame(event.getArena().getArenaName())) {
                Main.getPlugin().addPrivateGame(event.getArena());
            }
        }
    }

}
