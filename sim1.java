/*
	Programming Assignment: CPU Scheduling Simulator (Lab1)
	Vatsal Hirpara 201601239
*/

import java.io.*; 
import java.util.*;

class Process{
	int pid,arrival_time,burst_time;
	public Process(int p,int a,int b){
		pid=p;
		arrival_time=a;
		burst_time=b;
	}
}



public class sim1 implements Runnable{

	
	String[] args;
	public sim1(String[] arg) {
    	 args=arg;
   	}		
		
	public void run(){  
	
		String input_file = args[0];
		String scheduling_algo=args[1];

		if(scheduling_algo.equals("RR")){		
			int time_quantum = Integer.parseInt(args[2]);
        } 
		
		Scanner in=null;
		try{
			in = new Scanner(new File(input_file));		
		}
		catch(FileNotFoundException ex) {
			System.out.println(ex);
			System.exit(0);
		}
		
		ArrayList<Process> process_list=new ArrayList<Process>(); 
		ArrayList<Process> process_list2=new ArrayList<Process>();
		
		Process temp;
		while(in.hasNextInt()){
			temp=new Process(in.nextInt(),in.nextInt(),in.nextInt());
			process_list.add(temp);
			process_list2.add(temp);
		}
		

		
		Queue<Process> ready_queue = new LinkedList<>();
		Process p,first;
		int sys_time=0;
		if(scheduling_algo.equals("FCFS")){
			
			while(process_list.isEmpty()==false || ready_queue.size()>0){	
		
				
				if(process_list.size()>0){
					while(sys_time==process_list.get(0).arrival_time){
						ready_queue.add(process_list.get(0));	
						process_list.remove(0);
						if(ready_queue.size()==0 || process_list.size()==0 ) break;	
					}	
				}
				
				if(ready_queue.size()==0){
					System.out.printf("<system time %d> idle\n",sys_time);
				}
				else if(ready_queue.peek().burst_time>0 ){
					System.out.printf("<system time %d> process %d is running\n",sys_time,ready_queue.peek().pid);
					ready_queue.peek().burst_time--;
				}
				else if (ready_queue.peek().burst_time==0 )  {
					
					System.out.printf("<system time %d> process %d is finished.......\n",sys_time,ready_queue.peek().pid);
					if(process_list.isEmpty()==true){	
						if(ready_queue.size()==1) System.out.printf("<system time %d> All processes finish ....................\n",sys_time);
					}
					ready_queue.remove(); 
					if(ready_queue.size()==0){continue;}
					System.out.printf("<system time %d> process %d is running\n",sys_time,ready_queue.peek().pid);
					ready_queue.peek().burst_time--;
				}
				 
				
			sys_time++;
			}
		
		}
}
		

	
		
	
    public static void main(String[] args) throws FileNotFoundException { 		
		
		sim1 m1=new sim1(args);  
		Thread t1 =new Thread(m1);  
		t1.start();		
		try{  
  			t1.join();  
 		}
 		catch(Exception e){
			System.out.println(e);
		}  
		
	}    
} 

/*		int pid[]=new int[21];           old input
		int arrival_time[]=new int[21];
		int burst_time[]=new int[21];
		
		int i=1;
		while(in.hasNextInt()){
			pid[i]=in.nextInt();
			arrival_time[i]=in.nextInt();
			burst_time[i]=in.nextInt();
			i++;
		}
*/



/*		System.out.println("start");
		try 
		{
    		Thread.sleep(3000);
		}	 
		catch(InterruptedException e)
		{}
		System.out.println("slept for 3");
*/


/*		
		// Works if you don't consider empty cpu cycle or burst
		if(scheduling_algo.equals("FCFS")){  
			
			for(Process p:process_list){
				while(p.burst_time>0){
					System.out.printf("<system time %d> process %d is running\n",Sys_time++,p.pid);
					p.burst_time--;
					
					try 
					{
    					Thread.sleep(1); // CPU burst time assumed 1ms 
					}	 
					catch(InterruptedException e)
					{}
					
				}
				System.out.printf("<system time %d> process %d is finished.......\n",Sys_time,p.pid);
			}
			System.out.printf("<system time %d> All processes finish ....................",Sys_time);
					
		}
*/	


/**------------------------------------------------------------------------------------------------------*/

/*
			while(process_list.isEmpty()==false)
			{
				
			//	if(sys_time==5) {System.out.println(process_list.get(0).arrival_time);System.exit(0);}
				
				while( sys_time == ((process_list.get(0)).arrival_time) ){
				
					ready_queue.add(process_list.get(0));
					process_list.remove(0);
				}
			
				
				if(ready_queue.size()>0){
					while(ready_queue.peek().burst_time>0){
						System.out.printf("<system time %d> process %d is running queue %d \n",sys_time,ready_queue.peek().pid,ready_queue.peek().pid);
						ready_queue.peek().burst_time--;
						try {
    						Thread.sleep(10);	
						}	 
						catch(InterruptedException e)
						{
						}
						
				//if(sys_time==6) {System.out.println(process_list.get(0).arrival_time);System.exit(0);}
						
						if(process_list.isEmpty()==false){
							while( sys_time == process_list.get(0).arrival_time){
								ready_queue.add(process_list.get(0));
								process_list.remove(0);
								if(process_list.isEmpty()==true)break;
							}					
						}
						sys_time++;
					}
					ready_queue.remove();

					
				}

				else{
					System.out.printf("<system time %d> Ready queue is empty at the moment\n",sys_time++);
			
					try {
    				Thread.sleep(10);
					}	 
					catch(InterruptedException e)
					{	
					}	
				}		
			
			}


*/
/*-------------------------------------------------------------------------------------------------------------------*/
