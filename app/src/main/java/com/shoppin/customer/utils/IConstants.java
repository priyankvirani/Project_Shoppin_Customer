package com.shoppin.customer.utils;

/**
 * Created by ubuntu on 21/7/16.
 */
public interface IConstants {

    interface IDrawerMenu {
        // Login required
        String LOGIN_SIGNUP = "Login / Sign Up";
        int LOGIN_SIGNUP_ID = 10011;

        String WELCOME = "My Account";
        int WELCOME_ID = 10012;

        String CHANGE_SUBURB = "Change Suburb";
        int CHANGE_SUBURB_ID = 1002;

        String NEAR_BY_STORES = "Near By Stores";
        int NEAR_BY_STORES_ID = 1004;

        // Login required
        String MY_ORDERS = "My Orders";
        int MY_ORDER_ID = 1005;

        String CART = "Cart";
        int CART_ID = 1006;

        String OFFERS = "Offers";
        int OFFERS_ID = 1007;

        String ABOUT_US = "About Us";
        int ABOUT_US_ID = 1008;
    }

    interface IPushNotification {
        String APP_LAUNCH_TYPE = "app_launch_type";
        String APP_LAUNCH_NOTIFICATION = "app_launch_notification";
        String PUSHNOTIFICATION_DATA = "push_data";
        String MESSAGE = "message";
        String TTITLE = "title";
        String IMAGE = "image";
        String NOTIFICATION_TYPE = "type";
        String NOTIFICATION_TYPE_OFFER = "offer";
        String NOTIFICATION_TYPE_ORDER = "order";
    }
}
