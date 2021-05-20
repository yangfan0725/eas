package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupplierQualifyEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSupplierQualifyEntryInfo()
    {
        this("id");
    }
    protected AbstractSupplierQualifyEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��Χ��Ӧ�̷�¼ 's ��Χ��Ӧ�� property 
     */
    public com.kingdee.eas.fdc.invite.SupplierQualifyInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.SupplierQualifyInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.SupplierQualifyInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ��Χ��Ӧ�̷�¼ 's ��Ӧ�� property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo getSupplier()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo)get("supplier");
    }
    public void setSupplier(com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo item)
    {
        put("supplier", item);
    }
    /**
     * Object:��Χ��Ӧ�̷�¼'s ��ϵ��property 
     */
    public String getLinkPerson()
    {
        return getString("linkPerson");
    }
    public void setLinkPerson(String item)
    {
        setString("linkPerson", item);
    }
    /**
     * Object:��Χ��Ӧ�̷�¼'s ��ϵ�绰property 
     */
    public String getLinkPhone()
    {
        return getString("linkPhone");
    }
    public void setLinkPhone(String item)
    {
        setString("linkPhone", item);
    }
    /**
     * Object:��Χ��Ӧ�̷�¼'s ��Ӧ��״̬property 
     */
    public com.kingdee.eas.fdc.invite.supplier.IsGradeEnum getSupplierState()
    {
        return com.kingdee.eas.fdc.invite.supplier.IsGradeEnum.getEnum(getInt("supplierState"));
    }
    public void setSupplierState(com.kingdee.eas.fdc.invite.supplier.IsGradeEnum item)
    {
		if (item != null) {
        setInt("supplierState", item.getValue());
		}
    }
    /**
     * Object:��Χ��Ӧ�̷�¼'s Ͷ��������ѯ���property 
     */
    public com.kingdee.eas.fdc.invite.ResultEnum getResult()
    {
        return com.kingdee.eas.fdc.invite.ResultEnum.getEnum(getString("result"));
    }
    public void setResult(com.kingdee.eas.fdc.invite.ResultEnum item)
    {
		if (item != null) {
        setString("result", item.getValue());
		}
    }
    /**
     * Object:��Χ��Ӧ�̷�¼'s ����Ͷԭ��property 
     */
    public String getReason()
    {
        return getString("reason");
    }
    public void setReason(String item)
    {
        setString("reason", item);
    }
    /**
     * Object:��Χ��Ӧ�̷�¼'s �Ƿ���Χproperty 
     */
    public com.kingdee.eas.fdc.invite.supplier.DefaultStatusEnum getIsAccept()
    {
        return com.kingdee.eas.fdc.invite.supplier.DefaultStatusEnum.getEnum(getInt("isAccept"));
    }
    public void setIsAccept(com.kingdee.eas.fdc.invite.supplier.DefaultStatusEnum item)
    {
		if (item != null) {
        setInt("isAccept", item.getValue());
		}
    }
    /**
     * Object:��Χ��Ӧ�̷�¼'s ��עproperty 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object:��Χ��Ӧ�̷�¼'s �Ƽ���property 
     */
    public String getRecommended()
    {
        return getString("recommended");
    }
    public void setRecommended(String item)
    {
        setString("recommended", item);
    }
    /**
     * Object:��Χ��Ӧ�̷�¼'s ��Ŀ������property 
     */
    public String getManager()
    {
        return getString("manager");
    }
    public void setManager(String item)
    {
        setString("manager", item);
    }
    /**
     * Object:��Χ��Ӧ�̷�¼'s ����״̬property 
     */
    public String getCoState()
    {
        return getString("coState");
    }
    public void setCoState(String item)
    {
        setString("coState", item);
    }
    /**
     * Object:��Χ��Ӧ�̷�¼'s ��Լ�÷�property 
     */
    public java.math.BigDecimal getScore1()
    {
        return getBigDecimal("score1");
    }
    public void setScore1(java.math.BigDecimal item)
    {
        setBigDecimal("score1", item);
    }
    /**
     * Object:��Χ��Ӧ�̷�¼'s ����÷�property 
     */
    public java.math.BigDecimal getScore2()
    {
        return getBigDecimal("score2");
    }
    public void setScore2(java.math.BigDecimal item)
    {
        setBigDecimal("score2", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A6876635");
    }
}