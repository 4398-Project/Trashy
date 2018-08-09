package trashy.txstate.cs4398.sm4.trashy.View;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.MenuItem;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import trashy.txstate.cs4398.sm4.trashy.Controller.DBInterface;
import trashy.txstate.cs4398.sm4.trashy.Model.Submission;
import trashy.txstate.cs4398.sm4.trashy.Model.TrashItem;
import trashy.txstate.cs4398.sm4.trashy.Model.User;
import trashy.txstate.cs4398.sm4.trashy.R;

public class Trash extends AppCompatActivity {

    private Button captureBTN;
    private TextureView textureView;
    FirebaseStorage fireStorage = FirebaseStorage.getInstance();
    StorageReference storageRef = fireStorage.getReference();


    //Checks state orientation of image
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static{
        ORIENTATIONS.append(Surface.ROTATION_0,90);
        ORIENTATIONS.append(Surface.ROTATION_90,0);
        ORIENTATIONS.append(Surface.ROTATION_180,270);
        ORIENTATIONS.append(Surface.ROTATION_270,180);
    }

    private String cameraId;
    private CameraDevice cameraDevice;
    private CameraCaptureSession cameraCaptureSessions;
    private CaptureRequest.Builder captureRequestBuilder;
    private Size imageDimension;
    private ImageReader imageReader;

    //Save to Submission File.
    private File file;
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private boolean mFlashSupported;
    private Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    intent = new Intent(Trash.this, Trashy_Leaderboard.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_notifications:
                    intent = new Intent(Trash.this, Login.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }
    };

    CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            cameraDevice = camera;
            createCameraPreview();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            cameraDevice.close();
        }

        @Override
        public void onError(@NonNull CameraDevice cameraDevice, int i) {
            cameraDevice.close();
            cameraDevice = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Retrieve info from past activity
       final  Intent intent = this.getIntent();
       final String username = intent.getStringExtra("username");
            final User user = new User();
            user.setUsername(username);

        textureView = (TextureView)findViewById(R.id.textureView);
        assert textureView != null;
        textureView.setSurfaceTextureListener(textureListener);

        captureBTN = findViewById(R.id.captureBTN);
        captureBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get DB Instance to store submissions
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DBInterface dbInterface = new DBInterface(database, username);
                //Generate the ID
                final String ID = dbInterface.genID();
                //Gen reference for pic storage from ID
                StorageReference picRef = storageRef.child(ID + ".jpg");
                takePicture(picRef);
                //Inflate dialog for information entry
                AlertDialog.Builder dBuilder = new AlertDialog.Builder(Trash.this);
                View dView = getLayoutInflater().inflate(R.layout.dialog_trash_info_entry, null);

                //Assign and initialize handles to dialog components
                final EditText trashTypeEntryField = dView.findViewById(R.id.trash_type_field);
                final EditText recyclableField = dView.findViewById(R.id.recyclable_trash_info_field);
                final EditText numberOfTrashItemsField = dView.findViewById(R.id.number_of_items_trash_info_field);
                final EditText trashItemLocationField = dView.findViewById(R.id.location_of_trash_field);
                final EditText trashDescriptionField = dView.findViewById(R.id.trash_description_field);

                Button submitTrashInfoButton = (Button) dView.findViewById(R.id.submit_trashInfo_Button);
                Button cancelTrashInfoButton = (Button) dView.findViewById(R.id.cancel_trashInfo_Button);

                //Display dialog
                dBuilder.setView(dView);
                final AlertDialog dialog = dBuilder.create();
                dialog.show();

                //Listeners for trash info dialog
                submitTrashInfoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int worked = 0;
                        int numSet = 0;
                        //Pull fields
                        String trashDescription = trashDescriptionField.getText().toString();
                        String trashType = trashTypeEntryField.getText().toString().toLowerCase();
                        String trashLocation = trashItemLocationField.getText().toString();
                        Boolean recyclable = (recyclableField.getText().toString().toLowerCase() == "yes") ? true : false;
                        String recyclableString = recyclableField.getText().toString().toLowerCase();
                        Integer numberOfTrashItems;
                        try {
                            numberOfTrashItems = Integer.parseInt(numberOfTrashItemsField.getText().toString());
                            numSet = 1;
                        }catch (NumberFormatException ex){
                            numberOfTrashItems = 0;
                            Toast.makeText(Trash.this, "Enter a valid number of trash items", Toast.LENGTH_SHORT).show();
                        }


                        //Check for complete fields

                        if(!trashType.isEmpty() & (trashType.equals("plastic") || trashType.equals("paper")|| trashType.equals("metal") || trashType.equals("wood") || trashType.equals("glass")))
                            if (numberOfTrashItems > 0)
                                if (!trashLocation.isEmpty())
                                    if (!trashDescription.isEmpty())
                                        if (!recyclableField.getText().toString().isEmpty() & (recyclableString.equals("yes") || recyclableString.equals("no"))){
                                            TrashItem trashItem = new TrashItem(trashDescription, trashType, trashLocation, recyclable);
                                            Submission submission = new Submission(user,ID.substring(0,8));
                                            submission.addTrashItem(trashItem, numberOfTrashItems);
                                            dbInterface.uploadSubmission(submission);
                                            Toast.makeText(Trash.this, "Submission is TR@SHY!", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                            worked = 1;
                                        }
                        if (numSet == 1 & worked == 0)
                            Toast.makeText(Trash.this, "Trash type must be: plastic, paper, metal, wood, or glass. Recyclable must be yes or no.", Toast.LENGTH_LONG).show();
                    }
                });
                cancelTrashInfoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });

    }

    private void takePicture(final StorageReference picRef) {
        if (cameraDevice == null)
            return;
        CameraManager manager = (CameraManager)getSystemService(Context.CAMERA_SERVICE);
        try {
                CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraDevice.getId());
                Size[] jpegSizes = null;
                if(characteristics != null)
                    jpegSizes = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
                            .getOutputSizes(ImageFormat.JPEG);

                //Capture with custom size
                int width = 640;
                int height = 480;
                if(jpegSizes != null && jpegSizes.length > 0)
                {
                    width = jpegSizes[0].getWidth();
                    height = jpegSizes[0].getHeight();
                }


                final ImageReader reader = ImageReader.newInstance(width,height,ImageFormat.JPEG,1);
                List<Surface> outputSurface = new ArrayList<>(2);
                outputSurface.add(reader.getSurface());
                outputSurface.add(new Surface(textureView.getSurfaceTexture()));

                final CaptureRequest.Builder captureBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
                captureBuilder.addTarget(reader.getSurface());
                captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);

                //Check orientation base on device
                    int rotation = getWindowManager().getDefaultDisplay().getRotation();
                    captureBuilder.set(CaptureRequest.CONTROL_MODE, ORIENTATIONS.get(rotation));

            file = new File("SDCARD/DCIM/SDCamera");
            ImageReader.OnImageAvailableListener readListener = new ImageReader.OnImageAvailableListener() {
                @Override
                public void onImageAvailable(ImageReader imageReader) {
                    Image image = null;
                    try{
                        image = reader.acquireLatestImage();
                        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                        byte[] bytes = new byte[buffer.capacity()];
                        buffer.get(bytes);
                        save(bytes);
                    }
                    catch (FileNotFoundException e)
                    {
                        e.printStackTrace();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    finally {
                        {
                            if (image != null)
                                image.close();
                        }
                    }
                }
                private void save(byte[] bytes) throws IOException{
                    UploadTask uploadTask = picRef.putBytes(bytes);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Trash.this, "Upload Task Failed", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(Trash.this, "Upload Task Successful", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            };
                    reader.setOnImageAvailableListener(readListener, mBackgroundHandler);
                    final CameraCaptureSession.CaptureCallback captureListener = new CameraCaptureSession.CaptureCallback() {
                    @Override
                    public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result){
                        super.onCaptureCompleted(session, request, result);
                        //Toast.makeText(Trash.this, "Saved " +file, Toast.LENGTH_SHORT).show();
                        createCameraPreview();
                    }

                    };

                    cameraDevice.createCaptureSession(outputSurface, new CameraCaptureSession.StateCallback() {
                        @Override
                        public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession ) {
                            try {
                                cameraCaptureSession.capture(captureBuilder.build(), captureListener, mBackgroundHandler);
                            }catch (CameraAccessException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {

                        }
                    },mBackgroundHandler);

                }catch (CameraAccessException e) {
                    e.printStackTrace();
        }

    }

    private void uploadPicture() {


    }

    private void createCameraPreview() {
        try{
            SurfaceTexture texture = textureView.getSurfaceTexture();
            assert  texture != null;
            texture.setDefaultBufferSize(imageDimension.getWidth(),imageDimension.getHeight());
            Surface surface = new Surface(texture);
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(surface);
            cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    if(cameraDevice == null)
                        return;
                    cameraCaptureSessions = cameraCaptureSession;
                    updatePreview();
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                    Toast.makeText(Trash.this, "Changed", Toast.LENGTH_SHORT).show();
                }
            },null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void updatePreview() {
        if(cameraDevice == null)
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE,CaptureRequest.CONTROL_MODE_AUTO);
        try{
            cameraCaptureSessions.setRepeatingRequest(captureRequestBuilder.build(),null,mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("MissingPermission")
    private void openCamera() {
        CameraManager manager = (CameraManager)getSystemService(Context.CAMERA_SERVICE);
        try{
            cameraId = manager.getCameraIdList()[0];
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            assert map != null;
            imageDimension = map.getOutputSizes(SurfaceTexture.class)[0];
            //Check realtime permission if run higher API 23
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this,new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },REQUEST_CAMERA_PERMISSION);
                return;
            }
            manager.openCamera(cameraId,stateCallback,null);

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CAMERA_PERMISSION)
        {
            if(grantResults[0] != PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "You can't use camera without permission", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startBackgroundThread();
        if(textureView.isAvailable())
            openCamera();
        else
            textureView.setSurfaceTextureListener(textureListener);
    }

    @Override
    protected void onPause() {
        stopBackgroundThread();
        super.onPause();
    }

    private void stopBackgroundThread() {
        mBackgroundThread.quitSafely();
        try{
            mBackgroundThread.join();
            mBackgroundThread= null;
            mBackgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("Camera Background");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }

} // last bracket
