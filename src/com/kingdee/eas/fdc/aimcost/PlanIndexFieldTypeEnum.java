/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class PlanIndexFieldTypeEnum extends StringEnum
{
    public static final String TEXT_VALUE = "TEXT";//alias=�ı�
    public static final String DIGITAL_VALUE = "DIGITAL";//alias=С��
    public static final String INT_VALUE = "INT";//alias=����
    public static final String RATE_VALUE = "RATE";//alias=�ٷֱ�

    public static final PlanIndexFieldTypeEnum TEXT = new PlanIndexFieldTypeEnum("TEXT", TEXT_VALUE);
    public static final PlanIndexFieldTypeEnum DIGITAL = new PlanIndexFieldTypeEnum("DIGITAL", DIGITAL_VALUE);
    public static final PlanIndexFieldTypeEnum INT = new PlanIndexFieldTypeEnum("INT", INT_VALUE);
    public static final PlanIndexFieldTypeEnum RATE = new PlanIndexFieldTypeEnum("RATE", RATE_VALUE);

    /**
     * construct function
     * @param String planIndexFieldTypeEnum
     */
    private PlanIndexFieldTypeEnum(String name, String planIndexFieldTypeEnum)
    {
        super(name, planIndexFieldTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static PlanIndexFieldTypeEnum getEnum(String planIndexFieldTypeEnum)
    {
        return (PlanIndexFieldTypeEnum)getEnum(PlanIndexFieldTypeEnum.class, planIndexFieldTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(PlanIndexFieldTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(PlanIndexFieldTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(PlanIndexFieldTypeEnum.class);
    }
}