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
public class DelayPayTypeEnum extends StringEnum
{
    public static final String YQQY_VALUE = "YQQY";//alias=延期签约
    public static final String YQFK_VALUE = "YQFK";//alias=延期付款

    public static final DelayPayTypeEnum YQQY = new DelayPayTypeEnum("YQQY", YQQY_VALUE);
    public static final DelayPayTypeEnum YQFK = new DelayPayTypeEnum("YQFK", YQFK_VALUE);

    /**
     * construct function
     * @param String delayPayTypeEnum
     */
    private DelayPayTypeEnum(String name, String delayPayTypeEnum)
    {
        super(name, delayPayTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static DelayPayTypeEnum getEnum(String delayPayTypeEnum)
    {
        return (DelayPayTypeEnum)getEnum(DelayPayTypeEnum.class, delayPayTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(DelayPayTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(DelayPayTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(DelayPayTypeEnum.class);
    }
}