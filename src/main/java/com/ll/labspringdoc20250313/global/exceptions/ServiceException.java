package com.ll.labspringdoc20250313.global.exceptions;

import com.ll.labspringdoc20250313.global.rsData.RsData;

public class ServiceException extends RuntimeException {
    private final String resultCode;
    private final String msg;

    public ServiceException(String resultCode, String msg) {
        super(resultCode + " : " + msg);
        this.resultCode = resultCode;
        this.msg = msg;
    }

    public RsData<Void> getRsData() {
        return new RsData<>(resultCode, msg);
    }
}
