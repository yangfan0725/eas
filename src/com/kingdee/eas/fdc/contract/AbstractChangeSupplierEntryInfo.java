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
     * Object: �����������¼ 's ���͵�λ property 
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
     * Object: �����������¼ 's ��ͬ property 
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
     * Object:�����������¼'s �Ƿ����οۿλproperty 
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
     * Object:�����������¼'s ���οۿ���property 
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
     * Object:�����������¼'s �ۿ�ԭ��property 
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
     * Object:�����������¼'s �ɱ�������property 
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
     * Object:�����������¼'s �ɱ�����˵��property 
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
     * Object: �����������¼ 's ��������� property 
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
     * Object:�����������¼'s ���㷽ʽproperty 
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
     * Object: �����������¼ 's ������ property 
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
     * Object: �����������¼ 's ���ι�����λ property 
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
     * Object: �����������¼ 's ָ� property 
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
     * Object: �����������¼ 's ִ������ property 
     */
    public com.kingdee.eas.fdc.contract.SupplierContentEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.contract.SupplierContentEntryCollection)get("entrys");
    }
    /**
     * Object: �����������¼ 's ���͵�λ property 
     */
    public com.kingdee.eas.fdc.contract.CopySupplierEntryCollection getCopySupp()
    {
        return (com.kingdee.eas.fdc.contract.CopySupplierEntryCollection)get("copySupp");
    }
    /**
     * Object: �����������¼ 's ���ι������� property 
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
     * Object: �����������¼ 's �ұ� property 
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
     * Object:�����������¼'s ���οۿ���ԭ��property 
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
     * Object:�����������¼'s �ɱ�������property 
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
     * Object:�����������¼'s ����property 
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
     * Object:�����������¼'s ԭʼ��ϵ����property 
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
     * Object:�����������¼'s �Ƿ�ȷ�ϱ�����property 
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
     * Object:�����������¼'s ʩ����������property 
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
     * Object: �����������¼ 's �ܳа��� property 
     */
    public com.kingdee.eas.fdc.contract.NewMainSupplierEntryCollection getNewMainSupp()
    {
        return (com.kingdee.eas.fdc.contract.NewMainSupplierEntryCollection)get("newMainSupp");
    }
    /**
     * Object:�����������¼'s Ԥ�ƿ���ʱ��property 
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
     * Object:�����������¼'s Ԥ���깤ʱ��property 
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
     * Object:�����������¼'s Ԥ���շ�ʱ��property 
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
     * Object:�����������¼'s �Ƿ�ΪӪ������property 
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