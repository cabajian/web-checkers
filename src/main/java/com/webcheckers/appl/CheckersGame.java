package com.webcheckers.appl;

import com.webcheckers.model.*;

import java.util.ArrayList;

/**
 * The general, Checkers Game Application to handle the game processes
 *
 * @author Becky Reich + Chris Abajian + Andy Zhu
 */

public class CheckersGame {
    private Player whitePlayer;
    private Player redPlayer;

    private Gameboard whiteGameboard;
    private Gameboard redGameboard;

    private ArrayList<Move> currentPlayerMoves;

    private ArrayList<Space> currentMoveLostPieces;

    // Current players turn
    private Player.PlayerColor currentPlayerColor;

    // Flag to set if the current player can move
    private boolean canCurrentMove;

    // Flag to check if someone resigned
    private boolean resigned;

    // The winner flag is set after game is over
    private boolean redWins;
    private boolean whiteWins;


    /**
     * Make a new game w/ starting player being RED
     */
    public CheckersGame(Player redPlayer, Player whitePlayer) {
        this.redPlayer = redPlayer;
        this.currentPlayerColor = Player.PlayerColor.RED;
        this.redGameboard = new Gameboard(Player.PlayerColor.RED); // regular game
//        this.redGameboard = new Gameboard(Player.PlayerColor.RED, 0); // king/endgame test
//        this.redGameboard = new Gameboard(Player.PlayerColor.RED, 1); // no moves left
//        this.redGameboard = new Gameboard(Player.PlayerColor.RED, 2); // triple jump
//
        this.redPlayer.setInGame();

        this.whitePlayer = whitePlayer;
        this.whiteGameboard = new Gameboard(Player.PlayerColor.WHITE); // regular game
//        this.whiteGameboard = new Gameboard(Player.PlayerColor.WHITE, 2);

        this.currentPlayerMoves = new ArrayList<>();

        this.currentMoveLostPieces = new ArrayList<>();

        this.canCurrentMove = true;

        this.whitePlayer.setInGame();
        this.redWins = false;
        this.whiteWins = false;
        this.resigned = false;
    }
    /**
     * Start a game against the computer, user player is RED
     */
    public CheckersGame(Player user) {
        // Set the user in game and as the red player
        this.redPlayer = user;
        this.currentPlayerColor = Player.PlayerColor.RED;
        this.redGameboard = new Gameboard(Player.PlayerColor.RED);

        this.redPlayer.setInGame();

        // create an AI player with current game session set in
        this.whitePlayer = new AI();
        this.whiteGameboard = new Gameboard(Player.PlayerColor.WHITE);

        this.currentPlayerMoves = new ArrayList<>();

        this.currentMoveLostPieces = new ArrayList<>();

        this.canCurrentMove = true;

        this.whitePlayer.setInGame();
        this.redWins = false;
        this.whiteWins = false;
        this.resigned = false;
    }

    /**
     * Get the WHITE PLAYER Object
     *
     * @return the white player
     */
    public Player getWhitePlayer() {
        return whitePlayer;
    }

    /**
     * Get the RED PLAYER Object
     *
     * @return the red player
     */
    public Player getRedPlayer() {
        return redPlayer;
    }

    /**
     * Get whose turn it is based on PLAYER>COLOR
     *
     * @return current player's turn color
     */
    public Player.PlayerColor getWhoseTurn() {
        return this.currentPlayerColor;
    }

    /**
     * Gets the GameBoard of the provided player.
     *
     * @param player the player
     * @return the player's GameBoard
     */
    public Gameboard getBoard(Player player) {
        return (player.equals(redPlayer)) ? redGameboard : whiteGameboard;
    }

    /**
     * Checks if someone has resigned.
     *
     * @return whether someone has resigned or not.
     */
    public boolean hasResigned() {
        return resigned;
    }

    /**
     * Checks if the white player has won.
     *
     * @return if the white player has won or not.
     */
    public boolean hasWhiteWon() {
        return whiteWins;
    }

    /**
     * Checks if the red player has won.
     *
     * @return if the red player has won or not.
     */
    public boolean hasRedWon() {
        return redWins;
    }

    /**
     * Sets the resigned status of the game and sets the winner accordingly.
     *
     * @param loser the loser's color.
     */
    public void setResign(Player.PlayerColor loser) {
        resigned = true;
        if (loser.equals(Player.PlayerColor.RED)) setWinner(Player.PlayerColor.WHITE);
        else setWinner(Player.PlayerColor.RED);
    }

    /**
     * Sets the game winner.
     *
     * @param winner the winner's color
     */
    public void setWinner(Player.PlayerColor winner) {
        if (winner.equals(Player.PlayerColor.RED)) redWins = true;
        else if (winner.equals(Player.PlayerColor.WHITE)) whiteWins = true;
        redPlayer.endGame();
        whitePlayer.endGame();
    }

