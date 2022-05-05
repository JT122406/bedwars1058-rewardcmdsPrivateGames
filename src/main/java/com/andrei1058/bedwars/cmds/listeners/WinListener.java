package com.andrei1058.bedwars.cmds.listeners;

import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import com.andrei1058.bedwars.cmds.ConfigPath;
import com.andrei1058.bedwars.cmds.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.UUID;

import static com.andrei1058.bedwars.cmds.Main.getPlugin;

public class WinListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWin(GameEndEvent e) {
        if (!Main.getPlugin().getPrivateGames().isEmpty())
            if (Main.getPlugin().getPrivateGames().contains(e.getArena())){  //game is a private game
                Main.getPlugin().removePrivateGame(e.getArena());
                return;
            }

        Bukkit.getScheduler().runTaskLater(getPlugin(), () -> {
            for (UUID uuid : e.getWinners()) {
                for (String s : Main.getCfg().getYml().getStringList(ConfigPath.GAME_WIN_WINNER_CMDS_AS_CONSOLE)) {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
                            s.replace("{player}", Bukkit.getPlayer(uuid).getName().replace("{arena}", e.getArena().getWorldName()))
                            .replace("{arenaDisplay}", e.getArena().getDisplayName())
                            .replace("{group}", e.getArena().getGroup()));
                    }
                }
            }, 15L);
    }
}
