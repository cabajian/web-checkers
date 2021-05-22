package com.webcheckers.model;

/**
 * A representation of a move which holds the starting Position and end Position.
 *
 * @author Kevin Adrian
 */
public class Move {

    public enum MoveType {
        SINGLE, JUMP
    }

    private final Position start;
    private final Position end;
    private MoveType type;

    /**
     * Constructs the move data type
     *
     * @param start The starting Position
     * @param end The end Position
     */
    public Move(Position start, Position end){
        this.start = start;
        this.end = end;
    }

    /**
     * @return Returns the starting Position
     */
    public Position getStart(){
        return this.start;
    }

    /**
     * @return Returns the end Position
     */
    public Position getEnd(){
        return this.end;
    }

    /**
     * @return Returns the move type
     */
    public MoveType getType() {
        return type;
    }

    public void setType() {
        // get move deltas
        int deltaRow = Math.abs(start.getRow() - end.getRow());
        int deltaCol = Math.abs(start.getCell() - end.getCell());

        // set move type.
        this.type = (deltaRow == 1 && deltaCol == 1) ? MoveType.SINGLE : MoveType.JUMP;
    }
}
