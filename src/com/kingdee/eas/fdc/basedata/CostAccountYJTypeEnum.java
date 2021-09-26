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
public class CostAccountYJTypeEnum extends StringEnum
{
    public static final String YJ_VALUE = "YJ";//alias=佣金类
    public static final String FYJ_VALUE = "FYJ";//alias=非佣金类

    public static final CostAccountYJTypeEnum YJ = new CostAccountYJTypeEnum("YJ", YJ_VALUE);
    public static final CostAccountYJTypeEnum FYJ = new CostAccountYJTypeEnum("FYJ", FYJ_VALUE);

    /**
     * construct function
     * @param String costAccountYJTypeEnum
     */
    private CostAccountYJTypeEnum(String name, String costAccountYJTypeEnum)
    {
        super(name, costAccountYJTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CostAccountYJTypeEnum getEnum(String costAccountYJTypeEnum)
    {
        return (CostAccountYJTypeEnum)getEnum(CostAccountYJTypeEnum.class, costAccountYJTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CostAccountYJTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CostAccountYJTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CostAccountYJTypeEnum.class);
    }
}