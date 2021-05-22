package com.webcheckers.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The unit test suite for the {@link Move} component
 *
 * @author Fayez Mehdad + Chris Abajian
 */
class MoveTest {

    private Position singlePositionStart =
            new Position(new Row(0), new Space(0, Space.SpaceColor.DARK));
    private Position singlePositionEnd =
            new Position(new Row(1), new Space(1, Space.SpaceColor.DARK));
    private Position jumpPositionStart =
            new Position(new Row(0), new Space(0, Space.SpaceColor.DARK));
    private Position jumpPositionEnd =
            new Position(new Row(2), new Space(2, Space.SpaceColor.DARK));

    /**
     * Tests the getStart method
     */
    @Test
    void getStart() {
        Move MoveTest = new Move(singlePositionStart, singlePositionEnd);
        assertEquals(singlePositionStart, MoveTest.getStart());
    }

    /**
     * Tests the getEnd method
     */
    @Test
    void getEnd() {
        Move MoveTest = new Move(singlePositionStart, singlePositionEnd);
        assertEquals(singlePositionEnd, MoveTest.getEnd());
    }

    /**
     * Tests the getType and setType methods
     */
    @Test
    void getSetType() {
        Move singleMoveTest = new Move(singlePositionStart, singlePositionEnd);
        Move jumpMoveTest = new Move(jumpPositionStart, jumpPositionEnd);
        singleMoveTest.setType();
        jumpMoveTest.setType();
        assert singleMoveTest.getType().equals(Move.MoveType.SINGLE);
        assert jumpMoveTest.getType().equals(Move.MoveType.JUMP);
    }
}