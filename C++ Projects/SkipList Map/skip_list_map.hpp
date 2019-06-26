#ifndef __SKIPLISTMAP_HPP__
#define __SKIPLISTMAP_HPP__

#include <vector>
#include <utility>
#include <random>
#include <iostream>
#include <string>

// Exceptions (empty classes) for SkipListMap methods. You may add extra features
// to these classes, but do not change their names.
class key_not_found{};
class duplicate_key{};
class invalid_level{};


// An enum class to represent the three types of SkipListMapNode.
// Not all Key types will have +- infinity. So we will use an enum
// to represent these values.
enum class SkipListMapNodeType {finite, negative_infinity, positive_infinity};


// A SkipListMapNode from which we can build a SkipListMap
template <typename Key, typename Value>
struct SkipListMapNode
{
    SkipListMapNodeType type; // redundant. Don't really need to use it
    						// only infinity if left/right pointers are NULL
    						// could just use NULL for infinity
    						// finite if it's neither negative/positive 
    						// infinity
    Key key;
    Value value;

    SkipListMapNode* up;
    SkipListMapNode* down;
    SkipListMapNode* next;
    SkipListMapNode* prev;
};


// The actual SkipListMap.
// The template parameter Key should be assume to implement only those operators
// absolutely needed for a skiplist.
template <typename Key, typename Value>
class SkipListMap
{
    public:
        // Default constructor: creates an empty SkipListMap
        SkipListMap();
        SkipListMap(Key key, Value value);
        // Copy constructor: creates a deep-copy of the SkipListMap
        SkipListMap(const SkipListMap& other);
        // Destructor: deallocates all memory associated with the SkipListMap
        ~SkipListMap();
        // Assignment operator: assigns a deep-copy of other to "this"
        SkipListMap& operator=(const SkipListMap& other);

        // Returns the number of key-value pairs stored in the SkipListMap.
        unsigned int size() const;

        // clear() produces an empty SkipListMap
        void clear();
        // insert() takes a key and a value and adds them to the SkipListMap.
        // If the key is already in the SkipListMap, then a duplicate_key
        // exception is thrown.
        void insert(const Key& key, const Value& value);
        // remove() takes a key and removes the corresponding key-value pair.
        // If the key is already in the SkipListMap, then a key_not_cound
        // exception is thrown.
        void remove(const Key& key);
        // contains() returns true if key is in the SkipListMap, and false
        // otherwise.
        bool contains(const Key& key) const;

        // get() returns a constant reference to the value associated with the
        // given key. If the key is not in the SkipListMap, then a key_not_found
        // exception is thrown.
        const Value& get(const Key& key) const;
        // set() changes the value associated with the given key. If the key is
        // not in the SkipListMap, then a key_not_found exception is thrown.
        void set(const Key& key, const Value& value);

        // keys() returns a vector of all key in the SkipListMap in sorted order.
        std::vector<Key> keys() const;
        // items() returns a vector of all key-value pairs in the SkipListMap
        // in sorted order of the keys. 
        std::vector<std::pair<Key, Value>> items() const;
        

        // The following three methods would likely not exist in production code,
        // but will be helpful when debugging your project. You may want to use
        // them to create a method which prints the SkipListMap. Since these
        // methods are for testing you should you straightforward implementations
        // and not worry about speed.

        // height() returns the number of levels in the SkipListMap, counting
        // the empty level at the top of SkipList.
        unsigned int height() const;
        // level_count() returns the number of key-value pairs on a given level.
        // If the level does not exists, an invalid_level exception is thrown.
        unsigned int level_count(unsigned int level) const;
        // key_on_level() returns true if the given key is contained in the
        // given level, and false otherwise. If the level does not exist, an
        // invalid_level exception is thrown.
        bool key_on_level(const Key& key, unsigned int level) const;

		//added function(s)
		void print_map();
    private:
        // Implementation details:
        // 
        // You may add any private variables that you feel you need. 
        //
        // You may also feel free to add additional methods (public or private)
        // you feel you need, but you may not change the signatures of the
        // existing methods.
		unsigned int m_size;
		SkipListMapNode<Key,Value>* m_top_left;
		SkipListMapNode<Key,Value>* m_bot_left;
		std::default_random_engine m_rand;
		
		SkipListMapNode<Key, Value>* find(Key key) const;
		void destroylist();

};

// ==== Implementation =========================================================
// TODO: Implement all of the methods declared in the SkipListMap


/*******************CONSTRUCTORS*******************/
template <typename Key, typename Value>
SkipListMap<Key,Value>::SkipListMap()
	:m_top_left(nullptr)
	,m_bot_left(nullptr)
	,m_size(0)
	,m_rand(std::random_device()())
{
	SkipListMapNode<Key,Value>* first = new SkipListMapNode<Key,Value>;
	first->next = nullptr;
	first->up = nullptr;
	first->prev = nullptr;
	first->down = nullptr;
	m_top_left = m_bot_left = first;
	
}

