#ifndef __IMAGE_HPP__
#define __IMAGE_HPP__

#include "Canvas.hpp"
class Image
{
	public:
		Image(int width, int height, Color background);
		virtual ~Image();
		Image(const Image& other);
		Image& operator=(const Image& other);
		
		const Color& pixel(int x, int y) const;
		void set_pixel(int x, int y, Color c);
		
		int height() const;
		int width() const;
		
	private:
		int m_width;
		int m_height;
		Color *m_pixels;
};



#endif
