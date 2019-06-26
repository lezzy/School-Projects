import connectfour
from collections import namedtuple
import sockethandle

def print_board(connectfourgamestate: namedtuple) -> connectfour.ConnectFourGameState:
    current_board = connectfourgamestate.board
    for row in zip(*current_board):
        print(row)
    return connectfourgamestate

def valid_move_check(input_name: int, start_value: int, end_value: int) -> Exception:
    if input_name < start_value or input_name > end_value or input_name == '':
        print("Invalid move, please try again")
        raise connectfour.InvalidConnectFourMoveError
def str_split(user_input):
    length_input = len(user_input)
    number_column = user_input[(length_input - 1):]
    column = int(number_column)
    return (user_input, column)
    
def user_input():
    while True:
        user_str = input('''Please enter the corressponding action with column number ex. (DROP 3, POP 2):\n ''')
        user_str = user_str.upper()
        length_input = len(user_str)
        if length_input == 0:
            print('Invalid move, please try again.')
        else:
            return (str_split(user_str))

def check_yellow(newgamestate, user_str):
 
    yellow_turn = user_str
    valid_move_check(yellow_turn[1], 0, connectfour.BOARD_COLUMNS - 1)
    if (yellow_turn[0][:-2]) == 'DROP':
        newgamestate = connectfour.drop_piece(newgamestate, yellow_turn[1] - 1)
        return print_board(newgamestate)
    elif (yellow_turn[0][:-2]) == 'POP':
        newgamestate = connectfour.pop_piece(newgamestate, yellow_turn[1] - 1)
        return print_board(newgamestate)
    elif check_game_progress == True:
        return True
    
def check_red(newgamestate, user_str):

    red_turn = user_str
    valid_move_check(red_turn[1], 0, connectfour.BOARD_COLUMNS - 1)
    if (red_turn[0][:-2]) == 'DROP':
        newgamestate = connectfour.drop_piece(newgamestate, red_turn[1] - 1)
        return print_board(newgamestate)
    elif (red_turn[0][:-2]) == 'POP':
        newgamestate = connectfour.pop_piece(newgamestate, red_turn[1] - 1)
        return print_board(newgamestate)
    elif check_game_progress == True:
        return True
    
def newgamestate():
    return connectfour.new_game_state()

def game_turn(newgamestate):
    return newgamestate.turn

def check_game_progress(newgamestate):
    if connectfour.winning_player(newgamestate) == 'R':
        print('RED player has won! Congradulations!')
        return True
    elif connectfour.winning_player(newgamestate) == 'Y':
        print('YELLOW player has won! Congradulations!')
        return True
    else:
        return False

##def game_progress():
##        while True:
##            try:
##                if game_turn == 'R':
##                    print('''Red's turn!''')
##                    red_turn = user_str
##                    valid_move_check(red_turn[1], 0, connectfour.BOARD_COLUMNS - 1)
##                    if (red_turn[0][:-2]) == 'DROP':
##                        newgamestate = connectfour.drop_piece(newgamestate, red_turn[1] - 1)
##                        print_board(newgamestate)
##                    elif (red_turn[0][:-2]) == 'POP':
##                        newgamestate = connectfour.pop_piece(newgamestate, red_turn[1] - 1)
##                        print_board(newgamestate)
##                else:
##                    print('''Yellow's turn!''')
##                    yellow_turn = user_input()
##                    valid_move_check(yellow_turn[1], 0, connectfour.BOARD_COLUMNS - 1)
##                    if (yellow_turn[0][:-2]) == 'DROP':
##                        newgamestate = connectfour.drop_piece(newgamestate, yellow_turn[1] - 1)
##                        print_board(newgamestate)
##                    elif (yellow_turn[0][:-2]) == 'POP':
##                        newgamestate = connectfour.pop_piece(newgamestate, yellow_turn[1] - 1)
##                        print_board(newgamestate)
##                if connectfour.winning_player(newgamestate) == 'R':
##                    print('RED player has won! Congradulations!')
##                    break
##                elif connectfour.winning_player(newgamestate) == 'Y':
##                    print('YELLOW player has won! Congradulations!')
##                    break
##            except connectfour.InvalidConnectFourMoveError:
##                print('Invalid Connect Four move, please try again!')
##                break
##           ## except:
##                ##print('There was a unexpected error, please try agan.')
##                ##break
##        
##        if connectfour.winning_player(newgamestate) == 'R':
##            break
##        elif connectfour.winning_player(newgamestate) == 'Y':
##            break
##        
