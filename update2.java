package examples;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;

public class SlowCalc2 
{
	static volatile List<Integer> array1 = new ArrayList<>();
	static volatile List<Integer> array2 = new ArrayList<>();
	static volatile List<Integer> array3 = new ArrayList<>();
	static volatile List<Integer> array4 = new ArrayList<>();
	static volatile List<Integer> array5 = new ArrayList<>();
	
//	static volatile List<Integer> array1Add = new ArrayList<>();
//	static volatile List<Integer> array2Add = new ArrayList<>();
//	static volatile List<Integer> array3Add = new ArrayList<>();
//	static volatile List<Integer> array4Add = new ArrayList<>();
//	static volatile List<Integer> array5Add = new ArrayList<>();

	
	
	static volatile CopyOnWriteArrayList<Integer> array1Add = new CopyOnWriteArrayList<>();
	static volatile CopyOnWriteArrayList<Integer> array2Add = new CopyOnWriteArrayList<>();
	static volatile CopyOnWriteArrayList<Integer> array3Add = new CopyOnWriteArrayList<>();
	static volatile CopyOnWriteArrayList<Integer> array4Add = new CopyOnWriteArrayList<>();
	static volatile CopyOnWriteArrayList<Integer> array5Add = new CopyOnWriteArrayList<>();
	
	static CopyOnWriteArrayList<Integer> primeN = new CopyOnWriteArrayList<Integer>();
 


	
	static volatile List<List<Integer>> allArrayLists = new ArrayList<>();
	static volatile List<List<Integer>> allAddedArrays = new ArrayList<>();

	

	
	public static int getArrays(int userNum, int arraysNeeded)
	{
		int i = 1;
		if (arraysNeeded == 1)
		{
			while(i<=userNum)
			{
				array1.add(i);
				i++;
			}
		}
		
		if (arraysNeeded == 2) 
		{
			while(i<=userNum)
			{
				array1.add(i);
				int con = i;
				i++;
				while(i<=userNum && i > con)
				{
					array2.add(i);
					i++;
					break;
				}
			}
		}
		
		if (arraysNeeded == 3)
		{
			first:
				while(i<=userNum)
				{
					array1.add(i);
					int con = i;
					i++;
					while(i<=userNum && i > con)
					{
						array2.add(i);
						int con2 = i;
						i++;
						while(i<=userNum && i > con2)
						{
							array3.add(i);
							i++;
							continue first;
						}
					}
				}
		}
		
		if (arraysNeeded == 4)
		{
			first:
				while(i<=userNum)
				{
					array1.add(i);
					int con = i;
					i++;
					while(i<=userNum && i > con)
					{
						array2.add(i);
						int con2 = i;
						i++;
						while(i<=userNum && i > con2)
						{
							array3.add(i);
							int con3 = i;
							i++;
							while(i<=userNum && i > con3)
							{
								array4.add(i);
								i++;
								continue first;
							}

						}
					}
				}
		}
		
		if (arraysNeeded == 5)
		{
			first:
				while(i<=userNum)
				{
					array1.add(i);
					int con = i;
					i++;
					while(i<=userNum && i > con)
					{
						array2.add(i);
						int con2 = i;
						i++;
						while(i<=userNum && i > con2)
						{
							array3.add(i);
							int con3 = i;
							i++;
							while(i<=userNum && i > con3)
							{
								array4.add(i);
								int con4 = i;
								i++;
								while(i<=userNum && i > con4)
								{
									array5.add(i);
									i++;
									continue first;
								}
				
							}

						}
					}
				}
		}
		
		allArrayLists.add(array1);
		allArrayLists.add(array2);
		allArrayLists.add(array3);
		allArrayLists.add(array4);
		allArrayLists.add(array5);
		
		//System.out.println(allArrayLists);
		return arraysNeeded;
	}
	
	public static class findPrimes implements Runnable
	
	{
		private final List<Integer> array;
		private final Semaphore semaphore;
		private final List<Integer> addTo;
 
		public findPrimes(List<Integer> array, Semaphore semaphore, List<Integer> addTo)
		{
			this.array = array;
			this.semaphore = semaphore;
			this.addTo = addTo;
		}
		@Override
		public void run() 
		{
			
			try
			{
			    
				for (int x = 0; x < array.size(); x++) 
				{
			        boolean isPrime = true;
			        if (array.get(x) == 1)
			            isPrime = false;
			        else 
			        {
			            // check to see if the numbers are prime
			            for (int j = 2; j <= array.get(x) / 2; j++) 
			            {
			                if (array.get(x) % j == 0) {
			                    isPrime = false;
			                    break;
			                }
			            }
			        }
			        // print the number
			        if (isPrime)
			        {
			            if (array.get(x) == 0) {}
			            else 
			            {

			            	addTo.add(array.get(x)); 
			            	//System.out.println(addTo);
			            	//System.out.println(array.get(x) + " "+ "from thread " + Thread.currentThread().getName());

			            }
		            	
			        }
			        
			    }
				
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				System.out.println("exit");
				System.exit(1);
			}
			
			finally
			{
				semaphore.release();
			}
		}
	}
//	
	
	public static void threadWork(int arraysNeeded, int userNum) throws Exception
	{
		
		allAddedArrays.add(array1Add);
		allAddedArrays.add(array2Add);
		allAddedArrays.add(array3Add);
		allAddedArrays.add(array4Add);
		allAddedArrays.add(array5Add);
		
		Semaphore s = new Semaphore(getArrays(userNum,arraysNeeded));
		
		for( int i = 0; i < allArrayLists.size(); i++)
		{
			s.acquire();
			findPrimes primes = new findPrimes(allArrayLists.get(i), s, allAddedArrays.get(i));
			new Thread(primes).start();

		}
		
		//Collections.sort(array4Add, Comparator.comparing(Integer::valueOf));
		//System.out.println(array4Add);
		//System.out.println(array4Add.size());
	}
	
	
	public static void main(String[] args) throws Exception
	{
//		Scanner scan1 = new Scanner(System.in);
//        System.out.print("Enter integer number: ");
//        int userNum = scan1.nextInt();
//        scan1.close();
//        System.out.println("The number entered by user is: " + userNum);
//        
//        Scanner scan2 = new Scanner(System.in);
//        System.out.print("Enter integer number from 1-5 to set thread count: ");
//        int arraysNeeded = scan2.nextInt();
//        scan2.close();
//        System.out.println("The number of threads will be: " + arraysNeeded);
//       
		int userNum = 10000;
		int arraysNeeded = 5;
		
		
	
		//getArrays(userNum,arraysNeeded);
			
		threadWork(arraysNeeded,userNum);
		
		List<List<Integer>> allPrimes = new ArrayList<>();
		allPrimes.add(array1Add);
		allPrimes.add(array2Add);
		allPrimes.add(array3Add);
		allPrimes.add(array4Add);
		allPrimes.add(array5Add);
		

		
	
        
//		Collections.sort(array3Add, Comparator.comparing(Integer::valueOf));
//		System.out.println(array4Add);
//		System.out.println(array4Add.size());
//		
		//Collections.sort(allPrimes, Comparator.comparing(Integer::valueOf));
		System.out.println(allPrimes);


	}
}
