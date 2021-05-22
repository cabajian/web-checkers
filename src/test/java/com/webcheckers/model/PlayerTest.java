package com.webcheckers.model;

import com.webcheckers.appl.CheckersGame;
import com.webcheckers.model.Player.PlayerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The unit test suite for the {@link Player} component
 *
 * @author Andy Zhu + Chris Abajian
 */

@Tag("Model-tier")
public class PlayerTest {

    private static final String TEST_NAME = "Player";
    private static final Player OPPONENT = new Player("Opponent");
    private static final PlayerColor TEST_COLOR = PlayerColor.RED;
    private Player CuT;

    @BeforeEach
    void setup() {
        CuT = new Player(TEST_NAME);
    }

    /**
     * Test that Player constructor works without failure
     */
    @Test
    public void argTest() {
        assertEquals(TEST_NAME, CuT.getName());
        assertFalse(CuT.isInGame(), "Player should not be in game");
        assertNull(CuT.getGame());
    }

    /**
     * Test color change methods and initial state of a Player's color
     */
    @Test
    public void colorTest() {
        assertNull(CuT.getColor(), "Player has no color " + CuT.getColor());
        CuT.setColor(TEST_COLOR);
        assertEquals(TEST_COLOR, CuT.getColor());
    }

    /**
     * Test how a player is determined to be equal to another player
     */
    @Test
    public void equalsTest() {
        assertNotEquals(OPPONENT, CuT);
        final Player CuT2 = new Player(TEST_NAME);
        assertEquals(CuT2, CuT);
    }

    /**
     * Test the getName method
     */
    @Test
    void getName() {
        assertEquals(TEST_NAME,CuT.getName());
    }

    /**
     * Test the setColor method
     */
    @Test
    void setColor() {
        CuT.setColor(PlayerColor.RED);
        assertEquals(PlayerColor.RED, CuT.getColor());
    }

    /**
     * Test ingame, endGame and setInGame method
     */
    @Test
    void inGame() {
        assertFalse(CuT.isInGame());
        CuT.setInGame();
        assertTrue(CuT.isInGame());
        CuT.endGame();
        assertFalse(CuT.isInGame());
    }

    /**
     * Test that the method can set and get game
     */
    @Test
    void TestSetGetGame() {
        Player Becky = new Player("Becky");
        Player Chris = new Player("Chris");
        CheckersGame TestGame = new CheckersGame(Becky, Chris);
        Becky.setGame(TestGame);
        Chris.setGame(TestGame);
        assertEquals(Chris.getGame(),Becky.getGame());
        assertEquals(TestGame,Becky.getGame());
        assertEquals(TestGame,Chris.getGame());
    }

    /**
     * Test the equals method
     */
    @Test
    void equals() {
        assertEquals(CuT, new Player(TEST_NAME));
    }

    @Test
    void AITest() {
        AI ai = new AI();
        assertEquals(Player.PlayerType.AI, ai.getType());
    }
}
