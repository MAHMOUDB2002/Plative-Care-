package com.example.firbase.fragment_user

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firbase.R
import com.example.firbase.adapter.SearchAdapter
import com.example.firbase.databinding.FragmentSearchBinding
import com.example.firbase.model.Category
import com.example.firbase.utils.Constants
import com.example.firbase.view.DashBoardAdminActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.dialog_progress.*


class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    lateinit var db: FirebaseFirestore
//    lateinit var d: Activity
    lateinit var data:ArrayList<Category>
    //private var progressDialog: ProgressDialog? = null
    private lateinit var progressDialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        db = FirebaseFirestore.getInstance()
//        d = (activity as DashBoardAdminActivity)
        data= ArrayList<Category>()
        getAllProduct()
    }

    private fun getAllProduct() {
        showDialog("جاري التحميل ...")
        db.collection(Constants.CATEGORY)
            .get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    for (document in it) {
                        val id =document.id
                        val name = document.getString("name")
                        val description = document.getString("description")
                        val img = document.getString("img")
                        val imgName = document.getString("imgName")
                        val doctorName = document.getString("doctorName")

                        val category=Category(id,name!!,img!!,imgName!!,description!!,doctorName!!)
                        data.add(category)
                    }
                    var productAdapter = SearchAdapter(requireActivity(), data)
                    binding.rvSearch.layoutManager = LinearLayoutManager(requireContext())
                    binding.rvSearch.adapter = productAdapter
                    hideDialog()
                    binding.etSearch2.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                        }

                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                        }

                        override fun afterTextChanged(s: Editable?) {
                            val text = s.toString()
                            //(binding.list.adapter as RecAdapter).search(text)
                            binding.etSearch2.clearFocus()
                        }
                    })
                    binding.etSearch2.addTextChangedListener(object: TextWatcher {
                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                        }

                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                        }

                        override fun afterTextChanged(s: Editable?) {

                            (binding.rvSearch.adapter as SearchAdapter).search(s.toString())
                        }
                    })
                }
                hideDialog()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(),it.message, Toast.LENGTH_SHORT).show()
                hideDialog()
            }    }


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
