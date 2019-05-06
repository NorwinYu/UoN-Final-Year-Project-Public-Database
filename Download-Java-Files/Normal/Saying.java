package com.msproject.myhome.mydays;

public class Saying {
    String say;
    String source;

    public Saying(String say, String source) {
        this.say = say;
        this.source = source;
    }

    public String getSay() {
        return say;
    }

    public void setSay(String say) {
        this.say = say;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
