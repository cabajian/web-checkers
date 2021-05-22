package com.webcheckers.ui;
import com.google.gson.Gson;
import com.webcheckers.appl.CheckersGame;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import spark.*;

import java.util.logging.Logger;

/**
 * UI Controller to POST resigning a game
 *
 * @author Andy Zhu + Chris Abajian
 */
public class PostResignGameRoute implements Route {

    /* application logger */
    private static final Logger LOG = Logger.getLogger(PostResignGameRoute.class.getName());

    /* gson instance */
    private final Gson gson;

    public PostResignGameRoute(Gson gson) {
        LOG.config("PostResignGameRoute is initialized.");
        //
        this.gson = gson;
    }

    /**
     * Handles POST request for a player to resign a game.
     *
     * @return JSON message object of result
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostResignGameRoute is invoked.");

        // retrieve session
        Session httpSession = request.session();

        // get player from session, and game from player
        Player currentPlayer = httpSession.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR);
        CheckersGame game = currentPlayer.getGame();

        // There is no need to check anything, as either player can resign when they do not have moves, and
        // while there are moves, the resign button is blocked
        game.setResign(currentPlayer.getColor());

        // clear game instance from player
        currentPlayer.setGame(null);

        httpSession.attribute(GetHomeRoute.CLIENT_MSG_ATTR, new Message("You have resigned: opponent wins!", Message.MsgType.error));

        // Displaying winner might be done in GetGameRoute, just send the resign message when done
        return gson.toJson(new Message("Successfully resigned", Message.MsgType.info));
    }
}