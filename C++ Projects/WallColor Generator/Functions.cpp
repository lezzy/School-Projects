//
//  Functions.cpp
//  Wall Color Generator
//
//  Created by Lesmond Chow on 12/2/17.
//  Copyright © 2017 Lesmond Chow. All rights reserved.
//

#include "Functions.hpp"

int randomizer (int base)
{
    return rand()%base;
}

tuple<int, int> initializer()
{
    int input;
    
    cout << "Please input the width of the wall: ";
    cin >> input;
    int WIDTH = input;
    
    cout << "Please input the height of the wall: ";
    cin >> input;
    int HEIGHT = input;
    
    return {WIDTH, HEIGHT};
}

void create_wall(int ** &wall, int WIDTH, int HEIGHT)
{
    wall = new int*[WIDTH];
    for (int i = 0; i < WIDTH; i++)
    {
        wall[i] = new int[HEIGHT];
    }
}

void create_directions(int ** &directions, int WIDTH, int HEIGHT)
{
    directions = new int*[WIDTH];
    for (int i = 0; i < WIDTH; i++)
    {
        directions[i] = new int[HEIGHT];
    }
}

void cleanup( int ** &wall, int ** &directions, int WIDTH)
{
    for (int i = 0; i < WIDTH; i++)
    {
        delete [] wall[i];
        delete [] directions[i];
    }
    delete [] wall;
    delete [] directions;
}

void black_or_white(int ** &wall, int WIDTH, int HEIGHT)
{
    while (true)
    {
        int black_count = 0;
        int white_count = 0;
        for (int x = 0; x < WIDTH; x++)
        {
            for (int y = 0; y < HEIGHT; y++)
            {
                int blackorwhite = randomizer(2); // This base of 2 is to determine which block will be colored.
                wall[x][y] = blackorwhite;
                if (blackorwhite == 1)
                {
                    black_count++;
                }
                else
                {
                    white_count++;
                }

            }
        }
        int lower = (WIDTH*HEIGHT*4)/10 + 1;
        int upper = (WIDTH*HEIGHT*6)/10 - 2;
        if (lower <= white_count && white_count <= upper)
        {
            break;
        }
    }
}

void colors (int ** &wall, int ** &directions, int WIDTH, int HEIGHT)
{
    //int color;
    //int direction;
    for (int x = 0; x < WIDTH; x++)
    {
        for (int y = 0; y < HEIGHT; y++)
        {
            if (wall[x][y] == 1)
            {
                int color = randomizer(4) + 1;
                wall[x][y] = color;
                int direction = randomizer(2) + 1;
                directions[x][y] = direction;
            }
        }
    }
}

void printwall(int **wall, int **directions, int WIDTH, int HEIGHT)
{
    cout << endl << endl;
    for (int i = 0; i < WIDTH - 4; i++)
    {
        cout << " ";
    }
    cout << "WALL: COLORS";
    for (int i = 0; i < WIDTH - 4; i++)
    {
        cout << " ";
    }
    cout << endl;
    int White_count=0;
    //int color_count=0;
    int Cyan_count = 0;
    int Magenta_count = 0;
    int Yellow_count = 0;
    int Green_count = 0;
    for (int y = 0; y < HEIGHT; y++)
    {
        for (int x = 0; x < WIDTH; x++)
        {
            if (wall[x][y] == 0)
            {
                White_count++;
                cout << "W ";
            }
            else if (wall[x][y] == 1 )
            {
                Cyan_count++;
                cout << "C ";
            }
            else if (wall[x][y] == 2 )
            {
                Magenta_count++;
                cout << "M ";
            }
            else if (wall[x][y] == 3)
            {
                Yellow_count++;
                cout << "Y ";
            }
            else if (wall[x][y] == 4)
            {
                Green_count++;
                cout << "G ";
            }
//            else
//            {
//                cout << wall[x][y] << " ";
//                color_count++;
//            }
        }
        cout << endl;
    }
    cout << endl;
    cout << "White total: " << White_count << endl;
    //cout << "color total: " << color_count << endl;
    cout << "Cyan total: " << Cyan_count << endl;
    cout << "Magenta total: " << Magenta_count << endl;
    cout << "Yellow total: " << Yellow_count << endl;
    cout << "Green total: " << Green_count << endl;
    cout << "Color total: " << Cyan_count + Magenta_count + Yellow_count + Green_count << endl;
    
    cout << endl;
    for (int i = 0; i < WIDTH - 8; i++)
    {
        cout << " ";
    }
    cout << "WALL: DIRECTIONS";
    for (int i = 0; i < WIDTH - 8; i++)
    {
        cout << " ";
    }
    cout << endl;

    for (int y = 0; y < HEIGHT; y++)
    {
        for (int x = 0; x < WIDTH; x++)
        {
            if (y%2 == 0)
            {
                if (x%2 == 1)
                {
                    cout << "v ";
                }
                else
                {
                    cout << "h ";
                }
            }
            else
            {
                if (x%2 == 0)
                {
                    cout << "v ";
                }
                else
                {
                    cout << "h ";
                }
            }
        }
        cout << endl;
    }
}


