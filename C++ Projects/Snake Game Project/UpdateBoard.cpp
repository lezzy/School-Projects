#include "UpdateBoard.hpp"

// the random device
std::random_device rd;
std::default_random_engine engine(rd());
std::uniform_int_distribution<int> dist(0, GAME_SIZE-1);

void move_snake(GameView &game_view, Cell head)
{
	game_view.snake().pop_back();
	game_view.snake().insert(game_view.snake().begin(), head);
}

void remove_red_apple(GameView &game_view)
{
	game_view.red_apples().pop_back();
	std::string status;
	while (true)
	{
		// while true statement is used so if the randomly 
		// generated cell isn't in a blank spot
		// then it will generate another one.
		Cell random_cell = Cell(dist(engine), dist(engine));
		status = check_cell(game_view, random_cell);
		if (status == "blank")
		{
			game_view.red_apples().insert(game_view.red_apples().begin(),
										  random_cell);
			break;
		}
	}
}

void remove_yellow_apple(GameView &game_view)
{
	game_view.yellow_apples().pop_back();
	std::string status;
	while (true)
	{
		Cell random_cell = Cell(dist(engine), dist(engine));
		status = check_cell(game_view, random_cell);
		if (status == "blank")
		{
			game_view.yellow_apples().insert(game_view.yellow_apples().begin(),
										  random_cell);
			break;
		}
	}
}

void grow_snake(GameView &game_view, std::string snake_direction, int &counter)
{
	Cell temp;
	if (counter < 5)
	{
		// if statements used to see which direction the snake is heading
		// so it knows where to add the cell in the tail.
		temp = game_view.snake()[game_view.snake().size()-1];
		if (snake_direction == "right")
		{
			temp.second = temp.second - 1;
		}
		else if (snake_direction == "left")
		{
			temp.second = temp.second + 1;
		}
		else if (snake_direction == "up")
		{
			temp.first = temp.first - 1;
		}
		else if (snake_direction == "down")
		{
			temp.first = temp.first + 1;
		}
		counter++;
		game_view.snake().resize(game_view.snake().size() + 1, temp);
	}
}

void shrink_snake(GameView &game_view, int &counter2)
{
	if (counter2 < 5)
	{
		counter2++;
		game_view.snake().pop_back();
	}
}

