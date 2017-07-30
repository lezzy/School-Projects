//
//  main.c
//  BlackJack

/* 
 Lesmond Chow
 UC Irvine
 EECS 22 LAB Assignment #1
 This is a very basic project for a basic game of BlackJack.
 There will only be one player (the user) and it is set that the
 dealer will stop dealing itself once it reaches a total value of 17 or higher.
 I also decided to disregard the Ace value, (1 or 11) since this was a simple project for
 introductory C.
 */

#include "BlackJack.h"



/*
 Main Function
 */

int main()
{
    /*
     Initializations for int variables.
     */
    int user_sum=0;
    int dealer_sum=0;
    
    srand( time(NULL));  // Declaring this to initial the seed for the randomizer generator
    
    
    printf("\n*********************************\n******Welcome to Blackjack!******\n*********************************\n");
    
    /*
     This is the intiial start of the user drawing phase. The user initially draws 2 cards.
     */
    printf("User");
    user_sum = draw_card(user_sum);
    printf("User");
    user_sum = draw_card(user_sum);
    printf("Your combined value is now: [ %d ]\n", user_sum);
    
    // The call of user_turn will return the most updated user_sum
    user_sum = user_turn(user_sum);
    if (user_sum > 21)
    {
        // If the user_sum is over 21, the user automatically loses thus not entering dealer's turn at all. 
        goto Result;
    }

    printf("\n******* Dealer's Turn ******* \n");
    dealer_sum = dealer_turn(dealer_sum, user_sum);


    /*
     The Result statement.
     */
    
    
Result:
    
    if (dealer_sum > 21 || user_sum == 21)
    {
        printf("\nDealer loses. You Win!!!!\n");
    }
    
    if ((dealer_sum >= user_sum & dealer_sum <21) || user_sum > 21)
    {
        printf("\nSorry. You Lose.\n");
    }
    
    return 0;
}

