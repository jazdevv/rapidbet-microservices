<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('game_bets', function (Blueprint $table) {
            $table->timestamps();
            $table->unsignedBigInteger('game_id');
            $table->string('name');
            $table->float('round');
            $table->string('team1name');
            $table->string('team2name');
            $table->float('team1amount');
            $table->float('team2amount');
            $table->float('totalAmount');
            $table->float('winner');
            $table->float('odd');
            $table->float('endDateTimestamp');
            $table->float('startDateTimestamp');

            //define composite primary key
            $table->primary(['game_id','round']);

            //define foreign keys
            $table->foreign('game_id')
                ->references('id')
                ->on('games');
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('game_bets');
    }
};
