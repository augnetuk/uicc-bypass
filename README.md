# UICC privileges bypass module

This xposed module intercepts calls to UiccCard.getCarrierPrivilegeStatusForCurrentTransaction and always returns 'allowed'. It allows any application
to call injectSmsPdu which is normally restricted to apps signed by NMO.
This module was tested only on Android 6.0

Usage:

* root the phone
* install XPosed framework and module installer
* build and install uicc bypass module
* in xposed module installer, enable uicc bypass module and reboot


