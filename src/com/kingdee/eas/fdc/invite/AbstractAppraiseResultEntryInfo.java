package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAppraiseResultEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractAppraiseResultEntryInfo()
    {
        this("id");
    }
    protected AbstractAppraiseResultEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��������¼ 's ������ property 
     */
    public com.kingdee.eas.fdc.invite.AppraiseResultInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.AppraiseResultInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.AppraiseResultInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��������¼'s ר������÷�property 
     */
    public double getScore()
    {
        return getDouble("score");
    }
    public void setScore(double item)
    {
        setDouble("score", item);
    }
    /**
     * Object:��������¼'s �Ƿ��������property 
     */
    public boolean isIsPartIn()
    {
        return getBoolean("isPartIn");
    }
    public void setIsPartIn(boolean item)
    {
        setBoolean("isPartIn", item);
    }
    /**
     * Object:��������¼'s ר����������property 
     */
    public int getExpertAppraiseSeq()
    {
        return getInt("expertAppraiseSeq");
    }
    public void setExpertAppraiseSeq(int item)
    {
        setInt("expertAppraiseSeq", item);
    }
    /**
     * Object:��������¼'s Ͷ�걨��property 
     */
    public java.math.BigDecimal getBidAmount()
    {
        return getBigDecimal("bidAmount");
    }
    public void setBidAmount(java.math.BigDecimal item)
    {
        setBigDecimal("bidAmount", item);
    }
    /**
     * Object:��������¼'s ̸�м�property 
     */
    public java.math.BigDecimal getTreatAmount()
    {
        return getBigDecimal("treatAmount");
    }
    public void setTreatAmount(java.math.BigDecimal item)
    {
        setBigDecimal("treatAmount", item);
    }
    /**
     * Object:��������¼'s �ܼ�����property 
     */
    public int getTotalSeq()
    {
        return getInt("totalSeq");
    }
    public void setTotalSeq(int item)
    {
        setInt("totalSeq", item);
    }
    /**
     * Object: ��������¼ 's Ͷ���� property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getSupplier()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("supplier");
    }
    public void setSupplier(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("supplier", item);
    }
    /**
     * Object:��������¼'s Ͷ�걨���Ƿ���property 
     */
    public boolean isIsImport()
    {
        return getBoolean("isImport");
    }
    public void setIsImport(boolean item)
    {
        setBoolean("isImport", item);
    }
    /**
     * Object:��������¼'s �б�property 
     */
    public boolean isHit()
    {
        return getBoolean("hit");
    }
    public void setHit(boolean item)
    {
        setBoolean("hit", item);
    }
    /**
     * Object:��������¼'s Ԥ�б�property 
     */
    public boolean isPreHit()
    {
        return getBoolean("preHit");
    }
    public void setPreHit(boolean item)
    {
        setBoolean("preHit", item);
    }
    /**
     * Object:��������¼'s nullproperty 
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
     * Object:��������¼'s nullproperty 
     */
    public com.kingdee.eas.fdc.invite.EffectivenessEnum getEffective()
    {
        return com.kingdee.eas.fdc.invite.EffectivenessEnum.getEnum(getInt("effective"));
    }
    public void setEffective(com.kingdee.eas.fdc.invite.EffectivenessEnum item)
    {
		if (item != null) {
        setInt("effective", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3D324946");
    }
}