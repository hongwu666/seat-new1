package com.maizuo.seat.web.contorller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 影片相关action
 * 
 * @author Administrator
 * 
 */
@Controller
public class MovieController extends BaseController {

	/**
	 * 拉取具体影片信息
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/movie" }, method = RequestMethod.GET)
	public String movie() {

		return "view/index";
	}

}
