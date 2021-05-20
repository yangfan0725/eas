package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSaleBalanceInfo extends com.kingdee.eas.framework.ObjectBaseInfo implements Serializable 
{
    public AbstractSaleBalanceInfo()
    {
        this("id");
    }
    protected AbstractSaleBalanceInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ���۽��� 's ������Ŀ property 
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
     * Object:���۽���'s ��������property 
     */
    public com.kingdee.eas.fdc.sellhouse.SaleBalanceTypeEnum getOperateType()
    {
        return com.kingdee.eas.fdc.sellhouse.SaleBalanceTypeEnum.getEnum(getString("operateType"));
    }
    public void setOperateType(com.kingdee.eas.fdc.sellhouse.SaleBalanceTypeEnum item)
    {
		if (item != null) {
        setString("operateType", item.getValue());
		}
    }
    /**
     * Object:���۽���'s ���ڽ����ֹ��property 
     */
    public java.util.Date getLastBalancDate()
    {
        return getDate("lastBalancDate");
    }
    public void setLastBalancDate(java.util.Date item)
    {
        setDate("lastBalancDate", item);
    }
    /**
     * Object:���۽���'s ��ʼ����property 
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
     * Object:���۽���'s ��������property 
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
     * Object:���۽���'s ����˵��property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object:���۽���'s ��������property 
     */
    public java.util.Date getBalanceDate()
    {
        return getDate("balanceDate");
    }
    public void setBalanceDate(java.util.Date item)
    {
        setDate("balanceDate", item);
    }
    /**
     * Object:���۽���'s �Ϲ���ͬ�ܶ�property 
     */
    public java.math.BigDecimal getPurchContractAmount()
    {
        return getBigDecimal("purchContractAmount");
    }
    public void setPurchContractAmount(java.math.BigDecimal item)
    {
        setBigDecimal("purchContractAmount", item);
    }
    /**
     * Object:���۽���'s ���ۺ�ͬ�ܶ�property 
     */
    public java.math.BigDecimal getSaleContractAmount()
    {
        return getBigDecimal("saleContractAmount");
    }
    public void setSaleContractAmount(java.math.BigDecimal item)
    {
        setBigDecimal("saleContractAmount", item);
    }
    /**
     * Object:���۽���'s Ԥ����ͬ���property 
     */
    public java.math.BigDecimal getContractBookAmount()
    {
        return getBigDecimal("contractBookAmount");
    }
    public void setContractBookAmount(java.math.BigDecimal item)
    {
        setBigDecimal("contractBookAmount", item);
    }
    /**
     * Object: ���۽��� 's ����ڼ� property 
     */
    public com.kingdee.eas.basedata.assistant.PeriodInfo getPeriod()
    {
        return (com.kingdee.eas.basedata.assistant.PeriodInfo)get("period");
    }
    public void setPeriod(com.kingdee.eas.basedata.assistant.PeriodInfo item)
    {
        put("period", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("85E60ADA");
    }
}