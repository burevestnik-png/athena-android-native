// Generated by view binder compiler. Do not edit!
package ru.yofik.athena.settings.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;
import java.lang.NullPointerException;
import java.lang.Override;
import ru.yofik.athena.settings.R;

public final class ToolbarSettingsBinding implements ViewBinding {
  @NonNull
  private final Toolbar rootView;

  @NonNull
  public final Toolbar toolbar;

  private ToolbarSettingsBinding(@NonNull Toolbar rootView, @NonNull Toolbar toolbar) {
    this.rootView = rootView;
    this.toolbar = toolbar;
  }

  @Override
  @NonNull
  public Toolbar getRoot() {
    return rootView;
  }

  @NonNull
  public static ToolbarSettingsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ToolbarSettingsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.toolbar_settings, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ToolbarSettingsBinding bind(@NonNull View rootView) {
    if (rootView == null) {
      throw new NullPointerException("rootView");
    }

    Toolbar toolbar = (Toolbar) rootView;

    return new ToolbarSettingsBinding((Toolbar) rootView, toolbar);
  }
}
