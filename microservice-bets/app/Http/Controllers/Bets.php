<?php

namespace App\Http\Controllers;

use App\Models\Game;
use App\Models\GameBet;
use Illuminate\Http\Request;

class Bets extends Controller
{
    public function newGame(Request $request){
        try{
            $validated = $request->validate([
                'jwtBet' => ['required'],
                'id' => ['required'],
                'name' => ['required']
            ]);

            $game = new Game();
            $game -> id = $validated['id'];
            $game -> name = $validated['name'];
            $game->save();

            return response()->json([
                'status' => 'succes',
                'message' => 'game created correctly'
            ]);
        }catch(\Exception $e){
            return response()->json([
                'status' => 'fail',
                'message' => "$e"
            ]);
        }
    }

    public function getGames(){
        try{
            $games = Game::get();

            return response()->json([
                'status' => 'succes',
                'message' => 'game created correctly',
                'data' => $games
            ]);
        }catch(\Exception $e){
            return response()->json([
                'status' => 'fail',
                'message' => "$e",
                'data' => ''
            ]);
        }
    }

    public function newGameBet(Request $request){
        try{
            $validated = $request->validate([
                'gameId' => ['required'],
                'name' => ['required'],
                'round' => ['required'],
                'team1name' => ['required'],
                'team2name' => ['required'],
                'startDateTimestamp' => ['required'],
                'endDateTimestamp' => ['required']
            ]);

            $gameBet = new GameBet();
            $gameBet -> game_id = $validated['gameId'];
            $gameBet -> name = $validated['name'];
            $gameBet -> round = $validated['round'];
            $gameBet -> team1name = $validated['team1name'];
            $gameBet -> team2name = $validated['team2name'];
            $gameBet -> startDateTimestamp = $validated['startDateTimestamp'];
            $gameBet -> endDateTimestamp = $validated['endDateTimestamp'];
            $gameBet -> team1amount = 0.0;
            $gameBet -> team2amount = 0.0;
            $gameBet -> totalAmount = 0.0;
            $gameBet -> winner = 0.0;
            $gameBet -> odd = 0.0;

            $gameBet -> save();


            return response()->json([
                'status' => 'succes',
                'message' => 'gamebet created correctly',
                'data' => $gameBet
            ]);
        }catch(\Exception $e){
            return response()->json([
                'status' => 'fail',
                'message' => "$e",
                'data' => ''
            ]);
        }

    }

    public function setWinnerGameBet(){

    }

    private function restrictToAdmins(){

    }
}
