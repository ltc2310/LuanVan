package vn.home.com.model;

/**
 * Created by HUUTHIENDEV on 10/9/2016.
 */

public class Distance {
    private String text;
    private int value;

    public Distance(String text, int value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
