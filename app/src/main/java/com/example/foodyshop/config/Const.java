package com.example.foodyshop.config;

import java.util.concurrent.TimeUnit;

public class Const {

    public static final int TOAST_DEFAULT = 1500;
    public static final long LIMIT_TIME_EDIT_DEL_FEEDBACK = TimeUnit.MINUTES.toMillis(30); // minutes
    // ít nhất 1 kí tự & 1 số & min length 8
    public static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

    public static final String KEY_USER_PREFERENCES = "key_user_preferences";
    public static final String KEY_USER_OBJ = "key_user_obj";
    public static final String KEY_USER_CART = "key_user_cart";
    public static final String KEY_TOKEN_LOGIN = "key_token_login";
    public static final String KEY_NEW_TASK = "key_new_task";
    public static final String KEY_SIGN_IN_OK = "key_sign_in_ok";

    public static final String KEY_IS_CHECK_ALL = "key_is_check_all";
    public static final String KEY_TOTAL_MONEY = "key_total_money";

    public static final String KEY_TOPIC = "key_topic";
    public static final String KEY_CATEGORY = "key_category";
    public static final String KEY_PRODUCT = "key_product";
    public static final String KEY_FROM_CART = "key_from_cart";
    public static final String KEY_FROM_BUY_AGAIN = "key_from_buy_again";
    public static final String KEY_RELOAD_CART = "key_reload_cart";
    public static final String KEY_SELECT_TAP_ALL = "key_select_tap_all";

    public static final String KEY_ACTION = "key_action";
    public static final String KEY_PHONE_CODE = "key_phone_code";
    public static final String KEY_VERIFICATION_ID = "key_verification_id";

    // ======== Retrofit api key ======== \\
    public static final String KEY_TOKEN = "token";

    public static final String KEY_DATA_USER = "data_user";
    public static final String KEY_AVATAR_USER = "avatar_user";
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_ADDRESS = "address";

    public static final String KEY_PASSWORD = "password";
    public static final String KEY_OLD_PASSWORD = "old_password";
    public static final String KEY_NEW_PASSWORD = "new_password";

    public static final String KEY_TOPIC_ID = "topic_id";
    public static final String KEY_CATEGORY_ID = "category_id";
    public static final String KEY_PRODUCT_ID = "product_id";

    public static final String KEY_ORDER = "order";
    public static final String KEY_ORDER_ID = "order_id";

    public static final String KEY_REQUEST_STATUS = "request_status";

    public static final String KEY_FEEDBACK = "feedback";
    public static final String KEY_FEEDBACK_ID = "feedback_id";
    public static final String KEY_CONTENT = "content";

    public static final String KEY_CURRENT_PAGE = "current_page";
    public static final String KEY_MIN_PRICE = "min_price";
    public static final String KEY_MAX_PRICE = "max_price";
    public static final String KEY_SEARCH = "search";
    public static final String KEY_ORDER_PRICE = "order_price";
    public static final String KEY_OFFSET = "offset";
    public static final String KEY_IS_SALE = "is_sale";


}
