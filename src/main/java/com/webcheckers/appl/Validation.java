package com.webcheckers.appl;

import com.webcheckers.model.*;

import java.util.ArrayList;

/**
 * This application handles all the validation checks needed for ensuring piece movement is correct.
 * The RULES class is the middle ground translation between POSITIONS -> SPACES for GAMEBOARD access
 *
 * For validation, every method handles the parameters of ROWS and COLUMN indexes
 *
 * @author Becky Reich, Chris Abajian, and Kevin Adrian
 */
public class Validation {

    /**
     * Helper method for generating a message when validating a single or jump move.
     *
     * @param isDiagonal boolean indicating if move is along diagonal
     * @param isValidLanding boolean indicating if landing is valid
     * @return Message of validation result
     */
    private static Message validateMoveHelper(boolean isDiagonal, boolean isValidLanding) {
        // check if the move is along a diagonal and lands on a valid space
        if (isDiagonal) {
            // diagonal jump
            if (isValidLanding) {
                // valid landing space
                return new Message("Valid move", Message.MsgType.info);
            } else {
                return new Message("Invalid move: invalid landing space", Message.MsgType.error);
            }
        } else {
            // jump move-- invalid.
            return new Message("Invalid move: piece must move diagonally", Message.MsgType.error);
        }
    }

    /**
     * Check to see if the move is valid in accordance with checkers rules
     * THIS SPACE is the space that the piece will move to
     *
     * FOR UI IMPLEMENTATION
     *
     * @return Message result of single move validation
     */
    static Message validateSingle(Gameboard gameboard, Move move) {

        int oldRow = move.getStart().getRow();
        int oldColumn = move.getStart().getCell();
        int newRow = move.getEnd().getRow();
        int newColumn = move.getEnd().getCell();

        Piece piece = gameboard.getPieceFromSpace(oldRow, oldColumn);

        boolean isForward, isDiagonal, isValidLanding;
        isForward = (newRow < oldRow);
        isDiagonal = (1 == Math.abs(newColumn - oldColumn)
                        && 1 == Math.abs(newRow - oldRow));
        isValidLanding = (gameboard.isSpaceMoveable(newRow, newColumn));

        // check if a normal piece moves backwards
        if (!piece.isKing()) {
            // normal, non-king piece
            if (!isForward) {
                // backwards move-- invalid.
                return new Message("Invalid move: non-king piece cannot move backwards", Message.MsgType.error);
            }
        }

        return validateMoveHelper(isDiagonal, isValidLanding);
    }

    /**
     * Check to see if the move is valid in accordance with checkers rules
     * THIS SPACE is the space that the piece will move to
     *
     * FOR UI IMPLEMENTATION
     *
     * @return Message result of jump move validation
     */
    static Message validateJump(Gameboard gameboard, Move move, Move lastMove) {

        int oldRow = move.getStart().getRow();
        int oldColumn = move.getStart().getCell();
        int newRow = move.getEnd().getRow();
        int newColumn = move.getEnd().getCell();

        Piece piece = gameboard.getPieceFromSpace(oldRow, oldColumn);

        boolean isForward, isDiagonal, isValidLanding;
        isForward = (newRow < oldRow);
        isDiagonal = (2 == Math.abs(newColumn - oldColumn)
                        && 2 == Math.abs(newRow - oldRow));
        isValidLanding = (gameboard.isSpaceMoveable(newRow, newColumn));

        if (isDiagonal) {
            Piece jumpedOverPiece = gameboard.getJumpedOverSpace(oldRow, oldColumn, newRow, newColumn).getPiece();
            boolean doesJumpOverPiece = (jumpedOverPiece != null) && !(jumpedOverPiece.getColor().equals(piece.getColor()));

            // check that move jumps over piece
            if (!doesJumpOverPiece) {
                return new Message("Invalid move: a jump move must capture an opponent's piece", Message.MsgType.error);
            }
        } else {
            // filter out invalid jumps (moves more than 2 spaces)
            return new Message("Invalid move: cannot jump over more than one space", Message.MsgType.error);

        }

        // check if a normal piece moves backwards
        if (!piece.isKing()) {
            // normal, non-king piece
            if (!isForward) {
                // backwards move-- invalid.
                return new Message("Invalid move: non-king piece cannot move backwards", Message.MsgType.error);
            }
        }

        // check if move goes back to previous move's space
        if (lastMove != null && move.getEnd().equals(lastMove.getStart())) {
            return new Message("Invalid move: cannot move back to starting position", Message.MsgType.error);
        }

        return validateMoveHelper(isDiagonal, isValidLanding);
    }

