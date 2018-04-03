package com.tesla.framework.ui.fragment.adpater;

public interface FragmentPagerChangeListener {

	void instantiate(String fragmentName);
	
	void destroy(String fragmentName);
	
}
