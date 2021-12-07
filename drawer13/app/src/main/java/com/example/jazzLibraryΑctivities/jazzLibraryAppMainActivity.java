package com.example.jazzLibraryΑctivities;

import static android.text.TextUtils.split;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.DAO.JazzLibraryDAO;
import com.example.jazzLibraryAppClassies.ArtistAndVideoCount;
import com.example.jazzLibraryAppClassies.DurationAndVideoCount;
import com.example.jazzLibraryAppClassies.InstrumentAndArtistCount;
import com.example.jazzLibraryAppClassies.TypeAndVideoCount;
import com.example.jazzLibraryAppClassies.Video;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import android.view.View.OnKeyListener;



public class jazzLibraryAppMainActivity<clickListener> extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private LinearLayout videoLinearLayout;
    private LinearLayout pathBarLinearLayout;

    private Spinner  searchOptionSpinner;
    private AutoCompleteTextView autoCompleteEditText;

    private ChipGroup instrumentChipGroup;
    private ChipGroup artistChipGroup;
    private ChipGroup typeChipGroup;
    private ChipGroup durationChipGroup;

    private TextView artistCountTextView;


    private ImageView iRealProGooglePlayLink;
    private ImageView spotifyGooglePlayLink;
    private ImageView VLCGooglePlayLink;
    private ImageView sonemicGooglePlayLink;
    private ImageView WBGOGooglePlayLink;
    private ImageView IMSLPGooglePlayLink;
    private ImageView soundbrennerGooglePlayLink;


    Drawable drawable_bass_icon ;
    Drawable drawable_guitar_icon;
    Drawable drawable_piano_icon;
    Drawable drawable_drums_icon;
    Drawable drawable_voice_icon ;
    Drawable drawable_sax_icon ;
    Drawable drawable_trumpet_icon ;
    Drawable drawable_violin_icon ;
    Drawable drawable_vibes_icon ;
    Drawable drawable_other_icon ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //egiropiei, thn adia pou exeis dosei sto MANIFEST na exeis prosvash sto Internet
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);



        //bres to layout sto opoio einai to animation  ,pernei to animation ,, pou exei oristei ekei os background
        //setareis poso grigora tha alazei to gradient
        LinearLayoutCompat linearLayoutCompat = findViewById(R.id.main_layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayoutCompat.getBackground();
        animationDrawable.setEnterFadeDuration(10);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        //kanei ta hyper link mou , na mporeis na ta clickareis!
        setupHyperlinks();



        //Arxikopiiseis : breiskei kai dilonei byId apo to xml ta stoixeia pou tha xrisimopoiiso
        //                thetei to Edit text na exei sta sugestions titles apo videos ,
        //                kai trexei filterHandler();  me timi "0000"  sto Path
        initViews();

        try {
            Connection con = JazzLibraryDAO.getConnection();

            autoCompleteEditTextSuggestionLoader(con);
            filterHandler(con);

            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        searchOptionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        //molis patas filters , katharizei olo to path,, kai kanei unchecked ola ta chip group
        //kai analoga ti epilogei exei o spiner, ferno onomata apo tin bash , kai ta prosarto se Addapter
        //ton opoio prosarto sto AutocompleteEditText !
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                try {

                    Connection con = JazzLibraryDAO.getConnection();

                    resetFilters(con);
                    autoCompleteEditTextSuggestionLoader(con);

                    con.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });






        autoCompleteEditText.setOnKeyListener(new OnKeyListener() {
        //katharizo panta to path ,, travao thn timh pou exei to EditText,
        //meta kalo thn  searchBarVideoRetriever()  na mou kanei DAO kai na mou ferei ta video pou tha
        //emfaniso sto  videoLinearLayout!!
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {



                    try {


                        Connection con = JazzLibraryDAO.getConnection();

                        resetFilters(con);

                        String editTxtSearchString = autoCompleteEditText.getText().toString();

                        List<Video> videoList =searchBarVideoRetriever(editTxtSearchString,con);

                        videoLinearLayoutHandler(videoList);

                        con.close();

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    return true;
                }
                return false;
            }
        });




        instrumentChipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
        //LISTENER : kathe enas listener(instrument,artist,type,duration) exei ta e3is
        //trabaei to epilegmeno chip ,    pernei to text tou kai to dinei ston  pathLinearLayoutChipHandler()
        //opou autos dimiourgei to path pano sthn othonh !  meta kaleite o simplifyFilterActivity() o opoios
        //diorthonei kapoia BUGS aplopiontas thn dinamikotita ton filtron , kai telos , kaleite o filterHandler()
        //o opoios diavazei to PATHlinearLayout pou tre3ame prin kai analoga trabaei apo thn bash kai emfanizei apotelesmata sthn efarmogh!!
        //Parakato sto ELSE  einai o ucheckListener ! molis ena chip apo ta (instrument,artist,type,duration) parei thn timh ID=-1
        //o ucheck listener energopiite kai   trexei o PathTextViewCleaner()  autos katharizei to pathLinearLayout!  kai
        //meta kalite pali o  filterHandler() o opoios   emfanizei ta apotelesmata tou neo Path sto UI

            @SuppressLint("ResourceType")
            public void onCheckedChanged(ChipGroup chipGroup, int i) {

                Chip chip_byInstrument = chipGroup.findViewById(i);
                try {


                    Connection con = JazzLibraryDAO.getConnection();

                    if (chip_byInstrument != null) {

                        String selectedInstrumentChipText= (String) chip_byInstrument.getText();

                        pathLinearLayoutChipHandler("instrument",selectedInstrumentChipText);

                        simplifyFilterActivity("instrument");



                        filterHandler(con);


                    } else
                    {
                        pathTextViewCleaner("instrument");

                        filterHandler(con);

                        artistCountTextView.setText("");
                    }
                    Log.e("OnCheckedChangeListener", "Called");

                    con.close();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });


        artistChipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(ChipGroup chipGroup, int i) {

                Chip chip_byArtist = chipGroup.findViewById(i);

                try {

                    Connection con = JazzLibraryDAO.getConnection();

                    if (chip_byArtist != null) {

                        String selectedArtistChipText= (String) chip_byArtist.getText();

                        pathLinearLayoutChipHandler("artist",selectedArtistChipText);

                        //simplifyFilterActivity("artist");


                            filterHandler(con);


                    } else
                    {


                        pathTextViewCleaner("artist");

                        videoLinearLayout.removeAllViews();

                        filterHandler(con);

                    }
                    Log.e("OnCheckedChangeListener", "Called");

                    con.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });






        typeChipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(ChipGroup chipGroup, int i) {

                Chip chip_byType= chipGroup.findViewById(i);
                try {

                    Connection con = JazzLibraryDAO.getConnection();

                    if (chip_byType != null) {

                        String selectedTypeChipText= (String) chip_byType.getText();

                        pathLinearLayoutChipHandler("type",selectedTypeChipText);

                        simplifyFilterActivity("type");


                            filterHandler(con);

                    }
                    else{

                        pathTextViewCleaner("type");
                        filterHandler(con);
                    }
                    Log.e("OnCheckedChangeListener", "Called");

                    con.close();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });




        durationChipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup chipGroup, int i) {

                Chip chip_byDuration= chipGroup.findViewById(i);
                try {

                    Connection con = JazzLibraryDAO.getConnection();

                    if (chip_byDuration != null){

                        String selectedDurationChipText= (String) chip_byDuration.getText();

                        pathLinearLayoutChipHandler("duration",selectedDurationChipText);

                        //simplifyFilterActivity("duration");


                            filterHandler(con);


                    }
                    else{

                        pathTextViewCleaner("duration");
                        filterHandler(con);
                    }
                    Log.e("OnCheckedChangeListener", "Called");

                    con.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });













        iRealProGooglePlayLink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Intent viewIntent =
                            new Intent("android.intent.action.VIEW",
                                    Uri.parse("https://play.google.com/store/apps/details?id=com.massimobiolcati.irealb&hl=el&gl=US"));
                    startActivity(viewIntent);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Unable to Connect Try Again...",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

        });
        spotifyGooglePlayLink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Intent viewIntent =
                            new Intent("android.intent.action.VIEW",
                                    Uri.parse("https://play.google.com/store/apps/details?id=com.spotify.music&hl=el&gl=US"));
                    startActivity(viewIntent);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Unable to Connect Try Again...",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

        });
        VLCGooglePlayLink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Intent viewIntent =
                            new Intent("android.intent.action.VIEW",
                                    Uri.parse("https://play.google.com/store/apps/details?id=org.videolan.vlc&hl=el&gl=US"));
                    startActivity(viewIntent);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Unable to Connect Try Again...",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

        });
        sonemicGooglePlayLink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Intent viewIntent =
                            new Intent("android.intent.action.VIEW",
                                    Uri.parse("https://play.google.com/store/apps/details?id=com.sonemic.sonemiccharts&hl=el&gl=US"));
                    startActivity(viewIntent);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Unable to Connect Try Again...",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

        });
        WBGOGooglePlayLink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Intent viewIntent =
                            new Intent("android.intent.action.VIEW",
                                    Uri.parse("https://play.google.com/store/apps/details?id=com.skyblue.pra.wbgo&hl=el&gl=US"));
                    startActivity(viewIntent);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Unable to Connect Try Again...",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

        });
        IMSLPGooglePlayLink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Intent viewIntent =
                            new Intent("android.intent.action.VIEW",
                                    Uri.parse("https://play.google.com/store/apps/details?id=com.imslp.app&hl=el&gl=US"));
                    startActivity(viewIntent);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Unable to Connect Try Again...",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

        });
        soundbrennerGooglePlayLink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Intent viewIntent =
                            new Intent("android.intent.action.VIEW",
                                    Uri.parse("https://play.google.com/store/apps/details?id=com.soundbrenner.pulse&hl=el&gl=US"));
                    startActivity(viewIntent);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Unable to Connect Try Again...",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

        });


    }









    @SuppressLint("ResourceType")
    void filterHandler(Connection con) throws SQLException {
    //Diavazei to PATHlinearLAYOUT  (to path einai auto pou maw lei ti filtra exoun epilexthei kai me pia seira)
    //einai 4psifio string ,, kai analogo me to pio apo to parakato einai mpeno sto katalilo IF
    //an kapoio chip einai epilegmeno apo prin se kapoi chip group pou to xriazome ,tote diagrago ola ta CHIPS ektos apo
    //auto (auto epidi einai o monos tropos ta chis na alazoun dinamika opos thelo kai tautoxrona na menei to chip pou)
    //dialektike epilegmeno    :: resetUnselectedChips()    .
    // an den xriazomai epilegmeno chip Apla diagrafo ola ta chips tou group
    //  stin sinexia kalo tis kataliles DAO gia na kano tin select pou apetei to filtro sthn bash!
    //setaro kai to plithos ton artist dipla apo to text view   ]
    //kathe aritmos simbolizei ena chipGroup h alios mia katigoria (instrument:1  artist:2   type:3  duration:4    nothing:0)

        String filterPathString = filterPathReader();

        if (filterPathString.equals("0000")) { //none


            instrumentChipGroup.removeAllViews();
            typeChipGroup.removeAllViews();
            durationChipGroup.removeAllViews();
            artistChipGroup.removeAllViews();

            videoLinearLayout.removeAllViews();




            List<InstrumentAndArtistCount> instrumentAndArtistCount= JazzLibraryDAO.retriveInstrumentAndArtistCount_By(con);
            instrumentChipPopulator(instrumentAndArtistCount);

            artistCountTextView.setText("");



            List<TypeAndVideoCount> typeAndVideoCount= JazzLibraryDAO.retriveTypeAndVideoCount_By(con);
            typeChipPopulator(typeAndVideoCount);
        }
        else if (filterPathString.equals("1000")){ //instrument

            int selectedInstrumentChipId = instrumentChipGroup.getCheckedChipId();


            resetUnselectedChips("instrument",selectedInstrumentChipId);
            artistChipGroup.removeAllViews();
            typeChipGroup.removeAllViews();
            durationChipGroup.removeAllViews();


            List<InstrumentAndArtistCount> instrumentAndArtistCount= JazzLibraryDAO.retriveInstrumentAndArtistCount_By(con);
            instrumentChipPopulator(instrumentAndArtistCount,selectedInstrumentChipId);


            List<ArtistAndVideoCount> artistAndVideoCountList =JazzLibraryDAO.retriveArtistAndVideoCount_By(selectedInstrumentChipId,con);
            artistChipPopulator(artistAndVideoCountList);
            artistCountTextView.setText("::" + artistAndVideoCountList.size());


            List<TypeAndVideoCount> typeAndVideoCount= JazzLibraryDAO.retriveTypeAndVideoCount_By(selectedInstrumentChipId,con);
            typeChipPopulator(typeAndVideoCount);

        }
        else if (filterPathString.equals("1200")){ //instrument/artist

            int selectedInstrumentChipId = instrumentChipGroup.getCheckedChipId();
            int selectedArtistChipId = artistChipGroup.getCheckedChipId();

            resetUnselectedChips("instrument",selectedInstrumentChipId);
            resetUnselectedChips("artist",selectedArtistChipId);
            typeChipGroup.removeAllViews();
            durationChipGroup.removeAllViews();



            List<Video> videoList = JazzLibraryDAO.retriveVideo_By(selectedArtistChipId,con);
            videoLinearLayoutHandler(videoList);


            List<InstrumentAndArtistCount> instrumentAndArtistCount= JazzLibraryDAO.retriveInstrumentAndArtistCount_By(con);
            instrumentChipPopulator(instrumentAndArtistCount,selectedInstrumentChipId);


            List<ArtistAndVideoCount> artistAndVideoCountList =JazzLibraryDAO.retriveArtistAndVideoCount_By(selectedInstrumentChipId,con);
            artistChipPopulator(artistAndVideoCountList,selectedArtistChipId);
            artistCountTextView.setText("::" + artistAndVideoCountList.size());

            List<TypeAndVideoCount> typeAndVideoCount= JazzLibraryDAO.retriveTypeAndVideoCount_By(selectedArtistChipId,0,con);
            typeChipPopulator(typeAndVideoCount);

        }
        else if(filterPathString.equals("3000")){//type

            int selectedTypeChipId = typeChipGroup.getCheckedChipId();


            instrumentChipGroup.removeAllViews();
            artistChipGroup.removeAllViews();
            resetUnselectedChips("type",selectedTypeChipId);//thelo na kratisei
            durationChipGroup.removeAllViews();


            List<InstrumentAndArtistCount> instrumentAndArtistCount= JazzLibraryDAO.retriveInstrumentAndArtistCount_By(selectedTypeChipId,con);
            instrumentChipPopulator(instrumentAndArtistCount);



            List<TypeAndVideoCount> typeAndVideoCount= JazzLibraryDAO.retriveTypeAndVideoCount_By(con);
            typeChipPopulator(typeAndVideoCount,selectedTypeChipId);



            List<DurationAndVideoCount> durationAndVideoCount= JazzLibraryDAO.retriveDurationAndVideoCount_By(selectedTypeChipId,con);
            durationChipPopulator(durationAndVideoCount);

        }
        else if (filterPathString.equals("1300") || filterPathString.equals("3100")  ){ //instrument/type  //type/instru

            int selectedInstrumentChipId = instrumentChipGroup.getCheckedChipId();
            int selectedTypeChipId = typeChipGroup.getCheckedChipId();

            resetUnselectedChips("instrument",selectedInstrumentChipId);
            artistChipGroup.removeAllViews();
            resetUnselectedChips("type",selectedTypeChipId);
            durationChipGroup.removeAllViews();



            List<InstrumentAndArtistCount> instrumentAndArtistCount= JazzLibraryDAO.retriveInstrumentAndArtistCount_By(selectedTypeChipId,con);
            instrumentChipPopulator(instrumentAndArtistCount,selectedInstrumentChipId);


            List<ArtistAndVideoCount> artistAndVideoCountList =JazzLibraryDAO.retriveArtistAndVideoCount_By(selectedInstrumentChipId,selectedTypeChipId,con);
            artistChipPopulator(artistAndVideoCountList);
            artistCountTextView.setText("::" + artistAndVideoCountList.size());


            List<TypeAndVideoCount> typeAndVideoCount= JazzLibraryDAO.retriveTypeAndVideoCount_By(selectedInstrumentChipId,con);
            typeChipPopulator(typeAndVideoCount,selectedTypeChipId);


            List<DurationAndVideoCount> durationAndVideoCount=
                    JazzLibraryDAO.retriveDurationAndVideoCount_By(selectedTypeChipId,selectedInstrumentChipId,con);
            durationChipPopulator(durationAndVideoCount);

        }
        else if(filterPathString.equals("3400")){//type/duration

            int selectedTypeChipId = typeChipGroup.getCheckedChipId();
            int selectedDurationChipId = durationChipGroup.getCheckedChipId();

            instrumentChipGroup.removeAllViews();
            artistChipGroup.removeAllViews();
            resetUnselectedChips("type",selectedTypeChipId);
            resetUnselectedChips("duration",selectedDurationChipId);

            List<InstrumentAndArtistCount> instrumentAndArtistCount= JazzLibraryDAO.retriveInstrumentAndArtistCount_By(selectedTypeChipId,selectedDurationChipId,con);
            instrumentChipPopulator(instrumentAndArtistCount);


            List<TypeAndVideoCount> typeAndVideoCount= JazzLibraryDAO.retriveTypeAndVideoCount_By(0,0,selectedTypeChipId,selectedDurationChipId,con);
            typeChipPopulator(typeAndVideoCount,selectedTypeChipId);


            List<DurationAndVideoCount> durationAndVideoCount=JazzLibraryDAO.retriveDurationAndVideoCount_By(selectedTypeChipId,con);
            durationChipPopulator(durationAndVideoCount,selectedDurationChipId);
        }
        else if(filterPathString.equals("1340") || filterPathString.equals("3410") || filterPathString.equals("3140")  ){//instrument/type/duration    //type/duration/instrument

            int selectedInstrumentChipId = instrumentChipGroup.getCheckedChipId();
            int selectedTypeChipId = typeChipGroup.getCheckedChipId();
            int selectedDurationChipId = durationChipGroup.getCheckedChipId();

            resetUnselectedChips("instrument",selectedInstrumentChipId);
            artistChipGroup.removeAllViews();
            resetUnselectedChips("type",selectedTypeChipId);
            resetUnselectedChips("duration",selectedDurationChipId);




            List<InstrumentAndArtistCount> instrumentAndArtistCount
                    =JazzLibraryDAO.retriveInstrumentAndArtistCount_By(selectedTypeChipId,selectedDurationChipId,con);
            instrumentChipPopulator(instrumentAndArtistCount,selectedInstrumentChipId);


            List<ArtistAndVideoCount> artistAndVideoCountList
                    =JazzLibraryDAO.retriveArtistAndVideoCount_By(selectedInstrumentChipId,selectedTypeChipId,selectedDurationChipId,con);
            artistChipPopulator(artistAndVideoCountList);
            artistCountTextView.setText("::" + artistAndVideoCountList.size());


            List<TypeAndVideoCount> typeAndVideoCount
                    = JazzLibraryDAO.retriveTypeAndVideoCount_By(selectedInstrumentChipId,0,0,selectedDurationChipId,0,con);
            typeChipPopulator(typeAndVideoCount,selectedTypeChipId);


            List<DurationAndVideoCount> durationAndVideoCount
                    = JazzLibraryDAO.retriveDurationAndVideoCount_By(selectedTypeChipId,selectedInstrumentChipId,con);
            durationChipPopulator(durationAndVideoCount,selectedDurationChipId);

        }
        else if (filterPathString.equals("1230")|| filterPathString.equals("3120") || filterPathString.equals("1320")){ //instrument/artist/type //type/instrument/artist
                                                                                                                        //artist/type/duration
            int selectedInstrumentChipId = instrumentChipGroup.getCheckedChipId();
            int selectedArtistChipId = artistChipGroup.getCheckedChipId();
            int selectedTypeChipId = typeChipGroup.getCheckedChipId();

            resetUnselectedChips("instrument",selectedInstrumentChipId);
            resetUnselectedChips("artist",selectedArtistChipId);
            resetUnselectedChips("type",selectedTypeChipId);
            durationChipGroup.removeAllViews();




            List<Video> videoList = JazzLibraryDAO.retriveVideo_By(selectedArtistChipId,selectedTypeChipId,con);
            videoLinearLayoutHandler(videoList);

            List<InstrumentAndArtistCount> instrumentAndArtistCount= JazzLibraryDAO.retriveInstrumentAndArtistCount_By(selectedTypeChipId,con);
            instrumentChipPopulator(instrumentAndArtistCount,selectedInstrumentChipId);


            List<ArtistAndVideoCount> artistAndVideoCountList =JazzLibraryDAO.retriveArtistAndVideoCount_By(selectedInstrumentChipId,selectedTypeChipId,con);
            artistChipPopulator(artistAndVideoCountList,selectedArtistChipId);
            artistCountTextView.setText("::" + artistAndVideoCountList.size());


            List<TypeAndVideoCount> typeAndVideoCount= JazzLibraryDAO.retriveTypeAndVideoCount_By(selectedArtistChipId,0,con);
            typeChipPopulator(typeAndVideoCount,selectedTypeChipId);


            List<DurationAndVideoCount> durationAndVideoCount=
                    JazzLibraryDAO.retriveDurationAndVideoCount_By(selectedTypeChipId,0,selectedArtistChipId,con);
            durationChipPopulator(durationAndVideoCount);
        }
        else if (filterPathString.equals("1342") || filterPathString.equals("1234") || filterPathString.equals("1324")   //instrument/type/duration/artist //instrument/artist/type/duration
                || filterPathString.equals("3412") || filterPathString.equals("3124") || filterPathString.equals("3142")){ //instrument/type/artist/duration  //type/duration/instrument/artist
                                                                                                                            //type/instrument/artist/duration  //type/instrumetn/artist/duration
            int selectedInstrumentChipId = instrumentChipGroup.getCheckedChipId();                                           //type/instrument/duration/artist
            int selectedArtistChipId = artistChipGroup.getCheckedChipId();
            int selectedTypeChipId = typeChipGroup.getCheckedChipId();
            int selectedDurationChipId = durationChipGroup.getCheckedChipId();

            resetUnselectedChips("instrument",selectedInstrumentChipId);
            resetUnselectedChips("artist",selectedArtistChipId);
            resetUnselectedChips("type",selectedTypeChipId);
            resetUnselectedChips("duration",selectedDurationChipId);





            List<Video> videoList = JazzLibraryDAO.retriveVideo_By(selectedArtistChipId,selectedTypeChipId,selectedDurationChipId,con);
            videoLinearLayoutHandler(videoList);


            List<InstrumentAndArtistCount> instrumentAndArtistCount
                    = JazzLibraryDAO.retriveInstrumentAndArtistCount_By(selectedTypeChipId,selectedDurationChipId,con);
            instrumentChipPopulator(instrumentAndArtistCount,selectedInstrumentChipId);


            List<ArtistAndVideoCount> artistAndVideoCountList
                    =JazzLibraryDAO.retriveArtistAndVideoCount_By(selectedInstrumentChipId,selectedTypeChipId,selectedDurationChipId,con);
            artistChipPopulator(artistAndVideoCountList,selectedArtistChipId);
            artistCountTextView.setText("::" + artistAndVideoCountList.size());


            List<TypeAndVideoCount> typeAndVideoCount
                    = JazzLibraryDAO.retriveTypeAndVideoCount_By(0,selectedArtistChipId,selectedDurationChipId,con);
            typeChipPopulator(typeAndVideoCount,selectedTypeChipId);


            List<DurationAndVideoCount> durationAndVideoCount=
                    JazzLibraryDAO.retriveDurationAndVideoCount_By(selectedTypeChipId,0,selectedArtistChipId,con);
            durationChipPopulator(durationAndVideoCount,selectedDurationChipId);

        }
        else{
            Toast.makeText(getApplicationContext(), "chip chooser  error", Toast.LENGTH_SHORT).show();
        }



    }




    void simplifyFilterActivity(String filterCategory) {
    //paragei    briskei to index sto pathLayout opou emfanizete chip me ID idio me to  filter category,
    //           afou to brei , looparei gia osa chip menoun meta apo auto , kai kaneis unchek to chip tous apo to chip group
    //           efoson ginete kapoio chip Unchecked , apeuthias energopiite o ucheck listener pou kalei thn PathTextViewCleaner()
    //           etsi katharizontai kai chips apo to Path   kai ginonte uncheck apo to group
    //auth h method , einai mia diklida asfalias pou aplopiei thn poluplokotita tou filtrou me apotelesma na
    //trexei to programa poio provlepsina , linei to problima : "opou molis epilegeis px artist kai meta alazeis
    //instrument o artist apo to proigoumeno organo menei epilegmenos.. "

        String pathFilterString = filterPathReader();

        int chipPosition= findIndexOfCategoryContainsInPath(filterCategory);


        int count = pathBarLinearLayout.getChildCount() - chipPosition;

        if(chipPosition != -1) {
            for (int i = 0; i < count; i++) {
                String pathFilterCharToString = pathFilterString.charAt(chipPosition + i) + "";

                if (pathFilterCharToString.equals("1") && i != 0) {
                    instrumentChipGroup.check(-1);
                } else if (pathFilterCharToString.equals("2") && i != 0) {
                    artistChipGroup.check(-1);
                } else if (pathFilterCharToString.equals("3") && i != 0) {
                    typeChipGroup.check(-1);
                } else if (pathFilterCharToString.equals("4") && i != 0) {
                    durationChipGroup.check(-1);
                }
            }
        }

    }







    @SuppressLint("ResourceType")
    void pathTextViewCleaner(String filterCategory) {
    //lambanei    to anagnoristiko tis katigorias ("instrument","artist","type","duration")
    //paragei     AN to chip iparxei sto PATH (chipPosition != -1),diagrafei to chip tou PathLinearLayout pou egine UNchecked
    //            - kai ola ta chips pou akolouthoun  meta apo auto! ...
    //(pathBarLinearLayout.getChildCount() - chipPosition;) :: exontas brei to index position tou epithimitou Chip sto path
    //aferei apo to sinoliko megethostou path to index ,, kai 3erei tora poses fores tha kanei  (pathBarLinearLayout.removeViewAt(step);)
    //an paei na diagrapsei se thesh pou den exei View kanei ERROR ,,
        int chipPosition= findIndexOfCategoryContainsInPath(filterCategory);
        if(chipPosition != -1) {

            int step = chipPosition;
            int count = pathBarLinearLayout.getChildCount() - chipPosition;

            for (int i = 0; i < count; i++)
                pathBarLinearLayout.removeViewAt(step);
        }
    }


    public int findIndexOfCategoryContainsInPath(String filterCategory) {
    //kaleite otan ena chip gine unchecked
    //lambanei    autos pou to kalei stelnei kai to anagnoristiko tou to  filterCategory
    //epistrefei  tin thesh pou vrethike to chip tis categry (instrument:1  artuist:2 ext)
    //             an den to brei epistrefei   -1  !

        String pathFilterString = filterPathReader();
        int chipPosition=-1;

        if (filterCategory.equals("instrument")) {

            chipPosition = pathFilterString.indexOf("1");

        } else if (filterCategory.equals("artist")) {

            chipPosition = pathFilterString.indexOf("2");

        } else if (filterCategory.equals("type")) {

            chipPosition = pathFilterString.indexOf("3");

        } else if (filterCategory.equals("duration")) {

            chipPosition = pathFilterString.indexOf("4");
        }

        return chipPosition;
    }






    void instrumentChipPopulator(List<InstrumentAndArtistCount> instrumentAndArtistCount) {

        for (int i = 0; i < instrumentAndArtistCount.size(); i++) {

            Chip chipInstrument = chipTypeGenerator(instrumentAndArtistCount.get(i));
            instrumentChipGroup.addView(chipInstrument);
        }
    }





    void instrumentChipPopulator(List<InstrumentAndArtistCount> instrumentAndArtistCount, int checkedChipId) {
        //lambanei     instrument list , kai ID epilegmenou chip
        //paragei      gemizei to group me chips , ektos apo to epilegmeno chip , to epilegmaino
        //             emfanizete sto seimio pou patithike
        //an to neo chip einai idio me to ID toy paliou chip, min to baleis mesa sto group , apla prosperase


        for (int i = 0; i < instrumentAndArtistCount.size(); i++) {
            if(instrumentAndArtistCount.get(i).getArtist_count()!=0) {

                Chip chipInstrument = chipTypeGenerator(instrumentAndArtistCount.get(i));

                if(chipInstrument.getId()==checkedChipId ){
                    //skip that i
                }
                else
                    instrumentChipGroup.addView(chipInstrument,i);
            }
        }
    }


    public Chip chipTypeGenerator(InstrumentAndArtistCount instrumentAndArtistCount){

        LayoutInflater inflater = LayoutInflater.from(jazzLibraryAppMainActivity.this);

        Chip chipInstrument = (Chip) inflater.inflate(R.layout.chip_item_big, null, false);
        chipInstrument.setText(instrumentAndArtistCount.getInstrument_name());
        chipInstrument.setId(instrumentAndArtistCount.getInstrument_id());//mono etsi pianei to single selection

        return chipInstrument;
    }






    @SuppressLint("ResourceType")
    void artistChipPopulator(List<ArtistAndVideoCount> artistAndVideoCount){
    //apla gia kathe engrafh tou LIST ftiaxneis ena CHIP kai to xoneis sto CHIPGROUP!
    //to id ido me ths bashs
        LayoutInflater inflater = LayoutInflater.from(jazzLibraryAppMainActivity.this);

        for(int i = 0; i < artistAndVideoCount.size(); i++){


            if (artistAndVideoCount.get(i).getArtist_video_count()!=0) {//an o artis exei pano apo 0 videakia ,, ftia3tou chip


                Chip chipArtist= chipArtistGenerator(artistAndVideoCount.get(i));


                artistChipGroup.addView(chipArtist);

            }
        }



    }

    @SuppressLint("ResourceType")
    void artistChipPopulator(List<ArtistAndVideoCount> artistAndVideoCount, int checkedChipId){
    //auth h Overfloat version THS METHOD kaleite mono an kapoio chip einai idi epilegmeno kai thelo mono na ananeoso ta pedia
    //lambanei     ArtistAndVideoCount List kai to ID tou epilegmenou chip
    //paragei      gemizei to ArtistChipGroup me chips pou (e3erei to chip pou tha exei to idio ID me to  checkedChipId  , giati iparxei idi)
    //             to opoio emfanizete sto idio simio opou htan
    //gia kathe stixio tou ArtistList pou to plithos ton Video ths katigoria einai >0 , ftia3e ena chip
    //me ID to id tou Artist apo thn bash(to na bei ID einai kai anageo gia na doulepsei to simple selection tou kathe chipGroup)
    //setText to onoma tou Artist kai dipla kotsare to plithos ton Video POU O KATHE Artist PERIEXEI!
    //kai arxise na prostheteis to ena meta to allo ta CHIPS  sto chipGroup
    //AN omos   sunantiseis kathos trexeis to LIST engrafh    idiou ID me (checkedChipId) auto tou
    // epilegmenou chip ,mhn to prostheseis sto GROUP -- etsi kaneis SKIP ena stoixeio ths LIST ,, me apotelesma to epilegmeno , na emganizete sto simio pou patithike
        LayoutInflater inflater = LayoutInflater.from(jazzLibraryAppMainActivity.this);

        for(int i = 0; i < artistAndVideoCount.size(); i++){


            if (artistAndVideoCount.get(i).getArtist_video_count()!=0) {

                Chip chipArtist= chipArtistGenerator(artistAndVideoCount.get(i));

                if(chipArtist.getId()==checkedChipId ){

                    artistChipTextCountRenewer(i,artistAndVideoCount.get(i));
                }
                else
                    artistChipGroup.addView(chipArtist,i);
            }
        }

    }


    public void artistChipTextCountRenewer(int selectedChipPosition, ArtistAndVideoCount artistAndVideoCountObject){
    //ananeosh t ou idi epilegmenou chip , vazei to neo plithos video.. epidi mporei na exoun ala3ei ta filtra
        Chip selectedChipArtist = (Chip) artistChipGroup.getChildAt(selectedChipPosition);
        selectedChipArtist.setText(artistAndVideoCountObject.getArtist_name()
                + " " + artistAndVideoCountObject.getArtist_surname()
                + "::" + artistAndVideoCountObject.getArtist_video_count());

    }



    @SuppressLint("ResourceType")
    public Chip chipArtistGenerator(ArtistAndVideoCount artistAndVideoCountObject){
    //paragei     ena chip xrisimopoiontas ta stixia tou object, kolao dipla ton arithmo ton video pou exei o kathenasa artist
    //prosarta episeis checkIcon analoga me to epilegmeno instrument chipGroup
        LayoutInflater inflater = LayoutInflater.from(jazzLibraryAppMainActivity.this);

        Chip chipArtist = (Chip) inflater.inflate(R.layout.chip_item, null, false);
        chipArtist.setText(artistAndVideoCountObject.getArtist_name()
                + " " + artistAndVideoCountObject.getArtist_surname()
                + "::" + artistAndVideoCountObject.getArtist_video_count());
        chipArtist.setId(artistAndVideoCountObject.getArtist_id());//mono etsi pianei to single selection


        if (instrumentChipGroup.getCheckedChipId() == 1) { //bass
            chipArtist.setCheckedIcon(drawable_bass_icon);
        } else if (instrumentChipGroup.getCheckedChipId() == 2) { //guitar
            chipArtist.setCheckedIcon(drawable_guitar_icon);
        } else if (instrumentChipGroup.getCheckedChipId() == 3) { //piano
            chipArtist.setCheckedIcon(drawable_piano_icon);
        } else if (instrumentChipGroup.getCheckedChipId() == 4) { //drums
            chipArtist.setCheckedIcon(drawable_drums_icon);
        } else if (instrumentChipGroup.getCheckedChipId() == 5) { //voice
            chipArtist.setCheckedIcon(drawable_voice_icon);
        } else if (instrumentChipGroup.getCheckedChipId() == 6) { //sax
            chipArtist.setCheckedIcon(drawable_sax_icon);
        } else if (instrumentChipGroup.getCheckedChipId() == 7) { //trumpet
            chipArtist.setCheckedIcon(drawable_trumpet_icon);
        } else if (instrumentChipGroup.getCheckedChipId() == 8) { //violin
            chipArtist.setCheckedIcon(drawable_violin_icon);
        } else if (instrumentChipGroup.getCheckedChipId() == 9) { //vibes
            chipArtist.setCheckedIcon(drawable_vibes_icon);
        } else if (instrumentChipGroup.getCheckedChipId() == 10) { //other
            chipArtist.setCheckedIcon(drawable_other_icon);
        }



        return chipArtist;
    }






    void typeChipPopulator(List<TypeAndVideoCount> typeAndVideoCount){
    //apla gia kathe engrafh tou LIST ftiaxneis ena CHIP kai to xoneis sto CHIPGROUP!
    //to id ido me ths bashs
        LayoutInflater inflater = LayoutInflater.from(jazzLibraryAppMainActivity.this);

        for(int i = 0; i < typeAndVideoCount.size(); i++){

            Chip chipType= chipTypeGenerator(typeAndVideoCount.get(i));

            typeChipGroup.addView(chipType);
        }
    }

    void typeChipPopulator(List<TypeAndVideoCount> typeAndVideoCount, int checkedChipId){
    //auth h Overfloat version THS METHOD kaleite mono an kapoio chip einai idi epilegmeno kai thelo mono na ananeoso ta pedia
    //lambanei     typeAndVideoCount List kai to ID tou epilegmenou chip
    //paragei      gemizei to typeChipGroup me chips pou (e3erei to chip pou tha exei to idio ID me to  checkedChipId  , giati iparxei idi)
    //             to opoio emfanizete sto idio simio opou htan , kotsarei episeis dipla apo to text to plithos ton video pou exei to kathe TYPE
    //gia kathe stixio tou TypeList pou to plithos ton Video ths katigoria einai >0 , ftia3e ena chip
    //me ID to id tou Type apo thn bash(to na bei ID einai kai anageo gia na doulepsei to simple selection tou kathe chipGroup)
    //kai arxise na prostheteis to ena meta to allo ta CHIPS  sto chipGroup
    //AN omos   sunantiseis kathos trexeis to LIST engrafh    idiou ID me (checkedChipId) auto tou
    // epilegmenou chip ,tote mhn prostheseis neo chip sthn thesh tou epidi idi iparxei apla bres to sthn othonh kai alaxe to videoCount ME TO NEO
    //to epomeno chip tha prosthethei meta to epilegmeno

        LayoutInflater inflater = LayoutInflater.from(jazzLibraryAppMainActivity.this);

        for(int i = 0; i < typeAndVideoCount.size(); i++) {
            if (typeAndVideoCount.get(i).getVideo_count()!=0) {

                Chip chipType= chipTypeGenerator(typeAndVideoCount.get(i));

            if(chipType.getId()==checkedChipId ){  //an brei to idio , apa alazi to plithos dipla apo to onoma
                typeChipTextCountRenewer(i, typeAndVideoCount.get(i));
            }
            else
                typeChipGroup.addView(chipType,i);

            }
        }

    }


    public void typeChipTextCountRenewer(int selectedChipPosition, TypeAndVideoCount typeAndVideoCountObject){
        Chip selectedChipType = (Chip) typeChipGroup.getChildAt(selectedChipPosition);
        selectedChipType.setText(typeAndVideoCountObject.getType_name()
                + "::" + typeAndVideoCountObject.getVideo_count());

    }




    public Chip chipTypeGenerator(TypeAndVideoCount typeAndVideoCountObject){
    //ftiaxneis ena chip xrisimopoiontas ta stixia tou object
        LayoutInflater inflater = LayoutInflater.from(jazzLibraryAppMainActivity.this);

        Chip chipType = (Chip) inflater.inflate(R.layout.chip_item_big, null, false);
        chipType.setText(typeAndVideoCountObject.getType_name()
                + "::" + typeAndVideoCountObject.getVideo_count());
        chipType.setId(typeAndVideoCountObject.getType_id());//mono etsi pianei to single selection

        return chipType;
    }




    void durationChipPopulator(List<DurationAndVideoCount> durationAndVideoCount){
    //apla gia kathe engrafh tou LIST ftiaxneis ena CHIP kai to xoneis sto CHIPGROUP!
    //to id ido me ths bashs
        LayoutInflater inflater = LayoutInflater.from(jazzLibraryAppMainActivity.this);

        for(int i = 0; i < durationAndVideoCount.size(); i++){

            Chip chipDuration= chipDurationGenerator(durationAndVideoCount.get(i));

            durationChipGroup.addView(chipDuration);
        }
    }

    void durationChipPopulator(List<DurationAndVideoCount> durationAndVideoCount, int checkedChipId){
    //auth h Overfloat version THS METHOD kaleite mono an kapoio chip einai idi epilegmeno kai thelo mono na ananeoso ta pedia
    //lambanei     DurationAndVideoCount List kai to ID tou epilegmenou chip
    //paragei      gemizei to DurationChipGroup me chips pou (e3erei to chip pou tha exei to idio ID me to  checkedChipId  , giati iparxei idi)
    //             to opoio emfanizete sto idio simio opou htan
    //gia kathe stixio tou durationList pou to plithos ton Video ths katigoria einai >0 , ftia3e ena chip
    //me ID to id tou duration apo thn bash(to na bei ID einai kai anageo gia na doulepsei to simple selection tou kathe chipGroup)
    //kai arxise na prostheteis to ena meta to allo ta CHIPS  sto chipGroup
    //AN omos   sunantiseis kathos trexeis to LIST engrafh    idiou ID me (checkedChipId) auto tou
    // epilegmenou chip ,mhn to prostheseis sto GROUP -- etsi kaneis SKIP ena stoixeio ths LIST ,, me apotelesma to epilegmeno , na emganizete sto simio pou patithike

        for(int i = 0; i < durationAndVideoCount.size(); i++) {

            if (durationAndVideoCount.get(i).getVideo_count() != 0) {

                Chip chipDuration= chipDurationGenerator(durationAndVideoCount.get(i));

                if(chipDuration.getId()==checkedChipId ){
                    //skip mia engrafh apo to List
                }
                else
                    durationChipGroup.addView(chipDuration,i);

            }
        }
    }

    public Chip chipDurationGenerator(DurationAndVideoCount DurationAndVideoCountObject){
        //ftiaxneis ena chip xrisimopoiontas ta stixia tou object
        LayoutInflater inflater = LayoutInflater.from(jazzLibraryAppMainActivity.this);

        Chip chipDuration = (Chip) inflater.inflate(R.layout.chip_item_big, null, false);
        chipDuration.setText(DurationAndVideoCountObject.getDuration_name());
        chipDuration.setId(DurationAndVideoCountObject.getDuration_id());

        return chipDuration;
    }




    void videoLinearLayoutHandler(List<Video> videoList){
    //lambanei       lista me VIDEO
    //paragei        youtubeView kai ta fortonei sto videoLinearLayout meso ths   youtubePlayerViewPopulator
    //svinei ola ta proigomena videoView apo to videoLinearLayout ,
    //elenxei an to vrethike to video, an den brethike dinei "not found message"
        videoLinearLayout.removeAllViews();

        youtubePlayerViewPopulator(videoList);

        videoNotFoundMessage(videoList);

    }


    void videoNotFoundMessage(List<Video> videoList){
    //lambanei       lista me VIDEO
    //paragei        minima  "not found message"
    //an i nideoList einai adia ,fortose ena textView STO videoLinearLayout pou leei  "Video Not Found"
        if(videoList.size()==0) {
            TextView videoNotFoundMessage = new TextView(this);
            videoNotFoundMessage.setText("Video Not Found");
            videoLinearLayout.addView(videoNotFoundMessage);
        }
    }





    public void youtubePlayerViewPopulator(List<Video> videoList){
    //lambanei       lista me VIDEO
    //paragei        youtubeView kai ta fortonei sto videoLinearLayout
    // kai gia kathe mia engrafh ths listas , travaei ena ana videoObject kai to stelnei ston  youTubePlayerViewGenerator
        for (int i = 0; i < videoList.size(); i++) {

            YouTubePlayerView youTubePlayerView=youTubePlayerViewGenerator(videoList.get(i));

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(1000, 800);
            layoutParams.setMargins(0, 0, 0, 20);

            videoLinearLayout.addView(youTubePlayerView,layoutParams);

        }

    }


    public YouTubePlayerView youTubePlayerViewGenerator(Video videoObject){
    //pernei to pedio Video_path apo to SETer tou video Object
    //kai to split  sto "=" oste na kratisei to deutero meros pou einai h pragmatikh ID dieuthinsh tou Video stous servers tou youtube
    //fortonei ftiaxnei youtubeplayerVies-travaei tovideo apo to youtube kai to prosarta pano tou - dinei monadiko ID gia to layout
    //epita setarei parametrous gia apostaseis kai megethi , kai telos kolaei to video sto videoLinear layout
        YouTubePlayerView youTubePlayerView = new YouTubePlayerView(jazzLibraryAppMainActivity.this);
        String[] splitedYoutubePath=split(videoObject.getVideo_path(),"=");
        youTubePlayerView.getYouTubePlayerWhenReady(youTubePlayer -> {
            youTubePlayer.cueVideo(splitedYoutubePath[1],0); //to fortonei , xoris autoplay!!

        });
        youTubePlayerView.setId(videoObject.getVideo_id()); //simantiko na mpei ID sto video

        return youTubePlayerView;
    }




    @SuppressLint("ResourceType")
    void pathLinearLayoutChipHandler(String category, String selectedInstrumentChipText){
    //lambanei          onoma katigorias pou to kalese ("instrument","artist","type","duration")   ,kai text tou epilegmenou chip
    //paragei           prostheti/alazei ena chip apo to path
    // diavazo to path ton filtron me thn filterPathReader (einai 4psifios arithmos string , string apo IDs kathe epilegmenis katigortias)
    //ftiaxno ena chip xrisimopoiontas os protipo to layout chip_item_path, bazo sto chip to text tou epilegmenou
    //chip apo to chip group pou kalese thn method (mono pou to kano split sto "::" gia na mhn exo kai to plithos mazi)
    //epita , analoga me to category pou me kalese setareis to ID tou Chip (1:instrument    2:artist    3:type     4:duration)
    //kalese ton  pathLinearLayoutChipPopulator
        LayoutInflater inflater = LayoutInflater.from(jazzLibraryAppMainActivity.this);

        String pathFilter = filterPathReader();

        Chip newPathChip = (Chip) inflater.inflate(R.layout.chip_item_path, null, false);
        String[] trimmedChipText=split(selectedInstrumentChipText,"::");
        newPathChip.setText(trimmedChipText[0]);

        if (category.equals("instrument")){

            pathLinearLayoutChipPopulator(newPathChip,pathFilter,1);
        }
        else if (category.equals("artist")){

            pathLinearLayoutChipPopulator(newPathChip,pathFilter,2);
        }
        else if (category.equals("type")){

            pathLinearLayoutChipPopulator(newPathChip,pathFilter,3);
        }
        else if (category.equals("duration")){

            pathLinearLayoutChipPopulator(newPathChip,pathFilter,4);
        }


    }


    public void pathLinearLayoutChipPopulator(Chip newPathChip,String pathFilter,int categoryId){
    //lambanei     to neo chip pou idi exei dilothei ektos apo to id tou
    //             to 4psifio string me ta id ton paron stixion tou path
    //             to ID  tou categoru pou se kalese
    //paragei      an sto path den iparxei idi chip tis idias katigoria apo auth pou kalese thn MeTHOD
    //             TOte prosthese to chip sto telos , alios antikatestise to palio chip me to kenourio
    //mono 1 chip apo kathe ID tha na einai sto PATH ,kai mono os 4 chips
        String categoryIdToString=String.valueOf(categoryId);

        newPathChip.setId(categoryId);
        if(pathFilter.contains(categoryIdToString)){
            int oldPathChipPosition=pathFilter.indexOf(categoryIdToString);
            pathBarLinearLayout.removeViewAt(oldPathChipPosition);
            pathBarLinearLayout.addView(newPathChip,oldPathChipPosition);
        }
        else {
            pathBarLinearLayout.addView(newPathChip);

        }
    }


    String filterPathReader(){
    //paragei     ena string apo ta ID tou kath chip
    //koitaei to pathLinearLayout , kai fortonei ta id tou kathe paidiou se ena String!
    //to string thelo na exei 4 stoixia max! , sthn sinexeia siblironei me 0s  (zeros)
        StringBuffer pathFilter = new StringBuffer("");

        int i=0;
        int j=0;

        while(i < pathBarLinearLayout.getChildCount()) {
            pathFilter.append(pathBarLinearLayout.getChildAt(i).getId());
            i++;
            j++;
        }

        while(j < 4) {

            pathFilter.append(0);
            j++;
        }

        return pathFilter.toString();
    }






    @SuppressLint("ResourceType")
    void resetUnselectedChips(String category,int selectedCipId){
    //lambanei             onoma katigorias pou to kalese ("instrument","artist","type","duration")   ,kai ID tou epilegmenou chip
    //paragei              sbeinei kathe chip apo to  chip group , pou den einai to epilegmeno
    //to category dilonei poios kalei to function, to selectedID einai to ID tou epilegmenou Chip
    //auto pou ginete parakato , h function svinei ola ta chips pou den einai to epilegmeno
    //auto epitinxonete : loop , remove to arxiko stixio se kathe loop , an breis to child apo to
    // ID sinexise thn diagrafh apo to epomeno stixio os to telos

        int step=0;

        if (category.equals("instrument")) {

            int childCount= instrumentChipGroup.getChildCount();

            for(int i=0;i<childCount;i++)
                if (instrumentChipGroup.getChildAt(step).getId()==selectedCipId) {
                    step++;
                }
                else{
                    instrumentChipGroup.removeViewAt(step);
                }


        }
        else if (category.equals("artist")){

            int childCount= artistChipGroup.getChildCount();

            for(int i=0;i<childCount;i++)
                if (artistChipGroup.getChildAt(step).getId()==selectedCipId) {
                    step++;
                }
                else{
                    artistChipGroup.removeViewAt(step);
                }


        }
        else if(category.equals("type")){//

            int childCount= typeChipGroup.getChildCount();

            for(int i=0;i<childCount;i++)
                if (typeChipGroup.getChildAt(step).getId()==selectedCipId) {
                    step++;
                }
                else{
                    typeChipGroup.removeViewAt(step);
                }


        }
        else if (category.equals("duration")){

            int childCount= durationChipGroup.getChildCount();

            for(int i=0;i<childCount;i++)
                if (durationChipGroup.getChildAt(step).getId()==selectedCipId) {
                    step++;
                }
                else{
                    durationChipGroup.removeViewAt(step);
                }

        }

    }






    public void autoCompleteEditTextSuggestionLoader(Connection con) throws SQLException {
    //paragei     bazei ton arrayAdapterSetter , opou autos prosaptei to string[]   ston Addapter TOU autoCompleteEditText
    //prota diavazei to epilegmeno Text tou spinner, analoga me to poia epilogh einai epilegmenh
    //fortonei ta onomata se String[] , kalei  arrayAdapterSetter   pou prosartrei to string[] sto autocompeteedittext

        String spinnerSelection =searchOptionSpinner.getSelectedItem().toString();


        if (spinnerSelection.equals("Search Title")){

            String[] videoNames =JazzLibraryDAO.retriveVideoNames(con);
            arrayAdapterSetter(videoNames);
        }
        else if(spinnerSelection.equals("Search Artist")){

            String[] artistNames =JazzLibraryDAO.retriveArtistNames(con);
            arrayAdapterSetter(artistNames);
        }
        else if (spinnerSelection.equals("Contains")){

            String[] emptySuggestions = new String[0];
            arrayAdapterSetter(emptySuggestions);
        }


    }

    public void arrayAdapterSetter (String[] dropDownSuggestions){
        //lambanei    sinolo string[] me sugestions
        //paragei     prosaptei to string[]   ston Addapter TOU autoCompleteEditText

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,dropDownSuggestions);
        autoCompleteEditText.setAdapter(adapter);

    }





    public List<Video> searchBarVideoRetriever(String editTextValue,Connection con) throws SQLException {
        //lambanei       tin timh tou Edit Text
        //paragei      To sosto List<Video> apo to katalilo DAO
        //prota travao thn epilogh anazitishs apo to spinner ,
        //analoga thn epilogh kalo allo SELECT DAO sthn bash , gia na paro to result set

        String spinnerSelection =searchOptionSpinner.getSelectedItem().toString();

        List<Video> videoList = null;

        if(spinnerSelection.equals("Search Title")) {
            videoList = JazzLibraryDAO.retriveVideo_By(editTextValue,con);
        }
        else if(spinnerSelection.equals("Search Artist")) {

            String[] firstNameSurName = fullNameSeparator(editTextValue);
            videoList = JazzLibraryDAO.retriveVideo_By(firstNameSurName[0],firstNameSurName[1],con);
        }
        else if(spinnerSelection.equals("Contains")) {
            // videoList = JazzLibraryDAO.retriveVideo_By(0,0,editTxtSearchString);
            videoList = JazzLibraryDAO.retriveVideo_By(editTextValue,con);
        }

        return videoList;
    }



    private String[] fullNameSeparator(String fullName) {
        //lambanei     olokliro to onoma(first name + last name me kena endiamesa ),
        //paragei      ta dio tous xorismena se pinaka[firstname,secondname]
        //to eponimo einai panta mia le3i , h teleutea le3i "fullNameParts.length-1" to onoma einai ola ta prin
        //se periptosh pou to onoma ,apotelite apo perisoteres apo mia le3eis , sinthese to proto onoma
        //auto pou tha paraxthei tha exei ena keno sto telos , opote to kano trim()
        //balta se pinaka 2 theseon onoma/eponimo
        StringBuffer fullFirstName = new StringBuffer();

        String[] fullNameParts=fullName.split(" ");

        int firstNamePartsCount=fullNameParts.length-1;
        for(int i=0;i<firstNamePartsCount;i++)
            fullFirstName.append(fullNameParts[i]+" ");

        String[] separatedArtistName={fullFirstName.toString().trim() , fullNameParts[fullNameParts.length-1] };

        return separatedArtistName;
    }



    public void resetFilters(Connection con) throws SQLException {
    //paragei     kanei to path pali : "0000"..

        pathBarLinearLayout.removeAllViews();
        filterHandler(con);
    }



    public void setupHyperlinks() {

        TextView spotifyChanelHyperlink = findViewById(R.id.spotifyChanel);
        TextView sonemicChanelHyperlink = findViewById(R.id.sonemicChanel);
        TextView WBGORadioHyperlink = findViewById(R.id.WBGORadio);
        TextView goodReadsChanelHyperlink = findViewById(R.id.goodReadsChanel);
        TextView IMPLSHyperlink = findViewById(R.id.IMPLS);



        spotifyChanelHyperlink.setMovementMethod(LinkMovementMethod.getInstance());
        sonemicChanelHyperlink.setMovementMethod(LinkMovementMethod.getInstance());
        WBGORadioHyperlink.setMovementMethod(LinkMovementMethod.getInstance());
        goodReadsChanelHyperlink.setMovementMethod(LinkMovementMethod.getInstance());
        IMPLSHyperlink.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @SuppressLint("ResourceType")
    private void initViews(){
        Log.d(TAG,"initViews: Started");

        videoLinearLayout = findViewById(R.id.videoLinearLayout);
        pathBarLinearLayout = findViewById(R.id.pathBarLinearLayout);

        searchOptionSpinner =  findViewById(R.id.searchOptionSpinner);
        autoCompleteEditText = findViewById(R.id.autoCompleteSearchBar);

        instrumentChipGroup = findViewById(R.id.instrumentChipGroup);
        artistChipGroup = findViewById(R.id.artistChipGroup);
        typeChipGroup = findViewById(R.id.typeChipGroup);
        durationChipGroup = findViewById(R.id.durationChipGroup);

        artistCountTextView = findViewById(R.id.artistCountTextView);


        iRealProGooglePlayLink =(ImageView) findViewById(R.id.iRealProAppIcon);
        spotifyGooglePlayLink =(ImageView) findViewById(R.id.spotifyAppIcon);
        VLCGooglePlayLink =(ImageView) findViewById(R.id.VLCAppIcon);
        sonemicGooglePlayLink =(ImageView) findViewById(R.id.sonemicAppIcon);
        WBGOGooglePlayLink =(ImageView) findViewById(R.id.WBGOAppIcon);
        IMSLPGooglePlayLink =(ImageView) findViewById(R.id.IMSLPIcon);
        soundbrennerGooglePlayLink =(ImageView) findViewById(R.id.soundbrennerIcon);





        Resources res = getResources();
        drawable_bass_icon = ResourcesCompat.getDrawable(res, R.drawable.instru_icon_bass, null);
        drawable_guitar_icon = ResourcesCompat.getDrawable(res, R.drawable.instru_icon_guitar, null);
        drawable_piano_icon = ResourcesCompat.getDrawable(res, R.drawable.instru_icon_piano, null);
        drawable_drums_icon = ResourcesCompat.getDrawable(res, R.drawable.instru_icon_drums, null);
        drawable_voice_icon = ResourcesCompat.getDrawable(res, R.drawable.instru_icon_voive, null);
        drawable_sax_icon = ResourcesCompat.getDrawable(res, R.drawable.instru_icon_sax, null);
        drawable_trumpet_icon = ResourcesCompat.getDrawable(res, R.drawable.instru_icon_trumpet, null);
        drawable_violin_icon = ResourcesCompat.getDrawable(res, R.drawable.instru_icon_violin, null);
        drawable_vibes_icon = ResourcesCompat.getDrawable(res, R.drawable.instru_icon_vibes, null);








    }


}