package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCProDepSplitHasConInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractFDCProDepSplitHasConInfo()
    {
        this("id");
    }
    protected AbstractFDCProDepSplitHasConInfo(String pkField)
    {
        super(pkField);
        put("details", new com.kingdee.eas.fdc.finance.FDCProDepSplitHasConEntryCollection());
    }
    /**
     * Object: �����ƻ�������ǩ����ͬ��� 's ��ֵ��� property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepSplitInfo getHead()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepSplitInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.finance.FDCProDepSplitInfo item)
    {
        put("head", item);
    }
    /**
     * Object: �����ƻ�������ǩ����ͬ��� 's �����ƻ���¼ property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepConPayPlanContractInfo getFdcProDepEntry()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepConPayPlanContractInfo)get("fdcProDepEntry");
    }
    public void setFdcProDepEntry(com.kingdee.eas.fdc.finance.FDCProDepConPayPlanContractInfo item)
    {
        put("fdcProDepEntry", item);
    }
    /**
     * Object: �����ƻ�������ǩ����ͬ��� 's ��ͬ property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getContract()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("contract");
    }
    public void setContract(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("contract", item);
    }
    /**
     * Object:�����ƻ�������ǩ����ͬ���'s ��ͬ����property 
     */
    public String getContractName()
    {
        return getString("contractName");
    }
    public void setContractName(String item)
    {
        setString("contractName", item);
    }
    /**
     * Object:�����ƻ�������ǩ����ͬ���'s ��ͬ�������property 
     */
    public java.math.BigDecimal getContractPrice()
    {
        return getBigDecimal("contractPrice");
    }
    public void setContractPrice(java.math.BigDecimal item)
    {
        setBigDecimal("contractPrice", item);
    }
    /**
     * Object: �����ƻ�������ǩ����ͬ��� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object:�����ƻ�������ǩ����ͬ���'s �Ƿ��ֳɳɱ���Ŀproperty 
     */
    public boolean isIsSplitCost()
    {
        return getBoolean("isSplitCost");
    }
    public void setIsSplitCost(boolean item)
    {
        setBoolean("isSplitCost", item);
    }
    /**
     * Object: �����ƻ�������ǩ����ͬ��� 's �ɱ���Ŀ property 
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
     * Object: �����ƻ�������ǩ����ͬ��� 's ��ƿ�Ŀ property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getAccountView()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("accountView");
    }
    public void setAccountView(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("accountView", item);
    }
    /**
     * Object: �����ƻ�������ǩ����ͬ��� 's �����ϸ property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepSplitHasConEntryCollection getDetails()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepSplitHasConEntryCollection)get("details");
    }
    /**
     * Object:�����ƻ�������ǩ����ͬ���'s �Ƿ�����property 
     */
    public boolean isIsSplitRow()
    {
        return getBoolean("isSplitRow");
    }
    public void setIsSplitRow(boolean item)
    {
        setBoolean("isSplitRow", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9D955B6A");
    }
}