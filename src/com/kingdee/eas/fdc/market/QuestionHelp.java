package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;

public class QuestionHelp {

	 /**
	 * ������<��������>��������pk ���������ͻ�ȡ�����ȫ����Ϣ
	 * @param  <��������>pk ����pk 
	 * 			questionStyle ��������
	 * @return  <����ֵ����> ����ȫ����Ϣ
	 *
	 * @author:baihui_li
	 * ����ʱ�䣺2009-3-12 <p>
	 * 
	 * @see  <��ص���>
	 */
	public static QuestionBaseInfo getQuestion(String pk,String questionStyle) throws Exception, BOSException{
		 QuestionBaseInfo questionInfo=null;
		 
//			�����
	        if(questionStyle.equals(QuestionStyle.ASKQUESTION_VALUE)){
	        	questionInfo =AskQuestionFactory.getRemoteInstance().getAskQuestionInfo(pk);
	        }
	        //�ж���   
	        else  if(questionStyle.equals(QuestionStyle.JUDGEQUESTION_VALUE)){
	        	questionInfo =JudgeQuestionFactory.getRemoteInstance().getJudgeQuestionInfo(pk);
	       }
	        
	        //����� 
	        else  if(questionStyle.equals(QuestionStyle.FILLBLANKQUESTION_VALUE)){
	        	questionInfo =FillBlankQuestionFactory.getRemoteInstance().getFillBlankQuestionInfo(pk);
	       } 
	        
	        //��ѡ��
	        else  if(questionStyle.equals(QuestionStyle.SINGLESELECTQUESTION_VALUE)){
	        	EntityViewInfo viewInfo = new EntityViewInfo();
	    		FilterInfo fi = new FilterInfo();
	    		fi.getFilterItems().add(new FilterItemInfo("questionStyle", QuestionStyle.SINGLESELECTQUESTION_VALUE, CompareType.EQUALS));
	    		viewInfo.setFilter(fi);
	    		
	        	questionInfo =SelectQuestionFactory.getRemoteInstance().getSelectQuestionCollection(viewInfo).get(0);
	        		
	         
	       } 
	        
	        // ��ѡ��
	        else  if(questionStyle.equals(QuestionStyle.MUTISELECTQUESTION_VALUE)){
	        	EntityViewInfo viewInfo = new EntityViewInfo();
	    		FilterInfo fi = new FilterInfo();
	    		fi.getFilterItems().add(new FilterItemInfo("questionStyle", QuestionStyle.MUTISELECTQUESTION_VALUE, CompareType.EQUALS));
	    		viewInfo.setFilter(fi);
	    		
	        	questionInfo =SelectQuestionFactory.getRemoteInstance().getSelectQuestionCollection(viewInfo).get(0);
	       } 
	        
		 
		 return questionInfo;
	 }

}
