package org.hsmith.jmetrics.general.impl;

public abstract class BuilderBase {

    protected final void assertNotNull(final Object variable, final String variableName) {
        if (variable == null) {
            if (variableName == null || variableName.isEmpty()) {
                throw new IllegalArgumentException("Variable must not be null");
            } else {
                throw new IllegalArgumentException(String.format("Variable '%s' must not be null", variableName));
            }
        }
    }

    protected final void assertNotNull(final Object variable) {
        assertNotNull(variable, "");
    }

    protected final void assertNumberBetween(final Number variable,
                                             final String variableName,
                                             final Number lowerBound,
                                             final Number upperBound) {

        assertNotNull(variable, variableName);
        assertNotNull(lowerBound, "lowerBound");
        assertNotNull(upperBound, "upperBound");

        if (variable.doubleValue() < lowerBound.doubleValue() || variable.doubleValue() > upperBound.doubleValue()) {
            if (variableName == null || variableName.isEmpty()) {
                throw new IllegalArgumentException(
                        String.format("Number variable must be between %.2f and %.2f (value = %.2f)",
                                lowerBound.doubleValue(),
                                upperBound.doubleValue(),
                                variable.doubleValue()));
            } else {
                throw new IllegalArgumentException(
                        String.format("Number variable '%s' must be between %.2f and %.2f (value = %.2f)",
                                variableName,
                                lowerBound.doubleValue(),
                                upperBound.doubleValue(),
                                variable.doubleValue()));
            }
        }
    }

    protected final void assertPositiveNumber(final Number variable, final String variableName) {
        assertNumberBetween(variable, variableName, 0, Double.MAX_VALUE);
    }

    protected final void assertPositiveNumber(final Number variable) {
        assertPositiveNumber(variable, "");
    }
}
