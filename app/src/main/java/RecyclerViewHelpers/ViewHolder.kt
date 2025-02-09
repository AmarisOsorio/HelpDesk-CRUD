package RecyclerViewHelpers

import amaris.osorio.itevaluacion.R
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val imgBorrar: ImageView = view.findViewById(R.id.imgBorrar)
    val textView: TextView = view.findViewById(R.id.txt_productoCard)
    val imgEditar: ImageView = view.findViewById(R.id.img_editar)
}