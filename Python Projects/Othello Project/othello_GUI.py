import tkinter
import othello_logic

DEFAULT_FONT= ('Helvetica', 20)
SMALL_FONT = ('Helvetica', 8)

othello = othello_logic.OthelloGameState()

##This class implements the start menu that appears
##in the beginning of the game. It will ask the user
##the details of how to start the game, such
##as the row number, column number, how to win,
##the set up of the board, and who goes first.

class StartInterface:

##The init compiles all of the windows that are needed
##for the start menu. It also creates a list of StringVar
##for the entry boxes. There is also a button made to destroy
##the start menu and call the game interface.

    def __init__(self):
        self._user_window = tkinter.Tk()
        menu_label = tkinter.Label(
            master = self._user_window, text = "Start Menu",
            font = DEFAULT_FONT)
        menu_label.grid(
            row = 0, column = 0, columnspan = 2, padx = 10, pady = 10,
            sticky = tkinter.N)
        list_entry = []
        text1 = tkinter.StringVar()
        text1.set('How many rows would you like on the board (EVEN integer 4 - 16):')
        list_entry.append(text1)
        text2 = tkinter.StringVar()
        text2.set('How many columns would you like on the board (EVEN integer 4 - 16):')
        list_entry.append(text2)
        text3 = tkinter.StringVar()
        text3.set('Which player will go first? Black(B) or White(W):')
        list_entry.append(text3)
        text4 = tkinter.StringVar()
        text4.set('Which disc on the top left corner in the start of game? Black(B) or White(W):')
        list_entry.append(text4)
        text5 = tkinter.StringVar()
        text5.set('How will the game be won: player with most discs(1) or least discs(2):')
        list_entry.append(text5)
        for entry_label in range(5):
            start_label = tkinter.Label(
                master = self._user_window, textvariable = list_entry[entry_label],
                font = SMALL_FONT)
            start_label.grid(
                row = entry_label + 1, column = 0, padx = 10, pady = 10,
                stick = tkinter.W)
        self.start_entry1 = tkinter.Entry(
            master = self._user_window, width = 20, font = DEFAULT_FONT)
        self.start_entry1.grid(
            row = 1, column = 2, padx = 10, pady = 1)
        self.start_entry2 = tkinter.Entry(
            master = self._user_window, width = 20, font = DEFAULT_FONT)
        self.start_entry2.grid(
            row = 2, column = 2, padx = 10, pady = 1)
        self.start_entry3 = tkinter.Entry(
            master = self._user_window, width = 20, font = DEFAULT_FONT)
        self.start_entry3.grid(
            row = 3, column = 2, padx = 10, pady = 1)
        self.start_entry4 = tkinter.Entry(
            master = self._user_window, width = 20, font = DEFAULT_FONT)
        self.start_entry4.grid(
            row = 4, column = 2, padx = 10, pady = 1)
        self.start_entry5 = tkinter.Entry(
            master = self._user_window, width = 20, font = DEFAULT_FONT)
        self.start_entry5.grid(
            row = 5, column = 2, padx = 10, pady = 1)
        button_frame = tkinter.Frame(master = self._user_window)
        button_frame.grid(
            row = 6, column = 2, padx = 10, pady = 10,
            sticky = tkinter.S + tkinter.E)
        ok_button = tkinter.Button(
            master = button_frame, text = 'Okay', font = DEFAULT_FONT,
            command = self._on_ok_button)
        ok_button.grid(
            row = 0, column = 0, padx = 10, pady = 10)
        
    def start(self):
        self._user_window.mainloop()

##This executes when the button is pressed. It will
##append all of the answers to the box into a list
##and then destroys the box. 

    def _on_ok_button(self):
        self.list_of_results = []
        self.list_of_results.append(self.start_entry1.get())
        self.list_of_results.append(self.start_entry2.get())
        self.list_of_results.append(self.start_entry3.get())
        self.list_of_results.append(self.start_entry4.get())
        self.list_of_results.append(self.start_entry5.get())
        self._user_window.destroy()
      
##This class pretty much implements the gaming interface
##and the logic. It also calls the StartInterface class.


class GameInterface:

