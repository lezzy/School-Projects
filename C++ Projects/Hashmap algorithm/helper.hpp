#ifndef __HELPER_HPP__
#define __HELPER_HPP__

#include <unordered_map>
#include <iostream>
#include <string>
#include <iostream>
#include <vector>
#include <sstream>
#include <queue>
#include <utility>
#include <list>

template<typename V, typename W>
using Graph = std::unordered_map<V, std::unordered_map<V, W>>;

template<typename V, typename W>
struct WeightedVertex
{
	V vertex;
	W weight;
	WeightedVertex()
	{
	}
	WeightedVertex(const V& vertex1, const W& weight1)
	{
		vertex = vertex1;
		weight = weight1;
	}
	WeightedVertex& operator=(const WeightedVertex& other)
	{
		if (this != &other)
		{
			vertex = other.vertex;
			weight = other.weight;
		}
		return *this;
	}
	bool operator<(const WeightedVertex& other)
	{
		return weight < other.weight;
	}
	bool operator>(const WeightedVertex& other)
	{
		return weight > other.weight;
	}
	bool operator==(const WeightedVertex& other)
	{
		return weight == other.weight;
	}
	bool operator!=(const WeightedVertex& other)
	{
		return weight != other.weight;	
	}
	bool operator<=(const WeightedVertex& other)
	{
		return weight <= other.weight;
	}
	bool operator>=(const WeightedVertex& other)
	{
		return weight >= other.weight;
	}
};

template<typename V, typename W>
std::string init_graph(Graph<V,W>& g)
{
	std::string start;
	std::getline(std::cin, start);
	for (std::string line; std::getline(std::cin, line);)
	{
		std::stringstream line_stream(line);
		std::vector<std::string> result;
		for (std::string input; std::getline(line_stream, input, ' ');)
		{
			result.push_back(input);
		}	
		g[result[0]][result[1]] = std::stod(result[2]);
		g[result[1]][result[0]] = std::stod(result[2]);
	}
	return start;
}
#endif
