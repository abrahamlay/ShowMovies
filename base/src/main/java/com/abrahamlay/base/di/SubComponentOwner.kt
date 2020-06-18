package com.abrahamlay.base.di

/**
 * Created by Abraham Lay on 19/06/20.
 */

interface SubComponentOwner {
    fun <SubC> getSubComponent(subClass: Class<SubC>?): SubC?
}