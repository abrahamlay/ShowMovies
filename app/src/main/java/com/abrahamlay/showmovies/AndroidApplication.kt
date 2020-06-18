package com.abrahamlay.showmovies

import android.app.Application
import com.abrahamlay.base.di.ComponentOwner
import com.abrahamlay.base.di.SubComponentOwner
import com.abrahamlay.base.di.component.BaseComponent
import com.abrahamlay.base.di.component.BaseNavigationComponent
import com.abrahamlay.showmovies.di.DaggerApplicationComponent
import com.abrahamlay.showmovies.navigation.DaggerNavigationComponent
import java.util.*
import kotlin.reflect.KClass


/**
 * Created by Abraham Lay on 2020-06-09.
 */
class AndroidApplication : Application(),
    ComponentOwner<BaseComponent>,
    SubComponentOwner {

    private val subComponent: HashMap<KClass<*>, Any>? = hashMapOf()
    val appComponent = DaggerApplicationComponent.builder().build()
    override fun onCreate() {
        super.onCreate()

        appComponent.inject(this)
        subComponent?.put(
            BaseNavigationComponent::class,
            DaggerNavigationComponent.builder().build()
        )
    }

    override fun getComponent(): BaseComponent {
        return appComponent
    }

    override fun <SubC> getSubComponent(subClass: Class<SubC>?): SubC? {
        subClass?.let {
            if (BaseNavigationComponent::class.java.isAssignableFrom(it)) {
                return it.cast(subComponent?.get(BaseNavigationComponent::class))
            }
        }
        return null
    }
}