package uz.example.android_firebase.manager

import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser


object AuthManager {
    val firebaseAuth:FirebaseAuth = FirebaseAuth.getInstance()
    val firebaseUser:FirebaseUser? = firebaseAuth.currentUser

    fun isSignedIn():Boolean{
        return firebaseUser != null
    }

    fun signIn(email:String,password:String,handler: AuthHandler){
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                handler.onSuccess()
            }else{
                val errorCode = (task.exception as FirebaseAuthException?)!!.errorCode
                handler.onError(task.exception,errorCode)
            }

        }

    }
    fun signUp(email:String,password:String,handler: AuthHandler){
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                handler.onSuccess()
            }else{
                val errorCode = (task.exception as FirebaseAuthException?)!!.errorCode
                handler.onError(task.exception,errorCode)
                /*when (errorCode) {
                    "ERROR_INVALID_CUSTOM_TOKEN" ->
                    "ERROR_CUSTOM_TOKEN_MISMATCH" ->
                    "ERROR_INVALID_CREDENTIAL" ->
                    "ERROR_INVALID_EMAIL" ->
                    "ERROR_WRONG_PASSWORD" ->
                    "ERROR_USER_MISMATCH" ->
                    "ERROR_REQUIRES_RECENT_LOGIN" ->
                    "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" ->
                    "ERROR_EMAIL_ALREADY_IN_USE" ->
                    "ERROR_CREDENTIAL_ALREADY_IN_USE" ->
                    "ERROR_USER_DISABLED" ->
                    "ERROR_USER_TOKEN_EXPIRED" ->
                    "ERROR_USER_NOT_FOUND" ->
                    "ERROR_INVALID_USER_TOKEN" ->
                    "ERROR_OPERATION_NOT_ALLOWED" ->
                    "ERROR_WEAK_PASSWORD" ->
                }
                ("ERROR_INVALID_CUSTOM_TOKEN", "The custom token format is incorrect. Please check the documentation."));
 ("ERROR_CUSTOM_TOKEN_MISMATCH", "The custom token corresponds to a different audience."));
 ("ERROR_INVALID_CREDENTIAL", "The supplied auth credential is malformed or has expired."));
 ("ERROR_INVALID_EMAIL", "The email address is badly formatted."));
 ("ERROR_WRONG_PASSWORD", "The password is invalid or the user does not have a password."));
 ("ERROR_USER_MISMATCH", "The supplied credentials do not correspond to the previously signed in user."));
 ("ERROR_REQUIRES_RECENT_LOGIN", "This operation is sensitive and requires recent authentication. Log in again before retrying this request."));
 ("ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL", "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address."));
 ("ERROR_EMAIL_ALREADY_IN_USE", "The email address is already in use by another account."));
 ("ERROR_CREDENTIAL_ALREADY_IN_USE", "This credential is already associated with a different user account."));
 ("ERROR_USER_DISABLED", "The user account has been disabled by an administrator."));
 ("ERROR_USER_TOKEN_EXPIRED", "The user\'s credential is no longer valid. The user must sign in again."));
 ("ERROR_USER_NOT_FOUND", "There is no user record corresponding to this identifier. The user may have been deleted."));
 ("ERROR_INVALID_USER_TOKEN", "The user\'s credential is no longer valid. The user must sign in again."));
 ("ERROR_OPERATION_NOT_ALLOWED", "This operation is not allowed. You must enable this service in the console."));
 ("ERROR_WEAK_PASSWORD", "The given password is invalid."));
 ("ERROR_MISSING_EMAIL", "An email address must be provided.";

                */
            }

        }

    }

    fun signOut(){
        firebaseAuth.signOut()
    }
}