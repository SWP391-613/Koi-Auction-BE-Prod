package com.swp391.koibe.utils

import javax.swing.JOptionPane

object OTPUtils {
    @JvmStatic
    fun generateOTP(): String {
        return String.format("%06d", (Math.random() * 1000000).toInt())
    }

    fun IS_NOTIFY_VERIFY_ACCOUNT() {
        JOptionPane.showMessageDialog(
            null,
            "We've send OTP verification to your email, please provide to authenticate!",
            "Koi Auction",
            JOptionPane.INFORMATION_MESSAGE
        )
    }
}
