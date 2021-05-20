package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPaymentSplitInfo extends com.kingdee.eas.fdc.basedata.FDCSplitBillInfo implements Serializable 
{
    public AbstractPaymentSplitInfo()
    {
        this("id");
    }
    protected AbstractPaymentSplitInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.finance.PaymentSplitEntryCollection());
        put("voucherEntrys", new com.kingdee.eas.fdc.finance.PaySplit4VoucherCollection());
    }
    /**
     * Object: ������ 's ��¼ property 
     */
    public com.kingdee.eas.fdc.finance.PaymentSplitEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.finance.PaymentSplitEntryCollection)get("entrys");
    }
    /**
     * Object: ������ 's ��� property 
     */
    public com.kingdee.eas.fi.cas.PaymentBillInfo getPaymentBill()
    {
        return (com.kingdee.eas.fi.cas.PaymentBillInfo)get("paymentBill");
    }
    public void setPaymentBill(com.kingdee.eas.fi.cas.PaymentBillInfo item)
    {
        put("paymentBill", item);
    }
    /**
     * Object:������'s ���깤���������property 
     */
    public java.math.BigDecimal getCompletePrjAmt()
    {
        return getBigDecimal("completePrjAmt");
    }
    public void setCompletePrjAmt(java.math.BigDecimal item)
    {
        setBigDecimal("completePrjAmt", item);
    }
    /**
     * Object:������'s �Ƿ������ı���ͬproperty 
     */
    public boolean isIsConWithoutText()
    {
        return getBoolean("isConWithoutText");
    }
    public void setIsConWithoutText(boolean item)
    {
        setBoolean("isConWithoutText", item);
    }
    /**
     * Object:������'s �Ƿ��һ�ν���property 
     */
    public boolean isIsProgress()
    {
        return getBoolean("isProgress");
    }
    public void setIsProgress(boolean item)
    {
        setBoolean("isProgress", item);
    }
    /**
     * Object: ������ 's Ԥ���Ŀ property 
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
     * Object: ������ 's ��ʷ������Ŀ״̬ property 
     */
    public com.kingdee.eas.fdc.basedata.ProjectStatusInfo getHisStatus()
    {
        return (com.kingdee.eas.fdc.basedata.ProjectStatusInfo)get("hisStatus");
    }
    public void setHisStatus(com.kingdee.eas.fdc.basedata.ProjectStatusInfo item)
    {
        put("hisStatus", item);
    }
    /**
     * Object: ������ 's ƾ֤ property 
     */
    public com.kingdee.eas.fdc.finance.PaySplit4VoucherCollection getVoucherEntrys()
    {
        return (com.kingdee.eas.fdc.finance.PaySplit4VoucherCollection)get("voucherEntrys");
    }
    /**
     * Object:������'s ����ƾ֤����property 
     */
    public int getVoucherTimes()
    {
        return getInt("voucherTimes");
    }
    public void setVoucherTimes(int item)
    {
        setInt("voucherTimes", item);
    }
    /**
     * Object: ������ 's ��ʷƾ֤ property 
     */
    public com.kingdee.eas.fi.gl.VoucherInfo getHisVoucher()
    {
        return (com.kingdee.eas.fi.gl.VoucherInfo)get("hisVoucher");
    }
    public void setHisVoucher(com.kingdee.eas.fi.gl.VoucherInfo item)
    {
        put("hisVoucher", item);
    }
    /**
     * Object:������'s ���޿�property 
     */
    public java.math.BigDecimal getQualityAmount()
    {
        return getBigDecimal("qualityAmount");
    }
    public void setQualityAmount(java.math.BigDecimal item)
    {
        setBigDecimal("qualityAmount", item);
    }
    /**
     * Object:������'s Ӧ�����property 
     */
    public java.math.BigDecimal getPayAmount()
    {
        return getBigDecimal("payAmount");
    }
    public void setPayAmount(java.math.BigDecimal item)
    {
        setBigDecimal("payAmount", item);
    }
    /**
     * Object: ������ 's ��ͬ�������� property 
     */
    public com.kingdee.eas.fdc.contract.ContractBaseDataInfo getContractBaseData()
    {
        return (com.kingdee.eas.fdc.contract.ContractBaseDataInfo)get("contractBaseData");
    }
    public void setContractBaseData(com.kingdee.eas.fdc.contract.ContractBaseDataInfo item)
    {
        put("contractBaseData", item);
    }
    /**
     * Object:������'s �Ƿ��Ѿ����property 
     */
    public boolean isIsRedVouchered()
    {
        return getBoolean("isRedVouchered");
    }
    public void setIsRedVouchered(boolean item)
    {
        setBoolean("isRedVouchered", item);
    }
    /**
     * Object:������'s �Ƿ���Ҫ��תproperty 
     */
    public boolean isIsNeedTransit()
    {
        return getBoolean("isNeedTransit");
    }
    public void setIsNeedTransit(boolean item)
    {
        setBoolean("isNeedTransit", item);
    }
    /**
     * Object: ������ 's ��ת��Ŀ property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getTransitAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("transitAccount");
    }
    public void setTransitAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("transitAccount", item);
    }
    /**
     * Object: ������ 's ���ı���ͬ property 
     */
    public com.kingdee.eas.fdc.contract.ContractWithoutTextInfo getConWithoutText()
    {
        return (com.kingdee.eas.fdc.contract.ContractWithoutTextInfo)get("conWithoutText");
    }
    public void setConWithoutText(com.kingdee.eas.fdc.contract.ContractWithoutTextInfo item)
    {
        put("conWithoutText", item);
    }
    /**
     * Object:������'s �Ƿ�ر�property 
     */
    public boolean isIsColsed()
    {
        return getBoolean("isColsed");
    }
    public void setIsColsed(boolean item)
    {
        setBoolean("isColsed", item);
    }
    /**
     * Object:������'s �Ƿ񱻸��������ƾ֤property 
     */
    public boolean isIsUsedByPayment()
    {
        return getBoolean("isUsedByPayment");
    }
    public void setIsUsedByPayment(boolean item)
    {
        setBoolean("isUsedByPayment", item);
    }
    /**
     * Object:������'s ����ƾ֤�����õ���property 
     */
    public com.kingdee.eas.fdc.finance.PaySplitVoucherRefersEnum getVoucherRefer()
    {
        return com.kingdee.eas.fdc.finance.PaySplitVoucherRefersEnum.getEnum(getString("voucherRefer"));
    }
    public void setVoucherRefer(com.kingdee.eas.fdc.finance.PaySplitVoucherRefersEnum item)
    {
		if (item != null) {
        setString("voucherRefer", item.getValue());
		}
    }
    /**
     * Object:������'s ƾ֤����Դ��IDproperty 
     */
    public String getVoucherReferId()
    {
        return getString("voucherReferId");
    }
    public void setVoucherReferId(String item)
    {
        setString("voucherReferId", item);
    }
    /**
     * Object:������'s �Ƿ��ǳ�������ǰ�ĸ�����property 
     */
    public boolean isIsBeforeAdjust()
    {
        return getBoolean("isBeforeAdjust");
    }
    public void setIsBeforeAdjust(boolean item)
    {
        setBoolean("isBeforeAdjust", item);
    }
    /**
     * Object: ������ 's ������ȷ�ϵ� property 
     */
    public com.kingdee.eas.fdc.finance.WorkLoadConfirmBillInfo getWorkLoadConfirmBill()
    {
        return (com.kingdee.eas.fdc.finance.WorkLoadConfirmBillInfo)get("workLoadConfirmBill");
    }
    public void setWorkLoadConfirmBill(com.kingdee.eas.fdc.finance.WorkLoadConfirmBillInfo item)
    {
        put("workLoadConfirmBill", item);
    }
    /**
     * Object:������'s �Ƿ��ǹ�����ȷ�ϵ�property 
     */
    public boolean isIsWorkLoadBill()
    {
        return getBoolean("isWorkLoadBill");
    }
    public void setIsWorkLoadBill(boolean item)
    {
        setBoolean("isWorkLoadBill", item);
    }
    /**
     * Object:������'s ��Ʊ���property 
     */
    public java.math.BigDecimal getInvoiceAmt()
    {
        return getBigDecimal("invoiceAmt");
    }
    public void setInvoiceAmt(java.math.BigDecimal item)
    {
        setBigDecimal("invoiceAmt", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("962DB343");
    }
}