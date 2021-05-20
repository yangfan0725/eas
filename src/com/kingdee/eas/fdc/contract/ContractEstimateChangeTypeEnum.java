/**
 * output package name
 */
package com.kingdee.eas.fdc.contract;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class ContractEstimateChangeTypeEnum extends StringEnum
{
    public static final String SUPPLY_VALUE = "SUPPLY";//alias=签订补充合同
    public static final String CHANGE_VALUE = "CHANGE";//alias=设计变更/现场签证

    public static final ContractEstimateChangeTypeEnum SUPPLY = new ContractEstimateChangeTypeEnum("SUPPLY", SUPPLY_VALUE);
    public static final ContractEstimateChangeTypeEnum CHANGE = new ContractEstimateChangeTypeEnum("CHANGE", CHANGE_VALUE);

    /**
     * construct function
     * @param String contractEstimateChangeTypeEnum
     */
    private ContractEstimateChangeTypeEnum(String name, String contractEstimateChangeTypeEnum)
    {
        super(name, contractEstimateChangeTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ContractEstimateChangeTypeEnum getEnum(String contractEstimateChangeTypeEnum)
    {
        return (ContractEstimateChangeTypeEnum)getEnum(ContractEstimateChangeTypeEnum.class, contractEstimateChangeTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ContractEstimateChangeTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ContractEstimateChangeTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ContractEstimateChangeTypeEnum.class);
    }
}