package com.kingdee.eas.fdc.finance.app;

import java.math.BigDecimal;

import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.eas.fdc.basedata.CostSplitTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryInfo;

public class PayNoCostSplitAppHelper {
//	protected static final FDCCostSplit fdcCostSplit=new FDCCostSplit(null);
	static void adjustPayAmount(IObjectCollection allEntrys,PaymentNoCostSplitEntryInfo curEntry){
    	PaymentNoCostSplitEntryInfo entry=curEntry;    	
		int index=allEntrys.indexOf(curEntry);
		if(index==-1){
			return;
		}
    	
    	boolean isProj=false;
    	if(!entry.isIsAddlAccount()){
    		if(entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
    			if(!entry.isIsLeaf()){
    				isProj=true;
    			}
    		}else{
				isProj=true;
    		}
		}    	
    	
    	if(isProj){
    		adjustPayAmountProject(allEntrys, index);
//    		System.out.println("test");
    	}else{
    		adjustPayAmountAccount(allEntrys, index);    		
    	}
    }
    	
	/**
	 * �������������
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-12-26 <p>
	 */
    private static void adjustPayAmountProject(IObjectCollection allEntrys, int index){
		PaymentNoCostSplitEntryInfo curEntry =(PaymentNoCostSplitEntryInfo)allEntrys.getObject(index);		
		if(curEntry.isIsLeaf()){
			return;
		}		
		//����
		BigDecimal curAmount=curEntry.getPayedAmt();		
		//���	
		BigDecimal amount=FDCHelper.ZERO;//null;
		BigDecimal amountTotal=FDCHelper.ZERO;
		amountTotal = amountTotal.setScale(10);						
		int level=curEntry.getLevel();
		//int projLevel=curEntry.getCostAccount().getCurProject().getLevel();				
		//ֱ���¼�����
		PaymentNoCostSplitEntryInfo entry=null;
		int idx=0;
		BigDecimal amountMax=FDCHelper.ZERO;		
		for(int i=index+1; i<allEntrys.size(); i++){		
			entry=(PaymentNoCostSplitEntryInfo)allEntrys.getObject(i);			
			if(entry.getLevel()==(level+1)){
//				if(entry.getCostAccount().getLongNumber().replace('!','.').equals(
//						curEntry.getCostAccount().getLongNumber().replace('!','.'))){
					amount=entry.getPayedAmt();
					if(amount==null){
						amount=FDCHelper.ZERO;
					}					
					amountTotal=amountTotal.add(amount);
					//������Ϊ���������
					//if(amount.compareTo(FDCHelper.ZERO)!=0){
					if(amount.compareTo(amountMax)>=0){
						amountMax=amount;
						idx=i;						
					}
//				}
			}else if(entry.getLevel()<=level){
				break;
			}
		}    	
		if(idx>0 && curAmount!=null&&curAmount.compareTo(amountTotal)!=0){	
			entry=(PaymentNoCostSplitEntryInfo)allEntrys.getObject(idx);
			amount=entry.getPayedAmt();
			if(amount==null){
				amount=FDCHelper.ZERO;
			}
			amount=amount.add(curAmount.subtract(amountTotal));			
			entry.setPayedAmt(amount);
		}
		for(int i=index+1; i<allEntrys.size(); i++){		
			entry=(PaymentNoCostSplitEntryInfo)allEntrys.getObject(i);
			
			if(entry.getLevel()==(level+1)){
				if(!entry.isIsLeaf()){
					adjustPayAmountProject(allEntrys,i);
				}
//				if(entry.getCostAccount().getLongNumber().replace('!','.').equals(
//						curEntry.getCostAccount().getLongNumber().replace('!','.'))){					
//					if(!entry.isIsLeaf()){
//						adjustPayAmountAccount(allEntrys,i);
//					}					
//					if(!entry.getCostAccount().getCurProject().isIsLeaf()){
//						adjustPayAmountProject(allEntrys,i);						
//					}					
//				}
			}else if(entry.getLevel()<=level){
				break;
			}
		}    	
    }
    
	
	
