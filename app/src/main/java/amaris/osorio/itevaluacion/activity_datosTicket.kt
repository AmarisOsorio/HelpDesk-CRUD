package amaris.osorio.itevaluacion

import Modelo.ClaseConexion
import Modelo.tbTickete
import RecyclerViewHelpers.Adaptador
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class activity_datosTicket : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_datos_ticket)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtTitulo = findViewById<EditText>(R.id.txtTitulo)
        val txtDescripcion = findViewById<EditText>(R.id.txtDescripcion)
        val txtAutor = findViewById<EditText>(R.id.txtAutor)
        val txtEmail = findViewById<EditText>(R.id.txtCorreo)
        val txtFechaInicio = findViewById<EditText>(R.id.txtFechaInicio)
        val txtEstado = findViewById<EditText>(R.id.txtEstado)
        val txtFechaFinal = findViewById<EditText>(R.id.txtFechaFinal)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val rcvTicket = findViewById<RecyclerView>(R.id.rcvTicket)

        rcvTicket.layoutManager = LinearLayoutManager(this)

        ///////////////////////TODO: Mostrar Datos////////////////////////////////////////

        fun obtenerTickets(): List<tbTickete>{
            val  objConexion = ClaseConexion().cadenaConexion()

            val statement = objConexion?.createStatement()
            val resultSet = statement?.executeQuery("Select * from tbTickete")!!

            val listaTickets = mutableListOf<tbTickete>()

            while (resultSet.next()){
                val uuid = resultSet.getString("UUID")
                val titulo = resultSet.getString("titulo")
                val descripcion = resultSet.getString("descripcion")
                val autor = resultSet.getString("autor")
                val email = resultSet.getString("email")
                val fechInicio = resultSet.getString("fechainicio")
                val estado = resultSet.getString("estado")
                val fechaFinal = resultSet.getString("fechafinal")

                val todos = tbTickete(uuid,titulo,descripcion,autor,email,fechInicio,estado,fechaFinal)

                listaTickets.add(todos)
            }
            return listaTickets
        }

        CoroutineScope(Dispatchers.IO).launch {
            val TicketsDB = obtenerTickets()
            withContext(Dispatchers.Main){
                val adapter = Adaptador(TicketsDB)
                rcvTicket.adapter = adapter
            }
        }

        /////////////////////TODO: Guardar Datos//////////////////////////////////////
        btnGuardar.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch{
                val objConexion = ClaseConexion().cadenaConexion()
                val addTicket = objConexion?.prepareStatement("Insert into tbTickete(UUID,titulo,descripcion,autor,email,fechaInicio,estado,fechaFinal) values(?,?,?,?,?,?,?,?)")!!
                addTicket.setString(1, UUID.randomUUID().toString())
                addTicket.setString(2, txtTitulo.text.toString())
                addTicket.setString(3, txtDescripcion.text.toString())
                addTicket.setString(4, txtAutor.text.toString())
                addTicket.setString(5, txtEmail.text.toString())
                addTicket.setString(6,txtFechaInicio.text.toString())
                addTicket.setString(7, txtEstado.text.toString())
                addTicket.setString(8,txtFechaFinal.text.toString())
                addTicket.executeUpdate()

                val actTicket = obtenerTickets()
                withContext(Dispatchers.Main){
                    (rcvTicket.adapter as? Adaptador)?.actualizarRecyclerView(actTicket)
                }
            }
        }


    }
}