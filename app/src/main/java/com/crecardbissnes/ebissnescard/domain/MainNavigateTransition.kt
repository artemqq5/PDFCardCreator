package com.crecardbissnes.ebissnescard.domain

import android.os.Bundle

interface MainNavigateTransition {

    fun transitionFragment(nav: Int, bundle: Bundle? = null)

    fun backTransition()

}