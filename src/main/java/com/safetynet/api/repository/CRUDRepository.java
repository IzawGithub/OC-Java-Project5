package com.safetynet.api.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

public interface CRUDRepository<T> {
    // Public

    // -- Create --

    default T create(T item) {
        getMap().put(item.hashCode(), item);
        return item;
    }

    // -- Read --

    default T get(T item) {
        return getMap().get(item.hashCode());
    }

    default List<T> getAll() {
        return getMap()
                .entrySet()
                .stream()
                .map(Entry::getValue)
                .collect(Collectors.toList());
    }

    default boolean itemExist(T item) {
        return getMap().containsKey(item.hashCode());
    }

    // -- Update --

    default T update(T item, T updateItem) {
        this.delete(item);
        return this.create(updateItem);
    }

    // -- Delete --

    default T delete(T item) {
        getMap().remove(item.hashCode());
        return item;
    }

    // Private

    default Map<Integer, T> initData(List<T> arrayItem) {
        if (Objects.isNull(arrayItem)) {
            return new HashMap<>();
        }

        return arrayItem.stream()
                .collect(Collectors.toMap(T::hashCode, item -> item));
    }

    // -- Overrides --

    Map<Integer, T> getMap();
}
