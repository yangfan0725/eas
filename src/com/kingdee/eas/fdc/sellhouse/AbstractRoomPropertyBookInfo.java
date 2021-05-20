package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomPropertyBookInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractRoomPropertyBookInfo()
    {
        this("id");
    }
    protected AbstractRoomPropertyBookInfo(String pkField)
    {
        super(pkField);
        put("entryTwo", new com.kingdee.eas.fdc.sellhouse.RoomPropertyBookEntryTwoCollection());
        put("entry", new com.kingdee.eas.fdc.sellhouse.RoomPropertyBookEntryCollection());
    }
    /**
     * Object: ��Ȩ���� 's ���� property 
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
     * Object:��Ȩ����'s ��Ȩ�ϱ�����property 
     */
    public java.util.Date getReportDate()
    {
        return getDate("reportDate");
    }
    public void setReportDate(java.util.Date item)
    {
        setDate("reportDate", item);
    }
    /**
     * Object:��Ȩ����'s ��Ȩ��׼����property 
     */
    public java.util.Date getApproveDate()
    {
        return getDate("approveDate");
    }
    public void setApproveDate(java.util.Date item)
    {
        setDate("approveDate", item);
    }
    /**
     * Object:��Ȩ����'s ��Ȩ��ȡ����property 
     */
    public java.util.Date getDrawDate()
    {
        return getDate("drawDate");
    }
    public void setDrawDate(java.util.Date item)
    {
        setDate("drawDate", item);
    }
    /**
     * Object:��Ȩ����'s ��Ȩ��������property 
     */
    public java.util.Date getTransactDate()
    {
        return getDate("transactDate");
    }
    public void setTransactDate(java.util.Date item)
    {
        setDate("transactDate", item);
    }
    /**
     * Object:��Ȩ����'s ��Ȩ��������property 
     */
    public java.util.Date getRecordDate()
    {
        return getDate("recordDate");
    }
    public void setRecordDate(java.util.Date item)
    {
        setDate("recordDate", item);
    }
    /**
     * Object:��Ȩ����'s ��Ȩ֤���property 
     */
    public String getPropertyNumber()
    {
        return getString("propertyNumber");
    }
    public void setPropertyNumber(String item)
    {
        setString("propertyNumber", item);
    }
    /**
     * Object:��Ȩ����'s �뻧ָ��(�ѷ���)property 
     */
    public int getRuhuIndex()
    {
        return getInt("ruhuIndex");
    }
    public void setRuhuIndex(int item)
    {
        setInt("ruhuIndex", item);
    }
    /**
     * Object: ��Ȩ���� 's ������ property 
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
     * Object: ��Ȩ���� 's ��Ȩ������ property 
     */
    public com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeInfo getPropertyDoScheme()
    {
        return (com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeInfo)get("propertyDoScheme");
    }
    public void setPropertyDoScheme(com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeInfo item)
    {
        put("propertyDoScheme", item);
    }
    /**
     * Object:��Ȩ����'s ��Ȩ֤״̬property 
     */
    public com.kingdee.eas.fdc.sellhouse.PropertyStateEnum getPropertyState()
    {
        return com.kingdee.eas.fdc.sellhouse.PropertyStateEnum.getEnum(getString("propertyState"));
    }
    public void setPropertyState(com.kingdee.eas.fdc.sellhouse.PropertyStateEnum item)
    {
		if (item != null) {
        setString("propertyState", item.getValue());
		}
    }
    /**
     * Object: ��Ȩ���� 's ��¼һ property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomPropertyBookEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomPropertyBookEntryCollection)get("entry");
    }
    /**
     * Object: ��Ȩ���� 's ��¼�� property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomPropertyBookEntryTwoCollection getEntryTwo()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomPropertyBookEntryTwoCollection)get("entryTwo");
    }
    /**
     * Object:��Ȩ����'s ��ǰ����property 
     */
    public String getStep()
    {
        return getString("step");
    }
    public void setStep(String item)
    {
        setString("step", item);
    }
    /**
     * Object:��Ȩ����'s �뻧ָ�꣨�£�property 
     */
    public String getRuhuVarchar()
    {
        return getString("ruhuVarchar");
    }
    public void setRuhuVarchar(String item)
    {
        setString("ruhuVarchar", item);
    }
    /**
     * Object: ��Ȩ���� 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchInfo getBatch()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchInfo)get("batch");
    }
    public void setBatch(com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchInfo item)
    {
        put("batch", item);
    }
    /**
     * Object:��Ȩ����'s ��Ѻ��������property 
     */
    public java.util.Date getMortgageDate()
    {
        return getDate("mortgageDate");
    }
    public void setMortgageDate(java.util.Date item)
    {
        setDate("mortgageDate", item);
    }
    /**
     * Object:��Ȩ����'s ��Ѻ����property 
     */
    public String getMortgageBank()
    {
        return getString("mortgageBank");
    }
    public void setMortgageBank(String item)
    {
        setString("mortgageBank", item);
    }
    /**
     * Object: ��Ȩ���� 's ���� property 
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
     * Object: ��Ȩ���� 's ǩԼ�� property 
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
     * Object:��Ȩ����'s Ӧ�������property 
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
     * Object:��Ȩ����'s ʵ���������property 
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
     * Object:��Ȩ����'s ��Ȩ����״̬property 
     */
    public com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum getPropState()
    {
        return com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum.getEnum(getInt("propState"));
    }
    public void setPropState(com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum item)
    {
		if (item != null) {
        setInt("propState", item.getValue());
		}
    }
    /**
     * Object:��Ȩ����'s ǰһ��״̬property 
     */
    public com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum getPrePropState()
    {
        return com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum.getEnum(getInt("prePropState"));
    }
    public void setPrePropState(com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum item)
    {
		if (item != null) {
        setInt("prePropState", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("04BB90B9");
    }
}