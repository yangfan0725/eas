package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractActivityValueReportInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractActivityValueReportInfo()
    {
        this("id");
    }
    protected AbstractActivityValueReportInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's ��ĿIDproperty 
     */
    public String getProjectid()
    {
        return getString("projectid");
    }
    public void setProjectid(String item)
    {
        setString("projectid", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's ��Ŀ������property 
     */
    public String getProjectlongnumber()
    {
        return getString("projectlongnumber");
    }
    public void setProjectlongnumber(String item)
    {
        setString("projectlongnumber", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's ��Ŀ����property 
     */
    public String getProjectname()
    {
        return getString("projectname");
    }
    public void setProjectname(String item)
    {
        setString("projectname", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's ��Ʒ����property 
     */
    public String getProductConstitute()
    {
        return getString("productConstitute");
    }
    public void setProductConstitute(String item)
    {
        setString("productConstitute", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's ��Ʒ����property 
     */
    public String getTypename()
    {
        return getString("typename");
    }
    public void setTypename(String item)
    {
        setString("typename", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's �����property 
     */
    public String getAreaRange()
    {
        return getString("areaRange");
    }
    public void setAreaRange(String item)
    {
        setString("areaRange", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's ���������property 
     */
    public java.math.BigDecimal getTararea()
    {
        return getBigDecimal("tararea");
    }
    public void setTararea(java.math.BigDecimal item)
    {
        setBigDecimal("tararea", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's �����ܻ�ֵ����property 
     */
    public java.math.BigDecimal getTarcount()
    {
        return getBigDecimal("tarcount");
    }
    public void setTarcount(java.math.BigDecimal item)
    {
        setBigDecimal("tarcount", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's ��ֵ����property 
     */
    public java.math.BigDecimal getTarunitprice()
    {
        return getBigDecimal("tarunitprice");
    }
    public void setTarunitprice(java.math.BigDecimal item)
    {
        setBigDecimal("tarunitprice", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's ��ֵ���property 
     */
    public java.math.BigDecimal getTaramount()
    {
        return getBigDecimal("taramount");
    }
    public void setTaramount(java.math.BigDecimal item)
    {
        setBigDecimal("taramount", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's ��̬��ֵ�����property 
     */
    public java.math.BigDecimal getZjarea()
    {
        return getBigDecimal("zjarea");
    }
    public void setZjarea(java.math.BigDecimal item)
    {
        setBigDecimal("zjarea", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's ��̬��ֵ����property 
     */
    public java.math.BigDecimal getZjcount()
    {
        return getBigDecimal("zjcount");
    }
    public void setZjcount(java.math.BigDecimal item)
    {
        setBigDecimal("zjcount", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's ��̬��ֵ�ܵ���property 
     */
    public java.math.BigDecimal getZjunitprice()
    {
        return getBigDecimal("zjunitprice");
    }
    public void setZjunitprice(java.math.BigDecimal item)
    {
        setBigDecimal("zjunitprice", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's ��̬��ֵ�ܽ��property 
     */
    public java.math.BigDecimal getZjamount()
    {
        return getBigDecimal("zjamount");
    }
    public void setZjamount(java.math.BigDecimal item)
    {
        setBigDecimal("zjamount", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's δȡ֤δ�������property 
     */
    public java.math.BigDecimal getWqzarea()
    {
        return getBigDecimal("wqzarea");
    }
    public void setWqzarea(java.math.BigDecimal item)
    {
        setBigDecimal("wqzarea", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's property 
     */
    public java.math.BigDecimal getWqzcount()
    {
        return getBigDecimal("wqzcount");
    }
    public void setWqzcount(java.math.BigDecimal item)
    {
        setBigDecimal("wqzcount", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's δȡ֤δ���۵���property 
     */
    public java.math.BigDecimal getWqzunitprice()
    {
        return getBigDecimal("wqzunitprice");
    }
    public void setWqzunitprice(java.math.BigDecimal item)
    {
        setBigDecimal("wqzunitprice", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's δȡ֤δ���۽��property 
     */
    public java.math.BigDecimal getWqzamount()
    {
        return getBigDecimal("wqzamount");
    }
    public void setWqzamount(java.math.BigDecimal item)
    {
        setBigDecimal("wqzamount", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's δȡ֤�Ѷ������property 
     */
    public java.math.BigDecimal getWqzydjarea()
    {
        return getBigDecimal("wqzydjarea");
    }
    public void setWqzydjarea(java.math.BigDecimal item)
    {
        setBigDecimal("wqzydjarea", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's δȡ֤�Ѷ�������property 
     */
    public java.math.BigDecimal getWqzydjcount()
    {
        return getBigDecimal("wqzydjcount");
    }
    public void setWqzydjcount(java.math.BigDecimal item)
    {
        setBigDecimal("wqzydjcount", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's δȡ֤�Ѷ��۵���property 
     */
    public java.math.BigDecimal getWqzydjunitprice()
    {
        return getBigDecimal("wqzydjunitprice");
    }
    public void setWqzydjunitprice(java.math.BigDecimal item)
    {
        setBigDecimal("wqzydjunitprice", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's δȡ֤�Ѷ��۽��property 
     */
    public java.math.BigDecimal getWqzydjamount()
    {
        return getBigDecimal("wqzydjamount");
    }
    public void setWqzydjamount(java.math.BigDecimal item)
    {
        setBigDecimal("wqzydjamount", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's ��ȡ֤δ����property 
     */
    public java.math.BigDecimal getYqzwdjarea()
    {
        return getBigDecimal("yqzwdjarea");
    }
    public void setYqzwdjarea(java.math.BigDecimal item)
    {
        setBigDecimal("yqzwdjarea", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's ��ȡ֤δ����property 
     */
    public java.math.BigDecimal getYqzwdjcount()
    {
        return getBigDecimal("yqzwdjcount");
    }
    public void setYqzwdjcount(java.math.BigDecimal item)
    {
        setBigDecimal("yqzwdjcount", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's ��ȡ֤δ���۵���property 
     */
    public java.math.BigDecimal getYqzwdjunitprice()
    {
        return getBigDecimal("yqzwdjunitprice");
    }
    public void setYqzwdjunitprice(java.math.BigDecimal item)
    {
        setBigDecimal("yqzwdjunitprice", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's ��ȡ֤δ���۽��property 
     */
    public java.math.BigDecimal getYqzwdjamount()
    {
        return getBigDecimal("yqzwdjamount");
    }
    public void setYqzwdjamount(java.math.BigDecimal item)
    {
        setBigDecimal("yqzwdjamount", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's ��ȡ֤�Ѷ������property 
     */
    public java.math.BigDecimal getYqzydjarea()
    {
        return getBigDecimal("yqzydjarea");
    }
    public void setYqzydjarea(java.math.BigDecimal item)
    {
        setBigDecimal("yqzydjarea", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's ��ȡ֤�Ѷ���property 
     */
    public java.math.BigDecimal getYqzydjcount()
    {
        return getBigDecimal("yqzydjcount");
    }
    public void setYqzydjcount(java.math.BigDecimal item)
    {
        setBigDecimal("yqzydjcount", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's ��ȡ֤�Ѷ��۵���property 
     */
    public java.math.BigDecimal getYqzydjunitprice()
    {
        return getBigDecimal("yqzydjunitprice");
    }
    public void setYqzydjunitprice(java.math.BigDecimal item)
    {
        setBigDecimal("yqzydjunitprice", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's ��ȡ֤�Ѷ��۽��property 
     */
    public java.math.BigDecimal getYqzydjamount()
    {
        return getBigDecimal("yqzydjamount");
    }
    public void setYqzydjamount(java.math.BigDecimal item)
    {
        setBigDecimal("yqzydjamount", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's �������property 
     */
    public java.math.BigDecimal getYsarea()
    {
        return getBigDecimal("ysarea");
    }
    public void setYsarea(java.math.BigDecimal item)
    {
        setBigDecimal("ysarea", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's ��������property 
     */
    public java.math.BigDecimal getYscount()
    {
        return getBigDecimal("yscount");
    }
    public void setYscount(java.math.BigDecimal item)
    {
        setBigDecimal("yscount", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's ���۵���property 
     */
    public java.math.BigDecimal getYsunitprice()
    {
        return getBigDecimal("ysunitprice");
    }
    public void setYsunitprice(java.math.BigDecimal item)
    {
        setBigDecimal("ysunitprice", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's ���۽��property 
     */
    public java.math.BigDecimal getYsamount()
    {
        return getBigDecimal("ysamount");
    }
    public void setYsamount(java.math.BigDecimal item)
    {
        setBigDecimal("ysamount", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's ��������property 
     */
    public String getQuickName()
    {
        return getString("quickName");
    }
    public void setQuickName(String item)
    {
        setString("quickName", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's ��������property 
     */
    public String getQuickDes()
    {
        return getString("quickDes");
    }
    public void setQuickDes(String item)
    {
        setString("quickDes", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's �������property 
     */
    public java.math.BigDecimal getYeararea()
    {
        return getBigDecimal("yeararea");
    }
    public void setYeararea(java.math.BigDecimal item)
    {
        setBigDecimal("yeararea", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's ��������property 
     */
    public java.math.BigDecimal getYearcount()
    {
        return getBigDecimal("yearcount");
    }
    public void setYearcount(java.math.BigDecimal item)
    {
        setBigDecimal("yearcount", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's ���۾���property 
     */
    public java.math.BigDecimal getYearunitprice()
    {
        return getBigDecimal("yearunitprice");
    }
    public void setYearunitprice(java.math.BigDecimal item)
    {
        setBigDecimal("yearunitprice", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's ���۽��property 
     */
    public java.math.BigDecimal getYearamount()
    {
        return getBigDecimal("yearamount");
    }
    public void setYearamount(java.math.BigDecimal item)
    {
        setBigDecimal("yearamount", item);
    }
    /**
     * Object:��̬�ܻ�ֵ������ʷ��Ϣ's ���property 
     */
    public int getSeq()
    {
        return getInt("seq");
    }
    public void setSeq(int item)
    {
        setInt("seq", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("83D03E05");
    }
}