package com.tesla.framework.common.setting;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SettingUtility {

	private static Map<String, Setting> settingMap;

	static {
		settingMap = new HashMap<>();
	}

	private SettingUtility() {

	}

	/**
	 * 添加设置配置数据
	 * 
	 * @param settingsXmlName
	 */
	public static void addSettings(Context context, String settingsXmlName) {
		Map<String, Setting> newSettingMap = SettingsXmlParser.parseSettings(context, settingsXmlName);

		Set<String> keySet = newSettingMap.keySet();
		for (String key : keySet)
			settingMap.put(key, newSettingMap.get(key));
	}



	public static String getStringSetting(String type) {
		if (settingMap.containsKey(type))
			return settingMap.get(type).getValue();

		return null;
	}

	public static Setting getSetting(String type) {
		if (settingMap.containsKey(type))
			return settingMap.get(type);

		return null;
	}


}
