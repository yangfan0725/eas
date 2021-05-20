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
public class TenderTypeEnum extends StringEnum
{
    public static final String HZD_VALUE = "HZD";//alias=按制度要求定标
    public static final String WFZD_VALUE = "WFZD";//alias=违反制度定标

    public static final TenderTypeEnum HZD = new TenderTypeEnum("HZD", HZD_VALUE);
    public static final TenderTypeEnum WFZD = new TenderTypeEnum("WFZD", WFZD_VALUE);

    /**
     * construct function
     * @param String tenderTypeEnum
     */
    private TenderTypeEnum(String name, String tenderTypeEnum)
    {
        super(name, tenderTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static TenderTypeEnum getEnum(String tenderTypeEnum)
    {
        return (TenderTypeEnum)getEnum(TenderTypeEnum.class, tenderTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(TenderTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(TenderTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(TenderTypeEnum.class);
    }
}