package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCDepConPayPlanContractInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractFDCDepConPayPlanContractInfo()
    {
        this("id");
    }
    protected AbstractFDCDepConPayPlanContractInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractEntryCollection());
    }
    /**
     * Object: ��ǩ����ͬ��¼ 's ����� property 
     */
    public com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillInfo getHead()
    {
        return (com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillInfo item)
    {
        put("head", item);
    }
    /**
     * Object: ��ǩ����ͬ��¼ 's ������Ŀ property 
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
     * Object: ��ǩ����ͬ��¼ 's ��ͬ����  property 
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
    public com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractEntryCollection)get("entrys");
    }
    /**
     * Object:��ǩ����ͬ��¼'s �Ƿ񱻴��property 
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
     * Object:��ǩ����ͬ��¼'s ��غ��Ƿ��޸Ĺ�property 
     */
    public boolean isIsEdit()
    {
        return getBoolean("isEdit");
    }
    public void setIsEdit(boolean item)
    {
        setBoolean("isEdit", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4FE59E5A");
    }
}