package com.maizuo.seat.util;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import com.maizuo.seat.service.offer.RequestObj;
import com.maizuo.seat.service.offer.RequestUsedSeatObj;
import com.maizuo.seat.service.offer.impl.FirePhenixServiceImpl;

public class Test {

	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, IntrospectionException {
		FirePhenixServiceImpl imp=new FirePhenixServiceImpl();
		imp.getForetell(new RequestObj());
	}
}
