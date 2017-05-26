/*
 * Copyright (c) 2012-2015 TCL Inc. All Rights Reserved.
 * 
 * FileName: HostNameResolver.java
 * 
 * Description: 域名解析辅助类文件
 * 
 * History:
 * 1.0	devilxie	2012-09-05	Create
 */
package com.tesla.framework.common.util.network;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

/**
 * 域名解析辅助类，优先于系统的域名解析规则，有利于开发阶段进行调试
 * 
 * @author devilxie
 * @version 1.0
 */
public class HostNameResolver
{
	private final static String HOST_CONF_FILE = "hosts";
	/* 是否开启URL自定义域名解析 */
	public static boolean				NEED_HOST_RESOVLING	= false;
	
	/* 自定义域名映射集合 */
	private static Map<String, String>	hostMap				= null;
	
	public static void loadHostConfiguration(String hostsPath)
	{
		File sd = Environment.getExternalStorageDirectory();
		if (sd == null || !sd.exists())
			return;
		
		StringBuilder sb = new StringBuilder(sd.getAbsolutePath());
		sb.append(File.separator);
		if (TextUtils.isEmpty(hostsPath))
			sb.append(HOST_CONF_FILE);
		else {
			sb.append(hostsPath);
		}
		
		String path = sb.toString();
		File file = new File(path);
		if (!file.exists() || file.length() == 0)
			return;
		
		InputStreamReader reader = null;
		try {
			reader = new InputStreamReader(new FileInputStream(file), "utf-8");
			Properties properties = new Properties();			
			properties.load(reader);
			
			if (properties.size() == 0)
				return;
			
			Set<Entry<Object, Object>> entries = properties.entrySet();
			for (Entry<Object, Object> entry: entries) {
				String value = entry.getValue().toString();
				if (TextUtils.isEmpty(value))
					continue;
				
				addNameMap(entry.getKey().toString(), value);
			}
			
		} catch (UnsupportedEncodingException e) {
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
		}
		
	}
	
	public static void clear() {
		if (hostMap != null)
		{
			hostMap.clear();
			hostMap = null;
		}
	}
	
	/**
	 * 添加服务器域名与IP的映射关系元素
	 * @param server 服务器域名
	 * @param ip ip地址
	 */
	public static void addNameMap(String server, String ip)
	{
		if (hostMap == null)
		{
			hostMap = new HashMap<String, String>();
		}

		String sip = hostMap.get(server);
		if (sip == null)
			hostMap.put(server, ip);
		else {
			sip += ";" + ip;
			hostMap.put(server, sip);
		}
	}
	
	/**
	 * 解析指定的服务器域名为ip地址
	 * @param server 服务器域名
	 * @return 返回对应域名的ip，如果无法找到相应解析，将返回原服务器域名
	 */
	public static String resovleHost(String server)
	{
		// 没有映射时，直接返回
		if (!NEED_HOST_RESOVLING || hostMap == null || hostMap.size() == 0)
		{
			return server;
		}

		String ip = hostMap.get(server);
		if (ip == null)
			return server;
		
		String[] ips = ip.split(";");	
		int index = (int)(Math.random() * 1000000) % ips.length;
		return ips[index];
	}
	
	/**
	 * 解析指定URI中服务器域名，并替换成指定IP，如果无法解析，将返回原URI
	 * @param url 指定URI
	 * @return 返回解析替换后的URI
	 */
	public static String resovleURL(String url)
	{
		if (!NEED_HOST_RESOVLING || hostMap == null || hostMap.size() == 0)
		{
			return url;
		}

		String realUrl = url;
		try
		{
			URL u = new URL(url);
			String host = u.getHost();
			String ip = hostMap.get(host);
			if (ip != null)
			{				
				String[] ips = ip.split(";");
				int index = (int)(Math.random() * 1000000) % ips.length;
				
				realUrl = url.replace(host, ips[index]);
			}
		}
		catch (MalformedURLException e)
		{

		}

		return realUrl;
	}
}
