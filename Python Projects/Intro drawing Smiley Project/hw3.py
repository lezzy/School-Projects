# hw3.py
# Lesmond Chow
# 17412911
# 10/11/11
# EECS 12
# This program draws a smiley face using GUI interface.

from graphics import*
import math
def main():
    myWin = GraphWin("Smiley Face",800, 600)
    myWin.setCoords(0, 0, 800, 600)
    txtMsg = Text(Point(400, 550), "Please select two points to draw the countor of the face. ")
    txtMsg.setTextColor("Red")
    txtMsg.setStyle("bold")
    txtMsg.setSize(10)
    txtMsg.draw(myWin)
    
    Text(Point(150, 220), "Enter to change ratio: ").draw(myWin)
    text1 = Text(Point(100, 180), "Face Width: ").draw(myWin)
    input1 = Entry(Point(190, 180), 5)
    input1.setText("1")
    input1.draw(myWin)
    
    text2 = Text(Point(100, 150), "Eye Radius: ").draw(myWin)
    input2 = Entry(Point(190, 150), 5)
    input2.setText("1")
    input2.draw(myWin)
    
    text3 = Text(Point(100, 120), "Nose Width: ").draw(myWin)
    input3 = Entry(Point(190, 120), 5)
    input3.setText("1")
    input3.draw(myWin)
    
    text4 = Text(Point(100, 90), "Lips Width: ").draw(myWin)
    input4 = Entry(Point(190, 90), 5)
    input4.setText("1")
    input4.draw(myWin)
    
    P1 = myWin.getMouse()
    P2 = myWin.getMouse()
    myOval = Oval(P1, P2)
    myOval.draw(myWin)
    myOval.setFill("Yellow")

    txtMsg.undraw()
    txtMsg = Text(Point(400, 550), "Please select two points to draw the center and radius of the eyes. ")
    txtMsg.setTextColor("Red")
    txtMsg.setStyle("bold")
    txtMsg.setSize(10)
    txtMsg.draw(myWin)
    
    Mx = (P1.x + P2.x)/2
    
    P3 = myWin.getMouse()
    P4 = myWin.getMouse()
    r = math.sqrt(((P3.x - P4.x)**2) + ((P3.y - P4.y)**2))
    leftEye = Circle(P3, r)
    leftEye.draw(myWin)
    leftEye.setFill("Black")
    rightEye = leftEye.clone()
    rightEye = Circle(P3, r)
    rightEye.draw(myWin)
    rightEye.setFill("Black")
    rightEye.move(2*(Mx-P3.x), 0)

    txtMsg.undraw()
    txtMsg = Text(Point(400, 550), "Please select two points to draw the nose. ")
    txtMsg.setTextColor("Red")
    txtMsg.setStyle("bold")
    txtMsg.setSize(10)
    txtMsg.draw(myWin)
    
    P5 = myWin.getMouse()
    P6 = myWin.getMouse()
    P6.x = Mx
    P7 = Point(2*Mx-P5.x, P5.y)
    
    myTri = Polygon(P5, P6, P7)
    myTri.setFill("Orange")
    myTri.draw(myWin)
    
    txtMsg.undraw()
    txtMsg = Text(Point(400, 550), "Please select two points to draw the lips. ")
    txtMsg.setTextColor("Red")
    txtMsg.setStyle("bold")
    txtMsg.setSize(10)
    txtMsg.draw(myWin)
    
    P8 = myWin.getMouse()
    P9 = myWin.getMouse()
    P9 = Point(2*Mx-P8.x, P9.y)
    P10 = Point(P8.x, P9.y)
    P11 = Point(P9.x, P8.y)
    myRect = Polygon(P8, P10, P9, P11)
    myRect.setFill("Red")
    myRect.draw(myWin)
    
    txtMsg.undraw()
    txtMsg = Text(Point(400, 550), "Please click anywhere to update the face using the entered ratios. ")
    txtMsg.setTextColor("Red")
    txtMsg.setStyle("bold")
    txtMsg.setSize(10)
    txtMsg.draw(myWin)

    for i in range(5):
        myWin.getMouse()
        myOval.undraw()
        leftEye.undraw()
        rightEye.undraw()
        myTri.undraw()
        myRect.undraw()

        F1 = eval(input1.getText())
        
        if P2.x < P1.x:
            P1 = Point(P1.x*F1, P1.y)
            P2 = Point(P2.x/F1, P2.y)

        else:
            P1 = Point(P1.x/F1, P1.y)
            P2 = Point(P2.x*F1, P2.y)

        myOval = Oval(P1, P2)
        myOval.draw(myWin)
        myOval.setFill("Yellow")

        Mx = (P1.x + P2.x)/2
        
        F2 = eval(input2.getText())
        r = r*F2
        leftEye = Circle(P3, r)
        leftEye.draw(myWin)
        leftEye.setFill("Black")
        rightEye = leftEye.clone()
        rightEye = Circle(P3, r)
        rightEye.draw(myWin)
        rightEye.setFill("Black")
        rightEye.move(2*(Mx-P3.x), 0) 
        

        F3 = eval(input3.getText())
        P5 = Point(P5.x/F3, P5.y)
        P6 = Point(P6.x, P6.y)
        P6.x = Mx
        P7 = Point((2*Mx-P5.x), P5.y)
        myTri = Polygon(P5, P6, P7)
        myTri.draw(myWin)
        myTri.setFill("Orange")

        F4 = eval(input4.getText())
        P8 = Point(P8.x/F4, P8.y)
        P9 = Point(P9.x*F4, P9.y)
        P9 = Point(2*Mx-P8.x, P9.y)
        P10 = Point(P8.x, P9.y)
        P11 = Point(P9.x, P8.y)
        myRect = Polygon(P8, P10, P9, P11)
        myRect.setFill("Red")
        myRect.draw(myWin)

        if i == 3:
            End = Text(Point(300, 300), "Please click again to exit. ")
            End.setTextColor("Red")
            End.setSize(20)
            End.setStyle("bold")
            End.draw(myWin)
    
    myWin.getMouse
    myWin.close()

        
        
    

main()
