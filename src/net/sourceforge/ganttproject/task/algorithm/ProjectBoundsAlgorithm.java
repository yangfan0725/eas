package net.sourceforge.ganttproject.task.algorithm;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import net.sourceforge.ganttproject.task.Task;

import org.apache.log4j.Logger;

public class ProjectBoundsAlgorithm {
	
	private static Logger logger = Logger
			.getLogger("net.sourceforge.ganttproject.task.algorithm.ProjectBoundsAlgorithm");
	
    public static class Result {
        public final Date lowerBound;

        public final Date upperBound;

        private Result(Date lowerBound, Date upperBound) {
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
        }
    }

	/**
	 * ������
	 * 
	 * @param tasks
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-6-10
	 */
	public Result getBounds(Collection/* <Task> */tasks) {
		// ���������Ż�: ����ƿ�� 6.4% - 5,609 ms - 518 inv by skyiter_wang 2014-06-11

		// ���������Ż�: �˴��ǿ����Ż��� by skyiter_wang 2014-06-11
		// 1��KDTask.<init> --> TaskImpl$MutatorImpl.commit
		// KDTask.setEndAndCalDur --> TaskImpl$MutatorImpl.commit
		// AlgorithmImpl.recalculateSupertaskSchedule --> TaskImpl$MutatorImpl.commit
		// 2��TaskImpl$MutatorImpl.commit --> TaskManagerImpl.fireTaskScheduleChanged -->
		// 3��TaskManagerImpl.fireTaskChange -- >TaskManagerImpl.getProjectStart -->
		// 4��ProjectBoundsAlgorithm.getBounds

		// 1.1������TaskManagerImpl.fireTaskChange(3��)
		// 1.2��TaskManagerImpl.fireTaskChange������TaskManagerImpl.getProjectStart��getProjectEnd
		// 1.3��TaskManagerImpl.getProjectStart��getProjectEnd������ProjectBoundsAlgorithm.getBounds
		// 1.4���ܹ���3*2������ProjectBoundsAlgorithm.getBounds(6��)
		// 1.5���������ٻ����� 6.4%* 6 = 38%����

		// �Ż�������ʹ�û���TreeSet����
		// �����TaskManagerImpl.TaskMap.addTask��getMinStartDate;TaskManagerImpl.getProjectStart

		Result result = null;
		Date lowerBound = null;
		Date upperBound = null;
		for (Iterator it = tasks.iterator(); it.hasNext();) {
			Task next = (Task) it.next();

			// ���������Ż��� ����ƿ�� 2.4% - 2,154 ms - 134,421 inv by skyiter_wang 2014-06-11
			Date start = next.getStart().getTime();
			// ���������Ż��� ����ƿ�� 3.1% - 2,708 ms - 134,421 inv by skyiter_wang 2014-06-11
			Date end = next.getEnd().getTime();

			if (lowerBound == null || lowerBound.after(start)) {
				lowerBound = start;
			}
			if (upperBound == null || upperBound.before(end)) {
				upperBound = end;
			}
		}
		result = new Result(lowerBound, upperBound);

		if (true) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String lowerBoundStr = df.format(lowerBound);
			String upperBoundStr = df.format(upperBound);
			logger.info("======================lowerBound: " + lowerBoundStr + ", upperBound: " + upperBoundStr);
		}

		return result;
	}
}
