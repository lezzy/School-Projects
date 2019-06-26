//
// This file contains functions for testing the sorting functions you have
// implemented in sorting.hpp. You should not need to modify this file.
//
#ifndef __REPORTING_HPP__
#define __REPORTING_HPP__
#include "sorting.hpp"
#include <random>
#include <ctime>


// Function type for sorting function
template <typename T>
using sorter_t = decltype(insertion_sort<T>);


// Times sorting. Returns -1 if sorting failed.
template <typename T>
double time_sorting(sorter_t<T> sorter, const std::vector<T>& input)
{
    std::vector<T> vector = input;

    auto start = clock();
    sorter(vector);
    auto stop = clock();

    double ret =  1000 * static_cast<double>(stop - start) / CLOCKS_PER_SEC;
    if (is_sorted(vector) == false) ret = -1.0;

    return ret;
}


// Generate a random vector
std::vector<int> generate_random(size_t n)
{
    std::random_device rd;
    std::default_random_engine eng(rd());
    std::uniform_int_distribution<int> dis(0, n-1);

    std::vector<int> vector(n);
    for (auto& e : vector) e = dis(eng);

    return vector;
}


// Generate almost sorted vector
std::vector<int> generate_almost_sorted(size_t n)
{
    std::random_device rd;
    std::default_random_engine eng(rd());
    std::uniform_int_distribution<int> dis(0, n-1);

    std::vector<int> vector(n);
    for (int i = 0; i < n; i++) vector[i] = i;

    for (int i = 0; i < 0.1 * n; i++) vector[dis(eng)] = dis(eng);

    return vector;
}


// Formatted printing of times 
void formatted_print(std::vector<unsigned int> sizes, std::vector<double> times)
{
    for (int i = 0; i < sizes.size(); i++)
    {
        std::cout << sizes[i] << "\t: ";
        if (times[i] > 0)
        {
            std::cout << times[i];
        }
        else
        {
            std::cout << "Failed to sort vector!";
        }
        std::cout << std::endl;
    }
}


// Print sorting report
void sorting_report(sorter_t<int> sorter, unsigned int runs, std::vector<unsigned int> sizes)
{
    std::vector<double> random_times(sizes.size());
    std::vector<double> almost_sorted_times(sizes.size());
    
    for (int i = 0; i < sizes.size(); i++)
    {
        auto size = sizes[i];
        double r_sum = 0.0;
        double as_sum = 0.0;
        
        for (int j = 0; j < runs; j++)
        {
            auto input = generate_random(size);
            r_sum += time_sorting(sorter, input);

            input = generate_almost_sorted(size);
            as_sum += time_sorting(sorter, input);
        }

        random_times[i] = r_sum / runs;
        almost_sorted_times[i] = as_sum / runs;
    }

    std::cout << "Random vector (size : time)" << std::endl;
    formatted_print(sizes, random_times);
    std::cout << "Almost sorted vector (size : time)" << std::endl;
    formatted_print(sizes, almost_sorted_times);
}

#endif // __REPORTING_HPP__
