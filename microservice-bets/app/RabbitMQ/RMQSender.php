<?php

namespace App\RabbitMQ;

use PhpAmqpLib\Channel\AMQPChannel;
use PhpAmqpLib\Connection\AMQPStreamConnection;
use PhpAmqpLib\Message\AMQPMessage;

class RMQSender
{
    public string $exchange;
    public string $queue;
    public string $routing_key;
    private AMQPChannel $channel;
    function __construct() {
        $this->exchange = env('RABBITMQ_BETS_EXCHANGE');
        $this->queue = env('RABBITMQ_BETS_QUEUE');
        $this->routing_key = env('RABBITMQ_BETS_ROUTING_KEY');
        //init connection and channel with rabbitmq
        $connection = new AMQPStreamConnection('localhost', '5672', 'guest', 'guest', '/');
        $this->channel = $connection->channel();
    }

    public function sendMessageFinishBets(string $messageBody): void{
        $message = new AMQPMessage($messageBody, array('content_type' => 'text/plain', 'delivery_mode' => AMQPMessage::DELIVERY_MODE_PERSISTENT));

        $this->channel->basic_publish($message, $this->exchange, $this->routing_key);
    }

}
