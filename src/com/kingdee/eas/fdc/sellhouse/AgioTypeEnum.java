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
public class AgioTypeEnum extends StringEnum
{
    public static final String XJHC_VALUE = "XJHC";//alias=先减后乘
    public static final String XCHJ_VALUE = "XCHJ";//alias=先乘后减

    public static final AgioTypeEnum XJHC = new AgioTypeEnum("XJHC", XJHC_VALUE);
    public static final AgioTypeEnum XCHJ = new AgioTypeEnum("XCHJ", XCHJ_VALUE);

    /**
     * construct function
     * @param String agioTypeEnum
     */
    private AgioTypeEnum(String name, String agioTypeEnum)
    {
        super(name, agioTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static AgioTypeEnum getEnum(String agioTypeEnum)
    {
        return (AgioTypeEnum)getEnum(AgioTypeEnum.class, agioTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(AgioTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(AgioTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(AgioTypeEnum.class);
    }
}