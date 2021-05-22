package com.webcheckers.ui;

import java.util.*;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import spark.*;

import static spark.Spark.halt;

/**
 * The UI Controller to GET the Home page.
 *
 * @author Chris Abajian
 */
public class GetHomeRoute implements Route {

    /* render attributes */
    static final String TITLE_ATTR = "title";
    static final String CURRENT_PLAYER_ATTR = "currentPlayer";
    static final String CLIENT_MSG_ATTR = "message";
    static final String PLAYER_NUM_ATTR = "playerNum";
    static final String PLAYER_LIST_ATTR = "playerList";

    /* home render values */
    static final String TITLE = "Home";
    static final String ALT_TITLE = "WebCheckers: An Online Checkers Game";
    static final String VIEW_NAME = "home.ftl";

    /* application logger */
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    /* template engine instance */
    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request.
     *
     * @param templateEngine the HTML template rendering engine
     */
    public GetHomeRoute(final TemplateEngine templateEngine) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.templateEngine = templateEngine;
        //
        LOG.config("GetHomeRoute is initialized.");
    }

    /**
     * Render the WebCheckers Home page.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the Home page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetHomeRoute is invoked.");
        // initialize render map
        Map<String, Object> vm = new HashMap<>();
        // retrieve the HTTP session and current player
        final Session httpSession = request.session();
        final Player currentPlayer = httpSession.attribute(CURRENT_PLAYER_ATTR);

        if (currentPlayer != null) { // player signed in

            if (currentPlayer.isInGame()) { // player is in a game
                response.redirect(WebServer.GAME_URL); // redirect to /game
                halt();
                return null;
            }

            // player not in a game-- display any messages
            Message msg = httpSession.attribute(CLIENT_MSG_ATTR);
            if (msg != null) vm.put(CLIENT_MSG_ATTR, msg);

            // put player into render map
            vm.put(CURRENT_PLAYER_ATTR, currentPlayer);

            // get player list, remove player from list copy
            List<Player> playerList = PlayerLobby.displayNames();
            playerList.remove(currentPlayer);
            vm.put(PLAYER_LIST_ATTR, playerList);

            // put home page title
            vm.put(TITLE_ATTR, TITLE);

        } else { // player not signed in

            // put number of players into render map
            vm.put(PLAYER_NUM_ATTR, PlayerLobby.displayNames().size());
            // put alternate home page title
            vm.put(TITLE_ATTR, ALT_TITLE);

        }

        // render home
        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }

}