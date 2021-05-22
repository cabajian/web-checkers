package com.webcheckers.model;


import com.webcheckers.appl.CheckersGame;

/**
 * Represents an individual Player.
 *
 * @author Chris Abajian + Becky Reich (additions)
 */
public class Player {

    private final String name;
    private PlayerColor color;
    private final PlayerType type;

    private CheckersGame game;
    private boolean inGame;

    public enum PlayerColor{
        RED, WHITE
    };

    public enum PlayerType{
        USER, AI
    };

    /**
     * Constructor to create a Player.
     *
     * @param playerName the player name
     */
    public Player(final String playerName) {
        this.name = playerName;
        this.inGame = false;
        this.game = null;
        this.type = PlayerType.USER;
    }

    /**
     * Constructor for an AI Player
     */
    public Player(){
        this.name = "Computer";
        this.inGame = false;
        this.game = null;
        this.type = PlayerType.AI;
    }


    /**
     * Retrieve the player's name
     *
     * @return the player name String
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the player's color for the current game
     *
     * @param color
     */
    public void setColor(PlayerColor color){
        this.color = color;
    }

    public PlayerColor getColor(){
        return this.color;
    }

    /**
     * Returns whether or not this player is in a game
     *
     * @return
     */
    public boolean isInGame(){
        return this.inGame;
    }


    /**
     * Starts a game by initializing correct components
     **/
    public void setInGame(){
        this.inGame = true;
    }

    public void setGame(CheckersGame game) {
        this.game = game;
    }

    public CheckersGame getGame() {
        return this.game;
    }

    /**
     * Ends a game by assigning correct values accordingly
     */
    public void endGame(){
        this.inGame = false;
    }

    public PlayerType getType() { return type;}

    /**
     * Check is two players are equal. Two players are equal
     * if their names are the same.
     *
     * @param obj the Player object to compare to
     * @return whether or not the Players are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Player)) return false;
        final Player that = (Player) obj;
        return this.name.equals(that.getName());
    }
}
