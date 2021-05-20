package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractReceiveGatherEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractReceiveGatherEntryInfo()
    {
        this("id");
    }
    protected AbstractReceiveGatherEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ���ɻ��ܵ���ϸ 's ���ɻ��ܵ�ͷ property 
     */
    public com.kingdee.eas.fdc.sellhouse.ReceiveGatherInfo getReceiveGather()
    {
        return (com.kingdee.eas.fdc.sellhouse.ReceiveGatherInfo)get("receiveGather");
    }
    public void setReceiveGather(com.kingdee.eas.fdc.sellhouse.ReceiveGatherInfo item)
    {
        put("receiveGather", item);
    }
    /**
     * Object: ���ɻ��ܵ���ϸ 's �������� property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo getMoneyDefine()
    {
        return (com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo)get("moneyDefine");
    }
    public void setMoneyDefine(com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo item)
    {
        put("moneyDefine", item);
    }
    /**
     * Object: ���ɻ��ܵ���ϸ 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomInfo getRoom()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomInfo)get("room");
    }
    public void setRoom(com.kingdee.eas.fdc.sellhouse.RoomInfo item)
    {
        put("room", item);
    }
    /**
     * Object:���ɻ��ܵ���ϸ's �ͻ�����property 
     */
    public String getCustomerDisName()
    {
        return getString("customerDisName");
    }
    public void setCustomerDisName(String item)
    {
        setString("customerDisName", item);
    }
    /**
     * Object: ���ɻ��ܵ���ϸ 's ���㷽ʽ property 
     */
    public com.kingdee.eas.basedata.assistant.SettlementTypeInfo getSettlementType()
    {
        return (com.kingdee.eas.basedata.assistant.SettlementTypeInfo)get("settlementType");
    }
    public void setSettlementType(com.kingdee.eas.basedata.assistant.SettlementTypeInfo item)
    {
        put("settlementType", item);
    }
    /**
     * Object:���ɻ��ܵ���ϸ's ���㵥��property 
     */
    public String getSettlementNumber()
    {
        return getString("settlementNumber");
    }
    public void setSettlementNumber(String item)
    {
        setString("settlementNumber", item);
    }
    /**
     * Object:���ɻ��ܵ���ϸ's �ո����property 
     */
    public java.math.BigDecimal getRevAmount()
    {
        return getBigDecimal("revAmount");
    }
    public void setRevAmount(java.math.BigDecimal item)
    {
        setBigDecimal("revAmount", item);
    }
    /**
     * Object: ���ɻ��ܵ���ϸ 's �ͻ������˻� property 
     */
    public com.kingdee.eas.basedata.assistant.AccountBankInfo getCusAccountBank()
    {
        return (com.kingdee.eas.basedata.assistant.AccountBankInfo)get("cusAccountBank");
    }
    public void setCusAccountBank(com.kingdee.eas.basedata.assistant.AccountBankInfo item)
    {
        put("cusAccountBank", item);
    }
    /**
     * Object:���ɻ��ܵ���ϸ's �ͻ������˺�property 
     */
    public String getCusAccountBankNumber()
    {
        return getString("cusAccountBankNumber");
    }
    public void setCusAccountBankNumber(String item)
    {
        setString("cusAccountBankNumber", item);
    }
    /**
     * Object: ���ɻ��ܵ���ϸ 's �տ���ϸ property 
     */
    public com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo getSheRevBill()
    {
        return (com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo)get("sheRevBill");
    }
    public void setSheRevBill(com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo item)
    {
        put("sheRevBill", item);
    }
    /**
     * Object: ���ɻ��ܵ���ϸ 's �����տ��¼ property 
     */
    public com.kingdee.eas.fi.cas.ReceivingBillEntryInfo getReceivingBillEntry()
    {
        return (com.kingdee.eas.fi.cas.ReceivingBillEntryInfo)get("receivingBillEntry");
    }
    public void setReceivingBillEntry(com.kingdee.eas.fi.cas.ReceivingBillEntryInfo item)
    {
        put("receivingBillEntry", item);
    }
    /**
     * Object: ���ɻ��ܵ���ϸ 's �Է���Ŀ property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getOppAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("oppAccount");
    }
    public void setOppAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("oppAccount", item);
    }
    /**
     * Object:���ɻ��ܵ���ϸ's �վݺ�property 
     */
    public String getReceiptNumber()
    {
        return getString("receiptNumber");
    }
    public void setReceiptNumber(String item)
    {
        setString("receiptNumber", item);
    }
    /**
     * Object:���ɻ��ܵ���ϸ's ��Ʊ��property 
     */
    public String getInvoiceNumber()
    {
        return getString("invoiceNumber");
    }
    public void setInvoiceNumber(String item)
    {
        setString("invoiceNumber", item);
    }
    /**
     * Object:���ɻ��ܵ���ϸ's ��עproperty 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("EF155E6F");
    }
}