package com.kingdee.eas.base.uiframe.client;

import com.kingdee.bos.json.JSONArray;
import com.kingdee.bos.json.JSONException;
import com.kingdee.bos.json.JSONObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.license.EditionTypeEnum;
import com.kingdee.eas.base.license.ILicenseSrvAgent;
import com.kingdee.eas.base.license.LicenseException;
import com.kingdee.eas.base.license.LicenseUserInfo;
import com.kingdee.eas.base.license.client.LicenseController;
import com.kingdee.eas.base.license.client.LicenseSrvAgentFactory;
import com.kingdee.eas.base.license.file.Account;
import com.kingdee.eas.base.license.file.LicenseCloudRequestInfo;
import com.kingdee.eas.base.license.file.LicenseValidateInfo;
import com.kingdee.eas.base.log.StatisticConstant;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.uiframe.client.util.CloudUtil;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.util.AbortException;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.Base64Encoder;
import com.kingdee.util.StringUtils;
import java.awt.Component;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

public class LicenseHelper
{
  private static final Logger logger = Logger.getLogger(LicenseHelper.class);
  private static final String LICENSE_CLIENT_IP = "License.ClientIP";
  private static final String LICENSE_LOGIN_TIME = "License.LoginTime";
  private static final String LICENSE_USER_INFO = "License.UserInfo";
  
  public static LicenseUserInfo createLicenseUserInfo()
  {
    LicenseUserInfo user = null;
    try
    {
      String clientIP = InetAddress.getLocalHost().getHostAddress();
      Date loginTime = new Date(System.currentTimeMillis());
      SysContext.getSysContext().setProperty("License.ClientIP", clientIP);
      SysContext.getSysContext().setProperty("License.LoginTime", loginTime);
      user = new LicenseUserInfo();
      if (SysContext.getSysContext().getCurrentUser() != null) {
        user.setUserID(((UserInfo)SysContext.getSysContext().getCurrentUser()).getId().toString());
      }
      if (SysContext.getSysContext().getUserName() != null) {
        user.setUserName(SysContext.getSysContext().getUserName());
      }
      CompanyOrgUnitInfo orgUnit = (CompanyOrgUnitInfo)SysContext.getSysContext().getCurrentCompany();
      if (orgUnit == null)
      {
        user.setUserCompany("");
      }
      else
      {
        String compName = orgUnit.getName(SysContext.getSysContext().getLocale());
        user.setUserCompany(compName);
      }
      if (SysContext.getSysContext().getDcName() != null) {
        user.setUserLoginAis(SysContext.getSysContext().getDcName());
      }
      if (SysContext.getSysContext().getProperty("SessionId") != null) {
        user.setSessionID((String)SysContext.getSysContext().getProperty("SessionId"));
      }
      user.setUserIP(clientIP);
      user.setUserLoginTime(loginTime);
      SysContext.getSysContext().setProperty("License.UserInfo", user);
    }
    catch (Exception e)
    {
      logger.error(e.getMessage(), e);
      user = null;
    }
    return user;
  }
  
