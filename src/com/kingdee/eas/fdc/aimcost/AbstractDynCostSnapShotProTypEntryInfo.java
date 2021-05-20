package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDynCostSnapShotProTypEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractDynCostSnapShotProTypEntryInfo()
    {
        this("id");
    }
    protected AbstractDynCostSnapShotProTypEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��̬�ɱ����ղ�Ʒ���ͷ�¼ 's ��̬�ɱ����� property 
     */
    public com.kingdee.eas.fdc.aimcost.DynCostSnapShotInfo getParent()
    {
        return (com.kingdee.eas.fdc.aimcost.DynCostSnapShotInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.aimcost.DynCostSnapShotInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��̬�ɱ����ղ�Ʒ���ͷ�¼'s ��Ʒ����Idproperty 
     */
    public com.kingdee.bos.util.BOSUuid getProductTypeId()
    {
        return getBOSUuid("productTypeId");
    }
    public void setProductTypeId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("productTypeId", item);
    }
    /**
     * Object:��̬�ɱ����ղ�Ʒ���ͷ�¼'s Ŀ����۵���property 
     */
    public java.math.BigDecimal getAimSaleUnitAmt()
    {
        return getBigDecimal("aimSaleUnitAmt");
    }
    public void setAimSaleUnitAmt(java.math.BigDecimal item)
    {
        setBigDecimal("aimSaleUnitAmt", item);
    }
    /**
     * Object:��̬�ɱ����ղ�Ʒ���ͷ�¼'s Ŀ��property 
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
     * Object:��̬�ɱ����ղ�Ʒ���ͷ�¼'s �ѷ���property 
     */
    public java.math.BigDecimal getHasHappenAmt()
    {
        return getBigDecimal("hasHappenAmt");
    }
    public void setHasHappenAmt(java.math.BigDecimal item)
    {
        setBigDecimal("hasHappenAmt", item);
    }
    /**
     * Object:��̬�ɱ����ղ�Ʒ���ͷ�¼'s δ����property 
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
     * Object:��̬�ɱ����ղ�Ʒ���ͷ�¼'s ��̬���۵���property 
     */
    public java.math.BigDecimal getDynSaleUnitAmt()
    {
        return getBigDecimal("dynSaleUnitAmt");
    }
    public void setDynSaleUnitAmt(java.math.BigDecimal item)
    {
        setBigDecimal("dynSaleUnitAmt", item);
    }
    /**
     * Object:��̬�ɱ����ղ�Ʒ���ͷ�¼'s ��̬property 
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
     * Object:��̬�ɱ����ղ�Ʒ���ͷ�¼'s ��ʵ�ֳɱ�property 
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
     * Object:��̬�ɱ����ղ�Ʒ���ͷ�¼'s Ŀ��ָ���̯Ŀ��ɱ�property 
     */
    public java.math.BigDecimal getAimAimCostAmt()
    {
        return getBigDecimal("aimAimCostAmt");
    }
    public void setAimAimCostAmt(java.math.BigDecimal item)
    {
        setBigDecimal("aimAimCostAmt", item);
    }
    /**
     * Object:��̬�ɱ����ղ�Ʒ���ͷ�¼'s Ŀ��ָ���̯���۵���property 
     */
    public java.math.BigDecimal getAimAimSaleUnitAmt()
    {
        return getBigDecimal("aimAimSaleUnitAmt");
    }
    public void setAimAimSaleUnitAmt(java.math.BigDecimal item)
    {
        setBigDecimal("aimAimSaleUnitAmt", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BC1B8CC1");
    }
}