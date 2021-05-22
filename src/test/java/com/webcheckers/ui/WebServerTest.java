package com.webcheckers.ui;

import com.google.gson.Gson;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.TemplateEngine;

import static org.mockito.Mockito.mock;

@Tag("UI-tier")
public class WebServerTest {

    @Test
    void webserverTest() {
        TemplateEngine engine = mock(TemplateEngine.class);
        Gson gson = new Gson();
        WebServer CuT = new WebServer(engine, gson);

        try {
            CuT.initialize();
        } catch (IllegalStateException e) {

        }
    }

}
