package com.maizuo.seat.web.render;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.maizuo.seat.util.FastJsonUtils;

public class ViewRender {
	@ModelAttribute
	private static void render(String text, String contentType, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			request.setCharacterEncoding("UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType(contentType);

			out = response.getWriter();
			out.write(text);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * 直接输出纯字符串.
	 */
	public static void renderText(String text, HttpServletResponse response) {
		render(text, "text/plain;charset=UTF-8", response);
	}

	/**
	 * 直接输出纯字符串_对参数是Byte类型的
	 */
	public static void renderText(byte text, HttpServletResponse response) {
		render(String.valueOf(text), "text/plain;charset=UTF-8", response);
	}

	/**
	 * 直接输出纯字符串_对参数是Int类型的
	 */
	public static void renderText(int text, HttpServletResponse response) {
		render(String.valueOf(text), "text/plain;charset=UTF-8", response);
	}

	/**
	 * 直接输出纯HTML.
	 */
	public static void renderHtml(String text, HttpServletResponse response) {
		render(text, "text/html;charset=UTF-8", response);
	}

	/**
	 * 直接输出纯XML.
	 */
	public static void renderXML(String text, HttpServletResponse response) {
		render(text, "text/xml;charset=UTF-8", response);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param string
	 *            json字符串.
	 * @see #render(HttpServletResponse, String,String)
	 */
	public static void renderJson(String text, HttpServletResponse response) {
		render(text, "text/x-json;charset=UTF-8", response);
	}

	/**
	 * (将对象解析成JSON字符串)直接输出JSON.
	 * 
	 */
	public static void renderJson(Object obj, HttpServletResponse response) {

		String text = FastJsonUtils.toJson(obj);
		render(text, "text/x-json;charset=UTF-8", response);
	}

	public static void writeXml(Document doc, OutputStream out) {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("utf-8");
		XMLWriter writer;
		try {
			writer = new XMLWriter(out, format);
			writer.write(doc);
			out.flush();
			out.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
