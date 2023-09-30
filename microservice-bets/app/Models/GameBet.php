<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;

class GameBet extends Model
{
    use HasFactory;
    protected $table = 'game_bets';
    public $timestamps = false;

    public function game() :BelongsTo{
        return $this->belongsTo(Game::class);
    }
}
