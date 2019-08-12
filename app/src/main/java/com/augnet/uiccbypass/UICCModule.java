package com.augnet.uiccbypass;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;


public class UICCModule implements IXposedHookLoadPackage {
    private final static String DEFAULT_CARRIER_APP_PACKAGE="com.augnet.uiccservice";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("com.android.phone"))
            return;
        XposedHelpers.findAndHookMethod("com.android.internal.telephony.uicc.UiccCard", lpparam.classLoader,
                "getCarrierPrivilegeStatusForCurrentTransaction", PackageManager.class, new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("Called getCarrierPrivilegeStatusForCurrentTransaction");
                        return (int)1;
                    }
                });

        XposedHelpers.findAndHookMethod("com.android.internal.telephony.uicc.UiccCard", lpparam.classLoader,
                "getCarrierPackageNamesForIntent", PackageManager.class, Intent.class, new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("Called getCarrierPackageNamesForIntent for "+param.args[1].toString());
                        List<String> ret = new ArrayList<>();
                        ret.add(DEFAULT_CARRIER_APP_PACKAGE);
                        return ret;
                    }
                });

        XposedBridge.log("UICC privileges bypass installed on "+lpparam.packageName);



    }


 }
