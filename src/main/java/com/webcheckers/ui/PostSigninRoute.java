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
 * The UI Controller to POST the Signin form.
 *
 * @author Chris Abajian
 */
public class PostSigninRoute implements Route {

    /* post parameter attribute */
    static final String USERNAME_ATTR = "username";

    /* signin render values */
    static final String TITLE = "Sign in";
    static final String VIEW_NAME = "signin.ftl";

    /* application logger */
    private static final Logger LOG = Logger.getLogger(PostSigninRoute.class.getName());

    /* template engine instance */
    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code POST /} HTTP request.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public PostSigninRoute(final TemplateEngine templateEngine) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.templateEngine = templateEngine;
        //
        LOG.config("PostSigninRoute is initialized.");
    }

    /**
     * POST's signin form data to Player
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
        LOG.finer("PostSigninRoute is invoked.");

        Map<String, Object> vm = new HashMap<>();
        // retrieve the HTTP session
        final Session httpSession = request.session();

        // retrieve request parameter
        final String playerNameStr = request.queryParams(USERNAME_ATTR);

        // attempt to sign player in
        Player p = PlayerLobby.signin(playerNameStr);

        if (p != null) { // successful signin
            //put player object into session then redirect to home
            httpSession.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR, p);
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        } else { // unsuccessful sign in
            //display error message
            vm.put(GetHomeRoute.CLIENT_MSG_ATTR,
                    new Message("The username you've entered is either taken or contains illegal characters." +
                            "</br>Please enter a different username.", Message.MsgType.error));
        }

        // put title
        vm.put(GetHomeRoute.TITLE_ATTR, TITLE);

        // render signin
        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }

}