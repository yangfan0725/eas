package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCProDepConPayPlanContractInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractFDCProDepConPayPlanContractInfo()
    {
        this("id");
    }
    protected AbstractFDCProDepConPayPlanContractInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.finance.FDCProDepConPayContractEntryCollection());
    }
    /**
     * Object: ��ǩ����ͬ��¼ 's ����� property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepConPayPlanInfo getHead()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepConPayPlanInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.finance.FDCProDepConPayPlanInfo item)
    {
        put("head", item);
    }
    /**
     * Object: ��ǩ����ͬ��¼ 's ���ű��� property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getDepartment()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("department");
    }
    public void setDepartment(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("department", item);
    }
    /**
     * Object: ��ǩ����ͬ��¼ 's ��ͬ property 
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
     * Object:��ǩ����ͬ��¼'s �Ƿ���property 
     */
    public boolean isIsBack()
    {
        return getBoolean("isBack");
    }
    public void setIsBack(boolean item)
    {
        setBoolean("isBack", item);
    }
    /**
     * Object:��ǩ����ͬ��¼'s ��ͬ����property 
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
     * Object:��ǩ����ͬ��¼'s ��ͬ�������property 
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
     * Object:��ǩ����ͬ��¼'s ��ֹ�����ۼ�Ӧ��property 
     */
    public java.math.BigDecimal getLastMonthPayable()
    {
        return getBigDecimal("lastMonthPayable");
    }
    public void setLastMonthPayable(java.math.BigDecimal item)
    {
        setBigDecimal("lastMonthPayable", item);
    }
    /**
     * Object:��ǩ����ͬ��¼'s ��ֹ�����ۼ�ʵ��property 
     */
    public java.math.BigDecimal getLastMonthPay()
    {
        return getBigDecimal("lastMonthPay");
    }
    public void setLastMonthPay(java.math.BigDecimal item)
    {
        setBigDecimal("lastMonthPay", item);
    }
    /**
     * Object:��ǩ����ͬ��¼'s ��ֹ�����ۼ�δ��property 
     */
    public java.math.BigDecimal getLastMonthNopay()
    {
        return getBigDecimal("lastMonthNopay");
    }
    public void setLastMonthNopay(java.math.BigDecimal item)
    {
        setBigDecimal("lastMonthNopay", item);
    }
    /**
     * Object:��ǩ����ͬ��¼'s ��ֹ�����ۼ���;property 
     */
    public java.math.BigDecimal getLastMonthEnRoute()
    {
        return getBigDecimal("lastMonthEnRoute");
    }
    public void setLastMonthEnRoute(java.math.BigDecimal item)
    {
        setBigDecimal("lastMonthEnRoute", item);
    }
    /**
     * Object: ��ǩ����ͬ��¼ 's ��ͬ��¼�¶�֧�� property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepConPayContractEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepConPayContractEntryCollection)get("entrys");
    }
    /**
     * Object: ��ǩ����ͬ��¼ 's ��ͬ�����ƻ���Ӧ��¼ property 
     */
    public com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractInfo getDepPlan()
    {
        return (com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractInfo)get("depPlan");
    }
    public void setDepPlan(com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractInfo item)
    {
        put("depPlan", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("EC1F9E19");
    }
}