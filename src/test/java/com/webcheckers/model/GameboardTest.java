package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Chris Abajian
 */
@Tag("Model-tier")
public class GameboardTest {

    private Gameboard redGameboard;
    private Gameboard whiteGameboard;

    /**
     * Sets up both player boards before each test
     */
    @BeforeEach
    void setup() {
        redGameboard = new Gameboard(Player.PlayerColor.RED);
        whiteGameboard = new Gameboard(Player.PlayerColor.WHITE);
    }

    /**
     * Tests constructor
     */
    @Test
    void argTest() {
        assertEquals(redGameboard.getSpacesWithPieces().get(0).getPiece().getColor(),Piece.PieceColor.RED);
        assertEquals(whiteGameboard.getSpacesWithPieces().get(0).getPiece().getColor(),Piece.PieceColor.WHITE);
    }

    /**
     * Tests constructors for test boards
     */
    @Test
    void argTestForTests() {
        Gameboard redGameboardTests = new Gameboard(Player.PlayerColor.RED, 0);
        Gameboard whiteGameboardTests = new Gameboard(Player.PlayerColor.WHITE, 0);
        assertNotNull(redGameboardTests);
        assertNotNull(whiteGameboardTests);
        redGameboardTests = new Gameboard(Player.PlayerColor.RED, 1);
        whiteGameboardTests = new Gameboard(Player.PlayerColor.WHITE, 1);
        assertNotNull(redGameboardTests);
        assertNotNull(whiteGameboardTests);
        redGameboardTests = new Gameboard(Player.PlayerColor.RED, 2);
        whiteGameboardTests = new Gameboard(Player.PlayerColor.WHITE, 2);
        assertNotNull(redGameboardTests);
        assertNotNull(whiteGameboardTests);
    }

    /**
     * Test getPositionFromSpace method
     */
    @Test
    void getPositionFromSpace() {
        Position pos = redGameboard.getPositionFromSpace(0, 0);
        assert pos.getRow() == 0 && pos.getCell() == 0;
    }

    /**
     * Tests getSpacesWithPieces
     */
    @Test
    void getSpacesWithPieces() {
        assert redGameboard.getSpacesWithPieces().size() == 12;
    }

    /**
     * Tests getJumpedOverSpace
     */
    @Test
    void getJumpedOverSpace() {

        Space leftForward = redGameboard.getJumpedOverSpace(7, 6, 5, 4);
        Space leftBack = whiteGameboard.getJumpedOverSpace(5, 4, 7, 6);
        Space rightForward = whiteGameboard.getJumpedOverSpace(2, 1, 0, 3);
        Space rightBack = redGameboard.getJumpedOverSpace(0, 3, 2, 1);
        Space nullSpace = redGameboard.getJumpedOverSpace(0, 0, 0, 0);

        assert leftForward.getRow() == 6;
        assert leftForward.getCellIdx() == 5;
        assertEquals(leftForward.getPiece().getColor(), Piece.PieceColor.RED);
        assertEquals(leftBack.getPiece().getColor(), Piece.PieceColor.WHITE);
        assertEquals(rightForward.getPiece().getColor(), Piece.PieceColor.RED);
        assertEquals(rightBack.getPiece().getColor(), Piece.PieceColor.WHITE);
        assertNull(nullSpace);
    }

    /**
     * Tests movePiece
     */
    @Test
    void movePiece() {
        Position start = redGameboard.getPositionFromSpace(5, 0);
        Position end = redGameboard.getPositionFromSpace(4, 1);
        redGameboard.movePiece(start, end);

        assertNull(redGameboard.getPieceFromSpace(5, 0));
        assertNotNull(redGameboard.getPieceFromSpace(4, 1));
        assertEquals(redGameboard.getPieceFromSpace(4, 1).getColor(), Piece.PieceColor.RED);
        assert redGameboard.getSpacesWithPieces().size() == 12;
    }

    /**
     * Tests losePiece
     */
    @Test
    void losePiece() {
        Space space = redGameboard.getSpacesWithPieces().get(0);
        redGameboard.losePiece(space);

        assertNull(space.getPiece());
        assert redGameboard.getSpacesWithPieces().size() == 11;
    }

    /**
     * Tests isValidMove
    */
    @Test
    void isValidMove() {
        Space SpaceTesterFrom = new Space(1, Space.SpaceColor.DARK);
        Piece PieceTest = new Piece(Piece.PieceColor.RED);
        Row RowTestFrom = new Row(3);
        SpaceTesterFrom.setPiece(PieceTest);
        Position PositionTesterFrom = new Position(RowTestFrom,SpaceTesterFrom);


        Space SpaceTesterTo = new Space(2, Space.SpaceColor.DARK);
        Row RowTestTo = new Row(2);
        Position PositionTesterTo = new Position(RowTestTo, SpaceTesterTo);

//        assertTrue(TestGameBoardRed.isValidMove(PositionTesterFrom,PositionTesterTo));

        Row RowFail = new Row (0);
        Position PositionTesterFail = new Position(RowFail, SpaceTesterTo);
//        assertFalse(TestGameBoardWhite.isValidMove(PositionTesterFrom,PositionTesterFail));
    }

    /**
     * Tests losePieceTranslate
     */
    @Test
    void losePieceTranslate() {
        assertNotNull(whiteGameboard.getPieceFromSpace(7, 6));
        assertEquals(whiteGameboard.getPieceFromSpace(7, 6).getColor(), Piece.PieceColor.WHITE);

        // get space at (0,1) on red board
        Space space = redGameboard.getRows().get(0).getSpaces().get(1);
        // lose space at (7,6) on white board
        whiteGameboard.losePieceTranslate(space);

        assert whiteGameboard.getSpacesWithPieces().size() == 11;
        assertNull(whiteGameboard.getPieceFromSpace(7, 6));
    }

    /**
     * Tests convertDimension
     */
    @Test
    void convertDimension() {
        assert redGameboard.convertDimension(7) == 0;
        assert whiteGameboard.convertDimension(5) == 2;
    }

    /**
     * Tests translateBoard
     */
    @Test
    void translateBoard() {
        Position start = redGameboard.getPositionFromSpace(5, 0);
        Position end = redGameboard.getPositionFromSpace(4, 1);
        whiteGameboard.translateBoard(start, end);

        assertNull(whiteGameboard.getPieceFromSpace(2, 7));
        assertNotNull(whiteGameboard.getPieceFromSpace(3, 6));
        assertEquals(whiteGameboard.getPieceFromSpace(3, 6).getColor(), Piece.PieceColor.RED);
    }

    /**
     * Tests isSpaceMovable
     */
    @Test
    void isSpaceMovable() {
        assertFalse(redGameboard.isSpaceMoveable(0, 1));
        assertTrue(redGameboard.isSpaceMoveable(4, 1));
    }

    /**
     * Tests canKing
     */
    @Test
    void canKing() {
        Position pos = redGameboard.getPositionFromSpace(5, 0);
        assertFalse(redGameboard.canKing(pos));
        pos = whiteGameboard.getPositionFromSpace(0, 1);
        assertTrue(whiteGameboard.canKing(pos));
    }

    /**
     * Tests iterator
     */
    @Test
    void iterator() {
        assertNotNull(redGameboard.iterator());
    }

}
