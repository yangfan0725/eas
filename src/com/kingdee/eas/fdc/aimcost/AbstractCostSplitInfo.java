package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCostSplitInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractCostSplitInfo()
    {
        this("id");
    }
    protected AbstractCostSplitInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.aimcost.CostSplitEntryCollection());
    }
    /**
     * Object: �ɱ���� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.aimcost.CostSplitEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.aimcost.CostSplitEntryCollection)get("entrys");
    }
    /**
     * Object:�ɱ����'s ��������property 
     */
    public com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum getCostBillType()
    {
        return com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum.getEnum(getString("costBillType"));
    }
    public void setCostBillType(com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum item)
    {
		if (item != null) {
        setString("costBillType", item.getValue());
		}
    }
    /**
     * Object:�ɱ����'s �ɱ�����property 
     */
    public com.kingdee.bos.util.BOSUuid getCostBillId()
    {
        return getBOSUuid("costBillId");
    }
    public void setCostBillId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("costBillId", item);
    }
    /**
     * Object:�ɱ����'s ��ֵ���idproperty 
     */
    public com.kingdee.bos.util.BOSUuid getSplitBillId()
    {
        return getBOSUuid("splitBillId");
    }
    public void setSplitBillId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("splitBillId", item);
    }
    /**
     * Object:�ɱ����'s �Ƿ���������property 
     */
    public boolean isIsInvalid()
    {
        return getBoolean("isInvalid");
    }
    public void setIsInvalid(boolean item)
    {
        setBoolean("isInvalid", item);
    }
    /**
     * Object:�ɱ����'s ����������Ŀ��CUproperty 
     */
    public String getControlUnitId()
    {
        return getString("controlUnitId");
    }
    public void setControlUnitId(String item)
    {
        setString("controlUnitId", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E9A79BD6");
    }
}