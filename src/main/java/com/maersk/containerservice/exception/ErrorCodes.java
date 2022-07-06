package com.maersk.containerservice.exception;

import lombok.Getter;

@Getter
public enum ErrorCodes {

    UNKNOWN_ERROR("ME1"),
    UNABLE_TO_BOOK("ME2"),
    UNABLE_TO_CHECK_CONTAINER("ME3"),
    VALIDATION_ERROR("ME4"),
    ERROR_HANDLING_REQUEST("ME6"),
    UNABLE_TO_PROCESS_REQUEST("ME7");

    private final String errorCode;

    ErrorCodes(String errorCode) {this.errorCode = errorCode;}
}
