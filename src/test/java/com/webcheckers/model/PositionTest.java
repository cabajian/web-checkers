package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The unit test suite for the {@link Position} component
 *
 * @author Fayez Mehdad
 */

@Tag("Model-tier")

class PositionTest {

    private static final int CELL_INDEX = 0;
    private static final Space.SpaceColor COLOR_D = Space.SpaceColor.DARK;
    private static final Space.SpaceColor COLOR_L = Space.SpaceColor.LIGHT;
    private static final Piece PIECE = new Piece(Piece.PieceColor.RED);

    private static final int ROW_INDEX_0 = 0;
    private static final int ROW_INDEX_1 = 1;

    Space spacetester1  = new Space(CELL_INDEX, COLOR_D);
    Space spacetester2 = new Space(CELL_INDEX, COLOR_L);

    Row rowtester1 = new Row(ROW_INDEX_0);
    Row rowtester2 = new Row(ROW_INDEX_1);

    /*
    Tests out the getRow class.
     */
    @Test
    void getRow() {
        Position positiontester1 = new Position(rowtester1,spacetester1);
        assertEquals(rowtester1.getIndex(), positiontester1.getRow());
        Position positiontester2 = new Position(rowtester2,spacetester1);
        assertEquals(rowtester2.getIndex(), positiontester2.getRow());
    }

    /*
    Tests out the getCell class
     */
    @Test
    void getCell() {
        Position positiontester1 = new Position(rowtester1,spacetester1);
        assertEquals(spacetester1.getCellIdx(), positiontester1.getCell());
        Position positiontester2 = new Position(rowtester1,spacetester2);
        assertEquals(spacetester2.getCellIdx(), positiontester2.getCell());
    }

    /**
     * Test the equals method for this class
     */
    @Test
    void equals() {
        Position positiontester1 = new Position(rowtester1,spacetester1);
        assertFalse(positiontester1.equals(null));
        assertTrue(positiontester1.equals(new Position(rowtester1,spacetester1)));
    }
}