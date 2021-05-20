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
    public static final String BIGRANGE_VALUE = "BIGRANGE";//alias=集团/事业部/城市公司
    public static final String SMALLRANGE_VALUE = "SMALLRANGE";//alias=项目部
    public static final String ALLRANGE_VALUE = "ALLRANGE";//alias=集团/事业部/城市公司-项目部
    public static final String NEIBU_VALUE = "NEIBU";//alias=内部关联公司往来类（物业、致中和、农业等）
    public static final String WAIBU_VALUE = "WAIBU";//alias=外部供应商客户往来类

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