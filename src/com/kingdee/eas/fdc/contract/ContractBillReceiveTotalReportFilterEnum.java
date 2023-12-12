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
public class ContractBillReceiveTotalReportFilterEnum extends StringEnum
{
    public static final String CONTRACT_VALUE = "CONTRACT";//alias=合同日期
    public static final String RECEIVE_VALUE = "RECEIVE";//alias=收款日期

    public static final ContractBillReceiveTotalReportFilterEnum CONTRACT = new ContractBillReceiveTotalReportFilterEnum("CONTRACT", CONTRACT_VALUE);
    public static final ContractBillReceiveTotalReportFilterEnum RECEIVE = new ContractBillReceiveTotalReportFilterEnum("RECEIVE", RECEIVE_VALUE);

    /**
     * construct function
     * @param String contractBillReceiveTotalReportFilterEnum
     */
    private ContractBillReceiveTotalReportFilterEnum(String name, String contractBillReceiveTotalReportFilterEnum)
    {
        super(name, contractBillReceiveTotalReportFilterEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ContractBillReceiveTotalReportFilterEnum getEnum(String contractBillReceiveTotalReportFilterEnum)
    {
        return (ContractBillReceiveTotalReportFilterEnum)getEnum(ContractBillReceiveTotalReportFilterEnum.class, contractBillReceiveTotalReportFilterEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ContractBillReceiveTotalReportFilterEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ContractBillReceiveTotalReportFilterEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ContractBillReceiveTotalReportFilterEnum.class);
    }
}