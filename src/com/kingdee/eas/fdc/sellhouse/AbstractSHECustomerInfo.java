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
     * Object: ��¥�ͻ� 's ��ҵ���� property 
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
     * Object: ��¥�ͻ� 's ������ҵ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SharePropertyCollection getShareProperty()
    {
        return (com.kingdee.eas.fdc.sellhouse.SharePropertyCollection)get("shareProperty");
    }
    /**
     * Object: ��¥�ͻ� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.sellhouse.ShareSellProjectCollection getShareProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.ShareSellProjectCollection)get("shareProject");
    }
    /**
     * Object: ��¥�ͻ� 's ���ز����ͻ� property 
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
     * Object: ��¥�ͻ� 's ��ϵ�� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHECustomerLinkManCollection getLinkMan()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHECustomerLinkManCollection)get("linkMan");
    }
    /**
     * Object: ��¥�ͻ� 's ��Դ���� property 
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
     * Object: ��¥�ͻ� 's �״νӴ����� property 
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
     * Object:��¥�ͻ�'s �Ƿ񹫹��ͻ�property 
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
     * Object:��¥�ͻ�'s �Ƽ���property 
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
     * Object: ��¥�ͻ� 's �Ƽ��� property 
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
     * Object:��¥�ͻ�'s �Ƽ�����property 
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
     * Object: ��¥�ͻ� 's �̻����� property 
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
     * Object:��¥�ͻ�'s ��������property 
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
     * Object: ��¥�ͻ� 's ������Ա property 
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
     * Object:��¥�ͻ�'s ��������property 
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
     * Object:��¥�ͻ�'s �׷�����property 
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
     * Object:��¥�ͻ�'s ������������property 
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
     * Object:��¥�ͻ�'s �ͻ��ȼ�property 
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
     * Object:��¥�ͻ�'s һ������property 
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
     * Object:��¥�ͻ�'s ��������property 
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
     * Object:��¥�ͻ�'s ͳһ������ô���property 
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
     * Object:��¥�ͻ�'s �����˺�property 
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
     * Object:��¥�ͻ�'s ��������property 
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
     * Object:��¥�ͻ�'s ���˴���property 
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
     * Object:��¥�ͻ�'s ������Աproperty 
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
     * Object:��¥�ͻ�'s ��Ա����property 
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
     * Object:��¥�ͻ�'s ��������property 
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