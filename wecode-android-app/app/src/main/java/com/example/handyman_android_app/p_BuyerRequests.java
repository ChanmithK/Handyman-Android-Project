package com.example.handyman_android_app;
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

        import com.example.handyman_android_app.Adapters.RequestAdapter;
        import com.example.handyman_android_app.CreateGig;
        import com.example.handyman_android_app.Model.RequestsModel;
        import com.example.handyman_android_app.R;
        import com.example.handyman_android_app.databinding.FragmentPBuyerRequestsBinding;
        import com.example.handyman_android_app.loginActivity;
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

public class p_BuyerRequests extends Fragment  {

    private FragmentPBuyerRequestsBinding binding;
    private RecyclerView recyclerView;
    private Context context;
    private ArrayList<RequestsModel> arrayList;
    private RequestAdapter gigAdapter;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentPBuyerRequestsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        context=getContext();
        arrayList=new ArrayList<>();

        recyclerView=root.findViewById(R.id.r_recycleView_buyer_requests);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("Requests");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                if (task.isSuccessful() && task.getResult()!=null){

                    for (DocumentSnapshot data:task.getResult()){

                        RequestsModel gigModel=new RequestsModel(data.getDouble("RequestID"),
                                data.getString("category"),
                                data.getString("description"),
                                data.getString("location"),
                                data.getString("title"),
                                data.getString("customerName"));
                        arrayList.add(gigModel);

                    }

                    gigAdapter=new RequestAdapter(arrayList,context);
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