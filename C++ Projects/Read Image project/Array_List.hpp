#ifndef __ARRAY_LIST_HPP
#define __ARRAY_LIST_HPP

#include "shapes.hpp"
#include <iostream>
#include <string>
#include <sstream>

class ArrayList
{
	public:
		ArrayList();
		ArrayList(const ArrayList& other); 
		ArrayList& operator=(const ArrayList& other); 
													
		virtual ~ArrayList();
		virtual Shape& at(unsigned int index); 
		virtual const Shape& at(unsigned int index) const;
		ArrayList& push_back(Shape* input);
		virtual unsigned int size() const;
		virtual unsigned int capacity() const; 
								
	private:
		Shape** m_data;
		unsigned int m_size;
		unsigned int m_capacity;
};

#endif
