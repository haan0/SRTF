package srtf;


import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;


public class SRTF {

    
    static void WaitingTime(process proc[], int n,int wt[]){ 

        int rt[] = new int[n]; 
        
        for (int i = 0; i < n; i++) 

            rt[i] = proc[i].burst;

        int complete = 0, t = 0, minm = Integer.MAX_VALUE; 

        int shortest = 0, finish_time;

        boolean check = false; 

        while (complete != n) { 
            
            // Find process with minimum remaining time
            for (int j = 0; j < n; j++){
                if ((proc[j].arival <= t) &&(rt[j] < minm) && rt[j] > 0){ 

                    minm = rt[j]; 
                    shortest = j;
                    check = true; 
                    
                } 
                
            } 
            if (check == false) { 
                t++;
                continue; 
            } 
            // Reduce remaining time by one 
            rt[shortest]--; 
            // Update minimum 
            minm = rt[shortest]; 
            System.out.println("at time: "+t+" process "+proc[shortest].pid+" is running");
            

            if (minm == 0) {

                minm = Integer.MAX_VALUE; 
             
            if (rt[shortest] == 0) { 
                complete++; 

                check = false; 
               System.out.println("at time: "+t+" process "+proc[shortest].pid+" is finished");

                 finish_time = t + 1; 
                // Calculate waiting time 
                wt[shortest] = finish_time-proc[shortest].arival-proc[shortest].burst; 

                if (wt[shortest] < 0) 
                    wt[shortest] = 0; 
            } 
            
        } 
            t++; 
    } 
        System.out.println("at time: "+t+" all processes finish");
        
    }
 
    static void findTurnAroundTime(process proc[], int n,int wt[], int tat[]){  
        for (int i = 0; i < n; i++) 
            tat[i] = proc[i].burst+ wt[i]; 
    } 

    static void findavgTime(process proc[], int n){ 

        int wt[] = new int[n], tat[] = new int[n]; 

        int  total_wt = 0, total_tat = 0; 
        
        WaitingTime(proc, n, wt); 

        findTurnAroundTime(proc, n, wt, tat); 

        // Calculate total waiting time and total turnaround time
        for (int i = 0; i < n; i++) { 
            total_wt = total_wt + wt[i]; 
            total_tat = total_tat + tat[i]; 
            } 
             
            System.out.println("Average waiting time = "+(float)total_wt /(float)n);
            System.out.println("Average turn around time = " + (float)total_tat/(float)n); 

    } 

    
    public static void main(String[] args) {
         
                   ArrayList<process> jobs = new ArrayList<process>(); //array to store processes read in from file
		process proc; 
		
		try {
			FileInputStream fstream = new FileInputStream("input.txt");
			Scanner input = new Scanner(fstream);
			String line;
			Scanner info;
			int pid, arrival, cpub;
			
			while(input.hasNext()){
				line = input.nextLine();
				info = new Scanner(line);
				pid = Integer.parseInt(info.next());
				arrival = Integer.parseInt(info.next());
				cpub = Integer.parseInt(info.next());
				
				proc = new process(pid, arrival, cpub);
				jobs.add(proc); 
                                
			}
	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
                process[] pro=new process[jobs.size()];
                jobs.toArray( pro );
                findavgTime(pro, pro.length);

    }
    }

    

