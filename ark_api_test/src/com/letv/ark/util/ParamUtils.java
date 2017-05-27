package com.letv.ark.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class ParamUtils {

	public static String map2Str(Map<String, String> map) {
		StringBuilder sb = new StringBuilder();
		for (String key : map.keySet()) {
			sb.append(key);
			sb.append("=");
			sb.append(map.get(key));
/*			try {
				sb.append(URLEncoder.encode(map.get(key), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}*/

			sb.append("&");
		}
		if (!map.isEmpty())
			sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

}
