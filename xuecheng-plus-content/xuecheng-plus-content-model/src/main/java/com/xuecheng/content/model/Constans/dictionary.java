package com.xuecheng.content.model.Constans;

/**
 * @Author Luffy5522
 * @date: 2023/3/8 20:32
 * @description: 课程审核状态的枚举类
 */
public enum dictionary {

    PRICE_STATUS_PAY("201001", "收费"),
    PRICE_STATUS_FREE("201000", "免费"),

    MODEL_STATUS_RECORD("200002", "录播"),
    MODEL_STATUS_LIVE("200003", "直播"),

    STATUS_OUT_PUBLISH("203001", "未发布"),
    STATUS_PUBLISH("203002", "已发布"),
    STATUS_OFFLINE("203003", "下线"),

    AUDIT_STATUS_NOT_PASS("202001", "审核未通过"),
    AUDIT_STATUS_NOT_COMMIT("202002", "未提交"),
    AUDIT_STATUS_COMMIT("202003", "已提交"),
    AUDIT_STATUS_PASS("202004", "审核通过");


    private final String code;
    private final String describe;

    dictionary(String code, String describe) {
        this.code = code;
        this.describe = describe;
    }


    public String getCode() {
        return code;
    }

    public String getDescribe() {
        return describe;
    }
}
