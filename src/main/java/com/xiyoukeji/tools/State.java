package com.xiyoukeji.tools;

/**
 * Created by Matilda on 2016/12/20.
 */
public enum State {
    FAIL(0),SUCCESS(1),FILE_EMPTY(10001);

    private int value;

    State(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }


}
