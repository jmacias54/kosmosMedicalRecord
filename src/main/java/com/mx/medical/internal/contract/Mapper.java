package com.mx.medical.internal.contract;

public interface Mapper<From, To> {

	To map(From input);

}


