package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDeductBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractDeductBillInfo()
    {
        this("id");
    }
    protected AbstractDeductBillInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.finance.DeductBillEntryCollection());
    }
    /**
     * Object: �ۿ 's ��¼ property 
     */
    public com.kingdee.eas.fdc.finance.DeductBillEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.finance.DeductBillEntryCollection)get("entrys");
    }
    /**
     * Object:�ۿ's �ۿ������ں�֮ͬǰproperty 
     */
    public boolean isConTypeBefContr()
    {
        return getBoolean("conTypeBefContr");
    }
    public void setConTypeBefContr(boolean item)
    {
        setBoolean("conTypeBefContr", item);
    }
    /**
     * Object: �ۿ 's ������Ŀ property 
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
     * Object:�ۿ's ������Դ��ʽproperty 
     */
    public com.kingdee.eas.fdc.basedata.SourceTypeEnum getSourceType()
    {
        return com.kingdee.eas.fdc.basedata.SourceTypeEnum.getEnum(getInt("sourceType"));
    }
    public void setSourceType(com.kingdee.eas.fdc.basedata.SourceTypeEnum item)
    {
		if (item != null) {
        setInt("sourceType", item.getValue());
		}
    }
    /**
     * Object:�ۿ's ���IDproperty 
     */
    public String getPaymentId()
    {
        return getString("paymentId");
    }
    public void setPaymentId(String item)
    {
        setString("paymentId", item);
    }
    /**
     * Object:�ۿ's �Ƿ�������ƾ֤property 
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
     * Object: �ۿ 's ƾ֤ property 
     */
    public com.kingdee.eas.fi.gl.VoucherInfo getVoucher()
    {
        return (com.kingdee.eas.fi.gl.VoucherInfo)get("voucher");
    }
    public void setVoucher(com.kingdee.eas.fi.gl.VoucherInfo item)
    {
        put("voucher", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("953E59B9");
    }
}