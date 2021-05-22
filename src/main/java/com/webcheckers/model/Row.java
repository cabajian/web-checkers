package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents a row in the game board
 *
 * @author Kevin Adrian
 */

public class Row {

    private final int rowIndex;
    private final ArrayList<Space> spaces;

    /**
     * Constructs the row
     *
     * @param rowIndex An int that states the row's position in the game board(0-7)
     */
    public Row(final int rowIndex) {
        this.rowIndex = rowIndex;
        if (rowIndex % 2 == 0) {
            this.spaces = new ArrayList<>();
            for (int i = 0; i <= 7; ++i) {
                if (i % 2 == 0) {
                    Space space = new Space(i, Space.SpaceColor.LIGHT);
                    spaces.add(space);
                } else {
                    Space space = new Space(i, Space.SpaceColor.DARK);
                    spaces.add(space);
                }
            }
        } else {
            this.spaces = new ArrayList<>();
            for (int i = 0; i <= 7; ++i) {
                if (i % 2 == 0) {
                    Space space = new Space(i, Space.SpaceColor.DARK);
                    spaces.add(space);
                } else {
                    Space space = new Space(i, Space.SpaceColor.LIGHT);
                    spaces.add(space);
                }
            }
        }
    }

    /**
     * Gets the index of the row
     *
     * @return An int that represents the row's position on the game board
     */
    public int getIndex(){ return this.rowIndex; };

    /**
     * Returns an iterator of the spaces array list
     *
     * @return An iterator od the spaces in the row
     */
    public Iterator<Space> iterator(){ return this.spaces.iterator(); };


    public ArrayList<Space> getSpaces(){ return this.spaces; };
}
