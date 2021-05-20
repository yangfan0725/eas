package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupplierAppraiseTemplateInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractSupplierAppraiseTemplateInfo()
    {
        this("id");
    }
    protected AbstractSupplierAppraiseTemplateInfo(String pkField)
    {
        super(pkField);
        put("guideEntry", new com.kingdee.eas.fdc.invite.supplier.SupplierGuideEntryCollection());
    }
    /**
     * Object:����ģ��'s ����׶�property 
     */
    public com.kingdee.eas.fdc.invite.supplier.AppraisePhaseEnum getPhase()
    {
        return com.kingdee.eas.fdc.invite.supplier.AppraisePhaseEnum.getEnum(getString("phase"));
    }
    public void setPhase(com.kingdee.eas.fdc.invite.supplier.AppraisePhaseEnum item)
    {
		if (item != null) {
        setString("phase", item.getValue());
		}
    }
    /**
     * Object:����ģ��'s ��עproperty 
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
     * Object:����ģ��'s �Ƿ�����property 
     */
    public boolean isIsStart()
    {
        return getBoolean("isStart");
    }
    public void setIsStart(boolean item)
    {
        setBoolean("isStart", item);
    }
    /**
     * Object: ����ģ�� 's ģ������ property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTypeInfo getAppraiseType()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTypeInfo)get("appraiseType");
    }
    public void setAppraiseType(com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTypeInfo item)
    {
        put("appraiseType", item);
    }
    /**
     * Object: ����ģ�� 's ָ���¼ property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierGuideEntryCollection getGuideEntry()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierGuideEntryCollection)get("guideEntry");
    }
    /**
     * Object: ����ģ�� 's �������� property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierEvaluationTypeInfo getEvaluationType()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierEvaluationTypeInfo)get("evaluationType");
    }
    public void setEvaluationType(com.kingdee.eas.fdc.invite.supplier.SupplierEvaluationTypeInfo item)
    {
        put("evaluationType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("218F44EF");
    }
}