##This init stores everything for the gaming window.
##It also calls configure when the window is changed
##so the gaming window can be adjusted. The init takes
##the results from the StartInterface call and calls
##othello methods to start the game.
    def __init__(self):
        x = StartInterface()
        x.start()
        self.list_entries = x.list_of_results
        self.B_score = 0
        self.W_score = 0
        self._game_window = tkinter.Tk()
        self._canvas = tkinter.Canvas(
            master = self._game_window,
            width = 100*int(self.list_entries[1]), height = 100*int(self.list_entries[0]) + 134,
            background = '#22b822')
        self._canvas.grid(
            row = 0, column = 0, padx = 0, pady = 0,
            sticky = tkinter.N + tkinter.S + tkinter.W + tkinter.E)
        self._game_window.rowconfigure(0, weight = 1)    
        self._game_window.columnconfigure(0, weight = 1)
        if self.list_entries[3] == 'B':
            othello.get_new_board_1(int(self.list_entries[0]), int(self.list_entries[1]),
                                    int(self.list_entries[4]))
        elif self.list_entries[3] == 'W':
            othello.get_new_board_2(int(self.list_entries[0]), int(self.list_entries[1]),
                                    int(self.list_entries[4]))

        if self.list_entries[2] == 'B':
            othello.get_turn(1)
        elif self.list_entries[2] == 'W':
            othello.get_turn(2)
        self._canvas.bind('<Configure>', self._on_canvas_resized)
        B_count = 0
        W_count = 0
        for i in range(othello.input_rows):
            for j in range(othello.input_columns):
                if othello.board[i][j] == 'B':
                    B_count = B_count + 1
                elif othello.board[i][j] == 'W':
                    W_count = W_count + 1
        self.B_score = B_count
        self.W_score = W_count
        self._score_board = tkinter.Label(
            master = self._game_window,
            text = 'Score:\nBlack:{}\nWhite:{}\n Turn:{}'.format(self.B_score, self.W_score, othello.turn),
            font = DEFAULT_FONT,
            background = 'Grey')
        self._score_board.grid(
            row = 0, column = 0,
            sticky = tkinter.S + tkinter.W + tkinter.E)
        
        self._canvas.bind('<ButtonRelease-1>', self._on_mouse_down)
     
    def start(self):
        self._game_window.mainloop()

##This method is called when the window is resized. It will redraw
##everything in according to how the window was resized with
##appropiate sizing and ratios.
        
    def _on_canvas_resized(self, event: tkinter.Event):
        self._canvas.delete(tkinter.ALL)
        canvas_width = self._canvas.winfo_width()
        canvas_height = self._canvas.winfo_height() - 130
        w_ratio = canvas_width/int(self.list_entries[1])
        h_ratio = canvas_height/int(self.list_entries[0])
        for i in range(int(self.list_entries[0]) + 1):
            self._canvas.create_line(0, 0 + (i * h_ratio), canvas_width, 0 + (i * h_ratio))
        for j in range(int(self.list_entries[1]) + 1):
            self._canvas.create_line(0 + (j * w_ratio), 0, 0 + (j * w_ratio), canvas_height)
        self._draw_board()


##This method is called when the mouse is clicked
##on the game board. It will redraw everything and
##checks the mouse click for its coordinates. It will
##then follow the game logic and draw in empty spaces.
##This is also where the scoreboard is created as well,
##as when you click, this draws the entire game board again.

    def _on_mouse_down(self, event: tkinter.Event):
        self._canvas.delete(tkinter.ALL)
        draw_tile = False
        canvas_width = self._canvas.winfo_width()
        canvas_height = self._canvas.winfo_height() - 130
        w_ratio = canvas_width/int(self.list_entries[1])
        h_ratio = canvas_height/int(self.list_entries[0])
        if event.x > int(self.list_entries[1]) * 100:
            x_dif = canvas_width - int(self.list_entries[1]) * 100
            event.x = event.x - x_dif
        if event.y > int(self.list_entries[0]) * 100:
            y_dif = canvas_height - int(self.list_entries[0]) * 100
            event.y = event.y - y_dif
        x = str(event.x)
        y = str(event.y)
        if event.x < 0:
            event.x = 0
        elif int(x[1:]) > 50:
                event.x = event.x - 50
        if event.y < 0:
            event.y = 0
        elif int(y[1:]) > 50:
            event.y = event.y - 50
        user_x = round(event.x, -(int(len(x)) - 1)) 
        user_y = round(event.y, -(int(len(y)) - 1)) 
        tile_x = user_x//100
        tile_y = user_y//100
        potential = (tile_y, tile_x)
        if tile_x < 0 or tile_x > int(self.list_entries[1]) or tile_y < 0 or tile_y > int(self.list_entries[0]):
            draw_tile = False
        elif othello.valid_move(potential) == False:
            draw_tile = False
        else:
            draw_tile = True
        if draw_tile == True:
            if othello.turn == 'B':
                self._canvas.create_oval(
                    user_x, user_y, user_x + 100, user_y + 100, fill = 'Black')
            elif othello.turn == 'W':
                self._canvas.create_oval(
                    user_x, user_y, user_x + 100, user_y + 100, fill = 'White')
            othello.change_turn()
            draw_tile = False
            
