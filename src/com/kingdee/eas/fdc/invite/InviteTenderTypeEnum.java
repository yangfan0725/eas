/**
 * output package name
 */
package com.kingdee.eas.fdc.invite;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class InviteTenderTypeEnum extends StringEnum
{
    public static final String JG_VALUE = "JG";//alias=����
    public static final String JZ_VALUE = "JZ";//alias=��װ
    public static final String JZZ_VALUE = "JZZ";//alias=����
    public static final String DD_VALUE = "DD";//alias=����

    public static final InviteTenderTypeEnum JG = new InviteTenderTypeEnum("JG", JG_VALUE);
    public static final InviteTenderTypeEnum JZ = new InviteTenderTypeEnum("JZ", JZ_VALUE);
    public static final InviteTenderTypeEnum JZZ = new InviteTenderTypeEnum("JZZ", JZZ_VALUE);
    public static final InviteTenderTypeEnum DD = new InviteTenderTypeEnum("DD", DD_VALUE);

    /**
     * construct function
     * @param String inviteTenderTypeEnum
     */
    private InviteTenderTypeEnum(String name, String inviteTenderTypeEnum)
    {
        super(name, inviteTenderTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static InviteTenderTypeEnum getEnum(String inviteTenderTypeEnum)
    {
        return (InviteTenderTypeEnum)getEnum(InviteTenderTypeEnum.class, inviteTenderTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(InviteTenderTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(InviteTenderTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(InviteTenderTypeEnum.class);
    }
}