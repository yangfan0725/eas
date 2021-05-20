package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSHERevBillInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractSHERevBillInfo()
    {
        this("id");
    }
    protected AbstractSHERevBillInfo(String pkField)
    {
        super(pkField);
        put("customerEntry", new com.kingdee.eas.fdc.basecrm.SHERevCustEntryCollection());
        put("entrys", new com.kingdee.eas.fdc.basecrm.SHERevBillEntryCollection());
    }
    /**
     * Object: ��¥�ո�� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.basecrm.SHERevBillEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.basecrm.SHERevBillEntryCollection)get("entrys");
    }
    /**
     * Object:��¥�ո��'s �Ƿ�����ƾ֤property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    /**
     * Object: ��¥�ո�� 's ������֯ property 
     */
    public com.kingdee.eas.basedata.org.SaleOrgUnitInfo getSaleOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.SaleOrgUnitInfo)get("saleOrgUnit");
    }
    public void setSaleOrgUnit(com.kingdee.eas.basedata.org.SaleOrgUnitInfo item)
    {
        put("saleOrgUnit", item);
    }
    /**
     * Object: ��¥�ո�� 's �ұ� property 
     */
    public com.kingdee.eas.basedata.assistant.CurrencyInfo getCurrency()
    {
        return (com.kingdee.eas.basedata.assistant.CurrencyInfo)get("currency");
    }
    public void setCurrency(com.kingdee.eas.basedata.assistant.CurrencyInfo item)
    {
        put("currency", item);
    }
    /**
     * Object: ��¥�ո�� 's ��Ŀ property 
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
     * Object:��¥�ո��'s ����property 
     */
    public java.math.BigDecimal getExchangeRate()
    {
        return getBigDecimal("exchangeRate");
    }
    public void setExchangeRate(java.math.BigDecimal item)
    {
        setBigDecimal("exchangeRate", item);
    }
    /**
     * Object:��¥�ո��'s �տ���property 
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
     * Object: ��¥�ո�� 's ��Դ property 
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
     * Object:��¥�ո��'s ��λ�ҽ��property 
     */
    public java.math.BigDecimal getOriginalAmount()
    {
        return getBigDecimal("originalAmount");
    }
    public void setOriginalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("originalAmount", item);
    }
    /**
     * Object:��¥�ո��'s �տ��������property 
     */
    public com.kingdee.eas.fdc.basecrm.RevBillTypeEnum getRevBillType()
    {
        return com.kingdee.eas.fdc.basecrm.RevBillTypeEnum.getEnum(getString("revBillType"));
    }
    public void setRevBillType(com.kingdee.eas.fdc.basecrm.RevBillTypeEnum item)
    {
		if (item != null) {
        setString("revBillType", item.getValue());
		}
    }
    /**
     * Object:��¥�ո��'s ����ҵ�񵥾�����property 
     */
    public com.kingdee.eas.fdc.basecrm.RelatBizType getRelateBizType()
    {
        return com.kingdee.eas.fdc.basecrm.RelatBizType.getEnum(getString("relateBizType"));
    }
    public void setRelateBizType(com.kingdee.eas.fdc.basecrm.RelatBizType item)
    {
		if (item != null) {
        setString("relateBizType", item.getValue());
		}
    }
    /**
     * Object:��¥�ո��'s ����ҵ�񵥾�idproperty 
     */
    public String getRelateBizBillId()
    {
        return getString("relateBizBillId");
    }
    public void setRelateBizBillId(String item)
    {
        setString("relateBizBillId", item);
    }
    /**
     * Object:��¥�ո��'s ����ҵ�񵥾ݱ���property 
     */
    public String getRelateBizBillNumber()
    {
        return getString("relateBizBillNumber");
    }
    public void setRelateBizBillNumber(String item)
    {
        setString("relateBizBillNumber", item);
    }
    /**
     * Object:��¥�ո��'s �����Ľ�������idproperty 
     */
    public String getRelateTransId()
    {
        return getString("relateTransId");
    }
    public void setRelateTransId(String item)
    {
        setString("relateTransId", item);
    }
    /**
     * Object:��¥�ո��'s ת��������������property 
     */
    public com.kingdee.eas.fdc.basecrm.RelatBizType getRelateFromBizType()
    {
        return com.kingdee.eas.fdc.basecrm.RelatBizType.getEnum(getString("relateFromBizType"));
    }
    public void setRelateFromBizType(com.kingdee.eas.fdc.basecrm.RelatBizType item)
    {
		if (item != null) {
        setString("relateFromBizType", item.getValue());
		}
    }
    /**
     * Object:��¥�ո��'s ת����������idproperty 
     */
    public String getRelateFromBizId()
    {
        return getString("relateFromBizId");
    }
    public void setRelateFromBizId(String item)
    {
        setString("relateFromBizId", item);
    }
    /**
     * Object:��¥�ո��'s ת���������ݱ���property 
     */
    public String getRelateFromBizNumber()
    {
        return getString("relateFromBizNumber");
    }
    public void setRelateFromBizNumber(String item)
    {
        setString("relateFromBizNumber", item);
    }
    /**
     * Object:��¥�ո��'s ת���������ݵĽ�������idproperty 
     */
    public String getRelateFromTransId()
    {
        return getString("relateFromTransId");
    }
    public void setRelateFromTransId(String item)
    {
        setString("relateFromTransId", item);
    }
    /**
     * Object: ��¥�ո�� 's ת������ͻ� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHECustomerInfo getRelateFromCust()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHECustomerInfo)get("relateFromCust");
    }
    public void setRelateFromCust(com.kingdee.eas.fdc.sellhouse.SHECustomerInfo item)
    {
        put("relateFromCust", item);
    }
    /**
     * Object:��¥�ո��'s ������property 
     */
    public String getPayCustomerName()
    {
        return getString("payCustomerName");
    }
    public void setPayCustomerName(String item)
    {
        setString("payCustomerName", item);
    }
    /**
     * Object: ��¥�ո�� 's �վ� property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryInfo getReceipt()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryInfo)get("receipt");
    }
    public void setReceipt(com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryInfo item)
    {
        put("receipt", item);
    }
    /**
     * Object:��¥�ո��'s �վݺ�property 
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
     * Object: ��¥�ո�� 's ��Ʊ property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryInfo getInvoice()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryInfo)get("invoice");
    }
    public void setInvoice(com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryInfo item)
    {
        put("invoice", item);
    }
    /**
     * Object:��¥�ո��'s ��Ʊ��property 
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
     * Object: ��¥�ո�� 's ϵͳ�ͻ� property 
     */
    public com.kingdee.eas.basedata.master.cssp.CustomerInfo getSysCustomer()
    {
        return (com.kingdee.eas.basedata.master.cssp.CustomerInfo)get("sysCustomer");
    }
    public void setSysCustomer(com.kingdee.eas.basedata.master.cssp.CustomerInfo item)
    {
        put("sysCustomer", item);
    }
    /**
     * Object:��¥�ո��'s ����ʱ��property 
     */
    public java.util.Date getAuditTime()
    {
        return getDate("auditTime");
    }
    public void setAuditTime(java.util.Date item)
    {
        setDate("auditTime", item);
    }
    /**
     * Object:��¥�ո��'s ����״̬property 
     */
    public com.kingdee.eas.fdc.basedata.FDCBillStateEnum getState()
    {
        return com.kingdee.eas.fdc.basedata.FDCBillStateEnum.getEnum(getString("state"));
    }
    public void setState(com.kingdee.eas.fdc.basedata.FDCBillStateEnum item)
    {
		if (item != null) {
        setString("state", item.getValue());
		}
    }
    /**
     * Object:��¥�ո��'s ��ͻ�����property 
     */
    public String getCustomerNames()
    {
        return getString("customerNames");
    }
    public void setCustomerNames(String item)
    {
        setString("customerNames", item);
    }
    /**
     * Object:��¥�ո��'s ��ͻ�IDproperty 
     */
    public String getCustomerIds()
    {
        return getString("customerIds");
    }
    public void setCustomerIds(String item)
    {
        setString("customerIds", item);
    }
    /**
     * Object: ��¥�ո�� 's �ͻ���¼ property 
     */
    public com.kingdee.eas.fdc.basecrm.SHERevCustEntryCollection getCustomerEntry()
    {
        return (com.kingdee.eas.fdc.basecrm.SHERevCustEntryCollection)get("customerEntry");
    }
    /**
     * Object:��¥�ո��'s ת�����ɵ��տIDproperty 
     */
    public String getTrsToGatherId()
    {
        return getString("trsToGatherId");
    }
    public void setTrsToGatherId(String item)
    {
        setString("trsToGatherId", item);
    }
    /**
     * Object:��¥�ո��'s ת�����ɵ��˿IDproperty 
     */
    public String getTrsToRefundId()
    {
        return getString("trsToRefundId");
    }
    public void setTrsToRefundId(String item)
    {
        setString("trsToRefundId", item);
    }
    /**
     * Object:��¥�ո��'s �Ƿ���ת��Զ�����property 
     */
    public boolean isIsTansCreate()
    {
        return getBoolean("isTansCreate");
    }
    public void setIsTansCreate(boolean item)
    {
        setBoolean("isTansCreate", item);
    }
    /**
     * Object:��¥�ո��'s �Ƿ��ѻ���property 
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
     * Object: ��¥�ո�� 's �տ��˻� property 
     */
    public com.kingdee.eas.basedata.assistant.AccountBankInfo getAccountBank()
    {
        return (com.kingdee.eas.basedata.assistant.AccountBankInfo)get("accountBank");
    }
    public void setAccountBank(com.kingdee.eas.basedata.assistant.AccountBankInfo item)
    {
        put("accountBank", item);
    }
    /**
     * Object: ��¥�ո�� 's �տ��Ŀ property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getRevAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("revAccount");
    }
    public void setRevAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("revAccount", item);
    }
    /**
     * Object: ��¥�ո�� 's �տ����� property 
     */
    public com.kingdee.eas.basedata.assistant.BankInfo getBank()
    {
        return (com.kingdee.eas.basedata.assistant.BankInfo)get("bank");
    }
    public void setBank(com.kingdee.eas.basedata.assistant.BankInfo item)
    {
        put("bank", item);
    }
    /**
     * Object: ��¥�ո�� 's ���㷽ʽ property 
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
     * Object:��¥�ո��'s �����property 
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
     * Object:��¥�ո��'s ��������property 
     */
    public String getMoneyDefine()
    {
        return getString("moneyDefine");
    }
    public void setMoneyDefine(String item)
    {
        setString("moneyDefine", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DFC518D6");
    }
}