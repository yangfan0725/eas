package com.kingdee.eas.fdc.sellhouse;

public class CodeGenerater {

	private String codeRule;//�������

	private int count;//����

	private int beginIndex;//��ʼ��ˮ��
	
	private int currentIndex = 0;//��ǰ��ˮ��
	
	public int getBeginIndex() {
		return beginIndex;
	}

	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}

	public String getCodeRule() {
		return codeRule;
	}

	public void setCodeRule(String codeRule) {
		this.codeRule = codeRule;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
    /**
     * ��ȡ��һ������
     */
    public String nextNumber() {
    	//��ʼ����ǰ����
    	if (currentIndex <= 0) {
    		currentIndex = beginIndex;
    	}
    	
    	int maskBegin = codeRule.indexOf("*");//*����ʼ����
    	int maskEnd = codeRule.lastIndexOf("*");//*����ֹ����
    	
    	StringBuffer seqNumber = new StringBuffer("");
    	
    	int seqLen = 0;//*�ŵĳ���
    	
    	seqLen = maskEnd - maskBegin + 1;
    	
    	String prefix =""; //ǰ׺
    	prefix = codeRule.substring(0, maskBegin);
    	
    	String suffix =""; //��׺
    	suffix = codeRule.substring(maskEnd + 1);
    	
    	String sMaxIndex = String.valueOf(beginIndex + count -1);//�����ˮ��,���ڼ���λ��
    	
    	String sCurrentIx =  String.valueOf(currentIndex);//��ǰ���������ڼ���λ��
    	
    	int zeroNumber = 0;//��Ҫ����0�ĸ���
    	
    	if ( seqLen >= sMaxIndex.length()) {
    		//*�ŵĳ��ȴ��ڻ���������ˮ�ŵĳ���,��*�ŵĳ���-��ǰ��ˮ�ų��ȣ��õ���Ҫ��0�ĸ���
    		zeroNumber = seqLen - sCurrentIx.length();
    	} else {
    		//*�ŵĳ���С�������ˮ�ŵĳ���,�������ˮ�ų���-��ǰ��ˮ�ų��ȣ��õ���Ҫ��0�ĸ���
    		zeroNumber = sMaxIndex.length() - sCurrentIx.length();	
    	}
    	
    	//����0,ʹ����һ��
    	for (int i = 0; i < zeroNumber; i++) {
    		seqNumber.append("0");	
    	}
    	
    	seqNumber.append(String.valueOf(currentIndex));
    	
    	currentIndex++;
    	
    	//ǰ׺+ϵ�к�+��׺���õ�����֧Ʊ���
		return prefix + seqNumber.toString() + suffix;
	}
    
    /**
     * ��ȡ��һ�����룬�����ã������ұ������ʹ��
     */
    public String nextNumber(int countNumber,int begin) {
    	//��ʼ����ǰ����
    	if (currentIndex <= 0) {
    		currentIndex = beginIndex;
    	}
    	
    	int maskBegin = codeRule.indexOf("*");//*����ʼ����
    	int maskEnd = codeRule.lastIndexOf("*");//*����ֹ����
    	
    	StringBuffer seqNumber = new StringBuffer("");
    	
    	int seqLen = 0;//*�ŵĳ���
    	
    	seqLen = maskEnd - maskBegin + 1;
    	
    	String prefix =""; //ǰ׺
    	prefix = codeRule.substring(0, maskBegin);
    	
    	String suffix =""; //��׺
    	suffix = codeRule.substring(maskEnd + 1);
    	
    	String sMaxIndex = String.valueOf(begin + countNumber -1);//�����ˮ��,���ڼ���λ��
    	
    	String sCurrentIx =  String.valueOf(currentIndex);//��ǰ���������ڼ���λ��
    	
    	int zeroNumber = 0;//��Ҫ����0�ĸ���
    	
    	if ( seqLen >= sMaxIndex.length()) {
    		//*�ŵĳ��ȴ��ڻ���������ˮ�ŵĳ���,��*�ŵĳ���-��ǰ��ˮ�ų��ȣ��õ���Ҫ��0�ĸ���
    		zeroNumber = seqLen - sCurrentIx.length();
    	} else {
    		//*�ŵĳ���С�������ˮ�ŵĳ���,�������ˮ�ų���-��ǰ��ˮ�ų��ȣ��õ���Ҫ��0�ĸ���
    		zeroNumber = sMaxIndex.length() - sCurrentIx.length();	
    	}
    	
    	//����0,ʹ����һ��
    	for (int i = 0; i < zeroNumber; i++) {
    		seqNumber.append("0");	
    	}
    	
    	seqNumber.append(String.valueOf(currentIndex));
    	
    	currentIndex++;
    	
    	//ǰ׺+ϵ�к�+��׺���õ�����֧Ʊ���
		return prefix + seqNumber.toString() + suffix;
	}
    
}
