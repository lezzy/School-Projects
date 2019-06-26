#include "Image.hpp"
#include <string>

int intialization(Canvas &canvas)
{
	Color background_color;
	std::string line;

	std::getline(std::cin, line);
	std::stringstream line_stream(line);

	std::string segment;
	
	// Extra getline with the stream is called to skip through the label
	// since we know the first few lines are for the background color,
	// then the size of canvas, and depth.
	
	std::getline(line_stream, segment, ' ');
	
	// Temporary array used to parse through the input file.
	// Stores information in here.
	int temp_arry[5] = {0};
	
	for (int i = 0; i < 3; i++)
	{
		// for the color
		std::string::size_type sz;
		std::getline(line_stream, segment);
		temp_arry[i] = std::stoi(segment, &sz);
	}
	background_color.red = temp_arry[0];
	background_color.green = temp_arry[1];
	background_color.blue = temp_arry[2];
	canvas.background = background_color;
	//store into color
	
	std::getline(std::cin, line);
	std::stringstream line_stream1(line);
	
	std::getline(line_stream1, segment, ' ');
	for (int i = 0; i < 4; i++)
	{
		std::getline(line_stream1, segment, ' ');
		temp_arry[i] = std::stoi(segment);
	}
	// store for size of canvas
	
	canvas.minx = temp_arry[0];
	canvas.miny = temp_arry[1];
	canvas.maxx = temp_arry[2];
	canvas.maxy = temp_arry[3];
	
	std::getline(std::cin, line);
	std::stringstream line_stream2(line);
	
	std::getline(line_stream2, segment, ' ');
	std::getline(line_stream2, segment, ' ');
	return std::stoi(segment); //returns the depth read from file.
}


int main()
{
	Canvas canvas;
	int current_depth;
	
	// Function intialization (defined above) is called.
	// to intialize the canvas.
	
	current_depth = intialization(canvas);
	
	// Parse is then called to read the rest of the input file.
	parse(canvas, current_depth);

	// here is where the algorithm is implemented.
	Point testp;
	Image image(canvas.maxx, canvas.maxy, canvas.background);
	Color p_color;
	for (int y = canvas.miny;  y < canvas.maxy; y++)
	{
		for (int x = canvas.minx; x < canvas.maxx; x++)
		{
			testp.x = x;
			testp.y = y;
			for (int d = 0; d < 16; d++)
			{
				for (int s = 0; s < canvas.depths[d].size(); s++)
				{
					if (canvas.depths[d].at(s).contains_point(testp) == 1)
					{
						p_color = canvas.depths[d].at(s).get_color();
						image.set_pixel(x, y, p_color);
					}
				}
			}
		}
	}
	std::cout << "P3" << std::endl;
	std::cout << canvas.maxx - canvas.minx << " " << canvas.maxy - canvas.miny << std::endl;
	std::cout << 255 << std::endl;
	
	Color final_c;
	for (int y = canvas.miny; y < canvas.maxy; y++)
	{
		for (int x = canvas.minx; x < canvas.maxx; x++)
		{
			final_c = image.pixel(x,y);
			std::cout << final_c.red << " " << final_c.green << " " << final_c.blue 
			<< " ";
		}
		std::cout << std::endl;
	}
	

    return 0;
}


