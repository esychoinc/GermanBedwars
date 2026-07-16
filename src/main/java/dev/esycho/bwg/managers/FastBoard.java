package dev.esycho.bwg.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;

public class FastBoard {

    private final Player player;
    private final Scoreboard scoreboard;
    private final Objective objective;
    private final List<Team> teams = new ArrayList<>();

    public FastBoard(Player player) {
        this.player = player;
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective("sb", "dummy");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        
        player.setScoreboard(scoreboard);
    }

    public void updateTitle(String title) {
        this.objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', title));
    }

    // Scoreboard er line update korar method
    public void updateLines(List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            String line = ChatColor.translateAlternateColorCodes('&', lines.get(i));
            Team team = getOrCreateTeam(i, line);
            team.setPrefix(line);
            objective.getScore(getEntryName(i)).setScore(lines.size() - i);
        }
    }

    private Team getOrCreateTeam(int index, String line) {
        String teamName = "sb" + index;
        Team team = scoreboard.getTeam(teamName);
        if (team == null) {
            team = scoreboard.registerNewTeam(teamName);
            team.addEntry(getEntryName(index));
        }
        return team;
    }

    private String getEntryName(int index) {
        return ChatColor.values()[index].toString();
    }
}