/*
 * Copyright (C) 2018 NucleaRom
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nr.settings.tabs;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.content.Context;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.Vibrator;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.support.v14.preference.SwitchPreference;
import android.provider.Settings;

import com.android.settings.R;

import com.android.settings.SettingsPreferenceFragment;

import com.android.internal.logging.nano.MetricsProto;

public class ButtonSettings extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener{

private SwitchPreference mNavbarToggle;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.nr_settings_button);
        ContentResolver resolver = getActivity().getContentResolver();

        final PreferenceScreen prefScreen = getPreferenceScreen();

        mNavbarToggle = (SwitchPreference) findPreference("navigation_bar_enabled");
        boolean enabled = Settings.Secure.getIntForUser(
                resolver, Settings.Secure.NAVIGATION_BAR_ENABLED,
                getActivity().getResources().getBoolean(
                com.android.internal.R.bool.config_showNavigationBar) ? 1 : 0,
                UserHandle.USER_CURRENT) == 1;
        mNavbarToggle.setChecked(enabled);
        mNavbarToggle.setOnPreferenceChangeListener(this);
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        ContentResolver resolver = getActivity().getContentResolver();
        if (preference == mNavbarToggle) {
            boolean value = (Boolean) newValue;
            Settings.Secure.putIntForUser(getActivity().getContentResolver(),
                    Settings.Secure.NAVIGATION_BAR_ENABLED, value ? 1 : 0,
                    UserHandle.USER_CURRENT);
            mNavbarToggle.setChecked(value);
            return true;
        }

        return false;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.NR_SETTINGS;
    }

}
