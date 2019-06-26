#include "Loop.hpp"
#include "UpdateBoard.hpp"
int main()
{
    // Create game_view
    GameView game_view;

    // Some variables
    bool quit = false;
    bool game_over = false;
    unsigned int last_update = 0;

    // Adding some example apples and a stationary snake
    std::vector<Cell> red_apples(1);
    red_apples[0] = {10, 10};
    game_view.red_apples() = red_apples;

    std::vector<Cell> yellow_apples(1);
    yellow_apples[0] = {30, 30};
    game_view.yellow_apples() = yellow_apples;

    std::vector<Cell> snake(5);
    snake = {{15,16}, {15,17}, {15,18}, {15,19}, {15,20}};
    game_view.snake() = snake;
    
    // variables that will be used.
    std::string snake_direction = "up";
    std::string last_direction= "";
    int counter = 0;
    int counter2 = 0;
    bool grow = false;
    bool shrink = false;
    bool run = false;

    // Game loop
    while ( !quit )
    {
        // Process game events
        GameEvent e = game_view.get_event();
        if (run == false || quit == true)
        { // this if statement is implemented because I ran into a problem
          // where when say if my snake is going up, i rapidly click left then down
          // the game would end because supposedly the last direction was left and 
          // before the snake could update to going left, it was stil in a straight
          // line and I'd get the error where the snake ran into itself, which isn't
          // true since it was in a straight line still. 
          
          // This if statement makes sure that each time a command gets pressed
          // there can only be 1 command in the update section. And it makes sure that
          // it updates before handling another event/command.
		    switch (e)
		    {
		        // TODO: Missing events
		        case GameEvent::Quit:
		            quit = true;
		            break;
		        case GameEvent::Null:
		        	//do nothing
		        	break;
		        case GameEvent::Up:
		        	// move snake up
		        	if (snake_direction != "down")
		        	{
		        		last_direction = snake_direction;
		        		snake_direction = "up";
		        		run = true;
		        	}
		        	break;
		        case GameEvent::Down:
		        	// move snake down
		        	if (snake_direction != "up")
		        	{
		        		last_direction = snake_direction;
		        		snake_direction = "down";
		        		run = true;
		        	}
		        	break;
		        case GameEvent::Left:
		        	// move snake left
		        	if (snake_direction != "right")
		        	{
		        		last_direction = snake_direction;
		        		snake_direction = "left";
		        		run = true;
		        	}

		        	break;
		        case GameEvent::Right:
		        	//move snake right
		        	if (snake_direction != "left")
		        	{
		        		last_direction = snake_direction;
		        		snake_direction = "right";
		        		run = true;
		        	}
		        	break;
		        default:
		            break;
       		}
        }
        // Update game state
        if (game_view.time() - last_update > 250 && !game_over)
        {
            // TODO: Missing update code
	        
	        // Conditionals for checking if snake ate a red apple
	        
		    if (grow == true && counter < 5)
			{
				grow_snake(game_view, snake_direction, counter);
			}
			else if (counter == 5)
			{
				grow = false;
				counter = 0;
			}
		
			//Conditionals for checking if snake ate a yellow annple
			if ((shrink == true) && (counter2 < 5))
			{
				shrink_snake(game_view, counter2);
			}
			else if (counter2 == 5)
			{
				shrink = false;
				counter2 = 0;
			}
		
			std::string status;
		    Cell potential_cell;
		    
		    // This creates the potential cell that the snake wants to move
		    // based off on the direction that it's heading
		    // Then it gets passed to check this potential cell to see
		    // if it can land there.
		    potential_cell = make_cell(game_view, snake_direction);
		    status = check_cell(game_view, potential_cell);
		
		    if (status == "blank")
	    	{	
	    		// blank means it's a free spot!
		    	// start to move snake
		    	move_snake(game_view, potential_cell);
		    }
		    else if (status == "yellow")
		    {
		    	// means its the yellow apple
		    	// shrink snake
		    	remove_yellow_apple(game_view);
		    	if (game_view.snake().size() > 5)
		    	{
		    		shrink_snake(game_view, counter2);
		    		shrink = true;
		    	}
		    }
		    else if (status == "red")
		    {
		    	// red apple
		    	// grow snake
		    	remove_red_apple(game_view);
		    	grow_snake(game_view, snake_direction, counter);
		    	grow = true;
		    }
		    else if (status == "snake")
		    {
		    	// this if statement is used to see if 
		    	// the direction of the snake isn't the opposite as the
		    	// last direction. If it is, it just ignores it.
		    	// However it will cause the game to end if the snake runs into
		    	// itself.
		    	if (check_direction(snake_direction, last_direction) == true)
		    	{
		    		game_over = true;
		    		game_view.game_over() = true;
		   		}
		   	}
		   	else if (status == "boundary")
		   	{
		   		// boundary blocks
		   		
		   		game_over = true;
		   		game_view.game_over() = true;
		   	}
	
	        last_update = game_view.time();
	        
	        run = false;
    	
        }

        // The next command actually render the GameView to your screen.
        // You should not need to add additional calls to this method.
        game_view.render();
    }

    return 0;
}
