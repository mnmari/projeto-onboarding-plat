package com.example.nearby.data.datasource

import android.annotation.SuppressLint
import android.content.Context
import com.example.nearby.data.model.UserLocationResponse
import com.google.android.gms.location.LocationServices
import io.reactivex.Single

internal class LocationDeviceDataSourceImpl(private val context: Context) :
    LocationDeviceDataSource {

    //TODO: Levar o tratamento de permissões pra ViewModel
    @SuppressLint("MissingPermission")
    override fun getLocation(): Single<UserLocationResponse> {

        return Single.create { emmiter ->
            LocationServices.getFusedLocationProviderClient(context).lastLocation.addOnCompleteListener { task ->
               try {
                   if (task.isSuccessful) {
                       val userLocation = UserLocationResponse(
                           latitude = task.result.latitude,
                           longitude = task.result.longitude
                       // ou result ou latitude tava vindo nulo, so pra parar de quebrar
                       )
                       emmiter.onSuccess(userLocation)
                   } else {
                       emmiter.tryOnError(Exception("Não foi possível pegar a localização do dispositivo."))
                   }
               } catch (e: Exception){
                   emmiter.tryOnError(Exception("Não foi possível pegar a localização do dispositivo."))
               }
            }
        }
    }
}