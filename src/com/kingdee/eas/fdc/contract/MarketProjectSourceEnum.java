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
public class MarketProjectSourceEnum extends StringEnum
{
    public static final String ZHLX_VALUE = "ZHLX";//alias=综合立项
    public static final String DXLX_VALUE = "DXLX";//alias=单项立项
    public static final String DSF_VALUE = "DSF";//alias=第三方费用申请单

    public static final MarketProjectSourceEnum ZHLX = new MarketProjectSourceEnum("ZHLX", ZHLX_VALUE);
    public static final MarketProjectSourceEnum DXLX = new MarketProjectSourceEnum("DXLX", DXLX_VALUE);
    public static final MarketProjectSourceEnum DSF = new MarketProjectSourceEnum("DSF", DSF_VALUE);

    /**
     * construct function
     * @param String marketProjectSourceEnum
     */
    private MarketProjectSourceEnum(String name, String marketProjectSourceEnum)
    {
        super(name, marketProjectSourceEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static MarketProjectSourceEnum getEnum(String marketProjectSourceEnum)
    {
        return (MarketProjectSourceEnum)getEnum(MarketProjectSourceEnum.class, marketProjectSourceEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(MarketProjectSourceEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(MarketProjectSourceEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(MarketProjectSourceEnum.class);
    }
}