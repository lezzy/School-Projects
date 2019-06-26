#include "Canvas.hpp"


void parse(Canvas &canvas, int &depth)
{	
	// Color is intialized here so that if a shape is called
	// it will be set to the color that was most recently
	// set. This can make multiple shapes the same color if needed.
	Color color;
	
	for (std::string line; std::getline(std::cin, line);)
	{
		std::stringstream parse_stream(line);
		std::string label;
		
		std::getline(parse_stream, label, ' ');
		
		
		// This array is used temporarily to store whatever is being read in.
		// i is used to iterate through this temporary array.
		int temp[6] = {0};
		int i = 0;
		
		// Points are intialized here since each shape would technically
		// have new points.
		Point point1;
		Point point2;
		Point point3;
		
		if (label == "COLOR")
		{

			// Store into a color struct
			for (std::string segment; std::getline(parse_stream, segment, ' ');)
			{
				temp[i] = std::stoi(segment);
				i++;
			}
			color.red = temp[0];
			color.green = temp[1];
			color.blue = temp[2];
		}
		else if (label == "RECTANGLE")
		{

			//rectangle
			for (std::string segment; std::getline(parse_stream, segment, ' ');)
			{
				temp[i] = std::stoi(segment);
				i++;
			}
			point1.x = temp[0];
			point1.y = temp[1];
			point2.x = temp[2];
			point2.y = temp[3];
			canvas.depths[depth].push_back(
			new Rectangle(point1, point2, color));
		}
		else if (label == "TRIANGLE")
		{

			// triangle
			for (std::string segment; std::getline(parse_stream, segment, ' ');)
			{
				temp[i] = std::stoi(segment);
				i++;
			}
			point1.x = temp[0];
			point1.y = temp[1];
			point2.x = temp[2];
			point2.y = temp[3];
			point3.x = temp[4];
			point3.y = temp[5];
			canvas.depths[depth].push_back(
			new Triangle(point1, point2, point3, color));
		}
		else if (label == "CIRCLE")
		{
			for (std::string segment; std::getline(parse_stream, segment, ' ');)
			{
				temp[i] = std::stoi(segment);
				i++;
			}
			point1.x = temp[0];
			point1.y = temp[1];
			canvas.depths[depth].push_back(new Circle(point1, temp[2], color));
		}
		else if (label == "DEPTH")
		{
			std::string segment;
			std::getline(parse_stream, segment);
			depth = std::stoi(segment);
			// for depth, it just changes the current depth in this function.
		}

	}
	
}
