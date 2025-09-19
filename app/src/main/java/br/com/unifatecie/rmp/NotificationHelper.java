package br.com.unifatecie.rmp;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;

public class NotificationHelper {

    private static final String CHANNEL_ID = "rmp_channel";

    // Cria canal de notificação (necessário para Android 8+)
    public static void criarCanal(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "RMP Notificações";
            String description = "Canal para lembretes de compras";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // Agendar notificação
    @SuppressLint("ScheduleExactAlarm")
    public static void agendarNotificacao(Context context, String titulo, String mensagem, Calendar calendario) {
        criarCanal(context);

        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra("titulo", titulo);
        intent.putExtra("mensagem", mensagem);

        int requestCode = (int) System.currentTimeMillis(); // ID único para cada notificação
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendario.getTimeInMillis(),
                    pendingIntent
            );
        }
    }

    // Receiver que mostra a notificação
    public static class NotificationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String titulo = intent.getStringExtra("titulo");
            String mensagem = intent.getStringExtra("mensagem");

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.icone) // seu ícone
                    .setContentTitle(titulo)
                    .setContentText(mensagem)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                int notificationId = (int) System.currentTimeMillis();
                notificationManager.notify(notificationId, builder.build());
            }
        }
    }
}