##This is where the game will check the status
##to see if there's a winner or not. If there is,
##then it will make a new window that pops up
##declaring the game winner/status. When Okay button
##is hit, it will close everything.

        if self._check_status() == True or othello.change_turn == False:
            self.win_window = tkinter.Toplevel()
            win_label = tkinter.Label(
                master = self.win_window,
                textvariable = self.win_text,
                font = DEFAULT_FONT)
            win_label.grid(row = 0, column = 0,
                           columnspan = 3,
                           padx = 10, pady = 10,
                           sticky = tkinter.N)
            exit_button = tkinter.Button(
                master = self.win_window, text = 'Okay', font = DEFAULT_FONT,
                command = self._close_windows)
            exit_button.grid(
                row = 1, column = 0,
                padx = 10, pady = 10,
                sticky = tkinter.S)
        self._draw_board()
        B_count = 0
        W_count = 0
        for i in range(othello.input_rows):
            for j in range(othello.input_columns):
                if othello.board[i][j] == 'B':
                    B_count = B_count + 1
                elif othello.board[i][j] == 'W':
                    W_count = W_count + 1
        self.B_score = B_count
        self.W_score = W_count
        self._score_board = tkinter.Label(
            master = self._game_window,
            text = 'Score:\nBlack:{}\nWhite:{}\n Turn:{}'.format(self.B_score, self.W_score, othello.turn),
            font = DEFAULT_FONT,
            background = 'Grey')
        self._score_board.grid(
            row = 0, column = 0,
            sticky = tkinter.S + tkinter.W + tkinter.E)

##This method redraws the board.
        
    def _draw_board(self):
        self._canvas.delete(tkinter.ALL)
        canvas_width = self._canvas.winfo_width()
        canvas_height = self._canvas.winfo_height() - 130
        w_ratio = canvas_width/int(self.list_entries[1])
        h_ratio = canvas_height/int(self.list_entries[0])
        for i in range(int(self.list_entries[0]) + 1):
            self._canvas.create_line(0, 0 + (i * h_ratio), canvas_width, 0 + (i * h_ratio))
        for j in range(int(self.list_entries[1]) + 1):
            self._canvas.create_line(0 + (j * w_ratio), 0, 0 + (j * w_ratio), canvas_height)
        for i in range(othello.input_rows):
            for j in range(othello.input_columns):
                if othello.board[i][j] == 'B':
                    self._canvas.create_oval(
                        j * w_ratio , i * h_ratio , (j * w_ratio) + w_ratio ,
                        (i * h_ratio) + h_ratio , fill = 'Black')
                elif othello.board[i][j] == 'W':
                    self._canvas.create_oval(
                        j * w_ratio , i * h_ratio , (j * w_ratio) + w_ratio ,
                        (i * h_ratio) + h_ratio , fill = 'White')


##This method is used to check on the game status
##through the othello game logic file. It will
##set the text for the status of the winner and return
##True if there is a winner. Otherwise, it will return
##false.

    def _check_status(self):
        self.win_text = tkinter.StringVar()
        if othello.check_game_state() == 'B':
            self.win_text.set('Black user is winner!')
            return True
        elif othello.check_game_state() == 'W':
            self.win_text.set('White user is winner!')
            return True
        elif othello.check_game_state() == 'T':
            self.win_text.set('The game is a TIE!')
            return True
        else:
            return False

##This method is called to close all existing windows
##that were made.
        
    def _close_windows(self):
        self.win_window.destroy()
        self._game_window.destroy()

if __name__ == '__main__':
    GameInterface().start()
            
