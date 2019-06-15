package com.example.yerllisalas.acmetexplorer.entity;
import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.media.ExifInterface;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import es.us.acme.market.R;
import pl.aprilapps.easyphotopicker.EasyImage;

public class Perfil extends AppCompatActivity {

    private static final int RC_SIGN_IN = 0x1241;
    private FirebaseAuth mAuth;
    private static final int PERMISSION_REQUEST_STATE = 0x5123;
    private static int permissionIndex = 0;
    private static String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        findViewById(R.id.uploadImage).setOnClickListener(listener -> {
            showImagePicker();
        });
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser != null && currentUser.isEmailVerified()){
//            updateUI(currentUser);
//        }else{
//            updateUI(null);
//        }
//    }

//    private void updateUI(FirebaseUser user){
//        if (user== null) {
//            findViewById(R.id.buttonLoginGoogle).setVisibility(View.VISIBLE);
//            findViewById(R.id.buttonLogin).setVisibility(View.VISIBLE);
//            findViewById(R.id.buttonRegister).setVisibility(View.VISIBLE);
//            findViewById(R.id.progressBarLoading).setVisibility(View.GONE);
//        } if (user != null && user.isEmailVerified()){
//            Crashlytics.setUserIdentifier(user.getUid());
//            startActivity(new Intent(this, ProfileActivity.class));
//            this.finish();
//        } else {
//            Toast.makeText(this, "La cuenta de correo no ha sido verificada", Toast.LENGTH_SHORT).show();
//        }
//    }

//    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
//        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
//                        } else {
//                            Snackbar.make(findViewById(R.id.buttonLoginGoogle), "Autenticación fallida.", Snackbar.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }
//                    }
//                });
//    }


    //   @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//           if (requestCode == RC_SIGN_IN) {
//                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//                try {
//                    GoogleSignInAccount account = task.getResult(ApiException.class);
//                    firebaseAuthWithGoogle(account);
//                } catch (ApiException e) {
//                    Log.w("", "Google sign in failed", e);
//                }
//            }else {
//                EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
//                    @Override
    public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(Perfil.this);
        builder1.setCancelable(false);
        builder1.setMessage(R.string.setting_profile_picture_error_title);

        builder1.setPositiveButton(
                R.string.setting_profile_picture_error_yes,
                (dialog, id) -> dialog.cancel());
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    //     @Override
    public void onImagesPicked(List<File> imageFiles, EasyImage.ImageSource source, int type) {
        File imageFile = imageFiles.get(0);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("images/ + mAuth.getCurrentUser().getUid()" + "/profile.png");
        Bitmap bmp = null;
        try {
            bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(imageFile));

            ExifInterface ei = new ExifInterface(imageFile.getPath());
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

            Matrix matrix = new Matrix();
            switch (orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    matrix.preRotate(90);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    matrix.preRotate(180);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    matrix.preRotate(270);
                    break;
            }
            Bitmap rotatedBitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);

            int width = rotatedBitmap.getWidth();
            int height = rotatedBitmap.getHeight();

            float bitmapRatio = (float) width / (float) height;
            if (bitmapRatio > 1) {
                width = 400;
                height = (int) (width / bitmapRatio);
            } else {
                height = 400;
                width = (int) (height * bitmapRatio);
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Bitmap.createScaledBitmap(rotatedBitmap, width, height, true).compress(Bitmap.CompressFormat.JPEG, 15, baos);

            byte[] data = baos.toByteArray();
            storageReference.putBytes(data).addOnFailureListener(exception -> {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(Perfil.this);
                builder1.setCancelable(false);
                builder1.setMessage(R.string.setting_profile_picture_error_uploading_title);

                builder1.setPositiveButton(
                        R.string.setting_profile_picture_error_yes,
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }).addOnSuccessListener(taskSnapshot -> {
                Toast.makeText(Perfil.this, getString(R.string.setting_profile_picture_picked_title), Toast.LENGTH_SHORT).show();
                storageReference.getDownloadUrl().addOnCompleteListener(listener -> {
                    if (listener.isSuccessful() && listener.getResult() != null && listener.getResult().getPath() != null) {
                        Toast.makeText(Perfil.this, getString(R.string.setting_profile_picture_upload_title), Toast.LENGTH_SHORT).show();
                        Picasso.get()
                                .load(listener.getResult())
                                .placeholder(android.R.drawable.ic_menu_myplaces)
                                .error(android.R.drawable.ic_menu_myplaces)
                                .into((ImageView)findViewById(R.id.imageUploaded));
                    }
                });
            });
        } catch (IOException exception) {
            Crashlytics.logException(exception);
        }
    }
    //       });
    //       }
    //   }

    public void showImagePicker() {
        EasyImage.openChooserWithGallery(this, getString(R.string.setting_profile_picture_title), 0);
    }

    private void requestPermissions() {
        if (permissionIndex < permissions.length) {
            String permission = permissions[permissionIndex];
            if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        permission)) {
                    String text = "";
                    switch (permission) {
                        case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                            text = getString(R.string.permission_rationale_external_storage);
                            break;
                        case Manifest.permission.CAMERA:
                            text = getString(R.string.permission_rationale_camera);
                            break;
                    }
                    Snackbar.make(findViewById(R.id.buttonLogin), text,
                            Snackbar.LENGTH_INDEFINITE).setAction(R.string.permission_rationale_ok, view -> ActivityCompat.requestPermissions(this,
                            new String[]{permission},
                            PERMISSION_REQUEST_STATE)).show();
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{permission},
                            PERMISSION_REQUEST_STATE);
                }
            } else {
                permissionIndex++;
                requestPermissions();
            }
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            showImagePicker();
        }
    }

    private boolean retry = false;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissionsReq[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_STATE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    retry = false;
                    permissionIndex++;
                    requestPermissions();
                } else if (!retry) {
                    retry = true;
                    requestPermissions();
                } else {
                    retry = false;
                    permissionIndex++;
                    requestPermissions();
                }
            }
        }
    }

}
