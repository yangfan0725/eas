package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCCusBaseDataInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractFDCCusBaseDataInfo()
    {
        this("id");
    }
    protected AbstractFDCCusBaseDataInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �ͻ��������� 's �ͻ������������ property 
     */
    public com.kingdee.eas.fdc.basecrm.FDCCusBaseDataGroupInfo getGroup()
    {
        return (com.kingdee.eas.fdc.basecrm.FDCCusBaseDataGroupInfo)get("group");
    }
    public void setGroup(com.kingdee.eas.fdc.basecrm.FDCCusBaseDataGroupInfo item)
    {
        put("group", item);
    }
    /**
     * Object:�ͻ���������'s �Ƿ�Ĭ��property 
     */
    public boolean isIsDefault()
    {
        return getBoolean("isDefault");
    }
    public void setIsDefault(boolean item)
    {
        setBoolean("isDefault", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C46F3933");
    }
}