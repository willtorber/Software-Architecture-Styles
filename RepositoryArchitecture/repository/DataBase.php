<?php
/**
 * Created by PhpStorm.
 * User: WILLTOR
 * Date: 9/04/2018
 * Time: 11:30 PM
 */

class DataBase
{
    private $conecction;
    private static $instance;

    private function __construct($database = 'mysql', $domain = 'localhost' , $port = 3306 , $name_database, $username, $passwd)
    {
        try {
            $this->conecction = new PDO("$database:host=$domain;port=$port;dbname=$name_database", $username, $passwd);
            $this->conecction->exec("SET CHARACTER SET utf8");
        } catch (PDOException $e) {
            print "Error!: " . $e->getMessage();
            die();
        }
    }

    public static function getDataBaseInstance($database, $domain, $port, $name_database, $username, $passwd)
    {
        if (!isset(static::$instance)) {
            $class = __CLASS__;
            static::$instance = new $class($database, $domain, $port, $name_database, $username, $passwd);
        }
        return self::$instance;
    }

    private function __clone()
    {
        trigger_error('La clonación de este objeto no está permitida', E_ERROR);
    }

    public function getData($sql)
    {
        $result = array();
        foreach ($rows = $this->conecction->query($sql) as $row)
        {
            array_push(
                $result,
                array(
                    'id' => $row['id'],
                    'fecha' => $row['fecha'],
                    'latitud' => $row['latitud'],
                    'inestabilidad' => $row['inestabilidad'],
                    'temperatura' => $row['temperatura'],
                    'humedad' => $row['humedad']
                ));
        }

        return array('result' => -1, "data" => $result);
    }

    public function executeQuery($sql)
    {
        return array('result' => $this->conecction->exec($sql));
    }

}