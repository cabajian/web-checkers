package com.webcheckers.ui;

import org.junit.jupiter.api.*;
import spark.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Chris Abajian
 */
@Tag("UI-tier")
public class GetSigninRouteTest {

    private GetSigninRoute CuT;
    private TemplateEngine engine;
    private Request request;
    private Response response;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    void setup() {
        request = mock(Request.class);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        // create a unique CuT for each test
        CuT = new GetSigninRoute(engine);
    }

    @Test
    void signinTest() {
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
        testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, GetSigninRoute.TITLE);
        //   * test view name
        testHelper.assertViewName(GetSigninRoute.VIEW_NAME);
    }

}
