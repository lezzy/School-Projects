# Lesmond Chow
# 17412911
# EECS 12
# Hw6.py
# This program designs an Ant game between a Red ant and a Black ant. They race for candy and whoever at the end who got the most candy wins.



from graphics import *
import random
from time import sleep

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

def setAnt(C, ants, antPositions, antDirections, win):
    m2 = win.getMouse()
    if 6 <= m2.x <= 50 and 6 <= m2.y <= 50:
        antPositions[1][0] = int((m2.x-2)//4-1)
        antPositions[1][1] = int((m2.y-2)//4-1)
        
        if antPositions[1] == antPositions[0] or C[antPositions[1][0]][antPositions[1][1]] == 10 or C[antPositions[1][0]][antPositions[1][1]] == 50:
            return
        if (len(ants) == 1):
            ants.append(Image(Point(6 + 4 * antPositions[1][0] + 2, 6 + 4 * antPositions[1][1] + 2), images[1][0]))
            ants[1].draw(win)

        else:
            ants[1] = Image(Point(6 + 4 * antPositions[1][0] + 2, 6 + 4 * antPositions[1][1] + 2), images[1][0])
            ants[1].draw(win)
            
    else:
        return

        
def getDirection(color, oldDirection):
    if color == 1:
        newDirection = oldDirection - 1 
        return newDirection
    elif color == 0:
        if oldDirection == 3:
            oldDirection = -1
        newDirection = oldDirection + 1
        return newDirection

def antMove(index, D, M, C, antPositions, antDirections, antScores, ants, win):
    aPosx = antPositions[index][0]
    aPosy = antPositions[index][1]
    aDirection = antDirections[index]
    rectcolor = D[aPosx][aPosy]
    
    if aDirection == 0:
        aPosy = aPosy + 1

    elif aDirection == 1:
        aPosx = aPosx + 1

    elif aDirection == 2:
        aPosy = aPosy - 1

    elif aDirection == 3:
        aPosx = aPosx - 1

    if rectcolor == 0:
        rectcolor = 1
        M[aPosx][aPosy].setFill("black")

    elif rectcolor == 1:
        rectcolor = 0
        M[aPosx][aPosy].setFill("white")

    antDirections[index] = getDirection(rectcolor, aDirection)

    antPositions[index][0] = aPosx
    antPositions[index][1] = aPosy

    ants[index].undraw()
    ants[index] = Image(Point(6 + 4 * antPositions[index][0] + 2, 6 + 4 * antPositions[index][1] + 2), images[index][antDirections[index]])
    ants[index].draw(win)

    if C[antPositions[index][0]][antPositions[index][1]] == 10:
        antScores[index] = antScores[index] + 10
    elif C[antPositions[index][0]][antPositions[index][1]] == 50:
        antScores[index] = antScores[index] + 50


def setDirection(ants, antPositions, antDirections, win):
    m3 = win.getMouse()
    if (m3.x-2)//4-1 < antPositions[1][0]:
        ants[1].undraw()
        antDirections[1] = 3
        ants[1] = Image(Point(6 + 4 * antPositions[1][0] + 2, 6 + 4 * antPositions[1][1] + 2), images[1][antDirections[1]])
        ants[1].draw(win)

    elif (m3.x-2)//4-1 > antPositions[1][0]:
        ants[1].undraw()
        antDirections[1] = 1
        ants[1] = Image(Point(6 + 4 * antPositions[1][0] + 2, 6 + 4 * antPositions[1][1] + 2), images[1][antDirections[1]])
        ants[1].draw(win)

    elif (m3.y-2)//4-1 < antPositions[1][1]:
        ants[1].undraw()
        antDirections[1] = 2
        ants[1] = Image(Point(6 + 4 * antPositions[1][0] + 2, 6 + 4 * antPositions[1][1] + 2), images[1][antDirections[1]])
        ants[1].draw(win)

    elif (m3.y-2)//4-1 > antPositions[1][1]:
        ants[1].undraw()
        antDirections[1] = 0
        ants[1] = Image(Point(6 + 4 * antPositions[1][0] + 2, 6 + 4 * antPositions[1][1] + 2), images[1][antDirections[1]])
        ants[1].draw(win)

# reset colors of rectangles, rectangles and candies.
def reset(D, M, C):
    for i in range (11):
        for j in range (11):
            D[i][j] = 0
            C[i][j] = 0
            M[i][j].undraw()

def drawButton(corner1, corner2, text, win):
    button = Rectangle(corner1, corner2)
    button.draw(win)
    centerx = (corner1.x + corner2.x)/2
    centery = (corner1.y + corner2.y)/2
    text=Text(Point(centerx, centery), text)
    text.setSize(15)
    text.draw(win)

def clickButton(clickpt, corner1, corner2):
    if corner1.x < clickpt.x < corner2.x and corner2.y < clickpt.y < corner1.y:
        return True
    else:
        return False
    

    
def main():
    win = GraphWin("Ant war", 700, 600)
    win. setCoords(0, 0, 70, 60)
    win.setBackground("light green")

    title1 = Text(Point(18, 55), "Lets start an ant war!")
    title1.setSize(20)
    title1.setFill("Blue")
    title1.setStyle("bold")
    title1.draw(win)

    redscore = Text(Point(55, 58), "Red Score:")
    redscore.setSize(20)
    redscore.setFill("Blue")
    redscore.draw(win)

    blackscore = Text(Point(55, 55), "Black Score:")
    blackscore.setSize(20)
    blackscore.setFill("Blue")
    blackscore.draw(win)
                       

    drawButton(Point(55, 46), Point(67, 40), "Reset", win)

    drawButton(Point(55, 38), Point(67, 32), "Set Ant", win)

    drawButton(Point(55, 30), Point(67, 24), "Set Direction", win)

    drawButton(Point(55, 22), Point(67, 16), "Start", win)

    drawButton(Point(55, 14), Point(67, 8), "Quit", win)

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

    redscore1 = Text(Point(65, 57.8), antScores[1])
    redscore1.setFill("Red")
    redscore1.setSize(20)
    redscore1.draw(win)

    blackscore1 = Text(Point(65, 54.8), antScores[0])
    blackscore1.setFill("Red")
    blackscore1.setSize(20)
    blackscore1.draw(win)
    count = 0

    while True:
        m1 = win.getMouse()
        if clickButton(m1, Point(55, 46), Point(67, 40)) == True:
            reset(D, M, C)
            initial(D, M, C, antPositions, antScores, antDirections, ants, win)

        elif clickButton(m1, Point(55, 38), Point(67, 32)) == True:
            setAnt(C, ants, antPositions, antDirections, win)

        elif clickButton(m1, Point(55, 30), Point(67, 24)) == True:
            setDirection(ants, antPositions, antDirections, win)

        elif clickButton(m1, Point(55, 22), Point(67, 16)) == True:
            while True:
                count = count + 1
                if count == 50:
                    break
                if 6 + 4 * antPositions[1][0] + 2 == 50 and antDirections[1] == 1:
                    break
                if 6 + 4 * antPositions[0][0] + 2 == 50 and antDirections[0] == 1:
                    break
                if 6 + 4 * antPositions[1][1] + 2 == 50 and antDirections[1] == 0:
                    break
                if 6 + 4 * antPositions[0][1] + 2 == 50 and antDirections[0] == 0:
                    break
                if 6 + 4 * antPositions[1][0] + 2 == 0 and antDirections[1] == 3:
                    break
                if 6 + 4 * antPositions[0][0] + 2 == 0 and antDirections[0] == 3:
                    break
                if 6 + 4 * antPositions[1][1] + 2 == 0 and antDirections[1] == 2:
                    break
                if 6 + 4 * antPositions[0][1] + 2 == 0 and antDirections[0] == 2:
                    break
                

                antMove(1, D, M, C, antPositions, antDirections, antScores, ants, win)
                antMove(0, D, M, C, antPositions, antDirections, antScores, ants, win)
            

                redscore1.undraw()
                redscore1 = Text(Point(65, 57.8), antScores[1])
                redscore1.setFill("Red")
                redscore1.setSize(20)
                redscore1.draw(win)

                blackscore1.undraw()
                blackscore1 = Text(Point(65, 54.8), antScores[0])
                blackscore1.setFill("Red")
                blackscore1.setSize(20)
                blackscore1.draw(win)

                sleep(0.5)
                
            
        elif clickButton(m1, Point(55, 14), Point(67, 8)) == True:
            break

        if antScores[0] > antScores[1]:
            title1.undraw()
            title2 = Text(Point(18, 55), "You Lose")
            title2.setSize(20)
            title2.setFill("Blue")
            title2.setStyle("bold")
            title2.draw(win)
        elif antScores[1] > antScores[0]:
            title1.undraw()
            title2 = Text(Point(18, 55), "You Win!")
            title2.setSize(20)
            title2.setFill("Blue")
            title2.setStyle("bold")
            title2.draw(win)
    win.close()
main()

