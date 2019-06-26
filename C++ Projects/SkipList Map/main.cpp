#include <exception>
#include "skip_list_map.hpp"

int main()
{
	std::cout << "Initial Map:" << std::endl;
	std::cout << "*******************************" << std::endl;
	SkipListMap<int, double> map;
	map.print_map();
	std::cout << "*******************************" << std::endl;
	try
	{
		map.insert(0,1.1);
		map.insert(10, 10.10);
		map.insert(5, 5.5);
		map.insert(12, 12.12);
		std::cout << "Trying to insert 5 again with different value" << std::endl;
		map.insert(5, 1.0);
	}
	catch(duplicate_key)
	{
		std::cout << "##############################" << std::endl;
		std::cout << "Duplicate key exception. (insert)" << std::endl;
		std::cout << "##############################" << std::endl;
	}
	std::cout << "First map" << std::endl;
	std::cout << "*******************************" << std::endl;
	map.print_map();
	std::cout << "*******************************" << std::endl;
	std::vector<int> list_keys;
	list_keys = map.keys();
	std::cout << "List of keys: " << std::endl;
	for(auto e :list_keys)
	{
		std::cout << e << " ";
	}
	std::cout << std::endl;
	std::cout << "List of key, value pairs" << std::endl;
	std::vector<std::pair<int, double>> list_pairs;
	list_pairs = map.items();
	for(auto e:list_pairs)
	{
		std::cout << "(" << e.first << "," << e.second << ") ";
	}
	std::cout << std::endl;
	std::cout << "*******************************" << std::endl;
	int length;
	try
	{
		length = map.level_count(2);
		std::cout << "Level count(2): " << length << std::endl;
		length = map.level_count(1);
		std::cout << "Level count(1): " << length << std::endl;
		length = map.level_count(4);
		std::cout << "Level count(4): " << length << std::endl;
	}
	catch(invalid_level)
	{
		std::cout << "##############################" << std::endl;
		std::cout << "Invalid level exception. " << std::endl;
		std::cout << "##############################" << std::endl;
	}
	std::cout << "*******************************" << std::endl;
	int size;
	size = map.size();
	std::cout << "Size(): " << size << std::endl;
	bool temp;
	temp = map.contains(5);
	std::cout << "If contains 5: " << temp << std::endl;
	double result;
	std::cout << "trying to get 5. " << std::endl;
	try
	{
		result = map.get(5);
		std::cout << "Get(5): " << result << std::endl;
		std::cout << "Trying to get 100." << std::endl;
		result = map.get(100);
	}
	catch(key_not_found)
	{
		std::cout << "##############################" << std::endl;
		std::cout << "Key not found exception (get)" << std::endl;
		std::cout << "##############################" << std::endl;
	}
	std::cout << "Setting 5's value to 12.2" << std::endl;
	try
	{
		map.set(5, 12.2);
		result = map.get(5);
		std::cout << "After set 5 to 12.2: " << result << std::endl;
		std::cout << "Trying to set 100 to 100.1" << std::endl;
		map.set(100, 100.100);
	}
	catch(key_not_found)
	{
		std::cout << "##############################" << std::endl;
		std::cout << "Key not found exception (set)" << std::endl;
		std::cout << "##############################" << std::endl;
	}
	std::cout << "*******************************" << std::endl;
	std::cout << "Remove 0 from SkipList" << std::endl;
	try
	{
		map.remove(0);
		std::cout << "Trying to remove 100." << std::endl;
		map.remove(100);
	}
	catch(key_not_found)
	{
		std::cout << "##############################" << std::endl;
		std::cout << "Key not found exception. (remove) " << std::endl;
		std::cout << "##############################" << std::endl;
	}
	map.print_map();
	std::cout << "*******************************" << std::endl;
	int height;
	height = map.height();
	std::cout << "Getting Height(): " << height << std::endl;
	bool level;
	try
	{
		level = map.key_on_level(5, 2);
		std::cout << "If key 5 is on level 2: " << level << std::endl;
	}
	catch(invalid_level)
	{
		std::cout << "##############################" << std::endl;
		std::cout << "invalid level exception (key on level)" << std::endl;
		std::cout << "##############################" << std::endl;
	}
	std::cout << "*******************************" << std::endl;
	std::cout << "Clearing map." << std::endl;
	map.clear();
	map.print_map();
	
	std::cout << "*******************************" << std::endl;
	std::cout << "Assigning current skiplist to new skiplist" << std::endl;
	std::cout << "inserting a 1 into the new map." << std::endl;
	SkipListMap<int, double> map2;
	map2.insert(1, 1.2);
	map = map2;
	map.print_map();
    return 0;
}
