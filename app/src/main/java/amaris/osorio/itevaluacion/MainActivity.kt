package amaris.osorio.itevaluacion

import Modelo.ClaseConexion
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtCorreoLog = findViewById<EditText>(R.id.txtCorreoLog)
        val txtContraLog = findViewById<EditText>(R.id.txtContraLog)
        val btnAcceder = findViewById<Button>(R.id.btnAcceder)
        val btnRegistro = findViewById<Button>(R.id.btnRegistrarse)

        btnAcceder.setOnClickListener {
            val pantallaPrincipal = Intent(this, activity_datosTicket::class.java)

            GlobalScope.launch(Dispatchers.IO) {
                val objConexion = ClaseConexion().cadenaConexion()

                val comprobarUsuario = objConexion?.prepareStatement("SELECT * FROM tbUsuarios WHERE correoElectronico = ? AND clave = ?")!!
                comprobarUsuario.setString(1, txtCorreoLog.text.toString())
                comprobarUsuario.setString(2, txtContraLog.text.toString())
                val resultado = comprobarUsuario.executeQuery()
                if (resultado.next()) {
                    startActivity(pantallaPrincipal)
                } else {
                    println("Usuario no encontrado, verifique las credenciales")
                }
            }
        }

        btnRegistro.setOnClickListener {
            val pantallaRegistrarme = Intent(this, activity_Registro::class.java)
            startActivity(pantallaRegistrarme)
        }


    }
}