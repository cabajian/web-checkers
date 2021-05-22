package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

/**
 * The UI Controller to GET the Signout request.
 *
 * @author Chris Abajian
 */
public class GetSignoutRoute implements Route {

    /* application logger */
    private static final Logger LOG = Logger.getLogger(GetSignoutRoute.class.getName());

    /**
     * Create the Spark Route (UI controller) for the
     * {@code POST /} HTTP request.
     */
    public GetSignoutRoute() {
        LOG.config("GetSignoutRoute is initialized.");
    }

    /**
     * POST's signout form data
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the Signin page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetSignoutRoute is invoked.");

        Map<String, Object> vm = new HashMap<>();
        // retrieve the HTTP session
        final Session httpSession = request.session();

        // sign player out
        Player currentPlayer = httpSession.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR);
        PlayerLobby.signout(currentPlayer);
        httpSession.invalidate();

        // redirect to home
        response.redirect(WebServer.HOME_URL);
        halt();
        return null;
    }

}