	/**
	 * �������������
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-12-26 <p>
	 */
    private static void adjustPayAmountAccount(IObjectCollection allEntrys, int index){
		PaymentNoCostSplitEntryInfo curEntry =(PaymentNoCostSplitEntryInfo)allEntrys.getObject(index);	
		if(curEntry.isIsLeaf()){
			return;
		}
		
		//����
		BigDecimal curAmount=curEntry.getPayedAmt();
		
		//���	
		BigDecimal amount=FDCHelper.ZERO;//null;
		BigDecimal amountTotal=FDCHelper.ZERO;
		amountTotal = amountTotal.setScale(10);			
		
			
		int level=curEntry.getLevel();
		//int projLevel=curEntry.getCostAccount().getCurProject().getLevel();		

		
		//ֱ���¼�����
		PaymentNoCostSplitEntryInfo entry=null;

		int idx=0;
		
		for(int i=index+1; i<allEntrys.size(); i++){			
			entry=(PaymentNoCostSplitEntryInfo)allEntrys.getObject(i);	
						
			if(entry.getLevel()==(level+1)){
//				if(entry.getCostAccount().getCurProject().getId().equals(curEntry.getCostAccount().getCurProject().getId())){				
					amount=entry.getPayedAmt();
					if(amount==null){
						amount=FDCHelper.ZERO;
					}
					
					amountTotal=amountTotal.add(amount);
					
					if(amount.compareTo(FDCHelper.ZERO)!=0){
						idx=i;						
					}
//				}
			}else if(entry.getLevel()<=level){
				break;
			}
		}    	
		

		if(idx>0 && curAmount.compareTo(amountTotal)!=0){			
			entry=(PaymentNoCostSplitEntryInfo)allEntrys.getObject(idx);	

			amount=entry.getPayedAmt();
			if(amount==null){
				amount=FDCHelper.ZERO;
			}
			amount=amount.add(curAmount.subtract(amountTotal));			
			entry.setPayedAmt(amount);
		}
		

		for(int i=index+1; i<allEntrys.size(); i++){			
			entry=(PaymentNoCostSplitEntryInfo)allEntrys.getObject(idx);	
			
			
			if(entry.getLevel()==(level+1)){
//				if(entry.getCostAccount().getCurProject().getId().equals(curEntry.getCostAccount().getCurProject().getId())){				
					if(!entry.isIsLeaf()){
						adjustPayAmountAccount(allEntrys,i);
					}
//				}
			}else if(entry.getLevel()<=level){
				break;
			}
		}    	
    }
    
    /**
	 * ���������ܷ�̯���������ɷ���ϸ������Ŀ�и��ӿ�Ŀ�ĳɱ�����ֵΪ�¼�������Ŀ��ͬ��Ŀ�ĳɱ�֮�ͣ����ڻ�ƿ�Ŀ������
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-12-29 <p>
	 */
    public static void totAmountPayAddlAcct(IObjectCollection allEntrys,int curIndex){    	
	}
    
    public static void adjustQuaAmount(IObjectCollection allEntrys,PaymentNoCostSplitEntryInfo curEntry){
    	PaymentNoCostSplitEntryInfo entry=curEntry;    	
		int index=allEntrys.indexOf(curEntry);
		if(index==-1){
			return;
		}
    	
    	boolean isProj=false;
    	if(!entry.isIsAddlAccount()){
    		if(entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
    			if(!entry.isIsLeaf()){
    				isProj=true;
    			}
    		}else{
				isProj=true;
    		}
		}    	
    	
    	if(isProj){
    		adjustQuaAmountProject(allEntrys, index);
//    		System.out.println("test");
    	}else{
    		adjustQuaAmountAccount(allEntrys, index);    		
    	}
    }
    	
