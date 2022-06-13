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
    public static final String ZXQSGM_VALUE = "ZXQSGM";//alias=直系亲属间更名
    public static final String FZXQSGM_VALUE = "FZXQSGM";//alias=非直系亲属间更名
    public static final String YBTF_VALUE = "YBTF";//alias=一般退房
    public static final String HFTF_VALUE = "HFTF";//alias=换房退房
    public static final String GMTF_VALUE = "GMTF";//alias=更名退房
    public static final String TSZKTF_VALUE = "TSZKTF";//alias=特殊折扣退房
    public static final String YGTF_VALUE = "YGTF";//alias=员工退房（含员工及直系亲属换房、更名）

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