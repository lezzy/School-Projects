## User_Interface

import othello_logic

othello = othello_logic.OthelloGameState()


#This function starts of the game, asking the initial
#size for the board, the win state and who starts first.

def start() -> None:
    while True:
        try:
            rows = int(input('Please enter an even number between 4 to 16 for the board rows: '))
            columns = int(input('Please enter an even number between 4 to 16 for the board columns: '))
            if rows < 4 or rows > 16 or rows%2 != 0:
                raise InvalidRowInput
            elif columns < 4 or columns > 16 or columns%2 != 0:
                raise InvalidColumnInput
            else:
                break
        except:
            print('Invalid column or row input(s). Please try again.\n')


    while True:
        try:
            winning_state = int(input('''Please choose the state of how to win by the corresponding number:
1.) Winner has most amount of discs
2.) Winner has least amount of discs
'''))
            turn = int(input('''Please choose who goes first by the corresponding number:
1.) Black
2.) White
'''))
            if winning_state < 1 or winning_state > 2:
                raise InvalidInputs
            elif turn < 1 or turn  > 2:
                raise InvalidInputs
            else:
                break
        except:
            print('Invalid input(s), Please try again.\n')
    othello.get_new_board(rows, columns, winning_state)
    othello.get_turn(turn)
    othello.print_board()
    return None

'''
This function checks the game state and prints whoever wins.
returns True if someone wins, else it returns false.
'''

def update_interface() -> bool:
    if othello.check_game_state() == 'B':
        print('Black user is winner!')
        return True
    elif othello.check_game_state() == 'W':
        print('White user is winner!')
        return True
    else:
        return False
    

##This function contains the calls for the turn and asks for the
##coordinates where to place the tile. It prints the board as well.

    
def user_turn() -> None:
    while True:
        try:
            othello.game_turn()
            row_input = int(input('Please enter the row number of where you would like to place piece: '))
            column_input = int(input('Please enter the column number of where you would like to place piece: '))
            potential = (row_input-1, column_input-1)
            if row_input < 0 or row_input > othello.input_rows or column_input < 0 or column_input > othello.input_columns:
                raise InvalidMoveError
            if othello.valid_move(potential) == False:
                raise InvalidMoveError
            else:
                break
        except:
            print('Invalid move, please try again.\n')      
    othello.print_board()
    return None


#This is the User interface that calls the start and the user turn.
#It contains the while loop for the user turn and will break when the
#update interface returns true for a winner, otherwise it will continue
#to loop. Change turn returns false and will break the while loop if both
#users are unable to make moves.

    
def interface() -> None:
    start()
    while True:
        user_turn()
        if update_interface() == True:
            break
        else:
            if othello.change_turn() == True:
                pass
            else:
                break
                
        
    
    


if __name__ == '__main__':
    interface()
