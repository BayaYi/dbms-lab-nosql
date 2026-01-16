
package app;

import static spark.Spark.*;
import com.google.gson.Gson;
import app.store.*;

public class Main {
    public static void main(String[] args) {
        ipAddress("0.0.0.0");
        port(8080);
        Gson gson = new Gson();

        // Veritabanlarını başlat ve 10.000 kaydı otomatik yükle
        RedisStore.init();      // Bu metot kendi içinde 10.000 kayıt atıyor
        HazelcastStore.init();  // Bu metot kendi içinde 10.000 kayıt atıyor
        MongoStore.init();      // Bu metot kendi içinde 10.000 kayıt atıyor

        System.out.println("Tüm veritabanları hazır ve 10.000 kayıt yüklendi!");

        // Endpoint Tanımları
        get("/nosql-lab-rd/ogrenci_no=:id", (req, res) ->
                gson.toJson(RedisStore.get(req.params(":id"))));

        get("/nosql-lab-hz/ogrenci_no=:id", (req, res) ->
                gson.toJson(HazelcastStore.get(req.params(":id"))));

        get("/nosql-lab-mon/ogrenci_no=:id", (req, res) ->
                gson.toJson(MongoStore.get(req.params(":id"))));
    }
}
