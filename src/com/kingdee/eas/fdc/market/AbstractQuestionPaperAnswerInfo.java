package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractQuestionPaperAnswerInfo extends com.kingdee.eas.framework.BillBaseInfo implements Serializable 
{
    public AbstractQuestionPaperAnswerInfo()
    {
        this("id");
    }
    protected AbstractQuestionPaperAnswerInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.market.QuestionPaperAnswerEntryCollection());
    }
    /**
     * Object: �ʾ�ش� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.market.QuestionPaperAnswerEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.market.QuestionPaperAnswerEntryCollection)get("entrys");
    }
    /**
     * Object:�ʾ�ش�'s �Ƿ�����ƾ֤property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("fivouchered", item);
    }
    /**
     * Object: �ʾ�ش� 's ҵ��Ա property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getPersion()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("persion");
    }
    public void setPersion(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("persion", item);
    }
    /**
     * Object:�ʾ�ش�'s �ʾ���д����property 
     */
    public java.util.Date getInputDate()
    {
        return getDate("inputDate");
    }
    public void setInputDate(java.util.Date item)
    {
        setDate("inputDate", item);
    }
    /**
     * Object:�ʾ�ش�'s ��Ա����property 
     */
    public String getInsiderCard()
    {
        return getString("insiderCard");
    }
    public void setInsiderCard(String item)
    {
        setString("insiderCard", item);
    }
    /**
     * Object:�ʾ�ش�'s ҵ��Ա����property 
     */
    public String getPersionNumber()
    {
        return getString("persionNumber");
    }
    public void setPersionNumber(String item)
    {
        setString("persionNumber", item);
    }
    /**
     * Object: �ʾ�ش� 's �����ʾ� property 
     */
    public com.kingdee.eas.fdc.market.QuestionPaperDefineInfo getQuestionPaper()
    {
        return (com.kingdee.eas.fdc.market.QuestionPaperDefineInfo)get("questionPaper");
    }
    public void setQuestionPaper(com.kingdee.eas.fdc.market.QuestionPaperDefineInfo item)
    {
        put("questionPaper", item);
    }
    /**
     * Object: �ʾ�ش� 's ��Ա property 
     */
    public com.kingdee.eas.fdc.insider.InsiderInfo getInsider()
    {
        return (com.kingdee.eas.fdc.insider.InsiderInfo)get("insider");
    }
    public void setInsider(com.kingdee.eas.fdc.insider.InsiderInfo item)
    {
        put("insider", item);
    }
    /**
     * Object: �ʾ�ش� 's Ӫ����Ŀ property 
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
     * Object: �ʾ�ش� 's �ͻ� property 
     */
    public com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo getCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo)get("customer");
    }
    public void setCustomer(com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo item)
    {
        put("customer", item);
    }
    /**
     * Object: �ʾ�ش� 's �Ϲ��� property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseInfo getPurchase()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseInfo)get("purchase");
    }
    public void setPurchase(com.kingdee.eas.fdc.sellhouse.PurchaseInfo item)
    {
        put("purchase", item);
    }
    /**
     * Object: �ʾ�ش� 's ҵ������ property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getGoverner()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("governer");
    }
    public void setGoverner(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("governer", item);
    }
    /**
     * Object: �ʾ�ش� 's �̻� property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo getCommerceChance()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo)get("commerceChance");
    }
    public void setCommerceChance(com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo item)
    {
        put("commerceChance", item);
    }
    /**
     * Object: �ʾ�ش� 's �ͻ� property 
     */
    public com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo getQuestionCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo)get("questionCustomer");
    }
    public void setQuestionCustomer(com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo item)
    {
        put("questionCustomer", item);
    }
    /**
     * Object: �ʾ�ش� 's ��¥�ͻ� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHECustomerInfo getSheCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHECustomerInfo)get("sheCustomer");
    }
    public void setSheCustomer(com.kingdee.eas.fdc.sellhouse.SHECustomerInfo item)
    {
        put("sheCustomer", item);
    }
    /**
     * Object: �ʾ�ش� 's ��֯ property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("orgUnit");
    }
    public void setOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("orgUnit", item);
    }
    /**
     * Object:�ʾ�ش�'s nullproperty 
     */
    public String getCluesManage()
    {
        return getString("cluesManage");
    }
    public void setCluesManage(String item)
    {
        setString("cluesManage", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7E608333");
    }
}