package com.abrahamlay.base.di

import com.abrahamlay.base.di.component.BaseComponent

interface Injectable {

    fun injectComponent()

    fun <C : BaseComponent?> getBaseComponent(cClass: Class<C>?): C

    fun <SubC> getAppSubComponent(subClass: Class<SubC>?): SubC
}
