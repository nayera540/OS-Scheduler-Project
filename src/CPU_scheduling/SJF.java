import java.util.Scanner;
public class SJF {
    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);
        System.out.println ("enter no of process:");
        int n = sc.nextInt();
        int pid[] = new int[n];
        int at[] = new int[n];
        int bt[] = new int[n];
        int ct[] = new int[n];
        int ta[] = new int[n];
        int wt[] = new int[n];
        int f[] = new int[n];
        int k[]= new int[n];   
        int preemptive=0;
        int st=0, tot=0;
        float avgwt=0, avgta=0;
        System.out.println ("enter 1 for preemptive and 0 for non_preeptive ");
        preemptive = sc.nextInt();
        for(int i=0;i<n;i++)
        {
            System.out.println ("enter process " + (i+1) + " arrival time:");
            at[i] = sc.nextInt();
            System.out.println ("enter process " + (i+1) + " brust time:");
            bt[i] = sc.nextInt();
            k[i]= bt[i];
            pid[i] = i+1;
            f[i] = 0;
        }
        switch(preemptive)
        {
        case 1 :
        while(true){
     int min=99,c=n;
     if (tot==n)
     break;
    
     for ( int i=0;i<n;i++)
     {
     if ((at[i]<=st) && (f[i]==0) && (bt[i]<min))
     {
     min=bt[i];
     c=i;
     }
     }
    
     if (c==n)
     st++;
     else
     {
     bt[c]--;
     st++;
     if (bt[c]==0)
     {
     ct[c]= st;
     f[c]=1;
     tot++;
     }
     }
    }
    
    for( int i=0;i<n;i++)
    {
     ta[i] = ct[i] - at[i];
     wt[i] = ta[i] - k[i];
     avgwt+= wt[i];
     avgta+= ta[i];
    }
    
    System.out.println("pid  arrival  burst  complete turn waiting");
    for(int i=0;i<n;i++)
    {
     System.out.println(pid[i] +"\t"+ at[i]+"\t"+ k[i] +"\t"+ ct[i] +"\t"+ ta[i] +"\t"+ wt[i]);
    }
    
    System.out.println("\naverage tat is "+ (float)(avgta/n));
    System.out.println("average wt is "+ (float)(avgwt/n));
    sc.close();
    break;
                
    case 0:
    while(true)
{
int c=n, min=999;
if (tot == n) 
break;
for (int i=0; i<n; i++)
{

if ((at[i] <= st) && (f[i] == 0) && (bt[i]<min))
{
min=bt[i];
c=i;
}
}
if (c==n)
st++;
else
{
ct[c]=st+bt[c];
st+=bt[c];
ta[c]=ct[c]-at[c];
wt[c]=ta[c]-bt[c];
f[c]=1;
tot++;
}
}
System.out.println("\npid  arrival brust  complete turn waiting");
for(int i=0;i<n;i++)
{
avgwt+= wt[i];
avgta+= ta[i];
System.out.println(pid[i]+"\t"+at[i]+"\t"+bt[i]+"\t"+ct[i]+"\t"+ta[i]+"\t"+wt[i]);
}
System.out.println ("\naverage tat is "+ (float)(avgta/n));
System.out.println ("average wt is "+ (float)(avgwt/n));
sc.close();
}
}
}

        


    
