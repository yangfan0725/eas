package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCCostInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractFDCCostInfo()
    {
        this("id");
    }
    protected AbstractFDCCostInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�����м��'s ������ĿIDproperty 
     */
    public com.kingdee.bos.util.BOSUuid getProjectId()
    {
        return getBOSUuid("projectId");
    }
    public void setProjectId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("projectId", item);
    }
    /**
     * Object:�����м��'s �ɱ���ĿIdproperty 
     */
    public com.kingdee.bos.util.BOSUuid getCostAccountId()
    {
        return getBOSUuid("costAccountId");
    }
    public void setCostAccountId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("costAccountId", item);
    }
    /**
     * Object:�����м��'s δ�����ͬ���property 
     */
    public java.math.BigDecimal getUnSettConAmt()
    {
        return getBigDecimal("unSettConAmt");
    }
    public void setUnSettConAmt(java.math.BigDecimal item)
    {
        setBigDecimal("unSettConAmt", item);
    }
    /**
     * Object:�����м��'s �ѽ����ͬ���property 
     */
    public java.math.BigDecimal getSettConAmt()
    {
        return getBigDecimal("settConAmt");
    }
    public void setSettConAmt(java.math.BigDecimal item)
    {
        setBigDecimal("settConAmt", item);
    }
    /**
     * Object:�����м��'s δ����ǩԼ���ϼ�property 
     */
    public java.math.BigDecimal getUnSettSignAmt()
    {
        return getBigDecimal("unSettSignAmt");
    }
    public void setUnSettSignAmt(java.math.BigDecimal item)
    {
        setBigDecimal("unSettSignAmt", item);
    }
    /**
     * Object:�����м��'s �ѽ���ǩԼ���ϼ�property 
     */
    public java.math.BigDecimal getSettSignAmt()
    {
        return getBigDecimal("settSignAmt");
    }
    public void setSettSignAmt(java.math.BigDecimal item)
    {
        setBigDecimal("settSignAmt", item);
    }
    /**
     * Object:�����м��'s �������property 
     */
    public java.math.BigDecimal getSettAdjustAmt()
    {
        return getBigDecimal("settAdjustAmt");
    }
    public void setSettAdjustAmt(java.math.BigDecimal item)
    {
        setBigDecimal("settAdjustAmt", item);
    }
    /**
     * Object:�����м��'s �Ǻ�ͬ�Գɱ�property 
     */
    public java.math.BigDecimal getConWithoutTxtCostAmt()
    {
        return getBigDecimal("conWithoutTxtCostAmt");
    }
    public void setConWithoutTxtCostAmt(java.math.BigDecimal item)
    {
        setBigDecimal("conWithoutTxtCostAmt", item);
    }
    /**
     * Object:�����м��'s Ŀǰ�ѷ���property 
     */
    public java.math.BigDecimal getHappendAmt()
    {
        return getBigDecimal("happendAmt");
    }
    public void setHappendAmt(java.math.BigDecimal item)
    {
        setBigDecimal("happendAmt", item);
    }
    /**
     * Object:�����м��'s Ŀǰ������property 
     */
    public java.math.BigDecimal getNotHappenAmt()
    {
        return getBigDecimal("notHappenAmt");
    }
    public void setNotHappenAmt(java.math.BigDecimal item)
    {
        setBigDecimal("notHappenAmt", item);
    }
    /**
     * Object:�����м��'s ��ʵ�ֳɱ�property 
     */
    public java.math.BigDecimal getCostPayedAmt()
    {
        return getBigDecimal("costPayedAmt");
    }
    public void setCostPayedAmt(java.math.BigDecimal item)
    {
        setBigDecimal("costPayedAmt", item);
    }
    /**
     * Object:�����м��'s �Ѹ�����property 
     */
    public java.math.BigDecimal getHasPayedAmt()
    {
        return getBigDecimal("hasPayedAmt");
    }
    public void setHasPayedAmt(java.math.BigDecimal item)
    {
        setBigDecimal("hasPayedAmt", item);
    }
    /**
     * Object:�����м��'s ��̬�ɱ�property 
     */
    public java.math.BigDecimal getDynCostAmt()
    {
        return getBigDecimal("dynCostAmt");
    }
    public void setDynCostAmt(java.math.BigDecimal item)
    {
        setBigDecimal("dynCostAmt", item);
    }
    /**
     * Object:�����м��'s Ŀ��ɱ�property 
     */
    public java.math.BigDecimal getAimCostAmt()
    {
        return getBigDecimal("aimCostAmt");
    }
    public void setAimCostAmt(java.math.BigDecimal item)
    {
        setBigDecimal("aimCostAmt", item);
    }
    /**
     * Object:�����м��'s ����property 
     */
    public java.math.BigDecimal getDiffAmt()
    {
        return getBigDecimal("diffAmt");
    }
    public void setDiffAmt(java.math.BigDecimal item)
    {
        setBigDecimal("diffAmt", item);
    }
    /**
     * Object:�����м��'s ���۵���property 
     */
    public java.math.BigDecimal getSellAmt()
    {
        return getBigDecimal("sellAmt");
    }
    public void setSellAmt(java.math.BigDecimal item)
    {
        setBigDecimal("sellAmt", item);
    }
    /**
     * Object:�����м��'s ��������property 
     */
    public java.math.BigDecimal getBuildAmt()
    {
        return getBigDecimal("buildAmt");
    }
    public void setBuildAmt(java.math.BigDecimal item)
    {
        setBigDecimal("buildAmt", item);
    }
    /**
     * Object:�����м��'s ��̯����IDproperty 
     */
    public com.kingdee.bos.util.BOSUuid getApprotionTypeId()
    {
        return getBOSUuid("approtionTypeId");
    }
    public void setApprotionTypeId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("approtionTypeId", item);
    }
    /**
     * Object:�����м��'s ��Ŀ������property 
     */
    public String getAcctLongNumber()
    {
        return getString("acctLongNumber");
    }
    public void setAcctLongNumber(String item)
    {
        setString("acctLongNumber", item);
    }
    /**
     * Object:�����м��'s ��Ŀ������property 
     */
    public String getPrjLongNumber()
    {
        return getString("prjLongNumber");
    }
    public void setPrjLongNumber(String item)
    {
        setString("prjLongNumber", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7799479B");
    }
}