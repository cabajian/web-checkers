package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The unit test suite for the {@link Row} component
 *
 * @author Kevin Adrian
 */

@Tag("Model-tier")
public class RowTest {

    private static final int ROW_INDEX_0 = 0;
    private static final int ROW_INDEX_1 = 1;
    private static final ArrayList<Space> SPACES_EVEN = new ArrayList<Space>() {{
        add(new Space(0, Space.SpaceColor.LIGHT));
        add(new Space(1, Space.SpaceColor.DARK));
        add(new Space(2, Space.SpaceColor.LIGHT));
        add(new Space(3, Space.SpaceColor.DARK));
        add(new Space(4, Space.SpaceColor.LIGHT));
        add(new Space(5, Space.SpaceColor.DARK));
        add(new Space(6, Space.SpaceColor.LIGHT));
        add(new Space(7, Space.SpaceColor.DARK));
    }};
    private static final ArrayList<Space> SPACES_ODD = new ArrayList<Space>() {{
        add(new Space(0, Space.SpaceColor.DARK));
        add(new Space(1, Space.SpaceColor.LIGHT));
        add(new Space(2, Space.SpaceColor.DARK));
        add(new Space(3, Space.SpaceColor.LIGHT));
        add(new Space(4, Space.SpaceColor.DARK));
        add(new Space(5, Space.SpaceColor.LIGHT));
        add(new Space(6, Space.SpaceColor.DARK));
        add(new Space(7, Space.SpaceColor.LIGHT));
    }};

    /**
     * Test that rows have the correct index number and same number of spaces.
     */
    @Test
    public void argTest(){
        final Row CuT_1 = new Row(ROW_INDEX_0);
        assertEquals(ROW_INDEX_0, CuT_1.getIndex());
        assertEquals(SPACES_EVEN.size(), CuT_1.getSpaces().size());
        final Row CuT_2 = new Row(ROW_INDEX_1);
        assertEquals(ROW_INDEX_1, CuT_2.getIndex());
        assertEquals(SPACES_ODD.size(), CuT_2.getSpaces().size());
    }

    /**
     * Test that even and odd indexed rows create their pattern of spaces correctly.
     */
    @Test
    public void spacesTest(){
        final Row CuT_1 = new Row(ROW_INDEX_0);
        final ArrayList<Space> CuT_spaces_1 = CuT_1.getSpaces();
        for (Space s: CuT_spaces_1){
            if(s.getCellIdx() % 2 == 0){
                assertEquals(Space.SpaceColor.LIGHT, s.getSpaceColor());
            }
            else{
                assertEquals(Space.SpaceColor.DARK, s.getSpaceColor());
            }
        }
        final Row CuT_2 = new Row(ROW_INDEX_1);
        final ArrayList<Space> CuT_spaces_2 = CuT_2.getSpaces();
        for (Space s: CuT_spaces_2){
            if(s.getCellIdx() % 2 == 0){
                assertEquals(Space.SpaceColor.DARK, s.getSpaceColor());
            }
            else{
                assertEquals(Space.SpaceColor.LIGHT, s.getSpaceColor());
            }
        }
    }

    /**
     * Tests that the iterator method works as intended.
     */
    @Test
    public void iterTest(){
        final Row CuT = new Row(ROW_INDEX_0);
        final Iterator<Space> CuTIterator = CuT.iterator();
        final ArrayList<Space> CuT_spaces = CuT.getSpaces();
        for(int i = 0; i <= 7; i++){
            assertEquals(SPACES_EVEN.iterator().hasNext(), CuT_spaces.iterator().hasNext());
            SPACES_EVEN.iterator().next();
            CuT_spaces.iterator().next();
        }
    }

    /**
     * Test the getIndex method
     */
    @Test
    void getIndex() {
        final Row CuT_1 = new Row(ROW_INDEX_0);
        assertEquals(ROW_INDEX_0,CuT_1.getIndex());
    }

    /**
     * Test the getSpaces method
     */
    @Test
    void getSpaces() {
        final Row CuT_1 = new Row(ROW_INDEX_0);
        CuT_1.getSpaces();
    }
}