template<typename Key, typename Value>
SkipListMap<Key, Value>::SkipListMap(Key key, Value value)
	:m_top_left(nullptr)
	,m_bot_left(nullptr)
	,m_size(0)
	,m_rand(std::random_device()())
{
	SkipListMapNode<Key,Value>* first = new SkipListMapNode<Key,Value>;
	SkipListMapNode<Key,Value>* second = new SkipListMapNode<Key,Value>;
	first->up = nullptr;
	first->prev = nullptr;
	first->down = nullptr;
	m_top_left = m_bot_left = first;
	second->key = key;
	second->value = value;
	second->prev = first;
	second->next = nullptr;
	second->up = nullptr;
	second->down = nullptr;
	first->next = second;
	
}
/***********************************************/

/*******************DESTRUCTOR********************/

template <typename Key, typename Value>
SkipListMap<Key,Value>::~SkipListMap()
{
	destroylist();
}
/***********************************************/

/*******************COPY CONSTRUCTOR*******************/
template <typename Key, typename Value>
SkipListMap<Key,Value>::SkipListMap(const SkipListMap& other)
	:m_top_left(nullptr)
	,m_bot_left(nullptr)
	,m_size(other.m_size)
	,m_rand(other.m_rand)
{
	std::vector<std::pair<Key, Value>> other_list;
	other_list = other.items();
	for (auto e: other_list)
	{
		insert(e.first, e.second);
	}
}

/************************************************/

/*******************EQUAL OPERATOR*******************/

template <typename Key, typename Value>
SkipListMap<Key,Value>& SkipListMap<Key,Value>::operator=(const SkipListMap& other)
{
	if (this != &other)
	{
		clear();
		m_size = other.m_size;
		m_rand = other.m_rand;
		std::vector<std::pair<Key, Value>> other_list;
		other_list = other.items();
		for (auto e: other_list)
		{
			insert(e.first, e.second);
		}
		
	}
	return *this;
}
/********************************************************/

/*******************OTHER PUBLIC FUNCTIONS*************************/

template <typename Key, typename Value>
unsigned int SkipListMap<Key, Value>::size() const
{
	return m_size;
}

template <typename Key, typename Value>
void SkipListMap<Key, Value>::clear()
{
	destroylist();
	SkipListMapNode<Key,Value>* first = new SkipListMapNode<Key,Value>;
	first->next = nullptr;
	first->up = nullptr;
	first->prev = nullptr;
	first->down = nullptr;
	m_top_left = m_bot_left = first;
	
}

template <typename Key, typename Value>
void SkipListMap<Key, Value>::insert(const Key& key, const Value& value)
{
	SkipListMapNode<Key, Value>* prev;
	prev = find(key);
	if (prev->prev)
	{
		if (prev->key == key)
		{
			throw duplicate_key();
		}
		int height = 1 + std::geometric_distribution<>(0.5)(m_rand);
		SkipListMapNode<Key, Value>* last = nullptr;
		while (height > 0)
		{
			SkipListMapNode<Key, Value>* node = new SkipListMapNode<Key, Value>;
			node->key = key;
			node->value = value;
			node->next = prev->next;
			node->prev = prev;
			node->down = last;
			node->prev->next = node;
			if (node->next)
			{
				node->next->prev = node;
			}
			if (node->down)
			{
				node->down->up = node;
			}
			while (!prev->up && prev->prev)
			{
				prev = prev->prev;
			}
			if (!prev->up)
			{
				SkipListMapNode<Key, Value>* new_node = new SkipListMapNode<Key, Value>;
				prev->up = new_node;
				prev->up->down = prev;
				m_top_left = prev->up;
			}
			prev = prev->up;
			last = node;
			height = height - 1;
		}
		m_size = m_size + 1;
	}
	else
	{
		SkipListMapNode<Key, Value>* new_node = new SkipListMapNode<Key, Value>;
		new_node->key = key;
		new_node->value = value;
		new_node->prev = prev;
		new_node->next = prev->next;
		new_node->up = nullptr;
		new_node->down = nullptr;
		prev->next = new_node;
		m_size = m_size + 1;
	}
}

template<typename Key, typename Value>
void SkipListMap<Key, Value>::remove(const Key& key)
{
	SkipListMapNode<Key, Value>* n;
	n = find(key);
	if (n->key != key)
	{
		throw key_not_found();
	}
	while (n)
	{
		n->prev->next = n->next;
		if (n->next)
		{
			n->next->prev = n->prev;
		}
		auto up = n->up;
		delete n;
		n = up;
	}
	
}

