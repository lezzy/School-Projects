//==============================================================================
// This file contains the implementation of the GameView class, which acts as
// you interface to the GUI library SDL.
//
// DO NOT MODIFY THIS FILE
//==============================================================================
#include "GameView.hpp"
#include <algorithm>
#include <SDL.h>
#include <SDL_ttf.h>

// struct containing all of the private data for GameView
struct GameView::GameViewImpl
{
    SDL_Rect board;
    SDL_Window *window;
    SDL_Renderer *renderer;
    TTF_Font *font;
    std::vector<Cell> red_apples;
    std::vector<Cell> yellow_apples;
    std::vector<Cell> snake;
    std::vector<Cell> boundary;
    bool game_over;
};

// un-named namespace with private methods for GameView
namespace
{
    void draw_cells(SDL_Renderer *renderer, SDL_Rect board, const std::vector<Cell> cells)
    {
        SDL_Rect *rects = new SDL_Rect[cells.size()];
        for (int i = 0; i < cells.size(); i++)
        {
            int cell_size = board.w / GAME_SIZE;
            rects[i] = {board.x + cell_size * cells.at(i).first + 1,
                        board.y + cell_size * cells.at(i).second + 1,
                        cell_size-2, cell_size-2};
        }
        SDL_RenderFillRects(renderer, rects, cells.size());
        delete[] rects;
    }


    void draw_game_over(SDL_Renderer *renderer, TTF_Font *font, SDL_Rect board)
    {
        SDL_Color fg = {255, 255, 255, 255};
        SDL_Surface *ts = TTF_RenderText_Blended(font, "Game Over", fg);
        SDL_Texture *tx = SDL_CreateTextureFromSurface(renderer, ts);
        double text_scale = 0.9 * double(board.w) / double(ts->w);
        int text_w = text_scale * ts->w;
        int text_h = text_scale * ts->h;
        SDL_Rect box = {board.x + (board.w - text_w)/2,
                        board.y + (board.h - text_h)/2,
                        text_w, text_h};
        SDL_RenderCopy(renderer, tx, nullptr, &box);
        SDL_DestroyTexture(tx);
        SDL_FreeSurface(ts);
    }

}


// Implementations of public methods of GameView
GameView::GameView()
    : pimpl(nullptr)
{
    pimpl = new GameView::GameViewImpl;
    pimpl->board = {0,0,400,400};
    SDL_Init(SDL_INIT_VIDEO);
    pimpl->window = SDL_CreateWindow( "Project 4",
            SDL_WINDOWPOS_CENTERED, SDL_WINDOWPOS_CENTERED,
            pimpl->board.w, pimpl->board.h, SDL_WINDOW_RESIZABLE);
    pimpl->renderer = SDL_CreateRenderer(
            pimpl->window, -1, SDL_RENDERER_SOFTWARE);
    TTF_Init();
    pimpl->font = TTF_OpenFont("fonts/zombie.ttf", 120);
    pimpl->game_over = false;

    // Fill boundary
    unsigned int size = 39;
    for (int i = 0; i < size; i ++)
    {
        pimpl->boundary.push_back(std::make_pair(0, i));
        pimpl->boundary.push_back(std::make_pair(size, i + 1));
        pimpl->boundary.push_back(std::make_pair(i, size));
        pimpl->boundary.push_back(std::make_pair(i + 1, 0));
    }

}

GameView::~GameView()
{
    TTF_CloseFont(pimpl->font);
    TTF_Quit();
    SDL_DestroyRenderer(pimpl->renderer);
    SDL_DestroyWindow(pimpl->window);
    SDL_Quit();
    delete pimpl;
}

std::vector<Cell>& GameView::red_apples() { return pimpl->red_apples; }
const std::vector<Cell>& GameView::red_apples() const { return pimpl->red_apples; }

std::vector<Cell>& GameView::yellow_apples() { return pimpl->yellow_apples; }
const std::vector<Cell>& GameView::yellow_apples() const { return pimpl->yellow_apples; }

std::vector<Cell>& GameView::snake() { return pimpl->snake; }
const std::vector<Cell>& GameView::snake() const { return pimpl->snake; }

std::vector<Cell>& GameView::boundary() { return pimpl->boundary; }
const std::vector<Cell>& GameView::boundary() const { return pimpl->boundary; }

bool& GameView::game_over() { return pimpl->game_over; }
const bool& GameView::game_over() const { return pimpl->game_over; }

unsigned int GameView::time() { return SDL_GetTicks(); }

GameEvent GameView::get_event()
{
    SDL_Event se;
    GameEvent ge = GameEvent::Null;
    while ( SDL_PollEvent( &se ) ) {
        switch ( se.type ) {
            case SDL_QUIT:
                ge = GameEvent::Quit; break;
            case SDL_KEYDOWN:
                switch ( se.key.keysym.sym )
                {
                    case SDLK_UP:
                        ge = GameEvent::Up; break;
                    case SDLK_DOWN:
                        ge = GameEvent::Down; break;
                    case SDLK_LEFT:
                        ge = GameEvent::Left; break;
                    case SDLK_RIGHT:
                        ge = GameEvent::Right; break;
                    case SDLK_q:
                        ge = GameEvent::Quit; break;
                }
                break;
            case SDL_WINDOWEVENT:
                switch ( se.window.event )
                {
                    case SDL_WINDOWEVENT_SIZE_CHANGED:
                        int width = se.window.data1;
                        int height = se.window.data2;
                        int board_size = (std::min(width, height) / 40) * 40;
                        pimpl->board = {std::max(0, (width - board_size) / 2),
                                        std::max(0, (height - board_size) / 2),
                                        board_size,
                                        board_size};
                        break;
                }
                break;
            default:
                break;
        }
    }
    return ge;
}

void GameView::render()
{
    SDL_SetRenderDrawColor(pimpl->renderer, 128, 128, 128, 255);
    SDL_RenderClear(pimpl->renderer);
    SDL_SetRenderDrawColor(pimpl->renderer, 0, 0, 0, 255);
    SDL_RenderFillRect(pimpl->renderer, &(pimpl->board));

    SDL_SetRenderDrawColor(pimpl->renderer, 0, 128, 255, 255);
    draw_cells(pimpl->renderer, pimpl->board, pimpl->boundary);

    SDL_SetRenderDrawColor(pimpl->renderer, 255, 0, 0, 255);
    draw_cells(pimpl->renderer, pimpl->board, pimpl->red_apples);

    SDL_SetRenderDrawColor(pimpl->renderer, 255, 255, 0, 255);
    draw_cells(pimpl->renderer, pimpl->board, pimpl->yellow_apples);

    SDL_SetRenderDrawColor(pimpl->renderer, 0, 255, 0, 255);
    draw_cells(pimpl->renderer, pimpl->board, pimpl->snake);

    if (pimpl->game_over) draw_game_over(pimpl->renderer, pimpl->font, pimpl->board);

    SDL_RenderPresent(pimpl->renderer);

    SDL_Delay(10);
}

