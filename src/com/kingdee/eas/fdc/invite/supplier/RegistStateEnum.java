/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class RegistStateEnum extends StringEnum
{
    public static final String REGISTAUDITTING_VALUE = "1REGISTAUDITTING";//alias=×¢²áÉóÅúÖÐ
    public static final String REGISTED_VALUE = "2REGISTED";//alias=×¢²á³É¹¦
    public static final String REGISTFAILURE_VALUE = "3REGISTFAILURE";//alias=×¢²áÊ§°Ü
    public static final String INVALID_VALUE = "4INVALID";//alias=Ê§Ð§

    public static final RegistStateEnum REGISTAUDITTING = new RegistStateEnum("REGISTAUDITTING", REGISTAUDITTING_VALUE);
    public static final RegistStateEnum REGISTED = new RegistStateEnum("REGISTED", REGISTED_VALUE);
    public static final RegistStateEnum REGISTFAILURE = new RegistStateEnum("REGISTFAILURE", REGISTFAILURE_VALUE);
    public static final RegistStateEnum INVALID = new RegistStateEnum("INVALID", INVALID_VALUE);

    /**
     * construct function
     * @param String registStateEnum
     */
    private RegistStateEnum(String name, String registStateEnum)
    {
        super(name, registStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RegistStateEnum getEnum(String registStateEnum)
    {
        return (RegistStateEnum)getEnum(RegistStateEnum.class, registStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RegistStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RegistStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RegistStateEnum.class);
    }
}