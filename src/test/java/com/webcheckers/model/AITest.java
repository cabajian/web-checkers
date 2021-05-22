package com.webcheckers.model;

import com.webcheckers.appl.CheckersGame;
import com.webcheckers.model.Player.PlayerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The unit test suite for the {@link AI} component
 *
 * @author Kevin Adrian
 */

@Tag("Model-tier")
public class AITest {

    private static final Player USER = new Player("Human");
    private static final String AI_NAME = "Computer";
    private static final Player.PlayerColor AI_COLOR = PlayerColor.WHITE;
    private AI CuT;

    @BeforeEach
    void setup() { CuT = new AI();}

    /**
     * Tests that the AI constructor works
     */
    @Test
    public void consTest(){
        assertEquals(AI_NAME, CuT.getName());
        assertFalse(CuT.isInGame(), "AI should not be in a game.");
        assertNull(CuT.getGame());
        assertNull(CuT.getMove());
    }

    /**
     * Tests that the AI color field works
     */
    @Test
    public void colorTest() {
        assertNull(CuT.getColor(), "Player has no color " + CuT.getColor());
        CuT.setColor(AI_COLOR);
        assertEquals(AI_COLOR, CuT.getColor());
    }

    /**
     * Tests that two newly created AI's are equal
     */
    @Test
    public void equalsTest() {
        assertNotEquals(USER, CuT);
        final Player CuT2 = new AI();
        assertEquals(CuT2, CuT);
    }

    /**
     * Tests that the AI name is Computer AI
     */
    @Test
    void getName() {
        assertEquals(AI_NAME,CuT.getName());
    }

    /**
     * Tests the color field of AI
     */
    @Test
    void setColor() {
        CuT.setColor(PlayerColor.WHITE);
        assertEquals(PlayerColor.WHITE, CuT.getColor());
    }

    /**
     * Tests the inGame field of AI
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
     * Tests an AI and a user in a mock game
     */
    @Test
    void TestSetGetGame() {
        Player Kevin = new Player("Kevin");
        AI Computer = new AI();
        CheckersGame TestGame = new CheckersGame(Kevin, Computer);
        Kevin.setGame(TestGame);
        Computer.setGame(TestGame);
        assertEquals(Kevin.getGame(),Computer.getGame());
        assertEquals(TestGame,Kevin.getGame());
        assertEquals(TestGame,Computer.getGame());
    }

    /**
     * Test the equals method
     */
    @Test
    void equals() {
        assertEquals(CuT, new AI());
    }

    /**
     * Tests that the makeMove method is working
     */
    @Test
    void moveTest(){
        Player Kevin = new Player("Kevin");
        AI Computer = new AI();
        CheckersGame TestGame = new CheckersGame(Kevin, Computer);
        Kevin.setGame(TestGame);
        Computer.setGame(TestGame);
        assertNull(Computer.getMove());
        Message msg = Computer.makeMove();
        assertNotNull(Computer.getMove());
        assertEquals(msg.getType(), Message.MsgType.info);
    }

}
