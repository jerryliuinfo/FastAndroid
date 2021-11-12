package com.apache.fastandroid.util;

import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.tesla.framework.common.util.log.NLog;

/**
 * Created by Jerry on 2020/12/2.
 * 国内、海外 账号登录相关参数(注意:其他非账号登录相关的信息不要存放在这个类中)
 * 只存放纯数据，跟sdk有关的业务代码不要放在这个类中，不要用sdk中提供的类，否则有可能会报找不到类的错误
 */
public class UserInfoManager {
    private static final String ACCOUNT_ID = "accountId";
    private static final String ACCESS_TOKEN = "accessToken";
    private static final String REFRESH_TOKEN = "refreshToken";
    private static final String LOGIN_TYPE = "LoginWay";
    private static final String USERNAME = "username";
    private static final String PHONE = "phone";
    private static final String EMAIL = "email";
    private static final String WECHAT_NICK_NAME = "_wechatNickName";

    public static final String TAG = "UserInfoManager";
    private static final String FILE_NAME = "user_info";

    private String mAccoutId;

    private SPUtils spUtils;

    private UserInfoManager() {
        spUtils = SPUtils.getInstance(FILE_NAME);
    }

    private static class SingletonHolder {
        private SingletonHolder() {

        }

        private final static UserInfoManager INSTANCE = new UserInfoManager();
    }

    public static UserInfoManager getInstance() {
        return SingletonHolder.INSTANCE;
    }


    /**
     *  调用者需要对accoutId进行判空，有可能还未登录，这个时候accoutId是没有值的
     * @return
     */
    public String getAccountId() {
        if (TextUtils.isEmpty(mAccoutId)) {
            mAccoutId = spUtils.getString(ACCOUNT_ID, "");
        }

        if (TextUtils.isEmpty(mAccoutId)){
            NLog.e(TAG, "getAccountId is null");
        }
        return mAccoutId;
    }

    /**
     * 后台生成的账户唯一id，永远不会变(修改手机号也不会变)
     *
     *
     * @param accountId
     */
    public void saveAccountId(String accountId) {
        this.mAccoutId = accountId;
        spUtils.put(ACCOUNT_ID, accountId);
    }

    /**
     * 用于国内账号，海外账号没用这个字段,
     * 注意:海外账号不要调用这个方法
     * 注意:海外账号不要调用这个方法
     * 注意:海外账号不要调用这个方法
     * @return
     */
    public String getUsername() {
//        return getStringValue(USERNAME, "");
        return spUtils.getString(USERNAME, "");
    }

    /**
     * 国内账号username存的是电话号码
     * @param username
     */
    public void saveUsername(String username) {
        NLog.d(TAG, "saveUsername username: %s", username);
//        setStringValue(USERNAME, username);
        spUtils.put(USERNAME, username);

    }


    public void savePhone(String phone) {
        if (TextUtils.isEmpty(phone)){
            NLog.e(TAG, "savePhone null");
        }
//        setStringValue(PHONE, phone);
        spUtils.put(PHONE, phone);

    }

    //不能用用户手动输入的手机号，需要用getUserInfo接口返回的
    public String getPhone() {
        return spUtils.getString(PHONE,"");
    }

    public void saveEmail(String email) {
        spUtils.put(EMAIL,email);
    }

    //不能用用户手动输入的手机号，需要用getUserInfo接口返回的
    public String getEmail() {
        return spUtils.getString(EMAIL, "");
    }



    public String getAccessToken() {
        if (!TextUtils.isEmpty(accessToken)) {
            return accessToken;
        }
        return spUtils.getString(ACCESS_TOKEN, "");
    }

