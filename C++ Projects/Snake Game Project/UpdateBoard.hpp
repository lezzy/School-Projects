#ifndef __UPDATEBOARD_HPP__
#define __UPDATEBOARD_HPP__

#include "Loop.hpp"
#include <random>

void move_snake(GameView &game_view, Cell head);

void grow_snake(GameView &game_view, std::string snake_direction, int &counter);

void shrink_snake(GameView &game_view, int &counter2);

void remove_red_apple(GameView &game_view);

void remove_yellow_apple(GameView &game_view);



#endif
