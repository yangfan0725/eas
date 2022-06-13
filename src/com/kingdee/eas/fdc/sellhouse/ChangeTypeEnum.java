/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class ChangeTypeEnum extends StringEnum
{
    public static final String ZXQSGM_VALUE = "ZXQSGM";//alias=ֱϵ���������
    public static final String FZXQSGM_VALUE = "FZXQSGM";//alias=��ֱϵ���������
    public static final String YBTF_VALUE = "YBTF";//alias=һ���˷�
    public static final String HFTF_VALUE = "HFTF";//alias=�����˷�
    public static final String GMTF_VALUE = "GMTF";//alias=�����˷�
    public static final String TSZKTF_VALUE = "TSZKTF";//alias=�����ۿ��˷�
    public static final String YGTF_VALUE = "YGTF";//alias=Ա���˷�����Ա����ֱϵ����������������

    public static final ChangeTypeEnum ZXQSGM = new ChangeTypeEnum("ZXQSGM", ZXQSGM_VALUE);
    public static final ChangeTypeEnum FZXQSGM = new ChangeTypeEnum("FZXQSGM", FZXQSGM_VALUE);
    public static final ChangeTypeEnum YBTF = new ChangeTypeEnum("YBTF", YBTF_VALUE);
    public static final ChangeTypeEnum HFTF = new ChangeTypeEnum("HFTF", HFTF_VALUE);
    public static final ChangeTypeEnum GMTF = new ChangeTypeEnum("GMTF", GMTF_VALUE);
    public static final ChangeTypeEnum TSZKTF = new ChangeTypeEnum("TSZKTF", TSZKTF_VALUE);
    public static final ChangeTypeEnum YGTF = new ChangeTypeEnum("YGTF", YGTF_VALUE);

    /**
     * construct function
     * @param String changeTypeEnum
     */
    private ChangeTypeEnum(String name, String changeTypeEnum)
    {
        super(name, changeTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ChangeTypeEnum getEnum(String changeTypeEnum)
    {
        return (ChangeTypeEnum)getEnum(ChangeTypeEnum.class, changeTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ChangeTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ChangeTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ChangeTypeEnum.class);
    }
}