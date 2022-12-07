package examples;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Blah
{
	static long startTime = 0;
	static long endTime = 0;
	
	private static class Worker implements Runnable
	{
		private Semaphore s;
		private int topNum;
		private int divisionNum;
		private int numThreads;
		// only call from single threaded space
		public List<Integer> getPrimes() 
		{
			return Collections.unmodifiableList(primes);
		}
		
		private List<Integer> primes = new ArrayList<>();

		public Worker( Semaphore s, int topNum, int numThreads, int divisionNum )
		{
			this.s = s;
			this.topNum = topNum;
			this.divisionNum = divisionNum;
			this.numThreads = numThreads;
		}
		
		@Override
		public void run()
		{
			
			for( int x=0; x <= topNum; x++)
				if( x % numThreads == divisionNum)
				{
					if(isPrime(x))
					{
						primes.add(x);	
					}
				}
		
			//System.out.println(primes);

			s.release();
		}

	}


	static boolean isPrime(int n)
	    {
	        // Corner case
	       	if (n <= 1)
	            return false;
	        // Check from 2 to n-1
	        for (int i = 2; i < n; i++)
	            if (n % i == 0)
	                return false;
	        return true;
	    }
   
	
	public static void control( int topNum, int numThreads) throws Exception 
	{

		long startTime = System.currentTimeMillis();
	
		Semaphore s = new Semaphore(numThreads);
		
		List<Worker> workerList = new ArrayList<>();
		
		for( int x=0; x < numThreads; x++ )
		{
			s.acquire();
			Worker w = new Worker(s, topNum, numThreads, x);
			workerList.add(w);
			new Thread( w).start();
		}
		
		for( int x=0; x < numThreads; x++)
			s.acquire();
		
		long endTime = System.currentTimeMillis();
		
		System.out.println("Total time: " + (endTime-startTime)/1000f);
		
		// i am in single threaded space
		List<Integer> results = new ArrayList<>();
		
		for( Worker w: workerList)
			results.addAll(w.getPrimes());
		
		Collections.sort(results);
		
		//SlowGUI.output.append(results +"\n");
		
		for(Integer n: results)
		{
			SlowGUI.output.append(n +"\n");
		}
	
		//System.out.println(results);
		
		SlowGUI.progress.setText(results.size() + " prime numbers found.");
		SlowGUI.output.append("This took " + (endTime - startTime)/1000f + " seconds.");
	}
	
	public static void main(String[] args) throws Exception
	{
	
		int numThreads = 3;
	
		int topNum= 500;		
		
		control(topNum,numThreads);
	}
}
	

	

