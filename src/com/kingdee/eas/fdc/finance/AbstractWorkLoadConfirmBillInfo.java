package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractWorkLoadConfirmBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractWorkLoadConfirmBillInfo()
    {
        this("id");
    }
    protected AbstractWorkLoadConfirmBillInfo(String pkField)
    {
        super(pkField);
        put("reftaskentry", new com.kingdee.eas.fdc.finance.WorkLoadConfirmBillRelTaskCollection());
        put("entrys", new com.kingdee.eas.fdc.finance.WorkLoadConfirmBillEntryCollection());
        put("costVoucherEntrys", new com.kingdee.eas.fdc.finance.WorkLoadCostVoucherEntryCollection());
        put("payVoucherEntrys", new com.kingdee.eas.fdc.finance.WorkLoadPayVoucherEntryCollection());
        put("prjFillBillEntry", new com.kingdee.eas.fdc.finance.WorkLoadPrjBillEntryCollection());
    }
    /**
     * Object: ������ȷ�ϵ� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.finance.WorkLoadConfirmBillEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.finance.WorkLoadConfirmBillEntryCollection)get("entrys");
    }
    /**
     * Object: ������ȷ�ϵ� 's ������Ŀ property 
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
     * Object: ������ȷ�ϵ� 's ��ͬ property 
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
     * Object:������ȷ�ϵ�'s ȷ�Ϲ�����property 
     */
    public java.math.BigDecimal getWorkLoad()
    {
        return getBigDecimal("workLoad");
    }
    public void setWorkLoad(java.math.BigDecimal item)
    {
        setBigDecimal("workLoad", item);
    }
    /**
     * Object:������ȷ�ϵ�'s ȷ������property 
     */
    public java.util.Date getConfirmDate()
    {
        return getDate("confirmDate");
    }
    public void setConfirmDate(java.util.Date item)
    {
        setDate("confirmDate", item);
    }
    /**
     * Object: ������ȷ�ϵ� 's ƾ֤ property 
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
     * Object:������ȷ�ϵ�'s �Ƿ�����ƾ֤property 
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
     * Object: ������ȷ�ϵ� 's ƾ֤�ɱ���¼ property 
     */
    public com.kingdee.eas.fdc.finance.WorkLoadCostVoucherEntryCollection getCostVoucherEntrys()
    {
        return (com.kingdee.eas.fdc.finance.WorkLoadCostVoucherEntryCollection)get("costVoucherEntrys");
    }
    /**
     * Object: ������ȷ�ϵ� 's ƾ֤�����¼ property 
     */
    public com.kingdee.eas.fdc.finance.WorkLoadPayVoucherEntryCollection getPayVoucherEntrys()
    {
        return (com.kingdee.eas.fdc.finance.WorkLoadPayVoucherEntryCollection)get("payVoucherEntrys");
    }
    /**
     * Object: ������ȷ�ϵ� 's ��˾ property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getCompany()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("company");
    }
    public void setCompany(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("company", item);
    }
    /**
     * Object:������ȷ�ϵ�'s ���״̬property 
     */
    public com.kingdee.eas.fdc.basedata.CostSplitStateEnum getSplitState()
    {
        return com.kingdee.eas.fdc.basedata.CostSplitStateEnum.getEnum(getString("splitState"));
    }
    public void setSplitState(com.kingdee.eas.fdc.basedata.CostSplitStateEnum item)
    {
		if (item != null) {
        setString("splitState", item.getValue());
		}
    }
    /**
     * Object:������ȷ�ϵ�'s �Ƿ���������property 
     */
    public boolean isHasSettled()
    {
        return getBoolean("hasSettled");
    }
    public void setHasSettled(boolean item)
    {
        setBoolean("hasSettled", item);
    }
    /**
     * Object: ������ȷ�ϵ� 's ���������ϸ property 
     */
    public com.kingdee.eas.fdc.finance.WorkLoadPrjBillEntryCollection getPrjFillBillEntry()
    {
        return (com.kingdee.eas.fdc.finance.WorkLoadPrjBillEntryCollection)get("prjFillBillEntry");
    }
    /**
     * Object: ������ȷ�ϵ� 's �����������¼ property 
     */
    public com.kingdee.eas.fdc.finance.WorkLoadConfirmBillRelTaskCollection getReftaskentry()
    {
        return (com.kingdee.eas.fdc.finance.WorkLoadConfirmBillRelTaskCollection)get("reftaskentry");
    }
    /**
     * Object:������ȷ�ϵ�'s Ӧ������property 
     */
    public java.math.BigDecimal getAppAmount()
    {
        return getBigDecimal("appAmount");
    }
    public void setAppAmount(java.math.BigDecimal item)
    {
        setBigDecimal("appAmount", item);
    }
    /**
     * Object:������ȷ�ϵ�'s Ӧ������property 
     */
    public java.math.BigDecimal getAppRate()
    {
        return getBigDecimal("appRate");
    }
    public void setAppRate(java.math.BigDecimal item)
    {
        setBigDecimal("appRate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E4A3CD61");
    }
}