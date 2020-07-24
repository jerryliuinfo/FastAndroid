package com.apache.fastandroid;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import com.tesla.framework.support.annotation.IProxy;
import java.lang.Override;

public class MainActivity$$Proxy implements IProxy<MainActivity> {
  @Override
  public void inject(final MainActivity target, View root) {
    target.mNavigationView = (NavigationView)(root.findViewById(2131296490));
    target.mDrawerLayout = (DrawerLayout)(root.findViewById(2131296371));
  }
}
