import java.io.*; 
import java.util.*;
/*
	Vatsal Hirpara 201601239
*/
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
		
		Process temp;
		while(in.hasNextInt()){
			temp=new Process(in.nextInt(),in.nextInt(),in.nextInt());
			process_list.add(temp);
		}
		

		
		Queue<Process> ready_queue = new LinkedList<>();
		Process p;
		int sys_time=0;
		if(scheduling_algo.equals("FCFS")){
			
			while(process_list.isEmpty()==false)
			{
				Process first=process_list.get(0);
				if( sys_time == ((first).arrival_time) ){
					ready_queue.add(first);
					process_list.remove(0);
				}
			
			
				if(ready_queue.size()>0){
					Process current=ready_queue.peek();
					while(current.burst_time>0){
						System.out.printf("<system time %d> process %d is running\n",sys_time++,current.pid);
						current.burst_time--;
						try {
    						Thread.sleep(500);
						}	 
						catch(InterruptedException e)
						{
						}						
					}
					ready_queue.remove();
				}

				else{
					System.out.printf("<system time %d> Ready queue is empty at the moment\n",sys_time++);
			
					try {
    				Thread.sleep(500);
					}	 
					catch(InterruptedException e)
					{	
					}	
				}		
			
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



