#include <vector>
#include <iostream>
#include "sorting.hpp"
#include "reporting.hpp"

int main()
{
    unsigned int const runs_per_test = 10;
    std::vector<unsigned int> test_sizes = {10, 100, 1000, 10000, 100000};

    std::cout << "\n======== Insertion Sort ========\n";
    sorting_report(insertion_sort, runs_per_test, test_sizes);
    std::cout << "\n======== Merge Sort ========\n";
    sorting_report(merge_sort, runs_per_test, test_sizes);
    std::cout << "\n======== Hybrid Sort ========\n";
    sorting_report(hybrid_sort, runs_per_test, test_sizes);

    return 0;
}

