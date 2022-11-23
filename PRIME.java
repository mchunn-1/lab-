package lab6;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PRIME 
{
	
	public static void main(String[] args)
	{
		Scanner getUserNum = new Scanner(System.in);
        System.out.print("Enter a number: ");
        int num = getUserNum.nextInt();
        
        List<Integer> primeNumbers = new ArrayList<>();
        List<Integer> nonPrimeNumbers = new ArrayList<>();

        int i = 2;
        
        while (i <= num)
        {
            boolean passPrimeCheck = true;

            for (int x = 2; x < i; x++)
            {
                if (i % x == 0)
                {
                    passPrimeCheck = false;
                    nonPrimeNumbers.add(i);
                    break; 
                }
            }

            if (passPrimeCheck) 
            {
                primeNumbers.add(i);
            }
            
            i++;
            
        }
        
        String allNonPrime = nonPrimeNumbers.toString();
        System.out.println("Not Prime Numbers: " + allNonPrime);
        
        String allPrime = primeNumbers.toString();
        System.out.println("Prime Numbers: " + allPrime);
    }
}
