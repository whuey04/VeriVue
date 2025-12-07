package com.verivue.common.exception;

import com.verivue.model.common.enums.AppHttpCodeEnum;

public class CustomException extends RuntimeException {

  private AppHttpCodeEnum appHttpCodeEnum;

  public CustomException(AppHttpCodeEnum appHttpCodeEnum) {
    this.appHttpCodeEnum = appHttpCodeEnum;
  }

  public AppHttpCodeEnum getAppHttpCodeEnum() {
    return appHttpCodeEnum;
  }
}
