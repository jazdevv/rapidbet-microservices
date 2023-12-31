<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\Bets;
use App\Http\Middleware\ProtectedAdmin;
/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider and all of them will
| be assigned to the "api" middleware group. Make something great!
|
*/

Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
    return $request->user();
});

Route::prefix('/v1')->group(function (){

    Route::middleware(ProtectedAdmin::class)->prefix('/admin')->group(function (){
        Route::post('/newGame',[Bets::class,'newGame']);
        Route::post('/newGameBet',[Bets::class,'newGameBet']);
        Route::post('/setWinnerGameBet',[Bets::class,'setWinnerGameBet']);
    });

    Route::prefix('/bets')->group(function (){
        Route::get('/all',[Bets::class,'getGames']);
    });


});



