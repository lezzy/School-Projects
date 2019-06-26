import sockethandle
import functions

user_host = '128.195.6.182'
user_port = 4444

    
    
def move(connection):
    print('Starting new game!')
    newgamestate = functions.newgamestate()
    try:
        while True:
            if functions.game_turn(newgamestate) == 'R':
                print('''Red's turn!''')
                red_input = functions.user_input()
                newgamestate = functions.check_red(newgamestate, red_input)
                sockethandle.user_send(connection, red_input[0])
                if functions.check_game_progress(newgamestate) == True:
                    break
            elif functions.game_turn(newgamestate) == 'Y':
                print('''Yellow's turn!''')
                print(sockethandle.server_reply(connection))
                server_entry = sockethandle.server_reply(connection)
                if server_entry[:-2] == 'POP' or 'DROP':
                
                    yellow_input = functions.str_split(server_entry)
                    newgamestate = functions.check_yellow(newgamestate, yellow_input)
                    print(sockethandle.server_reply(connection))
                            
                    if functions.check_game_progress(newgamestate) == True:
                        break
                elif server_entry[:-4] == 'WINNER':
                    break

    except functions.connectfour.InvalidConnectFourMoveError:
        print('Invalid Connect Four move, please try again!')
    print("Game ended")
    

def net_interface():
    while True:
        try:
            username = str(input('''What is your username?(MUST HAVE NO SPACES/TABS): '''))
            for i in username:
                if i == ' ':
                    print('Invalid username, needs to have no spaces/tabs. Please try again')

            connection  = sockethandle.connect(user_host, user_port)
            sockethandle.startup(connection, username)
            move(connection)
        
        
        
        finally:
            sockethandle.close(connection)
            break

if __name__ == '__main__':
    net_interface()
    
    
    
            
            

