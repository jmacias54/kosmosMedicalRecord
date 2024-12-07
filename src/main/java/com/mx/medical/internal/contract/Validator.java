package com.mx.medical.internal.contract;

public interface Validator<T> {

	boolean isValid(T value);

}
