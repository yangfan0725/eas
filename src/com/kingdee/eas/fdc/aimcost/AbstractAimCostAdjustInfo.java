package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAimCostAdjustInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractAimCostAdjustInfo()
    {
        this("id");
    }
    protected AbstractAimCostAdjustInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.aimcost.AimCostAdjustEntryCollection());
    }
    /**
     * Object: Ŀ��ɱ������� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.aimcost.AimCostAdjustEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.aimcost.AimCostAdjustEntryCollection)get("entrys");
    }
    /**
     * Object: Ŀ��ɱ������� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D8EF24EA");
    }
}