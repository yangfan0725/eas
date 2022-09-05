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
public class DoPropertyEnum extends StringEnum
{
    public static final String CP_VALUE = "CP";//alias=操盘项目
    public static final String FCP_VALUE = "FCP";//alias=非操盘项目

    public static final DoPropertyEnum CP = new DoPropertyEnum("CP", CP_VALUE);
    public static final DoPropertyEnum FCP = new DoPropertyEnum("FCP", FCP_VALUE);

    /**
     * construct function
     * @param String doPropertyEnum
     */
    private DoPropertyEnum(String name, String doPropertyEnum)
    {
        super(name, doPropertyEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static DoPropertyEnum getEnum(String doPropertyEnum)
    {
        return (DoPropertyEnum)getEnum(DoPropertyEnum.class, doPropertyEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(DoPropertyEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(DoPropertyEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(DoPropertyEnum.class);
    }
}