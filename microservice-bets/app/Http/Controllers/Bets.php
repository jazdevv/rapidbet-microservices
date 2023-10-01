<?php

namespace App\Http\Controllers;

use App\Models\Game;
use App\Models\GameBet;
use App\RabbitMQ\RMQSender;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

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

    public function setWinnerGameBet(Request $request){
        try{
            $validated = $request->validate([
                'gameId' => ['required'],
                'round' => ['required'],
                'winnerIndex' => ['required']
            ]);
            $gameId = $validated["gameId"];
            $winnerIndex = $validated["winnerIndex"];
            //calculate odd
            $data = DB::select("SELECT team1amount, team2amount, totalAmount FROM `game_bets` WHERE game_id = $gameId  AND round = $validated[round];");
            $result = collect($data) -> first();

            $totalAmount = $result -> totalAmount;
            $oddRate = 0.0;
            $sendMessage = false;
            if($totalAmount > 0.0){


                if($winnerIndex == 0){
                    $teamAmount = $result -> team1amount;
                    if($teamAmount > 0.0){
                        $oddRate = $totalAmount / $teamAmount;
                        $sendMessage = true;
                    }
                }else{
                    $teamAmount = $result -> team2amount;
                    if($teamAmount > 0.0){
                        $oddRate = $totalAmount / $teamAmount;
                        $sendMessage = true;
                    }
                }

                if($sendMessage){
                    // update gamebet data
                    DB::statement("UPDATE `game_bets` SET `odd`=$oddRate, `winner`=$winnerIndex WHERE game_id = $gameId  AND round = $validated[round];");

                    // emit rabbitmq message for the userbet microservice
                    $rabbitmq = new RMQSender();
                    $rabbitmq->sendMessageFinishBets('message from laravel bets microservice');
                }

            }else{
                DB::statement("UPDATE `game_bets` SET `odd`=$oddRate, `winner`=$winnerIndex WHERE game_id = $gameId  AND round = $validated[round];");
            }


            return response()->json([
                'status' => 'succes',
                'message' => '',
                'data' => [
                    'oddRate' => $oddRate,
                    'sendMessage' => $sendMessage
                ]
            ]);
        }catch(\Exception $e){
            return response()->json([
                'status' => 'fail',
                'message' => "$e",
                'data' => ''
            ]);
        }
    }

    private function restrictToAdmins(){

    }
}
