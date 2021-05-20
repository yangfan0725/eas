package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteProjectEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractInviteProjectEntryInfo()
    {
        this("id");
    }
    protected AbstractInviteProjectEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�б������¼'s ������Ŀ����property 
     */
    public String getPrjNumber()
    {
        return getString("prjNumber");
    }
    public void setPrjNumber(String item)
    {
        setString("prjNumber", item);
    }
    /**
     * Object: �б������¼ 's ���� property 
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
     * Object:�б������¼'s ���property 
     */
    public String getSize()
    {
        return getString("size");
    }
    public void setSize(String item)
    {
        setString("size", item);
    }
    /**
     * Object:�б������¼'s ����property 
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
     * Object:�б������¼'s ˵��property 
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
     * Object: �б������¼ 's ������λ property 
     */
    public com.kingdee.eas.basedata.assistant.MeasureUnitInfo getMeasureUnit()
    {
        return (com.kingdee.eas.basedata.assistant.MeasureUnitInfo)get("measureUnit");
    }
    public void setMeasureUnit(com.kingdee.eas.basedata.assistant.MeasureUnitInfo item)
    {
        put("measureUnit", item);
    }
    /**
     * Object: �б������¼ 's �б����� property 
     */
    public com.kingdee.eas.fdc.invite.InviteProjectInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.InviteProjectInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.InviteProjectInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:�б������¼'s �ƻ���������property 
     */
    public java.util.Date getInputDate()
    {
        return getDate("inputDate");
    }
    public void setInputDate(java.util.Date item)
    {
        setDate("inputDate", item);
    }
    /**
     * Object:�б������¼'s �ɹ����뵥��property 
     */
    public String getPurchaseBillNum()
    {
        return getString("purchaseBillNum");
    }
    public void setPurchaseBillNum(String item)
    {
        setString("purchaseBillNum", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2E2294A6");
    }
}