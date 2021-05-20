/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class EnterpriseKindEnum extends StringEnum
{
    public static final String QT_VALUE = "6";//alias=�������ι�˾
    public static final String HZQY_VALUE = "5";//alias=������ҵ
    public static final String WZQY_VALUE = "4";//alias=������ҵ
    public static final String GFYXGS_VALUE = "3";//alias=�ɷ����޹�˾
    public static final String MYQY_VALUE = "2";//alias=��Ӫ��ҵ
    public static final String GYQY_VALUE = "1";//alias=������ҵ
    public static final String QM_VALUE = "7";//alias=ȫ��������
    public static final String JT_VALUE = "8";//alias=����������
    public static final String HH_VALUE = "9";//alias=�ϻ���ҵ

    public static final EnterpriseKindEnum QT = new EnterpriseKindEnum("QT", QT_VALUE);
    public static final EnterpriseKindEnum HZQY = new EnterpriseKindEnum("HZQY", HZQY_VALUE);
    public static final EnterpriseKindEnum WZQY = new EnterpriseKindEnum("WZQY", WZQY_VALUE);
    public static final EnterpriseKindEnum GFYXGS = new EnterpriseKindEnum("GFYXGS", GFYXGS_VALUE);
    public static final EnterpriseKindEnum MYQY = new EnterpriseKindEnum("MYQY", MYQY_VALUE);
    public static final EnterpriseKindEnum GYQY = new EnterpriseKindEnum("GYQY", GYQY_VALUE);
    public static final EnterpriseKindEnum QM = new EnterpriseKindEnum("QM", QM_VALUE);
    public static final EnterpriseKindEnum JT = new EnterpriseKindEnum("JT", JT_VALUE);
    public static final EnterpriseKindEnum HH = new EnterpriseKindEnum("HH", HH_VALUE);

    /**
     * construct function
     * @param String enterpriseKindEnum
     */
    private EnterpriseKindEnum(String name, String enterpriseKindEnum)
    {
        super(name, enterpriseKindEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static EnterpriseKindEnum getEnum(String enterpriseKindEnum)
    {
        return (EnterpriseKindEnum)getEnum(EnterpriseKindEnum.class, enterpriseKindEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(EnterpriseKindEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(EnterpriseKindEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(EnterpriseKindEnum.class);
    }
}