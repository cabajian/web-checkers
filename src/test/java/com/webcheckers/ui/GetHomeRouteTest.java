package com.webcheckers.ui;

import com.webcheckers.model.Player;
import org.junit.jupiter.api.*;
import spark.*;


import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
public class GetHomeRouteTest {

    private GetHomeRoute CuT;
    private TemplateEngine engine;
    private Request request;
    private Response response;
    private Session session;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        // create a unique CuT for each test
        CuT = new GetHomeRoute(engine);
    }

    @Test
    public void existing_player() {

        final Player current = new Player("current");
        session.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR, current);

        when(session.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR)).thenReturn(current);

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
        testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);
        testHelper.assertViewModelAttribute(GetHomeRoute.CURRENT_PLAYER_ATTR, current);
        //   * test view name
        testHelper.assertViewName(GetHomeRoute.VIEW_NAME);
    }

    @Test
    public void existing_player_ingame() {

        final Player current = new Player("current");
        final Player opponent = new Player("opponent");
        current.setInGame();
        session.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR, current);

        when(session.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR)).thenReturn(current);

        // Invoke the test
        try {
            CuT.handle(request, response);
            fail("Redirects invoke halt exceptions.");
        } catch (HaltException e) {
            // expected
        }

        // Analyze the results:
        //   * redirect to the Game view
        verify(response).redirect(WebServer.GAME_URL);
    }

    @Test
    public void new_player() {

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
        testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, GetHomeRoute.ALT_TITLE);
        //   * test view name
        testHelper.assertViewName(GetHomeRoute.VIEW_NAME);
    }

}