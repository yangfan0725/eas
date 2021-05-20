package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomModelInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractRoomModelInfo()
    {
        this("id");
    }
    protected AbstractRoomModelInfo(String pkField)
    {
        super(pkField);
        put("pic", new com.kingdee.eas.fdc.sellhouse.RoomModePicCollection());
    }
    /**
     * Object: ���� 's ¥�� property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingInfo getBuilding()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingInfo)get("building");
    }
    public void setBuilding(com.kingdee.eas.fdc.sellhouse.BuildingInfo item)
    {
        put("building", item);
    }
    /**
     * Object: ���� 's ������� property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo getRoomModelType()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo)get("roomModelType");
    }
    public void setRoomModelType(com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo item)
    {
        put("roomModelType", item);
    }
    /**
     * Object:����'s ����ͼ�ϴ���·��property 
     */
    public String getImgPath()
    {
        return getString("imgPath");
    }
    public void setImgPath(String item)
    {
        setString("imgPath", item);
    }
    /**
     * Object:����'s ����ͼproperty 
     */
    public byte[] getImgData()
    {
        return (byte[])get("imgData");
    }
    public void setImgData(byte[] item)
    {
        put("imgData", item);
    }
    /**
     * Object:����'s ͷproperty 
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
     * Object: ���� 's ����ͼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomModePicCollection getPic()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomModePicCollection)get("pic");
    }
    /**
     * Object: ���� 's ��Ŀ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B0C9FA93");
    }
}