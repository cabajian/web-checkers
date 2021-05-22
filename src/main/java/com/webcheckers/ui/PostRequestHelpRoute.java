package com.webcheckers.ui;
import com.google.gson.Gson;
import com.webcheckers.appl.CheckersGame;
import com.webcheckers.model.Player;
import spark.*;

import java.util.logging.Logger;

/**
 * UI Controller to POST requesting help
 *
 * @author Chris Abajian
 */
public class PostRequestHelpRoute implements Route {

    /* application logger */
    private static final Logger LOG = Logger.getLogger(PostRequestHelpRoute.class.getName());

    /* gson instance */
    private final Gson gson;

    public PostRequestHelpRoute(Gson gson) {
        LOG.config("PostRequestHelpRoute is initialized.");
        //
        this.gson = gson;
    }

    /**
     * Handles POST request for a player to request help
     *
     * @return JSON message object of result
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostRequestHelpRoute is invoked.");

        // retrieve session
        Session httpSession = request.session();

        // get player from session, and game from player
        Player currentPlayer = httpSession.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR);
        CheckersGame game = currentPlayer.getGame();

        // get a random move from a random piece, return
        return gson.toJson(game.getRandomMove());
    }
}