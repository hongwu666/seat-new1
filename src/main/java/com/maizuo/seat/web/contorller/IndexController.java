package com.maizuo.seat.web.contorller;

/**
 * Created by Table on 14-2-31.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.maizuo.seat.service.SeatService;
import com.maizuo.seat.web.render.ViewRender;

@Controller
public class IndexController extends BaseController {

	@Autowired
	private SeatService seatService;

	@RequestMapping(value = { "/{path}/*" }, method = RequestMethod.GET)
	public String login(@PathVariable(value = "path") String path) {
		String bufUrl = request.getPathInfo();
		
		String str = seatService.forwarderServer(bufUrl, request);
		ViewRender.renderXML(str, response);
		return "view/login";
	}
}