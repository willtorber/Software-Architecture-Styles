<?php
/**
 * Created by PhpStorm.
 * User: WILLTOR
 * Date: 31/03/2018
 * Time: 10:42 AM
 */
require 'repository/DataBase.php';

use PHPSocketIO\SocketIO;

class ServerSocket
{
    private $socketIO;
    private $database;


    public function __construct()
    {
        $this->socketIO = new SocketIO(2020);
    }

    public function configuration()
    {
        $this->socketIO->on('connection', function($socket)
        {


            $socket->on('start_database', function ($data_database) use ($socket)
            {
                $data = json_decode($data_database, true);
                $this->database = DataBase::getDataBaseInstance(
                    $data['database'],
                    $data['domain'],
                    $data['port'],
                    $data['name_database'],
                    $data['username'],
                    $data['passwd']
                );
            });

            $socket->on('query', function($query) use ($socket)
            {
                $query = strtolower($query);
                var_dump($query);
                $result = null;

                if (strpos($query, 'update') === false 
                    and strpos($query, 'delete') === false 
                    and strpos($query, 'insert') === false)
                {
                    $result = $this->database->getData($query);
                }else{
                    $result = $this->database->executeQuery($query);
                }                
                $socket->emit("result_query", $result);
            });
        }); 

    }
}