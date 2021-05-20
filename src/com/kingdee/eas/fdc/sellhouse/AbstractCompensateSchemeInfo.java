package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCompensateSchemeInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractCompensateSchemeInfo()
    {
        this("id");
    }
    protected AbstractCompensateSchemeInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.sellhouse.CompensateSchemeEntryCollection());
    }
    /**
     * Object: ����� 's ������Ŀ property 
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
     * Object:�����'s ���㷽��property 
     */
    public com.kingdee.eas.fdc.sellhouse.CalcWayEnum getCalcWay()
    {
        return com.kingdee.eas.fdc.sellhouse.CalcWayEnum.getEnum(getString("calcWay"));
    }
    public void setCalcWay(com.kingdee.eas.fdc.sellhouse.CalcWayEnum item)
    {
		if (item != null) {
        setString("calcWay", item.getValue());
		}
    }
    /**
     * Object: ����� 's ������¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.CompensateSchemeEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.sellhouse.CompensateSchemeEntryCollection)get("entrys");
    }
    /**
     * Object:�����'s ���ʽproperty 
     */
    public com.kingdee.eas.fdc.sellhouse.CompensateTypeEnum getCompensateType()
    {
        return com.kingdee.eas.fdc.sellhouse.CompensateTypeEnum.getEnum(getString("compensateType"));
    }
    public void setCompensateType(com.kingdee.eas.fdc.sellhouse.CompensateTypeEnum item)
    {
		if (item != null) {
        setString("compensateType", item.getValue());
		}
    }
    /**
     * Object:�����'s ȡ��С��λ��property 
     */
    public int getDecimalCount()
    {
        return getInt("decimalCount");
    }
    public void setDecimalCount(int item)
    {
        setInt("decimalCount", item);
    }
    /**
     * Object:�����'s ȡ����ʽproperty 
     */
    public com.kingdee.eas.fdc.sellhouse.DecimalTypeEnum getDecimalType()
    {
        return com.kingdee.eas.fdc.sellhouse.DecimalTypeEnum.getEnum(getString("decimalType"));
    }
    public void setDecimalType(com.kingdee.eas.fdc.sellhouse.DecimalTypeEnum item)
    {
		if (item != null) {
        setString("decimalType", item.getValue());
		}
    }
    /**
     * Object:�����'s �Ƿ�����property 
     */
    public boolean isIsEnabled()
    {
        return getBoolean("isEnabled");
    }
    public void setIsEnabled(boolean item)
    {
        setBoolean("isEnabled", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BB9AA697");
    }
}