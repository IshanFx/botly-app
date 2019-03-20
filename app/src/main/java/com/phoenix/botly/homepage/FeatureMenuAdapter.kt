package com.phoenix.botly.homepage

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup

import com.phoenix.botly.FeatureMenu
import com.phoenix.botly.R
import android.content.ContentValues.TAG

class FeatureMenuAdapter(internal var mContext: Context, internal var mFeatureMenu: List<FeatureMenu>,internal var mFeatureMenuView:FeatureMenuView) : androidx.recyclerview.widget.RecyclerView.Adapter<FeatureMenuViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureMenuViewHolder {
        val itemLayout = LayoutInflater.from(parent.context).inflate(R.layout.main_feature_list_item, parent, false)

        return FeatureMenuViewHolder(itemLayout)
    }

    override fun onBindViewHolder(holder: FeatureMenuViewHolder, position: Int) {
        holder.textFeatureMenuItemName.text = mFeatureMenu[position].name

        val resources = mContext.resources
        Log.d(TAG, "Item ICON : " + mFeatureMenu[position].iconName)
        val resourceId = resources.getIdentifier(mFeatureMenu[position].iconName, "drawable",
                mContext.packageName)
        holder.imageViewFeatureMenuListItemIcon.setImageDrawable(resources.getDrawable(resourceId))
        holder.cardFeatureMenuItemMaster.tag = mFeatureMenu[position].tag
        holder.textFeatureMenuCode.text = mFeatureMenu[position].code;
        holder.cardFeatureMenuItemMaster.setOnClickListener { view -> loadFeatureActivity(view.tag as FeatureMenu.tags) }

    }

    override fun getItemCount(): Int {
        return mFeatureMenu.size
    }


    private fun loadFeatureActivity(tag: FeatureMenu.tags) {
        mFeatureMenuView.onMenuItemClick(tag)

    }
}
