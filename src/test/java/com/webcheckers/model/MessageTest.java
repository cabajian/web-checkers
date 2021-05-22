package com.webcheckers.model;

import com.webcheckers.model.Message.MsgType;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The unit test suite for the {@link Message} component
 *
 * @author Andy Zhu & Fayez Mehdad
 */

@Tag("Model-tier")
public class MessageTest {

    private static final String TEXT = "Test text";
    private static final MsgType MESSAGE_TYPE = MsgType.info;

    /**
     * Test that main constructor works properly and text is properly received
     */
    @Test
    public void argTest() {
        final Message CuT = new Message(TEXT, MESSAGE_TYPE);
        assertEquals(TEXT, CuT.getText());
    }

    /**
     * Test that messages all have a type in them
     */
    @Test
    public void hasTypeTest() {
        final Message CuT = new Message(TEXT, MESSAGE_TYPE);
        assertEquals(MESSAGE_TYPE, CuT.getType());
    }

    /**
     * Test the getText method
     */
    @Test
    void getText() {
        final Message CuT = new Message(TEXT, MESSAGE_TYPE);
        assertEquals(TEXT, CuT.getText());
    }

    /**
     * Test the getType method
     */
    @Test
    void getType() {
        final Message CuT = new Message(TEXT, MESSAGE_TYPE);
        assertEquals(MESSAGE_TYPE, CuT.getType());
    }

    /**
     * Test the equals method
     */
    @Test
    void equals() {
        final Message CuT1 = new Message(TEXT, MESSAGE_TYPE);
        final Message CuT2 = new Message("Test text", Message.MsgType.info);
        assertTrue(CuT1.equals(CuT2));
    }
}
