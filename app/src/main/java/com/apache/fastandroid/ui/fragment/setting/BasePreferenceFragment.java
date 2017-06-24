package com.apache.fastandroid.ui.fragment.setting;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BasePreferenceFragment extends PreferenceFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = super.onCreateView(inflater, container, savedInstanceState);
		
		return rootView;
	}
	
	protected void setListSetting(int value, int hintId, ListPreference listPreference) {
		String[] valueTitleArr = getResources().getStringArray(hintId);
		
		listPreference.setSummary(valueTitleArr[value]);
	}

}