	/**
	 * �������������
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-12-26 <p>
	 */
    private static void adjustQuaAmountProject(IObjectCollection allEntrys, int index){
		PaymentNoCostSplitEntryInfo curEntry =(PaymentNoCostSplitEntryInfo)allEntrys.getObject(index);		
		if(curEntry.isIsLeaf()){
			return;
		}		
		//����
		BigDecimal curAmount=curEntry.getQualityAmount();		
		//���	
		BigDecimal amount=FDCHelper.ZERO;//null;
		BigDecimal amountTotal=FDCHelper.ZERO;
		amountTotal = amountTotal.setScale(10);						
		int level=curEntry.getLevel();
		//int projLevel=curEntry.getCostAccount().getCurProject().getLevel();				
		//ֱ���¼�����
		PaymentNoCostSplitEntryInfo entry=null;
		int idx=0;
		BigDecimal amountMax=FDCHelper.ZERO;		
		for(int i=index+1; i<allEntrys.size(); i++){		
			entry=(PaymentNoCostSplitEntryInfo)allEntrys.getObject(i);			
			if(entry.getLevel()==(level+1)){
//				if(entry.getCostAccount().getLongNumber().replace('!','.').equals(
//						curEntry.getCostAccount().getLongNumber().replace('!','.'))){
					amount=entry.getQualityAmount();
					if(amount==null){
						amount=FDCHelper.ZERO;
					}					
					amountTotal=amountTotal.add(amount);
					//������Ϊ���������
					//if(amount.compareTo(FDCHelper.ZERO)!=0){
					if(amount.compareTo(amountMax)>=0){
						amountMax=amount;
						idx=i;						
					}
//				}else{
//					
//				}
			}else if(entry.getLevel()<=level){
				break;
			}
		}    	
		if(idx>0 && curAmount!=null&&curAmount.compareTo(amountTotal)!=0){	
			entry=(PaymentNoCostSplitEntryInfo)allEntrys.getObject(idx);
			amount=entry.getQualityAmount();
			if(amount==null){
				amount=FDCHelper.ZERO;
			}
			amount=amount.add(curAmount.subtract(amountTotal));			
			entry.setQualityAmount(amount);
		}
		for(int i=index+1; i<allEntrys.size(); i++){		
			entry=(PaymentNoCostSplitEntryInfo)allEntrys.getObject(i);
			
			if(entry.getLevel()==(level+1)){
//				if(entry.getCostAccount().getLongNumber().replace('!','.').equals(
//						curEntry.getCostAccount().getLongNumber().replace('!','.'))){
//					
					if(!entry.isIsLeaf()){
						adjustQuaAmountAccount(allEntrys,i);
					}
					
//					if(!entry.getCostAccount().getCurProject().isIsLeaf()){
//						adjustQuaAmountProject(allEntrys,i);
//						
//					}
					
//				}else{
//					
//				}
			}else if(entry.getLevel()<=level){
				break;
			}
		}    	
    }
    
	
	
	/**
	 * �������������
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-12-26 <p>
	 */
    private static void adjustQuaAmountAccount(IObjectCollection allEntrys, int index){
		PaymentNoCostSplitEntryInfo curEntry =(PaymentNoCostSplitEntryInfo)allEntrys.getObject(index);	
		if(curEntry.isIsLeaf()){
			return;
		}
		
		//����
		BigDecimal curAmount=curEntry.getQualityAmount();
		
		//���	
		BigDecimal amount=FDCHelper.ZERO;//null;
		BigDecimal amountTotal=FDCHelper.ZERO;
		amountTotal = amountTotal.setScale(10);			
		
			
		int level=curEntry.getLevel();
		//int projLevel=curEntry.getCostAccount().getCurProject().getLevel();		

		
		//ֱ���¼�����
		PaymentNoCostSplitEntryInfo entry=null;

		int idx=0;
		
		for(int i=index+1; i<allEntrys.size(); i++){			
			entry=(PaymentNoCostSplitEntryInfo)allEntrys.getObject(i);	
						
			if(entry.getLevel()==(level+1)){
//				if(entry.getCostAccount().getCurProject().getId().equals(curEntry.getCostAccount().getCurProject().getId())){				
					amount=entry.getQualityAmount();
					if(amount==null){
						amount=FDCHelper.ZERO;
					}
					
					amountTotal=amountTotal.add(amount);
					
					if(amount.compareTo(FDCHelper.ZERO)!=0){
						idx=i;						
					}
//				}
			}else if(entry.getLevel()<=level){
				break;
			}
		}    	
		

		if(idx>0 && curAmount.compareTo(amountTotal)!=0){			
			entry=(PaymentNoCostSplitEntryInfo)allEntrys.getObject(idx);	

			amount=entry.getQualityAmount();
			if(amount==null){
				amount=FDCHelper.ZERO;
			}
			amount=amount.add(curAmount.subtract(amountTotal));			
			entry.setQualityAmount(amount);
		}
		

		for(int i=index+1; i<allEntrys.size(); i++){			
			entry=(PaymentNoCostSplitEntryInfo)allEntrys.getObject(idx);	
			
			
			if(entry.getLevel()==(level+1)){
//				if(entry.getCostAccount().getCurProject().getId().equals(curEntry.getCostAccount().getCurProject().getId())){				
					if(!entry.isIsLeaf()){
						adjustQuaAmountAccount(allEntrys,i);
					}
//				}
			}else if(entry.getLevel()<=level){
				break;
			}
		}    	
    }
    
    /**
	 * ���������ܷ�̯���������ɷ���ϸ������Ŀ�и��ӿ�Ŀ�ĳɱ�����ֵΪ�¼�������Ŀ��ͬ��Ŀ�ĳɱ�֮�ͣ�
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-12-29 <p>
	 */
    public static void totAmountQuaAddlAcct(IObjectCollection allEntrys,int curIndex){  		
	}
}
