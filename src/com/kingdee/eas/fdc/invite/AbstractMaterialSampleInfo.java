package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMaterialSampleInfo extends com.kingdee.eas.fdc.invite.BaseInviteInfo implements Serializable 
{
    public AbstractMaterialSampleInfo()
    {
        this("id");
    }
    protected AbstractMaterialSampleInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.invite.MaterialSampleEntrysCollection());
    }
    /**
     * Object: ����������ȷ�� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.invite.MaterialSampleEntrysCollection getEntry()
    {
        return (com.kingdee.eas.fdc.invite.MaterialSampleEntrysCollection)get("entry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1A7A41D5");
    }
}