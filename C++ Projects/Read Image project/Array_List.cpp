#include "Array_List.hpp"
#include <cassert>

ArrayList::ArrayList()
	:m_data(nullptr)
	,m_size(0)
	,m_capacity(16)
{
	m_data = new Shape*[m_capacity];
}

ArrayList::~ArrayList()
{
	for (int i = 0; i < m_size; i++)
	{
		delete m_data[i];
	}
	delete[] m_data;
}

ArrayList::ArrayList(const ArrayList& other)
	:m_data(nullptr)
	,m_size(other.m_size)
	,m_capacity(other.m_capacity)
{
	m_data = new Shape*[m_capacity];
	for (int i = 0; i < m_capacity; i++)
	{
		m_data[i] = other.m_data[i];
	}	
}

ArrayList& ArrayList::operator=(const ArrayList& other)
{	
	if (this != &other)
	{
		m_size = other.m_size;
		m_capacity = other.m_capacity;
		delete[] m_data;
		m_data = new Shape*[m_capacity];
		for (int i = 0; i < m_capacity; i++)
		{
			m_data[i] = other.m_data[i];
		}
	}
	return *this;
}

Shape& ArrayList::at(unsigned index)
{
	assert(0 <= index && index < m_size);
	return *m_data[index];
}

const Shape& ArrayList::at(unsigned index) const
{
	assert(0 <= index && index < m_size);
	return *m_data[index];
} 

ArrayList& ArrayList::push_back(Shape* input)
{
	assert(m_size <= m_capacity);
	if (m_size == m_capacity)
	{
		Shape** new_data = new Shape*[2 * m_capacity];
		for (int i = 0; i < m_capacity; i++)
		{
			new_data[i] = m_data[i];
			delete[] m_data[i];
		}
		
		delete[] m_data;
		m_data = new_data;
		new_data = nullptr;
		
	}
	m_data[m_size] = input;
	m_size++;
	return *this;
}

unsigned int ArrayList::size() const
{
	return m_size;
}

unsigned int ArrayList::capacity() const
{
	return m_capacity;
}

