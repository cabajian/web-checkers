package com.webcheckers.ui;

import com.webcheckers.appl.CheckersGame;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static spark.Spark.halt;

/**
 * The UI Controller to POST a Request from one player to another.
 *
 * @author Chris Abajian
 */
public class PostRequestRoute implements Route {

    /* post parameter attribute */
    static final String OPPONENT_ATTR = "opponent";

    /* application logger */
    private static final Logger LOG = Logger.getLogger(PostRequestRoute.class.getName());

    /**
     * Create the Spark Route (UI controller) for the
     * {@code POST /} HTTP request.
     */
    public PostRequestRoute() {
        LOG.config("PostRequestRoute is initialized.");
    }

    /**
     * POST's request data and GET's a game if valid request
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the page to render
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostRequestRoute is invoked.");

        // initialize render map
        Map<String, Object> vm = new HashMap<>();

        // retrieve the HTTP session and player from session
        final Session httpSession = request.session();
        final Player currentPlayer = httpSession.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR);

        // retrieve request parameter
        final String opponentName = request.queryParams(OPPONENT_ATTR);
        // get opponent from PlayerLobby
        final Player opponentPlayer = PlayerLobby.getPlayer(opponentName);

        // if both players exist
        if (currentPlayer != null && opponentPlayer != null) {
            // if the opponent is not in a game already
            if(!opponentPlayer.isInGame()) {
                // create game instance
                CheckersGame game = new CheckersGame(currentPlayer, opponentPlayer);
                // set players as session attributes and redirect to game.ftl
                currentPlayer.setColor(Player.PlayerColor.RED);
                opponentPlayer.setColor(Player.PlayerColor.WHITE);
                currentPlayer.setInGame();
                opponentPlayer.setInGame();
                currentPlayer.setGame(game);
                opponentPlayer.setGame(game);
                // redirect to game
                response.redirect(WebServer.GAME_URL);
                halt();
                return null;
            } else {
                // opponent is already in a game
                httpSession.attribute(GetHomeRoute.CLIENT_MSG_ATTR,
                        new Message("Player is currently in a game", Message.MsgType.error));
            }
        } else {
            // error-- one or both players do not exists
            httpSession.attribute(GetHomeRoute.CLIENT_MSG_ATTR,
                    new Message("System error: one or both players do not exist", Message.MsgType.error));
        }

        // redirect to home
        response.redirect(WebServer.HOME_URL);
        halt();
        return null;
    }

}