  public static boolean checkLicence(Component owner, String packageName)
    throws LicenseException
  {
    boolean hasLicence = false;
    LicenseUserInfo user = createLicenseUserInfo();
    LicenseController lc = LicenseController.getInstance();
    if (user == null) {
      return true;
    }
    int licenselcFlag = lc.requestLicense(user, packageName);
    switch (licenselcFlag)
    {
    case 1: 
      hasLicence = true;
      
      break;
    case 4: 
      MsgBox.showError(owner, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Error_License_Invalid"));
      
      break;
    case 2: 
      MsgBox.showError(owner, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Error_License_NO_SURPLUS"));
    }
    return hasLicence;
  }
  
  public static void abort()
  {
    throw new AbortException();
  }
  
  public static boolean releaseLicense(boolean hasLicence, String packageName)
  {
    if (hasLicence)
    {
      String sessionId = SysContext.getSysContext().getSessionID();
      LicenseController lc = LicenseController.getInstance();
      try
      {
        lc.releaseLicense(sessionId, packageName);
      }
      catch (LicenseException lex) {}
      hasLicence = false;
    }
    return hasLicence;
  }
  
  public static boolean isTrail()
  {
    try
    {
      ILicenseSrvAgent agent = LicenseSrvAgentFactory.getRemoteInstance();
      return agent.isDemoVersion();
    }
    catch (LicenseException e)
    {
      logger.error(e.getMessage(), e);
    }
    return false;
  }
  
  public static boolean keyPointLicenseCheck()
  {
    return true;
  }
  
  private static boolean showActiveUI(boolean needProvideAssistFile)
  {
    IUIFactory uiFactory = null;
    try
    {
      Map ctx = new UIContext();
      logger.error("是否需要生成GenuineValidity.dat：" + (needProvideAssistFile ? "是" : "否"));
      ctx.put("needProvideAssistFile", Boolean.valueOf(needProvideAssistFile));
      uiFactory = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory");
      IUIWindow uiWindow = uiFactory.create(ActiveCodeUI.class.getName(), ctx);
      
      ActiveCodeUI uiObject = (ActiveCodeUI)uiWindow.getUIObject();
      uiWindow.show();
      return uiObject.getReturnValue();
    }
    catch (UIException ex)
    {
      ExceptionHandler.handle(ex);
    }
    return false;
  }
  
  public static String sendData(JSONObject json)
    throws Exception
  {
    HttpClient http = new HttpClient();
    String url = "https://api.cmcloud.cn/api/genuineCheck.do";
    UTF8PostMethod post = new UTF8PostMethod(url);
    NameValuePair reqPart = new NameValuePair("req", json.toString());
    NameValuePair[] parts = { reqPart };
    post.setRequestBody(parts);
    int status = http.executeMethod(post);
    logger.info("http.executeMethod(get) status: " + status);
    String response = new String(post.getResponseBodyAsString().getBytes("utf-8"), "utf-8");
    logger.info("response: " + response);
    post.releaseConnection();
    return response;
  }
  
  public static JSONObject generateJSONObject(LicenseCloudRequestInfo requestInfo)
    throws JSONException
  {
    JSONObject json = new JSONObject();
    if (requestInfo == null) {
      return json;
    }
    json.put("FProductRegNO", requestInfo.getProductRegNO());
    json.put("FProductID", requestInfo.getProductID());
    json.put("FLicExpireDate", requestInfo.getExpireDate());
    json.put("FLicMD5", requestInfo.getLicenseMD5());
    json.put("FCellsMD5", requestInfo.getCellsMD5());
    json.put("FAdditionalInfo", requestInfo.getAdditionalInfo());
    json.put("FMode", requestInfo.getEncryMode());
    json.put("FType", requestInfo.getType());
    json.put("FPeriod", requestInfo.getPeriod());
    json.put("FLicName", requestInfo.getLicenseName());
    
    Account[] accounts = requestInfo.getAccounts();
    if ((accounts != null) && (accounts.length > 0))
    {
      JSONArray jsonArray = new JSONArray();
      for (int i = 0; i < accounts.length; i++)
      {
        JSONObject subJson = new JSONObject();
        subJson.put("FAccountName", accounts[i].getAccountName());
        subJson.put("FProductVer", accounts[i].getProductVersion());
        subJson.put("FCustomName", accounts[i].getCustomerName());
        jsonArray.put(subJson);
      }
      json.put("FAccounts", jsonArray);
    }
    return json;
  }
  
  public static String decodeBASE64(String source)
  {
    return new String(Base64Encoder.base64ToByteArray(source));
  }
  
  public static String generateActiveCode()
    throws LicenseException
  {
    ILicenseSrvAgent agent = LicenseSrvAgentFactory.getRemoteInstance();
    LicenseValidateInfo info = agent.getLicenseValidateInfo();
    String productCode = info.getProductCode();
    String softwareCode = info.getSoftwareCode();
    Random randomrand = new Random();
    String rand = String.valueOf(randomrand.nextInt(9999));
    switch (rand.length())
    {
    case 1: 
      rand = "000" + rand;
      break;
    case 2: 
      rand = "00" + rand;
      break;
    case 3: 
      rand = "0" + rand;
      break;
    default: 
      rand = rand.substring(0, 4);
    }
    String activeCode = productCode + softwareCode + rand;
    assert (activeCode.length() == 30);
    StringBuffer result = new StringBuffer();
    for (int i = 0; i < activeCode.length(); i += 5) {
      result.append(activeCode.substring(i, i + 5)).append("-");
    }
    return result.substring(0, result.length() - 1);
  }
  
  public static boolean validateLicense(LicenseCloudRequestInfo requestInfo)
  {
    try
    {
      if (requestInfo == null)
      {
        logger.error("requestInfo is null.");
        return false;
      }
      JSONObject obj = generateJSONObject(requestInfo);
      logger.error("验权从服务器获取到的信息：" + obj);
      String response = sendData(obj);
      JSONObject json = new JSONObject(response);
      String validateResult = decodeBASE64(json.get("result").toString());
      logger.error("验权云端返回结果：" + validateResult);
      ILicenseSrvAgent agent = LicenseSrvAgentFactory.getRemoteInstance();
      agent.updateLicenseValidate(validateResult);
    }
    catch (Exception e)
    {
      logger.error(e.getMessage(), e);
      return false;
    }
    return true;
  }
  
  public static LicenseValidateInfo getLicenseValidateInfo()
  {
    try
    {
      ILicenseSrvAgent agent = LicenseSrvAgentFactory.getRemoteInstance();
      LicenseValidateInfo info = agent.getLicenseValidateInfo();
      logger.error("获取校验信息-----");
      logger.error("requestId: " + info.getRequestId());
      logger.error("license创建时间: " + info.getCreateDate());
      logger.error("服务器当前时间: " + info.getCurrentDate());
      logger.error("license过期时间: " + info.getExpireDate());
      logger.error("license最后校验时间: " + info.getLatestCheckDate());
      logger.error("ProductCode: " + info.getProductCode());
      logger.error("特征码: " + info.getSoftwareCode());
      logger.error("ValidityCode: " + info.getValidityCode());
      logger.error("ValidityStatus: " + info.getValidityStatus());
      logger.error("status: " + info.getStatus());
      logger.error("获取校验信息-----");
      return info;
    }
    catch (Throwable e)
    {
      logger.error(e.getMessage(), e);
    }
    return null;
  }
  
  public static long calculateDaysBetween(String source, String destin)
  {
    if ((StringUtils.isEmpty(source)) || (StringUtils.isEmpty(destin))) {
      return 100L;
    }
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    try
    {
      Calendar c1 = Calendar.getInstance();
      Calendar c2 = Calendar.getInstance();
      if (!StringUtils.isEmpty(source)) {
        c1.setTime(formatter.parse(source));
      }
      if (!StringUtils.isEmpty(destin)) {
        c2.setTime(formatter.parse(destin));
      }
      return (c1.getTimeInMillis() - c2.getTimeInMillis()) / 86400000L;
    }
    catch (Throwable e)
    {
      logger.error(e.getMessage(), e);
    }
    return 100L;
  }
  
  public static boolean isNeedRevalidate()
  {
    ILicenseSrvAgent agent = null;
    try
    {
      agent = LicenseSrvAgentFactory.getRemoteInstance();
    }
    catch (LicenseException e)
    {
      logger.error(e.getMessage(), e);
      return false;
    }
    String period = CloudUtil.getLicenseCheckPeriod();
    logger.error("验证周期是： " + period);
    if (StatisticConstant.ERROR.equals(period)) {
      return false;
    }
    boolean isNeedRevalidate = agent.isNeedRevalidate(period);
    logger.error("是否需要发起云验权：" + isNeedRevalidate);
    return isNeedRevalidate;
  }
  
  public static class UTF8PostMethod
    extends PostMethod
  {
    public UTF8PostMethod(String url)
    {
      super();
    }
    
    public String getRequestCharSet()
    {
      return "UTF-8";
    }
  }
}
