package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractWithoutTextInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractContractWithoutTextInfo()
    {
        this("id");
    }
    protected AbstractContractWithoutTextInfo(String pkField)
    {
        super(pkField);
        put("invoiceEntry", new com.kingdee.eas.fdc.contract.ContractWTInvoiceEntryCollection());
        put("feeEntry", new com.kingdee.eas.fdc.contract.FeeEntryCollection());
        put("marketEntry", new com.kingdee.eas.fdc.contract.ContractWTMarketEntryCollection());
        put("traEntry", new com.kingdee.eas.fdc.contract.TraEntryCollection());
        put("bgEntry", new com.kingdee.eas.fdc.contract.ContractWithoutTextBgEntryCollection());
    }
    /**
     * Object: ���ı���ͬ 's ������Ŀ property 
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
     * Object:���ı���ͬ's ʵ�ʸ�������property 
     */
    public java.util.Date getSignDate()
    {
        return getDate("signDate");
    }
    public void setSignDate(java.util.Date item)
    {
        setDate("signDate", item);
    }
    /**
     * Object: ���ı���ͬ 's �տ������� property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getReceiveUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("receiveUnit");
    }
    public void setReceiveUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("receiveUnit", item);
    }
    /**
     * Object: ���ı���ͬ 's ���㷽ʽ property 
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
     * Object: ���ı���ͬ 's �ұ� property 
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
     * Object:���ı���ͬ's ��������property 
     */
    public String getFiSettleNo()
    {
        return getString("fiSettleNo");
    }
    public void setFiSettleNo(String item)
    {
        setString("fiSettleNo", item);
    }
    /**
     * Object:���ı���ͬ's ���ɸ���ƻ�property 
     */
    public boolean isGenPaymentPlan()
    {
        return getBoolean("genPaymentPlan");
    }
    public void setGenPaymentPlan(boolean item)
    {
        setBoolean("genPaymentPlan", item);
    }
    /**
     * Object:���ı���ͬ's ���ɸ������뵥property 
     */
    public boolean isGenPaymentReque()
    {
        return getBoolean("genPaymentReque");
    }
    public void setGenPaymentReque(boolean item)
    {
        setBoolean("genPaymentReque", item);
    }
    /**
     * Object:���ı���ͬ's ���ɸ��property 
     */
    public boolean isGenPaymentBill()
    {
        return getBoolean("genPaymentBill");
    }
    public void setGenPaymentBill(boolean item)
    {
        setBoolean("genPaymentBill", item);
    }
    /**
     * Object:���ı���ͬ's ��������property 
     */
    public String getBank()
    {
        return getString("bank");
    }
    public void setBank(String item)
    {
        setString("bank", item);
    }
    /**
     * Object:���ı���ͬ's �����˺�property 
     */
    public String getBankAcct()
    {
        return getString("bankAcct");
    }
    public void setBankAcct(String item)
    {
        setString("bankAcct", item);
    }
    /**
     * Object:���ı���ͬ's �Ƿ�ɱ����property 
     */
    public boolean isIsCostSplit()
    {
        return getBoolean("isCostSplit");
    }
    public void setIsCostSplit(boolean item)
    {
        setBoolean("isCostSplit", item);
    }
    /**
     * Object:���ı���ͬ's ������״̬property 
     */
    public com.kingdee.eas.fdc.contract.ConSplitExecStateEnum getConSplitExecState()
    {
        return com.kingdee.eas.fdc.contract.ConSplitExecStateEnum.getEnum(getString("conSplitExecState"));
    }
    public void setConSplitExecState(com.kingdee.eas.fdc.contract.ConSplitExecStateEnum item)
    {
		if (item != null) {
        setString("conSplitExecState", item.getValue());
		}
    }
    /**
     * Object:���ı���ͬ's �Ƿ���Ҫ����property 
     */
    public boolean isIsNeedPaid()
    {
        return getBoolean("isNeedPaid");
    }
    public void setIsNeedPaid(boolean item)
    {
        setBoolean("isNeedPaid", item);
    }
    /**
     * Object:���ı���ͬ's ���踶��ԭ��property 
     */
    public String getNoPaidReason()
    {
        return getString("noPaidReason");
    }
    public void setNoPaidReason(String item)
    {
        setString("noPaidReason", item);
    }
    /**
     * Object:���ı���ͬ's ������Դ��ʽproperty 
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
     * Object: ���ı���ͬ 's ������Ŀ property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("account");
    }
    public void setAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("account", item);
    }
    /**
     * Object:���ı���ͬ's ���״̬property 
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
     * Object: ���ı���ͬ 's ��ͬ���� property 
     */
    public com.kingdee.eas.fdc.basedata.ContractTypeInfo getContractType()
    {
        return (com.kingdee.eas.fdc.basedata.ContractTypeInfo)get("contractType");
    }
    public void setContractType(com.kingdee.eas.fdc.basedata.ContractTypeInfo item)
    {
        put("contractType", item);
    }
    /**
     * Object: ���ı���ͬ 's ��ͬ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.ContractChargeTypeInfo getConChargeType()
    {
        return (com.kingdee.eas.fdc.basedata.ContractChargeTypeInfo)get("conChargeType");
    }
    public void setConChargeType(com.kingdee.eas.fdc.basedata.ContractChargeTypeInfo item)
    {
        put("conChargeType", item);
    }
    /**
     * Object: ���ı���ͬ 's ��ͬ������Ϣ property 
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
     * Object:���ı���ͬ's ��Ʊ��property 
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
     * Object:���ı���ͬ's ��Ʊ���property 
     */
    public java.math.BigDecimal getInvoiceAmt()
    {
        return getBigDecimal("invoiceAmt");
    }
    public void setInvoiceAmt(java.math.BigDecimal item)
    {
        setBigDecimal("invoiceAmt", item);
    }
    /**
     * Object:���ı���ͬ's �ۼƷ�Ʊ���property 
     */
    public double getAllInvoiceAmt()
    {
        return getDouble("allInvoiceAmt");
    }
    public void setAllInvoiceAmt(double item)
    {
        setDouble("allInvoiceAmt", item);
    }
    /**
     * Object:���ı���ͬ's ��Ʊ����property 
     */
    public java.util.Date getInvoiceDate()
    {
        return getDate("invoiceDate");
    }
    public void setInvoiceDate(java.util.Date item)
    {
        setDate("invoiceDate", item);
    }
    /**
     * Object: ���ı���ͬ 's �ÿ�� property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getUseDepartment()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("useDepartment");
    }
    public void setUseDepartment(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("useDepartment", item);
    }
    /**
     * Object: ���ı���ͬ 's ��ܺ�Լ property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo getProgrammingContract()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo)get("programmingContract");
    }
    public void setProgrammingContract(com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo item)
    {
        put("programmingContract", item);
    }
    /**
     * Object: ���ı���ͬ 's ��ͬ�¶ȹ����ƻ��޺�ͬ��¼ property 
     */
    public com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractInfo getFdcDepConPlan()
    {
        return (com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractInfo)get("fdcDepConPlan");
    }
    public void setFdcDepConPlan(com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractInfo item)
    {
        put("fdcDepConPlan", item);
    }
    /**
     * Object:���ı���ͬ's �Ƿ�Ԥ�����property 
     */
    public boolean isIsBgControl()
    {
        return getBoolean("isBgControl");
    }
    public void setIsBgControl(boolean item)
    {
        setBoolean("isBgControl", item);
    }
    /**
     * Object: ���ı���ͬ 's ������ property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getApplier()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("applier");
    }
    public void setApplier(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("applier", item);
    }
    /**
     * Object: ���ı���ͬ 's ���벿�� property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getApplierOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("applierOrgUnit");
    }
    public void setApplierOrgUnit(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("applierOrgUnit", item);
    }
    /**
     * Object: ���ı���ͬ 's �����˹�˾ property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getApplierCompany()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("applierCompany");
    }
    public void setApplierCompany(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("applierCompany", item);
    }
    /**
     * Object: ���ı���ͬ 's Ԥ��е����� property 
     */
    public com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo getCostedDept()
    {
        return (com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo)get("costedDept");
    }
    public void setCostedDept(com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo item)
    {
        put("costedDept", item);
    }
    /**
     * Object: ���ı���ͬ 's Ԥ��е���˾ property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getCostedCompany()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("costedCompany");
    }
    public void setCostedCompany(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("costedCompany", item);
    }
    /**
     * Object: ���ı���ͬ 's ��֯ property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getCompany()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("company");
    }
    public void setCompany(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("company", item);
    }
    /**
     * Object: ���ı���ͬ 's �����嵥 property 
     */
    public com.kingdee.eas.fdc.contract.ContractWithoutTextBgEntryCollection getBgEntry()
    {
        return (com.kingdee.eas.fdc.contract.ContractWithoutTextBgEntryCollection)get("bgEntry");
    }
    /**
     * Object:���ı���ͬ's ���޷�Ʊproperty 
     */
    public boolean isIsInvoice()
    {
        return getBoolean("isInvoice");
    }
    public void setIsInvoice(boolean item)
    {
        setBoolean("isInvoice", item);
    }
    /**
     * Object: ���ı���ͬ 's �������� property 
     */
    public com.kingdee.eas.fi.cas.PaymentBillTypeInfo getPayBillType()
    {
        return (com.kingdee.eas.fi.cas.PaymentBillTypeInfo)get("payBillType");
    }
    public void setPayBillType(com.kingdee.eas.fi.cas.PaymentBillTypeInfo item)
    {
        put("payBillType", item);
    }
    /**
     * Object: ���ı���ͬ 's �������� property 
     */
    public com.kingdee.eas.fdc.contract.PayContentTypeInfo getPayContentType()
    {
        return (com.kingdee.eas.fdc.contract.PayContentTypeInfo)get("payContentType");
    }
    public void setPayContentType(com.kingdee.eas.fdc.contract.PayContentTypeInfo item)
    {
        put("payContentType", item);
    }
    /**
     * Object: ���ı���ͬ 's ְԱ property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("person");
    }
    public void setPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("person", item);
    }
    /**
     * Object:���ı���ͬ's ������ϸ����property 
     */
    public com.kingdee.eas.fdc.contract.FeeTypeEnum getFeeType()
    {
        return com.kingdee.eas.fdc.contract.FeeTypeEnum.getEnum(getString("feeType"));
    }
    public void setFeeType(com.kingdee.eas.fdc.contract.FeeTypeEnum item)
    {
		if (item != null) {
        setString("feeType", item.getValue());
		}
    }
    /**
     * Object: ���ı���ͬ 's ���ñ������¼ property 
     */
    public com.kingdee.eas.fdc.contract.FeeEntryCollection getFeeEntry()
    {
        return (com.kingdee.eas.fdc.contract.FeeEntryCollection)get("feeEntry");
    }
    /**
     * Object: ���ı���ͬ 's ���÷ѱ������¼ property 
     */
    public com.kingdee.eas.fdc.contract.TraEntryCollection getTraEntry()
    {
        return (com.kingdee.eas.fdc.contract.TraEntryCollection)get("traEntry");
    }
    /**
     * Object:���ı���ͬ's ˰��property 
     */
    public java.math.BigDecimal getRateAmount()
    {
        return getBigDecimal("rateAmount");
    }
    public void setRateAmount(java.math.BigDecimal item)
    {
        setBigDecimal("rateAmount", item);
    }
    /**
     * Object: ���ı���ͬ 's ��Ʊ��¼ property 
     */
    public com.kingdee.eas.fdc.contract.ContractWTInvoiceEntryCollection getInvoiceEntry()
    {
        return (com.kingdee.eas.fdc.contract.ContractWTInvoiceEntryCollection)get("invoiceEntry");
    }
    /**
     * Object:���ı���ͬ's ��˰������property 
     */
    public com.kingdee.eas.fdc.contract.app.TaxerQuaEnum getTaxerQua()
    {
        return com.kingdee.eas.fdc.contract.app.TaxerQuaEnum.getEnum(getString("taxerQua"));
    }
    public void setTaxerQua(com.kingdee.eas.fdc.contract.app.TaxerQuaEnum item)
    {
		if (item != null) {
        setString("taxerQua", item.getValue());
		}
    }
    /**
     * Object:���ı���ͬ's ��˰��ʶ���property 
     */
    public String getTaxerNum()
    {
        return getString("taxerNum");
    }
    public void setTaxerNum(String item)
    {
        setString("taxerNum", item);
    }
    /**
     * Object: ���ı���ͬ 's Ӫ������ property 
     */
    public com.kingdee.eas.fdc.contract.MarketProjectInfo getMarketProject()
    {
        return (com.kingdee.eas.fdc.contract.MarketProjectInfo)get("marketProject");
    }
    public void setMarketProject(com.kingdee.eas.fdc.contract.MarketProjectInfo item)
    {
        put("marketProject", item);
    }
    /**
     * Object: ���ı���ͬ 's ��ͬ�������ϸ property 
     */
    public com.kingdee.eas.fdc.contract.ContractWTMarketEntryCollection getMarketEntry()
    {
        return (com.kingdee.eas.fdc.contract.ContractWTMarketEntryCollection)get("marketEntry");
    }
    /**
     * Object: ���ı���ͬ 's �ɱ���Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountInfo getMpCostAccount()
    {
        return (com.kingdee.eas.fdc.basedata.CostAccountInfo)get("mpCostAccount");
    }
    public void setMpCostAccount(com.kingdee.eas.fdc.basedata.CostAccountInfo item)
    {
        put("mpCostAccount", item);
    }
    /**
     * Object: ���ı���ͬ 's ���к� property 
     */
    public com.kingdee.eas.fdc.contract.BankNumInfo getLxNum()
    {
        return (com.kingdee.eas.fdc.contract.BankNumInfo)get("lxNum");
    }
    public void setLxNum(com.kingdee.eas.fdc.contract.BankNumInfo item)
    {
        put("lxNum", item);
    }
    /**
     * Object:���ı���ͬ's ִ������Ƿ���Ҫ���ź�����property 
     */
    public boolean isIsJT()
    {
        return getBoolean("isJT");
    }
    public void setIsJT(boolean item)
    {
        setBoolean("isJT", item);
    }
    /**
     * Object:���ı���ͬ's oaְλproperty 
     */
    public String getOaPosition()
    {
        return getString("oaPosition");
    }
    public void setOaPosition(String item)
    {
        setString("oaPosition", item);
    }
    /**
     * Object:���ı���ͬ's �������̷�����֯property 
     */
    public com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum getOrgType()
    {
        return com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum.getEnum(getString("orgType"));
    }
    public void setOrgType(com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum item)
    {
		if (item != null) {
        setString("orgType", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3D9A5388");
    }
}