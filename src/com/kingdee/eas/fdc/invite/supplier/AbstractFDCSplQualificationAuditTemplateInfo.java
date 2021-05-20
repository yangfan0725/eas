package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCSplQualificationAuditTemplateInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractFDCSplQualificationAuditTemplateInfo()
    {
        this("id");
    }
    protected AbstractFDCSplQualificationAuditTemplateInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��Ӧ�������¼'s ����÷�property 
     */
    public java.math.BigDecimal getScore()
    {
        return getBigDecimal("score");
    }
    public void setScore(java.math.BigDecimal item)
    {
        setBigDecimal("score", item);
    }
    /**
     * Object: ��Ӧ�������¼ 's ������ property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getAuditPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("auditPerson");
    }
    public void setAuditPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("auditPerson", item);
    }
    /**
     * Object: ��Ӧ�������¼ 's ������ property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getAuditDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("auditDept");
    }
    public void setAuditDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("auditDept", item);
    }
    /**
     * Object:��Ӧ�������¼'s ����ʱ��property 
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
     * Object: ��Ӧ�������¼ 's ����ָ���¼ property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierGuideEntryInfo getTemplateEntry()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierGuideEntryInfo)get("templateEntry");
    }
    public void setTemplateEntry(com.kingdee.eas.fdc.invite.supplier.SupplierGuideEntryInfo item)
    {
        put("templateEntry", item);
    }
    /**
     * Object: ��Ӧ�������¼ 's ��ʵ�� property 
     */
    public com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��Ӧ�������¼'s �ϸ����property 
     */
    public com.kingdee.eas.fdc.invite.supplier.IsGradeEnum getIsPass()
    {
        return com.kingdee.eas.fdc.invite.supplier.IsGradeEnum.getEnum(getInt("isPass"));
    }
    public void setIsPass(com.kingdee.eas.fdc.invite.supplier.IsGradeEnum item)
    {
		if (item != null) {
        setInt("isPass", item.getValue());
		}
    }
    /**
     * Object:��Ӧ�������¼'s �������property 
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
     * Object: ��Ӧ�������¼ 's ѡ�� property 
     */
    public com.kingdee.eas.fdc.invite.supplier.ChooseInfo getChoose()
    {
        return (com.kingdee.eas.fdc.invite.supplier.ChooseInfo)get("choose");
    }
    public void setChoose(com.kingdee.eas.fdc.invite.supplier.ChooseInfo item)
    {
        put("choose", item);
    }
    /**
     * Object:��Ӧ�������¼'s ����property 
     */
    public String getWrite()
    {
        return getString("write");
    }
    public void setWrite(String item)
    {
        setString("write", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1D9975A8");
    }
}