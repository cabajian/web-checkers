package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.CheckersGame;
import com.webcheckers.model.Player;
import spark.*;

import java.util.logging.Logger;

/**
 * The UI Controller to POST a backup move request
 *
 * @author Chris Abajian
 */
public class PostBackupMoveRoute implements Route {

    /* application logger */
    private static final Logger LOG = Logger.getLogger(PostBackupMoveRoute.class.getName());

    /* gson instance */
    private final Gson gson;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code POST /} HTTP request.
     */
    public PostBackupMoveRoute(final Gson gson) {
        LOG.config("PostBackupMoveRoute is initialized.");
        //
        this.gson = gson;
    }

    /**
     * POST's backup move request by obtaining the previous moves
     * from the session and backtracking from the array list.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostBackupMoveRoute is invoked.");

        //obtain session
        Session httpSession = request.session();
        Player player = httpSession.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR);
        CheckersGame game = player.getGame();

        // attempt to backup move, return result message
        return gson.toJson(game.backupMove());
    }

}