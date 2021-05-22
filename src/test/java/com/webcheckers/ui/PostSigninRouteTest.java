package com.webcheckers.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
public class PostSigninRouteTest {

    private PostSigninRoute CuT;
    private Request request;
    private Response response;
    private TemplateEngine engine;
    private Session session;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    void setup() {
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        engine = mock(TemplateEngine.class);
        when(request.session()).thenReturn(session);
        // create a unique CuT for each test
        CuT = new PostSigninRoute(engine);
    }

    /**
     *
     */
    @Test
    void signin_successful() {

        when(request.queryParams(PostSigninRoute.USERNAME_ATTR)).thenReturn("current");

        try {
            CuT.handle(request, response);
        } catch (HaltException ex) {
            verify(response).redirect(WebServer.HOME_URL);
        }

    }

    /**
     *
     */
    @Test
    void signin_fail() {

        when(request.queryParams(PostSigninRoute.USERNAME_ATTR)).thenReturn("-current");

        CuT.handle(request, response);

        // To analyze what the Route created in the View-Model map you need
        // to be able to extract the argument to the TemplateEngine.render method.
        // Mock up the 'render' method by supplying a Mockito 'Answer' object
        // that captures the ModelAndView data passed to the template engine
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // Invoke the test (ignore the output)
        CuT.handle(request, response);

        // Analyze the content passed into the render method
        //   * model is a non-null Map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        //   * model contains all necessary View-Model data
        testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, PostSigninRoute.TITLE);
        //   * test view name
        testHelper.assertViewName(GetSigninRoute.VIEW_NAME);

    }

}
