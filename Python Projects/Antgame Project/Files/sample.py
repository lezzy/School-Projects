from graphics import *
import random

# direction images, first array is for the black ant and the second array is for the red ant.
images = [["up.gif", "right.gif", "down.gif", "left.gif"], ["up2.gif", "right2.gif", "down2.gif", "left2.gif"]] 

# Initial the colors of rectangles, rectangles, candies, positions of ants, directions of ants, images of ants.
# D: colors of rectangles. 0 represents white, 1 represents black.
# M: matrix of rectangles.
# C: scores for candies. big candy is 50 and small cansy is 10.
# antPositions: position of the two ants.
# antDirections: directions of the two ants, default value is 0 (up).
# ants: images of the two ants.
# win: the game window.
def initial(D, M, C, antPositions, antScores, antDirections, ants, win):
    
    for i in range(20): # create 20 small candies
        smallX = random.randint(0,10)
        smallY = random.randint(0,10)
        C[smallX][smallY] = 10
    for i in range(4): # create 4 large candies
        largeX = random.randint(0,10)
        largeY = random.randint(0,10)
        C[largeX][largeY] = 50
        
    for i in range (11):
        m = [] # rectangle list
        for j in range (11):
            rec = Rectangle(Point(6 + 4 * i, 6 + 4 * j), Point(10 + 4 * i, 10 + 4 * j))
            if (C[i][j] == 50):
                candy = Image(Point(6 + 4 * i + 2, 6 + 4 * j + 2), "big_candy.gif")
                candy.draw(win)
            elif (C[i][j] == 10):
                candy = Image(Point(6 + 4 * i + 2, 6 + 4 * j + 2), "small_candy.gif")
                candy.draw(win)
            elif(random.randint(0, 1)==1):
                D[i][j] = 1
                rec.setFill("black")
            else:
                D[i][j] = 0
                rec.setFill("white")
            rec.draw(win)    
            m.append(rec)
        M.append(m)
        
    # undraw the red ant, if necessary.   
    if (len(ants) == 2 and antPositions[1][0] != -1):
        ants[1].undraw()
        
    # Positions of ants
    antPositions[0] = [5, 5] # center cell.
    antPositions[1] = [-1, -1] # initial cell [-1, -1], which is not on the field.

    # Ant images
    if (len(ants) == 0): # for first time execution.
        ants.append(Image(Point(6 + 4 * antPositions[0][0] + 2, 6 + 4 * antPositions[0][1] + 2),images[0][0]))
        ants[0].draw(win)        
    else: # for reset
        ants[0].undraw()
        ants[0] = Image(Point(6 + 4 * antPositions[0][0] + 2, 6 + 4 * antPositions[0][1] + 2), images[0][0])
        ants[0].draw(win)
        
    # Scores of ants
    antScores[0] = 0
    antScores[1] = 0
    
    # Directions of ants
    antDirections[0] = 0
    antDirections[1] = 0

# reset colors of rectangles, rectangles and candies.
def reset(D, M, C):
    for i in range (11):
        for j in range (11):
            D[i][j] = 0
            C[i][j] = 0
            M[i][j].undraw()
                    

    
def main():
    win = GraphWin("Ant war", 700, 600)
    win. setCoords(0, 0, 70, 60)
    win.setBackground("light green")

    #Initialize two dimesion arrays.
    #D records color of grids, M records rectangles, C for candies
    D = []
    M = []
    C = []

    #initial the grids
    for i in range(11):
        d = []
        c = []
        for j in range(11):
            d.append(0)
            c.append(0)
        D.append(d)
        C.append(c)

    #Positions of ants
    antPositions = [[5, 5],[-1, -1]]

    #Scores of ants
    antScores =[0, 0]
    antDirections = [0, 0]

    # Ant images
    ants = []
        
    initial(D, M, C, antPositions, antScores, antDirections, ants, win)        
    win.getMouse()
    win.close()
    
main()
