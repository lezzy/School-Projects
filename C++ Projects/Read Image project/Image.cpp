#include "Image.hpp"

Image::Image(int width, int height, Color background)
	:m_width(width)
	,m_height(height)
	,m_pixels(nullptr)
{
	// intializes the array with the background color
	// if set_pixel gets called it will change the color
	// while the rest stays as the background color
	m_pixels = new Color[m_width*m_height];
	
	for (int i = 0; i <m_width*m_height; i++)
	{
		m_pixels[i].red = background.red;
		m_pixels[i].green = background.green;
		m_pixels[i].blue = background.blue;
	}
}

Image::~Image()
{
	delete[] m_pixels;
}

Image::Image(const Image& other)
	:m_width(other.m_width)
	,m_height(other.m_height)
	,m_pixels(nullptr)
{
	m_pixels = new Color[m_width*m_height];
	for (int i = 0; i <m_width*m_height; i++)
	{
		m_pixels[i] = other.m_pixels[i];
	}
}

Image& Image::operator=(const Image& other)
{
	if (this != &other)
	{
		m_width = other.m_width;
		m_height = other.m_height;
		delete[] m_pixels;
		m_pixels = new Color[m_height*m_width];
		for (int i = 0; i < m_width*m_height; i++)
		{
			m_pixels[i] = other.m_pixels[i];
		}
	}
	return *this;
}

const Color& Image::pixel(int x, int y) const
{
	return m_pixels[y*m_width + x];
}

void Image::set_pixel(int x, int y, Color c)
{
	m_pixels[y*m_width + x].red = c.red;
	m_pixels[y*m_width + x].green = c.green;
	m_pixels[y*m_width + x].blue = c.blue;
}

int Image::height() const
{
	return m_height;
}

int Image::width() const
{
	return m_width;
}


