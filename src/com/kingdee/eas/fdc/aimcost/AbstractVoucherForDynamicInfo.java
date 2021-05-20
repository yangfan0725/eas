package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractVoucherForDynamicInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractVoucherForDynamicInfo()
    {
        this("id");
    }
    protected AbstractVoucherForDynamicInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:Ŀ��ɱ�����ƾ֤'s �Ƿ�������ƾ֤property 
     */
    public boolean isFiVouchered()
    {
        return getBoolean("fiVouchered");
    }
    public void setFiVouchered(boolean item)
    {
        setBoolean("fiVouchered", item);
    }
    /**
     * Object: Ŀ��ɱ�����ƾ֤ 's ����ɱ�һ�廯��Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo getBeAccount()
    {
        return (com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo)get("beAccount");
    }
    public void setBeAccount(com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo item)
    {
        put("beAccount", item);
    }
    /**
     * Object: Ŀ��ɱ�����ƾ֤ 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    /**
     * Object: Ŀ��ɱ�����ƾ֤ 's ��Ʒ property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getProduct()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("product");
    }
    public void setProduct(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("product", item);
    }
    /**
     * Object: Ŀ��ɱ�����ƾ֤ 's ������¼ property 
     */
    public com.kingdee.eas.fdc.aimcost.AdjustRecordEntryInfo getAdjustEntry()
    {
        return (com.kingdee.eas.fdc.aimcost.AdjustRecordEntryInfo)get("adjustEntry");
    }
    public void setAdjustEntry(com.kingdee.eas.fdc.aimcost.AdjustRecordEntryInfo item)
    {
        put("adjustEntry", item);
    }
    /**
     * Object: Ŀ��ɱ�����ƾ֤ 's ƾ֤ property 
     */
    public com.kingdee.eas.fi.gl.VoucherInfo getVoucher()
    {
        return (com.kingdee.eas.fi.gl.VoucherInfo)get("voucher");
    }
    public void setVoucher(com.kingdee.eas.fi.gl.VoucherInfo item)
    {
        put("voucher", item);
    }
    /**
     * Object: Ŀ��ɱ�����ƾ֤ 's ��Ʒ���㵥 property 
     */
    public com.kingdee.eas.fdc.finance.FIProductSettleBillInfo getSettBill()
    {
        return (com.kingdee.eas.fdc.finance.FIProductSettleBillInfo)get("settBill");
    }
    public void setSettBill(com.kingdee.eas.fdc.finance.FIProductSettleBillInfo item)
    {
        put("settBill", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F5E6400D");
    }
}