//
// This file contains your implementations of the sorting algorithms. Do not
// change the function headers, as the functions in reporting.hpp will be
// calling these functions.
//
#ifndef __SORTING_HPP__
#define __SORTING_HPP__
#include <vector>

#include "merge.hpp"

// =============================================================================
// Helper functions
// =============================================================================


// Swaps two values
template <typename T>
void swap(T& a, T& b)
{
    T temp = a;
    a = b;
    b = temp;
}


// Checks if a vector is sorted
template <typename T>
bool is_sorted(const std::vector<T>& vector)
{
    auto t = vector[0];
    for (auto e : vector)
    {
        if (e < t) return false;
        t = e;
    }
    return true;
}



// =============================================================================
// Sorting functions
// =============================================================================


// Insertion sort implementation
template <typename T>
void insertion_sort(std::vector<T>& vector)
{
    // TODO implement insertion sort
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
}


// Merge sort implementation
template <typename T>
void merge_sort(std::vector<T>& vector)
{
    // TODO implement merge sort
    vector = merging(vector);

}


// Hybrid sort implementation
template <typename T>
void hybrid_sort(std::vector<T>& vector)
{
    // TODO implement hybrid sort
    int k;
    k = 101;
   	vector = merge_hyrbid(vector, k);
}

#endif // __SORTING_HPP__
