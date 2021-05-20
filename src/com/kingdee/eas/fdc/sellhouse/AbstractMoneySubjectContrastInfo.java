package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMoneySubjectContrastInfo extends com.kingdee.eas.framework.ObjectBaseInfo implements Serializable 
{
    public AbstractMoneySubjectContrastInfo()
    {
        this("id");
    }
    protected AbstractMoneySubjectContrastInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ���Ϳ�Ŀ���� 's �������� property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo getMoneyDefine()
    {
        return (com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo)get("moneyDefine");
    }
    public void setMoneyDefine(com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo item)
    {
        put("moneyDefine", item);
    }
    /**
     * Object: ���Ϳ�Ŀ���� 's ��ƿ�Ŀ property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getAccountView()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("accountView");
    }
    public void setAccountView(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("accountView", item);
    }
    /**
     * Object:���Ϳ�Ŀ����'s ����ı�property 
     */
    public boolean isIsChanged()
    {
        return getBoolean("isChanged");
    }
    public void setIsChanged(boolean item)
    {
        setBoolean("isChanged", item);
    }
    /**
     * Object: ���Ϳ�Ŀ���� 's ��֯��Ԫ property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getFullOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("fullOrgUnit");
    }
    public void setFullOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("fullOrgUnit", item);
    }
    /**
     * Object:���Ϳ�Ŀ����'s ȡ����ʽproperty 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyAccountContrastEnum getContrastSide()
    {
        return com.kingdee.eas.fdc.sellhouse.MoneyAccountContrastEnum.getEnum(getString("contrastSide"));
    }
    public void setContrastSide(com.kingdee.eas.fdc.sellhouse.MoneyAccountContrastEnum item)
    {
		if (item != null) {
        setString("contrastSide", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9BA14AC9");
    }
}