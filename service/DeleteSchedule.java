package xupt.se.ttms.service;

import xupt.se.ttms.model.Schedule;
import xupt.se.ttms.view.Plan.ProcessSchedUI;

import java.util.List;

//删除检测演出计划已经过期就删除
public class DeleteSchedule{

    public void setTask() {

        final long timeInterval = 5000;
        ProcessSchedUI psu = new ProcessSchedUI() ;

        Runnable runnable = new Runnable() {
            public void run() {
                while (true) {
                    String cur = psu.getCurTime() ;
                    try {
                        Thread.sleep(timeInterval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    List<Schedule>ls = getScheduleList() ;
                    if(ls.size() == 0) {
                        continue ;
                    }
                    else {
                        //遍历演出计划表将过期的已经完成的演出计划全部删除
                        for(Schedule s : ls) {
                            if(psu.compareDate(cur, s.getSched_time_end()) > 0) {
                                psu.deletePlan(s) ;
                            }
                        }
                    }
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    //获取演出计划的列表
    public List<Schedule> getScheduleList() {
        ScheduleSrv ssrv = new ScheduleSrv() ;
        List<Schedule> ls =  ssrv.select("") ;
        return ls ;
    }
}