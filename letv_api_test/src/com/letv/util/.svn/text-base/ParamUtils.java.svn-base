package com.letv.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

public class ParamUtils {

	public static String map2Str(Map<String, String> map) {
		StringBuilder sb = new StringBuilder();
		for (String key : map.keySet()) {
			sb.append(key);
			sb.append("=");
			try {
				sb.append(URLEncoder.encode(map.get(key), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			sb.append("&");
		}
		if (!map.isEmpty())
			sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
	
	public final static String generateSignature(Map<String, String> paramMap,
			String secretKey) {
		return DigestUtils.md5Hex(
				generateNormalizedString(paramMap) + secretKey).toLowerCase();
	}
		
	public final static String generateNormalizedString(
			Map<String, String> paramMap) {
		Set<String> params = paramMap.keySet();
		List<String> sortedParams = new ArrayList<String>(params);
		Collections.sort(sortedParams);
		StringBuilder sb = new StringBuilder();
		for (String paramKey : sortedParams) {
			if (paramKey.equalsIgnoreCase("sig"))
				continue;
			sb.append(paramKey)
					.append('=')
					.append(StringUtils.substring(paramMap.get(paramKey), 0, 50));
		}
		return sb.toString();
	}

}
