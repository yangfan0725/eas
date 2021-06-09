/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class TenancyBillTypeEnum extends StringEnum
{
    public static final String FWZL_VALUE = "FWZL";//alias=房屋租赁
    public static final String GGWLEDZL_VALUE = "GGWLEDZL";//alias=广告位/LED租赁
    public static final String DZJYSR_VALUE = "DZJYSR";//alias=多种经营收入

    public static final TenancyBillTypeEnum FWZL = new TenancyBillTypeEnum("FWZL", FWZL_VALUE);
    public static final TenancyBillTypeEnum GGWLEDZL = new TenancyBillTypeEnum("GGWLEDZL", GGWLEDZL_VALUE);
    public static final TenancyBillTypeEnum DZJYSR = new TenancyBillTypeEnum("DZJYSR", DZJYSR_VALUE);

    /**
     * construct function
     * @param String tenancyBillTypeEnum
     */
    private TenancyBillTypeEnum(String name, String tenancyBillTypeEnum)
    {
        super(name, tenancyBillTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static TenancyBillTypeEnum getEnum(String tenancyBillTypeEnum)
    {
        return (TenancyBillTypeEnum)getEnum(TenancyBillTypeEnum.class, tenancyBillTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(TenancyBillTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(TenancyBillTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(TenancyBillTypeEnum.class);
    }
}