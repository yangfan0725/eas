package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomJoinInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractRoomJoinInfo()
    {
        this("id");
    }
    protected AbstractRoomJoinInfo(String pkField)
    {
        super(pkField);
        put("approachEntry", new com.kingdee.eas.fdc.sellhouse.RoomJoinApproachEntryCollection());
        put("dataEntry", new com.kingdee.eas.fdc.sellhouse.RoomJoinDataEntryCollection());
        put("sharePPMList", new com.kingdee.eas.fdc.sellhouse.SharePPMCollection());
    }
    /**
     * Object: ������ 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomInfo getRoom()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomInfo)get("room");
    }
    public void setRoom(com.kingdee.eas.fdc.sellhouse.RoomInfo item)
    {
        put("room", item);
    }
    /**
     * Object:������'s �����������property 
     */
    public java.util.Date getJoinDate()
    {
        return getDate("joinDate");
    }
    public void setJoinDate(java.util.Date item)
    {
        setDate("joinDate", item);
    }
    /**
     * Object:������'s ����ʼʱ��property 
     */
    public java.sql.Time getJoinStartDate()
    {
        return getTime("joinStartDate");
    }
    public void setJoinStartDate(java.sql.Time item)
    {
        setTime("joinStartDate", item);
    }
    /**
     * Object:������'s �������ʱ��property 
     */
    public java.sql.Time getJoinEndDate()
    {
        return getTime("joinEndDate");
    }
    public void setJoinEndDate(java.sql.Time item)
    {
        setTime("joinEndDate", item);
    }
    /**
     * Object:������'s Լ���������property 
     */
    public java.util.Date getApptJoinDate()
    {
        return getDate("apptJoinDate");
    }
    public void setApptJoinDate(java.util.Date item)
    {
        setDate("apptJoinDate", item);
    }
    /**
     * Object:������'s �Ʒ�����property 
     */
    public java.util.Date getCalcFeeDate()
    {
        return getDate("calcFeeDate");
    }
    public void setCalcFeeDate(java.util.Date item)
    {
        setDate("calcFeeDate", item);
    }
    /**
     * Object:������'s ��������property 
     */
    public java.util.Date getFinishDate()
    {
        return getDate("finishDate");
    }
    public void setFinishDate(java.util.Date item)
    {
        setDate("finishDate", item);
    }
    /**
     * Object: ������ 's ������ property 
     */
    public com.kingdee.eas.base.permission.UserInfo getTransactor()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("transactor");
    }
    public void setTransactor(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("transactor", item);
    }
    /**
     * Object:������'s �ͻ�����ʽproperty 
     */
    public com.kingdee.eas.fdc.sellhouse.ShareModelEnum getJoinShareModel()
    {
        return com.kingdee.eas.fdc.sellhouse.ShareModelEnum.getEnum(getString("joinShareModel"));
    }
    public void setJoinShareModel(com.kingdee.eas.fdc.sellhouse.ShareModelEnum item)
    {
		if (item != null) {
        setString("joinShareModel", item.getValue());
		}
    }
    /**
     * Object: ������ 's ������ҵ��¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SharePPMCollection getSharePPMList()
    {
        return (com.kingdee.eas.fdc.sellhouse.SharePPMCollection)get("sharePPMList");
    }
    /**
     * Object:������'s ��ǰ����property 
     */
    public String getCurrentApproach()
    {
        return getString("currentApproach");
    }
    public void setCurrentApproach(String item)
    {
        setString("currentApproach", item);
    }
    /**
     * Object: ������ 's �������� property 
     */
    public com.kingdee.eas.fdc.sellhouse.BatchManageInfo getBatchManage()
    {
        return (com.kingdee.eas.fdc.sellhouse.BatchManageInfo)get("batchManage");
    }
    public void setBatchManage(com.kingdee.eas.fdc.sellhouse.BatchManageInfo item)
    {
        put("batchManage", item);
    }
    /**
     * Object: ������ 's ǩԼ�� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SignManageInfo getSign()
    {
        return (com.kingdee.eas.fdc.sellhouse.SignManageInfo)get("sign");
    }
    public void setSign(com.kingdee.eas.fdc.sellhouse.SignManageInfo item)
    {
        put("sign", item);
    }
    /**
     * Object: ������ 's ������� property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomJoinApproachEntryCollection getApproachEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomJoinApproachEntryCollection)get("approachEntry");
    }
    /**
     * Object: ������ 's �������� property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomJoinDataEntryCollection getDataEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomJoinDataEntryCollection)get("dataEntry");
    }
    /**
     * Object: ������ 's ��﷽�� property 
     */
    public com.kingdee.eas.fdc.sellhouse.JoinDoSchemeInfo getRoomJoinDoScheme()
    {
        return (com.kingdee.eas.fdc.sellhouse.JoinDoSchemeInfo)get("roomJoinDoScheme");
    }
    public void setRoomJoinDoScheme(com.kingdee.eas.fdc.sellhouse.JoinDoSchemeInfo item)
    {
        put("roomJoinDoScheme", item);
    }
    /**
     * Object:������'s Ӧ�������property 
     */
    public java.util.Date getPromiseFinishDate()
    {
        return getDate("promiseFinishDate");
    }
    public void setPromiseFinishDate(java.util.Date item)
    {
        setDate("promiseFinishDate", item);
    }
    /**
     * Object:������'s ʵ���������property 
     */
    public java.util.Date getActualFinishDate()
    {
        return getDate("actualFinishDate");
    }
    public void setActualFinishDate(java.util.Date item)
    {
        setDate("actualFinishDate", item);
    }
    /**
     * Object:������'s ������״̬property 
     */
    public com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum getJoinState()
    {
        return com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum.getEnum(getInt("joinState"));
    }
    public void setJoinState(com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum item)
    {
		if (item != null) {
        setInt("joinState", item.getValue());
		}
    }
    /**
     * Object:������'s ǰһ��״̬property 
     */
    public com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum getPreJoinState()
    {
        return com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum.getEnum(getInt("preJoinState"));
    }
    public void setPreJoinState(com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum item)
    {
		if (item != null) {
        setInt("preJoinState", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("60894880");
    }
}