package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents the actual game board
 *
 * @author Kevin Adrian and Becky Reich
 */

public class Gameboard {

    // Main Gameboard Object
    private final ArrayList<Row> rows;

    // The location of Playable Pieces of this board's player's color
    private final ArrayList<Space> spacesWithPieces;

    /**
     * Constructs the Gameboard
     * <p>
     * This method creates the double iterator Gameboard view by creating rows filled with spaces,
     * adding spacesWithPieces to said spaces, and establishing the movable spacesWithPieces for this players gameboard
     */
    public Gameboard(Player.PlayerColor color) {
        // Create gameboard w/ spaces
        this.rows = new ArrayList<>();
        this.spacesWithPieces = new ArrayList<>();

        for (int r = 0; r <= 7; ++r) {
            Row row = new Row(r);
            rows.add(row);
            for (Space space : row.getSpaces()) {
                space.setRow(r);
            }
        }

        // Set up for Red/White Player

        // If the current player's color is RED
        // Top two rows: WHITE PIECES
        // Bottom two rows: RED PIECES
        if (color.equals(Player.PlayerColor.RED)) {
            // First Three Rows
            for (int row = 0; row < 3; row++) {
                for (Space space : this.rows.get(row).getSpaces()) {
                    if (space.getSpaceColor() == Space.SpaceColor.DARK
                            && space.getPiece() == null) {
                        space.setPiece(new Piece(Piece.PieceColor.WHITE));
                    }
                }
            }
            for (int row = 5; row < 8; row++) {
                for (Space space : this.rows.get(row).getSpaces()) {
                    if (space.getSpaceColor() == Space.SpaceColor.DARK
                            && space.getPiece() == null) {
                        space.setPiece(new Piece(Piece.PieceColor.RED));
                        spacesWithPieces.add(space);
                    }
                }
            }
        }
        // If the current player's color is WHITE
        // Top two rows: RED PIECES
        // Bottom two rows: WHITE PIECES
        else {
            for (int row = 0; row < 3; row++) {
                for (Space space : this.rows.get(row).getSpaces()) {
                    if (space.getSpaceColor() == Space.SpaceColor.DARK
                            && space.getPiece() == null) {
                        space.setPiece(new Piece(Piece.PieceColor.RED));
                    }
                }
            }
            for (int row = 5; row < 8; row++) {
                for (Space space : this.rows.get(row).getSpaces()) {
                    if (space.getSpaceColor() == Space.SpaceColor.DARK
                            && space.getPiece() == null) {
                        space.setPiece(new Piece(Piece.PieceColor.WHITE));
                        spacesWithPieces.add(space);
                    }
                }
            }
        }
    }

