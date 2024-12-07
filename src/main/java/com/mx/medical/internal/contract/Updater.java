package com.mx.medical.internal.contract;

public interface Updater<ToUpdate, Input> {

	ToUpdate update(ToUpdate entity, Input data);

}

