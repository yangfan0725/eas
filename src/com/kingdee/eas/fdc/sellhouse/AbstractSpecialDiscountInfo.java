package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSpecialDiscountInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractSpecialDiscountInfo()
    {
        this("id");
    }
    protected AbstractSpecialDiscountInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.sellhouse.SpecialDiscountEntryCollection());
        put("agioEntry", new com.kingdee.eas.fdc.sellhouse.SpecialDiscountAgioEntryCollection());
    }
    /**
     * Object: �����ۿ��Żݵ� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SpecialDiscountEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.sellhouse.SpecialDiscountEntryCollection)get("entrys");
    }
    /**
     * Object:�����ۿ��Żݵ�'s �Ƿ�����ƾ֤property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    /**
     * Object:�����ۿ��Żݵ�'s ��Ŀ����property 
     */
    public String getItemName()
    {
        return getString("itemName");
    }
    public void setItemName(String item)
    {
        setString("itemName", item);
    }
    /**
     * Object:�����ۿ��Żݵ�'s ��������property 
     */
    public String getUnitPriceName()
    {
        return getString("unitPriceName");
    }
    public void setUnitPriceName(String item)
    {
        setString("unitPriceName", item);
    }
    /**
     * Object: �����ۿ��Żݵ� 's �ͻ� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHECustomerInfo getCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHECustomerInfo)get("customer");
    }
    public void setCustomer(com.kingdee.eas.fdc.sellhouse.SHECustomerInfo item)
    {
        put("customer", item);
    }
    /**
     * Object:�����ۿ��Żݵ�'s Ա���Ż�property 
     */
    public boolean isStaffDiscount()
    {
        return getBoolean("staffDiscount");
    }
    public void setStaffDiscount(boolean item)
    {
        setBoolean("staffDiscount", item);
    }
    /**
     * Object:�����ۿ��Żݵ�'s Ա������property 
     */
    public String getStaffName()
    {
        return getString("staffName");
    }
    public void setStaffName(String item)
    {
        setString("staffName", item);
    }
    /**
     * Object:�����ۿ��Żݵ�'s Ա������property 
     */
    public String getStaffRank()
    {
        return getString("staffRank");
    }
    public void setStaffRank(String item)
    {
        setString("staffRank", item);
    }
    /**
     * Object:�����ۿ��Żݵ�'s ��Ա����ϵproperty 
     */
    public String getStaffRelations()
    {
        return getString("staffRelations");
    }
    public void setStaffRelations(String item)
    {
        setString("staffRelations", item);
    }
    /**
     * Object:�����ۿ��Żݵ�'s �������property 
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
     * Object:�����ۿ��Żݵ�'s ��������property 
     */
    public java.math.BigDecimal getBuildingPrice()
    {
        return getBigDecimal("buildingPrice");
    }
    public void setBuildingPrice(java.math.BigDecimal item)
    {
        setBigDecimal("buildingPrice", item);
    }
    /**
     * Object:�����ۿ��Żݵ�'s �������property 
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
     * Object:�����ۿ��Żݵ�'s ���ڵ���property 
     */
    public java.math.BigDecimal getRoomPrice()
    {
        return getBigDecimal("roomPrice");
    }
    public void setRoomPrice(java.math.BigDecimal item)
    {
        setBigDecimal("roomPrice", item);
    }
    /**
     * Object:�����ۿ��Żݵ�'s ��׼�ܼ�property 
     */
    public java.math.BigDecimal getStandardTotalAmount()
    {
        return getBigDecimal("standardTotalAmount");
    }
    public void setStandardTotalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("standardTotalAmount", item);
    }
    /**
     * Object:�����ۿ��Żݵ�'s �Żݽ��property 
     */
    public java.math.BigDecimal getDiscountAmount()
    {
        return getBigDecimal("discountAmount");
    }
    public void setDiscountAmount(java.math.BigDecimal item)
    {
        setBigDecimal("discountAmount", item);
    }
    /**
     * Object:�����ۿ��Żݵ�'s �Żݺ󷿼��ܼ�property 
     */
    public java.math.BigDecimal getDiscountAfAmount()
    {
        return getBigDecimal("discountAfAmount");
    }
    public void setDiscountAfAmount(java.math.BigDecimal item)
    {
        setBigDecimal("discountAfAmount", item);
    }
    /**
     * Object:�����ۿ��Żݵ�'s �����Żݱ���property 
     */
    public java.math.BigDecimal getDiscountPercent()
    {
        return getBigDecimal("discountPercent");
    }
    public void setDiscountPercent(java.math.BigDecimal item)
    {
        setBigDecimal("discountPercent", item);
    }
    /**
     * Object:�����ۿ��Żݵ�'s �Żݺ�������property 
     */
    public java.math.BigDecimal getDiscountAfBPrice()
    {
        return getBigDecimal("discountAfBPrice");
    }
    public void setDiscountAfBPrice(java.math.BigDecimal item)
    {
        setBigDecimal("discountAfBPrice", item);
    }
    /**
     * Object:�����ۿ��Żݵ�'s �Żݺ����ڵ���property 
     */
    public java.math.BigDecimal getDiscountAfRPrice()
    {
        return getBigDecimal("discountAfRPrice");
    }
    public void setDiscountAfRPrice(java.math.BigDecimal item)
    {
        setBigDecimal("discountAfRPrice", item);
    }
    /**
     * Object: �����ۿ��Żݵ� 's ���� property 
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
     * Object: �����ۿ��Żݵ� 's ����� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo getPayType()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo)get("payType");
    }
    public void setPayType(com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo item)
    {
        put("payType", item);
    }
    /**
     * Object: �����ۿ��Żݵ� 's ������Ŀ property 
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
     * Object:�����ۿ��Żݵ�'s ���˵��property 
     */
    public String getCaseInfo()
    {
        return getString("caseInfo");
    }
    public void setCaseInfo(String item)
    {
        setString("caseInfo", item);
    }
    /**
     * Object:�����ۿ��Żݵ�'s ״̬property 
     */
    public com.kingdee.eas.fdc.basedata.FDCBillStateEnum getBizState()
    {
        return com.kingdee.eas.fdc.basedata.FDCBillStateEnum.getEnum(getString("bizState"));
    }
    public void setBizState(com.kingdee.eas.fdc.basedata.FDCBillStateEnum item)
    {
		if (item != null) {
        setString("bizState", item.getValue());
		}
    }
    /**
     * Object:�����ۿ��Żݵ�'s ����ʱ��property 
     */
    public java.util.Date getAuditTime()
    {
        return getDate("auditTime");
    }
    public void setAuditTime(java.util.Date item)
    {
        setDate("auditTime", item);
    }
    /**
     * Object:�����ۿ��Żݵ�'s �ܾ����ۿ�property 
     */
    public java.math.BigDecimal getManagerAgio()
    {
        return getBigDecimal("managerAgio");
    }
    public void setManagerAgio(java.math.BigDecimal item)
    {
        setBigDecimal("managerAgio", item);
    }
    /**
     * Object:�����ۿ��Żݵ�'s ���������ۿ�property 
     */
    public java.math.BigDecimal getSceneManagerAgio()
    {
        return getBigDecimal("sceneManagerAgio");
    }
    public void setSceneManagerAgio(java.math.BigDecimal item)
    {
        setBigDecimal("sceneManagerAgio", item);
    }
    /**
     * Object:�����ۿ��Żݵ�'s Ӫ���ܼ��ۿ�property 
     */
    public java.math.BigDecimal getSalesDirectorAgio()
    {
        return getBigDecimal("salesDirectorAgio");
    }
    public void setSalesDirectorAgio(java.math.BigDecimal item)
    {
        setBigDecimal("salesDirectorAgio", item);
    }
    /**
     * Object:�����ۿ��Żݵ�'s �ͻ���ϵproperty 
     */
    public com.kingdee.eas.fdc.sellhouse.SpecialDiscountRelateEnum getRelate()
    {
        return com.kingdee.eas.fdc.sellhouse.SpecialDiscountRelateEnum.getEnum(getString("relate"));
    }
    public void setRelate(com.kingdee.eas.fdc.sellhouse.SpecialDiscountRelateEnum item)
    {
		if (item != null) {
        setString("relate", item.getValue());
		}
    }
    /**
     * Object:�����ۿ��Żݵ�'s �׼�property 
     */
    public java.math.BigDecimal getBaseStandardPrice()
    {
        return getBigDecimal("baseStandardPrice");
    }
    public void setBaseStandardPrice(java.math.BigDecimal item)
    {
        setBigDecimal("baseStandardPrice", item);
    }
    /**
     * Object:�����ۿ��Żݵ�'s �ۿ�˵��property 
     */
    public String getAgioDesc()
    {
        return getString("agioDesc");
    }
    public void setAgioDesc(String item)
    {
        setString("agioDesc", item);
    }
    /**
     * Object: �����ۿ��Żݵ� 's �ۿ۷�¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SpecialDiscountAgioEntryCollection getAgioEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.SpecialDiscountAgioEntryCollection)get("agioEntry");
    }
    /**
     * Object:�����ۿ��Żݵ�'s ί����property 
     */
    public String getOnePerson()
    {
        return getString("onePerson");
    }
    public void setOnePerson(String item)
    {
        setString("onePerson", item);
    }
    /**
     * Object:�����ۿ��Żݵ�'s ��ί����property 
     */
    public String getTwoPerson()
    {
        return getString("twoPerson");
    }
    public void setTwoPerson(String item)
    {
        setString("twoPerson", item);
    }
    /**
     * Object: �����ۿ��Żݵ� 's ��Ʒ���� property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getProductTyupe()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("productTyupe");
    }
    public void setProductTyupe(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("productTyupe", item);
    }
    /**
     * Object:�����ۿ��Żݵ�'s �����ۿ��Żݱ���property 
     */
    public java.math.BigDecimal getBasePercent()
    {
        return getBigDecimal("basePercent");
    }
    public void setBasePercent(java.math.BigDecimal item)
    {
        setBigDecimal("basePercent", item);
    }
    /**
     * Object:�����ۿ��Żݵ�'s ���۲��property 
     */
    public java.math.BigDecimal getSubPrice()
    {
        return getBigDecimal("subPrice");
    }
    public void setSubPrice(java.math.BigDecimal item)
    {
        setBigDecimal("subPrice", item);
    }
    /**
     * Object:�����ۿ��Żݵ�'s ʧЧ����property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6012F8DF");
    }
}