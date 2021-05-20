package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBOMEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractBOMEntryInfo()
    {
        this("id");
    }
    protected AbstractBOMEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �׹������嵥��¼ 's ���� property 
     */
    public com.kingdee.eas.basedata.master.material.MaterialInfo getMaterial()
    {
        return (com.kingdee.eas.basedata.master.material.MaterialInfo)get("material");
    }
    public void setMaterial(com.kingdee.eas.basedata.master.material.MaterialInfo item)
    {
        put("material", item);
    }
    /**
     * Object:�׹������嵥��¼'s ����ͺ�property 
     */
    public String getModel()
    {
        return getString("model");
    }
    public void setModel(String item)
    {
        setString("model", item);
    }
    /**
     * Object:�׹������嵥��¼'s Ʒ��property 
     */
    public String getBrand()
    {
        return getString("brand");
    }
    public void setBrand(String item)
    {
        setString("brand", item);
    }
    /**
     * Object: �׹������嵥��¼ 's ��λ property 
     */
    public com.kingdee.eas.basedata.assistant.MeasureUnitInfo getUnit()
    {
        return (com.kingdee.eas.basedata.assistant.MeasureUnitInfo)get("unit");
    }
    public void setUnit(com.kingdee.eas.basedata.assistant.MeasureUnitInfo item)
    {
        put("unit", item);
    }
    /**
     * Object:�׹������嵥��¼'s ����property 
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
     * Object:�׹������嵥��¼'s ��������property 
     */
    public String getFactory()
    {
        return getString("factory");
    }
    public void setFactory(String item)
    {
        setString("factory", item);
    }
    /**
     * Object:�׹������嵥��¼'s ��;property 
     */
    public String getUse()
    {
        return getString("use");
    }
    public void setUse(String item)
    {
        setString("use", item);
    }
    /**
     * Object:�׹������嵥��¼'s ��װ��λproperty 
     */
    public com.kingdee.eas.fdc.invite.InstallTypeEnum getInstall()
    {
        return com.kingdee.eas.fdc.invite.InstallTypeEnum.getEnum(getString("install"));
    }
    public void setInstall(com.kingdee.eas.fdc.invite.InstallTypeEnum item)
    {
		if (item != null) {
        setString("install", item.getValue());
		}
    }
    /**
     * Object:�׹������嵥��¼'s �ƻ�����ʱ��property 
     */
    public java.util.Date getEnterTime()
    {
        return getDate("enterTime");
    }
    public void setEnterTime(java.util.Date item)
    {
        setDate("enterTime", item);
    }
    /**
     * Object:�׹������嵥��¼'s �Ƿ�Ҫ��ά��property 
     */
    public boolean isIsNeedService()
    {
        return getBoolean("isNeedService");
    }
    public void setIsNeedService(boolean item)
    {
        setBoolean("isNeedService", item);
    }
    /**
     * Object:�׹������嵥��¼'s ��עproperty 
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
        return new BOSObjectType("1BDED5B6");
    }
}