package com.kingdee.eas.fdc.tenancy;

import java.util.List;

import com.kingdee.bos.Context;
import com.kingdee.bos.kscript.KScriptException;

public abstract class AbstractFuncInfo {
	 public static final String CATEGORY_MATH = "����ȡ������";

	    String funcName, funcCategory, funcDesc;

	    /**
	     * @return ���� funcCategory��
	     */
	    public String getFuncCategory() {
	        return funcCategory;
	    }

	    /**
	     * @param funcCategory
	     *            Ҫ���õ� funcCategory��
	     */
	    public void setFuncCategory(String funcCategory) {
	        this.funcCategory = funcCategory;
	    }

	    /**
	     * @return ���� funcDesc��
	     */
	    public String getFuncDesc() {
	        return funcDesc;
	    }

	    /**
	     * @param funcDesc
	     *            Ҫ���õ� funcDesc��
	     */
	    public void setFuncDesc(String funcDesc) {
	        this.funcDesc = funcDesc;
	    }

	    /**
	     * @return ���� funcName��
	     */
	    public String getFuncName() {
	        return funcName;
	    }

	    /**
	     * @param funcName
	     *            Ҫ���õ� funcName��
	     */
	    public void setFuncName(String funcName) {
	        this.funcName = funcName;
	    }

	    public AbstractFuncInfo(String name, String category, String desc) {
	        funcName = name;
	        funcCategory = category;
	        funcDesc = desc;
	    }

	    public AbstractFuncInfo() {
	    }

	    abstract public Object evalFunction(List args) throws KScriptException;

	    /**
	     * @param args
	     * @return
	     */
	    protected Context getContext(List args) {
	        Context ctx = (Context) args.get(0);
	        return ctx;
	    }
}
