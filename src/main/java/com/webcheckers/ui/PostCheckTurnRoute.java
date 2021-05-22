package com.webcheckers.ui;
import com.google.gson.Gson;
import com.webcheckers.appl.CheckersGame;
import com.webcheckers.model.AI;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * UI Controller to POST checking a turn
 *
 * @author Andy Zhu + Chris Abajian
 */
public class PostCheckTurnRoute implements Route {
    private Gson gson;
    private static final Logger LOG = Logger.getLogger(PostCheckTurnRoute.class.getName());

    /**
     * Create the Spark Route for the ajax call
     *
     * @param gson an object used to translate objects to json objects
     */
    public PostCheckTurnRoute(final Gson gson) {
        Objects.requireNonNull(gson, "gson must not be null");
        this.gson = gson;
        LOG.config("PostCheckTurnRoute is initialized.");
    }


    /**
     * Checks whose turn it is
     * returns a message with string of boolean and type info as a json object
     * The message says TRUE if it is the current player's turn or FALSE
     * if it is not
     *
     * @return a json-converted message to be given to the ajax
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostCheckTurnRoute is invoked.");

        // retrieve the HTTP session
        final Session httpSession = request.session();

        // retrieve request parameter
        final Player currentPlayer = httpSession.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR);
        final CheckersGame game = currentPlayer.getGame();

        //Check if resigned or winner decided
        if(game.hasResigned() || game.hasRedWon() || game.hasWhiteWon()) {
            Message msg = new Message("true", Message.MsgType.info);
            return gson.toJson(msg);
        }

        Message turnMessage;
        //If it is currentPlayers turn player's turn, then let him do his moves
        if(game.getWhoseTurn().equals(currentPlayer.getColor())) {
            turnMessage = new Message("true", Message.MsgType.info);
        }
        //Else lock em out
        else {
            turnMessage = new Message("false", Message.MsgType.info);
        }

        // return message
        return gson.toJson(turnMessage);
    }
}