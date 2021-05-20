package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSHECustomerInfo extends com.kingdee.eas.fdc.basecrm.FDCBaseCustomerInfo implements Serializable 
{
    public AbstractSHECustomerInfo()
    {
        this("id");
    }
    protected AbstractSHECustomerInfo(String pkField)
    {
        super(pkField);
        put("linkMan", new com.kingdee.eas.fdc.sellhouse.SHECustomerLinkManCollection());
        put("shareProperty", new com.kingdee.eas.fdc.sellhouse.SharePropertyCollection());
        put("shareProject", new com.kingdee.eas.fdc.sellhouse.ShareSellProjectCollection());
    }
    /**
     * Object: 售楼客户 's 置业顾问 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getPropertyConsultant()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("propertyConsultant");
    }
    public void setPropertyConsultant(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("propertyConsultant", item);
    }
    /**
     * Object: 售楼客户 's 共享置业顾问 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SharePropertyCollection getShareProperty()
    {
        return (com.kingdee.eas.fdc.sellhouse.SharePropertyCollection)get("shareProperty");
    }
    /**
     * Object: 售楼客户 's 共享项目 property 
     */
    public com.kingdee.eas.fdc.sellhouse.ShareSellProjectCollection getShareProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.ShareSellProjectCollection)get("shareProject");
    }
    /**
     * Object: 售楼客户 's 房地产主客户 property 
     */
    public com.kingdee.eas.fdc.basecrm.FDCMainCustomerInfo getMainCustomer()
    {
        return (com.kingdee.eas.fdc.basecrm.FDCMainCustomerInfo)get("mainCustomer");
    }
    public void setMainCustomer(com.kingdee.eas.fdc.basecrm.FDCMainCustomerInfo item)
    {
        put("mainCustomer", item);
    }
    /**
     * Object: 售楼客户 's 联系人 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHECustomerLinkManCollection getLinkMan()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHECustomerLinkManCollection)get("linkMan");
    }
    /**
     * Object: 售楼客户 's 来源线索 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CluesManageInfo getClues()
    {
        return (com.kingdee.eas.fdc.sellhouse.CluesManageInfo)get("clues");
    }
    public void setClues(com.kingdee.eas.fdc.sellhouse.CluesManageInfo item)
    {
        put("clues", item);
    }
    /**
     * Object: 售楼客户 's 首次接待顾问 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getFirstConsultant()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("firstConsultant");
    }
    public void setFirstConsultant(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("firstConsultant", item);
    }
    /**
     * Object:售楼客户's 是否公共客户property 
     */
    public boolean isIsPublic()
    {
        return getBoolean("isPublic");
    }
    public void setIsPublic(boolean item)
    {
        setBoolean("isPublic", item);
    }
    /**
     * Object:售楼客户's 推荐人property 
     */
    public String getRecommended()
    {
        return getString("recommended");
    }
    public void setRecommended(String item)
    {
        setString("recommended", item);
    }
    /**
     * Object: 售楼客户 's 推荐人 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHECustomerInfo getF7recommended()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHECustomerInfo)get("f7recommended");
    }
    public void setF7recommended(com.kingdee.eas.fdc.sellhouse.SHECustomerInfo item)
    {
        put("f7recommended", item);
    }
    /**
     * Object:售楼客户's 推荐日期property 
     */
    public java.sql.Timestamp getRecommendDate()
    {
        return getTimestamp("recommendDate");
    }
    public void setRecommendDate(java.sql.Timestamp item)
    {
        setTimestamp("recommendDate", item);
    }
    /**
     * Object: 售楼客户 's 商机级别 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo getCommerceLevel()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo)get("commerceLevel");
    }
    public void setCommerceLevel(com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo item)
    {
        put("commerceLevel", item);
    }
    /**
     * Object:售楼客户's 跟进日期property 
     */
    public java.util.Date getTrackDate()
    {
        return getDate("trackDate");
    }
    public void setTrackDate(java.util.Date item)
    {
        setDate("trackDate", item);
    }
    /**
     * Object: 售楼客户 's 渠道人员 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getQdPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("qdPerson");
    }
    public void setQdPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("qdPerson", item);
    }
    /**
     * Object:售楼客户's 渠道日期property 
     */
    public java.sql.Timestamp getQdDate()
    {
        return getTimestamp("qdDate");
    }
    public void setQdDate(java.sql.Timestamp item)
    {
        setTimestamp("qdDate", item);
    }
    /**
     * Object:售楼客户's 首访日期property 
     */
    public java.sql.Timestamp getFirstDate()
    {
        return getTimestamp("firstDate");
    }
    public void setFirstDate(java.sql.Timestamp item)
    {
        setTimestamp("firstDate", item);
    }
    /**
     * Object:售楼客户's 最新来访日期property 
     */
    public java.sql.Timestamp getLatestDate()
    {
        return getTimestamp("latestDate");
    }
    public void setLatestDate(java.sql.Timestamp item)
    {
        setTimestamp("latestDate", item);
    }
    /**
     * Object:售楼客户's 客户等级property 
     */
    public String getLevel()
    {
        return getString("level");
    }
    public void setLevel(String item)
    {
        setString("level", item);
    }
    /**
     * Object:售楼客户's 一级渠道property 
     */
    public String getOneQd()
    {
        return getString("oneQd");
    }
    public void setOneQd(String item)
    {
        setString("oneQd", item);
    }
    /**
     * Object:售楼客户's 二级渠道property 
     */
    public String getTwoQd()
    {
        return getString("twoQd");
    }
    public void setTwoQd(String item)
    {
        setString("twoQd", item);
    }
    /**
     * Object:售楼客户's 统一社会信用代码property 
     */
    public String getCreditCode()
    {
        return getString("creditCode");
    }
    public void setCreditCode(String item)
    {
        setString("creditCode", item);
    }
    /**
     * Object:售楼客户's 银行账号property 
     */
    public String getBankAccount()
    {
        return getString("bankAccount");
    }
    public void setBankAccount(String item)
    {
        setString("bankAccount", item);
    }
    /**
     * Object:售楼客户's 开户银行property 
     */
    public String getBank()
    {
        return getString("bank");
    }
    public void setBank(String item)
    {
        setString("bank", item);
    }
    /**
     * Object:售楼客户's 法人代表property 
     */
    public String getLegalPerson()
    {
        return getString("legalPerson");
    }
    public void setLegalPerson(String item)
    {
        setString("legalPerson", item);
    }
    /**
     * Object:售楼客户's 渠道人员property 
     */
    public String getQdPersontxt()
    {
        return getString("qdPersontxt");
    }
    public void setQdPersontxt(String item)
    {
        setString("qdPersontxt", item);
    }
    /**
     * Object:售楼客户's 会员卡号property 
     */
    public String getInsiderCode()
    {
        return getString("insiderCode");
    }
    public void setInsiderCode(String item)
    {
        setString("insiderCode", item);
    }
    /**
     * Object:售楼客户's 报备日期property 
     */
    public java.sql.Timestamp getReportDate()
    {
        return getTimestamp("reportDate");
    }
    public void setReportDate(java.sql.Timestamp item)
    {
        setTimestamp("reportDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F1713EF3");
    }
}