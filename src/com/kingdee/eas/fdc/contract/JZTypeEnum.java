/**
 * output package name
 */
package com.kingdee.eas.fdc.contract;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class JZTypeEnum extends StringEnum
{
    public static final String YI_VALUE = "YI";//alias=一次性
    public static final String SE_VALUE = "SE";//alias=分摊

    public static final JZTypeEnum YI = new JZTypeEnum("YI", YI_VALUE);
    public static final JZTypeEnum SE = new JZTypeEnum("SE", SE_VALUE);

    /**
     * construct function
     * @param String jZTypeEnum
     */
    private JZTypeEnum(String name, String jZTypeEnum)
    {
        super(name, jZTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static JZTypeEnum getEnum(String jZTypeEnum)
    {
        return (JZTypeEnum)getEnum(JZTypeEnum.class, jZTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(JZTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(JZTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(JZTypeEnum.class);
    }
}