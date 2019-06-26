#ifndef __LOOP_HPP__
#define __LOOP_HPP__

#include "GameView.hpp"
#include <iostream>
#include <string>

bool check_direction(std::string snake_direction, std::string last_direction);

std::string check_cell(GameView &game_view, Cell &potential_cell);
				
Cell make_cell(GameView &game_view, std::string snake_direction);

#endif
