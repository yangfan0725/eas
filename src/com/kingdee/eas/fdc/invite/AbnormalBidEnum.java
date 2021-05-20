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
public class AbnormalBidEnum extends StringEnum
{
    public static final String HIGH_VALUE = "HIGH";//alias=��߼���̭
    public static final String GIVEUP_VALUE = "GIVEUP";//alias=�Զ�����
    public static final String UNQUALIFIED_VALUE = "UNQUALIFIED";//alias=�����겻�ϸ�
    public static final String ILLEGAL_VALUE = "ILLEGAL";//alias=Υ��
    public static final String NONE_VALUE = "NONE";//alias=��

    public static final AbnormalBidEnum HIGH = new AbnormalBidEnum("HIGH", HIGH_VALUE);
    public static final AbnormalBidEnum GIVEUP = new AbnormalBidEnum("GIVEUP", GIVEUP_VALUE);
    public static final AbnormalBidEnum UNQUALIFIED = new AbnormalBidEnum("UNQUALIFIED", UNQUALIFIED_VALUE);
    public static final AbnormalBidEnum ILLEGAL = new AbnormalBidEnum("ILLEGAL", ILLEGAL_VALUE);
    public static final AbnormalBidEnum NONE = new AbnormalBidEnum("NONE", NONE_VALUE);

    /**
     * construct function
     * @param String abnormalBidEnum
     */
    private AbnormalBidEnum(String name, String abnormalBidEnum)
    {
        super(name, abnormalBidEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static AbnormalBidEnum getEnum(String abnormalBidEnum)
    {
        return (AbnormalBidEnum)getEnum(AbnormalBidEnum.class, abnormalBidEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(AbnormalBidEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(AbnormalBidEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(AbnormalBidEnum.class);
    }
}