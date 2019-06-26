class InvalidMoveError(Exception):
    pass
class InvalidInputs(Exception):
    pass
class InvalidRowInput(Exception):
    pass
class InvalidColumnInput(Exception):
    pass

class OthelloGameState:

## This contains all of the variables that will be used through the methods
## to check on the board. Most will be passed through all the functions. It
## initializes all of the variables that will be used.

    def __init__(self):
        self.board = []
        self.B_list = []
        self.W_list = []
        self.valid_list = []
        self.turn = ''
        self.win_state = ''
        self.input_rows = 0
        self.move_state = False
        self.past_valid = []
        self.instant_win = False

## This method prints the board. Uses a series of for-loops to print the
## labels for the x and y axis.

    def print_board(self):
        title = '  '
        for i in range(self.input_columns):
            title += str(i+1) + ' '
        print(title)
        printing_list = []
        printing_str = ''
        for i in range(self.input_rows):
            for j in range(self.input_columns):
                printing_str += self.board[i][j] + ' '
            printing_list.append(printing_str)
            printing_str = ''
        for y in range(self.input_rows):
            print(y  + 1, printing_list[y])
        print('\n')
        return None

## This method initializes the board and makes the new board when called.

    def get_new_board_1(self, rows: int, columns: int, winning_state: int) -> None:
        self.input_rows = rows
        self.input_columns = columns
        self.win_state = winning_state
        for i in range(self.input_rows):
            self.board.append([])
            for j in range(self.input_columns):
                self.board[-1].append('_')
        self.board[int(self.input_rows/2 - 1)][int(self.input_columns/2 - 1)] = 'B'
        self.board[int(self.input_rows/2)][int(self.input_columns/2)] = 'B'
        self.board[int(self.input_rows/2) - 1][int(self.input_columns/2)] = 'W'
        self.board[int(self.input_rows/2)][int(self.input_columns/2 - 1)] = 'W'
        return None
    def get_new_board_2(self, rows: int, columns: int, winning_state: int) -> None:
        self.input_rows = rows
        self.input_columns = columns
        self.win_state = winning_state
        for i in range(self.input_rows):
            self.board.append([])
            for j in range(self.input_columns):
                self.board[-1].append('_')
        self.board[int(self.input_rows/2 - 1)][int(self.input_columns/2 - 1)] = 'W'
        self.board[int(self.input_rows/2)][int(self.input_columns/2)] = 'W'
        self.board[int(self.input_rows/2) - 1][int(self.input_columns/2)] = 'B'
        self.board[int(self.input_rows/2)][int(self.input_columns/2 - 1)] = 'B'

## This method is used inside this file and updates the lists.

    def _update_lists(self) -> None:
        for x in range(self.input_rows):
            for y in range(self.input_columns):
                if self.board[x][y] == 'B':
                    self.B_list.append((y,x))
                elif self.board[x][y] == 'W':
                    self.W_list.append((y,x))
        return None

## This method is used inside the file to clear the lists to be used again.
## This makes sure the lists don't have any repeats.

    def _clear_list(self) -> None:
        self.B_list = []
        self.W_list = []
        self.path_list = []
        list_to_use = []
        other_list = []
        return None

## This method is used to initialize the turn from user inputs.

    def get_turn(self, game_turn: int) -> None:
        if game_turn == 1:
            self.turn = 'B'
        elif game_turn == 2:
            self.turn = 'W'
        return None

## This method changes the turn in the game so the next turn would be
## the next player. It also checks if both players have not been able to make
## any moves in the past turns it will set instant_win to True to end the game.

    def change_turn(self) -> bool:
        if self.valid_list == [] and self.past_valid == []:
            self.instant_win = True
            return False
        elif self.turn == 'B':
            self.turn = 'W'
            return True
        elif self.turn == 'W':
            self.turn = 'B'
            return True

