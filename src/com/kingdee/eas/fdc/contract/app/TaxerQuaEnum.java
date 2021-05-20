/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.app;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class TaxerQuaEnum extends StringEnum
{
    public static final String NOMAL_VALUE = "NOMAL";//alias=一般纳税人
    public static final String SMALL_VALUE = "SMALL";//alias=小规模纳税人
    public static final String UNNOMAL_VALUE = "UNNOMAL";//alias=非增值税纳税人

    public static final TaxerQuaEnum NOMAL = new TaxerQuaEnum("NOMAL", NOMAL_VALUE);
    public static final TaxerQuaEnum SMALL = new TaxerQuaEnum("SMALL", SMALL_VALUE);
    public static final TaxerQuaEnum UNNOMAL = new TaxerQuaEnum("UNNOMAL", UNNOMAL_VALUE);

    /**
     * construct function
     * @param String taxerQuaEnum
     */
    private TaxerQuaEnum(String name, String taxerQuaEnum)
    {
        super(name, taxerQuaEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static TaxerQuaEnum getEnum(String taxerQuaEnum)
    {
        return (TaxerQuaEnum)getEnum(TaxerQuaEnum.class, taxerQuaEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(TaxerQuaEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(TaxerQuaEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(TaxerQuaEnum.class);
    }
}