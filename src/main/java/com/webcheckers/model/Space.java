package com.webcheckers.model;

import java.util.Objects;

/**
 * Represents a square space in a row
 *
 * @author Kevin Adrian and Becky Reich
 */

public class Space {

    public enum SpaceColor{
        LIGHT, DARK;
    }

    private final int cellIdx;
    private int row;

    private Piece piece;
    private final SpaceColor COLOR;

    /**
     * Constructor for the Space class
     *
     * @param column An int (0-7) that states the location of the space in a row
     * @param color The color of the space
     */
    public Space(final int column, final SpaceColor color){
        this.cellIdx = column;
        this.COLOR = color;
        this.piece = null;
    }

    /**
     * Get the piece that is on the space
     *
     * @return The piece that is on the space
     */
    public Piece getPiece() { return piece; }

    /**
     * Return the spaces color
     *
     * @return SpaceColor
     */
    public SpaceColor getSpaceColor(){
        return this.COLOR;
    }

    /**
     * Get the row index number of the space
     *
     * @return The cell index number
     */
    public int getRow() {
        return row;
    }

    /**
     * Get the cellIdx index number of the space
     *
     * @return The cell index number
     */
    public int getCellIdx(){ return this.cellIdx; }

    /**
     * Set the row index number of the space
     *
     * @return The cell index number
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Sets a piece on the space
     *
     * @param piece The piece that will be on the space
     */
    public void setPiece(Piece piece){ this.piece = piece; }

    public void clearPiece(){ this.piece = null; }

    public boolean isValid() {
        return this.COLOR.equals(SpaceColor.DARK) && this.piece == null;
    }

    /**
     * Determines if two spaces are the same or not
     *
     * @param o A space
     * @return Whether or not theyre the same
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Space)) return false;
        Space space = (Space) o;
        return cellIdx == space.cellIdx &&
                row == space.row &&
                Objects.equals(piece, space.piece) &&
                COLOR == space.COLOR;
    }

}


