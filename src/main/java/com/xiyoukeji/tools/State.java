package com.xiyoukeji.tools;

/**
 * Created by Matilda on 2016/12/20.
 */
public enum State {
    SUCCESS(0, "操作成功"),FAIL(1, "操作失败"),EXCEPTION(10001,"系统异常"),UPLOAD_FAIL(10002, "文件上传失败"),
    USER_NOT_EXIST(10003, "账号不存在"), USERNAME_USED(10004, "用户名已被使用"),PASSWORD_ERROR(10005, "用户密码错误"),
    NO_PERMISSION(10006, "用户权限不足");

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
