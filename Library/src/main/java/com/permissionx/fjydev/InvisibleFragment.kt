package com.permissionx.fjydev

import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

typealias PermissionCallback = (Boolean, List<String>) -> Unit
class InvisibleFragment : Fragment() {

    private var callback: PermissionCallback? = null

    fun request(cb: PermissionCallback, vararg permission: String) {
        callback = cb
        requestPermissions(permission, 1)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            // 记录所有被拒绝的权限
            val deniedList = ArrayList<String>()
            // 遍历 grantResult 数组，发现未授权权限就加入 deniedList
            for ((index, result) in grantResults.withIndex()) {
                if ( result != PackageManager.PERMISSION_GRANTED) {
                    deniedList.add(permissions[index])
                }
            }
            // allGranted 记录是否全部授权
            val allGranted = deniedList.isEmpty()
            callback?.let { it(allGranted, deniedList) }
        }
    }
}