//
//  Functions.hpp
//  Wall Color Generator
//
//  Created by Lesmond Chow on 12/2/17.
//  Copyright © 2017 Lesmond Chow. All rights reserved.
//

#ifndef Functions_hpp
#define Functions_hpp

#include <stdio.h>
#include <iostream>
#include <tuple>
#include <cstdlib>
using namespace std;

int randomizer(int base);

void black_or_white();

void colors (int ** &wall, int ** &directions, int WIDTH, int HEIGHT);

tuple<int, int> initializer();

void create_wall(int ** &wall, int WIDTH, int HEIGHT);

void create_directions(int ** &directions, int WIDTH, int HEIGHT);

void cleanup( int ** &wall, int ** &directions, int WIDTH);

void black_or_white(int ** &wall, int WIDTH, int HEIGHT);

void printwall(int **wall, int **directions, int WIDTH, int HEIGHT);

#endif /* Functions_hpp */
