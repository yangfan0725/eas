package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractZHMarketProjectEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractZHMarketProjectEntryInfo()
    {
        this("id");
    }
    protected AbstractZHMarketProjectEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: Ӫ�������¼ 's ͷ property 
     */
    public com.kingdee.eas.fdc.contract.ZHMarketProjectInfo getHead()
    {
        return (com.kingdee.eas.fdc.contract.ZHMarketProjectInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.contract.ZHMarketProjectInfo item)
    {
        put("head", item);
    }
    /**
     * Object: Ӫ�������¼ 's �ɱ���Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountInfo getCostAccount()
    {
        return (com.kingdee.eas.fdc.basedata.CostAccountInfo)get("costAccount");
    }
    public void setCostAccount(com.kingdee.eas.fdc.basedata.CostAccountInfo item)
    {
        put("costAccount", item);
    }
    /**
     * Object:Ӫ�������¼'s ���property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object:Ӫ�������¼'s ���Ƶ���property 
     */
    public com.kingdee.eas.fdc.contract.app.MarketCostTypeEnum getType()
    {
        return com.kingdee.eas.fdc.contract.app.MarketCostTypeEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.contract.app.MarketCostTypeEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    /**
     * Object:Ӫ�������¼'s ����Ԥ������ʱ��property 
     */
    public java.util.Date getBizDate()
    {
        return getDate("bizDate");
    }
    public void setBizDate(java.util.Date item)
    {
        setDate("bizDate", item);
    }
    /**
     * Object:Ӫ�������¼'s ����˵��property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    /**
     * Object: Ӫ�������¼ 's ǩԼ��λ property 
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
     * Object:Ӫ�������¼'s �̶���property 
     */
    public java.math.BigDecimal getSdAmount()
    {
        return getBigDecimal("sdAmount");
    }
    public void setSdAmount(java.math.BigDecimal item)
    {
        setBigDecimal("sdAmount", item);
    }
    /**
     * Object:Ӫ�������¼'s ��עproperty 
     */
    public String getSdRemark()
    {
        return getString("sdRemark");
    }
    public void setSdRemark(String item)
    {
        setString("sdRemark", item);
    }
    /**
     * Object:Ӫ�������¼'s �ȼ۵�λproperty 
     */
    public String getUnit()
    {
        return getString("unit");
    }
    public void setUnit(String item)
    {
        setString("unit", item);
    }
    /**
     * Object:Ӫ�������¼'s ����property 
     */
    public java.math.BigDecimal getBjAmount()
    {
        return getBigDecimal("bjAmount");
    }
    public void setBjAmount(java.math.BigDecimal item)
    {
        setBigDecimal("bjAmount", item);
    }
    /**
     * Object:Ӫ�������¼'s ��עproperty 
     */
    public String getBjRemark()
    {
        return getString("bjRemark");
    }
    public void setBjRemark(String item)
    {
        setString("bjRemark", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("84A6BD9E");
    }
}