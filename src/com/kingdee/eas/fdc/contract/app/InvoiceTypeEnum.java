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
public class InvoiceTypeEnum extends StringEnum
{
    public static final String SPECIAL_VALUE = "SPECIAL";//alias=增值税专用发票
    public static final String ORDINARY_VALUE = "ORDINARY";//alias=增值税普通发票

    public static final InvoiceTypeEnum SPECIAL = new InvoiceTypeEnum("SPECIAL", SPECIAL_VALUE);
    public static final InvoiceTypeEnum ORDINARY = new InvoiceTypeEnum("ORDINARY", ORDINARY_VALUE);

    /**
     * construct function
     * @param String invoiceTypeEnum
     */
    private InvoiceTypeEnum(String name, String invoiceTypeEnum)
    {
        super(name, invoiceTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static InvoiceTypeEnum getEnum(String invoiceTypeEnum)
    {
        return (InvoiceTypeEnum)getEnum(InvoiceTypeEnum.class, invoiceTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(InvoiceTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(InvoiceTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(InvoiceTypeEnum.class);
    }
}