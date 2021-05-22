package com.webcheckers.model;

/**
 * AI, a child class of Player, has a field called move that will be used to execute a , well, move when makeMove is inokved.
 *
 * @author Kevin Adrian
 */

public class AI extends Player {

    /**
     * AI's name field
     */
    private Move move;

    /**
     * Constructor of AI that uses the Player class's constructor and adds a null value for the move field
     */
    public AI(){
        super();
        this.move = null;
    }

    /**
     * A method that generates a random move, validates, and submits it
     * @return A info or error message depending on whether the move was successful or not
     */
    public Message makeMove(){
        //Generates random move
        this.move = getGame().getRandomMove();
        //Validates the move
        Message msg = getGame().validateMove(move);
        //If move was successful
        if(msg.getType().equals(Message.MsgType.info)) {
            //Submit the turn
            Message submitMsg = getGame().submitTurn();
            //Check if submission was successful
            if (submitMsg.getType().equals(Message.MsgType.error)) {
                return getGame().validateAIMove(move);
            }
            return submitMsg;
        }
        else{
            return getGame().validateAIMove(move);
        }
    }

    /**
     * Used for testing purposes
     * @return The mmove field
     */
    public Move getMove(){ return move; }
}