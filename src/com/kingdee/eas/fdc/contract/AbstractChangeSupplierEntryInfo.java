package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractChangeSupplierEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractChangeSupplierEntryInfo()
    {
        this("id");
    }
    protected AbstractChangeSupplierEntryInfo(String pkField)
    {
        super(pkField);
        put("newMainSupp", new com.kingdee.eas.fdc.contract.NewMainSupplierEntryCollection());
        put("entrys", new com.kingdee.eas.fdc.contract.SupplierContentEntryCollection());
        put("copySupp", new com.kingdee.eas.fdc.contract.CopySupplierEntryCollection());
    }
    /**
     * Object: 变更审批单分录 's 主送单位 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getMainSupp()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("mainSupp");
    }
    public void setMainSupp(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("mainSupp", item);
    }
    /**
     * Object: 变更审批单分录 's 合同 property 
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
     * Object:变更审批单分录's 是否责任扣款单位property 
     */
    public boolean isIsDeduct()
    {
        return getBoolean("isDeduct");
    }
    public void setIsDeduct(boolean item)
    {
        setBoolean("isDeduct", item);
    }
    /**
     * Object:变更审批单分录's 责任扣款金额property 
     */
    public java.math.BigDecimal getDeductAmount()
    {
        return getBigDecimal("deductAmount");
    }
    public void setDeductAmount(java.math.BigDecimal item)
    {
        setBigDecimal("deductAmount", item);
    }
    /**
     * Object:变更审批单分录's 扣款原因property 
     */
    public String getDeductReason()
    {
        return getString("deductReason");
    }
    public void setDeductReason(String item)
    {
        setString("deductReason", item);
    }
    /**
     * Object:变更审批单分录's 成本测算金额property 
     */
    public java.math.BigDecimal getCostAmount()
    {
        return getBigDecimal("costAmount");
    }
    public void setCostAmount(java.math.BigDecimal item)
    {
        setBigDecimal("costAmount", item);
    }
    /**
     * Object:变更审批单分录's 成本测算说明property 
     */
    public String getCostDescription()
    {
        return getString("costDescription");
    }
    public void setCostDescription(String item)
    {
        setString("costDescription", item);
    }
    /**
     * Object: 变更审批单分录 's 变更审批单 property 
     */
    public com.kingdee.eas.fdc.contract.ChangeAuditBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.ChangeAuditBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.ChangeAuditBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:变更审批单分录's 结算方式property 
     */
    public String getBalanceType()
    {
        return getString("balanceType");
    }
    public void setBalanceType(String item)
    {
        setString("balanceType", item);
    }
    /**
     * Object: 变更审批单分录 's 测算人 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getReckonor()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("reckonor");
    }
    public void setReckonor(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("reckonor", item);
    }
    /**
     * Object: 变更审批单分录 's 责任归属单位 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getDutySupplier()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("dutySupplier");
    }
    public void setDutySupplier(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("dutySupplier", item);
    }
    /**
     * Object: 变更审批单分录 's 指令单 property 
     */
    public com.kingdee.eas.fdc.contract.ContractChangeBillInfo getContractChange()
    {
        return (com.kingdee.eas.fdc.contract.ContractChangeBillInfo)get("contractChange");
    }
    public void setContractChange(com.kingdee.eas.fdc.contract.ContractChangeBillInfo item)
    {
        put("contractChange", item);
    }
    /**
     * Object: 变更审批单分录 's 执行内容 property 
     */
    public com.kingdee.eas.fdc.contract.SupplierContentEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.contract.SupplierContentEntryCollection)get("entrys");
    }
    /**
     * Object: 变更审批单分录 's 抄送单位 property 
     */
    public com.kingdee.eas.fdc.contract.CopySupplierEntryCollection getCopySupp()
    {
        return (com.kingdee.eas.fdc.contract.CopySupplierEntryCollection)get("copySupp");
    }
    /**
     * Object: 变更审批单分录 's 责任归属部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getDutyOrg()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("dutyOrg");
    }
    public void setDutyOrg(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("dutyOrg", item);
    }
    /**
     * Object: 变更审批单分录 's 币别 property 
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
     * Object:变更审批单分录's 责任扣款金额原币property 
     */
    public java.math.BigDecimal getOriDeductAmount()
    {
        return getBigDecimal("oriDeductAmount");
    }
    public void setOriDeductAmount(java.math.BigDecimal item)
    {
        setBigDecimal("oriDeductAmount", item);
    }
    /**
     * Object:变更审批单分录's 成本测算金额property 
     */
    public java.math.BigDecimal getOriCostAmount()
    {
        return getBigDecimal("oriCostAmount");
    }
    public void setOriCostAmount(java.math.BigDecimal item)
    {
        setBigDecimal("oriCostAmount", item);
    }
    /**
     * Object:变更审批单分录's 汇率property 
     */
    public java.math.BigDecimal getExRate()
    {
        return getBigDecimal("exRate");
    }
    public void setExRate(java.math.BigDecimal item)
    {
        setBigDecimal("exRate", item);
    }
    /**
     * Object:变更审批单分录's 原始联系单号property 
     */
    public String getOriginalContactNum()
    {
        return getString("originalContactNum");
    }
    public void setOriginalContactNum(String item)
    {
        setString("originalContactNum", item);
    }
    /**
     * Object:变更审批单分录's 是否确认变更金额property 
     */
    public boolean isIsSureChangeAmt()
    {
        return getBoolean("isSureChangeAmt");
    }
    public void setIsSureChangeAmt(boolean item)
    {
        setBoolean("isSureChangeAmt", item);
    }
    /**
     * Object:变更审批单分录's 施工方报审金额property 
     */
    public java.math.BigDecimal getConstructPrice()
    {
        return getBigDecimal("constructPrice");
    }
    public void setConstructPrice(java.math.BigDecimal item)
    {
        setBigDecimal("constructPrice", item);
    }
    /**
     * Object: 变更审批单分录 's 总承包商 property 
     */
    public com.kingdee.eas.fdc.contract.NewMainSupplierEntryCollection getNewMainSupp()
    {
        return (com.kingdee.eas.fdc.contract.NewMainSupplierEntryCollection)get("newMainSupp");
    }
    /**
     * Object:变更审批单分录's 预计开工时间property 
     */
    public java.util.Date getStartDate()
    {
        return getDate("startDate");
    }
    public void setStartDate(java.util.Date item)
    {
        setDate("startDate", item);
    }
    /**
     * Object:变更审批单分录's 预计完工时间property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    /**
     * Object:变更审批单分录's 预计收方时间property 
     */
    public java.util.Date getSfDate()
    {
        return getDate("sfDate");
    }
    public void setSfDate(java.util.Date item)
    {
        setDate("sfDate", item);
    }
    /**
     * Object:变更审批单分录's 是否为营销类变更property 
     */
    public String getIsMarket()
    {
        return getString("isMarket");
    }
    public void setIsMarket(String item)
    {
        setString("isMarket", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2B8A9E1B");
    }
}