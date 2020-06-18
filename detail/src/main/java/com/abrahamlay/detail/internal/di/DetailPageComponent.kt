package com.abrahamlay.detail.internal.di

import com.abrahamlay.base.di.component.BaseComponent
import com.abrahamlay.detail.DetailFragment
import dagger.Component

/**
 * Created by Abraham Lay on 17/06/20.
 */

@Component(
    dependencies = [
        BaseComponent::class
    ]
)
interface DetailPageComponent {
    fun inject(detailFragment: DetailFragment)
}