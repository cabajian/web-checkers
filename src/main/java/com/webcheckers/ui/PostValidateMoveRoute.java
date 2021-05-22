package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.CheckersGame;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import spark.*;

import java.util.logging.Logger;

/**
 * The UI Controller to POST a validate move request
 *
 * @author Chris Abajian
 */
public class PostValidateMoveRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostValidateMoveRoute.class.getName());

    private final Gson gson;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code POST /} HTTP request.
     */
    public PostValidateMoveRoute(final Gson gson) {
        LOG.config("PostValidateMoveRoute is initialized.");

        this.gson = gson;
    }

    /**
     * POST's validate move request
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return the json Message response
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostValidateMoveRoute is invoked.");

        // retrieve the session
        Session httpSession = request.session();
        // get the current player from session
        Player currentPlayer = httpSession.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR);
        // get the game
        CheckersGame game = currentPlayer.getGame();

        // validate move and return result message
        return gson.toJson(game.validateMove(gson.fromJson(request.body(), Move.class)));
    }

}