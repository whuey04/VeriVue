package com.verivue.model.common.enums;

public enum AppHttpCodeEnum {

    SUCCESS(200, "Success"),

    NEED_LOGIN(1,"Please login"),
    LOGIN_PASSWORD_ERROR(2,"Password Error"),
    ACCOUNT_LOCKED(3, "Account Locked"),

    TOKEN_INVALID(50,"Invalid Token"),
    TOKEN_EXPIRE(51,"Token is expired"),
    TOKEN_REQUIRE(52,"token is required"),

    SIGN_INVALID(100,"Invalid sign"),
    SIG_TIMEOUT(101,"Sign is timeout/expired"),

    PARAM_REQUIRE(500,"Missing required parameter"),
    PARAM_INVALID(501,"Invalid parameter"),
    PARAM_IMAGE_FORMAT_ERROR(502,"Format of image is error"),
    SERVER_ERROR(503,"Server error"),

    DATA_EXIST(1000,"Data already exists"),
    AP_USER_DATA_NOT_EXIST(1001,"ApUser data does not exists"),
    DATA_NOT_EXIST(1002,"Data does not exist"),

    NO_OPERATOR_AUTH(3000,"No permission to operate"),
    NEED_ADMIND(3001,"Administrator privileges required"),
    MATERIALS_REFERENCE_FAIL(3501, "Invalid material reference" );

    int code;
    String errorMsg;

    AppHttpCodeEnum(int code, String errorMsg) {
        this.code = code;
        this.errorMsg = errorMsg;
    }

    public int getCode() {
        return code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

}
