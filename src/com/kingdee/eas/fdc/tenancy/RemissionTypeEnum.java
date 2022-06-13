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
public class RemissionTypeEnum extends StringEnum
{
    public static final String ZZJM_VALUE = "ZZJM";//alias=正常减免
    public static final String WXSK_VALUE = "WXSK";//alias=无需收款

    public static final RemissionTypeEnum ZZJM = new RemissionTypeEnum("ZZJM", ZZJM_VALUE);
    public static final RemissionTypeEnum WXSK = new RemissionTypeEnum("WXSK", WXSK_VALUE);

    /**
     * construct function
     * @param String remissionTypeEnum
     */
    private RemissionTypeEnum(String name, String remissionTypeEnum)
    {
        super(name, remissionTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RemissionTypeEnum getEnum(String remissionTypeEnum)
    {
        return (RemissionTypeEnum)getEnum(RemissionTypeEnum.class, remissionTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RemissionTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RemissionTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RemissionTypeEnum.class);
    }
}