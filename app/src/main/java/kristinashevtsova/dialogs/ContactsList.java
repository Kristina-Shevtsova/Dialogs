package kristinashevtsova.dialogs;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Kristina Shevtsova.
 */

class ContactsList extends RecyclerView.Adapter<ContactsList.ViewHolder> {
    private List<User> contacts;
    private Context context;

    ContactsList(List<User> users, Context context) {
        this.contacts = users;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int Type) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        User contact = contacts.get(position);
        // Set avatar
        Picasso.with(context).load(contact.getAvatar()).into(viewHolder.avatar);
        // Set name
        viewHolder.name.setText(contact.getName());

        // Set age
        if ((contact.getAge() >= 11) && (contact.getAge() <= 14)) {
            viewHolder.age.setText(context.getString(R.string.year3, contact.getAge()));
        } else if (contact.getAge() % 10 == 1) {
            viewHolder.age.setText(context.getString(R.string.year1, contact.getAge()));
        } else if ((contact.getAge() % 10 == 2) || (contact.getAge() % 10 == 3) || (contact.getAge() % 10 == 4)) {
            viewHolder.age.setText(context.getString(R.string.year2, contact.getAge()));
        } else {
            viewHolder.age.setText(context.getString(R.string.year3, contact.getAge()));
        }

        // Set status
        GradientDrawable colorStatus = (GradientDrawable) viewHolder.status.getBackground();
        switch (contact.getStatus()) {
            case "dnd":
                colorStatus.setColor(ContextCompat.getColor(context, R.color.red));
                break;
            case "away":
                colorStatus.setColor(ContextCompat.getColor(context, R.color.yellow));
                break;
            case "online":
                colorStatus.setColor(ContextCompat.getColor(context, R.color.green));
                break;
        }

        // Set unread messages
        GradientDrawable colorUnreadMessages = (GradientDrawable) viewHolder.unreadMessages.getBackground();
        colorUnreadMessages.setColor(ContextCompat.getColor(context, R.color.purple));
        if (contact.getUnreadMessages() == 0) {
            viewHolder.unreadMessages.setVisibility(View.GONE);
        } else if (contact.getUnreadMessages() < 100) {
            viewHolder.countUnreadMessages.setText(String.valueOf(contact.getUnreadMessages()));
        } else {
            viewHolder.countUnreadMessages.setText(context.getString(R.string.unread_100_and_more_messages));
        }

        // Set similarity
        viewHolder.similarity.setText(context.getString(R.string.similarity, contact.getSimilarity()));
        if (contact.getSimilarity() < 40) {
            viewHolder.similarity.setTextColor(ContextCompat.getColor(context, R.color.red));
        } else if (contact.getSimilarity() < 70) {
            viewHolder.similarity.setTextColor(ContextCompat.getColor(context, R.color.yellow));
        } else {
            viewHolder.similarity.setTextColor(ContextCompat.getColor(context, R.color.green));
        }

        // Set last seen
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.getDefault());
        Date date = null;
        try {
            date = dateFormat.parse(contact.getLastSeen());
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        Calendar todayDate = Calendar.getInstance();
        Calendar lastSeenDate = Calendar.getInstance();
        lastSeenDate.setTime(date);
        if ((todayDate.get(Calendar.MONTH) == lastSeenDate.get(Calendar.MONTH)) && (todayDate.get(Calendar.YEAR) == (lastSeenDate.get(Calendar.YEAR)))) {
            if (todayDate.get(Calendar.DAY_OF_MONTH) == lastSeenDate.get(Calendar.DAY_OF_MONTH)) {
                viewHolder.lastSeen.setText(context.getString(R.string.today) + lastSeenDate.get(Calendar.HOUR_OF_DAY) + ":" + lastSeenDate.get(Calendar.MINUTE));
            } else if (todayDate.get(Calendar.DAY_OF_MONTH) - lastSeenDate.get(Calendar.DAY_OF_MONTH) == 1) {
                viewHolder.lastSeen.setText(context.getString(R.string.yesterday) + lastSeenDate.get(Calendar.HOUR_OF_DAY) + ":" + lastSeenDate.get(Calendar.MINUTE));
            }
        } else {
            viewHolder.lastSeen.setText(contact.getLastSeen());
        }
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView name;
        TextView age;
        ImageView status;
        ImageView unreadMessages;
        TextView countUnreadMessages;
        TextView similarity;
        TextView lastSeen;

         ViewHolder(View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            name = (TextView) itemView.findViewById(R.id.name);
            age = (TextView) itemView.findViewById(R.id.age);
            status = (ImageView) itemView.findViewById(R.id.status);
            unreadMessages = (ImageView) itemView.findViewById(R.id.unread_messages);
            countUnreadMessages = (TextView) itemView.findViewById(R.id.count_unread_messages);
            similarity = (TextView) itemView.findViewById(R.id.similarity);
            lastSeen = (TextView) itemView.findViewById(R.id.last_seen);
        }
    }
}
