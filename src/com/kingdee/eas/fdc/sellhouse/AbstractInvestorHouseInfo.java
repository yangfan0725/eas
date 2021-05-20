package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInvestorHouseInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractInvestorHouseInfo()
    {
        this("id");
    }
    protected AbstractInvestorHouseInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.sellhouse.InvestorHouseLinkmanEntrysCollection());
        put("trackRecords", new com.kingdee.eas.fdc.sellhouse.InvestorHouseTrackRecordCollection());
    }
    /**
     * Object:Ͷ�ʷ�Դ's ԭע����property 
     */
    public String getOldEnrollName()
    {
        return getString("oldEnrollName");
    }
    public void setOldEnrollName(String item)
    {
        setString("oldEnrollName", item);
    }
    /**
     * Object:Ͷ�ʷ�Դ's ¥��λ��property 
     */
    public String getOrderLocation()
    {
        return getString("orderLocation");
    }
    public void setOrderLocation(String item)
    {
        setString("orderLocation", item);
    }
    /**
     * Object:Ͷ�ʷ�Դ's ��Դ����property 
     */
    public com.kingdee.eas.fdc.sellhouse.OrderSourceClassEnum getOrderSourceClass()
    {
        return com.kingdee.eas.fdc.sellhouse.OrderSourceClassEnum.getEnum(getString("orderSourceClass"));
    }
    public void setOrderSourceClass(com.kingdee.eas.fdc.sellhouse.OrderSourceClassEnum item)
    {
		if (item != null) {
        setString("orderSourceClass", item.getValue());
		}
    }
    /**
     * Object:Ͷ�ʷ�Դ's ָ��¥��property 
     */
    public String getAssignBuilding()
    {
        return getString("assignBuilding");
    }
    public void setAssignBuilding(String item)
    {
        setString("assignBuilding", item);
    }
    /**
     * Object:Ͷ�ʷ�Դ's ָ������property 
     */
    public String getAssignRoom()
    {
        return getString("assignRoom");
    }
    public void setAssignRoom(String item)
    {
        setString("assignRoom", item);
    }
    /**
     * Object:Ͷ�ʷ�Դ's ���˵��property 
     */
    public String getAreaDescription()
    {
        return getString("areaDescription");
    }
    public void setAreaDescription(String item)
    {
        setString("areaDescription", item);
    }
    /**
     * Object:Ͷ�ʷ�Դ's �۸�˵��property 
     */
    public String getPriceDescription()
    {
        return getString("priceDescription");
    }
    public void setPriceDescription(String item)
    {
        setString("priceDescription", item);
    }
    /**
     * Object:Ͷ�ʷ�Դ's Ƿ�����property 
     */
    public String getChargeComplexion()
    {
        return getString("chargeComplexion");
    }
    public void setChargeComplexion(String item)
    {
        setString("chargeComplexion", item);
    }
    /**
     * Object:Ͷ�ʷ�Դ's ��Ȩ״��property 
     */
    public com.kingdee.eas.fdc.sellhouse.OwnerShipStateEnum getOwnerShipState()
    {
        return com.kingdee.eas.fdc.sellhouse.OwnerShipStateEnum.getEnum(getString("OwnerShipState"));
    }
    public void setOwnerShipState(com.kingdee.eas.fdc.sellhouse.OwnerShipStateEnum item)
    {
		if (item != null) {
        setString("OwnerShipState", item.getValue());
		}
    }
    /**
     * Object:Ͷ�ʷ�Դ's ��Ȩ��״property 
     */
    public com.kingdee.eas.fdc.sellhouse.OwnerShipNewEnum getOwnerShipNew()
    {
        return com.kingdee.eas.fdc.sellhouse.OwnerShipNewEnum.getEnum(getString("ownerShipNew"));
    }
    public void setOwnerShipNew(com.kingdee.eas.fdc.sellhouse.OwnerShipNewEnum item)
    {
		if (item != null) {
        setString("ownerShipNew", item.getValue());
		}
    }
    /**
     * Object:Ͷ�ʷ�Դ's ҵ������property 
     */
    public java.util.Date getBizDate()
    {
        return getDate("bizDate");
    }
    public void setBizDate(java.util.Date item)
    {
        setDate("bizDate", item);
    }
    /**
     * Object: Ͷ�ʷ�Դ 's ¥������ property 
     */
    public com.kingdee.eas.fdc.sellhouse.HabitationAreaInfo getHabitationArea()
    {
        return (com.kingdee.eas.fdc.sellhouse.HabitationAreaInfo)get("habitationArea");
    }
    public void setHabitationArea(com.kingdee.eas.fdc.sellhouse.HabitationAreaInfo item)
    {
        put("habitationArea", item);
    }
    /**
     * Object: Ͷ�ʷ�Դ 's Ӫ������ property 
     */
    public com.kingdee.eas.base.permission.UserInfo getSalesman()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("salesman");
    }
    public void setSalesman(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("salesman", item);
    }
    /**
     * Object: Ͷ�ʷ�Դ 's �������� property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo getBuildingProperty()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo)get("buildingProperty");
    }
    public void setBuildingProperty(com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo item)
    {
        put("buildingProperty", item);
    }
    /**
     * Object: Ͷ�ʷ�Դ 's �Ǽ��� property 
     */
    public com.kingdee.eas.base.permission.UserInfo getBooker()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("booker");
    }
    public void setBooker(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("booker", item);
    }
    /**
     * Object:Ͷ�ʷ�Դ's ��ҵ��״property 
     */
    public String getWuyeActuality()
    {
        return getString("wuyeActuality");
    }
    public void setWuyeActuality(String item)
    {
        setString("wuyeActuality", item);
    }
    /**
     * Object: Ͷ�ʷ�Դ 's ��Ϣ��Դ property 
     */
    public com.kingdee.eas.fdc.sellhouse.InvestorHouseSourceInfo getInformationSource()
    {
        return (com.kingdee.eas.fdc.sellhouse.InvestorHouseSourceInfo)get("informationSource");
    }
    public void setInformationSource(com.kingdee.eas.fdc.sellhouse.InvestorHouseSourceInfo item)
    {
        put("informationSource", item);
    }
    /**
     * Object: Ͷ�ʷ�Դ 's ��ϵ�˷�¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.InvestorHouseLinkmanEntrysCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.sellhouse.InvestorHouseLinkmanEntrysCollection)get("entrys");
    }
    /**
     * Object: Ͷ�ʷ�Դ 's ������¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.InvestorHouseTrackRecordCollection getTrackRecords()
    {
        return (com.kingdee.eas.fdc.sellhouse.InvestorHouseTrackRecordCollection)get("trackRecords");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("58D7C66D");
    }
}