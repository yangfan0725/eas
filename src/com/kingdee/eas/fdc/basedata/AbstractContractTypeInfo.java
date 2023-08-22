package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractTypeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractContractTypeInfo()
    {
        this("id");
    }
    protected AbstractContractTypeInfo(String pkField)
    {
        super(pkField);
        put("contractWFTypeEntry", new com.kingdee.eas.fdc.basedata.ContractWFEntryCollection());
        put("entry", new com.kingdee.eas.fdc.basedata.PayContentTypeEntryCollection());
        put("inviteTypeEntry", new com.kingdee.eas.fdc.basedata.ContractInviteTypeEntryCollection());
    }
    /**
     * Object:��ͬ����'s ���û����״̬property 
     */
    public boolean isIsEnabled()
    {
        return getBoolean("isEnabled");
    }
    public void setIsEnabled(boolean item)
    {
        setBoolean("isEnabled", item);
    }
    /**
     * Object:��ͬ����'s �Ƿ�ɱ������property 
     */
    public boolean isIsCost()
    {
        return getBoolean("isCost");
    }
    public void setIsCost(boolean item)
    {
        setBoolean("isCost", item);
    }
    /**
     * Object:��ͬ����'s �������property 
     */
    public java.math.BigDecimal getPayScale()
    {
        return getBigDecimal("payScale");
    }
    public void setPayScale(java.math.BigDecimal item)
    {
        setBigDecimal("payScale", item);
    }
    /**
     * Object: ��ͬ���� 's ����� property 
     */
    public com.kingdee.eas.fdc.basedata.ContractTypeInfo getParent()
    {
        return (com.kingdee.eas.fdc.basedata.ContractTypeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.basedata.ContractTypeInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ��ͬ���� 's ���β��� property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getDutyOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("dutyOrgUnit");
    }
    public void setDutyOrgUnit(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("dutyOrgUnit", item);
    }
    /**
     * Object:��ͬ����'s ӡ��˰��property 
     */
    public java.math.BigDecimal getStampTaxRate()
    {
        return getBigDecimal("stampTaxRate");
    }
    public void setStampTaxRate(java.math.BigDecimal item)
    {
        setBigDecimal("stampTaxRate", item);
    }
    /**
     * Object:��ͬ����'s ������property 
     */
    public String getForSupportLongnumberCoding()
    {
        return getString("forSupportLongnumberCoding");
    }
    public void setForSupportLongnumberCoding(String item)
    {
        setString("forSupportLongnumberCoding", item);
    }
    /**
     * Object: ��ͬ���� 's ���������¼ property 
     */
    public com.kingdee.eas.fdc.basedata.PayContentTypeEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.basedata.PayContentTypeEntryCollection)get("entry");
    }
    /**
     * Object:��ͬ����'s �������̷�����֯property 
     */
    public com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum getOrgType()
    {
        return com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum.getEnum(getString("orgType"));
    }
    public void setOrgType(com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum item)
    {
		if (item != null) {
        setString("orgType", item.getValue());
		}
    }
    /**
     * Object: ��ͬ���� 's ��ͬ�������ͷ�¼ property 
     */
    public com.kingdee.eas.fdc.basedata.ContractWFEntryCollection getContractWFTypeEntry()
    {
        return (com.kingdee.eas.fdc.basedata.ContractWFEntryCollection)get("contractWFTypeEntry");
    }
    /**
     * Object: ��ͬ���� 's �ɹ�����¼ property 
     */
    public com.kingdee.eas.fdc.basedata.ContractInviteTypeEntryCollection getInviteTypeEntry()
    {
        return (com.kingdee.eas.fdc.basedata.ContractInviteTypeEntryCollection)get("inviteTypeEntry");
    }
    /**
     * Object:��ͬ����'s �Ƿ��ܿ��ڳɱ���Ŀ����property 
     */
    public boolean isIsAccountView()
    {
        return getBoolean("isAccountView");
    }
    public void setIsAccountView(boolean item)
    {
        setBoolean("isAccountView", item);
    }
    /**
     * Object:��ͬ����'s �Ƿ�ֻ�����θ�������property 
     */
    public boolean isSinglePayment()
    {
        return getBoolean("singlePayment");
    }
    public void setSinglePayment(boolean item)
    {
        setBoolean("singlePayment", item);
    }
    /**
     * Object:��ͬ����'s �Ƿ��ܿ����б�������property 
     */
    public boolean isIsTA()
    {
        return getBoolean("isTA");
    }
    public void setIsTA(boolean item)
    {
        setBoolean("isTA", item);
    }
    /**
     * Object:��ͬ����'s �Ƿ����¼���ͬ�������ϸproperty 
     */
    public boolean isIsMarket()
    {
        return getBoolean("isMarket");
    }
    public void setIsMarket(boolean item)
    {
        setBoolean("isMarket", item);
    }
    /**
     * Object:��ͬ����'s �Ƿ�web�˺�Լ�滮����property 
     */
    public boolean isIsWebPC()
    {
        return getBoolean("isWebPC");
    }
    public void setIsWebPC(boolean item)
    {
        setBoolean("isWebPC", item);
    }
    /**
     * Object:��ͬ����'s �Ƿ��տ���property 
     */
    public boolean isIsReceive()
    {
        return getBoolean("isReceive");
    }
    public void setIsReceive(boolean item)
    {
        setBoolean("isReceive", item);
    }
    /**
     * Object:��ͬ����'s �Ƿ�����������ͬproperty 
     */
    public boolean isIsRelateReceive()
    {
        return getBoolean("isRelateReceive");
    }
    public void setIsRelateReceive(boolean item)
    {
        setBoolean("isRelateReceive", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B371775E");
    }
}