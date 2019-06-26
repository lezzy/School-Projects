//
//  main.cpp
//  WallColor Generator
//
//  Created by Lesmond Chow on 12/3/17.
//  Copyright © 2017 Lesmond Chow. All rights reserved.
//

#include "Functions.hpp"


int main(int argc, const char * argv[])
{
    // This declaration is used for the random number generation to make sure each iteration will be different.
    srand(time(NULL));
    
    // Declaration variables that will be passed through functions.
    int WIDTH;
    int HEIGHT;
    tuple<int, int> dimensions;
    
    
    // This call to initializer will ask user for input on the dimensions for the wall.
    // And then we set them into the int variables WIDTH and HEIGHT.
    dimensions = initializer();
    WIDTH = get<0>(dimensions);
    HEIGHT = get<1>(dimensions);
    
    // This will initialize the 2D array with the dimensions inputed.
    int **wall;
    create_wall(wall, WIDTH, HEIGHT);
    
    // This creates the 2D array of randomly generated directions: horizontal or veritcal)
    int **directions;
    create_directions(directions, WIDTH, HEIGHT);

    //
    black_or_white(wall,  WIDTH, HEIGHT);
    colors(wall, directions, WIDTH, HEIGHT);
    
    // This prints the wall
    printwall(wall, directions, WIDTH, HEIGHT);
    
    
    // This function cleans up the array used to create the wall.
    cleanup(wall, directions, WIDTH);
    
    return 0;
}
