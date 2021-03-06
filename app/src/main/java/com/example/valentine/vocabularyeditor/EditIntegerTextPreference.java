package com.example.valentine.vocabularyeditor;

import android.content.Context;
import android.preference.EditTextPreference;
import android.util.AttributeSet;

/**
 * Created by valentine on 08.11.14.
 */
public class EditIntegerTextPreference extends EditTextPreference {

    public EditIntegerTextPreference(Context context) {
        super(context);
    }

    public EditIntegerTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditIntegerTextPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected String getPersistedString(String defaultReturnValue) {
        return String.valueOf(getPersistedInt(-1));
    }

    @Override
    protected boolean persistString(String value) {
        return persistInt(Integer.valueOf(value));
    }

    @Override
    public String getText() {
        return super.getText();
    }
}