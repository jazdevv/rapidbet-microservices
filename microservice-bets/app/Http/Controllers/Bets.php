<?php

namespace App\Http\Controllers;

use App\Models\Game;
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

    public function newGameBet(){

    }

    public function setWinnerGameBet(){

    }

    private function restrictToAdmins(){

    }
}
