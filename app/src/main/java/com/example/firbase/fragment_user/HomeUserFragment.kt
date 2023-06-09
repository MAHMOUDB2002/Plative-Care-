package com.example.firbase.fragment_user

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firbase.R
import com.example.firbase.adapter.CategoryUserAdapter
import com.example.firbase.databinding.FragmentHomeUserBinding
import com.example.firbase.model.Category
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.dialog_progress.*


class HomeUserFragment : Fragment() {

    private var _binding: FragmentHomeUserBinding? = null
    private val binding get() = _binding!!
    lateinit var db: FirebaseFirestore
    lateinit var data: ArrayList<Category>

    //private var progressDialog: ProgressDialog? = null
    private lateinit var progressDialog: Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeUserBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = Firebase.firestore
        // d2=(activity as DashBoardUserActivity)
        data = ArrayList()
        showDialog("جار التحميل ...")
        getAllCategory()


//                val callback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
////                findNavController().navigate(R.id.action_fourthFragment_to_secondFragment)
//               requireActivity().finish()
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

    }


    fun getAllCategory() {
        val t1 = Thread(Runnable {
            val sharedP = requireActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE)
            val idSubscribeCategory = sharedP.getString("idSubscribeCategory", "0").toString()
            db.collection("Category")
                .get()
                .addOnSuccessListener {
                    for (document in it) {
                        val id = document.id
                        val name = document.getString("name")
                        val description = document.getString("description")
                        val img = document.getString("img")
                        val imgName = document.getString("imgName")
                        val doctorName = document.getString("doctorName")

                        val category =
                            Category(id, name!!, img!!, imgName!!, description!!, doctorName!!)
                        data.add(category)
                    }
                    var categoryUserAdapter = CategoryUserAdapter(requireActivity(), data)
                    binding.rvUser.layoutManager = LinearLayoutManager(requireActivity())
                    binding.rvUser.adapter = categoryUserAdapter
                    hideDialog()
                }
                .addOnFailureListener {
                    Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                    hideDialog()
                }
        })
        t1.start()
    }


    private fun showDialog(text: String) {
        progressDialog = Dialog(requireContext())
        progressDialog!!.setContentView(R.layout.dialog_progress)
        progressDialog.tv_progress_text.text = text

        progressDialog!!.setCancelable(false)
        progressDialog!!.setCanceledOnTouchOutside(false)
        progressDialog!!.show()

    }

    private fun hideDialog() {
        if (progressDialog.isShowing)
            progressDialog.dismiss()
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
    }
}




