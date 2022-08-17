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
public class PropertyEnum extends StringEnum
{
    public static final String BY_VALUE = "BY";//alias=必要
    public static final String FBY_VALUE = "FBY";//alias=非必要

    public static final PropertyEnum BY = new PropertyEnum("BY", BY_VALUE);
    public static final PropertyEnum FBY = new PropertyEnum("FBY", FBY_VALUE);

    /**
     * construct function
     * @param String propertyEnum
     */
    private PropertyEnum(String name, String propertyEnum)
    {
        super(name, propertyEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static PropertyEnum getEnum(String propertyEnum)
    {
        return (PropertyEnum)getEnum(PropertyEnum.class, propertyEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(PropertyEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(PropertyEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(PropertyEnum.class);
    }
}