#include "alg.hpp"

int main()
{
	Graph<std::string, double> g;
	std::string start;
	start = init_graph(g);
	auto hashmaps = dijkstra(g, start);
	std::list<std::string> vertices;
	for (auto element: hashmaps.first)
	{
		vertices.push_back(element.first);
	}
	vertices.sort();
	for(auto vert: vertices)
	{
		std::cout << vert << "; " << hashmaps.first[vert] << "; ";
		std::string vertex;
		vertex = vert;
		std::vector<std::string> parents;
		while (vertex != start)
		{
			parents.insert(parents.begin(),vertex);
			vertex = hashmaps.second[vertex];
		}
		parents.insert(parents.begin(), start);
		for(auto p: parents)
		{
			std::cout << p << " ";
		}
		std::cout << std::endl;
	}
    return 0;
}
