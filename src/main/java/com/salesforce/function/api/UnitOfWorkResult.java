package com.salesforce.function.api;

import java.util.List;

import javax.annotation.Nonnull;

/**
 * A subresult returned {@link UnitOfWork#commit()}. There is a 1:1 correspondence between this result and previous
 * invocations of a {@link UnitOfwork} register* method. 
 *
 * @since 218
 */
public interface UnitOfWorkResult {
    /**
     * @return the method that was used to perform unit of work operation
     */
    Method getMethod();

    /**
     * @return the id of a newly created object. This may return null in cases where the object wasn't inserted.
     */
    String getId();

    /**
     * @return true if the operation succeeded, else false.
     */
    boolean isSuccess();

    /**
     * @return any errors if the operation failed.
     */
    @Nonnull List<String> getErrors();
}
