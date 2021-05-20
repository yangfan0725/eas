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
public class ContractTypeOrgTypeEnum extends StringEnum
{
    public static final String BIGRANGE_VALUE = "BIGRANGE";//alias=����/��ҵ��/���й�˾
    public static final String SMALLRANGE_VALUE = "SMALLRANGE";//alias=��Ŀ��
    public static final String ALLRANGE_VALUE = "ALLRANGE";//alias=����/��ҵ��/���й�˾-��Ŀ��
    public static final String NEIBU_VALUE = "NEIBU";//alias=�ڲ�������˾�����ࣨ��ҵ�����к͡�ũҵ�ȣ�
    public static final String WAIBU_VALUE = "WAIBU";//alias=�ⲿ��Ӧ�̿ͻ�������

    public static final ContractTypeOrgTypeEnum BIGRANGE = new ContractTypeOrgTypeEnum("BIGRANGE", BIGRANGE_VALUE);
    public static final ContractTypeOrgTypeEnum SMALLRANGE = new ContractTypeOrgTypeEnum("SMALLRANGE", SMALLRANGE_VALUE);
    public static final ContractTypeOrgTypeEnum ALLRANGE = new ContractTypeOrgTypeEnum("ALLRANGE", ALLRANGE_VALUE);
    public static final ContractTypeOrgTypeEnum NEIBU = new ContractTypeOrgTypeEnum("NEIBU", NEIBU_VALUE);
    public static final ContractTypeOrgTypeEnum WAIBU = new ContractTypeOrgTypeEnum("WAIBU", WAIBU_VALUE);

    /**
     * construct function
     * @param String contractTypeOrgTypeEnum
     */
    private ContractTypeOrgTypeEnum(String name, String contractTypeOrgTypeEnum)
    {
        super(name, contractTypeOrgTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ContractTypeOrgTypeEnum getEnum(String contractTypeOrgTypeEnum)
    {
        return (ContractTypeOrgTypeEnum)getEnum(ContractTypeOrgTypeEnum.class, contractTypeOrgTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ContractTypeOrgTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ContractTypeOrgTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ContractTypeOrgTypeEnum.class);
    }
}