package com.safetynet.api.service;

import java.util.List;

import com.safetynet.api.exception.CRUDException;
import com.safetynet.api.exception.CRUDException.ECRUDException;
import com.safetynet.api.repository.CRUDRepository;

public interface CRUDService<T> {
    // Public

    // -- Create --

    default T create(T item) throws CRUDException {
        // The item already exist, cannot create
        if (getRepository().itemExist(item)) {
            throw new CRUDException(ECRUDException.ALREADY_EXIST, item);
        }

        return getRepository().create(item);
    }

    // -- Read --

    default T get(T item) {
        return getRepository().get(item);
    }

    default List<T> getAll() {
        return getRepository().getAll();
    }

    // -- Update --

    default T update(T item, T updateItem) throws CRUDException {
        // The item does not exist, cannot update
        if (!getRepository().itemExist(item)) {
            throw new CRUDException(ECRUDException.DOES_NOT_EXIST, item);
        }

        return getRepository().update(item, updateItem);
    }

    // -- Delete --

    default T delete(T item) throws CRUDException {
        // The item does not exist, cannot delete
        if (!getRepository().itemExist(item)) {
            throw new CRUDException(ECRUDException.DOES_NOT_EXIST, item);
        }

        return getRepository().delete(item);
    }

    // Private

    // -- Overrides --

    CRUDRepository<T> getRepository();

}
