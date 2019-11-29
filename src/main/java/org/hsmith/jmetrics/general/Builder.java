package org.hsmith.jmetrics.general;

/**
 * General builder interface.
 * @param <TTypeToBuild> Type of object to build.
 */
public interface Builder<TTypeToBuild> {
    /**
     * Build a new instance.
     */
    TTypeToBuild build();
}
