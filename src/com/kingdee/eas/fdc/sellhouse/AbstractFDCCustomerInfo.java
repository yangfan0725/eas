package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCCustomerInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractFDCCustomerInfo()
    {
        this("id");
    }
    protected AbstractFDCCustomerInfo(String pkField)
    {
        super(pkField);
        put("brand", new com.kingdee.eas.fdc.sellhouse.CustomerBrandCollection());
        put("businessScope", new com.kingdee.eas.fdc.sellhouse.CustomerBusinessScopeEntryCollection());
        put("insiders", new com.kingdee.eas.fdc.insider.InsiderCollection());
        put("shareSellerList", new com.kingdee.eas.fdc.sellhouse.ShareSellerCollection());
        put("xqEntry", new com.kingdee.eas.fdc.sellhouse.XQEntryCollection());
        put("workArea", new com.kingdee.eas.fdc.sellhouse.CustomerWorkAreaEntryCollection());
        put("adapterLogList", new com.kingdee.eas.fdc.sellhouse.AdapterLogCollection());
        put("questionPaperAnser", new com.kingdee.eas.fdc.market.QuestionPaperAnswerCollection());
        put("linkmanList", new com.kingdee.eas.fdc.sellhouse.LinkmanEntryCollection());
    }
    /**
     * Object:K客户台账's 客户类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.CustomerTypeEnum getCustomerType()
    {
        return com.kingdee.eas.fdc.sellhouse.CustomerTypeEnum.getEnum(getString("customerType"));
    }
    public void setCustomerType(com.kingdee.eas.fdc.sellhouse.CustomerTypeEnum item)
    {
		if (item != null) {
        setString("customerType", item.getValue());
		}
    }
    /**
     * Object:K客户台账's 性别property 
     */
    public com.kingdee.eas.fdc.sellhouse.SexEnum getSex()
    {
        return com.kingdee.eas.fdc.sellhouse.SexEnum.getEnum(getString("sex"));
    }
    public void setSex(com.kingdee.eas.fdc.sellhouse.SexEnum item)
    {
		if (item != null) {
        setString("sex", item.getValue());
		}
    }
    /**
     * Object:K客户台账's 证件号码property 
     */
    public String getCertificateNumber()
    {
        return getString("certificateNumber");
    }
    public void setCertificateNumber(String item)
    {
        setString("certificateNumber", item);
    }
    /**
     * Object:K客户台账's 工作单位property 
     */
    public String getWorkCompany()
    {
        return getString("workCompany");
    }
    public void setWorkCompany(String item)
    {
        setString("workCompany", item);
    }
    /**
     * Object:K客户台账's 职业property 
     */
    public String getEmployment()
    {
        return getString("employment");
    }
    public void setEmployment(String item)
    {
        setString("employment", item);
    }
    /**
     * Object:K客户台账's 通信地址property 
     */
    public String getMailAddress()
    {
        return getString("mailAddress");
    }
    public void setMailAddress(String item)
    {
        setString("mailAddress", item);
    }
    /**
     * Object:K客户台账's 地址property 
     */
    public String getAddress()
    {
        return getString("address");
    }
    public void setAddress(String item)
    {
        setString("address", item);
    }
    /**
     * Object:K客户台账's 电子邮箱property 
     */
    public String getEmail()
    {
        return getString("email");
    }
    public void setEmail(String item)
    {
        setString("email", item);
    }
    /**
     * Object:K客户台账's 移动电话property 
     */
    public String getPhone()
    {
        return getString("phone");
    }
    public void setPhone(String item)
    {
        setString("phone", item);
    }
    /**
     * Object:K客户台账's 邮编property 
     */
    public String getPostalcode()
    {
        return getString("postalcode");
    }
    public void setPostalcode(String item)
    {
        setString("postalcode", item);
    }
    /**
     * Object:K客户台账's 登记地点property 
     */
    public String getBookedPlace()
    {
        return getString("bookedPlace");
    }
    public void setBookedPlace(String item)
    {
        setString("bookedPlace", item);
    }
    /**
     * Object: K客户台账 's 国家 property 
     */
    public com.kingdee.eas.basedata.assistant.CountryInfo getCountry()
    {
        return (com.kingdee.eas.basedata.assistant.CountryInfo)get("country");
    }
    public void setCountry(com.kingdee.eas.basedata.assistant.CountryInfo item)
    {
        put("country", item);
    }
    /**
     * Object: K客户台账 's 市 property 
     */
    public com.kingdee.eas.basedata.assistant.CityInfo getCity()
    {
        return (com.kingdee.eas.basedata.assistant.CityInfo)get("city");
    }
    public void setCity(com.kingdee.eas.basedata.assistant.CityInfo item)
    {
        put("city", item);
    }
    /**
     * Object: K客户台账 's 省 property 
     */
    public com.kingdee.eas.basedata.assistant.ProvinceInfo getProvince()
    {
        return (com.kingdee.eas.basedata.assistant.ProvinceInfo)get("province");
    }
    public void setProvince(com.kingdee.eas.basedata.assistant.ProvinceInfo item)
    {
        put("province", item);
    }
    /**
     * Object: K客户台账 's 镇 property 
     */
    public com.kingdee.eas.basedata.assistant.RegionInfo getRegion()
    {
        return (com.kingdee.eas.basedata.assistant.RegionInfo)get("region");
    }
    public void setRegion(com.kingdee.eas.basedata.assistant.RegionInfo item)
    {
        put("region", item);
    }
    /**
     * Object: K客户台账 's 系统客户 property 
     */
    public com.kingdee.eas.basedata.master.cssp.CustomerInfo getSysCustomer()
    {
        return (com.kingdee.eas.basedata.master.cssp.CustomerInfo)get("sysCustomer");
    }
    public void setSysCustomer(com.kingdee.eas.basedata.master.cssp.CustomerInfo item)
    {
        put("sysCustomer", item);
    }
    /**
     * Object: K客户台账 's 客户来源 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CustomerOriginInfo getCustomerOrigin()
    {
        return (com.kingdee.eas.fdc.sellhouse.CustomerOriginInfo)get("customerOrigin");
    }
    public void setCustomerOrigin(com.kingdee.eas.fdc.sellhouse.CustomerOriginInfo item)
    {
        put("customerOrigin", item);
    }
    /**
     * Object: K客户台账 's 接待方式 property 
     */
    public com.kingdee.eas.fdc.sellhouse.ReceptionTypeInfo getReceptionType()
    {
        return (com.kingdee.eas.fdc.sellhouse.ReceptionTypeInfo)get("receptionType");
    }
    public void setReceptionType(com.kingdee.eas.fdc.sellhouse.ReceptionTypeInfo item)
    {
        put("receptionType", item);
    }
    /**
     * Object: K客户台账 's 客户分级 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CustomerGradeInfo getCustomerGrade()
    {
        return (com.kingdee.eas.fdc.sellhouse.CustomerGradeInfo)get("customerGrade");
    }
    public void setCustomerGrade(com.kingdee.eas.fdc.sellhouse.CustomerGradeInfo item)
    {
        put("customerGrade", item);
    }
    /**
     * Object: K客户台账 's 家庭收入 property 
     */
    public com.kingdee.eas.fdc.sellhouse.FamillyEarningInfo getFamillyEarning()
    {
        return (com.kingdee.eas.fdc.sellhouse.FamillyEarningInfo)get("famillyEarning");
    }
    public void setFamillyEarning(com.kingdee.eas.fdc.sellhouse.FamillyEarningInfo item)
    {
        put("famillyEarning", item);
    }
    /**
     * Object: K客户台账 's 居住区域 property 
     */
    public com.kingdee.eas.fdc.sellhouse.HabitationAreaInfo getHabitationArea()
    {
        return (com.kingdee.eas.fdc.sellhouse.HabitationAreaInfo)get("habitationArea");
    }
    public void setHabitationArea(com.kingdee.eas.fdc.sellhouse.HabitationAreaInfo item)
    {
        put("habitationArea", item);
    }
    /**
     * Object: K客户台账 's 联系人 property 
     */
    public com.kingdee.eas.fdc.sellhouse.LinkmanEntryCollection getLinkmanList()
    {
        return (com.kingdee.eas.fdc.sellhouse.LinkmanEntryCollection)get("linkmanList");
    }
    /**
     * Object: K客户台账 's 销售员 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getSalesman()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("salesman");
    }
    public void setSalesman(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("salesman", item);
    }
    /**
     * Object: K客户台账 's 所属项目 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object:K客户台账's 启用property 
     */
    public boolean isIsEnabled()
    {
        return getBoolean("isEnabled");
    }
    public void setIsEnabled(boolean item)
    {
        setBoolean("isEnabled", item);
    }
    /**
     * Object:K客户台账's 是否认购property 
     */
    public boolean isIsSub()
    {
        return getBoolean("isSub");
    }
    public void setIsSub(boolean item)
    {
        setBoolean("isSub", item);
    }
    /**
     * Object:K客户台账's 重点跟进property 
     */
    public boolean isIsImportantTrack()
    {
        return getBoolean("isImportantTrack");
    }
    public void setIsImportantTrack(boolean item)
    {
        setBoolean("isImportantTrack", item);
    }
    /**
     * Object:K客户台账's 最新跟进日期property 
     */
    public java.util.Date getLastTrackDate()
    {
        return getDate("lastTrackDate");
    }
    public void setLastTrackDate(java.util.Date item)
    {
        setDate("lastTrackDate", item);
    }
    /**
     * Object:K客户台账's 交易次数property 
     */
    public long getTradeTime()
    {
        return getLong("tradeTime");
    }
    public void setTradeTime(long item)
    {
        setLong("tradeTime", item);
    }
    /**
     * Object:K客户台账's 跟进阶段property 
     */
    public com.kingdee.eas.fdc.sellhouse.TrackPhaseEnum getTrackPhase()
    {
        return com.kingdee.eas.fdc.sellhouse.TrackPhaseEnum.getEnum(getString("trackPhase"));
    }
    public void setTrackPhase(com.kingdee.eas.fdc.sellhouse.TrackPhaseEnum item)
    {
		if (item != null) {
        setString("trackPhase", item.getValue());
		}
    }
    /**
     * Object:K客户台账's 销售跟进阶段property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceStatusEnum getSaleTrackPhase()
    {
        return com.kingdee.eas.fdc.sellhouse.CommerceStatusEnum.getEnum(getString("saleTrackPhase"));
    }
    public void setSaleTrackPhase(com.kingdee.eas.fdc.sellhouse.CommerceStatusEnum item)
    {
		if (item != null) {
        setString("saleTrackPhase", item.getValue());
		}
    }
    /**
     * Object:K客户台账's 租赁跟进阶段property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceStatusEnum getTenancyTrackPhase()
    {
        return com.kingdee.eas.fdc.sellhouse.CommerceStatusEnum.getEnum(getString("tenancyTrackPhase"));
    }
    public void setTenancyTrackPhase(com.kingdee.eas.fdc.sellhouse.CommerceStatusEnum item)
    {
		if (item != null) {
        setString("tenancyTrackPhase", item.getValue());
		}
    }
    /**
     * Object: K客户台账 's 共享销售顾问 property 
     */
    public com.kingdee.eas.fdc.sellhouse.ShareSellerCollection getShareSellerList()
    {
        return (com.kingdee.eas.fdc.sellhouse.ShareSellerCollection)get("shareSellerList");
    }
    /**
     * Object: K客户台账 's 会员分录 property 
     */
    public com.kingdee.eas.fdc.insider.InsiderCollection getInsiders()
    {
        return (com.kingdee.eas.fdc.insider.InsiderCollection)get("insiders");
    }
    /**
     * Object:K客户台账's 售楼属性property 
     */
    public boolean isIsForSHE()
    {
        return getBoolean("isForSHE");
    }
    public void setIsForSHE(boolean item)
    {
        setBoolean("isForSHE", item);
    }
    /**
     * Object:K客户台账's 租赁属性property 
     */
    public boolean isIsForTen()
    {
        return getBoolean("isForTen");
    }
    public void setIsForTen(boolean item)
    {
        setBoolean("isForTen", item);
    }
    /**
     * Object:K客户台账's 物业属性property 
     */
    public boolean isIsForPPM()
    {
        return getBoolean("isForPPM");
    }
    public void setIsForPPM(boolean item)
    {
        setBoolean("isForPPM", item);
    }
    /**
     * Object:K客户台账's 企业性质property 
     */
    public com.kingdee.eas.fdc.sellhouse.EnterprisePropertyEnum getEnterpriceProperty()
    {
        return com.kingdee.eas.fdc.sellhouse.EnterprisePropertyEnum.getEnum(getString("enterpriceProperty"));
    }
    public void setEnterpriceProperty(com.kingdee.eas.fdc.sellhouse.EnterprisePropertyEnum item)
    {
		if (item != null) {
        setString("enterpriceProperty", item.getValue());
		}
    }
    /**
     * Object:K客户台账's 企业规模property 
     */
    public String getEnterpriceSize()
    {
        return getString("enterpriceSize");
    }
    public void setEnterpriceSize(String item)
    {
        setString("enterpriceSize", item);
    }
    /**
     * Object:K客户台账's 企业网址property 
     */
    public String getEnterpriceHomepage()
    {
        return getString("enterpriceHomepage");
    }
    public void setEnterpriceHomepage(String item)
    {
        setString("enterpriceHomepage", item);
    }
    /**
     * Object: K客户台账 's 转接历史记录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.AdapterLogCollection getAdapterLogList()
    {
        return (com.kingdee.eas.fdc.sellhouse.AdapterLogCollection)get("adapterLogList");
    }
    /**
     * Object: K客户台账 's 行业 property 
     */
    public com.kingdee.eas.basedata.assistant.IndustryInfo getIndustry()
    {
        return (com.kingdee.eas.basedata.assistant.IndustryInfo)get("industry");
    }
    public void setIndustry(com.kingdee.eas.basedata.assistant.IndustryInfo item)
    {
        put("industry", item);
    }
    /**
     * Object:K客户台账's 固定电话property 
     */
    public String getTel()
    {
        return getString("tel");
    }
    public void setTel(String item)
    {
        setString("tel", item);
    }
    /**
     * Object:K客户台账's 租赁交易次数property 
     */
    public long getTenTradeTime()
    {
        return getLong("tenTradeTime");
    }
    public void setTenTradeTime(long item)
    {
        setLong("tenTradeTime", item);
    }
    /**
     * Object: K客户台账 's 工作区域 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CustomerWorkAreaEntryCollection getWorkArea()
    {
        return (com.kingdee.eas.fdc.sellhouse.CustomerWorkAreaEntryCollection)get("workArea");
    }
    /**
     * Object:K客户台账's 传真property 
     */
    public String getFax()
    {
        return getString("fax");
    }
    public void setFax(String item)
    {
        setString("fax", item);
    }
    /**
     * Object:K客户台账's 移动电话2property 
     */
    public String getPhone2()
    {
        return getString("phone2");
    }
    public void setPhone2(String item)
    {
        setString("phone2", item);
    }
    /**
     * Object:K客户台账's 办公电话property 
     */
    public String getOfficeTel()
    {
        return getString("officeTel");
    }
    public void setOfficeTel(String item)
    {
        setString("officeTel", item);
    }
    /**
     * Object:K客户台账's QQ\MSNproperty 
     */
    public String getQQ()
    {
        return getString("QQ");
    }
    public void setQQ(String item)
    {
        setString("QQ", item);
    }
    /**
     * Object: K客户台账 's 合作模式 property 
     */
    public com.kingdee.eas.fdc.tenancy.CooperateModeInfo getCooperateMode()
    {
        return (com.kingdee.eas.fdc.tenancy.CooperateModeInfo)get("cooperateMode");
    }
    public void setCooperateMode(com.kingdee.eas.fdc.tenancy.CooperateModeInfo item)
    {
        put("cooperateMode", item);
    }
    /**
     * Object: K客户台账 's 业务范围 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CustomerBusinessScopeEntryCollection getBusinessScope()
    {
        return (com.kingdee.eas.fdc.sellhouse.CustomerBusinessScopeEntryCollection)get("businessScope");
    }
    /**
     * Object: K客户台账 's 客户经理 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getCustomerManager()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("customerManager");
    }
    public void setCustomerManager(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("customerManager", item);
    }
    /**
     * Object: K客户台账 's businessNature property 
     */
    public com.kingdee.eas.fdc.tenancy.NatureEnterpriseInfo getBusinessNature()
    {
        return (com.kingdee.eas.fdc.tenancy.NatureEnterpriseInfo)get("businessNature");
    }
    public void setBusinessNature(com.kingdee.eas.fdc.tenancy.NatureEnterpriseInfo item)
    {
        put("businessNature", item);
    }
    /**
     * Object:K客户台账's 意向面积property 
     */
    public java.math.BigDecimal getIntentionArea()
    {
        return getBigDecimal("intentionArea");
    }
    public void setIntentionArea(java.math.BigDecimal item)
    {
        setBigDecimal("intentionArea", item);
    }
    /**
     * Object: K客户台账 's 意向户型 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo getRoomModelType()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo)get("roomModelType");
    }
    public void setRoomModelType(com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo item)
    {
        put("roomModelType", item);
    }
    /**
     * Object:K客户台账's 客户年龄property 
     */
    public java.math.BigDecimal getAge()
    {
        return getBigDecimal("age");
    }
    public void setAge(java.math.BigDecimal item)
    {
        setBigDecimal("age", item);
    }
    /**
     * Object: K客户台账 's 问卷回答 property 
     */
    public com.kingdee.eas.fdc.market.QuestionPaperAnswerCollection getQuestionPaperAnser()
    {
        return (com.kingdee.eas.fdc.market.QuestionPaperAnswerCollection)get("questionPaperAnser");
    }
    /**
     * Object: K客户台账 's 证件名称 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CertificateInfo getCertificateName()
    {
        return (com.kingdee.eas.fdc.sellhouse.CertificateInfo)get("certificateName");
    }
    public void setCertificateName(com.kingdee.eas.fdc.sellhouse.CertificateInfo item)
    {
        put("certificateName", item);
    }
    /**
     * Object:K客户台账's 出生日期property 
     */
    public java.util.Date getDayOfbirth()
    {
        return getDate("dayOfbirth");
    }
    public void setDayOfbirth(java.util.Date item)
    {
        setDate("dayOfbirth", item);
    }
    /**
     * Object:K客户台账's 籍贯property 
     */
    public String getNativePlace()
    {
        return getString("nativePlace");
    }
    public void setNativePlace(String item)
    {
        setString("nativePlace", item);
    }
    /**
     * Object:K客户台账's 中文property 
     */
    public String getText()
    {
        return getString("text");
    }
    public void setText(String item)
    {
        setString("text", item);
    }
    /**
     * Object:K客户台账's 法人代表property 
     */
    public String getFr()
    {
        return getString("fr");
    }
    public void setFr(String item)
    {
        setString("fr", item);
    }
    /**
     * Object:K客户台账's 成立日期property 
     */
    public java.util.Date getClrq()
    {
        return getDate("clrq");
    }
    public void setClrq(java.util.Date item)
    {
        setDate("clrq", item);
    }
    /**
     * Object:K客户台账's 注册资金property 
     */
    public java.math.BigDecimal getZczj()
    {
        return getBigDecimal("zczj");
    }
    public void setZczj(java.math.BigDecimal item)
    {
        setBigDecimal("zczj", item);
    }
    /**
     * Object:K客户台账's 币别property 
     */
    public com.kingdee.eas.fdc.invite.supplier.CurrencyEnum getBb()
    {
        return com.kingdee.eas.fdc.invite.supplier.CurrencyEnum.getEnum(getString("bb"));
    }
    public void setBb(com.kingdee.eas.fdc.invite.supplier.CurrencyEnum item)
    {
		if (item != null) {
        setString("bb", item.getValue());
		}
    }
    /**
     * Object:K客户台账's 雇员总数property 
     */
    public int getGyzs()
    {
        return getInt("gyzs");
    }
    public void setGyzs(int item)
    {
        setInt("gyzs", item);
    }
    /**
     * Object:K客户台账's 营业执照property 
     */
    public String getYyzz()
    {
        return getString("yyzz");
    }
    public void setYyzz(String item)
    {
        setString("yyzz", item);
    }
    /**
     * Object:K客户台账's 税务登记号property 
     */
    public String getTaxNO()
    {
        return getString("taxNO");
    }
    public void setTaxNO(String item)
    {
        setString("taxNO", item);
    }
    /**
     * Object:K客户台账's 公司注册号property 
     */
    public String getGsNo()
    {
        return getString("gsNo");
    }
    public void setGsNo(String item)
    {
        setString("gsNo", item);
    }
    /**
     * Object: K客户台账 's 需求信息 property 
     */
    public com.kingdee.eas.fdc.sellhouse.XQEntryCollection getXqEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.XQEntryCollection)get("xqEntry");
    }
    /**
     * Object:K客户台账's 资质property 
     */
    public String getZz()
    {
        return getString("zz");
    }
    public void setZz(String item)
    {
        setString("zz", item);
    }
    /**
     * Object:K客户台账's 面积property 
     */
    public java.math.BigDecimal getArea()
    {
        return getBigDecimal("area");
    }
    public void setArea(java.math.BigDecimal item)
    {
        setBigDecimal("area", item);
    }
    /**
     * Object:K客户台账's 指标property 
     */
    public String getZb()
    {
        return getString("zb");
    }
    public void setZb(String item)
    {
        setString("zb", item);
    }
    /**
     * Object: K客户台账 's 商机级别 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo getLevel()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo)get("level");
    }
    public void setLevel(com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo item)
    {
        put("level", item);
    }
    /**
     * Object:K客户台账's 首次接待时间property 
     */
    public java.util.Date getFirstDate()
    {
        return getDate("firstDate");
    }
    public void setFirstDate(java.util.Date item)
    {
        setDate("firstDate", item);
    }
    /**
     * Object: K客户台账 's 媒体渠道 property 
     */
    public com.kingdee.eas.fdc.market.ChannelTypeInfo getClassify()
    {
        return (com.kingdee.eas.fdc.market.ChannelTypeInfo)get("classify");
    }
    public void setClassify(com.kingdee.eas.fdc.market.ChannelTypeInfo item)
    {
        put("classify", item);
    }
    /**
     * Object:K客户台账's 备注property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object:K客户台账's 票据类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum getInvType()
    {
        return com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum.getEnum(getString("invType"));
    }
    public void setInvType(com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum item)
    {
		if (item != null) {
        setString("invType", item.getValue());
		}
    }
    /**
     * Object:K客户台账's 开票客户名称property 
     */
    public String getInvoiceName()
    {
        return getString("invoiceName");
    }
    public void setInvoiceName(String item)
    {
        setString("invoiceName", item);
    }
    /**
     * Object:K客户台账's 税务识别号property 
     */
    public String getTaxIdNumber()
    {
        return getString("taxIdNumber");
    }
    public void setTaxIdNumber(String item)
    {
        setString("taxIdNumber", item);
    }
    /**
     * Object:K客户台账's 发票地址property 
     */
    public String getInvoiceAddress()
    {
        return getString("invoiceAddress");
    }
    public void setInvoiceAddress(String item)
    {
        setString("invoiceAddress", item);
    }
    /**
     * Object:K客户台账's 发票电话property 
     */
    public String getInvoicePhone()
    {
        return getString("invoicePhone");
    }
    public void setInvoicePhone(String item)
    {
        setString("invoicePhone", item);
    }
    /**
     * Object:K客户台账's 开户行及帐号property 
     */
    public String getInvoiceBankAccount()
    {
        return getString("invoiceBankAccount");
    }
    public void setInvoiceBankAccount(String item)
    {
        setString("invoiceBankAccount", item);
    }
    /**
     * Object: K客户台账 's 品牌信息 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CustomerBrandCollection getBrand()
    {
        return (com.kingdee.eas.fdc.sellhouse.CustomerBrandCollection)get("brand");
    }
    /**
     * Object:K客户台账's 品牌描述property 
     */
    public String getBrandDesc()
    {
        return getString("brandDesc");
    }
    public void setBrandDesc(String item)
    {
        setString("brandDesc", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("682588A8");
    }
}