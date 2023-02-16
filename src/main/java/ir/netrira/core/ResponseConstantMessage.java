package ir.netrira.core;

public interface ResponseConstantMessage {

    String INTERNAL_ERROR = "به علت بروز مشکل عملیات انجام نشد.";
    String SC_OK = "عملیات با موفقیت انجام شد.";
    String SC_CONTINUE = "";
    String SC_SWITCHING_PROTOCOLS = "";
    String SC_CREATED = "";
    String SC_ACCEPTED = "";
    String SC_NON_AUTHORITATIVE_INFORMATION = "";
    String SC_NO_CONTENT = "";
    String SC_RESET_CONTENT = "";
    String SC_PARTIAL_CONTENT = "";
    String SC_MULTIPLE_CHOICES = "";
    String SC_MOVED_PERMANENTLY = "";
    String SC_MOVED_TEMPORARILY = "";
    String SC_FOUND = "";
    String SC_SEE_OTHER = "";
    String SC_NOT_MODIFIED = "";
    String SC_USE_PROXY = "";
    String SC_TEMPORARY_REDIRECT = "";
    String SC_BAD_REQUEST = "اطلاعات به درستی وارد نشده.";
    String SC_UNAUTHORIZED = "مدت زمان اعتبار توکن شما به پایان رسیده، لطفا مجدد وارد شوید.";
    String SC_PAYMENT_REQUIRED = "";
    String SC_FORBIDDEN = "";
    String SC_NOT_FOUND = "";
    String SC_METHOD_NOT_ALLOWED = "شما درسترسی به رابط کاربرد \"%s\" ندارید.";
    String SC_NOT_ACCEPTABLE = "";
    String SC_PROXY_AUTHENTICATION_REQUIRED = "";
    String SC_REQUEST_TIMEOUT = "";
    String SC_CONFLICT = "";
    String SC_GONE = "";
    String SC_LENGTH_REQUIRED = "";
    String SC_PRECONDITION_FAILED = "";
    String SC_REQUEST_ENTITY_TOO_LARGE = "";
    String SC_REQUEST_URI_TOO_LONG = "";
    String SC_UNSUPPORTED_MEDIA_TYPE = "";
    String SC_REQUESTED_RANGE_NOT_SATISFIABLE = "";
    String SC_EXPECTATION_FAILED = "";
    String SC_StringERNAL_SERVER_ERROR = "";
    String SC_NOT_IMPLEMENTED = "";
    String SC_BAD_GATEWAY = "";
    String SC_SERVICE_UNAVAILABLE = "";
    String SC_GATEWAY_TIMEOUT = "";
    String SC_HTTP_VERSION_NOT_SUPPORTED = "";

    //100000 range
    String EMAIL_EXIST = "ایمیل وارد شده در سیستم موجود میباشد";
    String USERNAME_EXIST = "نام کاربری وارد شده در سیستم موجود میباشد";
    String USERNAME_NOT_EXIST  = "نام کاربری وارد شده در سیستم موجود نمی باشد";
    String INVALID_LOGIN_DATA = "نام کاربری یا رمزعبور به درستی وارد نشده.";
    String INVALID_CAPTCHA = "عبارت امنیتی به درستی وارد نشده.";
    String CAPTCHA_NOT_FOUND = "مدت زمان استفاده از عبارت امنیتی منقضی شده، لطفا مجدد امتحان کتید.";

    String ELEMENT_EXIST  = "ایدی لاگ مورد نظر به درستی وارد نشده.";
    String ELEMENT_CODE_NOT_EXIST  = "کد مورد نظر یافت نشد.";
    String CONFIG_NOT_FOUND  = "تنظیمات مورد نظر یافت نشد.";
    String CAN_NOT_CONVERT_TO_JSON  = "مشکلی در تبدیل اطلاعات به فرمت json پیش آمده.";
}
