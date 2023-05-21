package com.example.firbase.fragment_admin

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firbase.R
import com.example.firbase.view.DashBoardAdminActivity
import com.example.firbase.adapter.CategoryAdminAdapter
import com.example.firbase.databinding.FragmentHomeAdminBinding
import com.example.firbase.model.Category
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.dialog_progress.*

class HomeAdminFragment : Fragment() {

    private var _binding: FragmentHomeAdminBinding? = null
    private val binding get() = _binding!!
    lateinit var db: FirebaseFirestore
    lateinit var data: ArrayList<Category>

    //private var progressDialog: ProgressDialog? = null
    lateinit var d: Activity
    private lateinit var progressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = Firebase.firestore
        d = (activity as DashBoardAdminActivity)
        data = ArrayList()
        showDialog("جار التحميل ...")
        getAllCategory()
        binding.btnAdd.setOnClickListener {
            (d as DashBoardAdminActivity).makeCurrentFragment(AddCategoryFragment())
        }

//        val callback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
////                findNavController().navigate(R.id.action_fourthFragment_to_secondFragment)
//               d.finish()
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }


    fun getAllCategory() {
        val t1 = Thread(Runnable {
            db.collection("Category")
                .get()
                .addOnSuccessListener {
                    for (document in it) {
                        val id = document.id
                        val name = document.getString("name")
                        val description = document.getString("description")
                        val doctorName = document.getString("doctorName")
                        val img = document.getString("img")
                        val imgName = document.getString("imgName")

                        val category =
                            Category(id, name!!, img!!, imgName!!, description!!, doctorName!!)
                        data.add(category)
                    }
                    var categoryAdapter = CategoryAdminAdapter(d, data)
                    binding.rv.layoutManager = LinearLayoutManager(d)
                    binding.rv.adapter = categoryAdapter
                    hideDialog()
                }
                .addOnFailureListener {
                    Toast.makeText(d, it.message, Toast.LENGTH_SHORT).show()
                    hideDialog()
                }
        })
        t1.start()
    }


    private fun showDialog(text: String) {
        progressDialog = Dialog(d)
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