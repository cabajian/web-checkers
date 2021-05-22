package com.webcheckers.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The unit test suite for the {@link Space} component
 *
 * @author Kevin Adrian
 */

@Tag("Model-tier")
public class SpaceTest {
    private static final int CELL_INDEX = 0;
    private static final Space.SpaceColor COLOR_D = Space.SpaceColor.DARK;
    private static final Space.SpaceColor COLOR_L = Space.SpaceColor.LIGHT;
    private static final Piece PIECE = new Piece(Piece.PieceColor.RED);

    /***
     * Tests that the space class has the correct index number and color.
     */
    @Test
    public void argTest(){
        final Space CuT = new Space(CELL_INDEX, COLOR_D);
        assertEquals(CELL_INDEX, CuT.getCellIdx());
        assertEquals(COLOR_D, CuT.getSpaceColor());
    }

    /**
     * Tests that a new space should not have a piece in it and that the .setPiece method works.
     */
    @Test
    public void pieceTest(){
        final Space CuT = new Space(CELL_INDEX, COLOR_D);
        assertNull(CuT.getPiece());
        CuT.setPiece(PIECE);
        assertEquals(PIECE, CuT.getPiece());
        CuT.clearPiece();
        assertNull(CuT.getPiece());
    }

    /**
     * Tests that the .isValidSpace method works.
     */
    @Test
    public void isValidTest(){
        final Space CuT = new Space(CELL_INDEX, COLOR_L);
        assertFalse(CuT.isValid());
        final Space CuT2 = new Space(CELL_INDEX, COLOR_D);
        assertTrue(CuT2.isValid());
    }

    /**
     * color test
     */
    @Test
    void colorTest() {
        final Space CuT = new Space(CELL_INDEX, COLOR_L);
        assertEquals(CuT.getSpaceColor(), COLOR_L);
    }

    /**
     * row test
     */
    @Test
    void rowTest() {
        final Space CuT = new Space(CELL_INDEX, COLOR_L);
        CuT.setRow(0);
        assert CuT.getRow() == 0;
    }

    /**
     * equals test
     */
    @Test
    void equalsTest() {
        final Space CuT = new Space(CELL_INDEX, COLOR_L);
        final Space CuT2 = new Space(CELL_INDEX, COLOR_L);
        assertEquals(CuT, CuT2);
    }
}