    /**
     * Constructs the Gameboard
     * <p>
     * This method creates the double iterator Gameboard view by creating rows filled with spaces,
     * adding spacesWithPieces to said spaces, and establishing the movable spacesWithPieces for this players gameboard
     */
    public Gameboard(Player.PlayerColor color, int testType) {
        // Create gameboard w/ spaces
        this.rows = new ArrayList<>();
        this.spacesWithPieces = new ArrayList<>();

        for (int r = 0; r <= 7; ++r) {
            Row row = new Row(r);
            rows.add(row);
            for (Space space : row.getSpaces()) {
                space.setRow(r);
            }
        }

        // Set up for Red/White Player

        // If the current player's color is RED
        if (color.equals(Player.PlayerColor.RED)) {

            Space s;
            switch (testType) {
                case 0:
                    /* king/capture all test */
                    // set white piece at (1,2) and (0,1)
                    s = this.rows.get(1).getSpaces().get(2);
                    if (s.isValid()) s.setPiece(new Piece(Piece.PieceColor.WHITE));
                    s = this.rows.get(0).getSpaces().get(3);
                    if (s.isValid()) s.setPiece(new Piece(Piece.PieceColor.WHITE));
                    // set red piece at (2,3)
                    s = this.rows.get(2).getSpaces().get(3);
                    if (s.isValid()) s.setPiece(new Piece(Piece.PieceColor.RED));
                    spacesWithPieces.add(s);
                    break;
                case 1:
                    /* no moves left test */
                    // set white piece at (0,3)
                    s = this.rows.get(0).getSpaces().get(3);
                    if (s.isValid()) s.setPiece(new Piece(Piece.PieceColor.WHITE));
                    // set red pieces at (2,1) (1,2) (2,3) (2,5)
                    s = this.rows.get(2).getSpaces().get(1);
                    if (s.isValid()) s.setPiece(new Piece(Piece.PieceColor.RED));
                    spacesWithPieces.add(s);
                    s = this.rows.get(1).getSpaces().get(2);
                    if (s.isValid()) s.setPiece(new Piece(Piece.PieceColor.RED));
                    spacesWithPieces.add(s);
                    s = this.rows.get(2).getSpaces().get(3);
                    if (s.isValid()) s.setPiece(new Piece(Piece.PieceColor.RED));
                    spacesWithPieces.add(s);
                    s = this.rows.get(2).getSpaces().get(5);
                    if (s.isValid()) s.setPiece(new Piece(Piece.PieceColor.RED));
                    spacesWithPieces.add(s);
                    break;
                case 2:
                    /* triple jump to king */
                    // set white piece at (1,4) (3,4) (5,2) (0,1)
                    s = this.rows.get(1).getSpaces().get(4);
                    if (s.isValid()) s.setPiece(new Piece(Piece.PieceColor.WHITE));
                    s = this.rows.get(3).getSpaces().get(4);
                    if (s.isValid()) s.setPiece(new Piece(Piece.PieceColor.WHITE));
                    s = this.rows.get(5).getSpaces().get(2);
                    if (s.isValid()) s.setPiece(new Piece(Piece.PieceColor.WHITE));
                    s = this.rows.get(0).getSpaces().get(1);
                    if (s.isValid()) s.setPiece(new Piece(Piece.PieceColor.WHITE));
                    // set red piece at (6,1)
                    s = this.rows.get(6).getSpaces().get(1);
                    if (s.isValid()) s.setPiece(new Piece(Piece.PieceColor.RED));
                    spacesWithPieces.add(s);
            }

        }
        // If the current player's color is WHITE
        else {

            Space s;
            switch (testType) {
                case 0:
                    // set white piece at (6,5) and (7,6)
                    s = this.rows.get(6).getSpaces().get(5);
                    if (s.isValid()) s.setPiece(new Piece(Piece.PieceColor.WHITE));
                    spacesWithPieces.add(s);
                    s = this.rows.get(7).getSpaces().get(4);
                    if (s.isValid()) s.setPiece(new Piece(Piece.PieceColor.WHITE));
                    spacesWithPieces.add(s);
                    // set red piece at (5,4)
                    s = this.rows.get(5).getSpaces().get(4);
                    if (s.isValid()) s.setPiece(new Piece(Piece.PieceColor.RED));
                    break;
                case 1:
                    /* no moves left test */
                    // set white piece at (7,4)
                    s = this.rows.get(7).getSpaces().get(4);
                    if (s.isValid()) s.setPiece(new Piece(Piece.PieceColor.WHITE));
                    spacesWithPieces.add(s);
                    // set red pieces at (5,6) (6,5) (5,4) (5,2)
                    s = this.rows.get(5).getSpaces().get(6);
                    if (s.isValid()) s.setPiece(new Piece(Piece.PieceColor.RED));
                    s = this.rows.get(6).getSpaces().get(5);
                    if (s.isValid()) s.setPiece(new Piece(Piece.PieceColor.RED));
                    s = this.rows.get(5).getSpaces().get(4);
                    if (s.isValid()) s.setPiece(new Piece(Piece.PieceColor.RED));
                    s = this.rows.get(5).getSpaces().get(2);
                    if (s.isValid()) s.setPiece(new Piece(Piece.PieceColor.RED));
                    break;
                case 2:
                    /* triple jump to king */
                    // set white piece at (6,3) (4,3) (2,5) (7,6)
                    s = this.rows.get(6).getSpaces().get(3);
                    if (s.isValid()) s.setPiece(new Piece(Piece.PieceColor.WHITE));
                    spacesWithPieces.add(s);
                    s = this.rows.get(4).getSpaces().get(3);
                    if (s.isValid()) s.setPiece(new Piece(Piece.PieceColor.WHITE));
                    spacesWithPieces.add(s);
                    s = this.rows.get(2).getSpaces().get(5);
                    if (s.isValid()) s.setPiece(new Piece(Piece.PieceColor.WHITE));
                    spacesWithPieces.add(s);
                    s = this.rows.get(7).getSpaces().get(6);
                    if (s.isValid()) s.setPiece(new Piece(Piece.PieceColor.WHITE));
                    spacesWithPieces.add(s);
                    // set red piece at (1,6)
                    s = this.rows.get(1).getSpaces().get(6);
                    if (s.isValid()) s.setPiece(new Piece(Piece.PieceColor.RED));

            }

        }
    }

