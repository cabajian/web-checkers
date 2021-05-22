package com.webcheckers.ui;

import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI Controller to GET the Signin page.
 *
 * @author Chris Abajian
 */
public class GetSigninRoute implements Route {

    /* signin render values */
    static final String TITLE = "Sign in";
    static final String VIEW_NAME = "signin.ftl";

    /* application logger */
    private static final Logger LOG = Logger.getLogger(GetSigninRoute.class.getName());

    /* template engine instance */
    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetSigninRoute(final TemplateEngine templateEngine) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.templateEngine = templateEngine;
        //
        LOG.config("GetSigninRoute is initialized.");
    }

    /**
     * Render the WebCheckers Signin page.
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
        LOG.finer("GetSigninRoute is invoked.");

        // initialize render map
        Map<String, Object> vm = new HashMap<>();

        // put title
        vm.put(GetHomeRoute.TITLE_ATTR, TITLE);

        // render signin
        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}