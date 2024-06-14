package Modelo

data class tbTickete(
    val UUID: String,
    var titulo: String,
    val descripcion: String,
    val autor: String,
    val email:String,
    val fechaInicio: String,
    val estado: String,
    val fechaFinal: String

)