    /**
     * Getter method to retrieve a SPACE from the gameboard easier
     *
     * @param row    Row Coordinate of a SPACE
     * @param column Column Coordinate of a SPACE
     * @return SPACE at that location
     */
    public Space getSpace(int row, int column) {
        return this.rows.get(row).getSpaces().get(column);
    }

    /**
     * Creates a new position for a space
     * @param row  space to convert
     *
     * @return position of that space
     */
    public Position getPositionFromSpace (int row, int column){
        return new Position(this.rows.get(row), this.getSpace(row, column));
    }

    /**
     * Get a copy of SpacesWithPieces
     * @return Copy of SpacesWithPieces
     */
    public ArrayList<Space> getSpacesWithPieces() {
        return new ArrayList<>(spacesWithPieces);
    }

    public Space getJumpedOverSpace(int oldRow, int oldColumn, int newRow, int newColumn) {
        // get delta row/col
        int deltaRow = (newRow - oldRow);
        int deltaCol = (newColumn - oldColumn);

        // Left Jump Forward
        if (deltaRow < 0 && deltaCol < 0){
            return this.getSpace(oldRow - 1, oldColumn - 1);
        }

        // Left Jump Backwards
        if (deltaRow > 0 && deltaCol < 0) {
            return this.getSpace(oldRow + 1, oldColumn - 1);
        }

        // Right Jump Forwards
        if (deltaRow < 0 && deltaCol > 0) {
            return this.getSpace(oldRow - 1, oldColumn + 1);
        }

        // Right Jump Backwards
        if (deltaRow > 0 && deltaCol > 0) {
            return this.getSpace(oldRow + 1, oldColumn + 1);
        }

        return null;
    }



    /**
     * This method moves a piece from one SPACE on THIS GAMEBOARD to
     * another given space on THIS GAMEBOARD
     *
     * @param oldPosition SPACE from which the piece started
     * @param newPosition SPACE to which the piece moved to
     */
    public void movePiece(Position oldPosition, Position newPosition) {
        // Save board rows and columns for fewer repeated method calls

        // Get new row space from opponents board and get that row on players board
        int newRow = newPosition.getRow();
        int oldRow = oldPosition.getRow();
        // Get new column space from opponents board and get that column on players board
        int newColumn = newPosition.getCell();
        int oldColumn = oldPosition.getCell();

        // get spaces
        Space newSpace = getSpace(newRow, newColumn);
        Space oldSpace = getSpace(oldRow, oldColumn);

        // Give the new Space a game piece
        newSpace.setPiece(oldSpace.getPiece());
        // clear the old space
        oldSpace.clearPiece();

        // replace spaces within collection
        this.spacesWithPieces.remove(oldSpace);
        this.spacesWithPieces.add(newSpace);
    }


