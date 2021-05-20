package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSHERevBillEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSHERevBillEntryInfo()
    {
        this("id");
    }
    protected AbstractSHERevBillEntryInfo(String pkField)
    {
        super(pkField);
        put("revEntry", new com.kingdee.eas.fdc.basecrm.SHERevbillTwoEntryCollection());
    }
    /**
     * Object: �ո����¼ 's ����ͷ property 
     */
    public com.kingdee.eas.fdc.basecrm.SHERevBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.basecrm.SHERevBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.basecrm.SHERevBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:�ո����¼'s �տ���property 
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
     * Object: �ո����¼ 's �շѿ��� property 
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
     * Object: �ո����¼ 's ���㷽ʽ property 
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
     * Object:�ո����¼'s �����property 
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
     * Object:�ո����¼'s ���˽��property 
     */
    public java.math.BigDecimal getHasRefundmentAmount()
    {
        return getBigDecimal("hasRefundmentAmount");
    }
    public void setHasRefundmentAmount(java.math.BigDecimal item)
    {
        setBigDecimal("hasRefundmentAmount", item);
    }
    /**
     * Object:�ո����¼'s ��ת���property 
     */
    public java.math.BigDecimal getHasTransferAmount()
    {
        return getBigDecimal("hasTransferAmount");
    }
    public void setHasTransferAmount(java.math.BigDecimal item)
    {
        setBigDecimal("hasTransferAmount", item);
    }
    /**
     * Object:�ո����¼'s ��̬����ֽ�ת����˿��property 
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
     * Object:�ո����¼'s �ѶԳ���property 
     */
    public java.math.BigDecimal getHasMapedAmount()
    {
        return getBigDecimal("hasMapedAmount");
    }
    public void setHasMapedAmount(java.math.BigDecimal item)
    {
        setBigDecimal("hasMapedAmount", item);
    }
    /**
     * Object: �ո����¼ 's ���������˻� property 
     */
    public com.kingdee.eas.basedata.assistant.AccountBankInfo getRevAccountBank()
    {
        return (com.kingdee.eas.basedata.assistant.AccountBankInfo)get("revAccountBank");
    }
    public void setRevAccountBank(com.kingdee.eas.basedata.assistant.AccountBankInfo item)
    {
        put("revAccountBank", item);
    }
    /**
     * Object:�ո����¼'s ���������ʺ�property 
     */
    public String getRevAccountNumber()
    {
        return getString("revAccountNumber");
    }
    public void setRevAccountNumber(String item)
    {
        setString("revAccountNumber", item);
    }
    /**
     * Object:�ո����¼'s �ͻ������ʺ�property 
     */
    public String getCustomerBankNumber()
    {
        return getString("customerBankNumber");
    }
    public void setCustomerBankNumber(String item)
    {
        setString("customerBankNumber", item);
    }
    /**
     * Object:�ո����¼'s ��Ʊ״̬property 
     */
    public com.kingdee.eas.fdc.basecrm.InvoiceTypeEnum getInvoiceType()
    {
        return com.kingdee.eas.fdc.basecrm.InvoiceTypeEnum.getEnum(getString("invoiceType"));
    }
    public void setInvoiceType(com.kingdee.eas.fdc.basecrm.InvoiceTypeEnum item)
    {
		if (item != null) {
        setString("invoiceType", item.getValue());
		}
    }
    /**
     * Object:�ո����¼'s �վݺ�property 
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
     * Object:�ո����¼'s ��Ʊ��property 
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
     * Object:�ո����¼'s ת���˿��տ���ϸidproperty 
     */
    public String getTransferFromEntryId()
    {
        return getString("transferFromEntryId");
    }
    public void setTransferFromEntryId(String item)
    {
        setString("transferFromEntryId", item);
    }
    /**
     * Object:�ո����¼'s �Ƿ��ѻ���property 
     */
    public boolean isIsGathered()
    {
        return getBoolean("isGathered");
    }
    public void setIsGathered(boolean item)
    {
        setBoolean("isGathered", item);
    }
    /**
     * Object:�ո����¼'s �ѿ�Ʊ���property 
     */
    public java.math.BigDecimal getHasMakeInvoiceAmount()
    {
        return getBigDecimal("hasMakeInvoiceAmount");
    }
    public void setHasMakeInvoiceAmount(java.math.BigDecimal item)
    {
        setBigDecimal("hasMakeInvoiceAmount", item);
    }
    /**
     * Object: �ո����¼ 's ������¼ property 
     */
    public com.kingdee.eas.fdc.basecrm.SHERevbillTwoEntryCollection getRevEntry()
    {
        return (com.kingdee.eas.fdc.basecrm.SHERevbillTwoEntryCollection)get("revEntry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("98D7153C");
    }
}