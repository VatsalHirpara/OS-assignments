/*
	Programming Assignment: CPU Scheduling Simulator (Lab1)
	Vatsal Hirpara 201601239
*/

import java.io.*; 
import java.util.*;

class Process{
	int pid,arrival_time,burst_time;
	int time_quantum; //time_quantum
	public Process(int pid,int arrival_time,int burst_time,int time_quantum){
		this.pid=pid;
		this.arrival_time=arrival_time;
		this.burst_time=burst_time;
		this.time_quantum=time_quantum;
	}
}



public class simulator implements Runnable{

	
	String[] args;
	public simulator(String[] arg) {
    	 args=arg;
   	}		
		
	public void run(){  
	
		String input_file = args[0];
		String scheduling_algo=args[1];
		int time_quantum=0;
		if(scheduling_algo.equals("RR") && args.length==2){
			System.out.println("Please enter time quantum");
			System.exit(0);
		}
		if(scheduling_algo.equals("RR") && args.length>=3){		
			time_quantum = Integer.parseInt(args[2]);
			//Process.tq=time_quantum;
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
		
		 
		//ArrayList<Integer> turnaround_time =new ArrayList<Integer>();
		
		Process temp;
		while(in.hasNextInt()){
			temp=new Process(in.nextInt(),in.nextInt(),in.nextInt(),time_quantum);
			process_list.add(temp);
			process_list2.add(temp);
		}

		
		simulator.sort_FCFS(process_list);
		
		for(int i=0;i<process_list.size();i++){
			System.out.print(process_list.get(i).pid+" ");
		}
			
		
		
		int[] end_time = new int[process_list.size()+1]; 
		int[] start_time = new int[process_list.size()+1];
		
		
		Process p,first;
		int sys_time=0;
		boolean start_flag=true;
		boolean end_flag=true;
		
		System.out.println("Schdeuling algorithm: " + scheduling_algo );
		System.out.println("Total " + process_list.size() + " tasks are read from " + input_file );
		System.out.println("============================================================");
		
		if(scheduling_algo.equals("FCFS")){

			Queue<Process> ready_queue = new LinkedList<>();
			simulator.sort_FCFS(process_list);
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

					if(start_flag==true && start_time[ready_queue.peek().pid]==0){
						start_time[ready_queue.peek().pid]=sys_time;
						start_flag=false;
					}

/*				
					if(process_list.get(process_list.indexOf(ready_queue.peek())).burst_time==ready_queue.peek().burst_time){
						start_time[ready_queue.peek().pid]=sys_time;
					}
*/

					System.out.printf("<system time %d> process %d is running\n",sys_time,ready_queue.peek().pid);
					ready_queue.peek().burst_time--;
				}
				else if (ready_queue.peek().burst_time==0 )  {
					
				//	ready_queue.peek().end_time= sys_time;

					System.out.printf("<system time %d> process %d is finished.......\n",sys_time,ready_queue.peek().pid);
					if(process_list.isEmpty()==true){	
						if(ready_queue.size()==1) System.out.printf("<system time %d> All processes finish ....................\n",sys_time);
					}
					
					if(end_flag==true){
						end_time[ready_queue.peek().pid]=sys_time;
						end_flag=false;
					}
					ready_queue.remove();
					start_flag=true;
		 			end_flag=true;
					 
					if(ready_queue.size()==0){continue;}
					
					if(start_flag==true){
						start_time[ready_queue.peek().pid]=sys_time;
						start_flag=false;
					}
				
					System.out.printf("<system time %d> process %d is running\n",sys_time,ready_queue.peek().pid);
					ready_queue.peek().burst_time--;
				}
				 	
			sys_time++;
			
			}
			
			float turnaround_time=0,waiting_time=0,response_time=0;
			for(int i=1;i<=process_list2.size();i++){
				turnaround_time += end_time[i] - process_list2.get(i-1).arrival_time;
			}
			turnaround_time =(float) turnaround_time/process_list2.size();
			//System.out.println(turnaround_time);
			
			for(int i=1;i<=process_list2.size();i++){
				waiting_time += start_time[i]-process_list2.get(i-1).arrival_time;
			}
			waiting_time =(float) waiting_time/process_list2.size();
			response_time=waiting_time;// For FCFS waiting time= response time
			
			System.out.println("============================================================");
			System.out.printf("Average waiting time : %.2f \n" , waiting_time);
			System.out.printf("Average response time : %.2f \n" ,response_time);
			System.out.printf("Average turnaround time : %.2f \n" , turnaround_time);
			System.out.println("============================================================");	

			for(int i=1;i<=process_list2.size();i++){
				System.out.print(start_time[i] + " ");
			}
			System.out.println("");
			for(int i=1;i<=process_list2.size();i++){
				System.out.print(end_time[i] + " ");
			}		
		}
		
