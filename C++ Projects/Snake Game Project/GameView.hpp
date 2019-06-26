//==============================================================================
// This file contains the header for the GameView class, which acts as your
// interface to the GUI library SDL
//
// DO NOT MODIFY THIS FILE
//==============================================================================
#ifndef __GAMEVIEW_HPP__
#define __GAMEVIEW_HPP__

#include <utility>
#include <vector>

const int GAME_SIZE = 40;
enum class GameEvent {Null, Up, Down, Left, Right, Quit};
using Cell = std::pair<int,int>;

class GameView {
    
    public:
        GameView();
        ~GameView();
        // The next two lines remove the default copy constructor and assignment 
        // operator. This prevents the class from being copied, which would
        // causes errors.
        GameView(const GameView&) = delete;
        GameView& operator=(const GameView&) = delete;
        // The next ten method are used to set the positions of the apples,
        // snake, boundary, and the state of the game over message.
        std::vector<Cell>& red_apples();
        const std::vector<Cell>& red_apples() const;
        std::vector<Cell>& yellow_apples();
        const std::vector<Cell>& yellow_apples() const;
        std::vector<Cell>& snake();
        const std::vector<Cell>& snake() const;
        std::vector<Cell>& boundary();
        const std::vector<Cell>& boundary() const;
        bool& game_over();
        const bool& game_over() const;
        // The remaining methods are the same as in Project 1.
        GameEvent get_event();
        void render();
        unsigned int time();
    
    private:
        struct GameViewImpl;
        GameViewImpl *pimpl;

};

#endif // __GAMEVIEW_HPP__
