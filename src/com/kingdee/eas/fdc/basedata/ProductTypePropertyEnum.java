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
public class ProductTypePropertyEnum extends StringEnum
{
    public static final String ZZ_VALUE = "ZZ";//alias=住宅
    public static final String BS_VALUE = "BS";//alias=别墅
    public static final String SY_VALUE = "SY";//alias=商业
    public static final String JD_VALUE = "JD";//alias=酒店
    public static final String BG_VALUE = "BG";//alias=办公
    public static final String CW_VALUE = "CW";//alias=车位/车库
    public static final String PT_VALUE = "PT";//alias=配套
    public static final String QT_VALUE = "QT";//alias=其他

    public static final ProductTypePropertyEnum ZZ = new ProductTypePropertyEnum("ZZ", ZZ_VALUE);
    public static final ProductTypePropertyEnum BS = new ProductTypePropertyEnum("BS", BS_VALUE);
    public static final ProductTypePropertyEnum SY = new ProductTypePropertyEnum("SY", SY_VALUE);
    public static final ProductTypePropertyEnum JD = new ProductTypePropertyEnum("JD", JD_VALUE);
    public static final ProductTypePropertyEnum BG = new ProductTypePropertyEnum("BG", BG_VALUE);
    public static final ProductTypePropertyEnum CW = new ProductTypePropertyEnum("CW", CW_VALUE);
    public static final ProductTypePropertyEnum PT = new ProductTypePropertyEnum("PT", PT_VALUE);
    public static final ProductTypePropertyEnum QT = new ProductTypePropertyEnum("QT", QT_VALUE);

    /**
     * construct function
     * @param String productTypePropertyEnum
     */
    private ProductTypePropertyEnum(String name, String productTypePropertyEnum)
    {
        super(name, productTypePropertyEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ProductTypePropertyEnum getEnum(String productTypePropertyEnum)
    {
        return (ProductTypePropertyEnum)getEnum(ProductTypePropertyEnum.class, productTypePropertyEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ProductTypePropertyEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ProductTypePropertyEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ProductTypePropertyEnum.class);
    }
}