		boolean flag=false;
		
		if(scheduling_algo.equals("SJF")){
			
			ArrayList<Process> ready_queue = new ArrayList<Process>();
			simulator.sort_FCFS(process_list);
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
					flag=false;
				}
				else if(ready_queue.get(0).burst_time>0 ){
					if(flag==false){
						sort_SJF(ready_queue);
						flag=true;
					}

					if(start_flag==true && start_time[ready_queue.get(0).pid]==0){
						start_time[ready_queue.get(0).pid]=sys_time;
						start_flag=false;
					}


					System.out.printf("<system time %d> process %d is running\n",sys_time,ready_queue.get(0).pid);
					ready_queue.get(0).burst_time--;
				}
				else if (ready_queue.get(0).burst_time==0 )  {
					
				//	ready_queue.peek().end_time= sys_time;

					System.out.printf("<system time %d> process %d is finished.......\n",sys_time,ready_queue.get(0).pid);
					if(process_list.isEmpty()==true){	
						if(ready_queue.size()==1) System.out.printf("<system time %d> All processes finish ....................\n",sys_time);
					}
					
					if(end_flag==true){
						end_time[ready_queue.get(0).pid]=sys_time;
						end_flag=false;
					}
					ready_queue.remove(0);
					sort_SJF(ready_queue);
					start_flag=true;
		 			end_flag=true;
					 
					if(ready_queue.size()==0){continue;}
					
					if(start_flag==true){
						start_time[ready_queue.get(0).pid]=sys_time;
						start_flag=false;
					}
				
					System.out.printf("<system time %d> process %d is running\n",sys_time,ready_queue.get(0).pid);
					ready_queue.get(0).burst_time--;
				}
				 	
			sys_time++;
			
			}
			
			float turnaround_time=0,waiting_time=0,response_time=0;
			for(int i=1;i<=process_list2.size();i++){
				turnaround_time += end_time[i] - process_list2.get(i-1).arrival_time;
			}
			turnaround_time =(float) turnaround_time/process_list2.size();
			//System.out.println(turnaround_time);
			
			for(int i=1;i<=process_list2.size();i++){
				waiting_time += start_time[i]-process_list2.get(i-1).arrival_time;
			}
			waiting_time =(float) waiting_time/process_list2.size();
			response_time=waiting_time;// For FCFS waiting time= response time
			
			System.out.println("============================================================");
			System.out.printf("Average waiting time : %.2f \n" , waiting_time);
			System.out.printf("Average response time : %.2f \n" ,response_time);
			System.out.printf("Average turnaround time : %.2f \n" , turnaround_time);
			System.out.println("============================================================");	

			for(int i=1;i<=process_list2.size();i++){
				System.out.print(start_time[i] + " ");
			}
			System.out.println("");
			for(int i=1;i<=process_list2.size();i++){
				System.out.print(end_time[i] + " ");
			}			

		}
		
		if(scheduling_algo.equals("RR")){
			Queue<Process> ready_queue = new LinkedList<>();
			sys_time=0;
/*			
			ready_queue.add(process_list.get(0));
			System.out.println(process_list.get(0).time_quantum--);
			System.out.println(process_list2.get(0).time_quantum);
			System.out.println(ready_queue.peek().time_quantum);
			System.exit(0);
*/
		
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
					ready_queue.peek().time_quantum--;
					
					
					if(ready_queue.peek().burst_time>0 && ready_queue.peek().time_quantum==0 ){
						ready_queue.peek().time_quantum=time_quantum;
						ready_queue.add(ready_queue.peek());
						ready_queue.remove();
					}
					
				}