template<typename Key, typename Value>
bool SkipListMap<Key, Value>::contains(const Key& key) const
{
	SkipListMapNode<Key, Value>* n;
	n = find(key);
	if (n->prev)
	{
		if (n->key == key)
		{
			return true;
		}
	}
	return false;
}

template<typename Key, typename Value>
const Value& SkipListMap<Key, Value>::get(const Key& key) const
{
	SkipListMapNode<Key, Value>* n;
	n = find(key);
	if (n->key != key)
	{
		throw key_not_found();
	}
	else
	{
		return n->value;
	}
}

template<typename Key, typename Value>
void SkipListMap<Key, Value>::set(const Key& key, const Value& value)
{
	SkipListMapNode<Key, Value>* n;
	n = find(key);
	if (n->key != key)
	{
		throw key_not_found();
	}
	else
	{
		n->value = value;
	}
}

template<typename Key, typename Value>
std::vector<Key> SkipListMap<Key, Value>::keys() const
{
	std::vector<Key> result;
	SkipListMapNode<Key, Value>* n;
	n = m_bot_left;
	while (n->next)
	{
		n = n->next;
		result.push_back(n->key);
	}
	return result;
}

template<typename Key, typename Value>
std::vector<std::pair<Key, Value>> SkipListMap<Key,Value>::items() const
{
	std::vector<std::pair<Key, Value>> result;
	SkipListMapNode<Key, Value>* n;
	n = m_bot_left;
	while(n->next)
	{
		n = n->next;
		auto p = std::make_pair(n->key, n->value);
		result.push_back(p);
	}
	return result;
}

template<typename Key, typename Value>
unsigned int SkipListMap<Key, Value>::height() const
{
	unsigned int h = 1;
	SkipListMapNode<Key, Value>* n;
	n = m_top_left;
	while (n->down)
	{
		n = n->down;
		h = h + 1;
	}
	return h;
}

template<typename Key, typename Value>
unsigned int SkipListMap<Key, Value>::level_count(unsigned int level) const
{
	unsigned int length= 0;
	unsigned int h = 0;
	h = height();
	if (h < level)
	{
		throw invalid_level();
	}
	else
	{
		unsigned int current;
		SkipListMapNode<Key, Value>* n;
		n = m_bot_left;
		for (current = 1; current < level; current++)
		{
			n = n->up;
		}
		while (n->next)
		{
			length = length + 1;
			n = n->next;
		}
	}
	return length;
}

template<typename Key, typename Value>
bool SkipListMap<Key, Value>::key_on_level
(const Key& key, unsigned int level) const
{
	unsigned int h;
	h = height();
	if (h < level)
	{
		throw invalid_level();
	}
	SkipListMapNode<Key, Value>* current;
	current = find(key);
	if (current->key != key)
	{
		return false;
	}
	unsigned int possible = 1;
	while (current->up && possible != level)
	{
		current = current->up;
		possible = possible + 1;
	}
	if (possible == level)
	{
		return true;
	}
	else
	{
		throw invalid_level();
	}
	
}

template<typename Key, typename Value>
void SkipListMap<Key, Value>::print_map()
{
	SkipListMapNode<Key, Value>* n;
	n = m_bot_left;
	while(n)
	{
		SkipListMapNode<Key, Value>* it;
		it = n;
		while (it)
		{
			if (!it->prev)
			{
				std::cout << "-inf ";
			}
			else
			{
				std::cout << it->key << " ";
			}
			it = it->up;
		}
		n = n->next;
		std::cout << std::endl;
	}
}
/*******************PRIVATE FUNCTIONS*******************************/

template <typename Key, typename Value>
SkipListMapNode<Key, Value>* SkipListMap<Key, Value>::find(Key key) const
{
	SkipListMapNode<Key, Value>* current;
	current = m_top_left;
	if (current)
	{
		while (current->next != nullptr && current->next->key <= key)
		{
			current = current->next;
		}
		while (current->down != nullptr)
		{
			current = current->down;
			while (current->next != nullptr && current->next->key <= key)
			{
				current = current->next;
			}
		}
		
	}
		
	return current;	
}

template <typename Key, typename Value>
void SkipListMap<Key, Value>::destroylist()
{
	SkipListMapNode<Key, Value>* n;
	SkipListMapNode<Key, Value>* it;
	n = m_bot_left;
	
	while(n)
	{
		auto right = n->next;
		it = n;
		while(it)
		{
			it->down = nullptr;
			it->next = nullptr;
			it->prev = nullptr;
			auto temp = it->up;
			delete it;
			it = temp;
		}
		n = right;
	}
}

#endif // __SKIPLISTMAP_HPP__
