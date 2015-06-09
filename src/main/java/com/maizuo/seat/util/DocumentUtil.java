package com.maizuo.seat.util;

import org.dom4j.Element;

public class DocumentUtil {
	public static String getVlaue(Element element,String key){
		return element.attributeValue(key);
	}
	
	
	/*
	public final static String ROOT_NODE_NAME = "Result";
	private Element root = null;
	private boolean isViewLog = true;
	private Document document = null;
	private int logId;

	public void setLogId(int logId) {
		this.logId = logId;
	}

	public void setViewLog(boolean isViewLog) {
		this.isViewLog = isViewLog;
	}

	public Document setDocumentStartPot() {
		document = DocumentHelper.createDocument();
		document.setXMLEncoding("utf-8");
		root = document.addElement(ROOT_NODE_NAME);
		return document;
	}

	public void setRootElemnt(String key, String value) {
		this.root.addElement(key).addText(value);
	}

	public void setRootElemnt(Element e) {

	}

	public void setRootElemnt(Element e, String parentELEStr) {
		this.root.addElement(parentELEStr).appendContent(e);
	}

	public void setRootElemnt(String key, int value) {
		this.root.addElement(key).addText(Integer.toString(value));
	}

	public Element getRootElement() {
		return root;
	}

	public void writeDocument() {
		this.writeDocument(true, false, "");
	}

	public void writeDocument(boolean isError) {
		this.writeDocument(true, isError, "");
	}

	public void writeDocument(boolean isError, String errorType) {
		this.writeDocument(true, isError, errorType);
	}

	public void writeDocument(boolean isShow, boolean isError, String errorType) {

		this.isViewLog = isShow;
		HttpServletResponse response = ServletActionContext.getResponse();
		if (response == null) {
			return;
		}
		response.setContentType("text/xml; charset=utf-8");
		try {
			Utils.writeXml(document, response.getOutputStream());
			writeResponseLog(document.asXML(), isError, !errorType.equals(Constants.BASE_ERROR));
		} catch (Exception e) {
			e.printStackTrace();
			BaseAction.logPrint("WRITE RESPONSE ERROR!", this.isViewLog);
		}
	}

	public void writeText(Object msg) {
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			response.getWriter().print(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void write(String method, String orderSeq, String msg, boolean writeLog) {

		if (writeLog) {
			MaizuoLogUtil.writeLog(1, method, "", msg);
		} else {
			// BaseAction.logPrint(msg);
		}
	}

	public void write(String orderSeq, String msg) {
		write("SkyStar_Check_Group", orderSeq, msg, false);
	}

	// 订座中心请求到第三方
	public void writeRequest2ThirdPart(int logId, String offer, String interfaceUri, String url) {
		BaseAction.logPrint("订座中心请求第三方[" + logId + "]：" + url, this.isViewLog);
		MaizuoLogUtil.writeLog(interfaceUri, url, Integer.toString(logId) + ",RequestToThirdParty(offerId=" + offer + ")");
	}

	// 第三方返回结果
	public void writeThirdPartResponse(int logId, String offer, boolean isShowLog, String url, String interfaceURI, String result) {
		if (result.length() > 600) {
			result = result.substring(0, 600) + "...";
		}
		BaseAction.logPrint("第三方返回结果[" + logId + "]：" + result, this.isViewLog);
		MaizuoLogUtil.writeLog(url, interfaceURI + "_3p_back", result, Integer.toString(logId) + ",RequestAndResponse(offerId=" + offer + ")");
	}

	// 订座中心接收到请求
	public void writeRequestLog(int logId, String interfaceUri, String url) {
		BaseAction.logPrint("订座中心接收到请求[" + logId + "]：" + url, this.isViewLog);
		MaizuoLogUtil.writeLog(interfaceUri, url, Integer.toString(logId) + ",RequestSelf");
	}

	// 订座中心返回的结果
	public void writeResponseLog(String result, boolean isError) {
		writeResponseLog(result, isError, true);
	}

	// 订座中心返回的结果
	public void writeResponseLog(String result, boolean isError, boolean isAlarm) {
		HttpServletRequest request = ServletActionContext.getRequest();
		String requestURI = "", interfaceURI = "";
		if (request != null) {
			String url = request.getRequestURL().toString();
			interfaceURI = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
			requestURI = url + "?" + request.getQueryString();
		}
		int si = result.indexOf("SUCCESS");
		if (si > 0) {
			isError = false;
		}
		if (result.length() > 300 && !isError) {
			result = result.substring(0, 300) + "...";
		}
		BaseAction.logPrint("订座中心返回结果[" + logId + "]：" + result, this.isViewLog);
		MaizuoLogUtil.writeLog((isError && isAlarm) ? 1 : 0, requestURI, interfaceURI + "_back", result, Integer.toString(logId) + ",RequestAndResponse,"
				+ (isError ? "ERROR" : "SUCC"));
	}
*/}
