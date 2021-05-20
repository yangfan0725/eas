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
public class WTInvoiceTypeEnum extends StringEnum
{
    public static final String SPECIAL_VALUE = "SPECIAL";//alias=��ֵ˰ר�÷�Ʊ
    public static final String ORDINARY_VALUE = "ORDINARY";//alias=��ֵ˰��ͨ��Ʊ
    public static final String NOMAL_VALUE = "NOMAL";//alias=��ͨ��Ʊ
    public static final String RECEIPT_VALUE = "RECEIPT";//alias=�վ�

    public static final WTInvoiceTypeEnum SPECIAL = new WTInvoiceTypeEnum("SPECIAL", SPECIAL_VALUE);
    public static final WTInvoiceTypeEnum ORDINARY = new WTInvoiceTypeEnum("ORDINARY", ORDINARY_VALUE);
    public static final WTInvoiceTypeEnum NOMAL = new WTInvoiceTypeEnum("NOMAL", NOMAL_VALUE);
    public static final WTInvoiceTypeEnum RECEIPT = new WTInvoiceTypeEnum("RECEIPT", RECEIPT_VALUE);

    /**
     * construct function
     * @param String wTInvoiceTypeEnum
     */
    private WTInvoiceTypeEnum(String name, String wTInvoiceTypeEnum)
    {
        super(name, wTInvoiceTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static WTInvoiceTypeEnum getEnum(String wTInvoiceTypeEnum)
    {
        return (WTInvoiceTypeEnum)getEnum(WTInvoiceTypeEnum.class, wTInvoiceTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(WTInvoiceTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(WTInvoiceTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(WTInvoiceTypeEnum.class);
    }
}