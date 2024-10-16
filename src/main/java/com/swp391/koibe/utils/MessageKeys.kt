package com.swp391.koibe.utils

object MessageKeys {
    const val LOGIN_SUCCESSFULLY: String = "user.login.login_successfully"
    const val LOGOUT_SUCCESSFULLY: String = "user.login.logout_successfully"
    const val LOGOUT_FAILED: String = "user.login.logout_failed"
    const val REGISTER_SUCCESSFULLY: String = "user.login.register_successfully"
    const val LOGIN_FAILED: String = "user.login.login_failed"
    const val PASSWORD_NOT_MATCH: String = "user.register.password_not_match"
    const val USER_IS_LOCKED: String = "user.login.user_is_locked"
    const val USER_NOT_FOUND: String = "user.login.user_not_found"

    const val UPDATE_USER_SUCCESSFULLY: String = "user.update.update_successfully"
    const val VERIFY_USER_SUCCESSFULLY: String = "user.verify.verify_successfully"
    const val VERIFY_USER_FAILED: String = "user.verify.verify_failed"
    const val USER_ALREADY_VERIFIED: String = "user.verify.user_already_verified"
    const val OTP_EXPIRED: String = "user.verify.otp_expired"
    const val OTP_IS_CORRECT: String = "user.verify.otp_is_correct"
    const val OTP_IS_INCORRECT: String = "user.verify.otp_is_incorrect"

    const val INSERT_CATEGORY_SUCCESSFULLY: String = "category.create_category.create_successfully"
    const val DELETE_CATEGORY_SUCCESSFULLY: String = "category.delete_category.delete_successfully"
    const val UPDATE_CATEGORY_SUCCESSFULLY: String = "category.update_category.update_successfully"
    const val DELETE_ORDER_SUCCESSFULLY: String = "order.delete_order.delete_successfully"
    const val DELETE_ORDER_DETAIL_SUCCESSFULLY: String =
        "order.delete_order_detail.delete_successfully"
    const val UPLOAD_IMAGES_MAX_5: String = "product.upload_images.error_max_5_images"
    const val UPLOAD_IMAGES_FILE_LARGE: String = "product.upload_images.file_large"
    const val UPLOAD_IMAGES_FILE_MUST_BE_IMAGE: String = "product.upload_images.file_must_be_image"
    const val INSERT_CATEGORY_FAILED: String = "category.create_category.create_failed"
    const val WRONG_PHONE_PASSWORD: String = "user.login.wrong_phone_password"
    const val ROLE_DOES_NOT_EXISTS: String = "user.login.role_not_exist"

    const val BIDDING_RULES_ERROR: String = "bid.exception.bidding_rule_error"
}
