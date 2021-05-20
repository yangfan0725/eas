/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class TaxInfoEnum extends StringEnum
{
    public static final String GENERAL_VALUE = "GENERAL";//alias=一般计征
    public static final String SIMPLE_VALUE = "SIMPLE";//alias=简易计征

    public static final TaxInfoEnum GENERAL = new TaxInfoEnum("GENERAL", GENERAL_VALUE);
    public static final TaxInfoEnum SIMPLE = new TaxInfoEnum("SIMPLE", SIMPLE_VALUE);

    /**
     * construct function
     * @param String taxInfoEnum
     */
    private TaxInfoEnum(String name, String taxInfoEnum)
    {
        super(name, taxInfoEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static TaxInfoEnum getEnum(String taxInfoEnum)
    {
        return (TaxInfoEnum)getEnum(TaxInfoEnum.class, taxInfoEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(TaxInfoEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(TaxInfoEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(TaxInfoEnum.class);
    }
}