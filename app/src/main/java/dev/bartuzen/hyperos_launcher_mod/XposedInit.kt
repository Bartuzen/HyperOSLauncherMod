package dev.bartuzen.hyperos_launcher_mod

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XSharedPreferences
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class XposedInit : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        val classLoader = lpparam.classLoader
        val pref = XSharedPreferences("dev.bartuzen.hyperos_launcher_mod", "preferences")

        XposedHelpers.findAndHookMethod(
            "com.miui.home.launcher.DeviceConfig",
            classLoader,
            "getCellCountX",
            object : XC_MethodHook() {
                override fun beforeHookedMethod(param: MethodHookParam) {
                    param.result = pref.getInt("icons_horizontal", 4)
                }
            }
        )

        XposedHelpers.findAndHookMethod(
            "com.miui.home.launcher.DeviceConfig",
            classLoader,
            "getCellCountY",
            object : XC_MethodHook() {
                override fun beforeHookedMethod(param: MethodHookParam) {
                    param.result = pref.getInt("icons_vertical", 6)
                }
            }
        )

        if (pref.getBoolean("disable_clear_all_killing_background_tasks", false)) {
            XposedHelpers.findAndHookMethod(
                "com.miui.home.recents.views.RecentsContainer",
                classLoader,
                "deepClean",
                object : XC_MethodHook() {
                    override fun beforeHookedMethod(param: MethodHookParam) {
                        param.result = null
                    }
                }
            )
        }
    }
}
