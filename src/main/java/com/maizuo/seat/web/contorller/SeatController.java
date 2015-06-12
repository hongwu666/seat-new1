package com.maizuo.seat.web.contorller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 座位相关action
 * 
 * @author Administrator
 * 
 */
@Controller
public class SeatController extends BaseController {
	
	
	/**
	 * 锁座位
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/lock" }, method = RequestMethod.GET)
	public String lock() {

		return "view/index";
	}

	/**
	 * 释放座位
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/unlock" }, method = RequestMethod.GET)
	public String unlock() {

		return "view/index";
	}
}
