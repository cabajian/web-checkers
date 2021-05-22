package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.CheckersGame;
import com.webcheckers.model.AI;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import spark.*;

import static spark.Spark.halt;

/**
 * The UI Controller to GET the Game page.
 *
 * @author Chris Abajian and Becky Reich
 */
public class GetGameRoute implements Route {

    /* game render attributes */
    static final String RED_PLAYER_ATTR = "redPlayer";
    static final String WHITE_PLAYER_ATTR = "whitePlayer";
    static final String ACTIVE_COLOR_ATTR = "activeColor";
    static final String VIEW_MODE_ATTR = "viewMode";
    static final String MODE_OPT_ATTR = "modeOptionsAsJSON";
    static final String BOARD_VIEW_ATTR = "board";
    static final String VIEW_NAME = "game.ftl";
    static final String TITLE = "Game";

    /* application logger */
    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

    /* template engine instance */
    private final TemplateEngine templateEngine;


    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetGameRoute(final TemplateEngine templateEngine) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.templateEngine = templateEngine;
        //
        LOG.config("GetGameRoute is initialized.");
    }

    /**
     * Render the WebCheckers Game page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the Game page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetGameRoute is invoked.");
        // get session
        final Session httpSession = request.session();
        // get player from session
        Player currentPlayer = httpSession.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR);

        // check player is signed in
        if (currentPlayer == null) {
            // player not signed in - redirect to home
            httpSession.attribute(GetHomeRoute.CLIENT_MSG_ATTR, new Message("You must sign-in to play a game.", Message.MsgType.error));
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        }

        // get game from player
        CheckersGame game = currentPlayer.getGame();

        // Check who won if game is over
        if (game.hasResigned() || game.hasWhiteWon() || game.hasRedWon()) {
            if (game.hasResigned()) {
                httpSession.attribute(GetHomeRoute.CLIENT_MSG_ATTR, new Message("Opponent resigned: you have won!", Message.MsgType.info));
            } else if (game.hasWhiteWon() && currentPlayer.getColor().equals(Player.PlayerColor.WHITE)) {
                httpSession.attribute(GetHomeRoute.CLIENT_MSG_ATTR, new Message("You have won!", Message.MsgType.info));
            } else if (game.hasWhiteWon() && currentPlayer.getColor().equals(Player.PlayerColor.RED)) {
                httpSession.attribute(GetHomeRoute.CLIENT_MSG_ATTR, new Message("Opponent has won!", Message.MsgType.error));
            } else if (game.hasRedWon() && currentPlayer.getColor().equals(Player.PlayerColor.RED)) {
                httpSession.attribute(GetHomeRoute.CLIENT_MSG_ATTR, new Message("You have won!", Message.MsgType.info));
            } else if (game.hasRedWon() && currentPlayer.getColor().equals(Player.PlayerColor.WHITE)) {
                httpSession.attribute(GetHomeRoute.CLIENT_MSG_ATTR, new Message("Opponent has won!", Message.MsgType.error));
            }
            // clear game from player, redirect to home
            currentPlayer.setGame(null);
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        }



        // create view-model render map
        final Map<String, Object> vm = new HashMap<>();

        Message msg = httpSession.attribute(GetHomeRoute.CLIENT_MSG_ATTR);
        if (msg != null) vm.put(GetHomeRoute.CLIENT_MSG_ATTR, msg);

        // remove any client messages in session
        httpSession.attribute(GetHomeRoute.CLIENT_MSG_ATTR, null);

        // put title
        vm.put(GetHomeRoute.TITLE_ATTR, TITLE);
        // put players
        vm.put(RED_PLAYER_ATTR, game.getRedPlayer());
        vm.put(WHITE_PLAYER_ATTR, game.getWhitePlayer());
        // put current player
        vm.put(GetHomeRoute.CURRENT_PLAYER_ATTR, currentPlayer);
        // set view mode
        vm.put(VIEW_MODE_ATTR, viewMode.PLAY);
        vm.put(MODE_OPT_ATTR, null);
        // set current color
        vm.put(ACTIVE_COLOR_ATTR, game.getWhoseTurn());
        // set current player's board
        vm.put(BOARD_VIEW_ATTR, game.getBoard(currentPlayer));

        // render view-model map
        return templateEngine.render(new ModelAndView(vm , VIEW_NAME));
    }

    enum viewMode {
        PLAY, SPECTATOR, REPLAY;
    }

}