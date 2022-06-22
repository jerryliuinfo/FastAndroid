// SPDX-License-Identifier: GPL-3.0-or-later

package com.apache.fastandroid.demo.widget;

import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apache.fastandroid.MainActivity;
import com.apache.fastandroid.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

public class ListOptions extends BottomSheetDialogFragment {
    public static final String TAG = ListOptions.class.getSimpleName();

    @IntDef(value = {
            SORT_BY_DOMAIN,
            SORT_BY_APP_LABEL,
            SORT_BY_PACKAGE_NAME,
            SORT_BY_LAST_UPDATE,
            SORT_BY_SHARED_ID,
            SORT_BY_TARGET_SDK,
            SORT_BY_SHA,
            SORT_BY_DISABLED_APP,
            SORT_BY_BLOCKED_COMPONENTS,
            SORT_BY_BACKUP,
            SORT_BY_TRACKERS,
            SORT_BY_LAST_ACTION,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface SortOrder {
    }

    public static final int SORT_BY_DOMAIN = 0;  // User/system app
    public static final int SORT_BY_APP_LABEL = 1;
    public static final int SORT_BY_PACKAGE_NAME = 2;
    public static final int SORT_BY_LAST_UPDATE = 3;
    public static final int SORT_BY_SHARED_ID = 4;
    public static final int SORT_BY_TARGET_SDK = 5;
    public static final int SORT_BY_SHA = 6;  // Signature
    public static final int SORT_BY_DISABLED_APP = 7;
    public static final int SORT_BY_BLOCKED_COMPONENTS = 8;
    public static final int SORT_BY_BACKUP = 9;
    public static final int SORT_BY_TRACKERS = 10;
    public static final int SORT_BY_LAST_ACTION = 11;

    @IntDef(flag = true, value = {
            FILTER_NO_FILTER,
            FILTER_USER_APPS,
            FILTER_SYSTEM_APPS,
            FILTER_DISABLED_APPS,
            FILTER_APPS_WITH_RULES,
            FILTER_APPS_WITH_ACTIVITIES,
            FILTER_APPS_WITH_BACKUPS,
            FILTER_RUNNING_APPS,
            FILTER_APPS_WITH_SPLITS,
            FILTER_INSTALLED_APPS,
            FILTER_UNINSTALLED_APPS,
            FILTER_APPS_WITHOUT_BACKUPS,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Filter {
    }

    public static final int FILTER_NO_FILTER = 0;
    public static final int FILTER_USER_APPS = 1;
    public static final int FILTER_SYSTEM_APPS = 1 << 1;
    public static final int FILTER_DISABLED_APPS = 1 << 2;
    public static final int FILTER_APPS_WITH_RULES = 1 << 3;
    public static final int FILTER_APPS_WITH_ACTIVITIES = 1 << 4;
    public static final int FILTER_APPS_WITH_BACKUPS = 1 << 5;
    public static final int FILTER_RUNNING_APPS = 1 << 6;
    public static final int FILTER_APPS_WITH_SPLITS = 1 << 7;
    public static final int FILTER_INSTALLED_APPS = 1 << 8;
    public static final int FILTER_UNINSTALLED_APPS = 1 << 9;
    public static final int FILTER_APPS_WITHOUT_BACKUPS = 1 << 10;

    private static final int[] SORT_ITEMS_MAP = {R.string.sort_by_domain, R.string.sort_by_app_label,
            R.string.sort_by_package_name};
    private static final SparseIntArray FILTER_MAP = new SparseIntArray() {{
        put(FILTER_USER_APPS, R.string.filter_user_apps);
        put(FILTER_SYSTEM_APPS, R.string.filter_system_apps);
        put(FILTER_DISABLED_APPS, R.string.filter_disabled_apps);

    }};

    private final List<String> profileNames = new ArrayList<>();
    private ChipGroup sortGroup;
    private ViewGroup filterView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_list_options, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) requireActivity();
        sortGroup = view.findViewById(R.id.sort_options);
        MaterialAutoCompleteTextView profileNameInput = view.findViewById(android.R.id.input);

        // Add sorting indices
        for (int i = 0; i < SORT_ITEMS_MAP.length; ++i) {
            sortGroup.addView(getRadioChip(i, SORT_ITEMS_MAP[i]), i);
        }
        sortGroup.setOnCheckedChangeListener((group, checkedId) -> {

        });

        // Add filters
        filterView = view.findViewById(R.id.filter_options);
        for (int i = 0; i < FILTER_MAP.size(); ++i) {
            filterView.addView(getFilterChip(FILTER_MAP.keyAt(i), FILTER_MAP.valueAt(i)));
        }
    }

    public Chip getFilterChip(@Filter int flag, @StringRes int strRes) {
        Chip chip = new Chip(filterView.getContext());
        chip.setCloseIconVisible(false);
        chip.setText(strRes);
//        chip.setChecked(model.hasFilterFlag(flag));
        chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) model.addFilterFlag(flag);
//            else model.removeFilterFlag(flag);
        });
        return chip;
    }

    public Chip getRadioChip(@SortOrder int sortOrder, @StringRes int strRes) {
        Chip chip = new Chip(sortGroup.getContext());
        chip.setCloseIconVisible(true);
        chip.setId(sortOrder);
        chip.setText(strRes);
        return chip;
    }
}