    /**
     * Switches the current player turn.
     *
     * @return the color of the new player turn.
     */
    public Player.PlayerColor nextTurn() {
        if (this.currentPlayerColor.equals(Player.PlayerColor.RED)) {
            currentPlayerColor = Player.PlayerColor.WHITE;
        } else currentPlayerColor = Player.PlayerColor.RED;
        return this.currentPlayerColor;
    }

    /**
     * Validates a move by checking the Validation.
     *
     * @param move the move to validate
     * @return a Message of the move validation.
     */
    public Message validateMove(Move move) {
        move.setType();
        // get current player turn and board
        Player currentPlayer = getWhoseTurn().equals(Player.PlayerColor.RED) ? getRedPlayer() : getWhitePlayer();
        Gameboard currentBoard = getBoard(currentPlayer);

        // reject move if player has already made a move
        if (!canCurrentMove) {
            return new Message("You have already made a move!", Message.MsgType.error);
        }

        Message msg;
        // validate move based on type
        switch (move.getType()) {
            case SINGLE:
                // check if a jump move can be made
                if (Validation.getMovesByType(currentBoard, Move.MoveType.JUMP).size() > 0) {
                    return new Message("Mandatory jump move available", Message.MsgType.error);
                }
                // validate a single move
                msg = Validation.validateSingle(currentBoard, move);
                // if valid, set canCurrentMove flag to false
                if (msg.getType().equals(Message.MsgType.info)) canCurrentMove = false;
                break;
            case JUMP:
                // validate a jump move
                if (this.currentPlayerMoves.size() == 0) {
                    msg = Validation.validateJump(currentBoard, move, null);
                } else {
                    msg = Validation.validateJump(currentBoard, move, this.currentPlayerMoves.get(this.currentPlayerMoves.size() - 1));
                }
                //if valid, remove jumped over piece
                if (msg.getType().equals(Message.MsgType.info)) {
                    // add jumped over piece to removal list
                    currentMoveLostPieces.add(currentBoard.getJumpedOverSpace(
                            move.getStart().getRow(), move.getStart().getCell(), move.getEnd().getRow(), move.getEnd().getCell()));
                }
                break;
            default:
                msg = new Message("CheckersGame error: invalid move type.", Message.MsgType.error);
        }

        // add valid move to current moves list
        if (msg.getType().equals(Message.MsgType.info)) {
            this.currentPlayerMoves.add(move);
            currentBoard.movePiece(move.getStart(), move.getEnd());
            // if no more jumps available, set canCurrentMove to false
            Space endSpace = currentBoard.getRows().get(move.getEnd().getRow()).getSpaces().get(move.getEnd().getCell());
            ArrayList<Position> jumps = Validation.getJumpsFromSpace(currentBoard, endSpace);
            boolean noMoreJumps = true;
            if (jumps.size() > 0) {
                for (Position p : jumps) {
                    if (!currentBoard.getPositionFromSpace(move.getStart().getRow(), move.getStart().getCell()).equals(p)) {
                        noMoreJumps = false;
                    }
                }
            }
            if (noMoreJumps) canCurrentMove = false;
        }

        return msg;
    }

    /**
     * Backs up the most recent move
     *
     * @return a Message of the backup result
     */
    public Message backupMove() {
        // get current player turn and board
        Player currentPlayer = getWhoseTurn().equals(Player.PlayerColor.RED) ? getRedPlayer() : getWhitePlayer();
        Gameboard currentBoard = getBoard(currentPlayer);

        // retrieve last move
        Message msg;
        if (currentPlayerMoves.size() < 1) {
            // current player hasn't made any moves
            msg = new Message("No moves to backup", Message.MsgType.error);
        } else {
            // player has made moves
            // get last move
            Move lastMove = currentPlayerMoves.remove(currentPlayerMoves.size() - 1);
            // move piece back
            currentBoard.movePiece(lastMove.getEnd(), lastMove.getStart());
            // check if move was a jump
            if (lastMove.getType().equals(Move.MoveType.JUMP)) {
                // restore piece
                currentMoveLostPieces.remove(currentMoveLostPieces.size() - 1);
            }
            msg = new Message("Last move backed up", Message.MsgType.info);
        }

        canCurrentMove = true;

        return msg;
    }

