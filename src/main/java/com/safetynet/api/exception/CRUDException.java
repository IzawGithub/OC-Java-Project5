package com.safetynet.api.exception;

import java.text.MessageFormat;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class CRUDException extends Exception {
    public enum ECRUDException {
        ALREADY_EXIST,
        DOES_NOT_EXIST;
    }

    public CRUDException(ECRUDException exception, Object item) {
        super(MessageFormat.format("CRUDException: ''{0}'' (''{1}'')", exception, item));
        this.enumException = exception;
    }

    final ECRUDException enumException;

    public ECRUDException getEnumException() {
        return enumException;
    }
}
