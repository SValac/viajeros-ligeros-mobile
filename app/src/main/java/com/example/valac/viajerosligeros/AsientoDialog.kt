package com.example.valac.viajerosligeros

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.valac.viajerosligeros.clases.Viajero


class AsientoDialog : DialogFragment() {

    private var mNombre : TextView? = null
    private var mAbonado : TextView? = null
    private var mCosto : TextView? = null
    private var mTelefono : TextView? = null
    private var mNumeroHabitacion : TextView? = null
    private var mAcompañantes : TextView? = null
    private var mObservacionesComentarios : TextView? = null
    private var mAddMoney : ImageView? = null
    private var mEditarViajero : ImageView? = null
    private var mMakeCall : ImageView? = null
    private var mSendMessage : ImageView? = null

    private var mViajero : Viajero? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_template_asiento, container, false)
        mNombre = view.findViewById(R.id.nombre_txtvw)
        mAbonado = view.findViewById(R.id.abonado_cantidad_txtvw)
        mCosto = view.findViewById(R.id.total_costo_txtvw)
        mTelefono = view.findViewById(R.id.telefono_txtvw)
        mNumeroHabitacion = view.findViewById(R.id.habitacion_no_txtvw)
        mAcompañantes = view.findViewById(R.id.acompañantes_txtvw)
        mObservacionesComentarios = view.findViewById(R.id.observaciones_commentarios_txtvw)
        mAddMoney = view.findViewById(R.id.add_money_imgvw)
        mEditarViajero = view.findViewById(R.id.editar_viajero_imgvw)
        mMakeCall = view.findViewById(R.id.make_call_imgvw)
        mSendMessage = view.findViewById(R.id.send_message_imgvw)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            mViajero = it.getParcelable(VIAJERO_DATA)
        }

        mNombre?.text = mViajero?.nombre.toString()
        mAbonado?.text = mViajero?.anticipo_cantidad.toString()
        mCosto?.text = mViajero?.anticipo.toString()
        mTelefono?.text = mViajero?.telefono.toString()
        mNumeroHabitacion?.text = mViajero?.tipoHabitacion.toString()
        mAcompañantes?.text = mViajero?.representanteGrupo.toString()
        mObservacionesComentarios?.text =mViajero?.observacionesComentarios.toString()

        mMakeCall?.setOnClickListener { view ->
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${mViajero?.telefono}")
            startActivity(intent)
        }

       mSendMessage?.setOnClickListener { view ->
           val uri = Uri.parse("smsto:${mViajero?.telefono}")
           val intent = Intent(Intent.ACTION_SENDTO, uri)
           intent.putExtra("sms_body", "Here goes your message...")
           startActivity(intent)
       }

    }

    companion object {
        private const val VIAJERO_DATA = "viajero_data"

        fun newInstance(viajero: Viajero): AsientoDialog {
            val dialog = AsientoDialog()
            val bundle = Bundle()
            bundle.putParcelable(VIAJERO_DATA, viajero)
            dialog.arguments = bundle

            return dialog
        }
    }

}