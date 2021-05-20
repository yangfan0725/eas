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
public class SignChangeStateEnum extends StringEnum
{
    public static final String DIRECT_VALUE = "DIRECT";//alias=直接签约
    public static final String CHANGE_VALUE = "CHANGE";//alias=换房签约

    public static final SignChangeStateEnum DIRECT = new SignChangeStateEnum("DIRECT", DIRECT_VALUE);
    public static final SignChangeStateEnum CHANGE = new SignChangeStateEnum("CHANGE", CHANGE_VALUE);

    /**
     * construct function
     * @param String signChangeStateEnum
     */
    private SignChangeStateEnum(String name, String signChangeStateEnum)
    {
        super(name, signChangeStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static SignChangeStateEnum getEnum(String signChangeStateEnum)
    {
        return (SignChangeStateEnum)getEnum(SignChangeStateEnum.class, signChangeStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(SignChangeStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(SignChangeStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(SignChangeStateEnum.class);
    }
}