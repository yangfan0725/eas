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
public class JTTypeEnum extends StringEnum
{
    public static final String DS_VALUE = "DS";//alias=按点数
    public static final String TS_VALUE = "TS";//alias=按套数

    public static final JTTypeEnum DS = new JTTypeEnum("DS", DS_VALUE);
    public static final JTTypeEnum TS = new JTTypeEnum("TS", TS_VALUE);

    /**
     * construct function
     * @param String jTTypeEnum
     */
    private JTTypeEnum(String name, String jTTypeEnum)
    {
        super(name, jTTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static JTTypeEnum getEnum(String jTTypeEnum)
    {
        return (JTTypeEnum)getEnum(JTTypeEnum.class, jTTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(JTTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(JTTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(JTTypeEnum.class);
    }
}