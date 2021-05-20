package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSHEFunctionSetEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractSHEFunctionSetEntryInfo()
    {
        this("id");
    }
    protected AbstractSHEFunctionSetEntryInfo(String pkField)
    {
        super(pkField);
        put("productTypeEntry", new com.kingdee.eas.fdc.sellhouse.SHEFunProductTypeEntryCollection());
    }
    /**
     * Object: ����¥���÷�¼ 's ͷ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHEFunctionSetInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHEFunctionSetInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.SHEFunctionSetInfo item)
    {
        put("head", item);
    }
    /**
     * Object: ����¥���÷�¼ 's ��Ŀ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    /**
     * Object:����¥���÷�¼'s ԤԼ��ʧЧʱ��property 
     */
    public int getAppointInvalidLimit()
    {
        return getInt("appointInvalidLimit");
    }
    public void setAppointInvalidLimit(int item)
    {
        setInt("appointInvalidLimit", item);
    }
    /**
     * Object:����¥���÷�¼'s Ԥ����ʧЧʱ��property 
     */
    public int getBookingInvalidLimit()
    {
        return getInt("bookingInvalidLimit");
    }
    public void setBookingInvalidLimit(int item)
    {
        setInt("bookingInvalidLimit", item);
    }
    /**
     * Object:����¥���÷�¼'s �Ϲ���ʧЧʱ��property 
     */
    public int getPurchaseInvalidLimit()
    {
        return getInt("purchaseInvalidLimit");
    }
    public void setPurchaseInvalidLimit(int item)
    {
        setInt("purchaseInvalidLimit", item);
    }
    /**
     * Object:����¥���÷�¼'s �Ϲ���Ĭ��ǩԼʱ��property 
     */
    public int getPurchaseSignLimit()
    {
        return getInt("purchaseSignLimit");
    }
    public void setPurchaseSignLimit(int item)
    {
        setInt("purchaseSignLimit", item);
    }
    /**
     * Object:����¥���÷�¼'s ǩԼ��Ĭ��ʧЧʱ��property 
     */
    public int getSignInvalidLimit()
    {
        return getInt("signInvalidLimit");
    }
    public void setSignInvalidLimit(int item)
    {
        setInt("signInvalidLimit", item);
    }
    /**
     * Object:����¥���÷�¼'s �����property 
     */
    public java.math.BigDecimal getSincerAmount()
    {
        return getBigDecimal("sincerAmount");
    }
    public void setSincerAmount(java.math.BigDecimal item)
    {
        setBigDecimal("sincerAmount", item);
    }
    /**
     * Object:����¥���÷�¼'s Ԥ����property 
     */
    public java.math.BigDecimal getPurBookingAmount()
    {
        return getBigDecimal("purBookingAmount");
    }
    public void setPurBookingAmount(java.math.BigDecimal item)
    {
        setBigDecimal("purBookingAmount", item);
    }
    /**
     * Object:����¥���÷�¼'s ����property 
     */
    public java.math.BigDecimal getBookingAmount()
    {
        return getBigDecimal("bookingAmount");
    }
    public void setBookingAmount(java.math.BigDecimal item)
    {
        setBigDecimal("bookingAmount", item);
    }
    /**
     * Object: ����¥���÷�¼ 's ��Ʒ���ͷ�¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHEFunProductTypeEntryCollection getProductTypeEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHEFunProductTypeEntryCollection)get("productTypeEntry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0871233D");
    }
}