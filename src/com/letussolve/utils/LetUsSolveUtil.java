package com.letussolve.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class LetUsSolveUtil {
	private static PasswordHasher hasher;
	private static Gson gson;
	static {
		hasher = new PasswordHasher();
		gson = new Gson();
	}
	private LetUsSolveUtil() {}
	
	public static String getDateString(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(date);
	}
	
	public static String hashPassword(String password) {
		return hasher.createHash(password);
	}

	public static boolean isPasswordMatch(String password, String storedHash) {
		if (isNull(password) || isNull(storedHash))
			return false;
		return hasher.isMatch(password, storedHash);
	}

	private static boolean isNull(String str) {
		return null == str || str.isEmpty();
	}
	
	public static String toJson(Object obj) {
		return gson.toJson(obj);
	}
	
	public static String fromJson(JsonElement jsonData, Class classType) {
		return gson.fromJson(jsonData, classType);		
	}
	
	public static Map<String,String> getKeyValFromFormData(ServletInputStream in) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String formData = br.readLine();
		System.out.println("FormData: " + formData);
		formData = formData.replace("+", " ");
		System.out.println("Replacing `+` with ` `" + formData);
		String[] formDataParts = formData.split("[&=]");
		Map<String, String> formDataMap = new HashMap<>();
		for (int i=0; i < formDataParts.length; i += 2) {
			formDataMap.put(formDataParts[i], formDataParts[i+1]);
		}
		return formDataMap;
	}
	
	public static String fetchKeyVal(Map<String, String> formData, String key) {
		return formData.get(key);
	}
	
	public static boolean isAjax(HttpServletRequest request) {
		return "XMLHttpRequest".equals(
                request.getHeader("X-Requested-With"));
	}
	
	public static List<Object> fromJson(String json, Class classOfT) {
		try {
			Object [] obj = gson.fromJson(json, classOfT);
			List<Object> objList = Arrays.asList(obj);
			return objList;
		} catch (Exception e) {
			System.out.println("Exception Catched while parsing json.");
		}
		return null;
	}
}
