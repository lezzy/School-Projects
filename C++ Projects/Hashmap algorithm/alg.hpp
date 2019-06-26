#ifndef __ALG_HPP__
#define __ALG_HPP__

#include "helper.hpp"

template<typename V, typename W>
std::pair<std::unordered_map<V,W>, std::unordered_map<V,V>> dijkstra(Graph<V,W> &g, V source)
{
	std::unordered_map<V, W> distance;
	std::unordered_map<V, V> parent;
	std::priority_queue<WeightedVertex<V,W>, std::vector<WeightedVertex<V,W>>, std::greater<void>> pq;
	for (auto e: g)
	{
		distance[e.first] = 1000000000000; // considerably infinite :D
	}
	parent[source] = source;
	distance[source] = 0;
	WeightedVertex<V,W> init(source, 0);
	pq.push(init);
	while (!pq.empty())
	{
		WeightedVertex<V,W> v = pq.top();
		pq.pop();
		for (auto u: g[v.vertex])
		{
			W new_distance = distance[v.vertex] + g[v.vertex][u.first];
			if (new_distance < distance[u.first])
			{
				distance[u.first] = new_distance;
				parent[u.first] = v.vertex;
				WeightedVertex<V,W> new_vertex (u.first, new_distance);
				pq.push(new_vertex);
			}
		}
	}
	return std::make_pair(distance, parent);
}

#endif
