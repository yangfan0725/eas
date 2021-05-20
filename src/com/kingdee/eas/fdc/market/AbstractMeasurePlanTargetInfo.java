package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMeasurePlanTargetInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractMeasurePlanTargetInfo()
    {
        this("id");
    }
    protected AbstractMeasurePlanTargetInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.market.MeasurePlanTargetEntryCollection());
    }
    /**
     * Object: Ŀ���ֵ���� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.market.MeasurePlanTargetEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.market.MeasurePlanTargetEntryCollection)get("entry");
    }
    /**
     * Object: Ŀ���ֵ���� 's ����׶� property 
     */
    public com.kingdee.eas.fdc.basedata.MeasurePhaseInfo getMeasurePhases()
    {
        return (com.kingdee.eas.fdc.basedata.MeasurePhaseInfo)get("measurePhases");
    }
    public void setMeasurePhases(com.kingdee.eas.fdc.basedata.MeasurePhaseInfo item)
    {
        put("measurePhases", item);
    }
    /**
     * Object: Ŀ���ֵ���� 's Ӫ����Ŀ����� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getProjectAgin()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("projectAgin");
    }
    public void setProjectAgin(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("projectAgin", item);
    }
    /**
     * Object:Ŀ���ֵ����'s �汾��property 
     */
    public String getVersions()
    {
        return getString("versions");
    }
    public void setVersions(String item)
    {
        setString("versions", item);
    }
    /**
     * Object:Ŀ���ֵ����'s �汾����property 
     */
    public String getVersionsName()
    {
        return getString("versionsName");
    }
    public void setVersionsName(String item)
    {
        setString("versionsName", item);
    }
    /**
     * Object:Ŀ���ֵ����'s ��������property 
     */
    public com.kingdee.eas.fdc.basedata.PhaseTypeEnum getMeasureType()
    {
        return com.kingdee.eas.fdc.basedata.PhaseTypeEnum.getEnum(getString("measureType"));
    }
    public void setMeasureType(com.kingdee.eas.fdc.basedata.PhaseTypeEnum item)
    {
		if (item != null) {
        setString("measureType", item.getValue());
		}
    }
    /**
     * Object:Ŀ���ֵ����'s ����ԭ��property 
     */
    public String getAdjustCause()
    {
        return getString("adjustCause");
    }
    public void setAdjustCause(String item)
    {
        setString("adjustCause", item);
    }
    /**
     * Object:Ŀ���ֵ����'s �ܻ�ֵproperty 
     */
    public java.math.BigDecimal getTotalAmount()
    {
        return getBigDecimal("totalAmount");
    }
    public void setTotalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("totalAmount", item);
    }
    /**
     * Object:Ŀ���ֵ����'s ��עproperty 
     */
    public String getRemarks()
    {
        return getString("remarks");
    }
    public void setRemarks(String item)
    {
        setString("remarks", item);
    }
    /**
     * Object:Ŀ���ֵ����'s �Ƿ����°汾property 
     */
    public boolean isIsNew()
    {
        return getBoolean("isNew");
    }
    public void setIsNew(boolean item)
    {
        setBoolean("isNew", item);
    }
    /**
     * Object:Ŀ���ֵ����'s �汾����property 
     */
    public com.kingdee.eas.fdc.market.VersionTypeEnum getVersionType()
    {
        return com.kingdee.eas.fdc.market.VersionTypeEnum.getEnum(getString("versionType"));
    }
    public void setVersionType(com.kingdee.eas.fdc.market.VersionTypeEnum item)
    {
		if (item != null) {
        setString("versionType", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("09D0E067");
    }
}