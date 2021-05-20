package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDeveloperManageInfo extends com.kingdee.eas.fdc.propertymgmt.PPMDataBaseInfo implements Serializable 
{
    public AbstractDeveloperManageInfo()
    {
        this("id");
    }
    protected AbstractDeveloperManageInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�����̹���'s ��ϵ��property 
     */
    public String getLinkMan()
    {
        return getString("linkMan");
    }
    public void setLinkMan(String item)
    {
        setString("linkMan", item);
    }
    /**
     * Object:�����̹���'s ��ϵ�绰property 
     */
    public String getLinkTel()
    {
        return getString("linkTel");
    }
    public void setLinkTel(String item)
    {
        setString("linkTel", item);
    }
    /**
     * Object:�����̹���'s ͨѶ��ַproperty 
     */
    public String getAddress()
    {
        return getString("address");
    }
    public void setAddress(String item)
    {
        setString("address", item);
    }
    /**
     * Object:�����̹���'s �ʱ�property 
     */
    public String getPostCode()
    {
        return getString("postCode");
    }
    public void setPostCode(String item)
    {
        setString("postCode", item);
    }
    /**
     * Object:�����̹���'s ��վproperty 
     */
    public String getWebSite()
    {
        return getString("webSite");
    }
    public void setWebSite(String item)
    {
        setString("webSite", item);
    }
    /**
     * Object:�����̹���'s ��������property 
     */
    public String getEMail()
    {
        return getString("eMail");
    }
    public void setEMail(String item)
    {
        setString("eMail", item);
    }
    /**
     * Object:�����̹���'s Ӫҵִ�պ�property 
     */
    public String getLicenceNumber()
    {
        return getString("licenceNumber");
    }
    public void setLicenceNumber(String item)
    {
        setString("licenceNumber", item);
    }
    /**
     * Object:�����̹���'s ���˴���  property 
     */
    public String getLawPerson()
    {
        return getString("lawPerson");
    }
    public void setLawPerson(String item)
    {
        setString("lawPerson", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9B500FDE");
    }
}