    public void saveAccessToken(String accessToken) {
        NLog.d(TAG, "saveAccessToken --->");
        if (TextUtils.isEmpty(accessToken)){
            NLog.e(TAG, "saveAccessToken accessToken is null");
        }
        this.accessToken = accessToken;
//        setStringValue(ACCESS_TOKEN, accessToken);
        spUtils.put(ACCESS_TOKEN, accessToken);
        //更新最后一次获取token的时间
        if (!TextUtils.isEmpty(accessToken)){
            setLastGetTokenTime(System.currentTimeMillis());
        }

    }

    public String getRefreshToken() {
        if (!TextUtils.isEmpty(refreshToken)) {
            return refreshToken;
        }
        return spUtils.getString(REFRESH_TOKEN, "");
    }

    public void saveRefreshToken(String refreshToken) {
        if (TextUtils.isEmpty(refreshToken)){
            NLog.e(TAG, "saveRefreshToken refreshToken is null");
        }
        this.refreshToken = refreshToken;
        spUtils.put(REFRESH_TOKEN, refreshToken);
        setLastGetRefreshTokenTime(System.currentTimeMillis());
    }


    public void setLoginType(String loginTypeName) {
        spUtils.put(LOGIN_TYPE,loginTypeName);
    }




    public boolean isAccountHasPwd() {
        return spUtils.getBoolean(UserInfoManager.getInstance().getAccountId() + "_hasPwd",false);
    }


    public void setAccountHasPwdInfo(String accoutId,boolean hasPwd) {
        spUtils.put(accoutId+ "_hasPwd", hasPwd);
    }



    public void saveEduUserInfo(String token) {
        NLog.d(TAG, "saveEduUserInfo token: %s",token);
        if (!TextUtils.isEmpty(token)) {
            saveAccessToken(token);
        }
    }




    public void setLastGetRefreshTokenTime(long time){
        spUtils.put(getAccountId()+"_lastUpdateRefreshTokenTime", time);
    }

    public long getLastUpdateRefreshTokenTime(){
        return spUtils.getLong(getAccountId()+"_lastUpdateRefreshTokenTime", 0);
    }


    /**
     * 用于海外版最后一次获取token的时间
     * @param time
     */
    public void setLastGetTokenTime(long time){
        NLog.d(TAG, "setLastGetTokenTime accountId: %s", getAccountId());
        spUtils.put(getAccountId()+"_LastGetTokenTime", time);
    }

    /**
     * 用于海外版最后一次获取token的时间
     * @return
     */
    public long  getLastGetTokenTime(){
        return spUtils.getLong(getAccountId()+"_LastGetTokenTime", 0);
    }




    /**
     * 不管是国内账号还是国外账号，登录成功后，都需要转换成EduUserInfoBean来使用
     */
    public void onLoginSuccess(String accessToken) {
        /**
         * 验证码、密码登录场景下，{@link TclAccessInfo#tclMnUserInfo  代表用户信息的字段是有值得，但是如果是第三方登录，这这个字段没有值，需要接入方手动调用}
         */
        saveEduUserInfo(accessToken);

        refreshLoginState();
    }

    public void logout() {
        //除了手机号和邮箱以外的登录信息都清除掉
        saveAccessToken("");
        saveRefreshToken("");
        saveAccountId("");
        saveUsername("");
        refreshLoginState();
    }




    private String mLoginType = "";
    private String accessToken;
    private String refreshToken;
    private boolean isLogin;

    /**
     * 是否初始化过本地账号信息
     */
    private boolean initAccountState = false;

    public boolean isLogin() {
        if (!initAccountState){
            initAccountState = true;
            refreshLoginState();
        }
        return isLogin;
    }

    public void refreshLoginState() {
        accessToken = getAccessToken();
        refreshToken = getRefreshToken();
        isLogin = !TextUtils.isEmpty(accessToken) && !TextUtils.isEmpty(mLoginType);
    }




    public void setWechatNickName(String wechatName){
        spUtils.put(getAccountId()+WECHAT_NICK_NAME, wechatName);
    }

    public String getWechatNickName(){
        return spUtils.getString(getAccountId()+WECHAT_NICK_NAME,"");
    }


}


