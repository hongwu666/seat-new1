package com.maizuo.seat.web.contorller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maizuo.seat.bo.xml.CinemaBO;
import com.maizuo.seat.service.CinemaService;

/**
 * 影院相关action
 * 
 * @author Administrator
 * 
 */
@Controller
public class CinemaController extends BaseController {
	@Autowired
	private CinemaService cinemaService;


	/**
	 * 拉取影院及城市列表
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/cinemas" }, method = RequestMethod.GET)
	@ResponseBody
	public CinemaBO cinemas() {
		int thirdId = this.getInt("thirdId", 1);
		String datetime = this.getString("datetime", "");
		String sign = this.getString("sign", "");
		CinemaBO coffee = cinemaService.getCinemas(thirdId, datetime, sign);
		return coffee;
	}
	/**
	 * 拉取具体影院信息
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/cinema" }, method = RequestMethod.GET)
	public String cinema() {

		return "view/index";
	}
}
