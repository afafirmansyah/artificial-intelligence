/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nim;

import java.util.Arrays;
import java.util.HashSet;

/**
 *
 * @author Kaze
 */
public class Nin2 {
    /**
     * @param args the command line arguments
     */
    static int PLAYER1 = 1; 
    static int PLAYER2 = 2; 

    // A Function to calculate Mex of all the values in that set 
    static int calculateMex(HashSet<Integer> Set) 
    { 
            int Mex = 0; 

            while (Set.contains(Mex)) 
                    Mex++; 

            return (Mex); 
    } 

    // A function to Compute Grundy Number of 'n' 
    static int calculateGrundy(int n, int Grundy[]) 
    { 
            Grundy[0] = 0; 
            Grundy[1] = 1; 
            Grundy[2] = 2; 
            Grundy[3] = 3; 

            if (Grundy[n] != -1) 
                    return (Grundy[n]); 

            // A Hash Table 
            HashSet<Integer> Set = new HashSet<Integer>(); 

            for (int i = 1; i <= 3; i++) 
                            Set.add(calculateGrundy (n - i, Grundy)); 

            // Store the result 
            Grundy[n] = calculateMex (Set); 

            return (Grundy[n]); 
    } 

    // A function to declare the winner of the game 
    static void declareWinner(int whoseTurn, int piles[],int Grundy[], int n) 
    { 
            int xorValue = Grundy[piles[0]]; 

            for (int i = 1; i <= n - 1; i++) 
                    xorValue = xorValue ^ Grundy[piles[i]]; 

            if (xorValue != 0) 
            { 
                    if (whoseTurn == PLAYER1) 
                            System.out.printf("Player 1 will win\n"); 
                    else
                            System.out.printf("Player 2 will win\n"); 
            } 
            else
            { 
                    if (whoseTurn == PLAYER1) 
                            System.out.printf("Player 2 will win\n"); 
                    else
                            System.out.printf("Player 1 will win\n"); 
            } 

            return; 
    } 
    public static void main(String[] args) {
        // Test Case 1 
	int piles[] = {10, 4}; 
	int n = piles.length; 

	// Find the maximum element 
	int maximum = Arrays.stream(piles).max().getAsInt(); 

	// An array to cache the sub-problems so that 
	// re-computation of same sub-problems is avoided 
	int Grundy[] = new int[maximum + 1]; 
	Arrays.fill(Grundy, -1); 

	// Calculate Grundy Value of piles[i] and store it 
	for (int i = 0; i <= n - 1; i++) 
		calculateGrundy(piles[i], Grundy); 

	declareWinner(PLAYER1, piles, Grundy, n); 

	/* Test Case 2 
	int piles[] = {3, 8, 2}; 
	int n = sizeof(piles)/sizeof(piles[0]); 


	int maximum = *max_element (piles, piles + n); 

	// An array to cache the sub-problems so that 
	// re-computation of same sub-problems is avoided 
	int Grundy [maximum + 1]; 
	memset(Grundy, -1, sizeof (Grundy)); 

	// Calculate Grundy Value of piles[i] and store it 
	for (int i=0; i<=n-1; i++) 
		calculateGrundy(piles[i], Grundy); 

	declareWinner(PLAYER2, piles, Grundy, n); */
        
    }
}
