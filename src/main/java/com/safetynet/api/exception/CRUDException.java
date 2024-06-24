package com.safetynet.api.exception;

import java.text.MessageFormat;
import java.util.Objects;

public class CRUDException extends Exception {
    public enum ECRUDException {
        ALREADY_EXIST,
        DOES_NOT_EXIST;
    }

    public CRUDException(ECRUDException exception, Object item) {
        super(MessageFormat.format("CRUDException: ''{0}'' (''{1}'')", exception, item));
        this.enumException = exception;
    }

    @Override
    public boolean equals(Object obj) {
        if (Objects.isNull(obj))
        {
            return false;
        }
        if (this.getClass() != obj.getClass())
        {
            return false;
        }

        final var other = (CRUDException) obj;
        return this.getMessage().equals(other.getMessage());
    }
    @Override
    public int hashCode() {
        return enumException.hashCode();
    }

    final ECRUDException enumException;

    public ECRUDException getEnumException() {
        return enumException;
    }
}
