package com.example.handyman_android_app.ui.gallery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.handyman_android_app.Adapters.ResponseAdapter;
import com.example.handyman_android_app.Adapters.ResponseAdapter;
import com.example.handyman_android_app.Calculator;
import com.example.handyman_android_app.CreateGig;
import com.example.handyman_android_app.Model.ResponseModel;
import com.example.handyman_android_app.Model.ResponseModel;
import com.example.handyman_android_app.R;
import com.example.handyman_android_app.databinding.FragmentGalleryBinding;
import com.example.handyman_android_app.databinding.FragmentHomeBinding;
import com.example.handyman_android_app.loginActivity;
import com.example.handyman_android_app.p_make_response;
import com.example.handyman_android_app.signupActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;



public class GalleryFragment extends Fragment  {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;
    public static RecyclerView recyclerView;
    private Context context;
    public static ArrayList<ResponseModel> arrayList;
    public static ResponseAdapter gigAdapter;
    private Button bt1;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        bt1=root.findViewById(R.id.p_cal);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Calculator.class));
            }
        });

        context=getContext();
        arrayList=new ArrayList<>();

        recyclerView=root.findViewById(R.id.r_recycleView_activeGigs);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("Responses");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                if (task.isSuccessful() && task.getResult()!=null){

                    for (DocumentSnapshot data:task.getResult()){

                        ResponseModel gigModel=new ResponseModel(data.getDouble("ResponseID"),
                                data.getString("category"),
                                data.getString("description"),
                                data.getString("location"),
                                data.getString("title"),
                                data.getString("customerName"));



                        arrayList.add(gigModel);

                    }

                    gigAdapter=new ResponseAdapter(arrayList,context);
                    recyclerView.setAdapter(gigAdapter);

                }

            }
        });

        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}