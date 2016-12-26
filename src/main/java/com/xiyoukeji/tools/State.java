package com.xiyoukeji.tools;

/**
 * Created by Matilda on 2016/12/20.
 */
public enum State {
    SUCCESS(1),FAIL(10000),EXCEPTION(10001),FILE_EMPTY(10002);

    private int value;

    State(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }


}
