package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractQuitTenancyInfo extends com.kingdee.eas.fdc.tenancy.TenBillBaseInfo implements Serializable 
{
    public AbstractQuitTenancyInfo()
    {
        this("id");
    }
    protected AbstractQuitTenancyInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ���ⵥ 's ���޺�ͬ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillInfo getTenancyBill()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyBillInfo)get("tenancyBill");
    }
    public void setTenancyBill(com.kingdee.eas.fdc.tenancy.TenancyBillInfo item)
    {
        put("tenancyBill", item);
    }
    /**
     * Object:���ⵥ's ��������property 
     */
    public java.util.Date getQuitDate()
    {
        return getDate("quitDate");
    }
    public void setQuitDate(java.util.Date item)
    {
        setDate("quitDate", item);
    }
    /**
     * Object:���ⵥ's ����ԭ��property 
     */
    public String getQuitReason()
    {
        return getString("quitReason");
    }
    public void setQuitReason(String item)
    {
        setString("quitReason", item);
    }
    /**
     * Object:���ⵥ's ���ⵥ����property 
     */
    public com.kingdee.eas.fdc.tenancy.FlagAtTermEnum getQuitTenancyType()
    {
        return com.kingdee.eas.fdc.tenancy.FlagAtTermEnum.getEnum(getString("quitTenancyType"));
    }
    public void setQuitTenancyType(com.kingdee.eas.fdc.tenancy.FlagAtTermEnum item)
    {
		if (item != null) {
        setString("quitTenancyType", item.getValue());
		}
    }
    /**
     * Object:���ⵥ's �۳�����property 
     */
    public java.math.BigDecimal getDeductAmount()
    {
        return getBigDecimal("deductAmount");
    }
    public void setDeductAmount(java.math.BigDecimal item)
    {
        setBigDecimal("deductAmount", item);
    }
    /**
     * Object: ���ⵥ 's ����ԭ�� property 
     */
    public com.kingdee.eas.fdc.tenancy.SurrenderReasonInfo getNewQuitReason()
    {
        return (com.kingdee.eas.fdc.tenancy.SurrenderReasonInfo)get("newQuitReason");
    }
    public void setNewQuitReason(com.kingdee.eas.fdc.tenancy.SurrenderReasonInfo item)
    {
        put("newQuitReason", item);
    }
    /**
     * Object:���ⵥ's ����ȥ��property 
     */
    public String getQuitGoing()
    {
        return getString("quitGoing");
    }
    public void setQuitGoing(String item)
    {
        setString("quitGoing", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("756D1468");
    }
}