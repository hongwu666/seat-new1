package com.maizuo.seat.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Service;

import com.maizuo.seat.service.offer.OfferConfig;
import com.maizuo.seat.service.offer.OfferService;

@Service
public class OfferServiceFactory implements BeanFactoryAware {

	private BeanFactory beanFactory;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	public OfferService getBean(int offerId) {
		return getBean(String.valueOf(offerId));
	}

	public OfferService getBean(String beanName) {
		return beanFactory.getBean(OfferConfig.ins().getOfferName(beanName), OfferService.class);
	}
}
