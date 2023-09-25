<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\HasMany;

class Game extends Model
{
    use HasFactory;
    protected $table = 'games';
    protected $primaryKey = 'id';
    public $fillable = [
        'name','id'
    ];
    public $timestamps = false;
    public function gameBets() :HasMany{
        return $this->hasMany(GameBet::class);
    }

}
