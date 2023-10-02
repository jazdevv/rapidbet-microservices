<?php

namespace App\Http\Middleware;

use Closure;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Http;
use Illuminate\Support\Facades\Log;
use PhpParser\Error;
use Symfony\Component\HttpFoundation\Response;

class ProtectedAdmin
{
    /**
     * Handle an incoming request.
     *
     * @param  \Closure(\Illuminate\Http\Request): (\Symfony\Component\HttpFoundation\Response)  $next
     */
    public function handle(Request $request, Closure $next): Response
    {
        //require jwt
        $validated = $request->validate([
            'jwtBet' => ['required']
        ]);
        //validate jwt with admin microservices
        $dataToSend = [
            'jwt' => $validated['jwtBet']
        ];
        Log::info('middleware protected admin');
        $responseHttp = Http::withBody(json_encode($dataToSend), 'application/json')->get('http://127.0.0.1:3001/api/v1/auth/authorized/admin/jwt');
        if ($responseHttp->successful()) {
            $responseJson = $responseHttp->json();
            return $next($request);
        }else{
            return response([
                'status' => 'fail',
                'message'=>'user is not authorized',
                'data' => null
            ],400);
        }

    }
}
