#ifndef __MERGE_HPP__
#define __MERGE_HPP__

template <typename T>
std::vector<T> merge(std::vector<T>& left, std::vector<T>& right)
{
	std::vector<T> result;
	while( left.size() > 0 || right.size() > 0)
	{
		if (left.size() > 0 && right.size() > 0)
		{
			if (left[0] <= right[0])
			{
				result.push_back(left[0]);
				left.erase(left.begin());
			}
			else
			{
				result.push_back(right[0]);
				right.erase(right.begin());
			}
		}
		else if (left.size() > 0)
		{
			result.push_back(left[0]);
			left.erase(left.begin());
		}
		else if (right.size() > 0)
		{
			result.push_back(right[0]);
			right.erase(right.begin());
		}
	}
	return result;
}


template<typename T>
std::vector<T> merging(std::vector<T> vector)
{
	if (vector.size() <= 1)
	{
		return vector;
	}
	int mid = vector.size()/2;
	std::vector<T> left;
	std::vector<T> right;	
	for (int i = 0; i < mid; i++)
	{
		left.push_back(vector[i]);
	}
	for (int i = mid; i < vector.size(); i++)
	{
		right.push_back(vector[i]);
	} 
	left = merging(left);
	right = merging(right);
	return merge(left, right);
}

template<typename T>
std::vector<T> merge_hyrbid(std::vector<T> vector, int k)
{
	if (vector.size() < k)
	{
		for (int i = 0; i < vector.size(); i++)
    	{
    		T x = vector[i];
    		int j = i;
    		while (j > 0 && vector[j-1] > x) 
    		{
    			vector[j] = vector[j-1];
    			j--;
    		}
    	vector[j] = x;
    	}
    	return vector;
	}
	int mid = vector.size()/2;
	std::vector<T> left;
	std::vector<T> right;	
	for (int i = 0; i < mid; i++)
	{
		left.push_back(vector[i]);
	}
	for (int i = mid; i < vector.size(); i++)
	{
		right.push_back(vector[i]);
	} 
	left = merge_hyrbid(left, k);
	right = merge_hyrbid(right, k);
	return merge(left, right);
}
#endif
