import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.math.BigInteger;

public class Test {
	public static void main(String[] args) {

		// Map<String,String> map=new HashMap<String, String>();
		// map.put(key, value);
		// String
		// s=UrlRequestUtils.execute("http://localhost:8080/seat-new1/cinemas.htm"
		// , null, "get");
		/*
		 * System.out.println(s); User user = new User();
		 * System.out.println(bean2Json(user));
		 */
		/*
		 * User user = new User(); user.setAge(2); user.setHigh(2); long start1
		 * = System.currentTimeMillis(); FastJsonUtils.toJson(user); //
		 * System.out.println(+"asds"); long start2 =
		 * System.currentTimeMillis(); System.out.println(start2 - start1 +
		 * "sdasd"); //bean2Json(user); // System.out.println(); long start3 =
		 * System.currentTimeMillis(); System.out.println(start3 - start2);
		 */
	}

	@SuppressWarnings("unused")
	private static String bean2Json(Object bean) {
		StringBuilder json = new StringBuilder();
		json.append("{");
		PropertyDescriptor[] props = null;
		try {
			props = Introspector.getBeanInfo(bean.getClass(), Object.class).getPropertyDescriptors();
		} catch (IntrospectionException e) {
		}
		if (props != null) {
			for (int i = 0; i < props.length; i++) {
				try {
					String name = object2Json(props[i].getName());
					String value = object2Json(props[i].getReadMethod().invoke(bean));
					json.append(name);
					json.append(":");
					json.append(value);
					json.append(",");
				} catch (Exception e) {
				}
			}
			json.setCharAt(json.length() - 1, '}');
		} else {
			json.append("}");
		}
		return json.toString();
	}

	private static String object2Json(Object obj) {
		StringBuilder json = new StringBuilder();
		if (obj == null) {
			json.append("\"\"");
		} else if (obj instanceof String || obj instanceof Integer || obj instanceof Float || obj instanceof Boolean || obj instanceof Short || obj instanceof Double
				|| obj instanceof Long || obj instanceof BigDecimal || obj instanceof BigInteger || obj instanceof Byte) {
			json.append("\"").append(string2Json(obj.toString())).append("\"");
		}

		return json.toString();
	}

	private static String string2Json(String s) {
		if (s == null)
			return "";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			switch (ch) {
			case '"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '/':
				sb.append("\\/");
				break;
			default:
				if (ch >= '\u0000' && ch <= '\u001F') {
					String ss = Integer.toHexString(ch);
					sb.append("\\u");
					for (int k = 0; k < 4 - ss.length(); k++) {
						sb.append('0');
					}
					sb.append(ss.toUpperCase());
				} else {
					sb.append(ch);
				}
			}
		}
		return sb.toString();
	}
}
