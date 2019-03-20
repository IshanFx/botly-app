package com.phoenix.botly.activities.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import com.phoenix.botly.homepage.FeatureMenuAdapter
import com.phoenix.botly.homepage.FeatureMenuView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_code_change.view.*
import java.util.*
import android.util.Log
import android.content.Intent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AlertDialog
import android.view.View
import com.phoenix.botly.AppSession
import com.phoenix.botly.FeatureMenu
import com.phoenix.botly.FeatureMenu.tags
import com.phoenix.botly.FeatureMenu.tags.*
import com.phoenix.botly.services.LocationService
import com.phoenix.botly.R
import com.phoenix.botly.R.layout
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), FeatureMenuView {
    internal var mContext: Context

    internal var mFeatureMenuList: MutableList<FeatureMenu>
    internal var session: AppSession
    internal var seletedItem: tags = WAKE_MY_PHONE
    var featureMenuAdapter: FeatureMenuAdapter

    init {
        mContext = this
        mFeatureMenuList = ArrayList()
        session = AppSession(mContext)
        featureMenuAdapter = FeatureMenuAdapter(this, mFeatureMenuList, this)
    }

    //todo  permission check
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        createFeatureMenuItems()

        featureMenuList.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(mContext)
        featureMenuList.adapter = featureMenuAdapter
        appPermissionChecker()
    }

    private fun createFeatureMenuItems() {
        mFeatureMenuList.clear();

        val featureMenuItem1 = FeatureMenu()
        featureMenuItem1.name = "RINGTONE"
        featureMenuItem1.tag = WAKE_MY_PHONE
        featureMenuItem1.iconName = "ic_ringtone"
        featureMenuItem1.code = session.code.wakeCode;

        val featureMenuItem2 = FeatureMenu()
        featureMenuItem2.name = "WIFI"
        featureMenuItem2.tag = WIFI
        featureMenuItem2.iconName = "ic_wifi"
        featureMenuItem2.code = session.code.wifiCode;

        val featureMenuItem3 = FeatureMenu()
        featureMenuItem3.name = "LOCATION"
        featureMenuItem3.tag = LOCATION
        featureMenuItem3.iconName = "ic_location"
        featureMenuItem3.code = session.code.locationCode;

        mFeatureMenuList.add(featureMenuItem1)
        mFeatureMenuList.add(featureMenuItem2)
        mFeatureMenuList.add(featureMenuItem3)


        val featureMenuItem4 = FeatureMenu()
        featureMenuItem4.name = "CALL"
        featureMenuItem4.tag = CALL
        featureMenuItem4.iconName = "ic_phone_call"
        featureMenuItem4.code = session.code.phoneCallCode;

        mFeatureMenuList.add(featureMenuItem4)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_exit) {
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun showWakeUpCodeChangeDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        val dialogView = LayoutInflater.from(mContext).inflate(layout.dialog_code_change, null)
        alertDialogBuilder.setView(dialogView)
        alertDialogBuilder.setTitle(null)

        val editTxtCodeChangeNewCode = dialogView.editTxtCodeChangeNewCode
        editTxtCodeChangeNewCode.setText(getCurrentCode())
        alertDialogBuilder.setPositiveButton("Save") { dialog, which ->
            saveCode(editTxtCodeChangeNewCode.text.toString())
            dialog.dismiss()
            createFeatureMenuItems()
            featureMenuAdapter.notifyDataSetChanged()
        }

        val alert = alertDialogBuilder.create();
        alert.show();
    }

    override fun onMenuItemClick(tag: tags) {
        seletedItem = tag
        showWakeUpCodeChangeDialog()
    }

    fun saveCode(code: String) {
        when (seletedItem) {
            WAKE_MY_PHONE -> {
                val sms = session.code
                sms.wakeCode = code
                session.updateNewSmsWakeUpCode(sms)
            }
            WIFI -> {
                val sms = session.code
                sms.wifiCode = code
                session.updateWifiCode(sms)

            }
            LOCATION -> {
                val sms = session.code
                sms.locationCode = code
                session.updateLocationCode(sms)
            }
            CALL -> {
                val sms = session.code
                sms.phoneCallCode = code
                session.updatePhoneCallCode(sms)
            }
        }
    }

    fun getCurrentCode(): String? {
        val sms = session.code

        when (seletedItem) {
            WAKE_MY_PHONE -> {
                return sms.wakeCode

            }
            WIFI -> {
                return sms.wifiCode
            }
            LOCATION -> {
                return sms.locationCode
            }
            CALL -> {
                return sms.phoneCallCode
            }
        }
        return ""
    }

    private fun appPermissionChecker() {
        val permissionCheckCourseLoca = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)

        val permissionCheckLocation = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)

        val permissionReceiveSms = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.RECEIVE_SMS)

        val permissionReadSms = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS)

        val permissionSendSms = ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)

        val permissionAccessWifiStates = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_WIFI_STATE)

        val permissionChangeWifiStates = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CHANGE_WIFI_STATE)

        var permissionPhoneCall = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)

        val permissions = ArrayList<String>()

        if (permissionCheckCourseLoca != PackageManager.PERMISSION_GRANTED) {
            permissions.add(android.Manifest.permission.ACCESS_COARSE_LOCATION)
            //ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_CALENDAR);
        }

        if (permissionCheckLocation != PackageManager.PERMISSION_GRANTED) {
            permissions.add(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }

        if (permissionReadSms != PackageManager.PERMISSION_GRANTED) {
            permissions.add(android.Manifest.permission.READ_SMS)
        }

        if (permissionSendSms != PackageManager.PERMISSION_GRANTED) {
            permissions.add(android.Manifest.permission.SEND_SMS)

        }

        if (permissionReceiveSms != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.RECEIVE_SMS)
        }

        if (permissionAccessWifiStates != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_WIFI_STATE)
        }

        if (permissionChangeWifiStates != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.CHANGE_WIFI_STATE)
        }

        if (permissionPhoneCall != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.CALL_PHONE)
        }

        if (!permissions.isEmpty()) {

            var permissionReqList = arrayOfNulls<String>(permissions.size)
            permissionReqList = permissions.toTypedArray<String?>()

            ActivityCompat.requestPermissions(this, permissionReqList, 100)
        } else {
            Log.d("LOg", "Permission all granted")
            if (permissionCheckCourseLoca == PackageManager.PERMISSION_GRANTED && permissionCheckLocation == PackageManager.PERMISSION_GRANTED) {
                startLocationServiceBackground()
            }
        }
    }

    fun startLocationServiceBackground() {
        val intent = Intent(this, LocationService::class.java)
        startService(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        var isNotGrandRequestFound = false
        when (requestCode) {
            100 -> {

                if (ContextCompat.checkSelfPermission(this,
                                android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    startLocationServiceBackground()
                }

                for (i in permissions.indices) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Log.d("Permissions", "Permission Granted: " + permissions[i])
                    } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        isNotGrandRequestFound = true
                        Log.d("Permissions", "Permission Denied: " + permissions[i])
                    }
                }
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }


    fun showHelpDialog(view: View) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage(R.string.dialog_help)
        alertDialogBuilder.setTitle("Help")
        alertDialogBuilder.setNegativeButton("Close") { dialog, which ->
            dialog.dismiss();
        }
        val alert = alertDialogBuilder.create();
        alert.show();
    }

}
