package Modelo

import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {

    fun cadenaConexion() : Connection? {
        try{
            val url = "jdbc:oracle:thin:@10.10.0.171:1521:xe"
            val usuario = "SYSTEM"
            val contra = "ITR2024"

            val connection= DriverManager.getConnection(url, usuario, contra)
            return connection
        }
        catch (e: Exception){
            println("este es el error:$e")
            return null
        }
    }
}