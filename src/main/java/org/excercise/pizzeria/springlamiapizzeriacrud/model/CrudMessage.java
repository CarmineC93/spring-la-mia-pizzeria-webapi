package org.excercise.pizzeria.springlamiapizzeriacrud.model;

public class CrudMessage {
    public enum CrudMessageType{
        SUCCESS, ERROR
    }



    private CrudMessageType type;
    private String text;

    public CrudMessage() {}

    public CrudMessage(CrudMessageType type, String text) {
        this.type = type;
        this.text = text;
    }

    public CrudMessageType getType() {
        return type;
    }

    public void setType(CrudMessageType type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    @Override
    public String toString() {
        return "CrudMessage{" +
                "type=" + type +
                ", text='" + text + '\'' +
                '}';
    }
}
