package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCReceivingBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractFDCReceivingBillInfo()
    {
        this("id");
    }
    protected AbstractFDCReceivingBillInfo(String pkField)
    {
        super(pkField);
        put("fdcCustomers", new com.kingdee.eas.fdc.basecrm.RevFDCCustomerEntryCollection());
        put("entries", new com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryCollection());
    }
    /**
     * Object: ���ز��տ 's ��˾ property 
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
     * Object: ���ز��տ 's Ӫ����Ŀ property 
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
     * Object: ���ز��տ 's �ұ� property 
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
     * Object: ���ز��տ 's ���ز��տ��¼ property 
     */
    public com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryCollection getEntries()
    {
        return (com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryCollection)get("entries");
    }
    /**
     * Object:���ز��տ's ����property 
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
     * Object:���ز��տ's �տ������property 
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
     * Object:���ز��տ's �տ�ҵ������property 
     */
    public com.kingdee.eas.fdc.basecrm.RevBizTypeEnum getRevBizType()
    {
        return com.kingdee.eas.fdc.basecrm.RevBizTypeEnum.getEnum(getString("revBizType"));
    }
    public void setRevBizType(com.kingdee.eas.fdc.basecrm.RevBizTypeEnum item)
    {
		if (item != null) {
        setString("revBizType", item.getValue());
		}
    }
    /**
     * Object: ���ز��տ 's �վ� property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChequeInfo getReceipt()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChequeInfo)get("receipt");
    }
    public void setReceipt(com.kingdee.eas.fdc.sellhouse.ChequeInfo item)
    {
        put("receipt", item);
    }
    /**
     * Object:���ز��տ's �վݺ�property 
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
     * Object: ���ز��տ 's ��Ʊ property 
     */
    public com.kingdee.eas.fdc.sellhouse.InvoiceInfo getInvoice()
    {
        return (com.kingdee.eas.fdc.sellhouse.InvoiceInfo)get("invoice");
    }
    public void setInvoice(com.kingdee.eas.fdc.sellhouse.InvoiceInfo item)
    {
        put("invoice", item);
    }
    /**
     * Object:���ز��տ's �վ�״̬property 
     */
    public com.kingdee.eas.fdc.sellhouse.ReceiptStatusEnum getReceiptState()
    {
        return com.kingdee.eas.fdc.sellhouse.ReceiptStatusEnum.getEnum(getString("receiptState"));
    }
    public void setReceiptState(com.kingdee.eas.fdc.sellhouse.ReceiptStatusEnum item)
    {
		if (item != null) {
        setString("receiptState", item.getValue());
		}
    }
    /**
     * Object: ���ز��տ 's �Ϲ��տ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseInfo getPurchaseObj()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseInfo)get("purchaseObj");
    }
    public void setPurchaseObj(com.kingdee.eas.fdc.sellhouse.PurchaseInfo item)
    {
        put("purchaseObj", item);
    }
    /**
     * Object: ���ز��տ 's �����Ϲ����� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo getSincerityObj()
    {
        return (com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo)get("sincerityObj");
    }
    public void setSincerityObj(com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo item)
    {
        put("sincerityObj", item);
    }
    /**
     * Object: ���ز��տ 's ���޳���Ԥ���տ���� property 
     */
    public com.kingdee.eas.fdc.tenancy.SincerObligateInfo getObligateObj()
    {
        return (com.kingdee.eas.fdc.tenancy.SincerObligateInfo)get("obligateObj");
    }
    public void setObligateObj(com.kingdee.eas.fdc.tenancy.SincerObligateInfo item)
    {
        put("obligateObj", item);
    }
    /**
     * Object: ���ز��տ 's ���޺�ͬ�տ���� property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillInfo getTenancyObj()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyBillInfo)get("tenancyObj");
    }
    public void setTenancyObj(com.kingdee.eas.fdc.tenancy.TenancyBillInfo item)
    {
        put("tenancyObj", item);
    }
    /**
     * Object: ���ز��տ 's ���ѿͻ� property 
     */
    public com.kingdee.eas.fdc.basecrm.RevFDCCustomerEntryCollection getFdcCustomers()
    {
        return (com.kingdee.eas.fdc.basecrm.RevFDCCustomerEntryCollection)get("fdcCustomers");
    }
    /**
     * Object: ���ز��տ 's �տ�ͻ� property 
     */
    public com.kingdee.eas.basedata.master.cssp.CustomerInfo getCustomer()
    {
        return (com.kingdee.eas.basedata.master.cssp.CustomerInfo)get("customer");
    }
    public void setCustomer(com.kingdee.eas.basedata.master.cssp.CustomerInfo item)
    {
        put("customer", item);
    }
    /**
     * Object:���ز��տ's ����״̬property 
     */
    public com.kingdee.eas.fdc.basecrm.RevBillStatusEnum getBillStatus()
    {
        return com.kingdee.eas.fdc.basecrm.RevBillStatusEnum.getEnum(getInt("billStatus"));
    }
    public void setBillStatus(com.kingdee.eas.fdc.basecrm.RevBillStatusEnum item)
    {
		if (item != null) {
        setInt("billStatus", item.getValue());
		}
    }
    /**
     * Object:���ز��տ's �Ƿ�������ƾ֤property 
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
     * Object:���ز��տ's ���䳤����property 
     */
    public String getRoomLongNumber()
    {
        return getString("roomLongNumber");
    }
    public void setRoomLongNumber(String item)
    {
        setString("roomLongNumber", item);
    }
    /**
     * Object: ���ز��տ 's ���� property 
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
     * Object: ���ز��տ 's ���޹��� property 
     */
    public com.kingdee.eas.base.permission.UserInfo getTenancyUser()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("tenancyUser");
    }
    public void setTenancyUser(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("tenancyUser", item);
    }
    /**
     * Object: ���ز��տ 's ����Դ�տ property 
     */
    public com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo getSrcRevBill()
    {
        return (com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo)get("srcRevBill");
    }
    public void setSrcRevBill(com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo item)
    {
        put("srcRevBill", item);
    }
    /**
     * Object:���ز��տ's �Ƿ����ɳ����տproperty 
     */
    public boolean isIsCreateBill()
    {
        return getBoolean("isCreateBill");
    }
    public void setIsCreateBill(boolean item)
    {
        setBoolean("isCreateBill", item);
    }
    /**
     * Object:���ز��տ's �Ƿ�����property 
     */
    public boolean isIsCollection()
    {
        return getBoolean("isCollection");
    }
    public void setIsCollection(boolean item)
    {
        setBoolean("isCollection", item);
    }
    /**
     * Object:���ز��տ's �Ƿ����½�property 
     */
    public boolean isIsMonthSettled()
    {
        return getBoolean("isMonthSettled");
    }
    public void setIsMonthSettled(boolean item)
    {
        setBoolean("isMonthSettled", item);
    }
    /**
     * Object:���ز��տ's ΥԼ���տ�property 
     */
    public java.math.BigDecimal getDeditAmount()
    {
        return getBigDecimal("deditAmount");
    }
    public void setDeditAmount(java.math.BigDecimal item)
    {
        setBigDecimal("deditAmount", item);
    }
    /**
     * Object: ���ز��տ 's ���������ڼ� property 
     */
    public com.kingdee.eas.basedata.assistant.PeriodInfo getChargePeriod()
    {
        return (com.kingdee.eas.basedata.assistant.PeriodInfo)get("chargePeriod");
    }
    public void setChargePeriod(com.kingdee.eas.basedata.assistant.PeriodInfo item)
    {
        put("chargePeriod", item);
    }
    /**
     * Object:���ز��տ's �Ƿ񰴿ͻ��տ�property 
     */
    public boolean isIsByCustomer()
    {
        return getBoolean("isByCustomer");
    }
    public void setIsByCustomer(boolean item)
    {
        setBoolean("isByCustomer", item);
    }
    /**
     * Object:���ز��տ's ���ͬ�����ֶ�property 
     */
    public String getTennacyStr()
    {
        return getString("tennacyStr");
    }
    public void setTennacyStr(String item)
    {
        setString("tennacyStr", item);
    }
    /**
     * Object:���ز��տ's �ͻ�����property 
     */
    public String getFdcCustomerStr()
    {
        return getString("fdcCustomerStr");
    }
    public void setFdcCustomerStr(String item)
    {
        setString("fdcCustomerStr", item);
    }
    /**
     * Object: ���ز��տ 's ת����ͬ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillInfo getTransOutTenBill()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyBillInfo)get("transOutTenBill");
    }
    public void setTransOutTenBill(com.kingdee.eas.fdc.tenancy.TenancyBillInfo item)
    {
        put("transOutTenBill", item);
    }
    /**
     * Object: ���ز��տ 's �տ��˻� property 
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
     * Object: ���ز��տ 's �տ��Ŀ property 
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
     * Object: ���ز��տ 's �տ����� property 
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
     * Object: ���ز��տ 's ���㷽ʽ property 
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
     * Object:���ز��տ's �����property 
     */
    public String getSettlementNumber()
    {
        return getString("settlementNumber");
    }
    public void setSettlementNumber(String item)
    {
        setString("settlementNumber", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F12182FE");
    }
}