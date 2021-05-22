package com.webcheckers.model;

/**
 * A representation of position which holds row and cell indices.
 *
 * @author Kevin Adrian
 */
public class Position {

    private final int row;
    private final int cell;
    private Space space;

    /**
     * Constructs the position data type
     *
     * @param row The Row object from which the index will be pulled from
     * @param space The Space object from which the index will be pulled from
     */
    public Position(Row row, Space space){
        this.row = row.getIndex();
        this.cell = space.getCellIdx();
        this.space = space;
    }

    /**
     * @return Returns the row index
     */
    public int getRow(){return this.row;}

    /**
     * @return Returns the cell index
     */
    public int getCell(){return this.cell;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row &&
                cell == position.cell;
    }
}
