package com.webcheckers.appl;

import com.webcheckers.model.*;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ValidationTest {

    private Gameboard TestSingleMoveBoard;
    private Gameboard TestJumpMoveBoard;
    private Gameboard TestKingMoveBoard;

    @BeforeEach
    void setup() {
        // Classic Gameboard at start of game ++++++++++++++++++++++++++++++++++++++++++++++++
        TestSingleMoveBoard = new Gameboard(Player.PlayerColor.RED);

        // GameBoard with jump move setup ++++++++++++++++++++++++++++++++++++++++++++++++++++
        TestJumpMoveBoard = new Gameboard(Player.PlayerColor.RED);

        TestJumpMoveBoard.movePiece(     new Position(TestJumpMoveBoard.getRows().get(5), new Space(2, Space.SpaceColor.DARK)),
                new Position(TestJumpMoveBoard.getRows().get(4), new Space(3, Space.SpaceColor.DARK)));
        TestJumpMoveBoard.translateBoard(new Position(TestJumpMoveBoard.getRows().get(5), new Space(2, Space.SpaceColor.DARK)),
                new Position(TestJumpMoveBoard.getRows().get(4), new Space(3, Space.SpaceColor.DARK)));

        // GameBoard with KING piece ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        TestKingMoveBoard = new Gameboard(Player.PlayerColor.RED);
        // 1 Turn
        TestKingMoveBoard.movePiece(     new Position(TestKingMoveBoard.getRows().get(5), new Space(6, Space.SpaceColor.DARK)),
                new Position(TestKingMoveBoard.getRows().get(4), new Space(7, Space.SpaceColor.DARK)));
        TestKingMoveBoard.translateBoard(new Position(TestKingMoveBoard.getRows().get(5), new Space(0, Space.SpaceColor.DARK)),
                new Position(TestKingMoveBoard.getRows().get(4), new Space(1, Space.SpaceColor.DARK)));
        // 2 Turns
        TestKingMoveBoard.movePiece(     new Position(TestKingMoveBoard.getRows().get(5), new Space(0, Space.SpaceColor.DARK)),
                new Position(TestKingMoveBoard.getRows().get(4), new Space(1, Space.SpaceColor.DARK)));
        TestKingMoveBoard.translateBoard(new Position(TestKingMoveBoard.getRows().get(6), new Space(1, Space.SpaceColor.DARK)),
                new Position(TestKingMoveBoard.getRows().get(5), new Space(0, Space.SpaceColor.DARK)));
        // 3 Turns
        TestKingMoveBoard.movePiece(     new Position(TestKingMoveBoard.getRows().get(6), new Space(1, Space.SpaceColor.DARK)),
                new Position(TestKingMoveBoard.getRows().get(5), new Space(0, Space.SpaceColor.DARK)));
        TestKingMoveBoard.translateBoard(new Position(TestKingMoveBoard.getRows().get(7), new Space(0, Space.SpaceColor.DARK)),
                new Position(TestKingMoveBoard.getRows().get(6), new Space(1, Space.SpaceColor.DARK)));
        // 4 Turns
        TestKingMoveBoard.movePiece(     new Position(TestKingMoveBoard.getRows().get(7), new Space(0, Space.SpaceColor.DARK)),
                new Position(TestKingMoveBoard.getRows().get(6), new Space(1, Space.SpaceColor.DARK)));
        TestKingMoveBoard.translateBoard(new Position(TestKingMoveBoard.getRows().get(5), new Space(2, Space.SpaceColor.DARK)),
                new Position(TestKingMoveBoard.getRows().get(4), new Space(3, Space.SpaceColor.DARK)));
        // 5 Turns
        TestKingMoveBoard.movePiece(     new Position(TestKingMoveBoard.getRows().get(4), new Space(7, Space.SpaceColor.DARK)),
                new Position(TestKingMoveBoard.getRows().get(2), new Space(5, Space.SpaceColor.DARK)));
        TestKingMoveBoard.movePiece(     new Position(TestKingMoveBoard.getRows().get(2), new Space(5, Space.SpaceColor.DARK)),
                new Position(TestKingMoveBoard.getRows().get(0), new Space(7, Space.SpaceColor.DARK)));
        TestKingMoveBoard.translateBoard(new Position(TestKingMoveBoard.getRows().get(7), new Space(2, Space.SpaceColor.DARK)),
                new Position(TestKingMoveBoard.getRows().get(6), new Space(1, Space.SpaceColor.DARK)));

        TestKingMoveBoard.getPieceFromSpace(0,7).setKing();
    }


    @Test
    void validateSingle() {

        Move TestValidSingleMove = new Move(   TestSingleMoveBoard.getPositionFromSpace(5,2),
                TestSingleMoveBoard.getPositionFromSpace(4,3));
        TestValidSingleMove.setType();
        Move TestInvalidSingleMove = new Move( TestSingleMoveBoard.getPositionFromSpace(5,2),
                TestSingleMoveBoard.getPositionFromSpace(4, 2));
        TestInvalidSingleMove.setType();

        Message CuT1 = Validation.validateSingle(TestSingleMoveBoard, TestValidSingleMove);
        Message CuT2 = new Message("Valid move", Message.MsgType.info);
        assertEquals(CuT1, CuT2);

        CuT1 = Validation.validateSingle(TestSingleMoveBoard, TestInvalidSingleMove);
        CuT2 = new Message("Invalid move: piece must move diagonally", Message.MsgType.error);
        assertEquals(CuT1, CuT2);

    }

    @Test
    void validateJump() {

        Message CuT1 = Validation.validateJump(TestJumpMoveBoard, new Move (new Position(TestSingleMoveBoard.getRows().get(4), new Space(3, Space.SpaceColor.DARK)),
                new Position(TestSingleMoveBoard.getRows().get(2), new Space(5, Space.SpaceColor.DARK))), null);

        Message CuT2 = new Message("Valid move", Message.MsgType.info);
        assertEquals(CuT1, CuT2);
    }

    @Test
    void getMovesByType() {
        ArrayList<Position> moves = Validation.getMovesByType(TestSingleMoveBoard, Move.MoveType.SINGLE);
        assertNotEquals(moves, 0);

        moves = Validation.getMovesByType(TestSingleMoveBoard, Move.MoveType.SINGLE);
        assertNotEquals(moves.size(), 0);

        moves = Validation.getMovesByType(TestSingleMoveBoard, Move.MoveType.JUMP);
        assertEquals(moves.size(), 0);

        moves = Validation.getMovesByType(TestJumpMoveBoard, Move.MoveType.SINGLE);
        assertNotEquals(moves.size(), 0);

        moves = Validation.getMovesByType(TestJumpMoveBoard, Move.MoveType.JUMP);
        assertNotEquals(moves.size(), 0);

        moves = Validation.getMovesByType(TestKingMoveBoard, Move.MoveType.JUMP);
        assertEquals(moves.size(), 1);

        moves = Validation.getMovesByType(TestKingMoveBoard, Move.MoveType.SINGLE);
        assertNotEquals(moves.size(), 0);
    }

    @Test
    void getSingleFromSpace() {
        ArrayList<Row> spacesWithPieces;
        ArrayList<Position> moves;
        Space spaceToCheck;

        // VALID Space[5,2]
        spacesWithPieces = TestSingleMoveBoard.getRows();
        spaceToCheck = spacesWithPieces.get(5).getSpaces().get(2);
        moves = Validation.getSingleFromSpace(TestSingleMoveBoard, spaceToCheck);
        assertTrue( moves.size() > 0);

        // VALID King Space
        TestKingMoveBoard.movePiece(     new Position(TestKingMoveBoard.getRows().get(0), new Space(7, Space.SpaceColor.DARK)),
                new Position(TestKingMoveBoard.getRows().get(2), new Space(5, Space.SpaceColor.DARK)));
        TestKingMoveBoard.movePiece(     new Position(TestKingMoveBoard.getRows().get(2), new Space(5, Space.SpaceColor.DARK)),
                new Position(TestKingMoveBoard.getRows().get(4), new Space(3, Space.SpaceColor.DARK)));

        spacesWithPieces = TestKingMoveBoard.getRows();
        spaceToCheck = spacesWithPieces.get(4).getSpaces().get(3);
        moves = Validation.getSingleFromSpace(TestKingMoveBoard, spaceToCheck);
        assertTrue( moves.size() > 0);

        // INVALID Out of Bounds
        spaceToCheck = new Space(100, Space.SpaceColor.DARK);
        spaceToCheck.setRow(100);
        moves = Validation.getSingleFromSpace(TestSingleMoveBoard, spaceToCheck);
        assertTrue( moves.size() == 0);

    }



    @Test
    void getJumpsFromSpace() {
        ArrayList<Row> spacesWithPieces;
        ArrayList<Position> moves;
        Space spaceToCheck;

        // VALID Single Space[4,3]
        spacesWithPieces = TestJumpMoveBoard.getRows();
        spaceToCheck = spacesWithPieces.get(4).getSpaces().get(3);
        moves = Validation.getJumpsFromSpace(TestJumpMoveBoard, spaceToCheck);
        assertTrue( moves.size() > 0);

        // VALID King Space [0,7]
        spacesWithPieces = TestKingMoveBoard.getRows();
        spaceToCheck = spacesWithPieces.get(0).getSpaces().get(7);
        moves = Validation.getJumpsFromSpace(TestKingMoveBoard, spaceToCheck);
        assertTrue( moves.size() > 0);

        // INVALID Out of Bounds
        spaceToCheck = new Space(100, Space.SpaceColor.DARK);
        spaceToCheck.setRow(100);
        moves = Validation.getJumpsFromSpace(TestKingMoveBoard, spaceToCheck);
        assertTrue( moves.size() == 0);

    }

    @Test
    void canMove() {
        assertTrue(Validation.canMove(TestSingleMoveBoard));
        assertTrue(Validation.canMove(TestJumpMoveBoard));
    }

}