package com.maizuo.seat.web.contorller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 影厅相关action
 * 
 * @author Administrator
 * 
 */
@Controller
public class HallController extends BaseController {

	/**
	 * 拉取影厅列表
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/halls" }, method = RequestMethod.GET)
	public String halls() {
		
		return "view/index";
	}

}
