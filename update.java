package examples;

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
	
	static volatile CopyOnWriteArrayList<Integer> prime1 = new CopyOnWriteArrayList<Integer>();
	static volatile CopyOnWriteArrayList<Integer> prime2 = new CopyOnWriteArrayList<Integer>();
	static volatile CopyOnWriteArrayList<Integer> prime3 = new CopyOnWriteArrayList<Integer>();
	static volatile CopyOnWriteArrayList<Integer> prime4 = new CopyOnWriteArrayList<Integer>();
	static volatile CopyOnWriteArrayList<Integer> prime5 = new CopyOnWriteArrayList<Integer>();

	
	static volatile List<List<Integer>> allArrayLists = new ArrayList<>();
	
	
	public static void getArrays(int userNum, int arraysNeeded)
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

	}
	
	public static class findPrimes implements Runnable
	
	{
		private final List<Integer> array;
		private final Semaphore semaphore;
 
		public findPrimes(List<Integer> array, Semaphore semaphore)
		{
			this.array = array;
			this.semaphore = semaphore;
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
			            	if (Thread.currentThread().getName().equals("Thread-0"))
			            	{
			            		prime1.add(array.get(x));
			            	}
			            	if (Thread.currentThread().getName().equals("Thread-1"))
			            	{
			            		prime2.add(array.get(x));
			            	}
			            	if (Thread.currentThread().getName().equals("Thread-2"))
			            	{
			            		prime3.add(array.get(x));
			            	}
			            	if (Thread.currentThread().getName().equals("Thread-3"))
			            	{
			            		prime4.add(array.get(x));
			            	}
			            	if (Thread.currentThread().getName().equals("Thread-4"))
			            	{
			            		prime5.add(array.get(x));
			            	}
			            	//System.out.print(array.get(x) + " "+ "from thread " + Thread.currentThread().getName() +"\n");

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
	
	public static void setArrays(int userNum, int arraysNeeded)
	{
	
		getArrays(userNum, arraysNeeded);
		
		allArrayLists.add(array1);
		allArrayLists.add(array2);
		allArrayLists.add(array3);
		allArrayLists.add(array4);
		allArrayLists.add(array5);
		
		System.out.println(allArrayLists);
		
	}
	
	
	public static void threadWork(int arraysNeeded) throws Exception
	{
		Semaphore s = new Semaphore(arraysNeeded);
		
		for( int i = 0; i < allArrayLists.size(); i++)
		{
			s.acquire();
			findPrimes primes = new findPrimes(allArrayLists.get(i), s);
			new Thread(primes).start();

		}
		//Collections.sort(primeN, Comparator.comparing(Integer::valueOf));
		//System.out.println(primeN.size());
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
		int arraysNeeded = 3;
		
		
			setArrays(userNum,arraysNeeded);
			
			threadWork(arraysNeeded);
	
        
        Collections.sort(prime1, Comparator.comparing(Integer::valueOf));
      	System.out.println(prime1.size());
