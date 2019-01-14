package com.it.onex.baby.di.component;

import android.content.Context;

import com.it.onex.baby.di.module.ApplicationModule;
import com.it.onex.baby.di.scope.ContextLife;
import com.it.onex.baby.di.scope.PerApp;

import dagger.Component;


/**
 * Created by OnexZgj on 2017/1/19.
 */
@PerApp
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    @ContextLife("Application")
    Context getApplication();
}