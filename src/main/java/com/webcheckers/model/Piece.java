package com.webcheckers.model;

/**
 * Represents a single checkers piece
 *
 * @author Kevin Adrian
 */

public class Piece {

    public enum PieceType{
        SINGLE, KING;
    }

    public enum PieceColor{
        RED, WHITE;
    }

    private PieceType type;
    private final PieceColor COLOR;

    /**
     * Constructor for the Piece class
     *
     * @param color The color of the piece
     */
    public Piece(final PieceColor color){
        this.type = PieceType.SINGLE;
        this.COLOR = color;
    }

    /**
     * Get the piece's type (Single or King)
     *
     * @return The piece's type
     */
    public PieceType getType(){ return this.type; }

    /**
     * Get the piece's color (Red or White)
     *
     * @return The piece's color
     */
    public PieceColor getColor() { return COLOR; }

    /**
     * Sets the piece as a king
     */
    public void setKing(){ this.type = PieceType.KING; }

    /**
     * Get the pieces type
     * @return Whether or not the piece is a king
     */
    public boolean isKing() {return this.type == PieceType.KING;}



}
