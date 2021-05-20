package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEnterpriseSchemeEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractEnterpriseSchemeEntryInfo()
    {
        this("id");
    }
    protected AbstractEnterpriseSchemeEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��ʵʩ��¼'s ��������IDproperty 
     */
    public String getSellPlanID()
    {
        return getString("sellPlanID");
    }
    public void setSellPlanID(String item)
    {
        setString("sellPlanID", item);
    }
    /**
     * Object:��ʵʩ��¼'s �Ƿ��ѽ���property 
     */
    public com.kingdee.eas.fdc.market.IsFinishEnum getIsEnd()
    {
        return com.kingdee.eas.fdc.market.IsFinishEnum.getEnum(getString("isEnd"));
    }
    public void setIsEnd(com.kingdee.eas.fdc.market.IsFinishEnum item)
    {
		if (item != null) {
        setString("isEnd", item.getValue());
		}
    }
    /**
     * Object:��ʵʩ��¼'s ʵ�ʽ��property 
     */
    public java.math.BigDecimal getFactAmount()
    {
        return getBigDecimal("factAmount");
    }
    public void setFactAmount(java.math.BigDecimal item)
    {
        setBigDecimal("factAmount", item);
    }
    /**
     * Object:��ʵʩ��¼'s ��ͬ��Idproperty 
     */
    public com.kingdee.bos.util.BOSUuid getContractNumberID()
    {
        return getBOSUuid("contractNumberID");
    }
    public void setContractNumberID(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("contractNumberID", item);
    }
    /**
     * Object:��ʵʩ��¼'s ��ͬ����property 
     */
    public String getContractName()
    {
        return getString("contractName");
    }
    public void setContractName(String item)
    {
        setString("contractName", item);
    }
    /**
     * Object: ��ʵʩ��¼ 's ��Ӧ�� property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getSupplier()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("supplier");
    }
    public void setSupplier(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("supplier", item);
    }
    /**
     * Object:��ʵʩ��¼'s ��ͬ���property 
     */
    public java.math.BigDecimal getContractAmount()
    {
        return getBigDecimal("contractAmount");
    }
    public void setContractAmount(java.math.BigDecimal item)
    {
        setBigDecimal("contractAmount", item);
    }
    /**
     * Object:��ʵʩ��¼'s ������property 
     */
    public java.math.BigDecimal getPayAmount()
    {
        return getBigDecimal("payAmount");
    }
    public void setPayAmount(java.math.BigDecimal item)
    {
        setBigDecimal("payAmount", item);
    }
    /**
     * Object:��ʵʩ��¼'s ��עproperty 
     */
    public String getBeizhu()
    {
        return getString("beizhu");
    }
    public void setBeizhu(String item)
    {
        setString("beizhu", item);
    }
    /**
     * Object: ��ʵʩ��¼ 's ��ʵʩ property 
     */
    public com.kingdee.eas.fdc.market.EnterpriseSchemeInfo getParent()
    {
        return (com.kingdee.eas.fdc.market.EnterpriseSchemeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.market.EnterpriseSchemeInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��ʵʩ��¼'s ʵ�ʿ�ʼ����property 
     */
    public java.util.Date getStartTime()
    {
        return getDate("startTime");
    }
    public void setStartTime(java.util.Date item)
    {
        setDate("startTime", item);
    }
    /**
     * Object:��ʵʩ��¼'s ʵ�ʽ���ʱ��property 
     */
    public java.util.Date getEndTime()
    {
        return getDate("endTime");
    }
    public void setEndTime(java.util.Date item)
    {
        setDate("endTime", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6C48F67B");
    }
}