    /**
     * This method removes a piece from a given position
     *
     * @param space A PARAMETER from the OPPONENTS Gameboard with which one of this players spacesWithPieces has been lost
     */
    public void losePiece(Space space) {
        // Get new row space from opponents board and get that row on players board
        int row = space.getRow();
        // Get new column space from opponents board and get that column on players board
        int column = space.getCellIdx();

        // get space
        Space removalSpace = getSpace(row, column);

        // Remove the piece from space
        removalSpace.clearPiece();

        // Remove space from collection
        this.spacesWithPieces.remove(removalSpace);

    }

    /**
     * By taking the input of a SPACE from the OPPONENTS GAMEBOARD, this method translates that SPACE to its
     * own board, then removes that piece from both the view and from the hand of spacesWithPieces
     *
     * @param space A PARAMETER from the OPPONENTS Gameboard with which one of this players spacesWithPieces has been lost
     */
    public void losePieceTranslate(Space space) {
        // Get new row space from opponents board and get that row on players board
        int oldRow = space.getRow();
        int newRow = convertDimension(oldRow);
        // Get new column space from opponents board and get that column on players board
        int oldColumn = space.getCellIdx();
        int newColumn = convertDimension(oldColumn);

        // get the space
        Space removalSpace = getSpace(newRow, newColumn);

        // Remove the piece from board view
        removalSpace.clearPiece();

        // Remove piece from hand
        this.spacesWithPieces.remove(removalSpace);

    }

    /**
     * Translates OPPONENTS BOARD to PLAYERS BOARD to display the correct view
     * It handles getting a piece and removing a piece, so PIECES should not be touched
     * on the current players board, only opponents.
     *
     * @param oldPosition ( from OPPONENTS BOARD )         position piece was located at
     * @param newPosition ( from CURRENT PLAYERS BOARD )   position piece moves to
     */

    public void translateBoard(Position oldPosition, Position newPosition) {
        // Get old row space from opponents board and get that row on players board
        int oldRow = convertDimension(oldPosition.getRow());
        // Get old column space from opponents board and get that column on players board
        int oldColumn = convertDimension(oldPosition.getCell());
        // Get new row space from opponents board and get that row on players board
        int newRow = convertDimension(newPosition.getRow());
        // Get new column space from opponents board and get that column on players board
        int newColumn = convertDimension(newPosition.getCell());

        // Give the new Space a game piece
        Space oldSpace = getSpace(oldRow, oldColumn);
        Space newSpace = getSpace(newRow, newColumn);

        // Give the new Space a game piece
        newSpace.setPiece(oldSpace.getPiece());
        // clear the old space
        oldSpace.clearPiece();
    }

    /**
     * Comverts a dimension to the opponent's view.
     *
     * @param d Dimension (Can be row of column) to convert
     * @return Converted dimension (Can be row of column)
     */
    public int convertDimension(int d) {
        return 7 - d;
    }

    /**
     * Recieve a PIECE from a given coordinant on the GAMEBOARD
     * @param row row of piece
     * @param col col of piece
     * @return
     */
    public Piece getPieceFromSpace(int row, int col){
        return getSpace(row, col).getPiece();
    }

    /**
     * A method which enables the APPLICATION TIER to check if a move is valid
     * @param row
     * @param col
     * @return
     */
    public boolean isSpaceMoveable(int row, int col){
        return getSpace(row, col).isValid();
    }
    /**
     * Get the GAMEBOARDS collection of SPACES
     * @return
     */
    public ArrayList<Row> getRows() {
        return rows;
    }

    /**
     * Checks if the piece (retrieved from a Position) can be crowned
     * @param position The Position the Piece ends up in
     * @return True if the piece can be king, False if the piece cannot be king
     */
    public boolean canKing(Position position){
        if(position.getRow() == 0){
            int cellIndex = position.getCell();
            int rowIndex = position.getRow();
            Space space = getSpace(rowIndex, cellIndex);
            return !space.getPiece().isKing();
        }
        return false;
    }



    /**
     * Returns an iterator of the array list of rows
     *
     * @return An iterator of the rows
     */
    public Iterator<Row> iterator() {
        return this.rows.iterator();
    }
}
