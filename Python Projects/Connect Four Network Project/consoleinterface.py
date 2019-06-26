import connectfour
from collections import namedtuple

#Need to run this for better interface

def print_board(connectfourgamestate: namedtuple) -> connectfour.ConnectFourGameState:
    current_board = connectfourgamestate.board
    for row in zip(*current_board):
        print(row)
    return connectfourgamestate



def main():
    print('Starting a new game...!')
    newgamestate = connectfour.new_game_state()
##    connectfour.BOARD_ROWS = int(input('How many columns would you like to play with?: '))
##    connectfour.BOARD_COLUMNS = int(input('How many rows would you like to play with?: '))
    while True:
        if newgamestate.turn == 'R':
            red_turn = int(input('''Red's turn!\n Please enter the corresponding numbers for desired action\n 1.) Drop piece, 2.)Pop Piece: '''))
            if red_turn == 1:
                red_drop = int(input("Enter number of column to drop red piece from 0 to {}: ".format(connectfour.BOARD_COLUMNS - 1)))
                if red_drop > connectfour.BOARD_COLUMNS or red_drop < 0:
                    raise connectfour.InvalidConnectFourMoveError()
                else:
                    newgamestate = connectfour.drop_piece(newgamestate, red_drop)
                    print_board(newgamestate)
            else:
                red_pop = int(input('Enter column number to pop red piece from 0 to {}: '.format(connectfour.BOARD_COLUMNS - 1)))
                if red_pop > connectfour.BOARD_COLUMNS or red_pop < 0:
                    raise connectfour.InvalidConnectFourMoveError()
                else:
                    newgamestate = connectfour.pop_piece(newgamestate, red_pop)
                    print_board(newgamestate)
        else:
            yellow_turn = int(input('''Yellow's turn!\n Please enter the corresponding numbers for desired action\n 1.) Drop piece, 2.)Pop Piece: '''))
            if yellow_turn == 1:
                yellow_drop = int(input("Enter column number to drop yellow piece from 0 to {}: ".format(connectfour.BOARD_COLUMNS - 1))) 
                newgamestate = connectfour.drop_piece(newgamestate, yellow_drop)
                print_board(newgamestate)
            else:
                yellow_pop = int(input('Enter column number to pop yellow piece from 0 to {}: '.format(connectfour.BOARD_COLUMNS - 1)))
                newgamestate = connectfour.pop_piece(newgamestate, yellow_pop)
                print_board(newgamestate)
        if connectfour.winning_player(newgamestate) == 'R':
                print('RED player has won! Congradulations!')
                break
        elif connectfour.winning_player(newgamestate) == 'Y':
                print('YELLOW player has won! Congradulations!')
                break

        
    
    
    


if __name__ == '__main__':
    main()
