package com.startng.newsapp.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.squareup.picasso.Picasso;
import com.startng.newsapp.Adapter.NoteRecyclerAdapter;
import com.startng.newsapp.Database.Model.Note;
import com.startng.newsapp.R;
import com.startng.newsapp.Util.FirebaseUtil;
import com.startng.newsapp.ViewModel.NoteViewModel;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    Paint paint = new Paint();
    int number;
    private NoteViewModel noteViewModel;
    private TextView textNameNavBar;
    private TextView textEmailNavBar;
    private CoordinatorLayout coordinatorLayout;
    private de.hdodenhof.circleimageview.CircleImageView profilePicture;
    //private List<Note> notesList = new ArrayList<>();
    private Activity activity = MainActivity.this;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String profileName;
    private String profileEmail;
    private String profilePictureString;

    private static Dictionary getUserDetails() {
        Dictionary profileData = new Hashtable();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String name = "";
        String email = "";
        String uid = "";
        Uri photoUrl = Uri.parse("");
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                String providerId = profile.getProviderId();

                // UID specific to the provider
                uid = profile.getUid();

                // Name, email address, and profile photo Url
                name = profile.getDisplayName();
                email = profile.getEmail();
                photoUrl = profile.getPhotoUrl();
            }
            profileData.put("name", name);
            profileData.put("email", email);
            profileData.put("uid", uid);
            profileData.put("imageUrl", photoUrl);
        } else {
            profileData.put("name", name);
            profileData.put("email", email);
            profileData.put("uid", uid);
            profileData.put("imageUrl", photoUrl);
        }
        return profileData;
    }

    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(NoteActivity.ORIGINAL_NOTE_TITLE);
            String text = data.getStringExtra(NoteActivity.ORIGINAL_NOTE_TEXT);
            String time = data.getStringExtra(NoteActivity.ORIGINAL_NOTE_TIME);

            Note note = new Note(title, text, time);
            noteViewModel.insert(note);
            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(NoteActivity.ORIGINAL_NOTE_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(NoteActivity.ORIGINAL_NOTE_TITLE);
            String text = data.getStringExtra(NoteActivity.ORIGINAL_NOTE_TEXT);
            String time = data.getStringExtra(NoteActivity.ORIGINAL_NOTE_TIME);

            Note note = new Note(title, text, time);
            note.setNid(id);
            noteViewModel.update(note);
            Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*recyclerView = findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        /*String[] myDataset = getResources().getStringArray(R.array.sports_info);
        mAdapter = new NoteRecyclerAdapter(this, myDataset);
        recyclerView.setAdapter(mAdapter);*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseUtil.openFbReference("notes", this);
        //FirebaseUtil.attachListener();

        coordinatorLayout = findViewById(R.id.coordinatorlayout);

        checkAuthenticationState();

        String welcomeBackString = "Welcome, " + profileName;
        final Snackbar snackbar = Snackbar.make(coordinatorLayout, welcomeBackString, Snackbar.LENGTH_LONG);
        snackbar.setTextColor(getResources().getColor(R.color.whiteColor));
        snackbar.setBackgroundTint(getResources().getColor(R.color.colorPrimary));

        snackbar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
        snackbar.show();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        final NoteRecyclerAdapter noteRecylerAdapter = new NoteRecyclerAdapter();
        recyclerView.setAdapter(noteRecylerAdapter);

        noteViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                //Update RecyclerView here
                noteRecylerAdapter.submitList(notes);
            }
        });

        FloatingActionButton buttonAddNote = findViewById(R.id.fab);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View myNavView = navigationView.getHeaderView(0);
        textNameNavBar = myNavView.findViewById(R.id.nameTextView);
        textEmailNavBar = myNavView.findViewById(R.id.emailTextView);
        profilePicture = myNavView.findViewById(R.id.navImageView);


        navigationView.setNavigationItemSelectedListener(this);
        selectNavigationMenuItem(R.id.nav_notes);


        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(noteRecylerAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX < 0) {
                        paint.setColor(Color.parseColor("#ffffff"));
                        RectF background = new RectF(
                                (float) itemView.getRight() + dX,
                                (float) itemView.getTop(),
                                (float) itemView.getRight(),
                                (float) itemView.getBottom());
                        c.drawRect(background, paint);
                        //icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete);
                        icon = getBitmapFromVectorDrawable(activity, R.drawable.ic_delete);

                        RectF icon_dest = new RectF(
                                (float) itemView.getRight() - 2 * width,
                                (float) itemView.getTop() + width,
                                (float) itemView.getRight() - width,
                                (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, paint);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        //initializeDisplayContent();

        noteRecylerAdapter.setOnItemClickListener(new NoteRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                intent.putExtra(NoteActivity.ORIGINAL_NOTE_ID, note.getNid());
                intent.putExtra(NoteActivity.ORIGINAL_NOTE_TIME, note.getTime_Stamp());
                intent.putExtra(NoteActivity.ORIGINAL_NOTE_TITLE, note.getNote_Title());
                intent.putExtra(NoteActivity.ORIGINAL_NOTE_TEXT, note.getNote_Text());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete_all_notes) {
            noteViewModel.deleteAllNotes();
            Toast.makeText(this, "All Notes Deleted", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_logout) {
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            FirebaseUtil.attachListener();
                        }
                    });
            FirebaseUtil.detachListener();
            return true;
        } else if (id == R.id.action_settings) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void showImage(Uri uri) {
        String url = uri.toString();
        if (url != null && url.isEmpty() == false) {
            Picasso.get().load(uri).into(profilePicture);
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(MainActivity.class.getSimpleName(), "Does it display the note");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final NoteRecyclerAdapter noteRecylerAdapter = new NoteRecyclerAdapter();
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                //Update RecyclerView here
                noteRecylerAdapter.submitList(notes);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final NoteRecyclerAdapter noteRecylerAdapter = new NoteRecyclerAdapter();
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                //Update RecyclerView here
                noteRecylerAdapter.submitList(notes);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        //FirebaseUtil.detachListener();
    }

    private void checkAuthenticationState() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            FirebaseUtil.attachListener();
        } else {
            Log.i("See them", "Check Authentication: User is Authenticated");
            NavigationView navigationView = findViewById(R.id.nav_view);
            View myNavView = navigationView.getHeaderView(0);
            textNameNavBar = myNavView.findViewById(R.id.nameTextView);
            textEmailNavBar = myNavView.findViewById(R.id.emailTextView);
            profilePicture = myNavView.findViewById(R.id.navImageView);

            Dictionary profileData = getUserDetails();
            profileName = profileData.get("name").toString();
            profileEmail = profileData.get("email").toString();
            profilePictureString = profileData.get("imageUrl").toString();
            if (profileName.isEmpty() && profileEmail.isEmpty()) {
                textNameNavBar.setText("");
                textEmailNavBar.setText("");
                showImage(Uri.parse(""));
            }
            textNameNavBar.setText(profileName);
            textEmailNavBar.setText(profileEmail);
            showImage(Uri.parse(profilePictureString));
            navigationView.setNavigationItemSelectedListener(this);
            selectNavigationMenuItem(R.id.nav_notes);
        }
    }

    private void selectNavigationMenuItem(int id) {
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        menu.findItem(id).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_notes) {
            onBackPressed();
        } else if (id == R.id.delete_all_notes) {
            noteViewModel.deleteAllNotes();
            onBackPressed();
            Toast.makeText(this, "All Notes Deleted", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.sign_out) {
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            FirebaseUtil.attachListener();
                        }
                    });
            FirebaseUtil.detachListener();
            return true;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


   /* public void addNumber(View view) {
        number++;
        TextView textView = findViewById(R.id.numbertextView);
        textView.setText(String.valueOf(number));
    }*/
}