## This method is used to check on the game state. It checks if the amount of
## Blacks and Whites fills up the entire board. It also checks if the
## instant_win is True or if either the number of Blacks or Whites is 0.

    def check_game_state(self) -> None:
        Black_list = []
        White_list = []
        for x in range(self.input_rows):
            for y in range(self.input_columns):
                if self.board[x][y] == 'B':
                    Black_list.append((y,x))
                elif self.board[x][y] == 'W':
                    White_list.append((y,x))
        if (len(White_list) + len(Black_list) == (self.input_rows*self.input_columns) or
            self.instant_win == True or len(Black_list) == 0 or len(White_list) == 0):
            if self.win_state == 1:
                if len(Black_list) > len(White_list):
                    return 'B'
                elif len(Black_list) < len(White_list):
                    return 'W'
                else:
                    return 'T'
            else:
                if len(Black_list) > len(White_list):
                    return 'W'
                elif len(Black_list) < len(White_list):
                    return 'B'
                else:
                    return 'T'
        else:
            pass
        return None

## This method prints the statement for who's turn it is for the user.

    def game_turn(self) -> None:
        if self.turn == 'B':
            print('Blacks turn!')
        else:
            print('Whites turn!')
        return None

## This method takes in the potential coordinates of where the user would like
## to place to tile. It creates a valid_list for the list of valid moves 
## to make. It uses a counter to check the board if there's a blank or 
## a black or white tile.

    def valid_move(self, potential: tuple):
        self.move_state = False
        a = 0
        self._clear_list()
        self._update_lists()
        self.past_valid = self.valid_list
        self.valid_list = []
        if self.turn == 'B':
            list_to_use = self.B_list
            other_list = self.W_list
        else:
            list_to_use = self.W_list
            other_list = self.B_list
        for i in range(len(list_to_use)):
            for j in range(len(other_list)):
                if list_to_use[i][1] == other_list[j][1]:
                    if list_to_use[i][0] + 1 == other_list[j][0]:
                        while True:
                            if other_list[j][0] + a >= 0 and other_list[j][0] + a < self.input_columns:
                                if self.board[other_list[j][1]][other_list[j][0] + a] == self.turn:
                                    break
                                elif self.board[other_list[j][1]][other_list[j][0] + a] == '_':
                                    self.valid_list.append((list_to_use[i][1], other_list[j][0] + a))
                                    break
                                else:
                                    a = a + 1
                            else:
                                break
                        a = 0
                    elif list_to_use[i][0] - 1 == other_list[j][0]:
                        while True:
                            if other_list[j][0] - a >= 0 and other_list[j][0] - a < self.input_columns:
                                if self.board[other_list[j][1]][other_list[j][0] - a] == self.turn:
                                    break
                                elif self.board[other_list[j][1]][other_list[j][0] - a] == '_':
                                    self.valid_list.append((list_to_use[i][1], other_list[j][0] - a))
                                    break
                                else:
                                    a = a + 1
                            else:
                                break
                        a = 0
                elif list_to_use[i][0] == other_list[j][0]:
                    if list_to_use[i][1] + 1 == other_list[j][1]:
                        while True:
                            if other_list[j][1] + a >= 0 and other_list[j][1] + a < self.input_rows:
                                if self.board[other_list[j][1] + a][other_list[j][0]] == self.turn:
                                    break
                                elif self.board[other_list[j][1] + a][other_list[j][0]] == '_':
                                    self.valid_list.append((other_list[j][1] + a, list_to_use[i][0]))
                                    break
                                else:
                                    a = a + 1
                            else:
                                break
                        a = 0
                    elif list_to_use[i][1] - 1 == other_list[j][1]:
                        while True:
                            if other_list[j][1] - a >= 0 and other_list[j][1] - a < self.input_rows:
                                if self.board[other_list[j][1] - a][other_list[j][0]] == self.turn:
                                    break
                                elif self.board[other_list[j][1] - a][other_list[j][0]] == '_':
                                    self.valid_list.append((other_list[j][1] - a, list_to_use[i][0]))
                                    break
                                else:
                                    a = a + 1
                            else:
                                break
                        a = 0
                elif list_to_use[i][0] - 1 == other_list[j][0] and list_to_use[i][1] - 1 == other_list[j][1]:
                    while True:
                        if (other_list[j][0] - a >= 0 and other_list[j][0] - a < self.input_columns and
                            other_list[j][1] - a >= 0 and other_list[j][1] - a < self.input_rows):
                            if self.board[other_list[j][1] - a][other_list[j][0] - a] == self.turn:
                                break
                            elif self.board[other_list[j][1] - a][other_list[j][0] - a] == '_':
                                self.valid_list.append((other_list[j][1] - a, other_list[j][0] - a))
                                break
                            else:
                                a = a + 1
                        else:
                            self.valid_list.append((other_list[j][1] - a + 1, other_list[j][0] - a + 1))
                            break
                    a = 0
                elif list_to_use[i][0] + 1 == other_list[j][0] and list_to_use[i][1] + 1 == other_list[j][1]:
                    while True:
                        if (other_list[j][0] + a >= 0 and other_list[j][0] + a < self.input_columns and
                            other_list[j][1] + a >= 0 and other_list[j][1] + a < self.input_rows):
                            if self.board[other_list[j][1] + a][other_list[j][0] + a] == self.turn:
                                break
                            elif self.board[other_list[j][1] + a][other_list[j][0] + a] == '_':
                                self.valid_list.append((other_list[j][1] + a, other_list[j][0] + a))
                                break
                            else:
                                a = a + 1
                        else:
                            self.valid_list.append((other_list[j][1] + a - 1, other_list[j][0] + a - 1))
                            break
                    a = 0
                elif list_to_use[i][0] + 1 == other_list[j][0] and list_to_use[i][1] - 1 == other_list[j][1]:
                    while True:   
                        if (other_list[j][0] + a >= 0 and other_list[j][0] + a < self.input_columns and
                            other_list[j][1] - a >= 0 and other_list[j][1] - a < self.input_rows):
                            if self.board[other_list[j][1] - a][other_list[j][0] + a] == self.turn:
                                break
                            elif self.board[other_list[j][1] - a][other_list[j][0] + a] == '_':
                                self.valid_list.append((other_list[j][1] - a, other_list[j][0] + a))
                                break
                            else:
                                a = a + 1
                        else:
                            self.valid_list.append((other_list[j][1] - a + 1, other_list[j][0] + a - 1))
                            break
                    a = 0
                elif list_to_use[i][0] - 1 == other_list[j][0] and list_to_use[i][1] + 1 == other_list[j][1]:
                    while True:
                        if (other_list[j][0] - a >= 0 and other_list[j][0] - a < self.input_columns and
                            other_list[j][1] + a >= 0 and other_list[j][1] + a < self.input_rows):
                            if self.board[other_list[j][1] + a][other_list[j][0] - a] == self.turn:
                                break
                            elif self.board[other_list[j][1] + a][other_list[j][0] - a] == '_':
                                self.valid_list.append((other_list[j][1] + a, other_list[j][0] - a))
                                break
                            else:
                                a = a + 1
                        else:
                            self.valid_list.append((other_list[j][1] + a - 1, other_list[j][0] - a + 1))
                            break
                    a = 0

