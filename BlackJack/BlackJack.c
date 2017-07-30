//
//  BlackJack.c
//  BlackJack
//
//  Created by Lesmond Chow on 7/29/17.
//  Copyright © 2017 Lesmond Chow. All rights reserved.
//

#include "BlackJack.h"

/*
This function randomizes the number for the user draw
using the random number generator, I would use the modulo operator
and add 1 so the range for the random number would be from 1-10
*/

int draw_card(int user_sum)
{
    
    int new_card = rand() % 10 + 1;
    printf(" draws new card: %d\n", new_card);
    return user_sum + new_card;
}

/*
 User Turn Function: This function will serve as a recursive function for the user turn.
 It will call the same function again and pass the updated user_sum if the user wants to draw another card,
 or if the user gives an invalid input and will ask again if the user wants to proceed or not.
 Once the user doesn't want to draw anymore cards, it will move on and go into the dealer's turn
 */

int user_turn(int user_sum)
{
    int answer=0;
    if (user_sum >= 21)
    {
        return user_sum;
    }
    printf("Do you want another card?\n");
    printf("Type 1 for Yes, 0 for No: ");
    scanf("%d", &answer);
    if (answer == 0)
    {
        return user_sum;
    }
    else if (answer == 1)
    {
        printf("User");
        user_sum = draw_card(user_sum);
        printf("Your combined value is now: [ %d ]\n", user_sum);
        return user_turn(user_sum);
    }
    else
    {
        printf("Invalid input; Please try again.\n");
        return user_turn(user_sum);
    }
    
}

/*
 This is the function for dealer's turn. It also functions recursively until the dealer's total 
 sum reaches 17 or higher. 
 */

int dealer_turn(int dealer_sum, int user_sum)
{
    if (dealer_sum>= user_sum || dealer_sum > 17)
    {
        return dealer_sum;
    }
    printf("Dealer");
    dealer_sum = draw_card(dealer_sum);
    printf("Dealer's value is [ %d] , you have [ %d ]\n", dealer_sum, user_sum);
    return dealer_turn(dealer_sum, user_sum);
}
