package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomAreaChangeHisInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractRoomAreaChangeHisInfo()
    {
        this("id");
    }
    protected AbstractRoomAreaChangeHisInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:������������ʷ's �������property 
     */
    public java.math.BigDecimal getBuildingArea()
    {
        return getBigDecimal("buildingArea");
    }
    public void setBuildingArea(java.math.BigDecimal item)
    {
        setBigDecimal("buildingArea", item);
    }
    /**
     * Object:������������ʷ's �������property 
     */
    public java.math.BigDecimal getRoomArea()
    {
        return getBigDecimal("roomArea");
    }
    public void setRoomArea(java.math.BigDecimal item)
    {
        setBigDecimal("roomArea", item);
    }
    /**
     * Object:������������ʷ's ʵ�⽨�����property 
     */
    public java.math.BigDecimal getActualBuildingArea()
    {
        return getBigDecimal("actualBuildingArea");
    }
    public void setActualBuildingArea(java.math.BigDecimal item)
    {
        setBigDecimal("actualBuildingArea", item);
    }
    /**
     * Object:������������ʷ's ʵ���������property 
     */
    public java.math.BigDecimal getActualRoomArea()
    {
        return getBigDecimal("actualRoomArea");
    }
    public void setActualRoomArea(java.math.BigDecimal item)
    {
        setBigDecimal("actualRoomArea", item);
    }
    /**
     * Object:������������ʷ's ����IDproperty 
     */
    public com.kingdee.bos.util.BOSUuid getHead()
    {
        return getBOSUuid("head");
    }
    public void setHead(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("head", item);
    }
    /**
     * Object:������������ʷ's ҵ������property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomAreaChangeTypeEnum getType()
    {
        return com.kingdee.eas.fdc.sellhouse.RoomAreaChangeTypeEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.sellhouse.RoomAreaChangeTypeEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    /**
     * Object:������������ʷ's ��������property 
     */
    public java.util.Date getOperationTime()
    {
        return getDate("operationTime");
    }
    public void setOperationTime(java.util.Date item)
    {
        setDate("operationTime", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BD7BBD1F");
    }
}