/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class SellStageEnum extends StringEnum
{
    public static final String RG_VALUE = "1RG";//alias=认购
    public static final String RGBG_VALUE = "2RGBG";//alias=认购变更
    public static final String QY_VALUE = "3QY";//alias=签约
    public static final String QYBG_VALUE = "4QYBG";//alias=签约变更
    public static final String WQ_VALUE = "5WQ";//alias=网签
    public static final String JF_VALUE = "6JF";//alias=交付

    public static final SellStageEnum RG = new SellStageEnum("RG", RG_VALUE);
    public static final SellStageEnum RGBG = new SellStageEnum("RGBG", RGBG_VALUE);
    public static final SellStageEnum QY = new SellStageEnum("QY", QY_VALUE);
    public static final SellStageEnum QYBG = new SellStageEnum("QYBG", QYBG_VALUE);
    public static final SellStageEnum WQ = new SellStageEnum("WQ", WQ_VALUE);
    public static final SellStageEnum JF = new SellStageEnum("JF", JF_VALUE);

    /**
     * construct function
     * @param String sellStageEnum
     */
    private SellStageEnum(String name, String sellStageEnum)
    {
        super(name, sellStageEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static SellStageEnum getEnum(String sellStageEnum)
    {
        return (SellStageEnum)getEnum(SellStageEnum.class, sellStageEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(SellStageEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(SellStageEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(SellStageEnum.class);
    }
}