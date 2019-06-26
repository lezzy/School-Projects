#ifndef __CANVAS_HPP__
#define __CANVAS_HPP__

#include "Array_List.hpp"


struct Canvas
{
	Color background;
	int minx, miny, maxx, maxy;
	ArrayList depths[16];
};

void parse(Canvas &canvas, int &depth);




#endif
