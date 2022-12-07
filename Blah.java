package examples;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Blah
{
	public static void main(String[] args) throws Exception
	{
		int numThreads = 4;
		
		int topNum= 50;
		
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
		
		// i am in single threaded space
		List<Integer> results = new ArrayList<>();
		
		for( Worker w: workerList)
			results.addAll(w.getPrimes());
		
		Collections.sort(results);
		
//		for( int x=0; x < results.size(); x++ )
//		{
//			if( x != results.get(x) )
//				throw new Exception("No");
//				
//			//System.out.println(x);
//		}
	//	System.out.println(results);
			
	}
	
	
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
		
			System.out.println(primes);

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
   
	}
	
	
	

	

