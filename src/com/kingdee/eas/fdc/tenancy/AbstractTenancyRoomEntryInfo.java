package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTenancyRoomEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractTenancyRoomEntryInfo()
    {
        this("id");
    }
    protected AbstractTenancyRoomEntryInfo(String pkField)
    {
        super(pkField);
        put("equipments", new com.kingdee.eas.fdc.tenancy.EquipmentEntryCollection());
        put("tenRoomCharges", new com.kingdee.eas.fdc.tenancy.TenancyRoomChargeEntryCollection());
        put("roomPayList", new com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryCollection());
        put("dealAmounts", new com.kingdee.eas.fdc.tenancy.DealAmountEntryCollection());
    }
    /**
     * Object: ���ⵥ�����¼ 's ���޺�ͬ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillInfo getTenancy()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyBillInfo)get("tenancy");
    }
    public void setTenancy(com.kingdee.eas.fdc.tenancy.TenancyBillInfo item)
    {
        put("tenancy", item);
    }
    /**
     * Object: ���ⵥ�����¼ 's ����ƻ� property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryCollection getRoomPayList()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryCollection)get("roomPayList");
    }
    /**
     * Object: ���ⵥ�����¼ 's ���� property 
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
     * Object:���ⵥ�����¼'s ���䳤����property 
     */
    public String getRoomLongNum()
    {
        return getString("roomLongNum");
    }
    public void setRoomLongNum(String item)
    {
        setString("roomLongNum", item);
    }
    /**
     * Object:���ⵥ�����¼'s �ɽ��������property 
     */
    public com.kingdee.eas.fdc.tenancy.RentTypeEnum getDealRentType()
    {
        return com.kingdee.eas.fdc.tenancy.RentTypeEnum.getEnum(getString("dealRentType"));
    }
    public void setDealRentType(com.kingdee.eas.fdc.tenancy.RentTypeEnum item)
    {
		if (item != null) {
        setString("dealRentType", item.getValue());
		}
    }
    /**
     * Object:���ⵥ�����¼'s ����ɽ����property 
     */
    public java.math.BigDecimal getDealRoomRent()
    {
        return getBigDecimal("dealRoomRent");
    }
    public void setDealRoomRent(java.math.BigDecimal item)
    {
        setBigDecimal("dealRoomRent", item);
    }
    /**
     * Object:���ⵥ�����¼'s �ɽ����ⵥ��property 
     */
    public java.math.BigDecimal getDealRoomRentPrice()
    {
        return getBigDecimal("dealRoomRentPrice");
    }
    public void setDealRoomRentPrice(java.math.BigDecimal item)
    {
        setBigDecimal("dealRoomRentPrice", item);
    }
    /**
     * Object:���ⵥ�����¼'s ԭ�������property 
     */
    public com.kingdee.eas.fdc.tenancy.RentTypeEnum getStandardRentType()
    {
        return com.kingdee.eas.fdc.tenancy.RentTypeEnum.getEnum(getString("standardRentType"));
    }
    public void setStandardRentType(com.kingdee.eas.fdc.tenancy.RentTypeEnum item)
    {
		if (item != null) {
        setString("standardRentType", item.getValue());
		}
    }
    /**
     * Object:���ⵥ�����¼'s ��׼�������property 
     */
    public java.math.BigDecimal getStandardRoomRent()
    {
        return getBigDecimal("standardRoomRent");
    }
    public void setStandardRoomRent(java.math.BigDecimal item)
    {
        setBigDecimal("standardRoomRent", item);
    }
    /**
     * Object:���ⵥ�����¼'s ��׼���ⵥ��property 
     */
    public java.math.BigDecimal getStandardRoomRentPrice()
    {
        return getBigDecimal("standardRoomRentPrice");
    }
    public void setStandardRoomRentPrice(java.math.BigDecimal item)
    {
        setBigDecimal("standardRoomRentPrice", item);
    }
    /**
     * Object:���ⵥ�����¼'s ��ʼ����property 
     */
    public java.util.Date getStartDate()
    {
        return getDate("startDate");
    }
    public void setStartDate(java.util.Date item)
    {
        setDate("startDate", item);
    }
    /**
     * Object:���ⵥ�����¼'s ��������property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    /**
     * Object:���ⵥ�����¼'s ʵ�ʽ�������property 
     */
    public java.util.Date getActDeliveryRoomDate()
    {
        return getDate("actDeliveryRoomDate");
    }
    public void setActDeliveryRoomDate(java.util.Date item)
    {
        setDate("actDeliveryRoomDate", item);
    }
    /**
     * Object:���ⵥ�����¼'s ���ڱ�־property 
     */
    public com.kingdee.eas.fdc.tenancy.FlagAtTermEnum getFlagAtTerm()
    {
        return com.kingdee.eas.fdc.tenancy.FlagAtTermEnum.getEnum(getString("flagAtTerm"));
    }
    public void setFlagAtTerm(com.kingdee.eas.fdc.tenancy.FlagAtTermEnum item)
    {
		if (item != null) {
        setString("flagAtTerm", item.getValue());
		}
    }
    /**
     * Object:���ⵥ�����¼'s ���������ԭ���������property 
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
     * Object:���ⵥ�����¼'s ʵ����������property 
     */
    public java.util.Date getActQuitTenDate()
    {
        return getDate("actQuitTenDate");
    }
    public void setActQuitTenDate(java.util.Date item)
    {
        setDate("actQuitTenDate", item);
    }
    /**
     * Object:���ⵥ�����¼'s �����¼״̬property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyStateEnum getTenRoomState()
    {
        return com.kingdee.eas.fdc.tenancy.TenancyStateEnum.getEnum(getString("tenRoomState"));
    }
    public void setTenRoomState(com.kingdee.eas.fdc.tenancy.TenancyStateEnum item)
    {
		if (item != null) {
        setString("tenRoomState", item.getValue());
		}
    }
    /**
     * Object:���ⵥ�����¼'s ����ʣ��Ѻ��property 
     */
    public java.math.BigDecimal getRemainDepositAmount()
    {
        return getBigDecimal("remainDepositAmount");
    }
    public void setRemainDepositAmount(java.math.BigDecimal item)
    {
        setBigDecimal("remainDepositAmount", item);
    }
    /**
     * Object:���ⵥ�����¼'s ���������property 
     */
    public java.math.BigDecimal getRoomTotalRent()
    {
        return getBigDecimal("roomTotalRent");
    }
    public void setRoomTotalRent(java.math.BigDecimal item)
    {
        setBigDecimal("roomTotalRent", item);
    }
    /**
     * Object: ���ⵥ�����¼ 's �����豸 property 
     */
    public com.kingdee.eas.fdc.tenancy.EquipmentEntryCollection getEquipments()
    {
        return (com.kingdee.eas.fdc.tenancy.EquipmentEntryCollection)get("equipments");
    }
    /**
     * Object: ���ⵥ�����¼ 's �շ���Ŀ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyRoomChargeEntryCollection getTenRoomCharges()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyRoomChargeEntryCollection)get("tenRoomCharges");
    }
    /**
     * Object:���ⵥ�����¼'s Ѻ����(������)property 
     */
    public java.math.BigDecimal getDepositAmount()
    {
        return getBigDecimal("depositAmount");
    }
    public void setDepositAmount(java.math.BigDecimal item)
    {
        setBigDecimal("depositAmount", item);
    }
    /**
     * Object:���ⵥ�����¼'s �������property 
     */
    public java.math.BigDecimal getFirstPayAmount()
    {
        return getBigDecimal("firstPayAmount");
    }
    public void setFirstPayAmount(java.math.BigDecimal item)
    {
        setBigDecimal("firstPayAmount", item);
    }
    /**
     * Object: ���ⵥ�����¼ 's �ɽ��۷�¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.DealAmountEntryCollection getDealAmounts()
    {
        return (com.kingdee.eas.fdc.tenancy.DealAmountEntryCollection)get("dealAmounts");
    }
    /**
     * Object:���ⵥ�����¼'s �����㷽ʽproperty 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyModeEnum getTenancyModel()
    {
        return com.kingdee.eas.fdc.tenancy.TenancyModeEnum.getEnum(getString("tenancyModel"));
    }
    public void setTenancyModel(com.kingdee.eas.fdc.tenancy.TenancyModeEnum item)
    {
		if (item != null) {
        setString("tenancyModel", item.getValue());
		}
    }
    /**
     * Object:���ⵥ�����¼'s �������property 
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
     * Object:���ⵥ�����¼'s װ�ޱ�׼property 
     */
    public String getFitmentStandard()
    {
        return getString("fitmentStandard");
    }
    public void setFitmentStandard(String item)
    {
        setString("fitmentStandard", item);
    }
    /**
     * Object: ���ⵥ�����¼ 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo getDirection()
    {
        return (com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo)get("direction");
    }
    public void setDirection(com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo item)
    {
        put("direction", item);
    }
    /**
     * Object: ���ⵥ�����¼ 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SightRequirementInfo getSight()
    {
        return (com.kingdee.eas.fdc.sellhouse.SightRequirementInfo)get("sight");
    }
    public void setSight(com.kingdee.eas.fdc.sellhouse.SightRequirementInfo item)
    {
        put("sight", item);
    }
    /**
     * Object: ���ⵥ�����¼ 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomModelInfo getRoomModel()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomModelInfo)get("roomModel");
    }
    public void setRoomModel(com.kingdee.eas.fdc.sellhouse.RoomModelInfo item)
    {
        put("roomModel", item);
    }
    /**
     * Object: ���ⵥ�����¼ 's ������ʽ property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomFormInfo getRoomForm()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomFormInfo)get("roomForm");
    }
    public void setRoomForm(com.kingdee.eas.fdc.sellhouse.RoomFormInfo item)
    {
        put("roomForm", item);
    }
    /**
     * Object: ���ⵥ�����¼ 's �������� property 
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
     * Object: ���ⵥ�����¼ 's ��Ʒ���� property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getProductType()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("productType");
    }
    public void setProductType(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("productType", item);
    }
    /**
     * Object: ���ⵥ�����¼ 's ��Ʒ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.ProductDetialInfo getProductDetail()
    {
        return (com.kingdee.eas.fdc.sellhouse.ProductDetialInfo)get("productDetail");
    }
    public void setProductDetail(com.kingdee.eas.fdc.sellhouse.ProductDetialInfo item)
    {
        put("productDetail", item);
    }
    /**
     * Object: ���ⵥ�����¼ 's ������; property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomUsageInfo getRoomUsage()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomUsageInfo)get("roomUsage");
    }
    public void setRoomUsage(com.kingdee.eas.fdc.sellhouse.RoomUsageInfo item)
    {
        put("roomUsage", item);
    }
    /**
     * Object:���ⵥ�����¼'s ����״̬property 
     */
    public com.kingdee.eas.fdc.tenancy.HandleStateEnum getHandleState()
    {
        return com.kingdee.eas.fdc.tenancy.HandleStateEnum.getEnum(getString("handleState"));
    }
    public void setHandleState(com.kingdee.eas.fdc.tenancy.HandleStateEnum item)
    {
		if (item != null) {
        setString("handleState", item.getValue());
		}
    }
    /**
     * Object:���ⵥ�����¼'s ����property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    /**
     * Object:���ⵥ�����¼'s ��׼�յ���property 
     */
    public java.math.BigDecimal getDayPrice()
    {
        return getBigDecimal("dayPrice");
    }
    public void setDayPrice(java.math.BigDecimal item)
    {
        setBigDecimal("dayPrice", item);
    }
    /**
     * Object:���ⵥ�����¼'s ʵ���յ��ۣ�Ԫ/��/�����property 
     */
    public java.math.BigDecimal getActDayprice()
    {
        return getBigDecimal("actDayprice");
    }
    public void setActDayprice(java.math.BigDecimal item)
    {
        setBigDecimal("actDayprice", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B54A7CE0");
    }
}