    /**
     * Helper method to return the Position of the suggested move if valid
     *
     * @param gameboard the gameboard reference
     * @param space the space to check moves from
     * @param changeRow the change in the row
     * @param changeColumn the change in the column
     * @param moveType the move type
     * @return the Position if valid at the resulting space
     */
    private static Position getMovesHelper(Gameboard gameboard, Space space, int changeRow, int changeColumn, Move.MoveType moveType){

        // convert to new row, column
        int newRow = space.getRow() + changeRow;
        int newColumn = space.getCellIdx() + changeColumn;

        if ((newRow < 0) || (newRow > 7) || (newColumn < 0) || (newColumn > 7))
                return null;

        // validate based off move type
        switch(moveType) {
            case SINGLE:
                // check if new space is movable
                if (gameboard.isSpaceMoveable(newRow, newColumn))
                    return gameboard.getPositionFromSpace(newRow, newColumn);
                break;
            case JUMP:
                // get jumped over space
                Space jumpedOver = gameboard.getJumpedOverSpace(space.getRow(), space.getCellIdx(), newRow, newColumn);
                // check valid jump conditions
                if (jumpedOver.getPiece() != null
                        && !jumpedOver.getPiece().getColor().equals(space.getPiece().getColor())
                        && gameboard.isSpaceMoveable(newRow, newColumn)) {
                    // valid jump, return landing position
                    return gameboard.getPositionFromSpace(newRow, newColumn);
                }
                break;
        }

        // return null if position is invalid
        return null;
    }

    /**
     * Gets all player moves on a gameboard based on move type
     *
     * @param gameboard the gameboard reference
     * @param moveType the move type
     * @return the collection of position
     */
    static ArrayList<Position> getMovesByType(Gameboard gameboard, Move.MoveType moveType) {

        // initialize collection of positions to return
        ArrayList<Position> moves = new ArrayList<>();

        // get spaces with pieces on them
        ArrayList<Space> spacesWithPieces = gameboard.getSpacesWithPieces();

        // iterate through each space
        for (Space space : spacesWithPieces) {

            // get row, column of space
            int row = space.getRow();
            int column = space.getCellIdx();

            // get move directions based on move type
            int changeRow, changeColumn;
            switch(moveType) {
                case SINGLE:
                    changeRow = 1;
                    changeColumn = 1;
                    break;
                case JUMP:
                    changeRow = 2;
                    changeColumn = 2;
                    break;
                default:
                    // error case-- should never be reached
                    changeRow = 0;
                    changeColumn = 0;
            }

            // ensure the row, column is within board constraints
            boolean withinConstraints = (row - changeRow >= 0)
                                        || (row + changeRow <= 7)
                                        && (column - changeColumn >= 0)
                                        || (column + changeColumn <= 7);

            // get piece from space
            // if space is out of board constraints, continue loop
            Piece piece;
            if (withinConstraints) piece = gameboard.getPieceFromSpace(row, column);
            else continue;

            // check moves from the space based on piece type
            Position pos;
            switch (piece.getType()) {
                case SINGLE: // check forward moves

                    // forward left
                    pos = getMovesHelper(gameboard, space, -changeRow, -changeColumn, moveType);
                    if (pos != null) moves.add(pos);
                    // forward right
                    pos = getMovesHelper(gameboard, space, -changeRow, changeColumn, moveType);
                    if (pos != null) moves.add(pos);

                    break;
                case KING: // check all direction moves

                    // forward left
                    pos = getMovesHelper(gameboard, space, -changeRow, -changeColumn, moveType);
                    if (pos != null) moves.add(pos);
                    // forward right
                    pos = getMovesHelper(gameboard, space, -changeRow, changeColumn, moveType);
                    if (pos != null) moves.add(pos);
                    // backward left
                    pos = getMovesHelper(gameboard, space, changeRow, -changeColumn, moveType);
                    if (pos != null) moves.add(pos);
                    // backward right
                    pos = getMovesHelper(gameboard, space, changeRow, changeColumn, moveType);
                    if (pos != null) moves.add(pos);

                    break;
            }
        }

        // return collection of available positions
        return moves;
    }

