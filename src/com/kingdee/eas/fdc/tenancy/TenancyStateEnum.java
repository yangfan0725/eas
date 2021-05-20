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
public class TenancyStateEnum extends StringEnum
{
    public static final String UNTENANCY_VALUE = "UNTenancy";//alias=δ����
    public static final String WAITTENANCY_VALUE = "WaitTenancy";//alias=����
    public static final String KEEPTENANCY_VALUE = "KeepTenancy";//alias=���
    public static final String NEWTENANCY_VALUE = "NewTenancy";//alias=����
    public static final String CONTINUETENANCY_VALUE = "ContinueTenancy";//alias=����
    public static final String ENLARGETENANCY_VALUE = "EnlargeTenancy";//alias=����
    public static final String SINCEROBLIGATE_VALUE = "SincerObligate";//alias=����Ԥ��

    public static final TenancyStateEnum unTenancy = new TenancyStateEnum("unTenancy", UNTENANCY_VALUE);
    public static final TenancyStateEnum waitTenancy = new TenancyStateEnum("waitTenancy", WAITTENANCY_VALUE);
    public static final TenancyStateEnum keepTenancy = new TenancyStateEnum("keepTenancy", KEEPTENANCY_VALUE);
    public static final TenancyStateEnum newTenancy = new TenancyStateEnum("newTenancy", NEWTENANCY_VALUE);
    public static final TenancyStateEnum continueTenancy = new TenancyStateEnum("continueTenancy", CONTINUETENANCY_VALUE);
    public static final TenancyStateEnum enlargeTenancy = new TenancyStateEnum("enlargeTenancy", ENLARGETENANCY_VALUE);
    public static final TenancyStateEnum sincerObligate = new TenancyStateEnum("sincerObligate", SINCEROBLIGATE_VALUE);

    /**
     * construct function
     * @param String tenancyStateEnum
     */
    private TenancyStateEnum(String name, String tenancyStateEnum)
    {
        super(name, tenancyStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static TenancyStateEnum getEnum(String tenancyStateEnum)
    {
        return (TenancyStateEnum)getEnum(TenancyStateEnum.class, tenancyStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(TenancyStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(TenancyStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(TenancyStateEnum.class);
    }
}