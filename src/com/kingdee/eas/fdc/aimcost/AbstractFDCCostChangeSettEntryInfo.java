package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCCostChangeSettEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractFDCCostChangeSettEntryInfo()
    {
        this("id");
    }
    protected AbstractFDCCostChangeSettEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�����м���������¼'s �������IDproperty 
     */
    public com.kingdee.bos.util.BOSUuid getChangeTypeId()
    {
        return getBOSUuid("changeTypeId");
    }
    public void setChangeTypeId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("changeTypeId", item);
    }
    /**
     * Object:�����м���������¼'s δ������property 
     */
    public java.math.BigDecimal getUnSettleAmt()
    {
        return getBigDecimal("unSettleAmt");
    }
    public void setUnSettleAmt(java.math.BigDecimal item)
    {
        setBigDecimal("unSettleAmt", item);
    }
    /**
     * Object:�����м���������¼'s �ѽ�����property 
     */
    public java.math.BigDecimal getSettleAmt()
    {
        return getBigDecimal("settleAmt");
    }
    public void setSettleAmt(java.math.BigDecimal item)
    {
        setBigDecimal("settleAmt", item);
    }
    /**
     * Object:�����м���������¼'s FDCCostͷproperty 
     */
    public com.kingdee.bos.util.BOSUuid getParentID()
    {
        return getBOSUuid("parentID");
    }
    public void setParentID(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("parentID", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("58EBC455");
    }
}