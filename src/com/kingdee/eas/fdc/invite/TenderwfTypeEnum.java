/**
 * output package name
 */
package com.kingdee.eas.fdc.invite;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class TenderwfTypeEnum extends StringEnum
{
    public static final String SD_VALUE = "SD";//alias=四大工程类
    public static final String JC_VALUE = "JC";//alias=集采落地类
    public static final String XW_VALUE = "XW";//alias=项外其他工程

    public static final TenderwfTypeEnum SD = new TenderwfTypeEnum("SD", SD_VALUE);
    public static final TenderwfTypeEnum JC = new TenderwfTypeEnum("JC", JC_VALUE);
    public static final TenderwfTypeEnum XW = new TenderwfTypeEnum("XW", XW_VALUE);

    /**
     * construct function
     * @param String tenderwfTypeEnum
     */
    private TenderwfTypeEnum(String name, String tenderwfTypeEnum)
    {
        super(name, tenderwfTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static TenderwfTypeEnum getEnum(String tenderwfTypeEnum)
    {
        return (TenderwfTypeEnum)getEnum(TenderwfTypeEnum.class, tenderwfTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(TenderwfTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(TenderwfTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(TenderwfTypeEnum.class);
    }
}