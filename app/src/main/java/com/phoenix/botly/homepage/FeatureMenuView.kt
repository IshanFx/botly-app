package com.phoenix.botly.homepage

import com.phoenix.botly.FeatureMenu

interface FeatureMenuView {

    fun onMenuItemClick( tag: FeatureMenu.tags);
}