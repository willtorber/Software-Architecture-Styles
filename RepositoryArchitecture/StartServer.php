<?php
require './vendor/autoload.php';
require 'communication_core/ServerSocket.php';

use Workerman\Worker;

$server = new ServerSocket();
$server->configuration();

Worker::runAll();