    static ArrayList<Position> getJumpsFromSpace(Gameboard gameboard, Space space) {
        int row = space.getRow();
        int column = space.getCellIdx();

        // initialize collection of positions to return
        ArrayList<Position> moves = new ArrayList<>();

        // ensure the row, column is within board constraints
        boolean withinConstraints = (row - 1 >= 0)    && (row <= 7)
                                 || (row + 1 <= 7)    && (row >= 0)
                                 && (column - 1 >= 0) && (column <= 7)
                                 || (column + 1 <= 7) && (column >= 0);

        // get piece from space
        // if space is out of board constraints, continue loop
        Piece piece;
        if (withinConstraints) piece = gameboard.getPieceFromSpace(row, column);
        else return moves;

        // check moves from the space based on piece type
        Position pos;
        switch(piece.getType()) {
            case SINGLE: // check forward moves

                // forward left
                pos = getMovesHelper(gameboard, space, -2, -2, Move.MoveType.JUMP);
                if (pos != null) moves.add(pos);
                // forward right
                pos = getMovesHelper(gameboard, space, -2, 2, Move.MoveType.JUMP);
                if (pos != null) moves.add(pos);

                break;
            case KING: // check all direction moves

                // forward left
                pos = getMovesHelper(gameboard, space, -2, -2, Move.MoveType.JUMP);
                if (pos != null) moves.add(pos);
                // forward right
                pos = getMovesHelper(gameboard, space, -2, 2, Move.MoveType.JUMP);
                if (pos != null) moves.add(pos);// forward left
                pos = getMovesHelper(gameboard, space, 2, -2, Move.MoveType.JUMP);
                if (pos != null) moves.add(pos);
                // forward right
                pos = getMovesHelper(gameboard, space, 2, 2, Move.MoveType.JUMP);
                if (pos != null) moves.add(pos);

                break;
        }

        return moves;
    }

    static ArrayList<Position> getSingleFromSpace(Gameboard gameboard, Space space) {
        int row = space.getRow();
        int column = space.getCellIdx();

        // initialize collection of positions to return
        ArrayList<Position> moves = new ArrayList<>();

        // ensure the row, column is within board constraints
        boolean withinConstraints = (row - 1 >= 0)    && (row <= 7)
                                 || (row + 1 <= 7)    && (row >= 0)
                                 && (column - 1 >= 0) && (column <= 7)
                                 || (column + 1 <= 7) && (column >= 0);

        // get piece from space
        // if space is out of board constraints, continue loop
        Piece piece;
        if (withinConstraints) piece = gameboard.getPieceFromSpace(row, column);
        else return moves;

        Position pos;
        switch(piece.getType()) {
            case SINGLE: // check forward moves

                // forward left
                pos = getMovesHelper(gameboard, space, -1, -1, Move.MoveType.SINGLE);
                if (pos != null) moves.add(pos);
                // forward right
                pos = getMovesHelper(gameboard, space, -1, 1, Move.MoveType.SINGLE);
                if (pos != null) moves.add(pos);

                break;
            case KING: // check all direction moves

                // forward left
                pos = getMovesHelper(gameboard, space, -1, -1, Move.MoveType.SINGLE);
                if (pos != null) moves.add(pos);
                // forward right
                pos = getMovesHelper(gameboard, space, -1, 1, Move.MoveType.SINGLE);
                if (pos != null) moves.add(pos);// forward left
                pos = getMovesHelper(gameboard, space, 1, -1, Move.MoveType.SINGLE);
                if (pos != null) moves.add(pos);
                // forward right
                pos = getMovesHelper(gameboard, space, 1, 1, Move.MoveType.SINGLE);
                if (pos != null) moves.add(pos);

                break;
        }
        return moves;
    }

    /**
     * Returns if there are any available moves for a gameboard
     *
     * @param gameboard the gameboard reference
     * @return if there are moves available on the gameboard
     */
    static boolean canMove(Gameboard gameboard) {
        return getMovesByType(gameboard, Move.MoveType.JUMP).size() > 0
                || getMovesByType(gameboard, Move.MoveType.SINGLE).size() > 0;
    }
}



