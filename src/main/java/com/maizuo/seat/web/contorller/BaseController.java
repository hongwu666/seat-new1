package com.maizuo.seat.web.contorller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.maizuo.seat.doman.Result;

/**
 * Controller的基类 </pre>
 * 
 * @see
 */
public class BaseController {
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	protected ModelMap modelMap;
	protected Map<Object, Object> map;

	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		this.request = request;
		this.response = response;
		this.modelMap = modelMap;
		this.session = request.getSession();
		this.map = new HashMap<Object, Object>();
	}
	/**
	 * 获取一个整形参数
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	protected int getInt(String key, int defaultValue) {
		String value = request.getParameter(key);
		if (StringUtils.isNumeric(value)) {
			return Integer.valueOf(value);
		}
		return defaultValue;
	}
	/**
	 * 获取一个字符串参数
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	protected String getString(String key, String defaultValue) {
		if (this.request.getParameterMap().containsKey(key)) {
			return this.request.getParameter(key);
		} else {
			return defaultValue;
		}
	}

/*	*//**
	 * 获取保存在Session中的用户对象
	 * 
	 * @param request
	 * @return
	 *//*
	protected User getSessionUser(HttpServletRequest request) {
		return (User) request.getSession().getAttribute(Constant.USER_CONTEXT);
	}

	*//**
	 * 保存用户对象到Session中
	 * 
	 * @param request
	 * @param user
	 *//*
	protected void setSessionUser(HttpServletRequest request, User user) {
		User u = new User();
		u.setId1(user.getId1());
		u.setName(user.getName());
		request.getSession().setAttribute(Constant.USER_CONTEXT, u);
	}*/

	public Result result(int status, Object data, String msg) {
		return new Result(status, data, msg);
	}

	public Result result(int status, Object data, String msg, Object extra) {
		return new Result(status, data, msg, extra);
	}

}
