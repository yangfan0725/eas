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
public class CostPropertyEnum extends StringEnum
{
    public static final String COMP_COMFIRM_VALUE = "COMP_COMFIRM";//alias=固定总价
    public static final String TEMP_EVAL_VALUE = "TEMP_EVAL";//alias=暂定总价
    public static final String BASE_CONFIRM_VALUE = "BASE_CONFIRM";//alias=暂定总价转固定总价

    public static final CostPropertyEnum COMP_COMFIRM = new CostPropertyEnum("COMP_COMFIRM", COMP_COMFIRM_VALUE);
    public static final CostPropertyEnum TEMP_EVAL = new CostPropertyEnum("TEMP_EVAL", TEMP_EVAL_VALUE);
    public static final CostPropertyEnum BASE_CONFIRM = new CostPropertyEnum("BASE_CONFIRM", BASE_CONFIRM_VALUE);

    /**
     * construct function
     * @param String costPropertyEnum
     */
    private CostPropertyEnum(String name, String costPropertyEnum)
    {
        super(name, costPropertyEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CostPropertyEnum getEnum(String costPropertyEnum)
    {
        return (CostPropertyEnum)getEnum(CostPropertyEnum.class, costPropertyEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CostPropertyEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CostPropertyEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CostPropertyEnum.class);
    }
}