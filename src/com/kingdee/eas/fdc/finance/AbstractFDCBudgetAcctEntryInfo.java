package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCBudgetAcctEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractFDCBudgetAcctEntryInfo()
    {
        this("id");
    }
    protected AbstractFDCBudgetAcctEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��ĿԤ���¼'s ������ĿIdproperty 
     */
    public String getProjectId()
    {
        return getString("projectId");
    }
    public void setProjectId(String item)
    {
        setString("projectId", item);
    }
    /**
     * Object: ��ĿԤ���¼ 's �����ɱ���Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountInfo getCostAccount()
    {
        return (com.kingdee.eas.fdc.basedata.CostAccountInfo)get("costAccount");
    }
    public void setCostAccount(com.kingdee.eas.fdc.basedata.CostAccountInfo item)
    {
        put("costAccount", item);
    }
    /**
     * Object: ��ĿԤ���¼ 's ������ property 
     */
    public com.kingdee.eas.base.permission.UserInfo getCreator()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("creator");
    }
    public void setCreator(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("creator", item);
    }
    /**
     * Object: ��ĿԤ���¼ 's ���β��� property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getDeptment()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("deptment");
    }
    public void setDeptment(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("deptment", item);
    }
    /**
     * Object: ��ĿԤ���¼ 's ������ͬ property 
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
     * Object: ��ĿԤ���¼ 's ���ı���ͬ property 
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
     * Object:��ĿԤ���¼'s ����property 
     */
    public String getNumber()
    {
        return getString("number");
    }
    public void setNumber(String item)
    {
        setString("number", item);
    }
    /**
     * Object:��ĿԤ���¼'s ����property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object:��ĿԤ���¼'s �Է���λproperty 
     */
    public String getPartB()
    {
        return getString("partB");
    }
    public void setPartB(String item)
    {
        setString("partB", item);
    }
    /**
     * Object:��ĿԤ���¼'s �Ƿ�����property 
     */
    public boolean isIsAdd()
    {
        return getBoolean("isAdd");
    }
    public void setIsAdd(boolean item)
    {
        setBoolean("isAdd", item);
    }
    /**
     * Object:��ĿԤ���¼'s ������property 
     */
    public com.kingdee.eas.fdc.finance.FDCBudgetAcctItemTypeEnum getItemType()
    {
        return com.kingdee.eas.fdc.finance.FDCBudgetAcctItemTypeEnum.getEnum(getString("itemType"));
    }
    public void setItemType(com.kingdee.eas.fdc.finance.FDCBudgetAcctItemTypeEnum item)
    {
		if (item != null) {
        setString("itemType", item.getValue());
		}
    }
    /**
     * Object:��ĿԤ���¼'s Ŀ��ɱ�property 
     */
    public java.math.BigDecimal getAimCost()
    {
        return getBigDecimal("aimCost");
    }
    public void setAimCost(java.math.BigDecimal item)
    {
        setBigDecimal("aimCost", item);
    }
    /**
     * Object:��ĿԤ���¼'s ��̬�ɱ�property 
     */
    public java.math.BigDecimal getDynCost()
    {
        return getBigDecimal("dynCost");
    }
    public void setDynCost(java.math.BigDecimal item)
    {
        setBigDecimal("dynCost", item);
    }
    /**
     * Object:��ĿԤ���¼'s ��ֹ����/����ĩ�ۼƷ����ɱ�property 
     */
    public java.math.BigDecimal getLstCost()
    {
        return getBigDecimal("lstCost");
    }
    public void setLstCost(java.math.BigDecimal item)
    {
        setBigDecimal("lstCost", item);
    }
    /**
     * Object:��ĿԤ���¼'s ��ͬ�������property 
     */
    public java.math.BigDecimal getConLatestPrice()
    {
        return getBigDecimal("conLatestPrice");
    }
    public void setConLatestPrice(java.math.BigDecimal item)
    {
        setBigDecimal("conLatestPrice", item);
    }
    /**
     * Object:��ĿԤ���¼'s ��ֽ��property 
     */
    public java.math.BigDecimal getSplitAmt()
    {
        return getBigDecimal("splitAmt");
    }
    public void setSplitAmt(java.math.BigDecimal item)
    {
        setBigDecimal("splitAmt", item);
    }
    /**
     * Object:��ĿԤ���¼'s ��עproperty 
     */
    public String getDesc()
    {
        return getString("desc");
    }
    public void setDesc(String item)
    {
        setString("desc", item);
    }
    /**
     * Object:��ĿԤ���¼'s Ԥ����property 
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
     * Object:��ĿԤ���¼'s �ɱ����property 
     */
    public java.math.BigDecimal getCost()
    {
        return getBigDecimal("cost");
    }
    public void setCost(java.math.BigDecimal item)
    {
        setBigDecimal("cost", item);
    }
    /**
     * Object:��ĿԤ���¼'s �����ۼƸ���property 
     */
    public java.math.BigDecimal getLstAllPay()
    {
        return getBigDecimal("lstAllPay");
    }
    public void setLstAllPay(java.math.BigDecimal item)
    {
        setBigDecimal("lstAllPay", item);
    }
    /**
     * Object:��ĿԤ���¼'s �����ۼƳɱ�property 
     */
    public java.math.BigDecimal getLstAllCost()
    {
        return getBigDecimal("lstAllCost");
    }
    public void setLstAllCost(java.math.BigDecimal item)
    {
        setBigDecimal("lstAllCost", item);
    }
    /**
     * Object:��ĿԤ���¼'s �ѷ����ɱ�property 
     */
    public java.math.BigDecimal getHappenCost()
    {
        return getBigDecimal("happenCost");
    }
    public void setHappenCost(java.math.BigDecimal item)
    {
        setBigDecimal("happenCost", item);
    }
    /**
     * Object:��ĿԤ���¼'s �ѷ�������property 
     */
    public java.math.BigDecimal getHappenPay()
    {
        return getBigDecimal("happenPay");
    }
    public void setHappenPay(java.math.BigDecimal item)
    {
        setBigDecimal("happenPay", item);
    }
    /**
     * Object:��ĿԤ���¼'s ��ͬ���property 
     */
    public java.math.BigDecimal getConAmt()
    {
        return getBigDecimal("conAmt");
    }
    public void setConAmt(java.math.BigDecimal item)
    {
        setBigDecimal("conAmt", item);
    }
    /**
     * Object:��ĿԤ���¼'s Դ����property 
     */
    public String getSourceId()
    {
        return getString("sourceId");
    }
    public void setSourceId(String item)
    {
        setString("sourceId", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("78623B84");
    }
}