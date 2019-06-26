import functions


def user_interface():
    print('Starting new game!')
    newgamestate = functions.newgamestate()
    try:
        while True:
            if functions.game_turn(newgamestate) == 'R':
                print('''Red's turn!''')
                newgamestate = functions.check_red(newgamestate, functions.user_input())
                if functions.check_game_progress(newgamestate) == True:
                    break
            elif functions.game_turn(newgamestate) == 'Y':
                print('''Yellow's turn!''')
                newgamestate = functions.check_yellow(newgamestate, functions.user_input())
                if functions.check_game_progress(newgamestate) == True:
                    break
    except functions.connectfour.InvalidConnectFourMoveError:
        print('Invalid Connect Four move, please try again!')
    print('Game ended')
        


if __name__ == '__main__':
    user_interface()
