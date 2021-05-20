package com.kingdee.eas.fdc.finance.app;

import java.math.BigDecimal;

import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.eas.fdc.basedata.CostSplitTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCCostSplit;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo;

/**
 * @deprecated ���з������Ƶ�PaymentSplitHelper��
 *  by sxhong 2009-07-20 12:17:48
 */
public class PayCostSplitAppHelper {
//	protected static final FDCCostSplit fdcCostSplit=new FDCCostSplit(null);
	static void adjustPayAmount(IObjectCollection allEntrys,PaymentSplitEntryInfo curEntry){
    	PaymentSplitEntryInfo entry=curEntry;    	
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
		PaymentSplitEntryInfo curEntry =(PaymentSplitEntryInfo)allEntrys.getObject(index);		
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
		PaymentSplitEntryInfo entry=null;
		int idx=0;
		BigDecimal amountMax=FDCHelper.ZERO;		
		for(int i=index+1; i<allEntrys.size(); i++){		
			entry=(PaymentSplitEntryInfo)allEntrys.getObject(i);			
			if(entry.getLevel()==(level+1)){
				if(entry.getCostAccount().getLongNumber().replace('!','.').equals(
						curEntry.getCostAccount().getLongNumber().replace('!','.'))){
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
				}else{
					
				}
			}else if(entry.getLevel()<=level){
				break;
			}
		}    	
		if(idx>0 && curAmount!=null&&curAmount.compareTo(amountTotal)!=0){	
			entry=(PaymentSplitEntryInfo)allEntrys.getObject(idx);
			amount=entry.getPayedAmt();
			if(amount==null){
				amount=FDCHelper.ZERO;
			}
			amount=amount.add(curAmount.subtract(amountTotal));			
			entry.setPayedAmt(amount);
		}
		for(int i=index+1; i<allEntrys.size(); i++){		
			entry=(PaymentSplitEntryInfo)allEntrys.getObject(i);
			
			if(entry.getLevel()==(level+1)){
				if(entry.getCostAccount().getLongNumber().replace('!','.').equals(
						curEntry.getCostAccount().getLongNumber().replace('!','.'))){
					
					if(!entry.isIsLeaf()){
						adjustPayAmountAccount(allEntrys,i);
					}
					
					if(!entry.getCostAccount().getCurProject().isIsLeaf()){
						adjustPayAmountProject(allEntrys,i);
						
					}
					
				}else{
					
				}
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
		PaymentSplitEntryInfo curEntry =(PaymentSplitEntryInfo)allEntrys.getObject(index);	
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
		PaymentSplitEntryInfo entry=null;

		int idx=0;
		
		for(int i=index+1; i<allEntrys.size(); i++){			
			entry=(PaymentSplitEntryInfo)allEntrys.getObject(i);	
						
			if(entry.getLevel()==(level+1)){
				if(entry.getCostAccount().getCurProject().getId().equals(curEntry.getCostAccount().getCurProject().getId())){				
					amount=entry.getPayedAmt();
					if(amount==null){
						amount=FDCHelper.ZERO;
					}
					
					amountTotal=amountTotal.add(amount);
					
					if(amount.compareTo(FDCHelper.ZERO)!=0){
						idx=i;						
					}
				}
			}else if(entry.getLevel()<=level){
				break;
			}
		}    	
		

		if(idx>0 && curAmount.compareTo(amountTotal)!=0){			
			entry=(PaymentSplitEntryInfo)allEntrys.getObject(idx);	

			amount=entry.getPayedAmt();
			if(amount==null){
				amount=FDCHelper.ZERO;
			}
			amount=amount.add(curAmount.subtract(amountTotal));			
			entry.setPayedAmt(amount);
		}
		

		for(int i=index+1; i<allEntrys.size(); i++){			
			entry=(PaymentSplitEntryInfo)allEntrys.getObject(idx);	
			
			
			if(entry.getLevel()==(level+1)){
				if(entry.getCostAccount().getCurProject().getId().equals(curEntry.getCostAccount().getCurProject().getId())){				
					if(!entry.isIsLeaf()){
						adjustPayAmountAccount(allEntrys,i);
					}
				}
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
    public static void totAmountPayAddlAcct(Context ctx,IObjectCollection allEntrys,int curIndex){
    	FDCCostSplit fdcCostSplit=new FDCCostSplit(ctx);
     	if(curIndex>=allEntrys.size()){
    		return;
    	}
		
		PaymentSplitEntryInfo topEntry =(PaymentSplitEntryInfo)allEntrys.getObject(curIndex);
    	if(topEntry==null||topEntry.getCostAccount()==null||topEntry.getCostAccount().getCurProject()==null||topEntry.getCostAccount().getCurProject().isIsLeaf()){		
			//��ϸ���̵Ŀ�Ŀ		
			return;			
		}

		//��ǰ������Ŀ���¼��ɱ���Ŀ
    	int acctLevel=topEntry.getCostAccount().getLevel();
    	
		for(int i=curIndex+1; i<allEntrys.size(); i++){				
			PaymentSplitEntryInfo entry=(PaymentSplitEntryInfo)allEntrys.getObject(i);

			if(entry.getCostAccount().getCurProject().getId().equals(topEntry.getCostAccount().getCurProject().getId())){
				if(entry.getCostAccount().getLevel()>acctLevel){
					if(entry.getCostAccount().getLevel()==acctLevel+1){
						totAmountPayAddlAcct(ctx,allEntrys,i);										
					}					
				}else{
					break;
				}
				
			}else{
				break;
			}
		}
		
		//�¼�������Ŀ����ͬ�ɱ���Ŀ
    	int topLevel=topEntry.getLevel();
    	
		BigDecimal amount=null;
		BigDecimal amountTotal=FDCHelper.ZERO;
		
		for(int i=curIndex+1; i<allEntrys.size(); i++){				
			PaymentSplitEntryInfo entry=(PaymentSplitEntryInfo)allEntrys.getObject(i);
			
			if(entry.getLevel()>topLevel){
				if(entry.getLevel()==topLevel+1){
					if(fdcCostSplit.isChildProjSameAcct(entry,topEntry)){
						totAmountPayAddlAcct(ctx,allEntrys,i);				
						
						amount=entry.getPayedAmt();
						if(amount==null){
							amount=FDCHelper.ZERO;
						}
						amountTotal=amountTotal.add(amount);					
					}					
				}
				
			}else if(entry.getLevel()<topLevel){
				//break;
			}
		}
		
		//topEntry.isIsAddlAccount() �������� by sxhong ��δ���Ӧ�����������
		if(topLevel!=0&&topEntry.isIsAddlAccount()){
			topEntry.setPayedAmt(amountTotal);
		}
   		
	}
    
    
    
    public static void adjustQuaAmount(IObjectCollection allEntrys,PaymentSplitEntryInfo curEntry){
    	PaymentSplitEntryInfo entry=curEntry;    	
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
		PaymentSplitEntryInfo curEntry =(PaymentSplitEntryInfo)allEntrys.getObject(index);		
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
		PaymentSplitEntryInfo entry=null;
		int idx=0;
		BigDecimal amountMax=FDCHelper.ZERO;		
		for(int i=index+1; i<allEntrys.size(); i++){		
			entry=(PaymentSplitEntryInfo)allEntrys.getObject(i);			
			if(entry.getLevel()==(level+1)){
				if(entry.getCostAccount().getLongNumber().replace('!','.').equals(
						curEntry.getCostAccount().getLongNumber().replace('!','.'))){
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
				}else{
					
				}
			}else if(entry.getLevel()<=level){
				break;
			}
		}    	
		if(idx>0 && curAmount!=null&&curAmount.compareTo(amountTotal)!=0){	
			entry=(PaymentSplitEntryInfo)allEntrys.getObject(idx);
			amount=entry.getQualityAmount();
			if(amount==null){
				amount=FDCHelper.ZERO;
			}
			amount=amount.add(curAmount.subtract(amountTotal));			
			entry.setQualityAmount(amount);
		}
		for(int i=index+1; i<allEntrys.size(); i++){		
			entry=(PaymentSplitEntryInfo)allEntrys.getObject(i);
			
			if(entry.getLevel()==(level+1)){
				if(entry.getCostAccount().getLongNumber().replace('!','.').equals(
						curEntry.getCostAccount().getLongNumber().replace('!','.'))){
					
					if(!entry.isIsLeaf()){
						adjustQuaAmountAccount(allEntrys,i);
					}
					
					if(!entry.getCostAccount().getCurProject().isIsLeaf()){
						adjustQuaAmountProject(allEntrys,i);
						
					}
					
				}else{
					
				}
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
		PaymentSplitEntryInfo curEntry =(PaymentSplitEntryInfo)allEntrys.getObject(index);	
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
		PaymentSplitEntryInfo entry=null;

		int idx=0;
		
		for(int i=index+1; i<allEntrys.size(); i++){			
			entry=(PaymentSplitEntryInfo)allEntrys.getObject(i);	
						
			if(entry.getLevel()==(level+1)){
				if(entry.getCostAccount().getCurProject().getId().equals(curEntry.getCostAccount().getCurProject().getId())){				
					amount=entry.getQualityAmount();
					if(amount==null){
						amount=FDCHelper.ZERO;
					}
					
					amountTotal=amountTotal.add(amount);
					
					if(amount.compareTo(FDCHelper.ZERO)!=0){
						idx=i;						
					}
				}
			}else if(entry.getLevel()<=level){
				break;
			}
		}    	
		

		if(idx>0 && curAmount.compareTo(amountTotal)!=0){			
			entry=(PaymentSplitEntryInfo)allEntrys.getObject(idx);	

			amount=entry.getQualityAmount();
			if(amount==null){
				amount=FDCHelper.ZERO;
			}
			amount=amount.add(curAmount.subtract(amountTotal));			
			entry.setQualityAmount(amount);
		}
		

		for(int i=index+1; i<allEntrys.size(); i++){			
			entry=(PaymentSplitEntryInfo)allEntrys.getObject(idx);	
			
			
			if(entry.getLevel()==(level+1)){
				if(entry.getCostAccount().getCurProject().getId().equals(curEntry.getCostAccount().getCurProject().getId())){				
					if(!entry.isIsLeaf()){
						adjustQuaAmountAccount(allEntrys,i);
					}
				}
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
    public static void totAmountQuaAddlAcct(Context ctx,IObjectCollection allEntrys,int curIndex){
    	FDCCostSplit fdcCostSplit=new FDCCostSplit(ctx);
    	if(curIndex>=allEntrys.size()){
    		return;
    	}
		
		PaymentSplitEntryInfo topEntry =(PaymentSplitEntryInfo)allEntrys.getObject(curIndex);
    	if(topEntry==null||topEntry.getCostAccount()==null||topEntry.getCostAccount().getCurProject()==null||topEntry.getCostAccount().getCurProject().isIsLeaf()){		
			//��ϸ���̵Ŀ�Ŀ		
			return;			
		}

		//��ǰ������Ŀ���¼��ɱ���Ŀ
    	int acctLevel=topEntry.getCostAccount().getLevel();
    	
		for(int i=curIndex+1; i<allEntrys.size(); i++){				
			PaymentSplitEntryInfo entry=(PaymentSplitEntryInfo)allEntrys.getObject(i);

			if(entry.getCostAccount().getCurProject().getId().equals(topEntry.getCostAccount().getCurProject().getId())){
				if(entry.getCostAccount().getLevel()>acctLevel){
					if(entry.getCostAccount().getLevel()==acctLevel+1){
						totAmountQuaAddlAcct(ctx,allEntrys,i);										
					}					
				}else{
					break;
				}
				
			}else{
				break;
			}
		}
		

		//�¼�������Ŀ����ͬ�ɱ���Ŀ
    	int topLevel=topEntry.getLevel();

		BigDecimal amount=null;
		BigDecimal amountTotal=FDCHelper.ZERO;
		
		for(int i=curIndex+1; i<allEntrys.size(); i++){				
			PaymentSplitEntryInfo entry=(PaymentSplitEntryInfo)allEntrys.getObject(i);
			
			if(entry.getLevel()>topLevel){
				if(entry.getLevel()==topLevel+1){
					if(fdcCostSplit.isChildProjSameAcct(entry,topEntry)){
						totAmountQuaAddlAcct(ctx,allEntrys,i);				
						
						amount=entry.getQualityAmount();
						if(amount==null){
							amount=FDCHelper.ZERO;
						}
						amountTotal=amountTotal.add(amount);					
					}					
				}
				
			}else if(entry.getLevel()<topLevel){
				//break;
			}
		}
		
		//topEntry.isIsAddlAccount() �������� by sxhong ��δ���Ӧ�����������
		if(topLevel!=0&&topEntry.isIsAddlAccount()){
			topEntry.setQualityAmount(amountTotal);
		}
   		
	}
}
