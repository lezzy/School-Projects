//
//  BlackJack.h
//  BlackJack
//
//  Created by Lesmond Chow on 7/29/17.
//  Copyright © 2017 Lesmond Chow. All rights reserved.
//

#ifndef BlackJack_h
#define BlackJack_h

#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int draw_card(int user_sum);
int user_turn(int user_sum);
int dealer_turn(int dealer_sum, int user_sum);


#endif /* BlackJack_h */