//coment : last					

				else if (ready_queue.peek().burst_time==0 ){
				
					System.out.printf("<system time %d> process %d is finished.......\n",sys_time,ready_queue.peek().pid);
					ready_queue.remove();
					
					if(ready_queue.size()>0){
						System.out.printf("<system time %d> process %d is running\n",sys_time,ready_queue.peek().pid);
						ready_queue.peek().burst_time--;
						ready_queue.peek().time_quantum--;
						
					}
					
				}			
			sys_time++;
			}
			
			
		}
		
	}
		

	public static void sort_FCFS( ArrayList<Process> process_list ){
		if(process_list.isEmpty()==true ) return;
		Process temp;
		boolean swapped=false;
		for(int i=0;i<process_list.size()-1;i++){
			swapped=false;	
			for(int j = 0; j < process_list.size()-i-1; j++){
				swapped=false;
				if(process_list.get(j).arrival_time > process_list.get(j+1).arrival_time){
					Collections.swap(process_list,j,j+1);
					swapped=true;	
				}
				else if(process_list.get(j).arrival_time == process_list.get(j+1).arrival_time ){
					
						if(process_list.get(j).pid > process_list.get(j+1).pid ){
							Collections.swap(process_list,j,j+1);
							swapped=true;
						}
				}
			}
			if(swapped==false)
				break;
		}
	} 

	public static void sort_SJF( ArrayList<Process> process_list ){
	if(process_list.isEmpty()==true ) return;

	Process temp;
		boolean swapped=false;
		for(int i=0;i<process_list.size()-1;i++){
			swapped=false;	
			for(int j = 0; j < process_list.size()-i-1; j++){
				if(process_list.get(j).burst_time > process_list.get(j+1).burst_time){
					Collections.swap(process_list,j,j+1);
					swapped=true;
				}
				else if (process_list.get(j).burst_time == process_list.get(j+1).burst_time){
					if(process_list.get(j).arrival_time > process_list.get(j+1).arrival_time){
						Collections.swap(process_list,j,j+1);
						swapped=true;
					}
				}
			}
			if(swapped==false)
				break;
		}
	}
	
		
	
    public static void main(String[] args) throws FileNotFoundException { 		
		
		simulator m1=new simulator(args);  
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


/*
	if(s.equals("SJF")){
						if(process_list.get(j).burst_time > process_list.get(j+1).burst_time ){
							Collections.swap(process_list,j,j+1);
							swapped=true;
						}
						else if (process_list.get(j).burst_time == process_list.get(j+1).burst_time){
							if(process_list.get(j).pid > process_list.get(j+1).pid ){
								Collections.swap(process_list,j,j+1);
								swapped=true;
							}
						
						}
					}
*/
// =====================================
//comment: last 

//					if (ready_queue.peek().burst_time==0 ){
//						System.out.printf("<system time %d> process %d is finished.......\n",sys_time,ready_queue.peek().pid);
//						ready_queue.remove();
	//				}
					
				
			//     		else if (ready_queue.peek().burst_time==0){
			//			ready_queue.remove();
					
/*		
					if(!(ready_queue.peek().time_quantum-->0)){
						ready_queue.peek().time_quantum=time_quantum;
						ready_queue.add(ready_queue.peek());
						ready_queue.remove();
					}
*/	



