package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractIPInviteListTypeEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractIPInviteListTypeEntryInfo()
    {
        this("id");
    }
    protected AbstractIPInviteListTypeEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 采购类别明细分录 's 采购类别 property 
     */
    public com.kingdee.eas.fdc.invite.InviteProjectInfo getHead()
    {
        return (com.kingdee.eas.fdc.invite.InviteProjectInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.invite.InviteProjectInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 采购类别明细分录 's 采购类别明细 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.InviteListTypeInfo getInviteListType()
    {
        return (com.kingdee.eas.fdc.invite.supplier.InviteListTypeInfo)get("inviteListType");
    }
    public void setInviteListType(com.kingdee.eas.fdc.invite.supplier.InviteListTypeInfo item)
    {
        put("inviteListType", item);
    }
    /**
     * Object:采购类别明细分录's 预计采购金额property 
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
     * Object:采购类别明细分录's 备注property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object:采购类别明细分录's 预计采购单价property 
     */
    public java.math.BigDecimal getPrice()
    {
        return getBigDecimal("price");
    }
    public void setPrice(java.math.BigDecimal item)
    {
        setBigDecimal("price", item);
    }
    /**
     * Object:采购类别明细分录's 预计采购数量property 
     */
    public java.math.BigDecimal getNum()
    {
        return getBigDecimal("num");
    }
    public void setNum(java.math.BigDecimal item)
    {
        setBigDecimal("num", item);
    }
    /**
     * Object:采购类别明细分录's 规格型号property 
     */
    public String getModel()
    {
        return getString("model");
    }
    public void setModel(String item)
    {
        setString("model", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3FFEC8A6");
    }
}