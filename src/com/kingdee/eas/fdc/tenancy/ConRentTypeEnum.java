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
public class ConRentTypeEnum extends StringEnum
{
    public static final String ZJ_VALUE = "ZJ";//alias=租金支付方式
    public static final String KD_VALUE = "KD";//alias=扣点支付方式

    public static final ConRentTypeEnum ZJ = new ConRentTypeEnum("ZJ", ZJ_VALUE);
    public static final ConRentTypeEnum KD = new ConRentTypeEnum("KD", KD_VALUE);

    /**
     * construct function
     * @param String conRentTypeEnum
     */
    private ConRentTypeEnum(String name, String conRentTypeEnum)
    {
        super(name, conRentTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ConRentTypeEnum getEnum(String conRentTypeEnum)
    {
        return (ConRentTypeEnum)getEnum(ConRentTypeEnum.class, conRentTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ConRentTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ConRentTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ConRentTypeEnum.class);
    }
}