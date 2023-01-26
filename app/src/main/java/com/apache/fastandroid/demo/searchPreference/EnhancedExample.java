package com.apache.fastandroid.demo.searchPreference;

import android.os.Bundle;
import android.os.Handler;

import com.apache.fastandroid.R;
import com.bytehamster.lib.preferencesearch.SearchConfiguration;
import com.bytehamster.lib.preferencesearch.SearchPreference;
import com.bytehamster.lib.preferencesearch.SearchPreferenceResult;
import com.bytehamster.lib.preferencesearch.SearchPreferenceResultListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

/**
 * This file demonstrates some additional features that might not be needed when setting it up for the first time
 */
public class EnhancedExample extends AppCompatActivity implements SearchPreferenceResultListener {
    private PrefsFragment prefsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        prefsFragment = new PrefsFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, prefsFragment).commit();
    }

    @Override
    public void onSearchResultClicked(@NonNull final SearchPreferenceResult result) {
        prefsFragment = new PrefsFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, prefsFragment).addToBackStack("PrefsFragment")
                .commit(); // Allow to navigate back to search

        new Handler().post(new Runnable() { // Allow fragment to get created
            @Override
            public void run() {
                prefsFragment.onSearchResultClicked(result);
            }
        });
    }

    public static class PrefsFragment extends PreferenceFragmentCompat {
        SearchPreference searchPreference;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            addPreferencesFromResource(R.xml.search_preferences);

            searchPreference = (SearchPreference) findPreference("searchPreference");
            SearchConfiguration config = searchPreference.getSearchConfiguration();
            config.setActivity((AppCompatActivity) getActivity());
            config.setFragmentContainerViewId(android.R.id.content);

            config.index(R.xml.search_preferences).addBreadcrumb("Main file");
            config.index(R.xml.preferences2).addBreadcrumb("Second file");
            config.setBreadcrumbsEnabled(true);
            config.setHistoryEnabled(true);
            config.setFuzzySearchEnabled(true);
        }

        private void onSearchResultClicked(SearchPreferenceResult result) {
            if (result.getResourceFile() == R.xml.search_preferences) {
                searchPreference.setVisible(false); // Do not allow to click search multiple times
                scrollToPreference(result.getKey());
                findPreference(result.getKey()).setTitle("RESULT: " + findPreference(result.getKey()).getTitle());
            } else {
                // Result was found in the other file
                getPreferenceScreen().removeAll();
                addPreferencesFromResource(R.xml.preferences2);
                result.highlight(this);
            }
        }
    }
}
