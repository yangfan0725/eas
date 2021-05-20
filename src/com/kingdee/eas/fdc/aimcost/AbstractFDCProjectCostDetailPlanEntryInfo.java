package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCProjectCostDetailPlanEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractFDCProjectCostDetailPlanEntryInfo()
    {
        this("id");
    }
    protected AbstractFDCProjectCostDetailPlanEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:����ƻ�Ԥ�㷴д��¼'s �·�property 
     */
    public int getYear()
    {
        return getInt("year");
    }
    public void setYear(int item)
    {
        setInt("year", item);
    }
    /**
     * Object:����ƻ�Ԥ�㷴д��¼'s �·�property 
     */
    public int getMonth()
    {
        return getInt("month");
    }
    public void setMonth(int item)
    {
        setInt("month", item);
    }
    /**
     * Object:����ƻ�Ԥ�㷴д��¼'s ��ͬ�¶ȼƻ�������property 
     */
    public java.math.BigDecimal getRequestAmt()
    {
        return getBigDecimal("requestAmt");
    }
    public void setRequestAmt(java.math.BigDecimal item)
    {
        setBigDecimal("requestAmt", item);
    }
    /**
     * Object:����ƻ�Ԥ�㷴д��¼'s ��ͬ�¶�Ԥ�㸶���׼���property 
     */
    public java.math.BigDecimal getApprovedAmt()
    {
        return getBigDecimal("approvedAmt");
    }
    public void setApprovedAmt(java.math.BigDecimal item)
    {
        setBigDecimal("approvedAmt", item);
    }
    /**
     * Object:����ƻ�Ԥ�㷴д��¼'s ��ͬ�¶ȹ�����Ͷ��ƻ����property 
     */
    public java.math.BigDecimal getApprovedWLAmt()
    {
        return getBigDecimal("approvedWLAmt");
    }
    public void setApprovedWLAmt(java.math.BigDecimal item)
    {
        setBigDecimal("approvedWLAmt", item);
    }
    /**
     * Object:����ƻ�Ԥ�㷴д��¼'s ��ͬ�¶�ʵ�����property 
     */
    public java.math.BigDecimal getPayedAmt()
    {
        return getBigDecimal("payedAmt");
    }
    public void setPayedAmt(java.math.BigDecimal item)
    {
        setBigDecimal("payedAmt", item);
    }
    /**
     * Object:����ƻ�Ԥ�㷴д��¼'s ��ͬ�¶ȸ���ƫ��property 
     */
    public java.math.BigDecimal getPayDiffAmt()
    {
        return getBigDecimal("payDiffAmt");
    }
    public void setPayDiffAmt(java.math.BigDecimal item)
    {
        setBigDecimal("payDiffAmt", item);
    }
    /**
     * Object:����ƻ�Ԥ�㷴д��¼'s ��Ŀ�¶ȼƻ�������property 
     */
    public java.math.BigDecimal getAccountRequestAmt()
    {
        return getBigDecimal("accountRequestAmt");
    }
    public void setAccountRequestAmt(java.math.BigDecimal item)
    {
        setBigDecimal("accountRequestAmt", item);
    }
    /**
     * Object:����ƻ�Ԥ�㷴д��¼'s ��Ŀ�¶�Ԥ�㸶���׼���property 
     */
    public java.math.BigDecimal getAccountApprovedAmt()
    {
        return getBigDecimal("accountApprovedAmt");
    }
    public void setAccountApprovedAmt(java.math.BigDecimal item)
    {
        setBigDecimal("accountApprovedAmt", item);
    }
    /**
     * Object: ����ƻ�Ԥ�㷴д��¼ 's �ɱ���ϸ property 
     */
    public com.kingdee.eas.fdc.aimcost.FDCProjectCostDetailInfo getHeadID()
    {
        return (com.kingdee.eas.fdc.aimcost.FDCProjectCostDetailInfo)get("headID");
    }
    public void setHeadID(com.kingdee.eas.fdc.aimcost.FDCProjectCostDetailInfo item)
    {
        put("headID", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C017FB60");
    }
}