#include "shapes.hpp"

//==== implementations for Shape class =========================================
Shape::Shape(Color color)
    : m_color(color)
{
}

Shape::~Shape()
{
}

const Color& Shape::get_color()
{
    return m_color;
}



//==== implementations for Triangle class =====================================
Triangle::Triangle(Point point1, Point point2, Point point3, Color color)
    : Shape(color)
    , m_point1(point1)
    , m_point2(point2)
    , m_point3(point3)
{
}

Triangle::~Triangle()
{
}

bool right_turn_test(Point p, Point q, Point r)
{
    return (q.x - p.x) * (r.y - p.y) - (q.y - p.y) * (r.x - p.x) <= 0;
}

bool Triangle::contains_point(Point p)
{
    bool test1 = right_turn_test(m_point1, m_point2, p);
    bool test2 = right_turn_test(m_point2, m_point3, p);
    bool test3 = right_turn_test(m_point3, m_point1, p);
    return (test1 == test2) && (test2 == test3) && (test3 == test1);
}

//=================Rectangle========================================================
Rectangle::Rectangle(Point point1, Point point2, Color color)
	: Shape(color)
	, m_point1(point1)
	, m_point2(point2)
{
}

Rectangle::~Rectangle()
{
}

bool Rectangle::contains_point(Point p)
{
    return (m_point1.y <= p.y) && (p.y <= m_point2.y) && 
    (m_point1.x <= p.x) && (p.x <= m_point2.x);
}

//========Circle======================================================================
Circle::Circle(Point point1, int radius, Color color)
	: Shape(color)
	, m_point1(point1)
	, m_radius(radius)
{
}

Circle::~Circle()
{
}


bool Circle::contains_point(Point p)
{
    int test1;
    int test2;
    test1 = (p.x - m_point1.x);
    test2 = (p.y - m_point1.y);
	return ((test1*test1) + (test2*test2) <= (m_radius*m_radius));
}





