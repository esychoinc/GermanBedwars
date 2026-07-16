package dev.esycho.bwg.arena;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Arena {

    private final String name;
    private final List<UUID> players;
    private GameState state;

    // Arena State
    public enum GameState {
        WAITING, STARTING, PLAYING, FINISHING
    }

    public Arena(String name) {
        this.name = name;
        this.players = new ArrayList<>();
        this.state = GameState.WAITING;
    }

    // --- Player Management ---
    public void addPlayer(Player player) {
        players.add(player.getUniqueId());
    }

    public void removePlayer(Player player) {
        players.remove(player.getUniqueId());
    }

    public List<UUID> getPlayers() {
        return players;
    }

    // --- State Management ---
    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    // --- Logic for Teams (Spawn/Bed) ---
    // Eikhane config theke location load korar method add korte paren
    public Location getTeamSpawn(String teamName) {
        // Code to fetch location from config using this.name
        return null; 
    }
}