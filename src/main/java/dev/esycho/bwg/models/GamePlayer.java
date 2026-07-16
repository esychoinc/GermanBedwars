package dev.esycho.bwg.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.UUID;

public class GamePlayer {

    private final UUID uuid;
    private String team; // "Red" or "Blue"
    private boolean bedBroken;
    private int kills;

    public GamePlayer(Player player) {
        this.uuid = player.getUniqueId();
        this.team = "None";
        this.bedBroken = false;
        this.kills = 0;
    }

    // Player er Bukkit entity return korar method
    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    // --- Team & Bed Logic ---
    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public boolean isBedBroken() {
        return bedBroken;
    }

    public void setBedBroken(boolean bedBroken) {
        this.bedBroken = bedBroken;
    }

    // --- Stats Logic ---
    public int getKills() {
        return kills;
    }

    public void addKill() {
        this.kills++;
    }
}