## This checks if the valid lists is 0 or empty, then it will
## automatically exit and return True, going to the next turn.
## Otherwise, it will call flip_path and pass through the potential
## tuple. Otherwise if the tuple of potential coordinates doesn't match
## with the list of valid moves, then it returns false.
        if len(self.valid_list) == 0:
            return True
        for i in range(len(self.valid_list)):
            if self.valid_list[i] == potential:
                self.move_state = True
                self.flip_path(potential)    
            else:
                self.move_state == False
            
        return self.move_state


## This method determines with places on the board to flip to the
## opposite turn. It first checks around the potential tuple's coordinates
## and see's if there's an opposite tile near it, then it appends it into
## a list that will check if it can be flipped.

    
    def flip_path(self, potential: tuple) -> None:
        self.board[potential[0]][potential[1]] = self.turn
        flip = []
        flipping_list = []
        temp_list = []  
        if potential[0]-1 >= 0 and potential[0]-1 < self.input_columns:
            if self.board[potential[0]-1][potential[1]] != self.turn and self.board[potential[0]-1][potential[1]] != '_':
                flip.append((potential[0]-1, potential[1]))
        if potential[0]+1 >= 0 and potential[0]+1 < self.input_columns:
            if self.board[potential[0]+1][potential[1]] != self.turn and self.board[potential[0]+1][potential[1]] != '_': 
                flip.append((potential[0]+1, potential[1]))
        if potential[1]-1 >= 0 and potential[1]-1 < self.input_rows:
            if self.board[potential[0]][potential[1]-1] != self.turn and self.board[potential[0]][potential[1]-1] != '_':
                flip.append((potential[0], potential[1]-1))
        if potential[1]+1 >= 0  and potential[1]+1 < self.input_rows:
            if self.board[potential[0]][potential[1]+1] != self.turn and self.board[potential[0]][potential[1]+1]!= '_':
                flip.append((potential[0], potential[1]+1))
        if potential[0]+1 >= 0 and potential[0]+1 < self.input_columns and potential[1]+1 >= 0  and potential[1]+1 < self.input_rows:
            if self.board[potential[0]+1][potential[1]+1] != self.turn and self.board[potential[0]+1][potential[1]+1] != '_':
                flip.append((potential[0]+1, potential[1]+1))
        if potential[0]+1 >= 0 and potential[0]+1 < self.input_columns and potential[1]-1 >= 0 and potential[1]-1 < self.input_rows:
            if self.board[potential[0]+1][potential[1]-1] != self.turn and self.board[potential[0]+1][potential[1]-1] != '_':
                flip.append((potential[0]+1, potential[1]-1))
        if potential[0]-1 >= 0 and potential[0]-1 < self.input_columns and potential[1]+1 >= 0  and potential[1]+1 < self.input_rows:
            if self.board[potential[0]-1][potential[1]+1] != self.turn and self.board[potential[0]-1][potential[1]+1] != '_':
                flip.append((potential[0]-1, potential[1]+1))
        if potential[0]-1 >= 0 and potential[0]-1 < self.input_columns and potential[1]-1 >= 0 and potential[1]-1 < self.input_rows:
            if self.board[potential[0]-1][potential[1]-1] != self.turn and self.board[potential[0]-1][potential[1]-1] != '_':
                flip.append((potential[0]-1, potential[1]-1))
        for element in flip:
            x_dif = potential[0] - element[0]
            y_dif = potential[1] - element[1]
            b = 0
            a = 0