    public Message submitTurn() {
        // get current player turn and board
        Player currentPlayer = getWhoseTurn().equals(Player.PlayerColor.RED) ? getRedPlayer() : getWhitePlayer();
        Player opponent = getWhoseTurn().equals(Player.PlayerColor.RED) ? getWhitePlayer() : getRedPlayer();
        Gameboard currentBoard = getBoard(currentPlayer);
        Gameboard opponentBoard = getBoard(opponent);

        // check that a move has been made
        if (currentPlayerMoves.size() == 0) {
            return new Message("CheckersGame error: no move made", Message.MsgType.error);
        }

        // check if player can still move
        if (canCurrentMove) {
            return new Message("Additional jumps available", Message.MsgType.error);
        }

        // valid move sequence-- modify boards
        for (Move move : currentPlayerMoves) {
            // translate moves onto opponent board
            opponentBoard.translateBoard(move.getStart(), move.getEnd());
            // king any pieces that made it to the end row
            if (currentBoard.canKing(move.getEnd())) {
                // set current player's view king
                Piece p = currentBoard.getRows().get(0).getSpaces().get(move.getEnd().getCell()).getPiece();
                p.setKing();
                // set opponent's view king
                int opRow = opponentBoard.convertDimension(move.getEnd().getRow());
                int opColumn = opponentBoard.convertDimension(move.getEnd().getCell());
                opponentBoard.getRows().get(opRow).getSpaces().get(opColumn).getPiece().setKing();
            }
            // remove jumped over pieces from both game boards
            for (Space space : currentMoveLostPieces) {
                currentBoard.losePiece(space);
                opponentBoard.losePieceTranslate(space);
            }
            currentMoveLostPieces.clear();
        }
        currentPlayerMoves.clear();

        // check if opponent has any pieces
        if (opponentBoard.getSpacesWithPieces().size() == 0) {
            setWinner(currentPlayerColor);
            return new Message("Move successful. Opponent has no more pieces left, you win! Redirecting...", Message.MsgType.info);
        }

        // check if opponent has any moves
        if (!Validation.canMove(opponentBoard)) {
            setWinner(currentPlayerColor);
            return new Message("Move successful. Opponent has no more moves, you win! Redirecting...", Message.MsgType.info);
        }

        // set next turn
        nextTurn();

        // reset canCurrentMove
        canCurrentMove = true;

        return new Message("Move successful", Message.MsgType.info);
    }

    public Move getRandomMove() {
        // get current player turn and board
        Player player = getWhoseTurn().equals(Player.PlayerColor.RED) ? getRedPlayer() : getWhitePlayer();
        Gameboard board = getBoard(player);

        ArrayList<Space> spacesWithPieces = board.getSpacesWithPieces();
        ArrayList<Position> spacesToMoveTo;
        Space space;
        int rand;

        // Check to see if that piece has any JUMP moves. Jump moves take PRIORITY.
        while (spacesWithPieces.size() > 0) {
            // Select a random piece from the board
            rand = (int) (Math.random() * spacesWithPieces.size());
            space = spacesWithPieces.get(rand);

            spacesToMoveTo = Validation.getJumpsFromSpace(board, space);
            if (spacesToMoveTo.size() > 0) {
                // If reach this point, there IS a jump move. Randomly pick a jump.
                rand = (int) (Math.random() * spacesToMoveTo.size());
                return new Move(board.getPositionFromSpace(space.getRow(), space.getCellIdx()), spacesToMoveTo.get(rand));
            }
            // No valid jump for this piece
            spacesWithPieces.remove(space);
        }

        // No JUMP move if reach this point, then
        // Check to see if that piece has any SINGLE moves
        spacesWithPieces = board.getSpacesWithPieces();
        while (spacesWithPieces.size() > 0) {
            // Select a random piece from the board
            rand = (int) (Math.random() * spacesWithPieces.size());
            space = spacesWithPieces.get(rand);

            spacesToMoveTo = Validation.getSingleFromSpace(board, space);
            if (spacesToMoveTo.size() > 0) {
                // If reach this point, there IS a single move. Randomly pick a single.
                rand = (int) (Math.random() * spacesToMoveTo.size());
                return new Move(board.getPositionFromSpace(space.getRow(), space.getCellIdx()), spacesToMoveTo.get(rand));
            }
            // No valid single move for this piec
            spacesWithPieces.remove(space);
        }
        return null;
    }

    public Message validateAIMove(Move move){
        if (getBoard(getWhitePlayer()).getSpacesWithPieces().size() == 0) {
            return new Message("No AI pieces available.", Message.MsgType.info);
        }
        ArrayList<Position> spacesToMoveTo = Validation.getJumpsFromSpace(whiteGameboard, whiteGameboard.getSpace(move.getEnd().getRow(), move.getEnd().getCell()));
        if (spacesToMoveTo == null || spacesToMoveTo.size() < 1) {
            return new Message("No AI moves available.", Message.MsgType.info);
        }
        Move otherMove = new Move(move.getEnd(), spacesToMoveTo.get(0));
        Message msg = validateMove(otherMove);
        if(msg.getType().equals(Message.MsgType.info)){
            return submitTurn();
        }
        else{
             return validateAIMove(otherMove);
        }
    }
}