package com.phoenix.botly

import android.graphics.drawable.GradientDrawable

class FeatureMenu {

    var name: String? = null
    var icon: String? = null
    var isActivated: Boolean = false
    var description: String? = null
    var iconName: String? = null
    var tag: tags? = null
    var code: String? = null;
    var backgroundGradient: GradientDrawable? = null

    enum class tags {
        WAKE_MY_PHONE,
        LOCATION,
        AUTOMATIC_SMS,

        WIFI,
        CALL
    }
}
