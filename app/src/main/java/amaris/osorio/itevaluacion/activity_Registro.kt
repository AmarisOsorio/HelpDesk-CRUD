package amaris.osorio.itevaluacion

import Modelo.ClaseConexion
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class activity_Registro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtCorreo = findViewById<EditText>(R.id.txtCorreoRegistro)
        val txtContra = findViewById<EditText>(R.id.txtContraRegistro)
        val txtConfirmaContra = findViewById<EditText>(R.id.txtCofirCorreo)
        val btnCrear = findViewById<Button>(R.id.btnCrear)
        val atras = findViewById<ImageView>(R.id.imgAtras)

        btnCrear.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val objConexion = ClaseConexion().cadenaConexion()

                val crearUsuario =
                    objConexion?.prepareStatement("INSERT INTO tbUsuarios(UUID_usuario, correoElectronico, clave) VALUES (?, ?, ?)")!!
                crearUsuario.setString(1, UUID.randomUUID().toString())
                crearUsuario.setString(2, txtCorreo.text.toString())
                crearUsuario.setString(3, txtContra.text.toString())
                crearUsuario.executeUpdate()
                withContext(Dispatchers.Main) {

                    Toast.makeText(this@activity_Registro, "Usuario creado", Toast.LENGTH_SHORT)
                        .show()
                    txtCorreo.setText("")
                    txtContra.setText("")
                    txtConfirmaContra.setText("")
                }
            }
        }

        atras.setOnClickListener{
            val atras = Intent(this, MainActivity::class.java)
            startActivity(atras)
        }
    }
}