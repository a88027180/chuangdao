package com.xiyoukeji.tools;

/**
 * Created by Matilda on 2016/12/20.
 */
public enum State {
    SUCCESS(0, "操作成功"),FAIL(1, "操作失败"),EXCEPTION(10001,"系统异常"),UPLOAD_FAIL(10002, "文件上传失败"), USER_NOT_EXIST(10003, "用户不存在"), PASSWORD_ERROR(10004, "密码错误");

    private int value;
    private String desc;

    State(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int value() {
        return this.value;
    }

    public String desc() {
        return this.desc;
    }

}
