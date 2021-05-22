package com.webcheckers.ui;
import com.google.gson.Gson;
import com.webcheckers.appl.CheckersGame;
import com.webcheckers.model.AI;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import spark.*;

import java.util.logging.Logger;

/**
 * UI Route to handle submitting a turn.
 *
 * @author Chris Abajian
 */
public class PostSubmitTurnRoute implements Route {

    /* application logger */
    private static final Logger LOG = Logger.getLogger(PostSubmitTurnRoute.class.getName());

    /* gson instance */
    private final Gson gson;

    public PostSubmitTurnRoute(Gson gson) {
        LOG.config("PostSubmitTurnRoute is initialized.");
        //
        this.gson = gson;
    }


    /**
     * Handles POST request for a player to submit a turn.
     * Also checks if game should end based on resigns and conditions
     *
     * @return JSON message object of result
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostSubmitTurnRoute is invoked.");

        // retrieve session
        Session httpSession = request.session();

        // get player from session, and game from player
        Player currentPlayer = httpSession.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR);
        CheckersGame game = currentPlayer.getGame();

        Message playerMsg = game.submitTurn();
        if(playerMsg.getType().equals(Message.MsgType.info) && game.getWhitePlayer() instanceof AI){

            AI aiPlayer = (AI) game.getWhitePlayer();
            Message aiMsg = aiPlayer.makeMove();

            if(aiMsg.getType().equals(Message.MsgType.info)){
                Message msg = new Message("Turn Submitted. AI player moved.", Message.MsgType.info);
                httpSession.attribute(GetHomeRoute.CLIENT_MSG_ATTR, msg);
                return gson.toJson(msg);
            }
            else{
                System.out.println("Invalid AI move.");
            }
        }

        // submit a turn and return the resulting message
        httpSession.attribute(GetHomeRoute.CLIENT_MSG_ATTR, playerMsg);
        return gson.toJson(playerMsg);
    }
}