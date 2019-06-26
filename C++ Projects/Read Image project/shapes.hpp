#ifndef __SHAPE_HPP__
#define __SHAPE_HPP__


// A struct for 2D-Points
struct Point
{
    int x;
    int y;
};


// A struct for RGB color values
struct Color
{
    int red;
    int green;
    int blue;
};


// A base class for all shapes
class Shape
{
    public:
        Shape(Color color);
        virtual ~Shape();
        virtual bool contains_point(Point p) = 0;
        const Color& get_color();
    private:
        Color m_color;
};


// A triangle class
class Triangle : public Shape
{
    public:
        Triangle(Point point1, Point point2, Point point3, Color color);
        virtual ~Triangle();
        bool contains_point(Point p);
    private:
        Point m_point1;
        Point m_point2;
        Point m_point3;
};

// Rectangle
class Rectangle : public Shape
{
	public:
		Rectangle(Point point1, Point point2, Color color);
		virtual ~Rectangle();
		bool contains_point(Point p);
	private:
		Point m_point1;
		Point m_point2;
};

// Circle
class Circle : public Shape
{
	public:
		Circle(Point point1, int radius, Color color);
		virtual ~Circle();
		bool contains_point(Point p);
	private:
		Point m_point1;
		int m_radius;
};


#endif // __SHAPES_HPP__
