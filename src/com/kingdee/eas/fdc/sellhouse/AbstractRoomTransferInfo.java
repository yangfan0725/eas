package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomTransferInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractRoomTransferInfo()
    {
        this("id");
    }
    protected AbstractRoomTransferInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �����ת 's ���� property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getRoom()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("room");
    }
    public void setRoom(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("room", item);
    }
    /**
     * Object:�����ת's ��ת״̬property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomTransferStateEnum getBizState()
    {
        return com.kingdee.eas.fdc.sellhouse.RoomTransferStateEnum.getEnum(getString("bizState"));
    }
    public void setBizState(com.kingdee.eas.fdc.sellhouse.RoomTransferStateEnum item)
    {
		if (item != null) {
        setString("bizState", item.getValue());
		}
    }
    /**
     * Object: �����ת 's �ͻ� property 
     */
    public com.kingdee.eas.basedata.master.cssp.CustomerInfo getCustomer()
    {
        return (com.kingdee.eas.basedata.master.cssp.CustomerInfo)get("customer");
    }
    public void setCustomer(com.kingdee.eas.basedata.master.cssp.CustomerInfo item)
    {
        put("customer", item);
    }
    /**
     * Object: �����ת 's ��Ʒ���� property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getProductType()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("productType");
    }
    public void setProductType(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("productType", item);
    }
    /**
     * Object:�����ת's �ܼ�property 
     */
    public java.math.BigDecimal getContractTotalAmount()
    {
        return getBigDecimal("contractTotalAmount");
    }
    public void setContractTotalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("contractTotalAmount", item);
    }
    /**
     * Object:�����ת's �������property 
     */
    public java.math.BigDecimal getBuildArea()
    {
        return getBigDecimal("buildArea");
    }
    public void setBuildArea(java.math.BigDecimal item)
    {
        setBigDecimal("buildArea", item);
    }
    /**
     * Object:�����ת's �Ƿ�����ƾ֤property 
     */
    public boolean isFiVouchered()
    {
        return getBoolean("fiVouchered");
    }
    public void setFiVouchered(boolean item)
    {
        setBoolean("fiVouchered", item);
    }
    /**
     * Object: �����ת 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    /**
     * Object: �����ת 's ��˾ property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getCompany()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("company");
    }
    public void setCompany(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("company", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("91BE8841");
    }
}