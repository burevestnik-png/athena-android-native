package ru.yofik.athena.settings.presentation;

import java.lang.System;

@dagger.hilt.android.lifecycle.HiltViewModel()
@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u0012\u001a\u00020\u0013H\u0002J\u000e\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u0016J\b\u0010\u0017\u001a\u00020\u0013H\u0002R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\r8F\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000b0\r8F\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u000f\u00a8\u0006\u0018"}, d2 = {"Lru/yofik/athena/settings/presentation/SettingsFragmentViewModel;", "Landroidx/lifecycle/ViewModel;", "logoutUser", "Lru/yofik/athena/settings/domain/usecases/LogoutUser;", "getCachedUser", "Lru/yofik/athena/settings/domain/usecases/GetCachedUser;", "(Lru/yofik/athena/settings/domain/usecases/LogoutUser;Lru/yofik/athena/settings/domain/usecases/GetCachedUser;)V", "_effects", "Landroidx/lifecycle/MutableLiveData;", "Lru/yofik/athena/settings/presentation/SettingsFragmentViewEffect;", "_state", "Lru/yofik/athena/settings/presentation/SettingsViewState;", "effects", "Landroidx/lifecycle/LiveData;", "getEffects", "()Landroidx/lifecycle/LiveData;", "state", "getState", "handleLogoutUser", "", "onEvent", "event", "Lru/yofik/athena/settings/presentation/SettingsFragmentEvent;", "provideUserInfo", "settings_debug"})
public final class SettingsFragmentViewModel extends androidx.lifecycle.ViewModel {
    private final ru.yofik.athena.settings.domain.usecases.LogoutUser logoutUser = null;
    private final ru.yofik.athena.settings.domain.usecases.GetCachedUser getCachedUser = null;
    private final androidx.lifecycle.MutableLiveData<ru.yofik.athena.settings.presentation.SettingsFragmentViewEffect> _effects = null;
    private final androidx.lifecycle.MutableLiveData<ru.yofik.athena.settings.presentation.SettingsViewState> _state = null;
    
    @javax.inject.Inject()
    public SettingsFragmentViewModel(@org.jetbrains.annotations.NotNull()
    ru.yofik.athena.settings.domain.usecases.LogoutUser logoutUser, @org.jetbrains.annotations.NotNull()
    ru.yofik.athena.settings.domain.usecases.GetCachedUser getCachedUser) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<ru.yofik.athena.settings.presentation.SettingsFragmentViewEffect> getEffects() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<ru.yofik.athena.settings.presentation.SettingsViewState> getState() {
        return null;
    }
    
    public final void onEvent(@org.jetbrains.annotations.NotNull()
    ru.yofik.athena.settings.presentation.SettingsFragmentEvent event) {
    }
    
    private final void provideUserInfo() {
    }
    
    private final void handleLogoutUser() {
    }
}