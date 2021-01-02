package com.example.task.ui.DetailSubTask;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.task.Model.Staff.SubTask_;
import com.example.task.R;
import com.example.task.helpers.SessionManager;
import com.example.task.helpers.UrlHelper;
import com.example.task.helpers.VolleyMultipartRequest;
import com.example.task.ui.SubTaskCompleteStaff.SubTaskCompleteStaffFragment;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class DetailSubTaskFragment extends Fragment {
    SessionManager sessionManager;
    LinearLayout form, pbLayout;
    ScrollView scrollView;
    String linkDownloadFile;
    TextView tvDescription, tvAssignBy, tvDeadline, tvStatus, tvTaskName, tvKomentar;
    EditText etDescription;
    Button btnUpload, btnDownload;
    private int id_sub_task;
    private static final int REQUEST_PERMISSIONS = 100;
    private static final int PICK_IMAGE_REQUEST =1 ;
    private Bitmap bitmap;
    private RequestQueue rQueue;
    private ArrayList<HashMap<String, String>> arraylist;
    String url = "https://www.google.com";

    public DetailSubTaskFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_detail_sub_task, container, false);
        initializeComponent(root);
        buttonActions();
        return root;
    }

    protected void initializeComponent(View root){
        tvTaskName = root.findViewById(R.id.sub_task_detail_task_name);
        tvDescription = root.findViewById(R.id.sub_task_detail_description);
        tvAssignBy = root.findViewById(R.id.sub_task_detail_assign_by_name);
        tvDeadline = root.findViewById(R.id.sub_task_detail_deadline);
        tvKomentar = root.findViewById(R.id.sub_task_detail_komentar);
        tvStatus = root.findViewById(R.id.sub_task_detail_status);
        etDescription = root.findViewById(R.id.sub_task_detail_form_description);
        btnUpload = root.findViewById(R.id.sub_task_detail_form_buttonUploadImage);
        btnDownload = root.findViewById(R.id.sub_task_detail_file);
        form = root.findViewById(R.id.form_upload_detail_sub_task);
        scrollView = root.findViewById(R.id.sub_task_detail_scrollview_container);
        pbLayout = root.findViewById(R.id.sub_task_detail_pblayout);

        scrollView.setVisibility(View.GONE);
        refreshPage();
    }

    protected void  buttonActions(){
        btnDownload.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(UrlHelper.base_url+"/"+linkDownloadFile));
            startActivity(browserIntent);
        });
        btnUpload.setOnClickListener(v -> {
            pbLayout.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
            if ((ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                if ((ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE))) {

                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_PERMISSIONS);
                }
            } else {
                Log.e("Else", "Else");
                showFileChooser();
            }
        });
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("application/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // Get the Uri of the selected file
            Uri uri = data.getData();
            String uriString = uri.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();
            String displayName = null;

            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        Log.d("nameeeee>>>>  ",displayName);

                        uploadPDF(displayName,uri);
                    }
                } finally {
                    cursor.close();
                    finishAction();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
                Log.d("nameeeee>>>>  ",displayName);
                finishAction();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }
    private void uploadPDF(final String pdfname, Uri pdffile){

        InputStream iStream = null;
        try {

            iStream = getActivity().getContentResolver().openInputStream(pdffile);
            final byte[] inputData = getBytes(iStream);

            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, UrlHelper.ajukan_sub_task+"?user_id="+sessionManager.getUserDetail().get("id_user")+"&id="+ id_sub_task,
                    response -> {
                        Log.d("ressssssoo",new String(response.data));
                        rQueue.getCache().clear();
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                        finishAction();
                        refreshPage();
                    },
                    error -> {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("GotError",""+error.getMessage());
                        finishAction();
                    }) {

                /*
                 * If you want to add more parameters with the image
                 * you can do it here
                 * here we have only one parameter with the image
                 * which is tags
                 * */

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    String description = (etDescription.getText().toString().equals("")) ? "-" : etDescription.getText().toString();
                    params.put("SubTask[description]", description);

                    return params;
                }

                /*
                 *pass files using below method
                 * */
                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();
                    params.put("SubTask[file]", new DataPart(pdfname ,inputData));
                    return params;
                }
            };


            volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            rQueue = Volley.newRequestQueue(getActivity());
            rQueue.add(volleyMultipartRequest);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
            finishAction();
        } catch (IOException e) {
            e.printStackTrace();
            finishAction();
        }
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    private void refreshPage() {

        if (getArguments() != null) {
            id_sub_task = getArguments().getInt("id_sub_task");
            RequestQueue queue = Volley.newRequestQueue(getContext());
            StringRequest request = new StringRequest(Request.Method.GET, UrlHelper.view_sub_task + "?id=" + id_sub_task, (Response.Listener<String>) response -> {
                Gson g = new Gson();
                SubTask_ subTask = g.fromJson(response, SubTask_.class);
                String status = subTask.getStatus();
                linkDownloadFile = (String) subTask.getFile();
                tvTaskName.setText(subTask.getTask().getTaskName());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tvDescription.setText(Html.fromHtml(subTask.getTask().getDescription(), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    tvDescription.setText(Html.fromHtml(subTask.getTask().getDescription()));
                }
                tvAssignBy.setText(subTask.getAssignBy().getName());
                tvDeadline.setText(subTask.getDeadline());
                if(subTask.getKomentar() != null){
                    tvKomentar.setText(subTask.getKomentar());
                }
                if(linkDownloadFile != null){
                    if (linkDownloadFile.equals("-")) {
                        btnDownload.setVisibility(View.GONE);
                    }
                }
                if(status != null){
                    tvStatus.setText(status);
                    if (status.toLowerCase().equals("perlu tindak lanjut") == false) {
                        form.setVisibility(View.GONE);
                    }
                }
                pbLayout.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
            }, error -> {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            });
            queue.add(request);
        }
    }

    public void close() {
//        Toast.makeText(getContext(), "coba", Toast.LENGTH_SHORT).show();
        Fragment rm = getActivity().getSupportFragmentManager().findFragmentByTag("DetailSubTask");
        Fragment fragment = new SubTaskCompleteStaffFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(rm);
        fragmentTransaction.add(R.id.nav_host_fragment, fragment);
        fragmentTransaction.commit();
    }

    public void finishAction(){
        // Reload current fragment

        pbLayout.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);
        Fragment frg = null;
        frg = getActivity().getSupportFragmentManager().findFragmentByTag("DetailSubTask");
        final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
    }
}