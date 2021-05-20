package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPolicyManageInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractPolicyManageInfo()
    {
        this("id");
    }
    protected AbstractPolicyManageInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.market.PolicyManageEntryCollection());
    }
    /**
     * Object: ���߹��� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.market.PolicyManageEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.market.PolicyManageEntryCollection)get("entrys");
    }
    /**
     * Object:���߹���'s �Ƿ�����ƾ֤property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    /**
     * Object:���߹���'s ����property 
     */
    public String getPolicyName()
    {
        return getString("policyName");
    }
    public void setPolicyName(String item)
    {
        setString("policyName", item);
    }
    /**
     * Object: ���߹��� 's ���� property 
     */
    public com.kingdee.eas.basedata.assistant.CountryInfo getCountry()
    {
        return (com.kingdee.eas.basedata.assistant.CountryInfo)get("country");
    }
    public void setCountry(com.kingdee.eas.basedata.assistant.CountryInfo item)
    {
        put("country", item);
    }
    /**
     * Object: ���߹��� 's ʡ property 
     */
    public com.kingdee.eas.basedata.assistant.ProvinceInfo getProvince()
    {
        return (com.kingdee.eas.basedata.assistant.ProvinceInfo)get("province");
    }
    public void setProvince(com.kingdee.eas.basedata.assistant.ProvinceInfo item)
    {
        put("province", item);
    }
    /**
     * Object: ���߹��� 's �� property 
     */
    public com.kingdee.eas.basedata.assistant.CityInfo getCity()
    {
        return (com.kingdee.eas.basedata.assistant.CityInfo)get("city");
    }
    public void setCity(com.kingdee.eas.basedata.assistant.CityInfo item)
    {
        put("city", item);
    }
    /**
     * Object:���߹���'s ���߷�Χproperty 
     */
    public String getPolicyRange()
    {
        return getString("policyRange");
    }
    public void setPolicyRange(String item)
    {
        setString("policyRange", item);
    }
    /**
     * Object:���߹���'s �䲼����property 
     */
    public String getPublishDep()
    {
        return getString("publishDep");
    }
    public void setPublishDep(String item)
    {
        setString("publishDep", item);
    }
    /**
     * Object:���߹���'s �ؼ���property 
     */
    public String getKeyWord()
    {
        return getString("keyWord");
    }
    public void setKeyWord(String item)
    {
        setString("keyWord", item);
    }
    /**
     * Object:���߹���'s ��עproperty 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F13F3588");
    }
}