package com.webcheckers.appl;

import com.webcheckers.model.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * PlayerLobby class to represent all players currently
 * signed in. Handles signin/signout actions.
 *
 * @author Chris Abajian
 */
public class PlayerLobby {

    private static List<Player> lobby = new ArrayList<>();

    /**
     * Signs a player in if the username is not already taken.
     *
     * @param playerName the username String to sign in
     * @return the Player created
     */
    public synchronized static Player signin(final String playerName) {
        // name is trimmed to remove leading/trailing spaces
        String trimmedName = playerName.trim();
        // validate name
        if(!validName(trimmedName)) return null;
        // create new player
        Player player = new Player(trimmedName);
        // return if signin was successful or not
        return addPlayer(player) ? player : null;
    }

    /**
     * Returns a copy of the list of players in lobby for displaying on the webpage
     *
     * @return new ArrayList containing lobby players
     */
    public static List<Player> displayNames() {
        return new ArrayList<>(lobby);
    }

    /**
     * Attempts to add player into the list of online players.
     *
     * @param player the player object that needs to be added
     * @return True if player has been added, false if username was already taken.
     */
    private synchronized static boolean addPlayer(Player player) {
        for (Player p : lobby) {
            if (player.equals(p)) {
                //player exists
                return false;
            }
        }
        //unique player name
        lobby.add(player);
        return true;
    }

    /**
     * gets a player in the playerlobby from a player's name
     *
     * @param playerName string of player's name
     * @return the player
     */
    public static Player getPlayer(String playerName) {
        for (Player p : lobby) {
            if (p.getName().equals(playerName)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Checks to see if a player's name String contains
     * any illegal characters and makes sure player's
     * name is not just all spaces
     *
     * @param playerName the player's signed name
     * @return if the player has a valid name
     */
    private static boolean validName(String playerName) {
        return !playerName.isEmpty()
                && playerName.matches("[a-zA-Z0-9 ]+");
    }

    /**
     * Signs a player out by removing them from the lobby and
     * resign current game, if in one.
     *
     * @param player the player to sign out
     */
    public static void signout(Player player) {
        lobby.remove(player);

        if (player.isInGame()) {
            player.getGame().setResign(player.getColor());
        }
    }

    /**
     * Clears every player from the lobby. Used for testing.
     */
    public static void clearAll() {
        lobby.clear();
    }

}
