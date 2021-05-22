package com.webcheckers.ui;
import com.webcheckers.appl.CheckersGame;
import com.webcheckers.model.AI;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static spark.Spark.halt;

/**
 * The UI Controller to Post a request for an AI game for one player
 * @Author Andy Zhu
 */
public class PostRequestAIRoute implements Route{
    /* application logger */
    private static final Logger LOG = Logger.getLogger(PostRequestRoute.class.getName());

    /**
     * Create the Spark Route (UI controller) for the
     * {@code POST /} HTTP request.
     */
    public PostRequestAIRoute() {
        LOG.config("PostRequestAIRoute is initialized.");
    }

    /**
     * POSTS data for AI game
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
        LOG.finer("PostRequestAIRoute is invoked.");

        // initialize render map
        Map<String, Object> vm = new HashMap<>();

        // retrieve the HTTP session and player from session
        final Session httpSession = request.session();
        final Player currentPlayer = httpSession.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR);

        // retrieve request parameter

        // do not need to check if AI exists because it will be created
        if (currentPlayer != null) {
            // create game instance, the AI is created in CheckersGame instance
            CheckersGame game = new CheckersGame(currentPlayer);
            // In an single player game, AI will always start second as white player

            AI computerPlayer = (AI) game.getWhitePlayer();

            // set players as session attributes and redirect to game.ftl
            currentPlayer.setColor(Player.PlayerColor.RED);
            computerPlayer.setColor(Player.PlayerColor.WHITE);
            currentPlayer.setInGame();
            computerPlayer.setInGame();
            currentPlayer.setGame(game);
            computerPlayer.setGame(game);

            // redirect to game
            response.redirect(WebServer.GAME_URL);
            halt();
            return null;
        } else {
            // error-- one or both players do not exists
            httpSession.attribute(GetHomeRoute.CLIENT_MSG_ATTR,
                    new Message("System error: Your player does not exist", Message.MsgType.error));
        }

        // redirect to home
        response.redirect(WebServer.HOME_URL);
        halt();
        return null;
    }

}