## This checks the list of potential tiles that can be flipped. It there's
## the original tile after it, then it flips. However, If there's a
## blank afterwards then it doesn't flip it. It uses a temp_list to see
## the next tile is good or not to flip. Especially when there's more
## than one tile that can be flipped.

            while True:
                if (element[0] - x_dif*b >= 0 and element[0] - x_dif*b <= self.input_columns and
                    element[1] - y_dif*b >= 0 and element[1] - y_dif*b <= self.input_rows):
                    if self.board[element[0] - x_dif*b][element[1] - y_dif*b] == '_' or self.board[element[0] - x_dif*b][element[1] - y_dif*b] == self.turn:
                        break
                    else:
                        while True:
                            a = b
                            if (element[0] - (x_dif*(b+1)) >= 0 and element[0] - (x_dif*(b+1)) < self.input_columns and
                                element[1] - (y_dif*(b+1)) >= 0 and element[1] - (y_dif*(b+1)) < self.input_rows):
                                if self.board[element[0] - (x_dif*(b+1))][element[1] - (y_dif*(b+1))] != self.turn and (
                                    self.board[element[0] - (x_dif*(b+1))][element[1] - (y_dif*(b+1))] != '_'):
                                    temp_list.append((element[0] - x_dif*b, element[1] - y_dif*b))
                                    b = b + 1
                                elif self.board[element[0] - (x_dif*(b+1))][element[1] - (y_dif*(b+1))] == self.turn :
                                    temp_list.append((element[0] - x_dif*b, element[1] - y_dif*b))
                                    for i in range(len(temp_list)):
                                        flipping_list.append(temp_list[i])
                                    temp_list = []
                                    b = a
                                    break
                                elif self.board[element[0] - (x_dif*(b+1))][element[1] - (y_dif*(b+1))] == '_':
                                    temp_list = []
                                    b = a
                                    break
                            else:
                                temp_list = []
                                b = b + 1
                                break
                    b = b + 1
                else:
                    break
        for flipping in flipping_list:
            self.board[flipping[0]][flipping[1]] = self.turn
        b = 1
        flipping_list = []
        self.past_valid = self.valid_list
        return None
        
        
            
            
