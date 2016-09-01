package com.shoppin.customer.network;

/**
 * Created by ubuntu on 10/8/16.
 */

public interface IWebService {

    //    String MAIN_URL = "http://192.168.0.1/ci/shoppin/admin/v1/";
    String MAIN_URL = "http://192.168.0.1/shoppin/service/index.php/";

    String KEY_RES_DATA = "data";
    String KEY_RES_SUCCESS = "success";
    String KEY_RES_MESSAGE = "message";

    String GET_SUBURB = MAIN_URL + "customer/GetSuburb";

    String CUSTOMER_LOGIN = MAIN_URL + "customer/CustomerLogin";
    String CUSTOMER_REGISTRATION = MAIN_URL + "customer/CustomerRegistration";
    String CUSTOMER_ACCOUNT_UPDATE = MAIN_URL + "customer/CustomerProfileUpdate";
    String CUSTOMER_ADDRESS_LIST = MAIN_URL + "customer/MyAddressList";
    String CUSTOMER_PROFILE = MAIN_URL + "customer/MyProfile";
    String CUSTOMER_ADD_ADDRESS = MAIN_URL + "customer/AddMyAddress";
    String CUSTOMER_UPDATE_ADDRESS = MAIN_URL + "customer/UpdateMyAddress";
    String CUSTOMER_DELETE_ADDRESS = MAIN_URL + "customer/DeleteMyAddress";

    String GET_CATEGORY = MAIN_URL + "category/GetCategoryList";

    String GET_PRODUCT_BY_SUB_CATEGORY = MAIN_URL + "category/GetProductsBySubCatID";

    String GET_PRODUCT_DETAIL = MAIN_URL + "product/GetProductDetail";

    String GET_CHECKOUT_DETAIL = MAIN_URL + "checkout/GetCheckOutDetails";

    /**
     * Request Params
     */
    String KEY_REQ_CUSTOMER_MOBILE = "customer_mobile";
    String KEY_REQ_CUSTOMER_PASSWORD = "customer_password";
    String KEY_REQ_CUSTOMER_DEVICE_TYPE = "customer_device_type";
    String KEY_REQ_CUSTOMER_DEVICE_TOKEN = "customer_device_token";
    String KEY_REQ_CUSTOMER_DEVICE_ID = "customer_device_id";
    //    String KEY_CUSTOMER_ = "";
    String KEY_REQ_CUSTOMER_NAME = "customer_name";
    String KEY_REQ_CUSTOMER_STREET = "customer_address_line1";
    String KEY_REQ_CUSTOMER_SUBURB_ID = "customer_suburb_id";
    String KEY_REQ_CUSTOMER_POSTCODE = "customer_zip";
    String KEY_REQ_IS_HOME = "is_home";
    String KEY_REQ_SUB_CATEGORY_ID = "sub_cat_id";
    String KEY_REQ_ADDRESS_ID = "customer_address_id";
    String KEY_REQ_CUSTOMER_SUBURB_NAME = "customer_suburb_name";
    String KEY_REQ_PRODUCT_ID = "product_id";
    String KEY_REQ_CUSTOMER_ID = "customer_id";
    String KEY_REQ_CUSTOMER__SUBURB_ID = "customer_suburb_id";

    /**
     * Response Params
     */
    String KEY_RES_CUSTOMER_ID = "customer_id";
    String KEY_RES_SUBURB_LIST = "suburb_list";
    String KEY_RES_SUBURB_ID = "customer_suburb_id";
    String KEY_RES_SUBURB_NAME = "customer_suburb_name";
    String KEY_RES_CATEGORY_LIST = "category_list";
    String KEY_RES_OFFER_LIST = "offer_list";
    String KEY_RES_PRODUCT_LIST = "product_list";
    String KEY_RES_ADDRESS_LIST = "address_list";
    String KEY_RES_DATE_LIST = "date_list";
    String KEY_RES_STORE_LIST = "store_list";

}
