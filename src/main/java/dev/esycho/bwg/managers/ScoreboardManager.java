package dev.esycho.bwg.managers;

import dev.esycho.bwg.GermenBedwars;
import org.bukkit.entity.Player;
import java.util.*;

public class ScoreboardManager {
    private static final Map<UUID, FastBoard> boards = new HashMap<>();

    public static void createBoard(Player player) {
        FastBoard board = new FastBoard(player);
        board.updateTitle("&6&lGERMAN BW");
        boards.put(player.getUniqueId(), board);
    }

    public static void updateBoard(Player player) {
        FastBoard board = boards.get(player.getUniqueId());
        if (board != null) {
            // Eikhane apnar scoreboard.yml er lines gulo load korben
            board.updateLines(Arrays.asList(
                "&7Map: &aArena1",
                "&cKills: &f10"
            ));
        }
    }
}