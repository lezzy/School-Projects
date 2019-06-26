#include "Loop.hpp"

bool check_direction(std::string snake_direction, std::string last_direction)
{
	// this function is used to make sure the current direction isn't the opposite
	// of the last direction.
	
	if (snake_direction == "up" && last_direction == "down")
	{
		return false;
	}
	
	else if (snake_direction == "down" && last_direction == "up")
	{
		return false;
	}
	
	else if (snake_direction == "left" && last_direction == "right")
	{
		return false;
	}
	
	else if (snake_direction == "right" && last_direction == "left")
	{
		return false;
	}
	return true;
}

Cell make_cell(GameView &game_view, std::string snake_direction)
{
	// this function makes a cell based off of 
	// where the snake is heading so it can move onward
	Cell new_cell;
	if (snake_direction == "up")
	{
		new_cell.first = game_view.snake().begin()->first;
		new_cell.second = game_view.snake().begin()->second - 1;
	}
	
	else if (snake_direction == "right")
	{
		new_cell.first = game_view.snake().begin()->first + 1;
		new_cell.second = game_view.snake().begin()->second;
	}
	
	else if (snake_direction == "left")
	{
		new_cell.first = game_view.snake().begin()->first - 1;
		new_cell.second = game_view.snake().begin()->second;
	}
	
	else if (snake_direction == "down")
	{
		new_cell.first = game_view.snake().begin()->first;
		new_cell.second = game_view.snake().begin()->second + 1;
	}
	
	return new_cell;
}

std::string check_cell(GameView &game_view, Cell &potential_cell)
{
	// will be called whenever the snake moves
	// checks cell if it's an red, yellow apple, boundary, snake, or blank
	// blank meaning it's free
	
	for (auto e = game_view.yellow_apples().begin(); 
			  e != game_view.yellow_apples().end();
			  e++)
	{
		if (potential_cell.first == e->first && 
			potential_cell.second == e->second)
		{
			return "yellow";
		}
	}
	
	for (auto e = game_view.red_apples().begin(); 
			  e != game_view.red_apples().end();
			  e++)
	{
		if (potential_cell.first == e->first && 
			potential_cell.second == e->second)
		{
			return "red";
		}
	}
	
	for (auto e = game_view.boundary().begin(); 
			  e != game_view.boundary().end();
			  e++)
	{
		if (potential_cell.first == e->first && 
			potential_cell.second == e->second)
		{
			return "boundary";
		}
	}
	
	for (auto e = game_view.snake().begin(); 
			  e != game_view.snake().end();
			  e++)
	{
		if (potential_cell.first == e->first && 
			potential_cell.second == e->second)
		{
			return "snake";
		}
	}
	
	return "blank";
}

