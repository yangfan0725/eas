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
public class ContractTypeEnum extends StringEnum
{
    public static final String FZ_VALUE = "FZ";//alias=·µ×âÀà
    public static final String FFZ_VALUE = "FFZ";//alias=·Ç·µ×âÀà

    public static final ContractTypeEnum FZ = new ContractTypeEnum("FZ", FZ_VALUE);
    public static final ContractTypeEnum FFZ = new ContractTypeEnum("FFZ", FFZ_VALUE);

    /**
     * construct function
     * @param String contractTypeEnum
     */
    private ContractTypeEnum(String name, String contractTypeEnum)
    {
        super(name, contractTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ContractTypeEnum getEnum(String contractTypeEnum)
    {
        return (ContractTypeEnum)getEnum(ContractTypeEnum.class, contractTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ContractTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ContractTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ContractTypeEnum.class);
    }
}