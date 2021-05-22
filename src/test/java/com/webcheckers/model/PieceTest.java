package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The unit test suite for the {@link Piece} component
 *
 * @author Fayez Mehdad + Chris Abajian
 */

@Tag("Model-tier")
public class PieceTest {

    Piece testPieceRed = new Piece(Piece.PieceColor.RED);
    Piece testPieceWhite = new Piece(Piece.PieceColor.WHITE);
    Piece testPieceForKing = new Piece(Piece.PieceColor.RED);

    /**
     * Tests that the gettype method works as intended, and
     * subsequently setKing.
     */
    @Test
    void getType() {
        assertEquals(Piece.PieceType.SINGLE, testPieceRed.getType());
        assertEquals(Piece.PieceType.SINGLE, testPieceWhite.getType());
        testPieceForKing.setKing();
        assertEquals(Piece.PieceType.KING, testPieceForKing.getType());
    }

    /**
     * Tests that the getcolor method works as intended.
     */
    @Test
    void getColor() {
        assertEquals(Piece.PieceColor.RED, testPieceRed.getColor());
        assertEquals(Piece.PieceColor.WHITE, testPieceWhite.getColor());
    }

    /**
     * Test the isKing method
     */
    @Test
    void isKing() {
        testPieceRed.setKing();
        assertTrue(testPieceRed.isKing());
        testPieceWhite.setKing();
        assertTrue(testPieceWhite.isKing());
    }
}
