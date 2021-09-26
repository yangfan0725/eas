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
public class MarketCostTypeEnum extends StringEnum
{
    public static final String CONTRACT_VALUE = "CONTRACT";//alias=合同
    public static final String NOTEXTCONTRACT_VALUE = "NOTEXTCONTRACT";//alias=无文本
    public static final String JZ_VALUE = "JZ";//alias=记账单

    public static final MarketCostTypeEnum CONTRACT = new MarketCostTypeEnum("CONTRACT", CONTRACT_VALUE);
    public static final MarketCostTypeEnum NOTEXTCONTRACT = new MarketCostTypeEnum("NOTEXTCONTRACT", NOTEXTCONTRACT_VALUE);
    public static final MarketCostTypeEnum JZ = new MarketCostTypeEnum("JZ", JZ_VALUE);

    /**
     * construct function
     * @param String marketCostTypeEnum
     */
    private MarketCostTypeEnum(String name, String marketCostTypeEnum)
    {
        super(name, marketCostTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static MarketCostTypeEnum getEnum(String marketCostTypeEnum)
    {
        return (MarketCostTypeEnum)getEnum(MarketCostTypeEnum.class, marketCostTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(MarketCostTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(MarketCostTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(MarketCostTypeEnum.class);
    }
}