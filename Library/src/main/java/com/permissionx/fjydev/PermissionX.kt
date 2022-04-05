package com.permissionx.fjydev

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

object PermissionX {

    private const val TAG = "InvisibleFragment"

    fun request(activity: FragmentActivity, vararg permission: String, callback:
                PermissionCallback) {
        val fragmentManager = activity.supportFragmentManager
        // 判断传入的Activity是否包含了TAG的Fragment
        val existedFragment = fragmentManager.findFragmentByTag(TAG)
        // 包含则直接使用，否则新建一个InvisibleFragment 并添加到Activity中
        val fragment = if (existedFragment != null) {
            existedFragment as InvisibleFragment
        } else {
            val invisibleFragment = InvisibleFragment()
            fragmentManager.beginTransaction().add(invisibleFragment, TAG).commitNow()
            invisibleFragment
        }
        // permission 是一个数组，在前面加*可转换成可变长度参数
        fragment.request(callback, *permission)
    }
}