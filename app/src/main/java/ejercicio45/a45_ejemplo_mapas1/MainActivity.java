package ejercicio45.a45_ejemplo_mapas1;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {
    //declaramos variable "googlemap" y representa el mapa completo
    //lo vamos a manipular a traves de metodos del objeto googlemap
    GoogleMap gm;
    LatLng pos=new LatLng(40.3960965,-3.743638);
    double lat;
    double longi;
    LatLng posi2=new LatLng(lat,longi);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TENEMOS QUE AÑADIR EL API DE GOOGLE MAPS
        //FILE/ PROJECT STRUCTURE/DEPENDENCES/+/ y
        // añadimos la libreria (primera casilla) de Google play services
        //tampoco se nos puede olvidar añadir los permisos


        //obtenemos una referencia al fragmento que contiene el mapa aen la actividad
        //despues a traves del metodo del fragmento, traemos una referencia al mapa en sí

        FragmentManager fm=this.getSupportFragmentManager();
        SupportMapFragment smf=(SupportMapFragment)fm.findFragmentById(R.id.mapa);

        //empezamos a operar con el mapa, "onmapready es lo que se ejecuta
        //en cuanto el mapa está cargado
        smf.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                //en la variable gm guardamos el objeto googlemap, asi podremos usarla
                //en cualquier sitio
                gm=googleMap;
                //tipo de mapa:
                gm.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                //habilitar botones y controles
                //controles de zoom:
                gm.getUiSettings().setZoomControlsEnabled(true);
                //toolbar:
                gm.getUiSettings().setMapToolbarEnabled(true);
                //localizarnos:
                gm.getUiSettings().setMyLocationButtonEnabled(true);
                //posicionar de salida en una determinada localizacion:
                //establecemos un objeto localizacion

                //movemos la camara hasta nuestro objeto
                //le damos nuestros parametros a traves de este objeto:
                //el primer parametro es el objeto POS, el segundo el zoom que
                //queremos que nos establezca
                gm.moveCamera(CameraUpdateFactory.newLatLngZoom(pos,18));
                //añadimos un marcador "you are here"
                //creamos un objeto markeroptions y lo configuramos
                MarkerOptions mk=new MarkerOptions();
                mk.position(pos);
                mk.title("El infierno del Java");
                //lo añadimos al mapa a traves del objeto Googlemap (gm)
                gm.addMarker(mk);
                //podemos añadir un evento en respuesta a onclick en el marcador(u otro elemento)
                //a traves de esta clase anonima
                gm.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        Toast.makeText(MainActivity.this,"me mola la cocacola",Toast.LENGTH_LONG).show();
                        return false;
                    }
                });

                //informacion adicional:
                mk.snippet("adicional");



            }
        });
    }
    public void posicion (View v){
        LocationManager lm=(LocationManager)MainActivity.this.getSystemService(Context.LOCATION_SERVICE);
        //dato1=proveedor del servicio/dato2 espera entre actualizaciones
        // /dato 3 distancia minima entre actualizaciones
        // / hacia que objeto va dirigido
        try {

            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000,10,new Localizador());
        }catch(SecurityException e){

            e.printStackTrace();
        }
        gm.moveCamera(CameraUpdateFactory.newLatLngZoom(posi2,18));
        MarkerOptions mk2=new MarkerOptions();
        mk2.position(posi2);
        mk2.title("te has movido a "+posi2.toString());
        gm.addMarker(mk2);
    }
    public class Localizador implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            Toast.makeText(MainActivity.this,"DETECTADO MOVIMIENTO", Toast.LENGTH_SHORT).show();
            lat=(location.getLatitude());
            longi=location.getLongitude();
            posi2=new LatLng(lat,longi);
            //lo imprimimos en un toast
            Toast.makeText(MainActivity.this,"te has movido a " +lat+" - "+longi, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }


}
