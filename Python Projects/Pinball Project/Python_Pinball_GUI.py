# Hw4.py
# Lesmond Chow
# 17412911
# EECS 12
# This program creates a pinball machine where the pinball must land within the basket to win.
# 11/3/11

from graphics import*
import math
from time import sleep
def main():
    myWin = GraphWin("Pinball Machine", 800, 600)
    myWin.setCoords(0, 0, 800, 600)
    
    myCirc = Circle(Point(400, 100), 50)
    myCirc.draw(myWin)
    myCirc.setFill("Black")
    
    myRect = Polygon(Point(200, 100), Point(200, 500), Point(600, 500), Point(600, 100))
    myRect.draw(myWin)
    myRect.setFill("Yellow")

    p1 = Point(350, 400)
    p2 = Point(350, 350)
    p3 = Point(450, 350)
    p4 = Point(450, 400)
    L1 = Line(p1, p2)
    L2 = Line(p2, p3)
    L3 = Line(p3, p4)
    L1.draw(myWin)
    L2.draw(myWin)
    L3.draw(myWin)

    pc = Point(400, 100)
    r = 10
    pinball = Circle(pc, r)
    pinball.draw(myWin)
    pinball.setFill("Red")

    p5 = Point(650, 100)
    p6 = Point(650, 150)
    p7 = Point(750, 150)
    p8 = Point(750, 100)
    button = Polygon(p5, p6, p7, p8)
    button.draw(myWin)
    fire = Text(Point(700, 125), "Fire")
    fire.draw(myWin)
    fire.setTextColor("Blue")
    fire.setSize(20)

    for i in range(5):
        if i > 0:
            title3.undraw()
        title1 = Text(Point(400, 570), "Click to set the direction and")
        title1.draw(myWin)
        title1.setSize(15)
        title1.setStyle("bold")
        title2 = Text(Point(405, 550), "hit the Fire button to shoot.")
        title2.draw(myWin)
        title2.setSize(15)
        title2.setStyle("bold")
        pinball.undraw()
        pc = Point(400, 100)
        pinball = Circle(pc, r)
        pinball.setFill("Red")
        pinball.draw(myWin)
        
        m1 = myWin.getMouse()
        p1 = Point(pc.x*2-m1.x, pc.y*2-m1.y)
        Line1 = Line(pc, p1)
        Line1.draw(myWin)
        Line1.setArrow("last")
        Line1.setFill("Red")
        
        m2 = myWin.getMouse()
        Line1.undraw()
        if 650<m2.x<750 and 100<m2.y<150:
            dis = math.sqrt((pc.x-m1.x)**2+(pc.y-m1.y)**2)
            Mag = dis/50*1000
            theta = math.acos((pc.x-m1.x)/dis)
            vx = Mag*math.cos(theta)
            vy = Mag*math.sin(theta)

            step = 10
            dx = step*math.cos(theta)
            dy = step*math.sin(theta)
            title1.undraw()
            title2.undraw()

            for j in range(1000):
                if pc.x + r <=600 and pc.x + r + dx > 600:
                    vx = vx*(-1)
                if pc.x - r >= 200 and pc.x - r + dx < 200:
                    vx = vx*(-1)
                if pc.y + r <= 500 and pc.y + r + dy > 500:
                    vy = vy*(-1)
                if pc.y - r >= 100 and pc.y - r + dy < 100:
                    title3 = Text(Point(400, 570), "You lose! Click anywhere to restart.")
                    title3.draw(myWin)
                    title3.setSize(15)
                    title3.setStyle("bold")
                    break
                if pc.x + r <= 350 and pc.x + r + dx > 350 and 350 - r < pc.y < 400 + r:
                    vx = vx*(-1)
                if pc.x - r >= 450 and pc.x - r + dx < 450 and 350 - r < pc.y < 400 + r:
                    vx = vx*(-1)
                if pc.x - r >= 350 and pc.x - r + dx < 350 and 350 - r < pc.y < 400 + r:
                    vx = vx*(-1)
                if pc.x + r <= 450 and pc.x + r + dx > 450 and 350 - r < pc.y < 400 + r:
                    vx = vx*(-1)
                if pc.y + r <= 350 and pc.y + r + dy > 350 and 350 - r < pc.x < 450 + r:
                    vy = vy*(-1)
                if pc.y - r >= 350 and pc.y - r + dy < 350 and 350 - r < pc.x < 450 + r:
                    title3 = Text(Point(400, 570), "You Win! Click anywhere to restart.")
                    title3.draw(myWin)
                    title3.setSize(15)
                    title3.setStyle("bold")
                    break
                vy = vy - 9.8

                dx = step*vx/math.sqrt(vx**2+vy**2)
                dy = step*vy/math.sqrt(vx**2+vy**2)
                pinball.move(dx, dy)
                pc.x = dx + pc.x
                pc.y = dy + pc.y
                sleep(0.05)
        myWin.getMouse()
    myWin.close()
    
main()
