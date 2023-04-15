package com.apache.fastandroid.demo.weaknetwork

import android.telephony.PhoneStateListener
import android.telephony.SignalStrength

/**
 * Created by Jerry on 2023/3/31.
 */
class PhoneStateTest : PhoneStateListener() {
    var signalStrengthValue = 0

    override fun onSignalStrengthsChanged(signalStrength: SignalStrength) {
        super.onSignalStrengthsChanged(signalStrength)
        signalStrengthValue = if (signalStrength.isGsm) {
            if (signalStrength.gsmSignalStrength != 99) signalStrength.gsmSignalStrength * 2 - 113 else signalStrength.gsmSignalStrength
        } else {
            signalStrength.cdmaDbm
        }
    }
}