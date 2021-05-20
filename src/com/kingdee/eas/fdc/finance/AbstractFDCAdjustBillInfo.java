package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCAdjustBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractFDCAdjustBillInfo()
    {
        this("id");
    }
    protected AbstractFDCAdjustBillInfo(String pkField)
    {
        super(pkField);
        put("infos", new com.kingdee.eas.fdc.finance.FDCAdjustBillAllInfoCollection());
        put("entrys", new com.kingdee.eas.fdc.finance.FDCAdjustBillEntryCollection());
    }
    /**
     * Object: ������ 's ������Ŀ property 
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
     * Object: ������ 's ��ͬ property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getContractBill()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("contractBill");
    }
    public void setContractBill(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("contractBill", item);
    }
    /**
     * Object:������'s ����ԭ��property 
     */
    public com.kingdee.eas.fdc.finance.VoucherAdjustReasonEnum getAdjustReason()
    {
        return com.kingdee.eas.fdc.finance.VoucherAdjustReasonEnum.getEnum(getString("adjustReason"));
    }
    public void setAdjustReason(com.kingdee.eas.fdc.finance.VoucherAdjustReasonEnum item)
    {
		if (item != null) {
        setString("adjustReason", item.getValue());
		}
    }
    /**
     * Object:������'s �Ƿ�ɱ�������property 
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
     * Object:������'s �Ƿ���Ҫƾ֤����property 
     */
    public boolean isIsNeedTraceVoucher()
    {
        return getBoolean("isNeedTraceVoucher");
    }
    public void setIsNeedTraceVoucher(boolean item)
    {
        setBoolean("isNeedTraceVoucher", item);
    }
    /**
     * Object:������'s �Ƿ�������ƾ֤property 
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
     * Object: ������ 's ƾ֤ property 
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
     * Object: ������ 's ȫ��Ϣ property 
     */
    public com.kingdee.eas.fdc.finance.FDCAdjustBillAllInfoCollection getInfos()
    {
        return (com.kingdee.eas.fdc.finance.FDCAdjustBillAllInfoCollection)get("infos");
    }
    /**
     * Object: ������ 's ��¼ property 
     */
    public com.kingdee.eas.fdc.finance.FDCAdjustBillEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.finance.FDCAdjustBillEntryCollection)get("entrys");
    }
    /**
     * Object: ������ 's ����ɱ�һ�廯��Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo getBeforeAccountView()
    {
        return (com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo)get("beforeAccountView");
    }
    public void setBeforeAccountView(com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo item)
    {
        put("beforeAccountView", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D24B04CC");
    }
}