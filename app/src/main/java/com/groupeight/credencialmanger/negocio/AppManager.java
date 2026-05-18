package com.groupeight.credencialmanger.negocio;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppManager {

    public List<AppInfo> obtenerAppsInstaladas(Context context){
        List<AppInfo> apps = new ArrayList<>();
        PackageManager pm = context.getPackageManager();

        //Obtenemos las apps instaladas por ele usuario
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> appsIntaladas = pm.queryIntentActivities(intent,0);

        //Ordenamos la lista alfabeticamente
        Collections.sort(appsIntaladas, (a,b) ->
                a.loadLabel(pm).toString().compareToIgnoreCase(b.loadLabel(pm).toString()));

        for (ResolveInfo app : appsIntaladas){
            AppInfo appInfo = new AppInfo(
                    app.loadLabel(pm).toString(),
                    app.activityInfo.packageName,
                    app.loadIcon(pm)
            );
            apps.add(appInfo);
        }

        return apps;
    }


}
