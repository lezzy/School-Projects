//
//  main.c
//  BlackJack

/* 
 Lesmond Chow
 UC Irvine
 EECS 22 LAB Assignment #1
 This is a very basic project. I decided to leave it in one file since it's quite short. 
 */

#include <stdio.h>
#include <stdlib.h>
#include <time.h>

/* 
 This function randomizes the number for the user draw
 using the random number generator, I would use the modulo operator 
 and add 1 so the range for the random number would be from 1-13
*/

int drawcard()
{
    
    return rand() % 13 + 1;
}

/*
 Main Function
 */

int main()
{
    int usercard=0;
    int usersum=0;
    int dealercard=0;
    int dealersum=0;
    int answer;
    
    srand( time(NULL));  // Declaring this to initial the seed for the randomizer generator
    
    
    printf("\n*********************************\n**Welcome to Blackjack!**\n*********************************\n");
    
    /*
     This is the intiial start of the user drawing phase.
     */
    
    usercard = drawcard();
    printf("Your first card is: %d\n", usercard);
    usersum = usercard;
    
    /*
     This is the do-while loop for the user if the user wishes to draw more cards
     */

    do
    {
        printf("Do you want another card?\n");
        printf("Type 1 for Yes, 0 for No: ");
        scanf("%d", &answer);
        
        /*
         Scans for user input in order to continue the do while loop.
         */
        
        if (answer ==0)
        {
            break;
        }
        /*
         Prompts drawcard(); function again if user decides to proceed with drawing card.
         */
        
        usercard = drawcard();
        printf("\nYour next card is: %d\n", usercard);
        usersum = usersum + usercard;
        printf("Your combined value is %d\n", usersum);
        
        /* 
         If the user reaches 21 or over, then it goes to the result.
         I decided to use a goto statement because for the case of the user's cards
         totaling over 21, then it would automatically be over.
         */
        
        if (usersum >= 21)
        {
            goto Result;
        }
    }
    while (answer ==1);

    
    /*
     This is the do-while loop for the dealer drawing
     */
    
    do
    {
        printf("\nDealer draws another card.\n");
        dealercard = drawcard();
        printf("Dealer's card is: %d\n", dealercard);
        dealersum = dealersum + dealercard;
        printf("Dealer's value is %d, you have %d\n", dealersum, usersum);
        
        /*
         Once the dealer's sum is greater than or equal to the
         user's sum, it goes to the result to compare the results
         */
        
        if (dealersum >= usersum)
        {
            break;
        }
    }
    while (dealersum < 21);
    

    /*
     The Result statement.
     */
    
    
Result:
    
    if (dealersum > 21 || usersum == 21)
    {
        printf("\nDealer loses. You Win!\n");
    }
    
    if ((dealersum >= usersum & dealersum <21) || usersum > 21)
    {
        printf("\nSorry. You Lose!\n");
    }
    
    return 0;
}

