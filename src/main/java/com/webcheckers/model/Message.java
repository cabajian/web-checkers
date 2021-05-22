package com.webcheckers.model;

/**
 *  Data type that holds either a error or info message
 *
 *  @author Andy Zhu
 */

public class Message {
    public enum MsgType {
        info, error
    }

    private String text;
    private MsgType type;

    public Message(String text, MsgType type) {
        this.text = text;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public MsgType getType() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Message)) return false;
        final Message that = (Message) obj;
        return this.text.equals(that.getText()) && this.type.equals(that.getType());
    }
}
