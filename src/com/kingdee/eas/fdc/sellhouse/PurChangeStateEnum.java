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
public class PurChangeStateEnum extends StringEnum
{
    public static final String DIRECT_VALUE = "DIRECT";//alias=直接认购
    public static final String CHANGE_VALUE = "CHANGE";//alias=换房认购

    public static final PurChangeStateEnum DIRECT = new PurChangeStateEnum("DIRECT", DIRECT_VALUE);
    public static final PurChangeStateEnum CHANGE = new PurChangeStateEnum("CHANGE", CHANGE_VALUE);

    /**
     * construct function
     * @param String purChangeStateEnum
     */
    private PurChangeStateEnum(String name, String purChangeStateEnum)
    {
        super(name, purChangeStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static PurChangeStateEnum getEnum(String purChangeStateEnum)
    {
        return (PurChangeStateEnum)getEnum(PurChangeStateEnum.class, purChangeStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(PurChangeStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(PurChangeStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(PurChangeStateEnum.class);
    }
}