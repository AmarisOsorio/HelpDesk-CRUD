package RecyclerViewHelpers

import Modelo.ClaseConexion
import Modelo.tbTickete
import amaris.osorio.itevaluacion.R
import amaris.osorio.itevaluacion.item_card
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Adaptador(private var Datos: List<tbTickete>) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card, parent, false)
        return ViewHolder(vista)
    }

    override fun getItemCount() = Datos.size

    fun actualizarRecyclerView(nuevaLista: List<tbTickete>){
        Datos = nuevaLista
        notifyDataSetChanged()
    }

    fun eliminarRegistro(titulo: String, posicion: Int){
        val listaDatos = Datos.toMutableList()
        listaDatos.removeAt(posicion)


        GlobalScope.launch(Dispatchers.IO){
            val objConexion = ClaseConexion().cadenaConexion()
            val deleteProducto = objConexion?.prepareStatement("delete tbTickete where titulo = ?")!!
            deleteProducto.setString(1, titulo)
            deleteProducto.executeUpdate()

            val commit = objConexion.prepareStatement("commit")
            commit.executeUpdate()
        }

        Datos = listaDatos.toList()
        notifyItemRemoved(posicion)
        notifyDataSetChanged()
    }

    fun actualizarListadoDespuesDeEditar(uuid: String, nuevoNombre: String){
        val identificador = Datos.indexOfFirst { it.UUID == uuid }
        Datos[identificador].titulo = nuevoNombre
        notifyItemChanged(identificador)
    }


    fun editarTicket(titulo: String,uuid: String){

        GlobalScope.launch(Dispatchers.IO){

            val objConexion = ClaseConexion().cadenaConexion()

            val updateTicket = objConexion?.prepareStatement("update tbTickete set titulo = ? where uuid = ?")!!
            updateTicket.setString(1, titulo)
            updateTicket.setString(2, uuid)
            updateTicket.executeUpdate()

            val commit = objConexion?.prepareStatement("commit")!!
            commit.executeUpdate()

            withContext(Dispatchers.Main){
                actualizarListadoDespuesDeEditar(uuid,titulo)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Ticket = Datos[position]
        holder.textView.text = Ticket.titulo

        val item = Datos[position]



        holder.imgBorrar.setOnClickListener {

            val context = holder.textView.context

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Eliminar")
            builder.setMessage("¿Estas seguro que deseas eliminar?")

            builder.setPositiveButton("Si"){
                    dialog, wich ->
                eliminarRegistro(Ticket.titulo, position)
            }

            builder.setNegativeButton("No"){
                    dialog, wich ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()


        }

        holder.imgEditar.setOnClickListener {
            val context = holder.itemView.context
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Editar nombre")

            val nuevoTitulo = EditText(context)
            nuevoTitulo.setHint(item.titulo)
            builder.setView(nuevoTitulo)

            builder.setPositiveButton("Actualizar"){dialog,wich ->
                editarTicket(nuevoTitulo.text.toString(),item.UUID)
            }

            builder.setNegativeButton("Cancelar"){dialog,wich ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }
    }







}
