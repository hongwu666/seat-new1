package com.maizuo.seat.web.contorller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 排期相关action
 * 
 * @author Administrator
 * 
 */
@Controller
public class ForetellController extends BaseController {

	/**
	 * 拉取排期列表
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/foretells" }, method = RequestMethod.GET)
	public String foretells() {

		return "view/index";
	}

	/**
	 * 拉取指定排期座位列表
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/foretell/seat" }, method = RequestMethod.GET)
	public String hallSeats() {

		return "view/index";
	}
}
