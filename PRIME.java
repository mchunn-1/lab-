package lab6;

import java.util.Scanner;

public class PRIME 
{
	
	public static void main(String[] args)
	{
		System.out.print("Enter a number: ");
		Scanner getUserNum=new Scanner(System.in);
		
	    int primeCount = 0;
	    int notPrimeCount = 0;
	    int num = getUserNum.nextInt();
	    //int iteration = 0;
	    //while (iteration < num)
	    //{
	    	for(int i=2; i<num; i++)
	    	{

	    		if(num % i == 0) 
	    		{
	    			notPrimeCount++;
	    			System.out.println("Not Prime Count " + notPrimeCount);
	    			break;
	    		}
	    		
	  	    	else
	  	    	{
	    			primeCount++;
	    			System.out.println("Prime Number Count " + primeCount);
	  	    	}
	    	}
	    	//iteration++;

	    //}

	}
	    
}
