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
public class DepositDealTypeEnum extends StringEnum
{
    public static final String QUITYJ_VALUE = "QUITYJ";//alias=��Ѻ������
    public static final String QUITZJ_VALUE = "QUITZJ";//alias=���������
    public static final String QUITQT_VALUE = "QUITQT";//alias=��������������

    public static final DepositDealTypeEnum QUITYJ = new DepositDealTypeEnum("QUITYJ", QUITYJ_VALUE);
    public static final DepositDealTypeEnum QUITZJ = new DepositDealTypeEnum("QUITZJ", QUITZJ_VALUE);
    public static final DepositDealTypeEnum QUITQT = new DepositDealTypeEnum("QUITQT", QUITQT_VALUE);

    /**
     * construct function
     * @param String depositDealTypeEnum
     */
    private DepositDealTypeEnum(String name, String depositDealTypeEnum)
    {
        super(name, depositDealTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static DepositDealTypeEnum getEnum(String depositDealTypeEnum)
    {
        return (DepositDealTypeEnum)getEnum(DepositDealTypeEnum.class, depositDealTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(DepositDealTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(DepositDealTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(DepositDealTypeEnum.class);
    }
}