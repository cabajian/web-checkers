package com.webcheckers.appl;

import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The unit test suite for the {@link CheckersGame} component
 *
 * @author Fayez Mehdad
 */

@Tag("UI-tier")
class CheckersGameTest {

    private Player TestPlayer;
    private Player TestOpponent;
    private CheckersGame TestSingleMoveGame;


    @BeforeEach
    void setup() {
        TestPlayer = new Player("Chris");
        TestOpponent = new Player("Becky");

        // Classic Gameboard at start of game ++++++++++++++++++++++++++++++++++++++++++++++++
        TestSingleMoveGame = new CheckersGame(TestPlayer, TestOpponent);
    }

    @Test
    void validateAIMove() {
        AI ai = new AI();

        // Classic Gameboard at start of game ++++++++++++++++++++++++++++++++++++++++++++++++
        TestSingleMoveGame = new CheckersGame(TestPlayer, ai);

        ai.setGame(TestSingleMoveGame);

        TestSingleMoveGame.validateMove(validateMoveTestHelper(5, 0, 4, 1));
        assertEquals(TestSingleMoveGame.submitTurn().getType(), Message.MsgType.info);

        try {
            TestSingleMoveGame.validateAIMove(TestSingleMoveGame.getRandomMove());
        } catch (NullPointerException e) {

        }

    }

    /**
     * Test getWhitePlayer method
     */
    @Test
    void getWhitePlayer() {
        assertEquals(TestOpponent, TestSingleMoveGame.getWhitePlayer());
    }

    /**
     * Test getRedPlayer method
     */
    @Test
    void getRedPlayer() {
        assertEquals(TestPlayer, TestSingleMoveGame.getRedPlayer());
    }

    /**
     * Test getWhoseTurn method
     */
    @Test
    void getWhoseTurn() {
        assertEquals(Player.PlayerColor.RED, TestSingleMoveGame.getWhoseTurn());
    }

    /**
     * Test getBoard method
     */
    @Test
    void getBoard() {
        assertNotNull(TestSingleMoveGame.getBoard(TestPlayer));
        assertNotNull(TestSingleMoveGame.getBoard(TestOpponent));
    }

    /**
     * Test hasResigned method
     */
    @Test
    void hasResigned() {
        assertFalse(TestSingleMoveGame.hasResigned());
        TestSingleMoveGame.setResign(Player.PlayerColor.RED);
        assertTrue(TestSingleMoveGame.hasResigned());
    }

    /**
     * Test hasWhiteWon method
     */
    @Test
    void hasWhiteWon() {
        TestSingleMoveGame.setResign(Player.PlayerColor.RED);
        assertTrue(TestSingleMoveGame.hasWhiteWon());
    }

    /**
     * Test hasRedWon method
     */
    @Test
    void hasRedWon() {
        TestSingleMoveGame.setResign(Player.PlayerColor.WHITE);
        assertTrue(TestSingleMoveGame.hasRedWon());
    }

    /**
     * test SetWinner method
     */
    @Test
    void setWinner() {
        TestSingleMoveGame.setWinner(Player.PlayerColor.WHITE);
        TestSingleMoveGame.setWinner(Player.PlayerColor.RED);
    }

    /**
     * Test the next turn method
     */
    @Test
    void nextTurn() {
        assertEquals(Player.PlayerColor.WHITE, TestSingleMoveGame.nextTurn());
        assertEquals(Player.PlayerColor.RED, TestSingleMoveGame.nextTurn());
    }

    /**
     * Tests validateMove method
     */
    @Test
    void validateMove() {
        assertEquals(TestSingleMoveGame.validateMove(validateMoveTestHelper(5, 0, 4, 1)).getType(), Message.MsgType.info);
        assertEquals(TestSingleMoveGame.validateMove(validateMoveTestHelper(4, 1, 3, 2)).getType(), Message.MsgType.error);

        setup();

        assertEquals(TestSingleMoveGame.validateMove(validateMoveTestHelper(5, 0, 3, 2)).getType(), Message.MsgType.error);
    }

    private Move validateMoveTestHelper(int row0, int col0, int row1, int col1) {
        Position start = new Position(new Row(row0), new Space(col0, Space.SpaceColor.DARK));
        Position end = new Position(new Row(row1), new Space(col1, Space.SpaceColor.DARK));
        Move move = new Move(start, end);
        move.setType();
        return move;
    }

    /**
     * Tests backupMove method
     */
    @Test
    void backupMove() {
        assertEquals(TestSingleMoveGame.backupMove().getType(), Message.MsgType.error);

        TestSingleMoveGame.validateMove(validateMoveTestHelper(5, 0, 4, 1));

        assertEquals(TestSingleMoveGame.backupMove().getType(), Message.MsgType.info);
        assertEquals(TestSingleMoveGame.getWhoseTurn(), Player.PlayerColor.RED);
    }

    /**
     * Tests submitTurn method
     */
    @Test
    void submitTurn() {
        TestSingleMoveGame.validateMove(validateMoveTestHelper(5, 0, 5, 0));
        assertEquals(TestSingleMoveGame.submitTurn().getType(), Message.MsgType.error);
        TestSingleMoveGame.validateMove(validateMoveTestHelper(5, 0, 3, 2));
        assertEquals(TestSingleMoveGame.submitTurn().getType(), Message.MsgType.error);

        TestSingleMoveGame.validateMove(validateMoveTestHelper(5, 0, 4, 1));
        assertEquals(TestSingleMoveGame.submitTurn().getType(), Message.MsgType.info);
    }
}