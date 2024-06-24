package com.safetynet.api.helper;

import java.util.stream.Collectors;

public interface IStackLog {
    public default String getCurrentMethod() {
        return StackWalker.getInstance()
                .walk(monad -> monad.limit(2).collect(Collectors.toList())).get(1).toString();
    }
}
