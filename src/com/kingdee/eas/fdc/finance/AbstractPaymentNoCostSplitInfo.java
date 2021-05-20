package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPaymentNoCostSplitInfo extends com.kingdee.eas.fdc.basedata.FDCNoCostSplitBillInfo implements Serializable 
{
    public AbstractPaymentNoCostSplitInfo()
    {
        this("id");
    }
    protected AbstractPaymentNoCostSplitInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryCollection());
        put("voucherEntrys", new com.kingdee.eas.fdc.finance.PayNoCostSplit4VoucherCollection());
    }
    /**
     * Object: ����ǳɱ���Ŀ��� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryCollection)get("entrys");
    }
    /**
     * Object: ����ǳɱ���Ŀ��� 's ��� property 
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
     * Object:����ǳɱ���Ŀ���'s ���깤���������property 
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
     * Object:����ǳɱ���Ŀ���'s �Ƿ������ı���ͬproperty 
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
     * Object:����ǳɱ���Ŀ���'s �Ƿ��һ�ν���property 
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
     * Object: ����ǳɱ���Ŀ��� 's Ԥ���Ŀ property 
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
     * Object: ����ǳɱ���Ŀ��� 's ��ʷ������Ŀ״̬ property 
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
     * Object: ����ǳɱ���Ŀ��� 's ƾ֤ property 
     */
    public com.kingdee.eas.fdc.finance.PayNoCostSplit4VoucherCollection getVoucherEntrys()
    {
        return (com.kingdee.eas.fdc.finance.PayNoCostSplit4VoucherCollection)get("voucherEntrys");
    }
    /**
     * Object:����ǳɱ���Ŀ���'s ����ƾ֤����property 
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
     * Object: ����ǳɱ���Ŀ��� 's ��ʷƾ֤ property 
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
     * Object:����ǳɱ���Ŀ���'s ���޿�property 
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
     * Object:����ǳɱ���Ŀ���'s Ӧ�����property 
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
     * Object: ����ǳɱ���Ŀ��� 's ��ͬ�������� property 
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
     * Object:����ǳɱ���Ŀ���'s �Ƿ��Ѿ����property 
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
     * Object:����ǳɱ���Ŀ���'s �Ƿ���Ҫ��תproperty 
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
     * Object: ����ǳɱ���Ŀ��� 's ��ת��Ŀ property 
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
     * Object: ����ǳɱ���Ŀ��� 's ���ı���ͬ property 
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
     * Object:����ǳɱ���Ŀ���'s �Ƿ񱻸��������ƾ֤property 
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
     * Object:����ǳɱ���Ŀ���'s ����ƾ֤�����õ���property 
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
     * Object:����ǳɱ���Ŀ���'s ƾ֤���õ�Դ��IDproperty 
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
     * Object:����ǳɱ���Ŀ���'s �Ƿ��ǳ�������ǰ�ĸ�����property 
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
     * Object:����ǳɱ���Ŀ���'s �Ƿ�ر�property 
     */
    public boolean isIsClosed()
    {
        return getBoolean("isClosed");
    }
    public void setIsClosed(boolean item)
    {
        setBoolean("isClosed", item);
    }
    /**
     * Object:����ǳɱ���Ŀ���'s ��Ʊ���property 
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
        return new BOSObjectType("BD7F63F5");
    }
}